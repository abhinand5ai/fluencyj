package com.abhinand.fluency.rxjava.ch02;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import java.util.Arrays;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;


public class Explore {
    public static void main(String[] args) throws InterruptedException {
//        subscriberDemonstration();
//           intDemostrtn();
        final Subscription delayedSubscription = delayed(10).subscribe(System.out::println);

            Thread.sleep(22000);
            delayedSubscription.unsubscribe();
    }

    public static  void intDemostrtn() {
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .skip(100)
                .subscribe(ObserverSubscriber::log);
    }
    public static void subscriberDemonstration() {
        String[] tweets = { "ajd", "ajfif", "scala", "python", "javascript", "java", "haskell", "sml" };
        Observable<Tweet> observableTweets = Observable.from(tweets).map(twt -> new Tweet(twt));

        Subscriber<Tweet> subscriber = new Subscriber<Tweet>() {
            private Integer counter = 0;

            @Override public void onCompleted() {

            }

            @Override public void onError(final Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override public void onNext(final Tweet tweet) {
                if (tweet.getMessage().contains("java") || counter > 10) {
                    unsubscribe();
                }
                counter++;
                System.out.println(tweet.getMessage());
            }
        };
        Subscriber<Integer> numericLimitSubscriber = new Subscriber<Integer>() {
            private Integer counter = 0;

            @Override public void onCompleted() {

            }

            @Override public void onError(final Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override public void onNext(final Integer tweet) {
                counter++;
                if (counter > 10) {
                    unsubscribe();
                    return;
                }

                System.out.println(tweet);
            }
        };

        observableTweets.subscribe(subscriber);
        Observable.from(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)).subscribe(numericLimitSubscriber);
    }

    static <T> Observable<T> delayed(T x) {
        return Observable.create(
                subscriber -> {
                    Runnable r = () -> {
                        try {
                            Thread.sleep(10000);
                            subscriber.onNext(x);
                            Thread.sleep(10000);
                            subscriber.onNext(x);
                            System.out.println(x);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    final Thread thread = new Thread(r);
                    thread.start();
                    subscriber.add(Subscriptions.create(thread::interrupt));
                });
    }
}
