package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * StackOverflow question: <a href="https://stackoverflow.com/q/77983535/11289119">Items are being added and printed correctly, but ArrayList size shows up as 0, why is that?</a>
 */
public class ListBySymbol {
    private final List<Stock> stockData;

    public ListBySymbol(List<Stock> stockData) {
        this.stockData = stockData;
    }

    //    private List<AbstractStock> listBySymbol = new ArrayList<>();

    public static void main(String[] args) {
        List<Stock> stocks = Arrays.asList(
                new Stock("ABC"),
                new Stock("CDE"),
                new Stock("DEF")
        );
        ListBySymbol repository = new ListBySymbol(stocks);

        System.out.println("listBySymbol 'A': " + repository.listBySymbol("A"));
        System.out.println("listBySymbol 'C': " + repository.listBySymbolWithStream("C"));
        System.out.println("listBySymbol 'D': " + repository.listBySymbolWithStream("D"));
    }

    public List<Stock> listBySymbol(String symbol) {
        List<Stock> listBySymbol = new ArrayList<>();
        int i = 0;
        while (i < stockData.size()) {
            if (stockData.get(i).toString().contains(symbol)) {
                listBySymbol.add(stockData.get(i));
            }
            i++;
        }
        return Collections.unmodifiableList(listBySymbol);
    }

    public List<Stock> listBySymbolWithStream(String symbol) {
        return stockData.stream() // Use stream
                .filter(s -> s.toString().contains(symbol)) // filter
                .toList(); // collect result of filter to list
    }

    public record Stock(String string) {
        @Override
        public String toString() {
            return string;
        }
    }
}
