package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 用深度优先搜索来判断是否有环
 */
public class DirectCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // 有向环中的所有顶点
    private boolean[] onStack; // 递归调用的栈上的所有顶点

    public DirectCycle(Digraph g) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        for (int s = 0; s < g.V(); s++) {
            if (!marked[s]) {
                dfs(g, s);
            }
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w: g.adj(v)) {
            if (hasCycle()) return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
            else if (onStack[w]) {
                cycle = new ArrayStack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {return cycle != null;}

    public Iterable<Integer> cycle() {
        if (hasCycle()) return cycle;
        else return null;
    }

    public static void main(String[] args) {
        Digraph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Digraph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DirectCycle cc = new DirectCycle(g);

        System.out.println("是否有环：" + cc.hasCycle());
        if (cc.hasCycle())
            for (int v: cc.cycle())
                System.out.println(v);

    }
}
