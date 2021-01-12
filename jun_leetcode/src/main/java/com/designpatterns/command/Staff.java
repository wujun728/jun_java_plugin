package com.designpatterns.command;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Staff {
    List<Command> commandList = new ArrayList<>();

    public void addCommand(Command command) {
        commandList.add(command);
    }

    public void exec() {
        for (Command command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}
