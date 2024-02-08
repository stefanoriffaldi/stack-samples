package org.example.test;

import org.example.Sorting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {

    @Test
    public void test_sort_empty_array() {
        // Given
        String[][] locations = {};

        // When
        Sorting sorting = new Sorting();
        sorting.sort(locations);

        // Then
        assertEquals(0, locations.length);
    }

    @Test
    public void test_sort_ascending_order() {
        // Given
        String[][] locations = {{"Paris", "London", "New York"}, {"Tokyo", "Sydney", "Beijing"}};
        String[][] expected = {{"London", "New York", "Paris"}, {"Beijing", "Sydney", "Tokyo"}};

        // When
        Sorting sorting = new Sorting();
        sorting.sort(locations);

        // Then
        assertArrayEquals(expected, locations);
    }

}