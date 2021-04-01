package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 利用拓扑排序求得最长路径
 * <p>
 * 不能有环
 */
public class AcyclicLP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public AcyclicLP(EdgeWeightedDigraph g, int s) {
        Topological top = new Topological(g);
        distTo = new double[g.V()];
        edgeTo = new DirectedEdge[g.V()];

        for (int v = 0; v < g.V(); v++) distTo[v] = Double.NEGATIVE_INFINITY;
        distTo[s] = 0.0;

        for (int v : top.order()) {
            for (DirectedEdge e : g.adj(v))
                relax(e);
        }

    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }


    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new ArrayStack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] != Double.POSITIVE_INFINITY;
    }

    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedDigraph g;
        try (Scanner stdin = new Scanner(new FileReader("noCycleWeightedGraph.txt"))) {
            g = new EdgeWeightedDigraph(stdin);
        }

        for (DirectedEdge e : g.edges()) {
            System.out.printf("%s| ", e);
        }
        System.out.println();

        AcyclicLP lp = new AcyclicLP(g, 0);

        for (int v = 0; v < g.V(); v++) {
            System.out.printf("%dto%d Dist:%.2f Path: ", 0, v, lp.distTo(v));
            if (lp.hasPathTo(v))
                for (DirectedEdge e : lp.pathTo(v))
                    System.out.printf("%s| ", e);
            System.out.println();
        }
    }
}
