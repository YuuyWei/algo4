package com.yuuy.sorting;

public interface MaxPQ<Key extends Comparable<Key>>  {
    void insert(Key v); // 向队列中插入元素
    Key max(); // 返回最大元素
    Key delMax(); // 删除并返回最大元素
    boolean isEmpty(); // 返回队列是否为空值
    int size(); // 返回队列中元素的个数
}
