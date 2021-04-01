package com.yuuy.graph;

import com.yuuy.basic.ArrayStack;
import com.yuuy.basic.CircleQueue;
import com.yuuy.basic.Queue;
import com.yuuy.basic.Stack;

/**
 * 深度优先搜索的顶点排序。
 *
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private Queue<Integer> pre; // 入栈的顺序
    private Queue<Integer> post; // 出栈的顺序
    private Stack<Integer> reversePost; // 出栈的顺序的逆序，即拓扑排序

    public DepthFirstOrder(Digraph g){
        marked = new boolean[g.V()];
        pre = new CircleQueue<>();
        post = new CircleQueue<>();
        reversePost = new ArrayStack<>();
        for (int v = 0; v < g.V(); v++) {
            if (!marked[v]) dfs(g, v);
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for(int w: g.adj(v)) if(!marked[w]) dfs(g, w);
        post.enqueue(v);
        reversePost.push(v);
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public Iterable<Integer> pre() {return pre;}

    public Iterable<Integer> post() {return post;}

    public Iterable<Integer> reversePost() {return reversePost;}

    public static void main(String[] args) {

    }
}
