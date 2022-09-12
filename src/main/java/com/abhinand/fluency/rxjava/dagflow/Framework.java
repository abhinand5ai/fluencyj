package com.abhinand.fluency.rxjava.dagflow;

import rx.Single;
import rx.schedulers.Schedulers;

import java.util.HashMap;

public class Framework {

    private HashMap<Task, Object> dataBus = new HashMap<>();

    public void run() {
        Schedulers.io();
        dataBus = new HashMap<>();

        Single<HashMap<Task, Object>> getBalanceSingle = Single.just(dataBus).map(d -> {
            balanceRequestCreator(d);
            return d;
        }).subscribeOn(Schedulers.io());

        Single<HashMap<Task, Object>> getUser = Single.just(dataBus).map(d -> {
            d.put(Task.USER_DETAILS_TASK, getUser("a"));
            return d;
        }).subscribeOn(Schedulers.io());

        long start = System.nanoTime();
        Single.merge(getBalanceSingle, getUser).toBlocking().subscribe();
        System.out.println("Time Taken " + (System.nanoTime() - start) / 1e9);

        start = System.nanoTime();
        Single.concat(getBalanceSingle, getUser).toBlocking().subscribe();
        System.out.println("Time Taken " + (System.nanoTime() - start) / 1e9);
    }

    public String balanceRequestCreator(HashMap<Task, Object> dataBus) {
        try {
            System.out.println("Getting Balance ...");
            Thread.sleep(10000);
        } catch (  InterruptedException e) {
            e.printStackTrace();
        }
        return (String) dataBus.get(Task.USER_DETAILS_TASK);
    }

    public String getUser(String id) {
        try {
            System.out.println("Getting User ...");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "user";
    }

    public float fetchBalance(String user) {
        return 0.0f;
    }

    public static void main(String[] args) {
        Framework fw = new Framework();
        fw.run();

    }

}

enum Task {
    USER_DETAILS_TASK, TASK2
}
