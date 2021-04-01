package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 用深度优先搜索来判断是否有环
 */
public class Cycle {
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph g) {
        marked = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) {
                dfs(g, s, s);
            }
        }
    }

    private void dfs(Graph g, int v, int u) {
        marked[v] = true;
        for (int w: g.adj(v)) {
            if (!marked[w]) dfs(g, w, v);
            else if (w != u) // 排除刚刚的父节点，因为父节点必然是v的一个邻接点。
                hasCycle = true;
        }
    }

    public boolean hasCycle() {return hasCycle;}

    public static void main(String[] args) {
        Graph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Graph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Cycle cc = new Cycle(g);

        System.out.println("是否有环：" + cc.hasCycle());

    }
}
