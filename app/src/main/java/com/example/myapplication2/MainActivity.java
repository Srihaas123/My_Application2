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

    TextView tv_lat, tv_lon, tv_temp, tv_timestamp1, tv_timestamp2, tv_fuel, tv_timestamp3;
    TextView tv_torque, tv_timestamp4, tv_tyre, tv_timestamp5, tv_speed, tv_timestamp6;
    Switch sw1, sw2, sw3, sw4, sw5, sw6;
    LocationBroadcastReceiver receiver;
    TempBroadcastReceiver receiver2;
    FuelBroadcastReceiver receiver3;
    TorqueBroadcastReceiver receiver4;
    TyreBroadcastReceiver receiver5;
    SpeedBroadcastReceiver receiver6;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_lat = findViewById(R.id.tv_labellat);
        tv_lon = findViewById(R.id.tv_labellon);
        tv_temp = findViewById(R.id.tv_labeltemp);
        tv_fuel = findViewById(R.id.tv_labelfuel);
        tv_torque = findViewById(R.id.tv_labeltorque);
        tv_tyre = findViewById(R.id.tv_labeltyre);
        tv_speed = findViewById(R.id.tv_labelspeed);

        sw1 = findViewById(R.id.sw_GPS);
        sw2 = findViewById(R.id.sw_TEMP);
        sw3 = findViewById(R.id.sw_FUEL);
        sw4 = findViewById(R.id.sw_TORQUE);
        sw5 = findViewById(R.id.sw_TYRE);
        sw6 = findViewById(R.id.sw_SPEED);

        tv_timestamp1 = findViewById(R.id.timestamp1);
        tv_timestamp2 = findViewById(R.id.timestamp2);
        tv_timestamp3 = findViewById(R.id.timestamp3);
        tv_timestamp4 = findViewById(R.id.timestamp4);
        tv_timestamp5 = findViewById(R.id.timestamp5);
        tv_timestamp6 = findViewById(R.id.timestamp6);

        receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver, filter);

        receiver2 = new TempBroadcastReceiver();
        IntentFilter filterTemp = new IntentFilter("TEMP");
        registerReceiver(receiver2, filterTemp);

        receiver3 = new FuelBroadcastReceiver();
        IntentFilter filterFuel = new IntentFilter("FUEL");
        registerReceiver(receiver3, filterFuel);

        receiver4 = new TorqueBroadcastReceiver();
        IntentFilter filterTorque = new IntentFilter("TORQUE");
        registerReceiver(receiver4,filterTorque);

        receiver5 = new TyreBroadcastReceiver();
        IntentFilter filterTyre = new IntentFilter("TYRE");
        registerReceiver(receiver5, filterTyre);

        receiver6 = new SpeedBroadcastReceiver();
        IntentFilter filterSpeed = new IntentFilter("SPEED");
        registerReceiver(receiver6,filterSpeed);

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
        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw3.isChecked()){
                    StartFuelService();
                }
                else{
                    tv_fuel.setText("Fuel");
                }
            }
        });
        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw4.isChecked()){
                    StartTorqueService();
                }
                else{
                    tv_torque.setText("Torque");
                }
            }
        });
        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw5.isChecked()){
                    StartTyrePressService();
                }
                else{
                    tv_tyre.setText("TyrePress");
                }
            }
        });
        sw6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw6.isChecked()){
                    StartSpeedService();
                }
                else{
                    tv_speed.setText("Speed");
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
    void StartFuelService(){

        Intent intent = new Intent(MainActivity.this, FuelService.class);
        startService(intent);

    }
    void StartTorqueService(){
        Intent intent = new Intent(MainActivity.this, TorqueService.class);
        startService(intent);
    }
    void StartTyrePressService(){
        Intent intent = new Intent(MainActivity.this, TyrePressureService.class);
        startService(intent);
    }
    void StartSpeedService(){
        Intent intent = new Intent(MainActivity.this, SpeedService.class);
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
                    Log.d("logt", "run: Temp_reading is "+ temp);

                }

            }
        }
    public class FuelBroadcastReceiver extends BroadcastReceiver{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("FUEL")){
                int fuel = (int) intent.getIntExtra("fuel", 0);
                tv_fuel.setText(String.valueOf(fuel));
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_timestamp3.setText(String.valueOf(currentTime));
                Toast.makeText(MainActivity.this, "Fuel : " +fuel, Toast.LENGTH_SHORT).show();
                Log.d("logf", "run: Fuel: "+ fuel);
            }

        }
    }

    public class TorqueBroadcastReceiver extends BroadcastReceiver{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("TORQUE")){
                int torque = (int) intent.getIntExtra("torque",0);
                tv_torque.setText(String.valueOf(torque));
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_timestamp4.setText(String.valueOf(currentTime));
                Toast.makeText(MainActivity.this, "Torque : " +torque, Toast.LENGTH_SHORT).show();
                Log.d("logto","run: Torque: "+ torque);
            }
        }
    }

    public class TyreBroadcastReceiver extends BroadcastReceiver{
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("TYRE")){
                int tyre =  intent.getIntExtra("tyre",0);
                tv_tyre.setText(String.valueOf(tyre));
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_timestamp5.setText(String.valueOf(currentTime));
                Toast.makeText(MainActivity.this, "Tyre Pressure : " +tyre, Toast.LENGTH_SHORT).show();
                Log.d("logto","run: Torque: "+ tyre);
            }
        }
    }

    public class SpeedBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("SPEED")){
                int speed = intent.getIntExtra("speed", 0);
                tv_speed.setText(String.valueOf(speed));
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                tv_timestamp6.setText(String.valueOf(currentTime));
                Toast.makeText(MainActivity.this, "Speed: " +speed, Toast.LENGTH_SHORT).show();
                Log.d("logsp","run: Speed: "+ speed);
            }
        }
    }


    }







