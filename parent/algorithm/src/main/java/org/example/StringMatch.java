package org.example;

public class StringMatch {
    public static int[] next(char[] chars) {
        int[] next = new int[chars.length];
        int i = 1;
        int j = 0;
        while (i < chars.length - 1) {
            if (chars[i] == chars[j]) {
                next[++i] = ++j;
            } else {
                if (j == 0) {
                    i++;
                }
                j = next[j];
            }
        }
        for (int k = 1; k < next.length; k++) {
            int x = next[k];
            if (chars[k] == chars[x]) {
                next[k] = next[x];
            }
        }
        return next;
    }

    public static int kmp(String s, String t) {
        char[] source = s.toCharArray();
        char[] target = t.toCharArray();
        int i = 0;
        int j = 0;
        int[] next = next(target);
        while (i < source.length && j < target.length) {
            if (source[i] == target[j]) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                }
                j = next[j];
            }
        }
        return j >= target.length ? i - j : -1;
    }
}
