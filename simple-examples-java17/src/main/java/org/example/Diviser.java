package org.example;

import java.util.Scanner;

/**
 * StackOverflow Question:
 * <a href="https://stackoverflow.com/q/78016547/11289119">Handling Java unchecked exceptions</a>
 *
 * @author stefano-riffaldi
 */
public class Diviser {
    static int[] T = {17, 12, 15, 38, 29, 157, 89, -22, 0, 5};

    public static void main(String[] args) {
        divideFirstWay();
        divideSecondWay();
    }

    private static void divideFirstWay() {
        int indice = -1, diviseur = 0; // set variables with wrong value, to check
        boolean termine = false;

        Scanner sc = new Scanner(System.in);

        while (!termine) {
            if (indice < 0 || indice >= T.length) {
                System.out.println("L’indice de l’entier à diviser: ");
                indice = sc.nextInt();
                if (indice < 0 || indice >= T.length) {
                    // System.out.println(m.getMessage());
                    System.out.println("Entrez un indice entre 0 et " + (T.length - 1) + "\n");
                    sc.nextLine();
                }
            } else if (diviseur == 0) {
                System.out.println("Le diviseur: ");
                diviseur = sc.nextInt();
                if (diviseur == 0) {
                    // System.out.println(s.getMessage());
                    System.out.println("Entrez un diviseur différent de zéro\n");
                    sc.nextLine();
                }
            } else {
                System.out.println("\nLe résultat de la division est: " + div(indice, diviseur));
                termine = true;
            }
        }
    }

    private static void divideSecondWay() {
        int indice = -1, diviseur = 0; // set variables with wrong value, to check

        Scanner sc = new Scanner(System.in);

        while (indice < 0 || indice >= T.length) {
            System.out.println("L’indice de l’entier à diviser: ");
            indice = sc.nextInt();
            if (indice < 0 || indice >= T.length) {
                // System.out.println(m.getMessage());
                System.out.println("Entrez un indice entre 0 et " + (T.length - 1) + "\n");
                sc.nextLine();
            }
        }
        while (diviseur == 0) {
            System.out.println("Le diviseur: ");
            diviseur = sc.nextInt();
            if (diviseur == 0) {
                // System.out.println(s.getMessage());
                System.out.println("Entrez un diviseur différent de zéro\n");
                sc.nextLine();
            }
        }
        System.out.println("\nLe résultat de la division est: " + div(indice, diviseur));
    }

    static int div(int indice, int diviseur) {
        return T[indice] / diviseur;
    }
}