package com.example.a.spadeweather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.spadeweather.GSON.City;
import com.example.a.spadeweather.R;

import java.util.List;

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.ViewHolder>{
    private List<String> mCityList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView citySearchText;

        public ViewHolder(View itemView) {
            super(itemView);
            citySearchText=itemView.findViewById(R.id.city_search_text);
        }
    }
    public CitySearchAdapter(List<String> cityList){
        mCityList=cityList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_search_item, parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.citySearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String cityName=mCityList.get(position);
                Toast.makeText(v.getContext(), cityName, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String citySearchName=mCityList.get(position);
        holder.citySearchText.setText(citySearchName);
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }
}
