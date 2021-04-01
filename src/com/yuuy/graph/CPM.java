package com.yuuy.graph;

import java.util.Random;

public class CPM {
    public static void main(String[] args) {
        int N = 10;
        Random rand = new Random(1234);

        int source = 2*N;
        int end = 2*N + 1;
        EdgeWeightedDigraph jobGraph = new EdgeWeightedDigraph(2*N+2);
        for (int i = 0; i < N; i++) {
            double time = rand.nextDouble();
            jobGraph.addEdge(new DirectedEdge(source, i, 0));
            jobGraph.addEdge(new DirectedEdge(i, i+N, time));
            jobGraph.addEdge(new DirectedEdge(i+N, end,0));

            if (i == N - 1) break;
            jobGraph.addEdge(new DirectedEdge(i+N,i+1,0));
        }

        AcyclicLP lp = new AcyclicLP(jobGraph, source);

        if (lp.hasPathTo(end)){
            for (DirectedEdge e: lp.pathTo(end))
                System.out.println(e);
        }

        if (lp.hasPathTo(3)){
            for (DirectedEdge e: lp.pathTo(3))
                System.out.println(e);
        }

    }
}
