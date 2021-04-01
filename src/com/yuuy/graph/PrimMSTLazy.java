package com.yuuy.graph;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.sorting.MinPQ;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PrimMSTLazy implements MST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;
    private double weight;

    public PrimMSTLazy(EdgeWeightedGraph g) {
        marked = new boolean[g.V()];
        mst = new CircleQueue<>();
        pq = new MinPQ<>();
        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) prim(g, v);
        }
    }

    private void prim(EdgeWeightedGraph g, int s) {
        scan(g, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            assert marked[v] || marked[w];
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) scan(g, v);
            if (!marked[w]) scan(g, w);
        }
    }

    private void scan(EdgeWeightedGraph g, int v) {
        assert !marked[v];
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)])
                pq.insert(e);
        }
    }

    @Override
    public Iterable<Edge> edges() {
        return mst;
    }

    @Override
    public double weight() {
        return weight;
    }


    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedGraph g;
        try (Scanner stdin = new Scanner(new FileReader("weightedGraph.txt"))) {
            g = new EdgeWeightedGraph(stdin);
        }

        MST mst = new PrimMSTLazy(g);
        System.out.println(mst.weight());
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }

    }
}
