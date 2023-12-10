/*
 * File: Payment.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents a Payment entity, extending the Invoice class,
 *              with additional information such as bus ID, departure date, and bus seat information.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The Payment class represents a payment entity, extending the Invoice class,
 * with additional information such as bus ID, departure date, and bus seat information.
 */
public class Payment extends Invoice {

    // The unique identifier for the bus associated with the payment.
    public int busId;

    // The timestamp representing the departure date.
    public Timestamp departureDate;

    // The list of bus seat numbers associated with the payment.
    public List<String> busSeat;

    /**
     * Provides a string representation of the Payment object.
     *
     * @return A formatted string containing departure date, seat information, and payment status.
     */
    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss");
        return dateFormat.format(this.departureDate.getTime()) + "\t\t" + "Seat: " + busSeat + "Status: " + status;
    }
}
