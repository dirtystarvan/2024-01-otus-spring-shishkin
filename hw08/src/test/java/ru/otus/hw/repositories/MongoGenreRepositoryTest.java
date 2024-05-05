package ru.otus.hw.repositories;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Genre;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMongock
@DataMongoTest
public class MongoGenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findAllTest() {
        var expectedGenres = mongoTemplate.findAll(Genre.class);
        var actualGenres = genreRepository.findAll();

        assertThat(actualGenres).isNotEmpty();
        assertThat(actualGenres).containsExactlyElementsOf(expectedGenres);
    }

    @Test
    void findByIdsTest() {
        var expectedGenres = mongoTemplate.findAll(Genre.class).stream().skip(2).toList();
        var expectedGenreIds = expectedGenres.stream().map(Genre::getId).collect(Collectors.toSet());

        var actualGenres = genreRepository.findAllByIdIn(expectedGenreIds);

        assertThat(actualGenres).isNotEmpty();
        assertThat(actualGenres).containsExactlyInAnyOrderElementsOf(expectedGenres);
    }

}
