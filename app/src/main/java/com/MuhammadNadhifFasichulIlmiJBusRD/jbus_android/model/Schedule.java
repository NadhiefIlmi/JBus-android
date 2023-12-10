/*
 * File: Schedule.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents a schedule entity with departure time and seat availability information.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * The Schedule class represents a schedule entity with departure time and seat availability information.
 */
public class Schedule {

    // The timestamp representing the departure schedule.
    public Timestamp departureSchedule;

    // A map containing seat availability information, with seat numbers as keys and availability status as values.
    public Map<String, Boolean> seatAvailability;

    /**
     * Provides a string representation of the Schedule object.
     *
     * @return A formatted string containing departure time and seat availability statistics.
     */
    @NonNull
    @Override
    public String toString() {
        int countOccupied = 0;
        for (boolean val : seatAvailability.values()) {
            if (!val) countOccupied++;
        }
        int totalSeat = seatAvailability.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        return dateFormat.format(this.departureSchedule.getTime()) + "\t\t" + "[ " + countOccupied + "/" + totalSeat + " ]";
    }
}
