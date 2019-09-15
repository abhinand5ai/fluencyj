package com.abhinand.designpatterns.observer.rxobservable;

public interface Subscription {
    void unsubscribe();

    boolean isUnsubscribed();
}
