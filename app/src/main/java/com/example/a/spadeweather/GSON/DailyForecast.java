package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecast {
    public String status;
    public class Forecast{
        @SerializedName("date")
        public String updateTime;
        @SerializedName("cond_code_d")
        public String weatherImage;
        @SerializedName("cond_txt_d")
        public String weatherText;
        @SerializedName("tmp_min")
        public String minTemperature;
        @SerializedName("tmp_max")
        public String maxTemperature;
    }
    @SerializedName("daily_forecast")
    public List<Forecast> dailyForecastList;
}
