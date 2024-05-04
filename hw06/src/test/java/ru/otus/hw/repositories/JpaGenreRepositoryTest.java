package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.Map;

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
        var dbGenres = Map.of(
                2L, new Genre(2L, "Genre_2"),
                3L, new Genre(3L, "Genre_3"),
                4L, new Genre(4L, "Genre_4")
        );

        var actualGenres = genreRepository.findAllByIds(dbGenres.keySet());

        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(dbGenres.values());
        actualGenres.forEach(System.out::println);
    }
}
