package com.yuuy.searching;

/**
 * 利用符号表来进行稀疏矩阵的计算
 *
 * 这样不仅空间利用率上升，时间复杂度也降低了好几个量级
 */
public class SparseVector {
    private SeparateChainingHashST<Integer, Double> st;

    public SparseVector() {
        st = new SeparateChainingHashST<>();
    }

    public int size() {
        return st.size();
    }

    public void put(int i, double x) {
        st.put(i, x);
    }

    public double get(int i) {
        if (!st.contains(i)) return 0;
        else return st.get(i);
    }

    public double dot(SparseVector that) {
        double sum = 0.0;
        for (int i:
                st.keys()) {
            sum += st.get(i)*that.get(i);
        }
        return sum;
    }
}
