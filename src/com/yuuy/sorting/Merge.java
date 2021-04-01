package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * 归并排序
 * <p>
 * 归并排序是分治思想的典型算法，
 * 归并排序的主要思路就是先将一个大的数组分成若干小数组，
 * 对小规模数组进行排序，或者干脆分成只有一个元素的数组，
 * 再将有序的子数组两两合并成一个大数组，
 * 最终得到的数组就是有序的
 * <p>
 * 归并排序分自顶向上和自顶向下两种思路，
 * 这也是大多数分治思想算法的主要思路，
 *
 * 归并排序的优势是时间复杂度为NlogN，
 * 并且是唯一稳定的线性对数排序算法。
 * 稳定是指排序前和排序后各记录之间的相对位置不会改变。
 */
public class Merge {

    /**
     * 使用静态变量在这里是欠妥的，
     * 若有多个函数使用这个类会导致不可预料的后果。
     * 正确的方法是在sort里创建局部变量，再用参数传递进去。
     * 这里只是为了简单采取的权宜之计。
     */
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];

        sort(a, 0, N);
    }

    /**
     * 自顶向下的归并排序
     *
     * @param a
     * @param lo
     * @param hi
     */
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi - lo <= 1) return; // 这个地方可以作为小规模数组排序的入口
        int mid = (lo + hi) / 2;
        sort(a, lo, mid);
        sort(a, mid, hi);
        merge(a, lo, mid, hi);
        show(a);
    }

    /**
     * 原地归并
     * 该方法使用了一个辅助数组，用它复制原数组所有的数据，
     * 实现通过一层k次的遍历就能将两个子数组归并完毕。
     *
     * @param a
     * @param lo
     * @param mid
     * @param hi
     */
    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid;

        // 复制所用的数组
        for (int k = lo; k < hi; k++) {
            aux[k] = a[k];
        }

        // 此时可以把数组a看作空数组，所有的比较都是在辅助数组上完成的。
        for (int k = lo; k < hi; k++) {
            if (i >= mid) a[k] = aux[j++];
            else if (j >= hi) a[k] = aux[i++]; //这里必须用else，因为循环内每个条件只能执行一次
            else if (less(aux[i], aux[j])) {
                a[k] = aux[i++];
            } else {
                a[k] = aux[j++];
            }
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable @NotNull [] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Integer[] a;

        try (Scanner sc = new Scanner(new FileReader("sortString.txt"));) {
            a = sc.findAll("\\d+")
                    .map(matchResult -> Integer.parseInt(matchResult.group()))
                    .toArray(Integer[]::new);
        }
        show(a);
        sort(a);
        show(a);

    }
}
