package com.example.myapplication2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI2 {
    @POST("/")
    Call<TemperatureModel> createPost(@Body TemperatureModel temperatureModel);//creating a method to post our data.
}
