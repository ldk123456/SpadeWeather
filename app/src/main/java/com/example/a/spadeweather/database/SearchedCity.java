package com.example.a.spadeweather.database;

import org.litepal.crud.LitePalSupport;

public class SearchedCity extends LitePalSupport {
    private String cityName;
    private int currentCityImage;
    private String currentCityText;

    public int getCurrentCityImage() {
        return currentCityImage;
    }

    public void setCurrentCityImage(int currentCityImage) {
        this.currentCityImage = currentCityImage;
    }

    public String getCurrentCityText() {
        return currentCityText;
    }

    public void setCurrentCityText(String currentCityText) {
        this.currentCityText = currentCityText;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
