package com.GiovanChristoffelSihombingJBusRS.jbus_android.request;

public class UtilsApi {
    public static final String BASE_URL_API = "http://10.0.2.2:5000/";

//    public static final String BASE_URL_API = "http://192.168.137.1:5000/";
    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
