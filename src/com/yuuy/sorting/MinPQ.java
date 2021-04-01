package com.yuuy.sorting;

import java.util.Random;

public class MinPQ<Key extends Comparable<Key>> {
    Key[] pq; // pq中的元素应该假设为不可变的，如果发生变化可能会导致队列失效。
    int N = 0;
    int capacity;

    private void swim(int i) {
        while (i > 1 && more(i / 2, i)) { // 比较该节点和父节点的长度
            exch(i / 2, i);
            i /= 2;
        }
    }

    private void sink(int i) {
        while (i <= N / 2) {
            int j = 2 * i;
            if (j < N && more(j, j + 1)) j++;
            if (more(j,i)) break; // 如果j指向的元素小于i，那么说明到位了。
            exch(i, j);
            i = j;
        }
    }

    private boolean more(int i, int j) {
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void resize(int capacity) {
        this.capacity = capacity;
        Key[] temp = (Key[]) new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public MinPQ() {
        this.capacity = 5;
        pq = (Key[]) new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
    }

    public MinPQ(int capacity) {
        this.capacity = capacity;
        pq = (Key[]) new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
    }

    public void insert(Key v) {
        if (N >= capacity) resize(2 * capacity);
        pq[++N] = v;
        swim(N);
    }

    public Key delMin() {
        if (N < capacity / 2) resize(capacity / 2);

        Key max = pq[1];
        exch(1, N);
        pq[N] = null;
        N--;
        sink(1);

        return max;
    }

    public Key min() {
        return pq[1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public static void main(String[] args) {
        int N = 7;
        MinPQ<Integer> pq = new MinPQ<>();
        Random rand = new Random(124532);
        for (int i = 0; i < N; i++) {
            int randInt = rand.nextInt(30);
            System.out.printf("%d ", randInt);
            pq.insert(randInt);
        }
        System.out.println();

        for (int i = 0; i < N; i++) {
            System.out.printf("%d ", pq.delMin());
        }

    }
}
