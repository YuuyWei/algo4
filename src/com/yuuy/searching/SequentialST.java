package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;

import java.util.Random;

public class SequentialST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    private Node first;
    private int size;

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public void delete(Key key) {
        put(key, null);
        size++;
    }

    @Override
    public void put(Key key, Value value) {
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        first = new Node(key, value, first);
        size++;
    }

    @Override
    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next)
            if (key.equals(x.key)) return x.value;
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new CircleQueue<>();
        for (Node x = first; x != null; x = x.next) {
            if (x.value != null)
                queue.enqueue(x.key);
//            System.out.printf("%d: %d ", x.key, x.value);
//            System.out.println();
        }
        return queue;
    }

    public static void main(String[] args) {
        SequentialST<Integer, Integer> symbolTree = new SequentialST(); // 忘加泛型参数，导致后面出现各种问题。
        Random rand = new Random(1234);
        int[] keys = new int[10];

        for (int i = 0; i < 10; i++) {
            keys[i] = rand.nextInt(10);
            System.out.printf("%d ", keys[i]);
        }
        System.out.println();

        for (int i = 0; i < 10; i++) {
            if (symbolTree.contains(keys[i])) symbolTree.put(keys[i], symbolTree.get(keys[i])+1);
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


