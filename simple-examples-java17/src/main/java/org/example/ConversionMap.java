package org.example;

import java.util.Map;
import java.util.stream.Collectors;

public class ConversionMap {
    public static void main(String[] args) {
        Map<String, String> forConversion = Map.of(
            "$", "s",
            "§", "ss",
            "Đ", "d"
            // add more mappings as needed
        );

        String input = "$ab§d";

        String output = input.chars()
            .mapToObj(c -> String.valueOf((char) c))
            .map(s -> forConversion.getOrDefault(s, s))
            .collect(Collectors.joining());

        System.out.println(output);  // prints "sabssd"
    }
}
