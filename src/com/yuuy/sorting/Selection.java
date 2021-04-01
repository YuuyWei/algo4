package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * 选择排序
 *
 * 从未排序的数组部分选择最小的元素放在已按从小到大顺序排好的数组后面。
 * 他有两个很鲜明的特点：
 * 1. 运行时间和输入无关。每次扫描并不会为下次扫描带来什么额外的帮助。
 * 2. 数据移动是最少的。每次交换都只会改变两个数组元素的值。
 */
public class Selection {
    public static void sort(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i+1; j < a.length; j++) {
                if (less(a[j],a[min])) min = j;
            }
            exch(a, i, min);
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

        /**
         * 蛋疼的mapToInt，使用它返回一个int而不是Integer导致编译器报错。
         * 原因是java虽然支持基本类型的自动装箱，但是基本类型的数组却无法从中受益，
         * 所以int[]和Integer[]会被视为不同的类型。
         *
         * 并且由于正则表达式返回的结果解析出来是字符串类型，所以还得使用parseInt方法转换一下。
         */
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
