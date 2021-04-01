package com.yuuy.graph;

import com.yuuy.basic.Bag;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Graph {
    private final int V; // 顶点的数量
    private int E; // 边的数量
    private Bag<Integer>[] adj; //邻接表 装着图中所有的边

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public Graph(Scanner sc) {
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
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph g;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Graph(stdin);
        }
        for (int i :
                g.adj(2)) {
            System.out.println(i);
        }
    }
}
