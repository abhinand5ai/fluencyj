package com.abhinand.fluency.rxjava.ch02;

import rx.Observable;
import rx.Subscriber;

import static java.util.concurrent.TimeUnit.MICROSECONDS;


public class ObserverSubscriber {
    public static void main(String[] args) throws InterruptedException {
        clientThreadOperation();
        Observable ints =
                Observable.create(subscriber -> {
                            log("Create");
                            subscriber.onNext(42);
                            subscriber.onCompleted();
                        }
                ).publish().refCount();

        log("Starting");

        ints.publish().refCount();
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe((Long i) -> log(i));
    }

    public static void log(Object msg) {
        System.out.println(
                Thread.currentThread().getName() +
                        ": " + msg);
    }

    private static void clientThreadOperation(){
        log("before");
        Observable.range(3,10).subscribe(ObserverSubscriber::log);
        log("After");

        Observable<Integer> ints = Observable
                .create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        log("Create");
                        subscriber.onNext(5);
                        subscriber.onNext(6);
                        subscriber.onNext(7);
                        subscriber.onCompleted();
                        log("Completed");
                    }
                });
        log("Starting");
        ints.subscribe(i -> log("Element: " + i));
        log("Exit");
    }
    
}

