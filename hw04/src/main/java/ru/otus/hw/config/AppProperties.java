package ru.otus.hw.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.Map;

@Setter
@Configuration
@ConfigurationProperties(prefix = "test")
public class AppProperties implements TestConfig, TestFileNameProvider, LocaleConfig {
    @Getter
    private int rightAnswersCountToPass;

    private Map<String, String> fileNameByLocaleTag;

    @Getter
    private Locale locale;

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public String getTestFileName() {
        return fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
