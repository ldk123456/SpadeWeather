package com.example.a.spadeweather.GSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LifeStyle {
    public String status;
    public class Update{
        @SerializedName("loc")
        public String lifeUpdateTime;
    }
    public Update update;
    public class Suggestion{
        @SerializedName("type")
        public String suggestionType;
        @SerializedName("brf")
        public String suggestionBrf;
        @SerializedName("txt")
        public String suggestionText;
    }
    @SerializedName("lifestyle")
    public List<Suggestion> suggestionList;
}
