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

public class TorqueService extends Service {
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
                Intent intent = new Intent("TORQUE");
                intent.putExtra("torque", Torque());
                sendBroadcast(intent);
                //postData3(Torque());
            }
        },0,5000);

    }

    private void postData3(int Torque) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.4/sensors/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI_Torque retrofitAPI_torque = retrofit.create(RetrofitAPI_Torque.class);
        TorqueModel model = new TorqueModel(Torque);
        Call<TorqueModel> call = retrofitAPI_torque.createPost(model);
        call.enqueue(new Callback<TorqueModel>() {
            @Override
            public void onResponse(Call<TorqueModel> call, Response<TorqueModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(TorqueService.this, "Data is added to API", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(TorqueService.this, response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TorqueModel> call, Throwable t) {
                Toast.makeText(TorqueService.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                Log.d("log1", t.getMessage());

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Torque();
        return super.onStartCommand(intent, flags, startId);
    }

    public int Torque(){
       int Tor = Randomizer.random_with_range(0,140);
       return Tor;
    }
}
