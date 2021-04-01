package com.yuuy.graph;

import com.yuuy.basic.Bag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Digraph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Digraph(Scanner sc) throws FileNotFoundException {
        this(sc.nextInt());
        this.E = sc.nextInt();
        for (int i = 0; i < E; i++) {
            int v, w;
            if (sc.hasNextInt())
                v = sc.nextInt();
            else break;
            if (sc.hasNextInt())
                w = sc.nextInt();
            else break;
            addEdge(v, w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++) {
            for (int w: adj[v]) R.addEdge(w, v);
        }
        return R;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Digraph g;
        try (Scanner sc = new Scanner(new FileReader("graph.txt"))) {
            g = new Digraph(sc);
        }
        for (int i : g.adj(0)) {
            System.out.println(i);
        }
        Digraph r = g.reverse();
        for (int i : r.adj(2)) {
            System.out.println(i);
        }

    }
}
