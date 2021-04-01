package com.yuuy.graph;

import com.yuuy.basic.Bag;
import com.yuuy.searching.ST;
import com.yuuy.searching.SeparateChainingHashST;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class EdgeWeightedDigraph {
    private final int V; // 顶点的数量
    private int E; // 边的数量
    private Bag<DirectedEdge>[] adj; //邻接表 装着图中所有的边
    private int[] inDegree; // 顶点入度

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        this.inDegree = new int[V];
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedDigraph(Scanner sc) {
        this(sc.nextInt());
        this.E = sc.nextInt();
        for (int i = 0; i < E; i++) {
            int v, w;
            double weight;
            if (sc.hasNextInt())
                v = sc.nextInt();
            else break;
            if (sc.hasNextInt())
                w = sc.nextInt();
            else break;
            if (sc.hasNextDouble())
                weight = sc.nextDouble();
            else break;
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        int start = e.from();
        int end = e.to();
        adj[start].add(e);
        inDegree[end]++;
        E++;
    }

    public int inDegree(int v) {return inDegree[v];}

    public int outDegree(int v) {
        return adj[v].size();
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        ST st = new SeparateChainingHashST(30);
        for (int v = 0; v < V; v++) {
            for ( DirectedEdge e: adj[v]) {
                st.put(e, 0);
            }
        }
        return st.keys();
    }

    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedDigraph g;
        try (Scanner stdin = new Scanner(new FileReader("weightedGraph.txt"))) {
            g = new EdgeWeightedDigraph(stdin);
        }
//        for (Edge e : g.adj(2)) {
//            System.out.println(e);
//        }
        for (DirectedEdge e: g.edges()) {
            System.out.println(e);
        }
    }
}
