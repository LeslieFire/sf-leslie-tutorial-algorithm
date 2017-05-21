package com.seefeel.leslie.algorithm.queue;

import java.util.Iterator;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 21/5/17
 * Time: PM5:46
 */
public class TestRandomizedQueue {

    public static void main(String[] args) {
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();

        rQueue.enqueue("hello");
        rQueue.enqueue("world");
        rQueue.enqueue("Java");
        rQueue.enqueue("C++");
        rQueue.enqueue("Python");
        rQueue.enqueue("sikuli");

        Iterator<String> it1 = rQueue.iterator();
        Iterator<String> it2 = rQueue.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            System.out.println(it1.next() + ", " + it2.next());
        }
    }
}
