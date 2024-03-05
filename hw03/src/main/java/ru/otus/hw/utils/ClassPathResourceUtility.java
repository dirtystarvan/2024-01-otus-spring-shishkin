package ru.otus.hw.utils;

import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.Optional;

@Component
public class ClassPathResourceUtility {
    public InputStreamReader getResourceReader(String resourcePath) {
        var inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        return Optional.ofNullable(inputStream).map(InputStreamReader::new).orElse(null);
    }
}
