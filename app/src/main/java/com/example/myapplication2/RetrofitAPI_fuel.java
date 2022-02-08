package com.example.myapplication2;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI_fuel {
    @POST("gps/")
    Call<FuelModel> createPost(@Body FuelModel fuelModel);//creating a method to post our data.

}
