package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

public class DailyForecast {
    public String status;
    public class Forecast{
        @SerializedName("date")
        public String updateTime;
        @SerializedName("cond_code_d")
        public String weatherImage;
        @SerializedName("cond_txt_d")
        public String weatherText;
        public int temp_max;
        public int temp_min;
    }
    @SerializedName("daily_forecast")
    public Forecast[] dailyForecast;
}
