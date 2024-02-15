package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private static final String TRY_AGAIN_MESSAGE = "Try again%n";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printFormattedLine("%nPlease answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        questions.forEach(question -> {
            var answers = question.answers();
            var userChoice = ioService.readIntForRangeWithPrompt(1, answers.size(),
                    question.toTestString(), TRY_AGAIN_MESSAGE);
            var isAnswerValid = userChoice == question.getCorrectAnswerIdx();

            testResult.applyAnswer(question, isAnswerValid);
        });

        return testResult;
    }
}
