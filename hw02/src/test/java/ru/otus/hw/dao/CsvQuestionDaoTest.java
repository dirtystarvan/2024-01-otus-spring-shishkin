package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.utils.ClassPathResourceUtility;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {
    private static final String TEST_FILE_NAME = "questions.csv";

    private static CsvQuestionDao questionDao;

    @BeforeAll
    static void init(@Mock TestFileNameProvider fileNameProvider) {
        when(fileNameProvider.getTestFileName()).thenReturn(TEST_FILE_NAME);

        questionDao = new CsvQuestionDao(fileNameProvider, new ClassPathResourceUtility());
    }

    @Test
    void readQuestionsSuccess() {
        var questions = questionDao.findAll();

        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        questions.forEach(question ->
                assertFalse(question.answers().isEmpty())
        );
    }
}
