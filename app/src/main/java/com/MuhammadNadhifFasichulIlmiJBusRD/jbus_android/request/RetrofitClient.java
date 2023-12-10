/*
 * File: RetrofitClient.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This class provides a Retrofit client with a base URL and a custom OkHttpClient.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The RetrofitClient class provides a Retrofit client with a base URL and a custom OkHttpClient.
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    /**
     * Retrieves a Retrofit client instance with the specified base URL.
     *
     * @param baseUrl The base URL for the API.
     * @return A Retrofit client instance.
     */
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * Creates a custom OkHttpClient with a network interceptor for adding headers to requests.
     *
     * @return A custom OkHttpClient instance.
     */
    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            .addHeader("Client-Name", "Nadhif")
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }
}
