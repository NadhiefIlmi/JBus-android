/*
 * File: Renter.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents a Renter entity with contact and company information.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

/**
 * The Renter class represents a renter entity with contact and company information.
 */
public class Renter extends Serializable {

    // The phone number associated with the renter.
    public String phoneNumber;

    // The address information of the renter.
    public String address;

    // The name of the company associated with the renter.
    public String companyName;
}
