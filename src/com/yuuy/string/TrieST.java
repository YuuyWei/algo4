package com.yuuy.string;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;

public class TrieST<Value> implements StringST<Value>{
    private static int R = 256; // 字母表大小
    private Node root; // 单词查找树的根节点
    private int size;

    private static class Node {
        private Object val; // 因为java不支持范型数组
        private Node[] next = new Node[R];
    }

    @Override
    public Value get(String key) {
        Node x = get(root, key, 0);
        if(x == null) return null;
        return (Value) x.val;
    }

    @Override
    public void delete(String key) {
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    @Override
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(length);
    }

    public int search(Node x, String s, int d, int length) {
        if (x == null) return 0;
        if (x.val != null) length = d;
        if (d == s.length()) return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    @Override
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new CircleQueue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre+c, q);
        }
    }

    @Override
    public Iterable<String> keysThatMatch(String s) {
        return null;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d); // 否则找到第d个字符所对应的子单词查找树
        return get(x.next[c], key, d+1);
    }

    @Override
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
        size++;
    }

    private Node put(Node x, String key, Value val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }
}
