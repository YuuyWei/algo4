package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * 冒泡排序
 * 顾名思义，通过当前指针位置和下一个指针位置的元素比较，
 * 如果当前大于下一个位置，那么交换两个的值。
 * 这样随着指针不断往后移动，最终处在最后一个的必然是所遍历元素里最大的值。
 * 接着缩小范围继续遍历数组。
 *
 * 冒泡排序的时间复杂度是N^2，元素本来的顺序并不影响遍历的次数，只会影响交换的次数。
 */
public class Bubble {
    public static void sort(Comparable[] a){
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - i - 1; j++) {
                if (less(a[j+1], a[j])) exch(a, j, j + 1);
            }
            show(a);
        }
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable @NotNull [] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        Integer[] a;

        try(Scanner sc = new Scanner(new FileReader("sortString.txt"));){
            a = sc.findAll("\\d+")
                    .map(matchResult -> Integer.parseInt(matchResult.group()))
                    .toArray(Integer[]::new);
        }
        show(a);
        sort(a);
        show(a);

    }
}
