package com.yuuy.graph;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.sorting.IndexMinPQ;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PrimMST implements MST {
    private IndexMinPQ<Double> pq;
    private boolean[] marked;
    private Edge[] edgeTo;
    private double[] distTo;

    public PrimMST(EdgeWeightedGraph g) {
        pq = new IndexMinPQ<>(g.V());
        marked = new boolean[g.V()];
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];

        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) prim(g, v);
        }

    }

    private void prim(EdgeWeightedGraph g, int s) {

        distTo[s] = 0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()){
            int v = pq.delMin(); // 赋予了算法识别首尾两端的能力
            scan(g, v);
        }

    }

    private void scan(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e: g.adj(v)){
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight()<distTo[w]){
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.change(w, e.weight());
                else pq.insert(w, e.weight());
            }
        }
    }


    @Override
    public Iterable<Edge> edges() {
        Queue<Edge> queue = new CircleQueue<>();
        for (int v = 0; v < edgeTo.length; v++) {
            Edge e = edgeTo[v];
            if (e != null)
                queue.enqueue(e);
        }
        return queue;
    }

    @Override
    public double weight() {
        double weight=0.0;
        for (Edge e: edgeTo) {
            weight+=e.weight();
        }

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
