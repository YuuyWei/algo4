package com.yuuy.basic;

/**
 * Stack接口
 * 继承自Iterable接口
 * @param <T>
 */
public interface Stack<T> extends Iterable<T> {
    void push(T item);
    T pop();
    boolean isEmpty();
    int size();
    T peek();
}


