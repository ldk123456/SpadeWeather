package com.example.a.spadeweather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a.spadeweather.GSON.DailyForecast;
import com.example.a.spadeweather.R;
import com.example.a.spadeweather.util.ShowUtil;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private List<DailyForecast.Forecast> mDailyForecastList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView dailyUpdateTime;
        ImageView dailyWeatherImage;
        TextView dailyWeatherText;
        TextView dailyTemperature;

        public ViewHolder(View itemView) {
            super(itemView);
            dailyUpdateTime=itemView.findViewById(R.id.daily_time_text);
            dailyWeatherImage=itemView.findViewById(R.id.daily_weather_image);
            dailyWeatherText=itemView.findViewById(R.id.daily_weather_text);
            dailyTemperature=itemView.findViewById(R.id.daily_temperature_text);
        }
    }

    public DailyForecastAdapter(List<DailyForecast.Forecast> dailyForecastList) {
        mDailyForecastList=dailyForecastList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_forecast_item, parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast.Forecast dailyForecast=mDailyForecastList.get(position);
        String dailyUpdateTime=ShowUtil.showDailyTime(dailyForecast.updateTime);
        holder.dailyUpdateTime.setText(dailyUpdateTime);
        int weatherImage= ShowUtil.showWeatherImage(dailyForecast.weatherImage);
        holder.dailyWeatherImage.setImageResource(weatherImage);
        holder.dailyWeatherText.setText(dailyForecast.weatherText);
        String degreeText=dailyForecast.minTemperature+"° ~ "+
                dailyForecast.maxTemperature+"°";
        holder.dailyTemperature.setText(degreeText);
    }

    @Override
    public int getItemCount() {
        return mDailyForecastList.size();
    }

}
