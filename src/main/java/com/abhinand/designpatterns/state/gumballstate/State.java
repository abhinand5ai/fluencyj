package com.abhinand.designpatterns.state.gumballstate;

public interface State {

    void insertQuarter();

    void ejectQuarter();

    void turnCrank();

    void dispense();
}
