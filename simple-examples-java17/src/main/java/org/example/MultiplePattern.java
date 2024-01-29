package org.example;

import java.util.regex.Pattern;

public class MultiplePattern {
    public static void main(String[] args) {

        Replace[] replaces = new Replace[]{
                new Replace("foo", "abc"),
                new Replace("bar", "xyz"),
                new Replace("baz", "123")
                // n more
        };

        String input = "foo0bar1baz2";
        String output = input;
        for (Replace replace : replaces) {
            output = replace.pattern().matcher(output).replaceFirst(replace.replacement());
        }
        System.out.println("Input: " + input);
        System.out.println("Output: " + output);
    }

    record Replace(Pattern pattern, String replacement) {
        public Replace(String pattern, String replacement) {
            this(Pattern.compile(pattern), replacement);
        }
    }
}
