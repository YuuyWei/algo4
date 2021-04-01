package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 广度优先搜索
 *
 * 深度优先搜索和广度优先搜索最大的区别就是
 * 深度优先搜索是用栈来存放待访问的节点，
 * 而广度优先搜索是用队列来存放待访问的节点。
 *
 * 广度优先搜索相比深度优先搜索最大的优势在于
 * 它能优先找到到达一个顶点的最短路径，而DFS不能做到这一点。
 */

public class BroadFirstPaths implements Paths {
    private boolean[] marked; // 若调用过dfs()，那么设为已标记。
    private int[] edgeTo; // 从起点到已知路径上最后一个顶点。有点类似于union-find里的那个结构，数组表示的二叉树。
    private final int s; // 起点

    public BroadFirstPaths(Graph g, int s) {
        this.s = s;
        edgeTo = new int[g.V()];
        marked = new boolean[g.V()];
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {
        Queue<Integer> queue = new CircleQueue<>();
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            marked[v] = true;
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
            }
        }
    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new ArrayStack<>();
        for (int x = v; x != s; x = edgeTo[x]) path.push(x);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        Graph g = null;
        try (Scanner stdin = new Scanner(new FileReader("graph.txt"))) {
            g = new Graph(stdin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BroadFirstPaths depthFirstPaths = new BroadFirstPaths(g, 0);

        for (int v = 0; v < g.V(); v++) {
            System.out.printf("0to%d:", v);
            if (depthFirstPaths.hasPathTo(v)) {
                for (int x : depthFirstPaths.pathTo(v)) {
                    if (x == 0) System.out.printf("0");
                    else System.out.printf("-%d", x);
                }
            }
            System.out.println();
        }
    }
}