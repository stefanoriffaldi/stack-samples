package org.example;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class LabProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

        BigDecimal first = scanner.nextBigDecimal();
        BigDecimal second = scanner.nextBigDecimal();
        BigDecimal third = scanner.nextBigDecimal();

        System.out.println(first);
        System.out.println(second);
        System.out.println(third);
    }
}
