package ru.otus.hw;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw.service.TestRunnerService;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {
	private static TestRunnerService testRunner;

	private final TestRunnerService testRunnerImpl;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		testRunner.run();
	}

	@PostConstruct
	void init() {
		testRunner = testRunnerImpl;
	}

}
