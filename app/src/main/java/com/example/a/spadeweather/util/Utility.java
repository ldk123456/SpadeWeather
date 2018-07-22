package com.example.a.spadeweather.util;

import com.example.a.spadeweather.GSON.City;
import com.example.a.spadeweather.GSON.DailyForecast;
import com.example.a.spadeweather.GSON.NowAir;
import com.example.a.spadeweather.GSON.NowWeather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {
    public static City handleCityResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
            String cityContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(cityContent, City.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static NowWeather handleNowWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
            String nowWeatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(nowWeatherContent, NowWeather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static NowAir handleNowAirResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
            String airNowContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(airNowContent, NowAir.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static DailyForecast handleDailyForecastResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather6");
            String dailyForecastContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(dailyForecastContent, DailyForecast.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
