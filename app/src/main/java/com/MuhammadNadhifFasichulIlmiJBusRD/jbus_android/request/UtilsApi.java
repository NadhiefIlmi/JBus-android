/*
 * File: UtilsApi.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class provides utility methods for obtaining an instance of the BaseApiService.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request;

/**
 * The UtilsApi class provides utility methods for obtaining an instance of the BaseApiService.
 */
public class UtilsApi {

    // The base URL for the API.
    public static final String BASE_URL_API = "http://10.0.2.2:5000/";

    /**
     * Retrieves an instance of the BaseApiService using the RetrofitClient with the base URL.
     *
     * @return An instance of the BaseApiService.
     */
    public static BaseApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
