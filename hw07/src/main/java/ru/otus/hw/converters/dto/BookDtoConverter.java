package ru.otus.hw.converters.dto;

import org.mapstruct.Mapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.dto.BookDto;

@Mapper(componentModel = "spring")
public interface BookDtoConverter {
    BookDto bookToBookDto (Book book);
}
