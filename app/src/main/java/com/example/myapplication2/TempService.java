package com.example.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TempService extends Service {
    Random random;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        random = new Random();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //what you want to do

                Intent intent = new Intent("TEMP");
                intent.putExtra("Temp_reading", Temp());
                sendBroadcast(intent);
                //postData1(Temp());

            }
        }, 0, 5000);//wait 0 ms before doing the action and do it evry 1000ms (1second)

            }
    private void postData1(double Temperature) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enap8kztggghjn2.m.pipedream.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI2 retrofitAPI2 = retrofit.create(RetrofitAPI2.class);
        TemperatureModel model = new TemperatureModel(Temperature);
        Call<TemperatureModel> call = retrofitAPI2.createPost(model);
        call.enqueue(new Callback<TemperatureModel>() {
            @Override
            public void onResponse(Call<TemperatureModel> call, Response<TemperatureModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TempService.this, "Data is added to API", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(TempService.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TemperatureModel> call, Throwable t) {
                Toast.makeText(TempService.this, "Error connecting to server", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Temp();
        return super.onStartCommand(intent, flags, startId);
    }



    private double Temp(){
        Random random = new Random();
        double val = random.nextInt(31-20)+20;
        return val;

    }
}
