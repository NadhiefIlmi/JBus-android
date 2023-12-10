/*
 * File: BaseResponse.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class represents the base response structure for API requests.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model;

/**
 * A generic class for handling API responses.
 *
 * @param <T> The type of the payload data.
 */
public class BaseResponse<T> {
    // Indicates whether the API request was successful.
    public boolean success;

    // A message providing additional information about the API response.
    public String message;

    // The payload data of the API response.
    public T payload;
}
