package com.yuuy.basic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class QuickFindUF implements UF {
    private int[] id;
    private int count;

    public QuickFindUF(int n) {
        this.id = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        if(connected(p, q)) return;
        for (int i = 0; i < id.length; i++) {
            if(connected(i, q)) id[i] = id[p];
        }
        count--;
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public int count() {
        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try(Scanner sc = new Scanner(new FileReader("TinyUF.txt"));){
            QuickFindUF uf = null;
            if(sc.hasNextInt()) {
                uf = new QuickFindUF(sc.nextInt());
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
