package com.yuuy.sorting;

import com.yuuy.basic.QuickFindUF;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class Example {
    public static void sort(Comparable[] a){}

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
        String[] a;

        /**
         * 使用流式处理方式读取数据，并采用正则表达式来识别并分组读取的字符串
         * 遇到一个问题，中括号和不加中括号的正则表达式返回的东西很不一样，需要研究研究
         * \w+ -> 一个个单词
         * [\w+] -> 一个个字母
         */
        try(Scanner sc = new Scanner(new FileReader("sortString.txt"));){
            a = sc.findAll("\\w+")
                    .map(MatchResult::group)
                    .toArray(String[]::new);
        }
        show(a);
        sort(a);
        show(a);

    }
}
