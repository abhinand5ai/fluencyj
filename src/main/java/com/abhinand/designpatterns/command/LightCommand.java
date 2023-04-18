package com.abhinand.designpatterns.command;

public class LightCommand implements Command {
    @Override
    public void execute() {
        IO.out(IO.LIGHT_ADDRESS, 1);
    }
}
