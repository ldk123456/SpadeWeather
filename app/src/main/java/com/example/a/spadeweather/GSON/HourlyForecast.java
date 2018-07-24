package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyForecast {
    public String status;
    public class Forecast{
        @SerializedName("time")
        public String hourlyTime;
        @SerializedName("cond_code")
        public String hourlyWeatherImage;
        @SerializedName("cond_txt")
        public String hourlyWeatherText;
        @SerializedName("tmp")
        public String hourlyTemperature;
    }
    @SerializedName("hourly")
    public List<Forecast> hourlyForecastList;
}
