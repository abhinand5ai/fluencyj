package com.abhinand.designpatterns.observer.rxobservable;

public interface Observer<T> {
    void onNext(T t);

    void onError(Throwable t);

    void onCompleted();
}