package com.yuuy.sorting;

import javax.management.ConstructorParameters;
import java.util.NoSuchElementException;

public class IndexMinPQ<Key extends Comparable<Key>> implements IndexPQ<Key> {
    private int[] pq;
    private int[] qp;
    private Key[] keys;
    private int N;

//    public IndexMinPQ() {
//    }

    public IndexMinPQ(int capacity) {
        keys = (Key[]) new Comparable[capacity + 1];
        pq = new int[capacity + 1];
        qp = new int[capacity + 1];
        N = 0;

        for (int i = 0; i < capacity; i++) {
            qp[i] = -1;
        }
    }

    private void swim(int i) {
        while (i > 1 && more(i / 2, i)) { // 比较该节点和父节点的长度
            exch(i / 2, i);
            i /= 2;
        }
    }

    private void sink(int i) {
        while (2 * i <= N) {
            int j = 2 * i;
            if (j < N && more(j, j + 1)) j++;
            if (more(j, i)) break; // 如果j指向的元素小于i，那么说明到位了。
            exch(i, j);
            i = j;
        }
    }

    private boolean more(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    private void exch(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

//    private void resize(int capacity) {
//        this.capacity = capacity;
//        temp = (new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
//        for (int i = 1; i <= N; i++) {
//            temp[i] = pq[i];
//        }
//        pq = temp;
//    }

    @Override
    public void insert(int k, Key key) {
        if (contains(k)) return;
        N++; // 因为此时索引是以1开头的，所以要先加1再进行操作。
        keys[k] = key;
        pq[N] = k;
        qp[k] = N;
        swim(N);
    }

    @Override
    public void change(int k, Key key) {
        if (!contains(k)) return;
        keys[k] = key;
        swim(qp[k]);
        sink(qp[k]);
    }

    @Override
    public Key key(int k) {
        return keys[k];
    }

    @Override
    public boolean contains(int k) {
        return qp[k] != -1;
    }

    @Override
    public void delete(int k) {
        if (!contains(k)) return;
        int index = qp[k];
        exch(index, N--);
        sink(index);
        swim(index);
        qp[k] = -1;
        keys[k] = null;
    }

    public Key min() {
        return keys[pq[1]];
    }

    @Override
    public int minIndex() {
        return qp[pq[1]];
    }

    @Override
    public int delMin() {

        if (N == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        exch(1, N--);
        sink(1);
        qp[min] = -1;
        keys[min] = null;
        return min;

    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

}
