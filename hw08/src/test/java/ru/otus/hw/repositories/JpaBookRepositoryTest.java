package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//class JpaBookRepositoryTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @ParameterizedTest
//    @ValueSource(longs = {1, 2, 3})
//    void testFindById(Long id) {
//        var actualBook = bookRepository.findById(id).get();
//        var expectedBook = testEntityManager.find(Book.class, id);
//
//        assertThat(expectedBook).isEqualTo(actualBook);
//    }
//
//    @Test
//    void testFindAll() {
//        var actualBooks = bookRepository.findAll();
//        var expectedBooks = getDbBooks(getDbAuthors(), getDbGenres());
//
//        assertThat(expectedBooks).containsExactlyElementsOf(actualBooks);
//    }
//
//    @Test
//    void testSaveInsert() {
//        var dbAuthors = getDbAuthors();
//        var dbGenres = getDbGenres();
//
//        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0),
//                List.of(dbGenres.get(0), dbGenres.get(2)));
//
//        var actualBook = bookRepository.save(expectedBook);
//
//        assertThat(actualBook).isNotNull()
//                .matches(book -> book.getId() > 0)
//                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);
//
//        assertThat(testEntityManager.find(Book.class, actualBook.getId())).isEqualTo(actualBook);
//    }
//
//    @Test
//    void testSaveUpdate() {
//        var dbAuthors = getDbAuthors();
//        var dbGenres = getDbGenres();
//
//        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2),
//                List.of(dbGenres.get(4), dbGenres.get(5)));
//
//        assertThat(testEntityManager.find(Book.class, expectedBook.getId())).isNotEqualTo(expectedBook);
//
//        var actualBook = bookRepository.save(expectedBook);
//
//        assertThat(actualBook).isNotNull()
//                .matches(book -> book.getId() > 0)
//                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);
//
//        assertThat(testEntityManager.find(Book.class, actualBook.getId())).isEqualTo(actualBook);
//    }
//
//    @Test
//    void testDeleteById() {
//        assertThat(testEntityManager.find(Book.class, 1L)).isNotNull();
//        bookRepository.deleteById(1L);
//        assertThat(testEntityManager.find(Book.class, 1L)).isNull();
//    }
//
//    private static List<Author> getDbAuthors() {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Author(id, "Author_" + id))
//                .toList();
//    }
//
//    private static List<Genre> getDbGenres() {
//        return IntStream.range(1, 7).boxed()
//                .map(id -> new Genre(id, "Genre_" + id))
//                .toList();
//    }
//
//    private static List<Book> getDbBooks(List<Author> dbAuthors, List<Genre> dbGenres) {
//        return IntStream.range(1, 4).boxed()
//                .map(id -> new Book(id,
//                        "BookTitle_" + id,
//                        dbAuthors.get(id - 1),
//                        dbGenres.subList((id - 1) * 2, (id - 1) * 2 + 2)
//                ))
//                .toList();
//    }
//
//}