package com.abhinand.fluency.concurrency;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class MultiThreadedSequenceProcessing {

    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);

    }

}
