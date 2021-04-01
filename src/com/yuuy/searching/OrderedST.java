package com.yuuy.searching;

public interface OrderedST<Key extends Comparable<Key>, Value> extends ST<Key, Value>{

    default void deleteMin() // 删除最小的键
    {
        delete(min());
    }

    default void deleteMax() // 删除最大的键
    {
        delete(max());
    }

    Key min();

    Key max();

    Key floor(Key key); // 返回小于key的最大键值

    Key ceiling(Key key); // 返回大于key的最小键值

    int rank(Key key); // 返回小于key的键的数量

    Key select(int k); // 选取排名为k的键

    default int size(Key lo, Key hi) // [lo..hi]之间所有键值对的数量
    {
        if (hi.compareTo(lo) < 0) return 0;
        else if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }


    Iterable<Key> keys(Key lo, Key hi); // [lo..hi]之间所有键

    default Iterable<Key> keys()
    {
        return keys(min(), max());
    }
}
