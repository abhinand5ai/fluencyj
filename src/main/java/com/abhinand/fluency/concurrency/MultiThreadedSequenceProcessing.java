package com.abhinand.fluency.concurrency;

import rx.Observable;

public class MultiThreadedSequenceProcessing {

    public static void main(String[] args) {
        Observable.just("Hello world").subscribe(System.out::println);

    }

}
