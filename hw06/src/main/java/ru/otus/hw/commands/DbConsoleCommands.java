package ru.otus.hw.commands;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.SQLException;

@ShellComponent
public class DbConsoleCommands {

    @ShellMethod(value = "Start H2 web console", key = "h2c")
    public void startH2Console() throws SQLException {
        Console.main();
    }
}
