package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

public class City{
    public class CityBasic{
        @SerializedName("cid")
        public String cityId;
        @SerializedName("location")
        public String cityName;
        @SerializedName("parent_city")
        public String parentCity;
        @SerializedName("admin_area")
        public String adminArea;
    }
    public CityBasic[] basic;
    public String status;
}
