package com.abhinand.lc;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.function.DoubleSupplier;

public class SlidingWindowMedian {
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] res = new double[nums.length - k + 1];
        Comparator<Integer> cmp = (x, y) -> nums[x] != nums[y] ? Integer.compare(nums[x], nums[y]) : x - y;
        TreeSet<Integer> left = new TreeSet<>(cmp.reversed());
        TreeSet<Integer> right = new TreeSet<>(cmp);

        DoubleSupplier median = () -> k % 2 == 0
                ? ((double)nums[left.first()] + (double)nums[right.first()]) / 2
                : (double) (nums[right.first()]);

        Runnable balance = () -> {
            while (left.size() > right.size()) {
                right.add(left.pollFirst());
            }
        };
        for (int i = 0; i < k; i++) left.add(i);
        balance.run();
        res[0] = median.getAsDouble();

        for (int i = k, r = 1; i < nums.length; i++, r++) {
            if (!left.remove(i - k)) right.remove(i - k);
            right.add(i);
            left.add(right.pollFirst());
            balance.run();
            res[r] = median.getAsDouble();
        }

        return res;
    }
}