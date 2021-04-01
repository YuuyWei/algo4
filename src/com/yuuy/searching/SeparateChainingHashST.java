package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;

import java.util.Random;

/**
 * 拉链法实现的散列表
 *
 * 其实就是用一个数组容纳一组其他容器的引用，数组的下标当作散列表的地址。
 * 其他容器可以是数组，链表，二叉树，或是其他的东西。java标准库的Hashmap就是用红黑树作为二级容器。
 *
 * 我们这里实现的比较简单，直接使用Bag容器，底层是一个数组。
 * Hash函数使用自带的hashCode函数。
 *
 * 扩容功能也没有实现，一切都是最简单的模样。
 * @param <Key>
 * @param <Value>
 */

public class SeparateChainingHashST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    private SequentialST<Key, Value>[] st;
    private int M; // 散列表的大小
    private int N;// 键值对总数

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int m) {
        M = m;
        st = (SequentialST<Key, Value>[]) new SequentialST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialST<>();
        }
    }

    private int hash(Key key) {
        return key.hashCode() & 0x7fffffff % M; // 保证结果是正数
    }

    @Override
    public void put(Key key, Value value) {
        int s = st[hash(key)].size();
        st[hash(key)].put(key, value);
        N += st[hash(key)].size() - s; // 这里是为了探测是否插入
    }

    @Override
    public Value get(Key key) {
        return st[hash(key)].get(key);
    }

    @Override
    public void delete(Key key) {
        int s = st[hash(key)].size();
        st[hash(key)].delete(key);
        N += st[hash(key)].size() - s;
    }


    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new CircleQueue<>();
        for (int i = 0; i < M; i++) {
            for (Key key :
                    st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<Integer, Integer> symbolTree = new SeparateChainingHashST<>(); // 忘加泛型参数，导致后面出现各种问题。
        Random rand = new Random(1234);
        int[] keys = new int[10];

        for (int i = 0; i < 10; i++) {
            keys[i] = rand.nextInt(10);
            System.out.printf("%d ", keys[i]);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            if (symbolTree.contains(keys[i])) symbolTree.put(keys[i], symbolTree.get(keys[i]) + 1);
            else symbolTree.put(keys[i], 1);
        }
        System.out.println(symbolTree.size());

        Iterable<Integer> keyIter = symbolTree.keys(); // java的var千万不要用，把我害惨了
        for (Integer key :
                keyIter) {
            System.out.printf("%d:%d ", key, symbolTree.get(key));
        }
    }
}
