package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * 插入排序
 *
 * 将数组元素分为两个部分，前一部分元素已经排好，后一部分元素还处于乱序。
 * 此时选取临界点元素，将其与前一个元素比较，如果小于前一个元素，则交换两个元素。
 * 否则就选取临界点的下一个点作为新的临界点
 *
 * 插入排序相比于选择排序带来的进步是数组本身的特征会影响排序的时间。
 * 比如，给定一个已经排序完成的数组，插入排序的时间复杂度会是N。
 */
public class Insertion {
    public static void sort(Comparable[] a){
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j - 1);
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
