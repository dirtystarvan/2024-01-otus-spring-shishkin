package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Set;

@Repository
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        var query = em.createQuery("select g from Genre g", Genre.class);

        return query.getResultList();
    }

    @Override
    public List<Genre> findAllByIds(Set<Long> ids) {
        var query = em.createQuery("select g from Genre g where g.id in :ids", Genre.class);
        query.setParameter("ids", ids);

        return query.getResultList();
    }
}
