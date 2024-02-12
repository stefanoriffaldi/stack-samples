package org.example;

/**
 * StackOverflow question:
 * <a href="https://stackoverflow.com/q/77983762/11289119">Print an exact value in the Fibonacci sequence</a>
 */
public class Fibonacci {
    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fib(10));
        System.out.println(fibonacci.fib(9));
        System.out.println(fibonacci.fib(7));
    }

    public int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        int a = 0, b = 1;
        for (int i = 0; i < n - 2; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return a + b;
    }
}
