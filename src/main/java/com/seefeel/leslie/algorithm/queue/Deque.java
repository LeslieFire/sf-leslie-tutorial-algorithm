package com.seefeel.leslie.algorithm.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ${DESCRIPTION}
 * Author: Leslie
 * Date: 15/5/17
 * Time: PM10:39
 */
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head;
    private Node<Item> tail;
    private int size;
    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = head;
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> pre;
        private Node<Item> next;

        Node(Item item) {
            this.item = item;
            this.pre = null;
            this.next = null;
        }
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;


        public ListIterator(Node<Item> first) {
            this.current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item rt = current.item;
            current = current.next;
            // iterator 不能改变原有结构
//            if (current != null) {
//                current.pre = null;
//            }
            return rt;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item, "null cannot add");

        Node<Item> first = new Node<>(item);
        if (head != null) {
            Node<Item> second = head;
            second.pre = first;
            first.next = second;
        } else {
            tail = first;
        }

        head = first;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        checkNull(item, "null cannot add");

        Node<Item> last = new Node<>(item);
        if (tail != null) {
            tail.next = last;
            last.pre = tail;
        } else {
            head = last;
        }

        tail = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkElement();
        Item rt = head.item;
        head = head.next;

        if (--size == 0) {
            tail = null;
        } else {
            head.pre = null;
        }

        return rt;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkElement();

        Item rt = tail.item;
        tail = tail.pre;

        if (--size == 0) {
            head = null;
        } else {
            tail.next = null;
        }

        return rt;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator<>(head);
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    private void checkNull(Item item, String message) {
        if (item == null) {
            throw new NullPointerException(message);
        }
    }

    private void checkElement() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty deque");
        }
    }
}
