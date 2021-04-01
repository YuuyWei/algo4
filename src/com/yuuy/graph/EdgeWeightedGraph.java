package com.yuuy.graph;

import com.yuuy.basic.Bag;
import com.yuuy.searching.ST;
import com.yuuy.searching.SeparateChainingHashST;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 加权无向图
 *
 * 使用Edge对象来代替节点整数表示边，使得表示更加清晰和灵活
 * 每个临接表中都会装着两个指向同一Edge对象的引用，
 * 在解析对象时会带来不必要的开销，
 */
public class EdgeWeightedGraph {
    private final int V; // 顶点的数量
    private int E; // 边的数量
    private Bag<Edge>[] adj; //邻接表 装着图中所有的边

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedGraph(Scanner sc) {
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
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        ST st = new SeparateChainingHashST(30);
        for (int v = 0; v < V; v++) {
            for ( Edge e: adj[v]) {
                st.put(e, 0);
            }
        }
        return st.keys();
    }

    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedGraph g;
        try (Scanner stdin = new Scanner(new FileReader("weightedGraph.txt"))) {
            g = new EdgeWeightedGraph(stdin);
        }
//        for (Edge e : g.adj(2)) {
//            System.out.println(e);
//        }
        for (Edge e: g.edges()) {
            System.out.println(e);
        }
    }
}
