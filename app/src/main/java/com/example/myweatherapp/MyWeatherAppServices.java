package com.example.myweatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyWeatherAppServices {
    @GET("data/2.5/weather?")
    Call<MyWeatherAppResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id,@Query("units") String units);
}

