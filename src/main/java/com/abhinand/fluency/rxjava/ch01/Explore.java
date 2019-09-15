package com.abhinand.fluency.rxjava.ch01;

import java.util.concurrent.CompletableFuture;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;


public class Explore {
    public static void main(String[] args) {

        Observable.create(s -> {
            CompletableFuture.runAsync(() -> {
                int counter = 0;
                while (true) {
                    try {
                        Thread.sleep(5000);
                        s.onNext(counter++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }).map(i -> "Value " + i + " processed on " + Thread.currentThread().getName())
                .subscribe(s -> System.out.println("SOME VALUE =>" + s));
        System.out.println("Came past this");

        /* Illegal */
        Observable.create(s -> {
            new Thread(() -> {
                s.onNext("one");
                s.onNext("two");
            }).start();

            // Thread B
            new Thread(() -> {
                s.onNext("three");
                s.onNext("four");
            }).start();
        }).subscribe(System.out::println);
        /* Illegal */

        Observable<String> a = Observable.create(s -> {
            new Thread(() -> {
                s.onNext("one");
                s.onNext("two");
                s.onCompleted();
            }).start();
        });

        Observable<String> b = Observable.create(s -> {
            new Thread(() -> {
                s.onNext("three");
                s.onNext("four");
                s.onCompleted();
            }).start();
        });

        Observable.merge(a, b).subscribe(System.out::println);
        Observable<String> a_merge_b = getDataA().mergeWith(getDataB());
        a_merge_b.subscribe(System.out::println);
        while (true) {

        }
    }
    public static Single<String> getDataA() {
        return Single.<String> create(o -> {
            o.onSuccess("DataA");
        }).subscribeOn(Schedulers.io());
    }

    public static Single<String> getDataB() {
        return Single.just("DataB")
                .subscribeOn(Schedulers.io());
    }
}
