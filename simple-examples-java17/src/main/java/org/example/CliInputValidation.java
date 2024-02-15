package org.example;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Question: <a href="https://stackoverflow.com/q/77955579/11289119">In a loop what's the best practice to get input (cli) with validation, that also resumes like this?</a>
 *
 * @author stefano-riffaldi
 */

public class CliInputValidation {
    public static void main(String[] args) {

    }

    private Product readProduct() {
        Scanner sc = new Scanner(System.in);
        MockValidator vController = new MockValidator(); // mock
        // fields outside loop
        int pID = -1;
        String pName = null;
        int pTypeID = -1;
        int pQuantity = -1;

        int inputProgress = 0;
        while (inputProgress < 4) { // Loop exit condition linked to inputProgress. Exit when read all 4 valid inputs
            if (inputProgress == 0) {
                System.out.println("Enter the Product ID");
                pID = sc.nextInt();
                if (vController.validatePIDFormat(pID)) {
                    inputProgress += 1;
                } else {
                    System.out.println("Invalid input");
                }
            }

            if (inputProgress == 1) {
                System.out.println("Enter the Product Name");
                pName = sc.next();
                if (vController.validatePNameFormat(pName)) {
                    inputProgress += 1;
                } else {
                    System.out.println("Invalid input");
                }
            }

            if (inputProgress == 2) {
                System.out.println("Enter the Product Type");
                String pType = sc.next();
                pTypeID = -1;
                if (pType.contains("commodity")) {
                    pTypeID = 1;
                } else if (pType.contains("equity")) {
                    pTypeID = 2;
                } else if (pType.contains("derivative")) {
                    pTypeID = 3;
                }
                if (pTypeID > 0) {
                    inputProgress += 1;
                } else {
                    System.out.println("Invalid input");
                }
            }


            if (inputProgress == 3) {
                System.out.println("Enter the Quantity");
                pQuantity = sc.nextInt();
                if (vController.validatePQuantity(pQuantity)) {
                    inputProgress += 1;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }

        return new Product(pID, pName, pQuantity, pTypeID);
    }

    private Product readProductSmart() {
        Scanner sc = new Scanner(System.in);
        MockValidator vController = new MockValidator(); // mock

        Map<String, Integer> productTypeMap = Map.of(
                "commodity", 1,
                "equity", 2,
                "derivative", 3
        );

        int id = readUntilIsValid("Product ID", sc::nextInt, Function.identity(), vController::validatePIDFormat);
        String name = readUntilIsValid("Product Name", sc::next, Function.identity(), vController::validatePNameFormat);
        int typeId = readUntilIsValid("Product Type", sc::next, productTypeMap::get, Objects::nonNull);
        int quantity = readUntilIsValid("Quantity", sc::nextInt, Function.identity(), vController::validatePQuantity);

        return new Product(id, name, quantity, typeId);
    }

    private <S, T> T readUntilIsValid(String name, Supplier<S> supplier, Function<S, T> mapper, Predicate<T> validator) {
        T result = null;
        boolean valid = false;
        while (!valid) { // Repeat until result is valid
            System.out.println("Enter the " + name);
            S read = supplier.get(); // Read each parameter of a certain type
            result = mapper.apply(read); // Convert it in another type (sometimes)
            if (!(valid = validator.test(result))) { // Validate it
                System.out.println("Invalid input");
            }
        }
        return result;
    }

    private static class MockValidator {
        public boolean validatePIDFormat(int pID) {
            return false;
        }

        public boolean validatePNameFormat(String pName) {
            return false;
        }

        public boolean validatePQuantity(int pQuantity) {
            return false;
        }
    }

    private record Product(int id, String name, int quantity, int typeId) {
    }
}
