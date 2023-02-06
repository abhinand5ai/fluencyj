package com.abhinand.systemdesign;

import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

class Node {

    public int key;
    public int value;
    public int freq;
    public int timeStamp;

    public Node(int key, int value, int freq, int timeStamp) {
        this.key = key;
        this.value = value;
        this.freq = freq;
        this.timeStamp = timeStamp;
    }

}

class FreqComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        int compare = Integer.compare(o1.freq, o2.freq);
        if (compare == 0) {
            return Integer.compare(o1.timeStamp, o2.timeStamp);
        }
        return compare;
    }
}

class LFUCache {
    private TreeSet<Node> freqSet;
    private HashMap<Integer, Node> cache;

    private final int capacity;
    private int size;
    private int timeStamp;

    public LFUCache(int capacity) {
        freqSet = new TreeSet<>(new FreqComparator());
        cache = new HashMap<>();
        this.capacity = capacity;
    }


    public int get(int key) {
        this.timeStamp++;
        if (cache.containsKey(key)) {
            Node ret = cache.get(key);
            prioritize(ret);
            return ret.value;
        }
        return -1;
    }

    private void prioritize(Node ret) {
        freqSet.remove(ret);
        ret.freq++;
        ret.timeStamp = this.timeStamp;
        freqSet.add(ret);
    }


    public void put(int key, int value) {
        this.timeStamp++;
        if (cache.containsKey(key)) {
            Node ret = cache.get(key);
            ret.value = value;
            prioritize(ret);
            return;
        }
        if (size == capacity) {
            Node rm = freqSet.first();
            freqSet.remove(rm);
            cache.remove(rm.key);
        } else{
            this.size++;
        }
        Node node = new Node(key, value, 1, this.timeStamp);
        cache.put(key, node);
        freqSet.add(node);
    }

    public static void main(String[] args) {
        LFUCache cache = new LFUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        System.out.println(cache.get(1));
        cache.put(3,3);
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        cache.put(4,4);
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));




    }
}



/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */