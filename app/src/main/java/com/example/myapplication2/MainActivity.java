package com.example.myapplication2;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import com.google.android.material.timepicker.TimeFormat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    TextView tv_lat, tv_lon, tv_temp, tv_timestamp1, tv_timestamp2;
    Switch sw1, sw2;
    LocationBroadcastReceiver receiver;
    TempBroadcastReceiver receiver2;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_lat = findViewById(R.id.tv_labellat);
        tv_lon = findViewById(R.id.tv_labellon);
        tv_temp = findViewById(R.id.tv_labeltemp);
        sw1 = findViewById(R.id.sw_GPS);
        sw2 = findViewById(R.id.sw_TEMP);
        tv_timestamp1 = findViewById(R.id.timestamp1);
        tv_timestamp2 = findViewById(R.id.timestamp2);

        receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver, filter);
        receiver2 = new TempBroadcastReceiver();
        IntentFilter filterTemp = new IntentFilter("TEMP");
        registerReceiver(receiver2, filterTemp);

        if (Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //Req Location Permission
                sw1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(sw1.isChecked()){
                            startLocService();
                        }
                        else{
                            tv_lon.setText("Lon:");
                            tv_lat.setText("Lat:");
                        }

                    }
                });
            }
        } else {
            //Start the Location Service
            sw1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sw1.isChecked()){
                        startLocService();
                    }
                    else{
                        tv_lon.setText("Lon:");
                        tv_lat.setText("Lat:");
                    }

                }
            });
        }
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw2.isChecked()){
                    StartTempService();
                }
                else{
                    tv_temp.setText("Temp");
                }
            }
        });
    }

    void startLocService() {

        Intent intent = new Intent(MainActivity.this, LocationService.class);
        startService(intent);
    }
    void StartTempService() {

        Intent intent = new Intent(MainActivity.this, TempService.class);
        startService(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocService();
                } else {
                    Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }



    public class LocationBroadcastReceiver extends BroadcastReceiver {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("ACT_LOC")){
                double lat = intent.getDoubleExtra("latitude", 0f);
                double longitude = intent.getDoubleExtra("longitude", 0f);
                tv_lat.setText(String.valueOf(lat));
                tv_lon.setText(String.valueOf(longitude));
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_timestamp1.setText(String.valueOf(currentTime));
                Toast.makeText(MainActivity.this, "lat is " +lat +" long is " + longitude, Toast.LENGTH_SHORT).show();
            }


            }

        }

        public class TempBroadcastReceiver extends BroadcastReceiver{
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals("TEMP")){
                    double temp = intent.getDoubleExtra("Temp_reading", 0f);
                    tv_temp.setText(String.valueOf(temp));
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    tv_timestamp2.setText(String.valueOf(currentTime));
                    Toast.makeText(MainActivity.this, "Temperature is " +temp, Toast.LENGTH_SHORT).show();
                    Log.d("log", "run: Temp_reading is "+ temp);
                }

            }
        }


    }







