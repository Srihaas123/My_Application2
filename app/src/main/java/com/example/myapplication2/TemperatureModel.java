package com.example.myapplication2;



public class TemperatureModel {
    private double temp_reading;
    public TemperatureModel(double temp_reading){
        this.temp_reading = temp_reading;
    }
    public double getTemp_reading(){
        return temp_reading;
    }
    public void setTemp_reading(double temp_reading){
        this.temp_reading = temp_reading;

    }
}
