package com.seefeel.leslie.algorithm.queue;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 16/5/17
 * Time: PM11:32
 */
public class TestDeque {

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        deque.isEmpty();
        deque.isEmpty();
        deque.addLast(2);
        deque.addLast(3);
        deque.removeLast();

        deque.removeLast();

        deque.addFirst(4);
        deque.addLast(8);
        deque.removeFirst();
        deque.removeLast();
        deque.isEmpty();
        deque.size();
    }
}
