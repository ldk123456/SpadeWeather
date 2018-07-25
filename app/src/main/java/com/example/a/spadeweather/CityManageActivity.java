package com.example.a.spadeweather;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        Toolbar manageToolbar=findViewById(R.id.manage_toolbar);
        setSupportActionBar(manageToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        TextView titleView=findViewById(R.id.title_view);
        titleView.setText("城市管理");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    private void initList(){
        String cityName=getIntent().getStringExtra("city_name");
        List<SearchedCity> searchedCities=LitePal.where("cityName = ?", cityName)
                .find(SearchedCity.class);
        if (searchedCities.size()==0){
            SearchedCity searchedCity=new SearchedCity();
            searchedCity.setCityName(cityName);
            searchedCity.save();
        }
        for (SearchedCity searchedCity: LitePal.findAll(SearchedCity.class)){
            if (searchedCity.getCityName().equals(cityName)){
                searchedCity.setCurrentCityText("当前显示");
                searchedCity.setCurrentCityImage(R.mipmap.ic_current_city);
            }
            searchedCityList.add(searchedCity);
        }
    }
}
