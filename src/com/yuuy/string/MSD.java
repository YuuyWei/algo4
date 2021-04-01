package com.yuuy.string;

import com.yuuy.sorting.Insertion;

/**
 * MSD String sort
 * <p>
 * - Partition array into R pieces according to first characters
 * - *Recursively* sort all strings that start with each characters (Key-indexed counts delineate subarrays to sort
 * <p>
 * Consider characters from left to right
 * <p>
 * Disadvantage of MSD:
 * - Access memory "randomly"
 * - Inner loop has a lot of instructions
 * - Extra space for count[]
 * - Extra space for aux[]
 */

public class MSD {
    private static int R = 256; // 进制数
    private static final int M = 0; // 切换阈值

    public static void sort(String[] a) {
        int N = a.length;
        String[] aux = new String[N]; // 复用aux

        sort(a, aux, 0, N - 1, 0);
    }

    private static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo + M) {
            return;
        }
        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
        }

        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }

        for (int r = 0; r < R; r++) {
            sort(a, aux, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    private static int charAt(String s, int index) {
        if (index < s.length()) return s.charAt(index);
        else return -1;
    }

    public static void main(String[] args) {
        String[] a;
        a = new String[]{"abc", "ghi", "def", "jkl", "fjsljfs", "jfsl", "sjfsldfja" };

        sort(a);


        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
