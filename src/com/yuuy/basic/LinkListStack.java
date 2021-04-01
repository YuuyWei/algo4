package com.yuuy.basic;

import java.util.Iterator;
import java.util.Scanner;

/**
 * 链表实现的Stack
 * @param <T>
 */
public class LinkListStack<T> implements Stack<T> {
    private class Node{
        public T val;
        public Node next;

        public Node(T item, Node n){
            val = item;
            next = n;
        }
    }

    private Node top;
    private int size;

    @Override
    public void push(T item) {
        Node neo = new Node(item, top);
        top = neo;
        size++;
    }

    @Override
    public T pop() {
        if(isEmpty()) return null;
        Node temp = top;
        top = temp.next;
        size--;

        temp.next = null;
        return temp.val;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T peek() {
        return top.val;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkListStackIterator();
    }

    public class LinkListStackIterator implements Iterator<T> {
        private Node node = top;


        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            Node temp = node;
            node = temp.next;
            return temp.val;
        }
    }
    public static void main(String[] args) {
        Stack<Integer> stack = new LinkListStack<>();

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
