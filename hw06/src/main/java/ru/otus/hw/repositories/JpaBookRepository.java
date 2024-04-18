package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> findById(long id) {
        var query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);

        var graph = em.getEntityGraph("book-all");
        query.setHint(FETCH.getKey(), graph);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Boolean existsById(long id) {
        var query = em.createQuery("select exists(select 1 from Book b where b.id = :id)", Boolean.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        var query = em.createQuery("select b from Book b", Book.class);
        var graph = em.getEntityGraph("book-author");
        query.setHint(FETCH.getKey(), graph);

        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }

        return em.merge(book);
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(book -> em.remove(book));
    }
}
