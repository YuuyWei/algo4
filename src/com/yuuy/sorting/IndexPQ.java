package com.yuuy.sorting;

public interface IndexPQ<Key extends Comparable<Key>> {
    void insert(int k, Key key);
    void change(int k, Key key);
    Key key(int k);
    boolean contains(int k); // 是否存在索引为k的元素
    void delete(int k); // 删去索引k及其相关联的元素
    Key min();
    int minIndex();
    int delMin();
    boolean isEmpty();
    int size();
}
