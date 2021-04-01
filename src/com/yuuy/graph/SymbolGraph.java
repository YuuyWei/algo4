package com.yuuy.graph;

import com.yuuy.searching.LinearProbingHashST;
import com.yuuy.searching.ST;
import com.yuuy.searching.SeparateChainingHashST;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SymbolGraph {
    private ST<String, Integer> st; // 符号名→索引
    private String[] keys; // 索引→符号名
    private Graph g;

    public SymbolGraph(String fileName, String sp) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader(fileName));
        st = new LinearProbingHashST<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] a = line.split(sp);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }

        keys = new String[st.size()];
        for (String s : st.keys()) {
            keys[st.get(s)] = s;
        }

        g = new Graph(st.size());
        Scanner sc2 = new Scanner(new FileReader(fileName));
        while (sc2.hasNextLine()) {
            String[] a = sc2.nextLine().split(sp);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                g.addEdge(v, st.get(a[i]));
            }
        }
    }

    public boolean contains(String s) {
        return st.contains(s);
    }

    public int index(String s) {
        return st.get(s);
    }

    public String name(int v) {
        return keys[v];
    }

    public Graph G() {
        return g;
    }

    public static void main(String[] args) throws FileNotFoundException {
        SymbolGraph symbolGraph = new SymbolGraph("SymbolGraph.txt", " ");
        Graph g = symbolGraph.G();

        String source = "yuuy";

        for (int w : g.adj(symbolGraph.index(source))){
            System.out.println(symbolGraph.name(w));
        }
    }
}
