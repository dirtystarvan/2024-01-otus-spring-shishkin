package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import ru.otus.hw.utils.ClassPathResourceUtility;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    private final ClassPathResourceUtility resourceUtility;

    @Override
    public List<Question> findAll() {
        var questions = Collections.<QuestionDto>emptyList();

        try (var inputReader = resourceUtility.getResourceReader(fileNameProvider.getTestFileName())) {
            questions = new CsvToBeanBuilder<QuestionDto>(inputReader)
                    .withType(QuestionDto.class)
                    .withSeparator(';')
                    .withSkipLines(1)
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new QuestionReadException("Error while questions read", e);
        }

        return questions.stream().map(QuestionDto::toDomainObject).toList();
    }
}
