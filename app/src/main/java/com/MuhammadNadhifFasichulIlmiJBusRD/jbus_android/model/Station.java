/*
 * File: Station.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents a bus station entity with station name, city, and address information.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

/**
 * The Station class represents a bus station entity with station name, city, and address information.
 */
public class Station extends Serializable {

    // The name of the bus station.
    public String stationName;

    // The city in which the bus station is located.
    public City city;

    // The address information of the bus station.
    public String address;
}
