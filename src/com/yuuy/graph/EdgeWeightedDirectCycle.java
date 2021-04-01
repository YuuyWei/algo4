package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 用深度优先搜索来判断是否有环
 */
public class EdgeWeightedDirectCycle {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private Stack<DirectedEdge> cycle; // 有向环中的所有顶点
    private boolean[] onStack; // 递归调用的栈上的所有顶点

    public EdgeWeightedDirectCycle(EdgeWeightedDigraph g) {
        marked = new boolean[g.V()];
        edgeTo = new DirectedEdge[g.V()];
        onStack = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) {
                dfs(g, s);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph g, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (DirectedEdge e: g.adj(v)) {
            int w = e.to();
            if (hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = e;
                dfs(g, w);
            }
            else if (onStack[w]) {
                cycle = new ArrayStack<>();
                for (DirectedEdge edge = edgeTo[v]; edge != edgeTo[w]; edge = edgeTo[edge.from()])
                    cycle.push(edge);
//                cycle.push(edgeTo[w]);
                cycle.push(e);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {return cycle != null;}

    public Iterable<DirectedEdge> cycle() {
        if (hasCycle()) return cycle;
        else return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        EdgeWeightedDigraph g;
        try (Scanner stdin = new Scanner(new FileReader("WeightedGraph.txt"))) {
            g = new EdgeWeightedDigraph(stdin);
        }
        EdgeWeightedDirectCycle cc = new EdgeWeightedDirectCycle(g);

        System.out.println("是否有环：" + cc.hasCycle());
        if (cc.hasCycle())
            for (DirectedEdge e: cc.cycle())
                System.out.println(e);

    }
}
