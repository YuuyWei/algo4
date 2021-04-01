package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;

import java.util.Random;

/**
 * 符号表的二分查找实现
 *
 * 利用两个数组keys values来存储键值对，
 * 其中keys数组中的数据是有序的，values里的值和keys一一对应。
 *
 * 二分查找原理很简单，rank函数迭代版本和递归版本相互转换也比较容易。
 * 但是注意一定要保持每步操作之后keys数组必须是有序的。
 * 不然rank函数无法正常工作，其他操作结果也会收到影响。
 * @param <Key>
 * @param <Value>
 */
public class BiSearchST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private Key keys[];
    private Value values[];
    private int N = 0;

    public BiSearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    @Override
    public Key min() {
        return keys[0];
    }

    @Override
    public Key max() {
        if (N == 0) return null;
        return keys[N - 1];
    }

    @Override
    public Key floor(Key key) {
        int r = rank(key);
        return keys[r + 1];
    }

    @Override
    public Key ceiling(Key key) {
        int r = rank(key);
        return keys[r];
    }

    @Override
    public int rank(Key key) {
        int lo = 0;
        int hi = N - 1;
        int mid;

        if (N >= 1 && key.compareTo(keys[hi]) > 0) return N;

        /**
         * 二分查找难就难在对于边界的判断，
         * 要想准确的判断出边界需要把握好下面两点：
         * 1. 划分条件尽量把大于小于等于分开，这样便于理清思路。思路理清可再根据情况合并条件，简化代码行数。
         * 2. 对于数组一定要考虑空数组的情况和数组溢出的情况。
         *
         * 其次二分查找是一个典型的分治法的应用。一定要保证每次要么退出循环，要么问题规模变小。
         */
        while (lo <= hi) {
            mid = (lo + hi) / 2;
            if (key.compareTo(keys[mid]) < 0) hi = mid - 1;
            else if (key.compareTo(keys[mid]) > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    @Override
    public Key select(int k) {
        return keys[k];
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        int rankLo = rank(lo);
        int rankHi = rank(hi);
        Queue<Key> queue = new CircleQueue<>();
        for (int i = rankLo; i < rankHi; i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) queue.enqueue(keys[rankHi]);
        return queue;
    }

    @Override
    public void put(Key key, Value value) {
        int r = rank(key);
        if (r < N && keys[r].compareTo(key) == 0) { // 里面包含了删除操作的逻辑
            keys[r] = key;
            values[r] = value;
            return;
        }
        for (int i = N; i > r; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        N++;
        keys[r] = key;
        values[r] = value;
    }

    @Override
    public Value get(Key key) {
        if (isEmpty()) return null;
        int r = rank(key);
        if (r < N && keys[r].compareTo(key) == 0) return values[r];
        return null;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void delete(Key key) {
        put(key, null);
        N--;
    }

    public static void main(String[] args) {
        OrderedST<Integer, Integer> symbolTable = new BiSearchST<>(15);
        Random rand = new Random(1234);
        int[] keys = new int[10];

        for (int i = 0; i < 10; i++) {
            keys[i] = rand.nextInt(10);
            System.out.printf("%d ", keys[i]);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            if (symbolTable.contains(keys[i])) symbolTable.put(keys[i], symbolTable.get(keys[i]) + 1);
            else symbolTable.put(keys[i], 1);
        }
        System.out.printf("符号表的大小是%d\n", symbolTable.size());

        System.out.printf("向下取小于2的第一各整数是%d\n", symbolTable.floor(2));

        System.out.printf("rank为2的整数的键值是%d\n", symbolTable.select(2));

        System.out.printf("7在符号表中的rank为%d\n", symbolTable.rank(7));

        Iterable<Integer> keyIter = symbolTable.keys();
        for (Integer key :
                keyIter) {
            System.out.printf("%d:%d ", key, symbolTable.get(key));
        }
    }
}
