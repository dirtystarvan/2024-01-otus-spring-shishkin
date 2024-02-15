package ru.otus.hw.domain;

import java.util.List;
import java.util.stream.IntStream;

public record Question(String text, List<Answer> answers) {
    public int getCorrectAnswerIdx() {
        if (answers.isEmpty()) {
            return 0;
        }

        int correctIndex = 0;

        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).isCorrect()) {
                correctIndex = i + 1;
                break;
            }
        }

        return correctIndex;
    }

    public String toTestString() {
        StringBuilder sb = new StringBuilder();

        sb.append(text);
        sb.append("\n-----\n");

        if (answers != null) {
            IntStream.range(0, answers.size())
                    .mapToObj(idx -> (idx + 1) + ". " + answers.get(idx).toTestString())
                    .forEach(sb::append);
        }

        return sb.toString();
    }
}
