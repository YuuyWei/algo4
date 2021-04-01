package com.yuuy.searching;

public interface ST<Key extends Comparable<Key>, Value> {
    void put(Key key, Value value); // 将键值对存入符号表

    Value get(Key key); // 获取键为key的值

    void delete(Key key); // 删除键为key的值

    default boolean contains(Key key) // 返回是否存在键key
    {
        return get(key) != null;
    }

    default boolean isEmpty() // 判断符号表是否为空
    {
        return size()==0;
    }

    int size(); // 返回表中键值对数量

    Iterable<Key> keys();
}
