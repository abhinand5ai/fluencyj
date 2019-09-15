package com.abhinand.fluency.rxjava.ch01;


import java.math.BigInteger;
import java.util.Iterator;
import rx.Single;
import rx.schedulers.Schedulers;


public class RxSingle {
    public static void main(String[] args) {
       getDataA().mergeWith(getDataB()).subscribe();
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
