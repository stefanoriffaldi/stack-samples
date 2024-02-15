package org.example;

public class Sorting {

    public void sort(String[][] Locations) {
        for (int a = 0; a < Locations.length; a++)
            for (int b = 0; b < Locations[a].length - 1; b++) {
                for (int c = 0; c < Locations[a].length - b - 1; c++) {
                    if (Locations[a][b].compareTo(Locations[a][b + 1]) > 0) {
                        String temp = Locations[a][b];
                        Locations[a][b] = Locations[a][b + 1];
                        Locations[a][b + 1] = temp;
                    }
                }
            }
    }

        public void sortMy(String[][] locations) {
            for (int a = 0; a < locations.length; a++) {
                for (int b = 0; b < locations[a].length - 1; b++) {
                    for (int c = b + 1; c < locations[a].length; c++) {
                        if (locations[a][b].compareTo(locations[a][c]) > 0) {
                            String temp = locations[a][b];
                            locations[a][b] = locations[a][c];
                            locations[a][c] = temp;
                        }
                    }
                }
            }
        }
}