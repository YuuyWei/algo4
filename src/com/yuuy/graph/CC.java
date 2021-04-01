package com.yuuy.graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 用深度优先搜索来探索连通分量
 *
 * 在第一章我们利用union-find来判断两个节点是否连通，
 * 而在这里我们使用深度优先搜索来判断连通分量，
 * 理论上来说，深度优先搜索的时间复杂度要小于union-find，
 * 深度优先搜索的时间复杂度是常数级别，而UF的时间复杂度是对数级别。
 *
 * 但是实际运用中，两者的差别十分微小，
 * 而union-find算法具有一个深度优先搜索所不具有的优势，
 * 那就是它不用事先构造完整的图，再用搜索算法，而是直接读取输入，
 * 在构建整个连通过程中就能给出是否连通的答案。
 */

public class CC {
    private boolean[] marked;
    private int count;
    private int[] id;

    public CC(Graph g) {
        marked = new boolean[g.V()];
        id = new int[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int getCount() {
        return count;
    }

    public int getId(int v) {
        return id[v];
    }

    public boolean connected(int v, int w) {
        return id[w] == id[v];
    }

    public static void main(String[] args) {
        Graph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Graph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CC cc = new CC(g);

        System.out.println(cc.getCount());

        for (int v = 0; v < g.V(); v++) {
            System.out.println(cc.getId(v));
            for (int w = 0; w < g.V(); w++) {
                if (v != w) System.out.printf("%d and %d is connected: %s\n", v, w, cc.connected(v, w));
            }
        }

    }
}
