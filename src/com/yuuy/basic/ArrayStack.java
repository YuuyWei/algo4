package com.yuuy.basic;

import java.util.Iterator;
import java.util.Scanner;

/**
 * 数组实现的Stack
 * @param <T>
 */
public class ArrayStack<T> implements Stack<T> {
    private T[] array;
    private int size = 0;
    private int capacity = 5;

    public ArrayStack() {
        array = (T[]) new Object[capacity];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<T>{

        private int i = size - 1;

        @Override
        public boolean hasNext() {
            return i >= 0;
        }

        @Override
        public T next() {
            return array[i--];
        }
    }
    @Override
    public void push(T item) {
        if(size>=capacity) resize(capacity * 2);
        array[size++] = item;
    }

    @Override
    public T pop() {
        if(size<capacity/4) resize(capacity/2);
        T item =  array[--size];
        array[size] = null; //避免对象游离
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T peek() {
        return array[size-1];
    }

    private void resize(int cap) {
        capacity = cap;
        T[] temp = (T[]) new Object[capacity];
        if(size>0) System.arraycopy(array, 0, temp,0,size);
        array = temp;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>();

        Scanner stdin = new Scanner(System.in);
        while(stdin.hasNextInt()) {
            stack.push(stdin.nextInt());
        }

        for(int i: stack) System.out.println(i);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        for(int i: stack) System.out.println(i);

    }

}
