package com.yuuy.basic;

public interface Queue<T> extends Iterable<T> {
    void enqueue(T item);
    T dequeue();
    boolean isEmpty();
    int size();
}
