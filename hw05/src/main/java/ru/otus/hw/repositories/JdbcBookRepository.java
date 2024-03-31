package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final GenreRepository genreRepository;

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<Book> findById(long id) {
        var sql = """
                select books.id, books.title, authors.id, authors.full_name, genres.id, genres.name
                from books left join authors on books.author_id = authors.id
                           left join books_genres on books.id = books_genres.book_id
                           left join genres on books_genres.genre_id = genres.id
                where books.id = :id
                """;

        return Optional.ofNullable(jdbc.query(sql, Map.of("id", id), new BookResultSetExtractor()));
    }

    @Override
    public List<Book> findAll() {
        var genres = genreRepository.findAll();
        var relations = getAllGenreRelations();
        var books = getAllBooksWithoutGenres();
        mergeBooksInfo(books, genres, relations);

        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }

        return update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    private List<Book> getAllBooksWithoutGenres() {
        var sql = """
                select books.id, title, authors.id, full_name
                from books left join authors on books.author_id = authors.id
                """;
        return jdbc.query(sql, new BookRowMapper());
    }

    private List<BookGenreRelation> getAllGenreRelations() {
        return jdbc.query("select book_id, genre_id from books_genres", new GenreRelationsRowMapper());
    }

    private void mergeBooksInfo(List<Book> booksWithoutGenres, List<Genre> genres,
                                List<BookGenreRelation> relations) {
        var bookMapping = relations.stream().collect(Collectors.groupingBy(relation -> relation.book_id));
        var genreMapping = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));

        for (var book: booksWithoutGenres) {
            var genresList = new ArrayList<Genre>();

            for (var genreId: bookMapping.get(book.getId())) {
                Optional.ofNullable(genreMapping.get(genreId.genre_id)).ifPresent(genresList::add);
            }

            book.setGenres(genresList);
        }
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        var map = Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId());
        MapSqlParameterSource params = new MapSqlParameterSource().addValues(map);

        jdbc.update("insert into books (title, author_id) values (:title, :author_id)", params, keyHolder);

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private Book update(Book book) {
        var affected = jdbc.update("update books set title = :title, author_id = :author_id where id = :id",
                Map.of("title", book.getTitle(), "author_id", book.getAuthor().getId(), "id", book.getId()));

        if (affected == 0) {
            throw new EntityNotFoundException("Can't find Book entity with id " + book.getId());
        }

        removeGenresRelationsFor(book);
        batchInsertGenresRelationsFor(book);

        return book;
    }

    private void batchInsertGenresRelationsFor(Book book) {
        var relations = book.getGenres().stream()
                .map(genre -> new BookGenreRelation(book.getId(), genre.getId()))
                .collect(Collectors.toCollection(ArrayList::new));

        jdbc.batchUpdate("insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
                SqlParameterSourceUtils.createBatch(relations));

    }

    private void removeGenresRelationsFor(Book book) {
        jdbc.update("delete from books_genres where book_id = :book_id ", Map.of("book_id", book.getId()));
    }

    private static class BookRowMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = rs.getLong("books.id");
            var title = rs.getString("title");
            var authorId = rs.getLong("authors.id");
            var authorName = rs.getString("full_name");

            return new Book(id, title, new Author(authorId, authorName), Collections.emptyList());
        }
    }

    private static class GenreRelationsRowMapper implements RowMapper<BookGenreRelation> {
        @Override
        public BookGenreRelation mapRow(ResultSet rs, int rowNum) throws SQLException {
            var bookId = rs.getLong("book_id");
            var genreId = rs.getLong("genre_id");

            return new BookGenreRelation(bookId, genreId);
        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    @RequiredArgsConstructor
    private static class BookResultSetExtractor implements ResultSetExtractor<Book> {

        @Override
        public Book extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (!rs.next()) {
                return null;
            }
            var genres = new ArrayList<Genre>();
            var bookId = rs.getLong("books.id");
            var title = rs.getString("books.title");

            var authorId = rs.getLong("authors.id");
            var authorName = rs.getString("authors.full_name");
            var author = new Author(authorId, authorName);

            var genreId = rs.getLong("genres.id");
            var genreName = rs.getString("genres.name");

            genres.add(new Genre(genreId, genreName));

            while (rs.next()) {
                genreId = rs.getLong("genres.id");
                genreName = rs.getString("genres.name");
                genres.add(new Genre(genreId, genreName));
            }

            return new Book(bookId, title, author, genres);
        }
    }

    private record BookGenreRelation(long book_id, long genre_id) {
    }
}
