package com.example.myapplication2;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/")
    Call<DataModel> createPost(@Body DataModel dataModel);//creating a method to post our data.

}
