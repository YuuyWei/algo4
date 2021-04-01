package com.yuuy.basic;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Iterable也是是泛型接口，不要忘了加类型参数，否则返回的类型都是Object类型。
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

    private T[] a;
    private int n;
    private int cap = 5;

    /**
     * 创建一个泛型数组在java里是不被允许的，因为java是通过泛型擦除来实现泛型功能的。
     * 正确的创建方法是创建Object数组在通过类型转换。
     */
    public Bag() {
        n = 0;
        a = (T[]) new Object[cap];
    }

    public void add(T item) {
        if (n >= cap) resize(cap * 2 + 1);
        a[n++] = item;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int max) {
        cap = max;

        T[] b = (T[]) new Object[cap];

        /**
         * 数组克隆可以通过系统自带的System.arraycopy()方法来进行
         */
        if (n >= 0) System.arraycopy(a, 0, b, 0, n);
        a = b;
    }

    @Override
    public Iterator<T> iterator() {
        return new BagIterator();
    }

    /**
     * 实现迭代操作的关键就是实现一个满足Iterator<T>接口的类
     */
    private class BagIterator implements Iterator<T>{

        private int i = 0;
        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public T next() {
            return a[i++];
        }
    }
    public static void main(String[] args) {
        Bag<Double> numbers = new Bag<>();

        /**
         * java se5增加的Scanner类大大减少了扫描输入的负担。Scanner除了支持简单的解析数字分解单词外，还能使用正则表达式解析字符串。
         * 之前通常是使用BufferReader转化成字符串，然后使用Integer Double所带的parse类方法进行解析操作。
         */
        Scanner stdin = new Scanner(System.in);

        while (stdin.hasNextDouble()) {
            Double number = stdin.nextDouble();
            numbers.add(number);
        }

        int size = numbers.size();

        double sum = 0;
        for (Double n: numbers){
            sum += n;
        }
        double mean = sum / size;

        System.out.println("Mean: " + mean);
    }
}
