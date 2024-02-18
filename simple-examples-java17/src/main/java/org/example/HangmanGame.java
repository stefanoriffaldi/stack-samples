package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * StackOverflow Question:
 * <a href="https://stackoverflow.com/q/78017168/11289119">How can I add a counter for every miss the user makes?</a>
 *
 * @author stefano-riffaldi
 */
public class HangmanGame {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();
        String[] words = {"greetings", "mountain", "school", "horse", "program"};

        String playAgain = "y";

        while (playAgain.equals("y")) {
            int misses = 0;

            String word = words[r.nextInt(5)];
            char[] wordArray = word.toCharArray();
            char[] hiddenWordArray = new char[wordArray.length];
            Arrays.fill(hiddenWordArray, '*');

            while (!Arrays.equals(hiddenWordArray, wordArray)) {
                System.out.printf("(Guess) Enter a letter in word '%s'%n", new String(hiddenWordArray));
                char guess = input.next().charAt(0);
                boolean found = false;
                for (int i = 0; i < word.length(); i++) {
                    if (guess == wordArray[i]) {
                        hiddenWordArray[i] = guess;
                        found = true;
                    }
                }
                if (!found) {
                    misses++;
                }
            }
            System.out.printf("The word is: %s. You missed %d times%n", word, misses);

            System.out.println("Would you like to play again? [y/n]: ");
            playAgain = input.next().toLowerCase();
        }
    }
}