package com.abhinand.fluency.rxjava.ch01;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import java.math.BigInteger;
import java.util.Iterator;

public class RxSingle {
    public static void main(String[] args) {
        Flowable<String> mergeAB = getDataA().mergeWith(getDataB());

    }

    public static Single<String> getDataA() {
        return Single.<String>create(o -> {
            o.onSuccess("DataA");
        }).subscribeOn(Schedulers.io());
    }

    public static Single<String> getDataB() {
        return Single.just("DataB")
                .subscribeOn(Schedulers.io());
    }
}

class NaturalNumbersIterator implements Iterator<BigInteger> {

    private BigInteger current = BigInteger.ZERO;

    public boolean hasNext() {
        return true;
    }

    @Override
    public BigInteger next() {
        current = current.add(BigInteger.ONE);
        return current;
    }
}
