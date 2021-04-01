package com.yuuy.basic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @param <T>
 */
public class CircleQueue<T> implements Queue<T> {
    private class Node {
        T val;
        Node next;
    }

    /**
     * 指向最近插入的一个节点
     */
    private Node tail;

    /**
     * 迭代器判停指示器。
     * 神之一手，使得难以实现的迭代器变得可行，同时大大简化了入队和出队的操作。
     * stop实际上是第一个插入的节点。在它之后插入的第一个元素是逻辑意义上的第一个节点。
     */
    private Node stop;
    private int size;

    public CircleQueue() {
        size = 0;
        tail = new Node();
        stop = new Node();
        tail.next = stop;
        stop.next = stop;
    }

    @Override
    public void enqueue(T item) {
        Node temp = new Node();
        temp.val = item;

        Node last = tail.next;
        last.next = temp;
        tail.next = temp;
        temp.next = stop;
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) return null;

        Node first = stop.next;
        T value = first.val;
        stop.next = first.next;

        size--;
        return value;
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
    public Iterator<T> iterator() {
        return new CircleQueueIterator();
    }

    private class CircleQueueIterator implements Iterator<T>{
        private Node p = stop.next;

        @Override
        public boolean hasNext() {
            return p != stop ;
        }

        @Override
        public T next() {
            T value = p.val;
            p = p.next;
            return value;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Queue<Integer> queue = new CircleQueue<>();
//        File dir = new File("");
//        System.out.println(dir.getAbsolutePath());

        /**
         * 利用Scanner类来读取文本文件中的信息
         */
        String filename = "test.txt";
        try (Scanner sc = new Scanner(new FileReader(filename))){
            while(sc.hasNextInt()){
                queue.enqueue(sc.nextInt());
            }
        }

        for(int i: queue){
            System.out.println(i);
        }

    }

}
