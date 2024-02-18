package org.example;


import java.util.Scanner;

/**
 * StackOverflow Question:
 * <a href="https://stackoverflow.com/q/78009411/11289119">Why is my program to find prime numbers within a range not working the way it's supposed to</a>
 *
 * @author stefano-riffaldi
 */
public class FindPrimes {
    public static void main(String[] args) {
        // program to check prime numbers b/w two numbers
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter first number: ");
        int start = sc.nextInt();
        System.out.println("Enter second number: ");
        int end = sc.nextInt();

        System.out.println("The prime numbers are: ");

        findPrimes(start, end);
    }

    /**
     * Finds prime numbers within a given range and print them to the console
     *
     * @param start the start of the range
     * @param end   the end of the range
     */
    private static void findPrimes(int start, int end) {
        for (int number = start % 2 == 0 ? start + 1 : start + 2; number <= end; number += 2) {
            boolean hasDivisor = false;
            for (int divisor = 3; divisor < number && !hasDivisor; divisor += 2) {
                hasDivisor = number % divisor == 0;
            }
            if (!hasDivisor) {
                System.out.println(number);
            }
        }
    }
}