package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 深度优先搜索解决Two Color问题
 */
public class TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColorable;

    public TwoColor(Graph g) {
        marked = new boolean[g.V()];
        color = new boolean[g.V()];
        isTwoColorable = true;

        for (int s = 0; s < g.V(); s++) {
            if (!marked[s])
                dfs(g, s);
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(g, w);
            } else if (color[w] == color[v]) {
                isTwoColorable = false;
            }
        }
    }

    public boolean isTwoColorable() {
        return isTwoColorable;
    }

    public static void main(String[] args) {
        Graph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Graph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TwoColor twoColor = new TwoColor(g);

        System.out.println("是一幅二分图吗：" + twoColor.isTwoColorable);
    }
}
