package com.example.myweatherapp;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Float.isNaN;

public class MainActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "15ac32f0ba3d18448856b67056385503";
    public static String lat = "43.70011";
    public static String lon = "-79.4163";
    public static String units = "metric";

    private TextView weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherData = findViewById(R.id.textView);
       



        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentData();
            }
        });
    }

    void getCurrentData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyWeatherAppServices service = retrofit.create(MyWeatherAppServices.class);

        Call<MyWeatherAppResponse> call = service.getCurrentWeatherData(lat,lon, AppId,units);
        call.enqueue(new Callback<MyWeatherAppResponse>() {


            @Override
            public void onResponse(Call<MyWeatherAppResponse> call, Response<MyWeatherAppResponse> response) {
                if (response.code() == 200) {
                    MyWeatherAppResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String stringBuilder = "Country: " +
                            weatherResponse.sys.country +
                            "\n" +
                            "Temperature: " +
                        weatherResponse.main.temp +
                            "\n" +
                            "Temperature(Min): " +
                            weatherResponse.main.temp_min +
                            "\n" +
                            "Temperature(Max): " +
                            weatherResponse.main.temp_max +
                            "\n" +
                            "Humidity: " +
                            weatherResponse.main.humidity +
                            "\n" +
                            "Pressure: " +
                            weatherResponse.main.pressure +
                            "\n" +
                             "SunRise: " +
                            weatherResponse.sys.sunrise+
                            "\n" +
                            "SunSet: " +
                            weatherResponse.sys.sunset+
                            "\n" +
                            "Wind Speed: " +
                            Math.round(weatherResponse.wind.speed * 60 *60/1000)+" "+"Km/hr"+
                            "\n" +
                            "Lat: " +
                            lat+
                            "\n" +
                            "Lon: " +
                            lon;
                    weatherData.setText(stringBuilder);
                }
            }

            public void onFailure(Call<MyWeatherAppResponse> call, Throwable t) {
                weatherData.setText(t.getMessage());
            }


        });
    }


}