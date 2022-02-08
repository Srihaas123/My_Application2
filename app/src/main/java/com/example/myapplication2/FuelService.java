package com.example.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.protobuf.Enum;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FuelService extends Service {
    int current_fuel = 90;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fuel();
                Log.d("logc",String.valueOf(current_fuel));
                Intent intent = new Intent("FUEL");
                intent.putExtra("fuel", current_fuel);
                sendBroadcast(intent);
                //postData2(current_fuel);
            }
        },0,5000);



    }

    private void postData2(int fuel) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4/sensors/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI_fuel retrofitAPI_fuel = retrofit.create(RetrofitAPI_fuel.class);
        FuelModel model = new FuelModel(fuel);
        Call<FuelModel> call = retrofitAPI_fuel.createPost(model);
        call.enqueue(new Callback<FuelModel>() {
            @Override
            public void onResponse(Call<FuelModel> call, Response<FuelModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FuelService.this, "Data is added to API", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FuelService.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FuelModel> call, Throwable t) {
                Toast.makeText(FuelService.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                Log.d("log1", t.getMessage());

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fuel();
        return super.onStartCommand(intent, flags, startId);
    }

    private void fuel(){
        if(current_fuel>=10)
            current_fuel = Randomizer.decrement(current_fuel, 5);
    }

}
