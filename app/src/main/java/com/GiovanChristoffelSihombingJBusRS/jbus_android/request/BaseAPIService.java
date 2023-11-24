package com.GiovanChristoffelSihombingJBusRS.jbus_android.request;

import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.Account;
import com.GiovanChristoffelSihombingJBusRS.jbus_android.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseAPIService {
    @GET("account/{id}")
    Call<Account> getAccountbyId(@Query("id") String id);

    @POST("account/register")
    Call<BaseResponse<Account>> register(@Query("name") String name, @Query("email") String email, @Query("password") String password);

    @POST("account/login")
    Call<BaseResponse<Account>> login(@Query("email") String email, @Query("password") String password);
}
