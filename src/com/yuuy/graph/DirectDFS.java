package com.yuuy.graph;

/**
 * 有向图的深度优先搜索
 *
 * 和无向图基本一样，
 * 在垃圾搜集的标记-清除阶段有着广泛的应用。
 */
public class DirectDFS implements Search{
    private boolean[] marked;
    private int count;

    public DirectDFS(Digraph g, int s){
        marked = new boolean[g.V()];
        dfs(g, s);
    }

    public DirectDFS(Digraph g, Iterable<Integer> sources) {
        marked = new boolean[g.V()];
        for (int s: sources)
            if (!marked[s]) dfs(g, s);
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        count++;
        for(int w: g.adj(v)) if(!marked[w]) dfs(g, w);
    }

    @Override
    public boolean marked(int v) {
        return marked[v];
    }

    @Override
    public int count() {
        return count;
    }
}
