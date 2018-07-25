package com.example.a.spadeweather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a.spadeweather.GSON.HourlyForecast;
import com.example.a.spadeweather.R;
import com.example.a.spadeweather.util.ShowUtil;

import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.ViewHolder> {

    private List<HourlyForecast.Forecast> mHourlyForecastList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView hourlyTime;
        ImageView hourlyWeatherImage;
        TextView hourlyWeatherText;
        TextView hourlyTemperature;

        public ViewHolder(View itemView) {
            super(itemView);
            hourlyTime=itemView.findViewById(R.id.hourly_time);
            hourlyWeatherImage=itemView.findViewById(R.id.hourly_weather_image);
            hourlyWeatherText=itemView.findViewById(R.id.hourly_weather_text);
            hourlyTemperature=itemView.findViewById(R.id.hourly_temperature);
        }
    }

    public HourlyForecastAdapter(List<HourlyForecast.Forecast> hourlyForecastList){
        mHourlyForecastList=hourlyForecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hourly_forecast_item, parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HourlyForecast.Forecast hourlyForecast=mHourlyForecastList.get(position);
        String time= ShowUtil.showHourlyTime(hourlyForecast.hourlyTime);
        holder.hourlyTime.setText(time);
        int hourlyWeatherImageId=ShowUtil.showWeatherImage(hourlyForecast.hourlyWeatherImage);
        holder.hourlyWeatherImage.setImageResource(hourlyWeatherImageId);
        holder.hourlyWeatherText.setText(hourlyForecast.hourlyWeatherText);
        holder.hourlyTemperature.setText(hourlyForecast.hourlyTemperature);
    }

    @Override
    public int getItemCount() {
        return mHourlyForecastList.size();
    }

}
