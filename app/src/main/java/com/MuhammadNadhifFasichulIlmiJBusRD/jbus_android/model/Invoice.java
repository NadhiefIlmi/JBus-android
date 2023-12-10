/*
 * File: Invoice.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents an Invoice entity with payment status information.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

/**
 * The Invoice class represents an invoice entity with payment status information.
 */
public class Invoice extends Serializable {

    // The payment status associated with the invoice.
    public PaymentStatus status;

    /**
     * Enum representing various payment statuses for an invoice.
     */
    public enum PaymentStatus {
        /**
         * Payment failed.
         */
        FAILED,

        /**
         * Payment is pending.
         */
        WAITING,

        /**
         * Payment successful.
         */
        SUCCESS
    }
}
