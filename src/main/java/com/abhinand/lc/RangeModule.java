package com.abhinand.lc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

class Interval {
    public int start;
    public int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

class IntervalCompare implements Comparator<Interval> {

    @Override
    public int compare(Interval o1, Interval o2) {
        if (o1.start == o2.start) {
            return Integer.compare(o1.end, o2.end);
        } else {
            return Integer.compare(o2.start, o1.start);
        }
    }
}

public class RangeModule {
    public TreeSet<Interval> ranges;

    public RangeModule() {
        ranges = new TreeSet<>(new IntervalCompare());
    }

    public void addRange(int left, int right) {
        Interval newInterval = new Interval(left, right);
        ranges.add(newInterval);
        Interval prev = new Interval(-1, -1);
        for (Interval range : ranges) {
            if (prev.end > range.start){

            }
        }
    }

    public boolean queryRange(int left, int right) {
        return false;
    }

    public void removeRange(int left, int right) {
        return;
    }
}