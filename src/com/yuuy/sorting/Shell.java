package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * 希尔排序
 * 插入排序的改版，主要思想就是让数组中任意间隔为h的元素都是有序的。
 * 一个2有序数组就像这样：4 1 6 3 7 5 8 6
 * 换句话说，一个h有序数组就是h个互相独立的有序数组相互编织在一起形成的数组。
 *
 * 实现希尔排序的方法就是将插入排序中移动元素的间隔由1改为h。
 * h取自一个递增数列，数列的取法会影响希尔排序的效率。
 *
 * 希尔排序高效的原因是因为他权衡了子数组的规模和有序性。
 * 排序之初各子数组都很短，排序之后子数组部分有序，这两种情况都很适合插入排序。
 *
 * 因此希尔排序的时间复杂度突破了N^2，属于次平方级别。
 * 本例最坏情况下的时间复杂度与N^1.5成正比。
 * 并且希尔排序的时间复杂度仍有改进突破的能力，不过依然是N^1.4、N^1.3之类的东西。
 *
 * 有经验的程序员有时会选择希尔排序，尽管它的时间复杂度要高于NlogN类的算法。
 * 但对于中等大小的数组而言，希尔排序的运行时间是可接受的。
 * 它的代码量很小，而且它不需要额外的空间，这让它成为这种情况下的可行之选。
 */
public class Shell {
    public static void sort(Comparable[] a){

        int N = a.length;
        int h = 1;
        final int alpha = 3;
        while (h < N/alpha) h = alpha * h + 1;
        while(h >= 1){
            System.out.println(h + "-sort:");
            for (int i = h; i < a.length; i++) {
                for (int j = i; j > 0 && less(a[j], a[j-h]); j--) {
                    exch(a, j, j - h);
                }
                show(a);
            }
            h /=alpha;
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
