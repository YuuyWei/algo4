package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;

import javax.management.ValueExp;
import java.util.Random;

/**
 * 线性探测法
 * <p>
 * 使用线性探测的方法实现的Hash表，
 * <p>
 * 由于线性探测法只是单纯的用数组来盛放元素，因此数组的大小必须维持在1/2-1/8之间。
 * 调整大小的方法和可变数组的resize类似，
 *
 * @param <Key>
 * @param <Value>
 */
public class LinearProbingHashST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    private int M = 50; // 散列表的大小
    private int N;// 键值对总数
    private Key[] keys;
    private Value[] values;


    public LinearProbingHashST() {
        keys = (Key[]) new Comparable[M];
        values = (Value[]) new Object[M];
    }

    private int hash(Key key) {
        return key.hashCode() & 0x7fffffff % M; // 保证结果是正数
    }

    public void resize(int cap) {
        Key[] tKeys = (Key[]) new Comparable[cap];
        Value[] tValues = (Value[]) new Object[cap];

        if (M > 0) System.arraycopy(keys, 0, tKeys, 0, M);
        if (M > 0) System.arraycopy(values, 0, tValues, 0, M);

        keys = tKeys;
        values = tValues;
    }

    @Override
    public void put(Key key, Value value) {
        if (N >= M / 2) resize(2 * M);
        int h;
        for (h = hash(key); keys[h] != null; h = (h + 1) % M) { // 如果命中就直接改，注意h的自增是取余的。
            if (keys[h].equals(key)) { // equals()比较的是引用所指的对象，而==比较的是两引用的地址。
                values[h] = value;
                return;
            }
        }
        // 未命中的情况下h此刻对应的肯定是空的。
        N++;
        keys[h] = key;
        values[h] = value;

    }

    @Override
    public Value get(Key key) {
        for (int h = hash(key); keys[h] != null; h = (h + 1) % M) {
            if (keys[h].equals(key)) return values[h];
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        int h = hash(key);
        boolean f = false;
        while (keys[h] != null) { // 命中的情况下将该元素和后继交换，一直到后继为null
            int k = (h + 1) % M;
            if (keys[h].equals(key)) {
                exch(h, k);
                f = true;
            }
            h = k;
        }
        if (f) { // 如果命中，那么删除
            N--;
            keys[h - 1] = null;
            values[h - 1] = null;
        }
    }

    private void exch(int i, int j) {
        Key tKey = keys[i];
        Value tValue = values[i];

        keys[i] = keys[j];
        values[i] = values[j];

        keys[j] = tKey;
        values[j] = tValue;
    }


    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new CircleQueue<>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null)
                queue.enqueue(keys[i]);
        }
        return queue;
    }

    public static void main(String[] args) {
        LinearProbingHashST<Integer, Integer> symbolTree = new LinearProbingHashST<>(); // 忘加泛型参数，导致后面出现各种问题。
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
            System.out.printf("%d: %d ", key, symbolTree.get(key));
        }
    }
}
