package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.Stack;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 深度优先搜索
 *
 * 深度优先搜索顾名思义就是优先访问一个节点的子节点，
 * 而不是优先访问它的兄弟节点，
 * 在这里我们是以递归函数的形式来实现它的，
 * 而实际上，我们可以使用迭代的形式，
 * 手动构造一个栈，来容纳所有的待访问节点。
 * 实际上利用递归函数就是利用了java自带的函数栈来实现深度优先访问的。
 */
public class DepthFirstPaths implements Paths {
    private boolean[] marked; // 若调用过dfs()，那么设为已标记。
    private int[] edgeTo; // 从起点到已知路径上最后一个顶点。有点类似于union-find里的那个结构，数组表示的二叉树。
    private final int s; // 起点

    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        edgeTo = new int[g.V()];
        marked = new boolean[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
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

        DepthFirstPaths depthFirstPaths = new DepthFirstPaths(g, 0);

        for (int v = 0; v < g.V(); v++) {
            System.out.printf("0to%d:", v);
            if (depthFirstPaths.hasPathTo(v)){
                for(int x: depthFirstPaths.pathTo(v)){
                    if(x==0) System.out.printf("0");
                    else System.out.printf("-%d", x);
                }
            }
            System.out.println();
        }
    }
}