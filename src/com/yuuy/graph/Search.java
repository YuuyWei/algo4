package com.yuuy.graph;

public interface Search {
    boolean marked(int v); // v和s是连通的吗
    int count(); // 与s连通的顶点总数
}
