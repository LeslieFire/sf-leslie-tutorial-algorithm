package com.seefeel.leslie.algorithm.queue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 15/5/17
 * Time: PM10:24
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int length;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        length = 2;
        items = (Item[]) new Object[length];
    }

    private void resize(int capacity) {
        assert capacity >= n;

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; ++i) {
            copy[i] = items[i];
        }
        items = copy;

        this.length = capacity;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        valid(item);
        if (n == length) {
            resize(2 * length);
        }

        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        checkEmpty();

        int index = StdRandom.uniform(n);
        Item temp = items[index];
        items[index] = items[--n];
        items[n] = null;  // to avoid loitering


        if (n > 0 && n == length/4) {
            resize(length / 2);
        }
        return temp;
    }

    // return (but do not remove) a random item
    public Item sample() {
        checkEmpty();

        int index = StdRandom.uniform(n);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new RandomizedQueueIterator<>(items, n);
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private Item[] items;
        private int[] indices;
        private int n;

        RandomizedQueueIterator(Item[] items, int size) {
            this.n = 0;
            this.items = items;
            indices = new int[size];
            for (int i = 0; i < indices.length; ++i) {
                indices[i] = i;
            }

            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return n < indices.length;
        }

        @Override
        public Item next() {
            if (n == indices.length) {
                throw new NoSuchElementException();
            }

            return items[indices[n++]];
        }
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    private void valid(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}
