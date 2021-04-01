package com.yuuy.sorting;

import java.util.Random;

/**
 * 二叉堆可以完美实现优先级队列的基本操作。
 * <p>
 * 二叉堆的表示
 * 二叉堆一般使用完全二叉树来表示，而完全二叉树又能直接使用数组来实现，相对指针实现方便了许多。
 * 使用数组表示的完全二叉树结构十分严格，但她所具有的灵活性已经足够我们高效实现优先级队列了。
 * 我们使用长度为N+1的数组来表示大小为N的堆，我们并不会使用pq[0]，为了代码的简洁。
 * 这种情况下，索引为i的数组的父节点就是int(2/i),父节点为0说明它就是根节点。
 * 为了使实现方式更加紧凑，我们不会将数组作为参数传递。
 * <p>
 * 二叉堆中的引用的对象不能发生改变，如果改变可能会导致队列整体失效
 * 不过在java中用作键的数据类型一般都是不可变的，如:String Integer Double File
 *
 * @param <Key>
 */
public class MaxBiHeap<Key extends Comparable<Key>> implements MaxPQ<Key> {
    Key[] pq; // pq中的元素应该假设为不可变的，如果发生变化可能会导致队列失效。
    int N = 0;
    int capacity;

    private void swim(int i) {
        while (i > 1 && less(i / 2, i)) { // 比较该节点和父节点的长度
            exch(i / 2, i);
            i /= 2;
        }
    }

    private void sink(int i) {
        while (i <= N / 2) {
            int j = 2 * i;
            if (j < N && less(j, j + 1)) j++;
            if (less(j,i)) break; // 如果j指向的元素小于i，那么说明到位了。
            exch(i, j);
            i = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
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

    public MaxBiHeap() {
        this.capacity = 5;
        pq = (Key[]) new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
    }

    public MaxBiHeap(int capacity) {
        this.capacity = capacity;
        pq = (Key[]) new Comparable[this.capacity + 1]; // 前面说过创建一个泛型数组是不被允许的只能new它的父类来创建
    }

    @Override
    public void insert(Key v) {
        if (N >= capacity) resize(2 * capacity);
        pq[++N] = v;
        swim(N);
    }

    @Override
    public Key delMax() {
        if (N < capacity / 2) resize(capacity / 2);

        Key max = pq[1];
        exch(1, N);
        pq[N] = null;
        N--;
        sink(1);

        return max;
    }

    @Override
    public Key max() {
        return pq[1];
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    public static void main(String[] args) {
        int N = 7;
        MaxBiHeap<Integer> pq = new MaxBiHeap<>();
        Random rand = new Random(124532);
        for (int i = 0; i < N; i++) {
            int randInt = rand.nextInt(30);
            System.out.printf("%d ", randInt);
            pq.insert(randInt);
        }
        System.out.println();

        for (int i = 0; i < N; i++) {
            System.out.printf("%d ", pq.delMax());
        }

    }
}
