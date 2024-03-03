package ru.otus.hw.shell.security;

public interface GroupContext {
    boolean isGroupSpecified();

    void setGroup(String groupId);
}
