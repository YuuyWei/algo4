package com.yuuy.string;

public interface StringST<Value> {
    void put(String key, Value value); // 将键值对存入符号表

    Value get(String key); // 获取键为key的值

    void delete(String key); // 删除键为key的值

    default boolean contains(String key) // 返回是否存在键key
    {
        return get(key) != null;
    }

    default boolean isEmpty() // 判断符号表是否为空
    {
        return size()==0;
    }

    int size(); // 返回表中键值对数量

    Iterable<String> keys(); // 符号表中的所有键

    String longestPrefixOf(String s); // s中前缀最长的键

    Iterable<String> keysWithPrefix(String s); // 所有以s为前缀的键

    Iterable<String> keysThatMatch(String s); // 所有和s匹配的键（其中.匹配任意字符）
}
