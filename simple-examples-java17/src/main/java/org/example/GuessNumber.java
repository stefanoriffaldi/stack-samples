package org.example;

import java.util.Random;
import java.util.Scanner;

/**
 * StackOverflow Question:
 * <a href="https://stackoverflow.com/q/78016824/11289119">Why is my tries in my loop never decreasing in my program?</a>
 *
 * @author stefano-riffaldi
 */
public class GuessNumber {
    public static void main(String[] args) {
        Scanner numberCheck = new Scanner(System.in);
        System.out.println("Try to guess the number 0-100\n");
        int numberToGuess = new Random().nextInt(101); // pick at startup, just once
        for (int tries = 10; tries > 0; tries--) {
            int answer = numberCheck.nextInt(); // read user input
            if (answer > numberToGuess) { // check if answer is too high
                System.out.println("You are incorrect, answer is too high");
            } else if (answer < numberToGuess) { // check if answer is too low
                System.out.println("You are incorrect, answer is too low");
            } else { // check if answer is correct
                System.out.println("You are Correct !");
                tries = 0; // exit loop, avoid using break if not necessary
            }
        }
    }
}