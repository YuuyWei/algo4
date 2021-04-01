package com.yuuy.graph;

import org.jetbrains.annotations.NotNull;

public class Edge implements Comparable<Edge>{
    private final int v; // one of  Vertex
    private final int w; // another of Vertex
    private final double weight; // weight of Edge

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {return weight;}

    public int either() {return v;}
    public int other(int vertex) {
        if (vertex == w) return v;
        else if (vertex == v) return w;
        else throw new RuntimeException("Inconsistent Edge");
    }

    @Override
    public int compareTo(@NotNull Edge that) {
        if (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else return 0;
    }

    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
