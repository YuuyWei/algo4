package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Topological {

    private Iterable<Integer> order;

    public Topological(Digraph g) {
        DirectCycle cycleFinder = new DirectCycle(g);
        if (cycleFinder.hasCycle()) throw new IllegalArgumentException("The graph is not a DAG");

        DepthFirstOrder dfs = new DepthFirstOrder(g);
        order = dfs.reversePost();
    }

    public Topological(EdgeWeightedDigraph g) {
        EdgeWeightedDirectCycle cycleFinder = new EdgeWeightedDirectCycle(g);
        if (cycleFinder.hasCycle()) throw new IllegalArgumentException("The graph is not a DAG");

        EdgeWeightedDepthFirstOrder dfs = new EdgeWeightedDepthFirstOrder(g);
        order = dfs.reversePost();
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }

    public static void main(String[] args) {
        Digraph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Digraph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Topological top = new Topological(g);

        System.out.println(top.isDAG());
        if (top.isDAG())
            for (int v : top.order())
                System.out.println(v);

    }
}
