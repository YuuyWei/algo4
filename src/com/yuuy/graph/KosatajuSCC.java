package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 有向图的强连通分量
 * <p>
 * 图的逆序的深度遍历的逆后序 → 按照该顺序深度遍历图
 * <p>
 * 按照该次序遍历的图，每次构造函数调用dfs都只会遍历s所在的连通分量的顶点，或者访问到已经访问过的连通分量。
 * 这样所有连通分量都会被依次遍历。
 */
public class KosatajuSCC implements SCC {
    public boolean[] marked;
    public int[] id;
    public int count;

    public KosatajuSCC(Digraph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        DepthFirstOrder order = new DepthFirstOrder(g.reverse());
        for (int s : order.reversePost()) {
            System.out.println(s);
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v))
            if (!marked[w]) dfs(g, w);
    }

    @Override
    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        Digraph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Digraph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        KosatajuSCC scc = new KosatajuSCC(g);

        System.out.println("强连通分量的数量：" + scc.count());
    }
}
