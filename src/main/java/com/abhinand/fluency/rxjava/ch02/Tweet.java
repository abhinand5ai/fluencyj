package com.abhinand.fluency.rxjava.ch02;

public class Tweet {
    public Tweet(final String message) {
        this.message = message;
    }

    private String message;

    public String getMessage(){
        return message;
    }
}
