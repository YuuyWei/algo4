package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.Stack;
import com.yuuy.sorting.IndexMinPQ;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Dijkstra最短路径
 *
 * 不能有负权重的边
 */
public class DijkstraSP implements SP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph g, int s) {

        for (DirectedEdge e : g.edges())
            if (e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");

        this.distTo = new double[g.V()];
        this.edgeTo = new DirectedEdge[g.V()];
        this.pq = new IndexMinPQ<>(g.V());

        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : g.adj(v))
                relax(e);
        }
    }

    private void relax(DirectedEdge e) {

        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo(v) + e.weight()) {
            distTo[w] = distTo(v) + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.change(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }

    }

    @Override
    public double distTo(int v) {
        return distTo[v];
    }

    @Override
    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new ArrayStack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    @Override
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedDigraph g;
        try (Scanner stdin = new Scanner(new FileReader("weightedGraph.txt"))) {
            g = new EdgeWeightedDigraph(stdin);
        }

        for (DirectedEdge e : g.edges()) {
            System.out.printf("%s", e);
        }
        System.out.println();

        SP sp = new DijkstraSP(g, 0);

        for (int v = 0; v < g.V(); v++) {
            System.out.printf("%dto%d Dist:%.2f Path: ", 0, v, sp.distTo(v));
            if (sp.hasPathTo(v))
                for (DirectedEdge e : sp.pathTo(v))
                    System.out.printf("%s ", e);
            System.out.println();
        }
    }
}
