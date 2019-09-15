package com.abhinand.designpatterns.observer.rxobservable;

public interface Subscriber<T> extends Observer<T>, Subscription {
    void onNext(T t);

    void onError(Throwable t);

    void onCompleted();

    void unsubscribe();

    void setProducer(Producer p);
}
