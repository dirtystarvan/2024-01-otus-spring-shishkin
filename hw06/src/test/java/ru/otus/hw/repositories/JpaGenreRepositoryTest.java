package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@Import(JpaGenreRepository.class)
class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository genreRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findAllTest() {
        var actualGenres = genreRepository.findAll();
        var expectedGenres = entityManager.getEntityManager()
                .createQuery("select g from Genre g", Genre.class).getResultList();

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
        actualGenres.forEach(System.out::println);
    }

    @Test
    void findBiIdsTest() {
        var testIds = Set.of(2L, 3L, 4L);
        var actualGenres = genreRepository.findAllByIds(Set.of(2L, 3L, 4L));
        var expectedGenresQuery = entityManager.getEntityManager()
                .createQuery("select g from Genre g where g.id in :ids", Genre.class);
        expectedGenresQuery.setParameter("ids", testIds);

        var expectedGenres = expectedGenresQuery.getResultList();

        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
        actualGenres.forEach(System.out::println);
    }
}