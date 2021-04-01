package com.yuuy.basic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parentheses {
    public static boolean isMatch() {
        Stack<Character> stack = new LinkListStack<>();

        String filename = "paren.txt";
        String s = "";
        try (Scanner sc = new Scanner(new File(filename))) {
            if (sc.hasNextLine()) {
                s = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0;i<s.length();i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                } else if(stack.peek() != '('){
                    return false;
                } else {
                    stack.pop();
                }

            } else if (c == ']') {
                if (stack.isEmpty()) {
                    return false;
                } else if(stack.peek() != '['){
                    return false;
                } else {
                    stack.pop();
                }
            } else if (c == '}') {
                if (stack.isEmpty()) {
                    return false;
                } else if(stack.peek() != '{'){
                    return false;
                } else {
                    stack.pop();
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isMatch());
    }
}
