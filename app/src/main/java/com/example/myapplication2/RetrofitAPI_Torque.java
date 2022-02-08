package com.example.myapplication2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI_Torque {
    @POST("gps/")
    Call<TorqueModel> createPost(@Body TorqueModel torqueModel);
}
