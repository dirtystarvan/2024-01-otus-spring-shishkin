package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.shell.security.GroupContext;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandService {
    private final TestRunnerService testRunner;

    private final GroupContext groupContext;

    @ShellMethod(value = "Set student group", key = {"g", "group"})
    public String groupSet(@ShellOption(defaultValue = "unknown") String groupId) {
        groupContext.setGroup(groupId);

        return String.format("Your group: %s", groupId);
    }

    @ShellMethodAvailability(value = "isStartCommandAvailable")
    @ShellMethod(value = "Start test program", key = {"s", "start"})
    public String start() {
        testRunner.run();

        return "Test program completed";
    }

    private Availability isStartCommandAvailable() {
        return groupContext.isGroupSpecified()
                ? Availability.available()
                : Availability.unavailable("Please setup your group first");
    }

}
