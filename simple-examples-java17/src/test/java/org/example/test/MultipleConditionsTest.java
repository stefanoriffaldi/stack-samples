package org.example.test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StackOverflow Question:
 * <a href="https://stackoverflow.com/q/78028068/11289119">AssertJ Java: multiple conditions assertion</a>*
 *
 * @author stefano-riffaldi
 */
public class MultipleConditionsTest {

    @Test
    public void containsOrMatchesRegexTest() {
        // Given
        // When
        List<String> fruits = Arrays.asList("banana", "apple", "orange");
        // Then
        assertThat(fruits)
                .as("At least one element should contain '%s' or match the regex '%s'", "raspberry", "a.+?")
                .anyMatch(contains("raspberry").or(matchesRegex("a.+?")))
                .as("At least one element should contain '%s' or match the regex '%s'", "banana", "p.+?")
                .anyMatch(contains("banana").or(matchesRegex("p.+?")))
                .as("No element should contain '%s' or match the regex '%s'", "blueberry", ".+?c.+?")
                .noneMatch(contains("blueberry").or(matchesRegex(".+?c.+?")))
        ;
    }

    private static Predicate<String> contains(String someString) {
        return string -> string.equalsIgnoreCase(someString);
    }

    private static Predicate<String> matchesRegex(String regex) {
        return string -> Pattern.compile(regex).matcher(string).matches();

    }
}