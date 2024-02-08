package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class CustomSort {
    public String customSortString(String order, String s) {
        int[] orderMap = new int[26];
        for (int i = 0; i < order.length(); i++) {
            orderMap[order.charAt(i) - 'a'] = i + 1;
        }

        char[] charArray = s.toCharArray();
        Comparator<Character> comparator = Comparator.comparingInt(a -> orderMap[a - 'a']);

        Character[] complexArray = new Character[charArray.length];
        // Convert the char array to Character array to be able to use custom Comparator
        for (int i = 0; i < charArray.length; i++) {
            complexArray[i] = charArray[i];
        }

        Arrays.sort(complexArray, comparator);

        for (int i = 0; i < complexArray.length; i++) {
            charArray[i] = complexArray[i];
        }

        return new String(charArray);
    }

    public static void main(String[] args) {
        System.out.println(new CustomSort().customSortString("edcba", "abccba"));
    }
}