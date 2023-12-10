/*
 * File: Bus.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents a Bus entity with associated information such as name, facilities,
 *              price, capacity, bus type, departure and arrival stations, and schedules.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The Bus class represents a bus entity with various attributes and associated methods.
 */
public class Bus extends Serializable {
    // The unique identifier for the bus's account.
    public int accountId;

    // The name of the bus.
    public String name;

    // The list of facilities available on the bus.
    public List<Facility> facilities;

    // The pricing information for the bus.
    public Price price;

    // The maximum seating capacity of the bus.
    public int capacity;

    // The type of the bus (e.g., luxury, standard).
    public BusType busType;

    // The departure station for the bus.
    public Station departure;

    // The arrival station for the bus.
    public Station arrival;

    // The list of schedules associated with the bus.
    public List<Schedule> schedules;

    /**
     * Generates a list of sample buses for testing purposes.
     *
     * @param size The number of sample buses to generate.
     * @return A list of sample Bus objects.
     */
    public static List<Bus> sampleBusList(int size) {
        List<Bus> busList = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            Bus bus = new Bus();
            bus.name = "Bus " + i;
            busList.add(bus);
        }

        return busList;
    }

    /**
     * Provides a string representation of the Bus object.
     *
     * @return The name of the bus.
     */
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
