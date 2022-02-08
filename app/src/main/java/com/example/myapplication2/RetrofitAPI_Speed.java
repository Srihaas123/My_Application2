package com.example.myapplication2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI_Speed {
    @POST("gps/")
    Call<SpeedModel> createPost(@Body SpeedModel speedModel);//creating a method to post our data.
}
