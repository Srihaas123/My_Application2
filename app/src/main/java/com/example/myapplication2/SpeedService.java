package com.example.myapplication2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpeedService extends Service {
    int current_speed = 40;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Speed();
                Log.d("speedlog", String.valueOf(current_speed));
                Intent intent = new Intent("SPEED");
                intent.putExtra("speed", current_speed);
                sendBroadcast(intent);
                //postData5(current_speed);
            }
        },0,5000);

    }

    private void postData5(int Speed) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4/sensors/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI_Speed retrofitAPI_speed = retrofit.create(RetrofitAPI_Speed.class);
        SpeedModel model = new SpeedModel(Speed);
        Call<SpeedModel> call = retrofitAPI_speed.createPost(model);
        call.enqueue(new Callback<SpeedModel>() {
            @Override
            public void onResponse(Call<SpeedModel> call, Response<SpeedModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SpeedService.this, "Data is added to API", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SpeedService.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SpeedModel> call, Throwable t) {
                Toast.makeText(SpeedService.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                Log.d("log1", t.getMessage());

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Speed();
        return super.onStartCommand(intent, flags, startId);
    }

    public void Speed() {

            int val = Randomizer.random_with_range(0, 1);
        if (current_speed >= 10) {
            if (val == 0) {
                current_speed = Randomizer.decrement(current_speed, 10);
            }
        }
            if(current_speed<=90){
                if (val == 1) {
                    current_speed = Randomizer.increment(current_speed, 10);
                }
            }


    }
}
