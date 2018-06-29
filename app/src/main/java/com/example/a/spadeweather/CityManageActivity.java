package com.example.a.spadeweather;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.a.spadeweather.adapter.CityManageAdapter;
import com.example.a.spadeweather.database.SearchedCity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class CityManageActivity extends AppCompatActivity {

    private List<SearchedCity> searchedCityList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manage);
        FloatingActionButton addCityBtn=findViewById(R.id.add_city_btn);
        addCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CityManageActivity.this, SearchCityActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initList();
        RecyclerView cityManageList=findViewById(R.id.city_manage_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        cityManageList.setLayoutManager(layoutManager);
        CityManageAdapter adapter=new CityManageAdapter(searchedCityList);
        cityManageList.setAdapter(adapter);
    }
    private void initList(){
        String cityName=getIntent().getStringExtra("city_name");
        for (SearchedCity searchedCity: LitePal.findAll(SearchedCity.class)){
            if (searchedCity.getCityName().equals(cityName)){
                searchedCity.setCurrentCityText("当前显示");
                searchedCity.setCurrentCityImage(R.mipmap.ic_current_city);
            }
            searchedCityList.add(searchedCity);
        }
    }
}
