package com.yuuy.graph;

public interface SCC {
    boolean stronglyConnected(int v, int w); // v和w是强连通吗
    int count(); // 强连通分量的总数
    int id(int v); // v所在的强连通分量的标识符
}
