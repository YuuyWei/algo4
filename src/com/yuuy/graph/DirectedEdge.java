package com.yuuy.graph;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.DOMImplementation;

public class DirectedEdge implements Comparable<DirectedEdge>{
    private final int v; // start of Edge
    private final int w; // end of Edge
    private final double weight; // weight of Edge

    public DirectedEdge(int start, int end, double weight) {
        if (start < 0) throw new IllegalArgumentException("Vertex names must be NONNEGATIVE INTEGERS");
        if (end < 0) throw new IllegalArgumentException("Vertex names must be NONNEGATIVE INTEGERS");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("weight is NaN");
        this.v = start;
        this.w = end;
        this.weight = weight;
    }

    public double weight() {return weight;}

    public int from() {return v;}

    public int to() {
        return w;
    }

    @Override
    public int compareTo(@NotNull DirectedEdge that) {
        if (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else return 0;
    }

    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(1, 2, 3.2);
        System.out.println(e);
    }
}
