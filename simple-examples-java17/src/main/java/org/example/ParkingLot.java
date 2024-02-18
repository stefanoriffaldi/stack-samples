package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ParkingLot {

    public static void main(String[] args) {
        Map<Integer, Vehicle> map = Map.of(1, new Car(), 2, new Truck(), 3, new Bus());
        System.out.println();
        Vehicle[][] parking = new Vehicle[10][10];
        Random random = new Random();
        for (int i = 0; i < parking.length; i++) {
            for (int j = 0; j < parking[i].length; j++) {
                parking[i][j] = map.get(random.nextInt(0, map.size() + 1));
                char symbol = parking[i][j] == null ? '-' : parking[i][j].getClass().getSimpleName().charAt(0);
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Getter @Setter
    static class Vehicle {
        private final String vehicleNumber = UUID.randomUUID().toString();
    }

    static class Car extends Vehicle {
    }

    static class Truck extends Vehicle {
    }

    static class Bus extends Vehicle {
    }
}
