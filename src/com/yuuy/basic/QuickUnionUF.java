package com.yuuy.basic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 用数组表示了一个逻辑意义上的二叉树，
 * 使得union的时间复杂度降低到对数级别
 */
public class QuickUnionUF implements UF{

    private int[] id;
    private int[] weight;

    private int count;

    public QuickUnionUF(int n) {
        count = n;
        id = new int[n];
        weight = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        if (weight[pRoot] < weight[qRoot]) {
            id[pRoot] = qRoot;
            weight[qRoot] += weight[pRoot];
        } else {
            id[qRoot] = pRoot;
            weight[pRoot] += weight[qRoot];
        }
        count--;
    }

    @Override
    public int find(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    @Override
    public int count() {
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try(Scanner sc = new Scanner(new FileReader("TinyUF.txt"));){
            QuickUnionUF uf = null;
            if(sc.hasNextInt()) {
                uf = new QuickUnionUF(sc.nextInt());
            } else {
                return;
            }
            while(sc.hasNextInt()){
                int p = sc.nextInt();
                if(!sc.hasNextInt()) break;
                int q = sc.nextInt();
                uf.union(p,q);
            }
            System.out.println(uf.count());

        }
    }
}


