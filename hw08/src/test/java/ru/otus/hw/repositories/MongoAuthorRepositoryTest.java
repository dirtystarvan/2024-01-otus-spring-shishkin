package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;

class MongoAuthorRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testFindAll() {
        var actualAuthors = authorRepository.findAll();
        var expectedAuthors = mongoTemplate.findAll(Author.class);

        assertThat(actualAuthors).containsExactlyElementsOf(expectedAuthors);
        actualAuthors.forEach(System.out::println);
    }

    @Test
    void testFindById() {
        var expectedAuthors = mongoTemplate.findAll(Author.class);

        for (var author : expectedAuthors) {
            var actualAuthor = authorRepository.findById(author.getId());
            assertThat(actualAuthor).isEqualTo(author);
        }
    }
}
