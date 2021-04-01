package com.yuuy.basic;

public interface UF {
    void union(int p, int q);
    int find(int p);
    default boolean connected(int p, int q){
        return find(p) == find(q);
    }
    int count();
}
