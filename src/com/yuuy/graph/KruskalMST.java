package com.yuuy.graph;

import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.basic.QuickUnionUF;
import com.yuuy.basic.UF;
import com.yuuy.sorting.MinPQ;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class KruskalMST implements MST{
    private double weight; // 树的总权重
    private Queue<Edge> mst = new CircleQueue<>(); // 最小生成树

    public KruskalMST(EdgeWeightedGraph g) {
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e: g.edges()) {
            pq.insert(e);
        }

        UF uf = new QuickUnionUF(g.V());
        while (!pq.isEmpty() && mst.size() < g.V() - 1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(w, v)){
                uf.union(w,v);
                mst.enqueue(e);
                weight += e.weight();
            }
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

        MST mst = new KruskalMST(g);
        System.out.println(mst.weight());
        for (Edge e : mst.edges()) {
            System.out.println(e);
        }

    }
}
