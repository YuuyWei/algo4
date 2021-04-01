package com.yuuy.string;

public class KMP {
    int[][] dfa;
    final int R = 256;
    int M;

    public KMP(String pat) {
        M = pat.length();
        dfa = new int[R][M];

        dfa[pat.charAt(0)][0] = 1;
        for (int j = 1, X = 0; j < M; j++) {
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pat.charAt(j)][j] = j+1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    public int search(String str) {
        int i, j;
        for (i = 0, j = 0; i < str.length() && j < M; i++) {
            j = dfa[str.charAt(i)][j];
        }

        if (j == M) return i - M;
        else return str.length();
    }

    public static void main(String[] args) {
        KMP kmp = new KMP("ababac");

        System.out.println(kmp.search("abababababac"));
    }
}
