package com.abhinand.designpatterns.command;

import java.util.List;

public class ActorModelMain {
    private List<Command> commandList;

    public void run() {
        while (commandList.size() != 0) {
            Command cmd = commandList.get(0);
            commandList.remove(0);
            cmd.execute();
        }
    }
}
