/*
 * File: BaseApiService.java
 * Author: Muhammad Nadhif Fasichul Ilmi
 * Description: This interface defines the API endpoints for interacting with the backend server.
 * Date: [Insert Date]
 */

package com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.request;

import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Account;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.BaseResponse;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Bus;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.BusType;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Facility;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Payment;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Renter;
import com.MuhammadNadhifFasichulIlmiJBusRD.jbus_android.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The BaseApiService interface defines the API endpoints for interacting with the backend server.
 */
public interface BaseApiService {

    @GET("account/{id}")
    Call<Account> getAccountbyId(@Path("id") int id);

    @POST("account/register")
    Call<BaseResponse<Account>> register(
            @Query("name") String name,
            @Query("email") String email,
            @Query("password") String password);

    @POST("account/login")
    Call<BaseResponse<Account>> login(
            @Query("email") String email,
            @Query("password") String password);

    @POST("account/{id}/topUp")
    Call<BaseResponse<Double>> topUp(
            @Path("id") int id,
            @Query("amount") double amount
    );

    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter(
            @Path("id") int id,
            @Query("companyName") String companyName,
            @Query("address") String address,
            @Query("phoneNumber") String phoneNumber);

    @GET("station/getAll")
    Call<List<Station>> getAllStation();

    @POST("bus/create")
    Call<BaseResponse<Bus>> create(
            @Query("accountId") int accountId,
            @Query("name") String name,
            @Query("capacity") int capacity,
            @Query("facilities") List<Facility> facilities,
            @Query("busType") BusType busType,
            @Query("price") int price,
            @Query("stationDepartureId") int stationDepartureId,
            @Query("stationArrivalId") int stationArrivalId);

    @GET("bus/getMyBus")
    Call<List<Bus>> getMyBus(@Query("accountId") int accountId);

    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule(@Query("busId") int busId,
                                        @Query("time") String time);

    @GET("bus/{id}")
    Call<Bus> getBusbyId(@Path("id") int busId);

    @GET("bus/getAllBus")
    Call<List<Bus>> getAllBus();

    @POST("payment/makeBooking")
    Call<BaseResponse<Payment>> makeBooking(
            @Query("buyerId") int buyerId,
            @Query("renterId") int renterId,
            @Query("busId") int busId,
            @Query("busSeats") List<String> busSeats,
            @Query("departureDate") String departureDate
    );

    @GET("payment/buyer/{buyerId}")
    Call<List<Payment>> getBuyerPayments(@Path("buyerId") int buyerId);

    @POST("payment/{id}/pay")
    Call<BaseResponse<Double>> pay(@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<BaseResponse<Payment>> cancel(@Path("id") int id);
}
