package com.abhinand.designpatterns.command;

import javax.management.Query;

public class ButtonCommand implements Command {

    public ButtonCommand() {
    }

    @Override
    public void execute() {
        CommandList.COMMANDS.add(buttonHasBeenPressed() ? new LightCommand() : this);
    }

    private boolean buttonHasBeenPressed() {
        return IO.in(IO.BUTTON_ADDRESS) == 0;
    }
}
