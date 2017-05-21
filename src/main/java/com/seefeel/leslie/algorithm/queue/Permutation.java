package com.seefeel.leslie.algorithm.queue;

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 21/5/17
 * Time: PM5:23
 */
public class Permutation {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("at least one parameter for the number to print");
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();


        while (!StdIn.isEmpty()) {
            if (queue.size() >= k && !queue.isEmpty()) {
                queue.dequeue();
            }
            String str = StdIn.readString();
            queue.enqueue(str);
        }

        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < k && iterator.hasNext(); ++i) {
            System.out.println(iterator.next());

        }
    }
}
