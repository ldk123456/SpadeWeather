package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

public class NowAir {
    public String status;
    public class AQI{
        @SerializedName("aqi")
        public String airNumber;
        @SerializedName("qlty")
        public String airText;
    }
    @SerializedName("air_now_city")
    public AQI aqi;
}
