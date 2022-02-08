package com.example.myapplication2;


import java.util.Random;

public class Randomizer {

    public static int random_with_range(int minimum, int maximum){
        Random random;
        random = new Random();
        int val = random.nextInt((maximum+1)-minimum)+minimum;
        return val;
    }
    public static int decrement(int current_val, int min_reduction){

        int reduction = (int) random_with_range(0,min_reduction);
        return (current_val - reduction);

    }
    public static int increment(int current_val, int min_inc){
        int increase = random_with_range(0, min_inc);
        return (current_val+increase);
    }

}
