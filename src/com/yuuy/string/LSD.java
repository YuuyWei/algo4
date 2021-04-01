package com.yuuy.string;

/**
 * Least significant digital fist radix sort
 * It is a stable sorting algorithm but require fixed length keys
 * <p>
 * - Consider characters from right to left
 * - Stable sort using d characters as the key (using key index counting)
 *
 * - Time: 2WN
 * - Space: R + N
 *
 */

public class LSD {

    /**
     * @param a group of Strings
     * @param W Fix-length W strings
     */
    public static void sort(String[] a, int W) {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];

            for (int i = 0; i < N; i++) {
                count[a[i].charAt(d) + 1]++;
            }

            for (int i = 0; i < R; i++) {
                count[i + 1] += count[i];
            }

            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(d)]++] = a[i];
            }

            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {
        String[] a;
        a = new String[]{"abc", "ghi", "def", "jkl"};

        int w = a[0].length();
        sort(a, w);

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
