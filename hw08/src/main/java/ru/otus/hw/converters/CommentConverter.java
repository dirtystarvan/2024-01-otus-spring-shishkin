package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
public class CommentConverter {
    public String commentToString (Comment comment) {
        return "id: %s, title: %s".formatted(comment.getId(), comment.getTitle());
    }
}
