package com.abhinand.fluency.rxjava.ch02;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import static java.util.concurrent.TimeUnit.MICROSECONDS;


public class ObserverSubscriber {
    public static void main(String[] args) throws InterruptedException {
        Observable ints =
                Observable.create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onComplete();
                        }
                ).publish().refCount();

        log("Starting");

        ints.publish().refCount();
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(i));
    }

    private static void log(Object msg) {
        System.out.println(
                Thread.currentThread().getName() +
                        ": " + msg);
    }
    
}

