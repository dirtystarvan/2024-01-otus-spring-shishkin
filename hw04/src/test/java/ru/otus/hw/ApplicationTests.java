package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import ru.otus.hw.service.TestRunnerService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Configuration
	@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestRunnerService.class))
	static class TestConfig {

	}
}
