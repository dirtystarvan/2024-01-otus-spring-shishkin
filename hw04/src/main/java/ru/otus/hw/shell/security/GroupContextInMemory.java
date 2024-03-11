package ru.otus.hw.shell.security;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GroupContextInMemory implements GroupContext {

    private String groupId;

    @Override
    public boolean isGroupSpecified() {
        return Objects.nonNull(groupId);
    }

    @Override
    public void setGroup(String groupId) {
        this.groupId = groupId;
    }
}
