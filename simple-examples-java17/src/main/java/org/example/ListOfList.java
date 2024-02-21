package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StackOverflow Question: <a href="https://stackoverflow.com/q/78009687/11289119">How to split array of objects into subarray based on multiple property/field values?</a>
 *
 * @author stefano-riffaldi
 */
public class ListOfList {
    public static void main(String[] args) {
        List<Element> elements = Arrays.asList(
                new Element("District 1", "School 1", "Grade 1", "Math", "Student 1"),
                new Element("District 1", "School 1", "Grade 1", "Math", "Student 2"),
                new Element("District 1", "School 1", "Grade 1", "Math", "Student 3"),
                new Element("District 1", "School 1", "Grade 1", "Math", "Student 4")
                // and so on
        );

        List<List<Element>> listOfList = subList(elements);

        System.out.println("Outer List size: " + listOfList.size());
        listOfList.forEach(list -> System.out.println("Inner List size: " + list.size()));
    }

    private static boolean changeGroup(Element latest, Element current) {
        return !new ElementKey(latest).equals(new ElementKey(current));
    }

    private static List<List<Element>> realSubList(List<Element> elements) {
        if (elements == null || elements.isEmpty()) {
            return List.of();
        }
        List<List<Element>> listOfList = new ArrayList<>();
        int fromIndex = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (changeGroup(elements.get(fromIndex), elements.get(i))) {
                listOfList.add(elements.subList(fromIndex, i));
                fromIndex = i;
            }
        }
        listOfList.add(elements.subList(fromIndex, elements.size()));
        return listOfList;
    }

    /**
     * Assuming list is sorted by key
     *
     * @param elements list of elements to group
     * @return lists of elements grouped by key
     */
    private static List<List<Element>> subList(List<Element> elements) {
        if (elements == null || elements.isEmpty()) {
            return List.of();
        }
        List<List<Element>> listOfList = new ArrayList<>();
        listOfList.add(new ArrayList<>());
        Element latest = elements.get(0);
        for (Element current : elements) {
            if (changeGroup(latest, current)) {
                listOfList.add(new ArrayList<>());
                latest = current;
            }
            listOfList.get(listOfList.size() - 1).add(current);
        }
        return listOfList;
    }

    private static List<List<Element>> subListViaGroupingBy(List<Element> elements) {
        return elements.stream()
                .collect(Collectors.groupingBy(ElementKey::new, Collectors.toList()))
                .values().stream()
                .toList();
    }

    record ElementKey(String district, String school, String grade) {
        public ElementKey(Element element) {
            this(element.district(), element.school(), element.grade());
        }
    }

    record Element(String district, String school, String grade, String subject, String student) {
    }
}
