package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {
    private TestServiceImpl testService;

    @BeforeAll
    void init(@Mock IOService ioService, @Mock QuestionDao questionDao) {
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void testResultForStudent(@Mock Student student) {
        var testResult = testService.executeTestFor(student);

        assertNotNull(testResult);
        assertEquals(student, testResult.getStudent());
    }
}
