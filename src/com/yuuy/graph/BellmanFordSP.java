package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * BellmanFord最短路径
 * <p>
 * 使用场景是加权有向图中无负权重环。
 * 这个条件比其它两个算法的条件无疑要松了许多
 */
public class BellmanFordSP implements SP {

    private static final double EPSILON = 1E-14;
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private Queue<Integer> queue;
    private boolean[] onQueue;
    private int cost;
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(EdgeWeightedDigraph g, int s) {

        this.distTo = new double[g.V()];
        this.edgeTo = new DirectedEdge[g.V()];
        this.queue = new CircleQueue<>();
        this.onQueue = new boolean[g.V()];

        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(g, v);
        }
    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo(v) + e.weight() + EPSILON) {
                distTo[w] = distTo(v) + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (++cost % g.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;
            }

        }
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        // 如果有负环必然在最短路径树里
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);

        EdgeWeightedDirectCycle cycleFinder = new EdgeWeightedDirectCycle(spt);
        cycle = cycleFinder.cycle();
    }

    private boolean hasNegativeCycle() {
        return cycle != null;
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
