package com.yuuy.searching;

import com.yuuy.basic.CircleQueue;

import java.util.Random;

/**
 * 二叉查找树
 * <p>
 * 我们利用二叉树来实现之前所定义的有序符号表的接口。
 * 二叉查找树是充分利用递归思想构建起来的查找算法。
 * <p>
 * 我们可以将二叉树理解成一个父节点加上它所指向的两个子树所构成的数据结构。
 * 对一个二叉查找树所进行的操作可分解为对该父节点的操作和递归的对两个子树所进行的操作。
 *
 * 这里我们对树的操作都是通过递归函数调用的形式进行的。写法上简单一点，但是运行效率可能并不是最优的。
 *
 * @param <Key>
 * @param <Value>
 */
public class BinaryST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {

    private class Node {
        Key key;
        Value value;
        Node leftChild;
        Node rightChild;
        int N;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            N = n;
        }
    }

    private Node root;

    @Override
    public Key min() {
        if (root == null) return null;
        return min(root);
    }

    private Key min(Node node) {
        if (node.leftChild == null) return node.key;
        return min(node.leftChild);
    }

    @Override
    public Key max() {
        if (root == null) return null;
        return max(root);
    }

    private Key max(Node node) {
        if (node.rightChild == null) return node.key;
        return max(node.rightChild);
    }

    @Override
    public Key floor(Key key) {
        return floor(key, root);
    }

    private Key floor(Key key, Node node) {
        if (node == null) return null; // 这里判空条件放在首位可以说是非常巧妙，避免了调用者额外调用该函数。
        int cmp = key.compareTo(node.key); // 这里用一个cmp是为了简化代码，同时避免了频繁的函数调用。
        Key k;
        if (cmp == 0) return node.key; // 这里else的用法是为了保证每次函数执行完成后，三个条件里的东西都只运行一次。
        else if (cmp < 0) return floor(key, node.leftChild);
        else {
            k = floor(key, node.rightChild);
        }
        if (k == null) return node.key;
        else return k;
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node node) {
        if (node == null) return 0;
        int t;
        if (node.leftChild == null) t = 0;
        else t = size(node.leftChild);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return rank(key, node.leftChild);
        else if (cmp > 0) return t + 1 + rank(key, node.rightChild);
        else return t;
    }

    @Override
    public Key select(int k) {
        return select(k, root);
    }

    private Key select(int k, Node node) {
        if (node == null) return null;
        int t;
        if (node.leftChild == null) t = 0;
        else t = size(node.leftChild);
        if (k < t) return select(k, node.leftChild);
        else if (k == t) return node.key;
        else return select(k - t - 1, node.rightChild);
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        CircleQueue<Key> queue = new CircleQueue<Key>();
        keys(lo, hi, root, queue);
        return queue;
    }

    private void keys(Key lo, Key hi, Node node, CircleQueue<Key> queue) {
        if (node == null) return;
        System.out.println(node.key);
        int cmplo = node.key.compareTo(lo);
        int cmphi = node.key.compareTo(hi);
        if (cmphi <= 0) // 这里不能用else的原因是为了遍历所有的元素。
            keys(lo, hi, node.leftChild, queue);
        if (cmphi <= 0 && cmplo >= 0) queue.enqueue(node.key);
        if (cmplo >= 0)
            keys(lo, hi, node.rightChild, queue);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.N;
    }

    @Override
    public void put(Key key, Value value) {
        root = put(key, value, root);
    }

    private Node put(Key key, Value value, Node node) {
        if (node == null) return new Node(key, value, 1);
        if (key.compareTo(node.key) == 0) node.value = value;
        else if (key.compareTo(node.key) < 0) {
            node.leftChild = put(key, value, node.leftChild);
        } else {
            node.rightChild = put(key, value, node.rightChild);
        }
        node.N = size(node.leftChild) + size(node.rightChild) + 1;
        return node;
    }

    @Override
    public Value get(Key key) {
        return get(key, root);
    }

    private Value get(Key key, Node node) {
        if (node == null) return null;
        else if (key.compareTo(node.key) > 0) {
            get(key, node.rightChild);
        } else if (key.compareTo(node.key) < 0) {
            get(key, node.leftChild);
        }
        return node.value;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.leftChild = delete(x.leftChild, key);
            return x;
        } else if (cmp > 0) {
            x.rightChild = delete(x.rightChild, key);
            return x;
        } else {
            Node left = x.leftChild;
            Node right = x.rightChild;
            if (right != null) {
                right.leftChild = left;
                return right;
            } else {
                return left;
            }
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    public static void main(String[] args) {
        OrderedST<Integer, Integer> symbolTree = new BinaryST<>();
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
        System.out.printf("符号表的大小是%d\n", symbolTree.size());

        System.out.printf("向下取小于2的第一各整数是%d\n", symbolTree.floor(2));

        System.out.printf("rank为2的整数的键值是%d\n", symbolTree.select(2));

        System.out.printf("7在符号表中的rank为%d\n", symbolTree.rank(7));

        System.out.printf("最大元素为%d，最小元素为%d\n", symbolTree.max(), symbolTree.min());

        System.out.printf("删除键为7的元素...\n");
        symbolTree.delete(7);

        Iterable<Integer> keyIter = symbolTree.keys();
        for (Integer key :
                keyIter) {
            System.out.printf("%d:%d ", key, symbolTree.get(key));
        }
    }
}
