package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

public class NowWeather {
    public class Now{
        @SerializedName("cond_code")
        public String weatherImage;
        @SerializedName("tmp")
        public String temperature;
        @SerializedName("cond_txt")
        public String weatherText;
    }
    public Now now;
    public String status;
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
    public Update update;
}
