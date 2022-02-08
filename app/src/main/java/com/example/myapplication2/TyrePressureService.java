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

public class TyrePressureService extends Service {

    int current_val = 150;

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
                TyrePress();
                Intent intent = new Intent("TYRE");
                intent.putExtra("tyre", current_val);
                sendBroadcast(intent);
                //postData4(current_val);
            }
        },0,5000);

    }

    private void postData4(int Tyre_pressure) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4/sensors/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI_Tyre retrofitAPI_tyre = retrofit.create(RetrofitAPI_Tyre.class);
        TyrePressureModel model = new TyrePressureModel(Tyre_pressure);
        Call<TyrePressureModel> call = retrofitAPI_tyre.createPost(model);
        call.enqueue(new Callback<TyrePressureModel>() {
            @Override
            public void onResponse(Call<TyrePressureModel> call, Response<TyrePressureModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TyrePressureService.this, "Data is added to API", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(TyrePressureService.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TyrePressureModel> call, Throwable t) {
                Toast.makeText(TyrePressureService.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                Log.d("log1", t.getMessage());

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TyrePress();
        return super.onStartCommand(intent, flags, startId);
    }

    public void TyrePress(){
        if(current_val>130)
            current_val = Randomizer.decrement( current_val,1);
    }
}
