package com.yuuy.string;

import com.yuuy.searching.LinearProbingHashST;
import com.yuuy.searching.ST;

public class Alphabet {
    private final char[] chars;
    private ST<Character, Integer> st;

    public Alphabet(String s){
        st = new LinearProbingHashST();
        chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            st.put(chars[i], i);
        }
    }

    public char toChar(int index){
        return chars[index];
    }

    public int toIndex(char c){
        return st.get(c);
    }

    public boolean contains(char c) {
        return st.contains(c);
    }

    public int R() {
        return chars.length;
    }

    public int lgR() {
        return (int) Math.ceil(Math.log(chars.length));
    }

    public int[] toIndices(String s){
        int[] indices = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            indices[i] = toIndex(s.charAt(i));
        }
        return indices;
    }

    public String toChars(int[] indices) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indices.length; i++) {
            builder.append(toChar(indices[i]));
        }

        return builder.toString();
    }

    public static void main(String[] args) {

        Alphabet alphabet = new Alphabet("aeiou");

        for (int i: alphabet.toIndices("aeiou")){
            System.out.printf("%d ", i);
        }
        System.out.println();

        System.out.println(alphabet.toChars(new int[]{0, 1, 2, 3, 4, 3, 2, 1, 0}));

    }
}
