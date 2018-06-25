package com.example.a.spadeweather.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.spadeweather.GSON.City;
import com.example.a.spadeweather.MainActivity;
import com.example.a.spadeweather.R;
import com.example.a.spadeweather.SearchCityActivity;
import com.example.a.spadeweather.database.SearchedCity;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

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
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_search_item, parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.citySearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                String cityName=mCityList.get(position);
                Connector.getDatabase();
                List<SearchedCity> searchedCities=LitePal.where("cityName = ?", cityName)
                        .find(SearchedCity.class);
                if (searchedCities.size()==0){
                    SearchedCity searchedCity=new SearchedCity();
                    searchedCity.setCityName(cityName);
                    searchedCity.save();
                }
                Intent intent=new Intent(parent.getContext(), MainActivity.class);
                intent.putExtra("city_name", cityName);
                parent.getContext().startActivity(intent);
                SearchCityActivity activity = (SearchCityActivity) parent.getContext();
                activity.finish();
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
