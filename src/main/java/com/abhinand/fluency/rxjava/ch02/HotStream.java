package com.abhinand.fluency.rxjava.ch02;

import rx.Observable;
import rx.subscriptions.Subscriptions;


public class HotStream {

    public static Observable<Tweet> getHotStream() {
        return Observable.create(subscriber -> {
            System.out.println("Creating a worker Thread producing tweets");
            Runnable r = () -> {
                try {
                    String[] tweets = { "ajd", "ajfif", "scala", "python", "javascript", "java", "haskell", "sml" };
                    int i = 0;
                    while (true) {
                        Thread.sleep(1000);
                        subscriber.onNext(new Tweet(tweets[i++ % 8]));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            final Thread thread = new Thread(r);
            thread.start();
            subscriber.add(Subscriptions.create(thread::interrupt));
        });

    }

    public static void main(String[] args) {
        System.out.println("hello");
        final Observable<Tweet> shareableObservable = getHotStream().publish().refCount();
        shareableObservable.subscribe(tweet -> System.out.println("Sub 1: " + tweet.getMessage()));
        shareableObservable.subscribe(tweet -> System.out.println("Sub 2: " + tweet.getMessage()));
        while (true);

    }
}
