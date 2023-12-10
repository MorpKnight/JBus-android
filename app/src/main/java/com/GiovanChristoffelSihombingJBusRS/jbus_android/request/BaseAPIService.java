package com.GiovanChristoffelSihombingJBusRS.jbus_android.request;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Bus;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BusType;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Facility;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.MakeBooking;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Payment;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Renter;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseAPIService {
    @GET("account/{id}")
    Call<Account> getAccountbyId(@Query("id") String id);

    @FormUrlEncoded
    @POST("account/register")
//    Call<BaseResponse<Account>> register(@Query("name") String name, @Query("email") String email, @Query("password") String password);
    Call<BaseResponse<Account>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("account/login")
    Call<BaseResponse<Account>> login(@Field("email") String email, @Field("password") String password);
//    Call<BaseResponse<Account>> login(@Query("email") String email, @Query("password") String password);

    @FormUrlEncoded
    @POST("account/{id}/topUp")
//    Call<BaseResponse<Double>> topUp(@Path("id") int id, @Query("amount") double amount);
    Call<BaseResponse<Double>> topUp(@Path("id") int id, @Field("balance") double amount);

    @FormUrlEncoded
    @POST("account/{id}/registerRenter")
    Call<BaseResponse<Renter>> registerRenter(@Path("id") int id, @Field("companyName") String companyName,
                                              @Field("address") String companyAddress, @Field("phoneNumber") String companyPhone);

    @FormUrlEncoded
    @GET("account/getAccountDetails")
    Call<BaseResponse<Account>> getAccountDetails(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("bus/create")
    Call<BaseResponse<Bus>> create(@Field("accountId") int accountId, @Field("name") String name,
                                   @Field("capacity") int capacity, @Field("facility") List<Facility> facility,
                                   @Field("busType") BusType busType, @Field("price") int price,
                                   @Field("stationDepartureId") int stationDepartureId, @Field("stationArrivalId") int stationArrivalId);


    @FormUrlEncoded
    @POST("bus/delete")
    Call<BaseResponse<Bus>> delete(@Field("busId") int busId, @Field("accountId") int accountId);

    @FormUrlEncoded
    @POST("bus/deleteSchedule")
    Call<BaseResponse<Bus>> deleteSchedule(@Field("busId") int busId, @Field("time") String time);

    @FormUrlEncoded
    @POST("bus/addSchedule")
    Call<BaseResponse<Bus>> addSchedule(@Field("busId") int busId, @Field("time") String time);

    @GET("bus/getMyBus")
    Call<List<Bus>> getMyBus(@Query("accountId") int accountId);

    @GET("bus/getAllBus")
    Call<List<Bus>> getAllBus();

    @GET("payment/getAllPayment")
    Call<List<Payment>> getAllPayment();

    @FormUrlEncoded
    @POST("payment/makeBooking")
    Call<BaseResponse<MakeBooking>> makeBooking(@Field("buyerId") int buyerId, @Field("renterId") int renterId,
                                                @Field("busId") int busId, @Field("busSeats") List<String> busSeats,
                                                @Field("departureDate") String departureDate);

    @GET("payment/getMyPayment/{id}")
    Call<List<Payment>> getMyPayment(@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<BaseResponse<Payment>> cancelPayment(@Path("id") int id);

    @POST("payment/{id}/accept")
    Call<BaseResponse<Payment>> acceptPayment(@Path("id") int id);

    @FormUrlEncoded
    @POST("station/create")
    Call<BaseResponse<Station>> createStation(@Field("stationName") String stationName, @Field("city") String city, @Field("address") String address);

    @GET("station/getAll")
    Call<List<Station>> getAllStation();

}
