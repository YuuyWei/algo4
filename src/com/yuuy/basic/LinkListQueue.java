package com.yuuy.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;

public class LinkListQueue<T> implements Queue<T> {
    private class Node{
        public T val;
        public Node prev;
        public Node next;
    }

    private Node head;
    private Node tail;
    private int size;

    public LinkListQueue(){
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void enqueue(T item) {
        Node neo = new Node();
        neo.val = item;
        neo.prev = head;
        neo.next = head.next;
        head.next.prev = neo;
        head.next = neo;
        size++;
    }

    @Override
    public T dequeue() {
        Node temp = tail.prev;
        temp.prev.next = tail;
        tail.prev = temp.prev;
        size--;
        return temp.val;
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
        return new LinkListQueueIterator();
    }

    private class LinkListQueueIterator implements Iterator<T>{

        private Node node = tail.prev;

        @Override
        public boolean hasNext() {
            return node.prev != null;
        }

        @Override
        public T next() {
            T value = node.val;
            node = node.prev;
            return value;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Queue<Integer> queue = new LinkListQueue<>();
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
