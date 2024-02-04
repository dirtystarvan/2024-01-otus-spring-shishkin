package ru.otus.hw.utils;

import java.io.InputStreamReader;
import java.util.Optional;

public class ClassPathResourceUtility {
    public InputStreamReader getResourceReader(String resourcePath) {
        var inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

        return Optional.ofNullable(inputStream).map(InputStreamReader::new).orElse(null);
    }
}
