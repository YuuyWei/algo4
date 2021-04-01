package com.yuuy.sorting;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * 快速排序
 * 快速排序是基于分治思想对冒泡排序算法的一个改进。
 * 他们的共同之处就是比较之后再决定是否交换元素。
 *
 * 快速排序使用分治思想，
 * 先利用partition找到某元素在数组中的位置，同时也借此将该问题分成两个排序更小的数组的问题
 * 然后利用同样的方法求解子问题，最终递归到求解元素只有一个的数组的排序问题，那么问题就不攻自破了。
 *
 * 快速排序的基础算法尽管能在平均意义上达到NlogN的时间复杂度，但在最坏情况下却无法避免的退化成N^2。
 * 当我们总是选择了序列的最小或者最大元素作为划分点，导致二叉排序树的高度增加到N。
 * 为了避免这样的情况产生，我们可以在选区划分元素时随机选择数组中的元素，
 * 或者更进一步，先选取三个元素作为样本，取其中间的一个作为划分元素，另外两个作为左右哨兵元素。
 *
 * 当数组中重复元素过多时，我们的划分算法依然会扫描每个重复元素，这种情况我们可以规避掉。
 * 使用三划分算法，将每次划分后的数组分为三个部分，即在原来的基础上增加和划分元素相等的数组。
 *
 * 快速排序是最快的通用排序算法，他之所以快是因为他的内循环所用的指令很少，并且他还能利用缓存（因为快速排序总是顺序的访问数据）。
 * 但是快速排序是不稳定的，当需要稳定的排序算法时，建议选择归并排序。
 *
 */
public class Quick {
    public static void sort(Comparable[] a) {
        int n = a.length - 1;
        sort(a, 0, n);
    }

    private static void sort(Comparable[] a, int low, int high) {
        if (low >= high) return; // 判断条件要苛刻，不然可能会停不下来。
        int p = partition(a, low, high);
        show(a);
        sort(a, low, p - 1);
        sort(a, p + 1, high);
    }

    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo + 1;
        int j = hi;

        Random rand = new Random();
        int select = rand.nextInt(hi - lo + 1) + lo;
        if (select != lo) exch(a, lo, select);

        Comparable v = a[lo];

        while (true) {
            while (less(a[i++], v)) { // 自增函数无法写在循环体内，因为这样遇到重复元素就无法进行下去了。
                if (i >= hi) break;
            }
            while (less(v, a[j--])) {
                if (j <= lo) break;
            }
            if (i >= j) break;
            exch(a, i, j);
        }

        if (less(a[i], v)) {
            exch(a, lo, i);
        }

        return i;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Integer[] a;

        try (Scanner sc = new Scanner(new FileReader("sortString.txt"));) {
            a = sc.findAll("\\d+")
                    .map(matchResult -> Integer.parseInt(matchResult.group())).toArray(Integer[]::new);
        }
        show(a);
        sort(a);
        show(a);

    }
}
