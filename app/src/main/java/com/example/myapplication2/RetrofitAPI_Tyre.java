package com.example.myapplication2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI_Tyre {
    @POST("gps/")
    Call<TyrePressureModel> createPost(@Body TyrePressureModel tyrePressureModel);//creating a method to post our data.
}
