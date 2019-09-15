package com.abhinand.designpatterns.observer.rxobservable;

public interface Observable<T> {
    Subscription subscribe(Observer s);
}
