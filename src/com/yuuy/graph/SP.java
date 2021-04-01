package com.yuuy.graph;

public interface SP {
    double distTo(int v);
    Iterable<DirectedEdge> pathTo(int v);
    boolean hasPathTo(int v);
}
