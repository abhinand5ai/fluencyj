package com.abhinand.fluency.rxjava.ch02;


import rx.Observable;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String SOME_KEY = "key";
    private static Map cache;

    static {
        cache = new HashMap();
    }

    public static void main(String[] args) {
        // write your code here
        Observable<Integer> o = Observable.create(s -> {
            s.onNext(1);
            s.onNext(2);
            s.onNext(3);
            s.onCompleted();
        });
        o.doOnNext(i -> System.out.println(Thread.currentThread()))
                .filter(i -> i % 2 != 0)
                .map(i -> "Value " + i + " processed on " + Thread.currentThread())
                .subscribe(s -> System.out.println("SOME VALUE =>" + s));
        o.map(i -> "Number " + i)
                .subscribe(System.out::println);

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

// this subscribes to a and b concurrently,
// and merges into a third sequential stream
        Observable<String> c = Observable.merge(a, b);
        c.subscribe(s -> System.out.println(s));
    }

    private static String getDataAsynchronously(String someKey) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "response";
    }

    private static String getFromCache(String someKey) {
        return null;
    }
}
