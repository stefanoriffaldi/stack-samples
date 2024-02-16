package org.example;

import java.util.Scanner;

/**
 * StackOverflow question:
 * <a href="https://stackoverflow.com/q/77930300/11289119">How do I get user input with spaces in IntelliJ</a>
 */
public class SpaceUserInput {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter words: ");
            System.out.print("Input: '" + scanner.nextLine() + "'");
        }
    }
}
