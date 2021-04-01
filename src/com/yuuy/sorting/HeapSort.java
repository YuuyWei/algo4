package com.yuuy.sorting;

import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

/**
 * 堆排序
 * <p>
 * 堆排序是唯一一个同时在时间和空间复杂度上达到最优的排序算法，
 * 即使在最坏情况下，堆排序时间复杂度也能达到NlogN，空间复杂度达到常数级别。
 * 同时由于堆排序的代码量很小，因此经常被用在实现嵌入式设备上的排序。
 * 同时注意堆排序的也属于不稳定的排序算法。
 * <p>
 * 堆排序算法主要分为以下两步：
 * 1.构建一个堆有序的数组，
 * 2.将数组第一个元素和最后一个元素互换，
 * 3.然后执行下沉操作使数组重新堆有序，注意此过程要排除最后一个元素。
 * 4.迭代进行以上操作N-1次，数组将被完美排序。
 *
 * 通过下沉操作实现的堆排序算法仍有改进的空间，
 * 在上面步骤3中先下沉后上浮，可以适当减少比较次数，
 * 这在比较操作十分耗时的情况下能有效减少运行时间。
 */
public class HeapSort {
    public static void sort(Comparable[] a) {
        int N = a.length;
        // 初始化数组使其堆有序
        for (int i = N / 2; i >= 1; i--) {
            sink(a, i, N);
        }
        show(a);
        for (int i = 0; i < N - 1; i++) {
            exch(a, 1, N - i);
            sink(a, 1, N - i - 1);
        }
    }

    private static void sink(Comparable[] a, int i, int n) {
        while (i <= n / 2) {
            int j = 2 * i;
            if (j < n && less(a[j - 1], a[j])) j++;
            if (less(a[j - 1], a[i - 1])) break; // 如果j指向的元素小于i，那么说明到位了。
            exch(a, i, j);
            i = j;
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = t;
    }

    private static void show(Comparable @NotNull [] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        String[] a;

        /**
         * 使用流式处理方式读取数据，并采用正则表达式来识别并分组读取的字符串
         * 遇到一个问题，中括号和不加中括号的正则表达式返回的东西很不一样，需要研究研究
         * \w+ -> 一个个单词
         * [\w+] -> 一个个字母
         */
        try (Scanner sc = new Scanner(new FileReader("sortString.txt"));) {
            a = sc.findAll("\\w+")
                    .map(MatchResult::group)
                    .toArray(String[]::new);
        }
        show(a);
        sort(a);
        show(a);

    }
}
