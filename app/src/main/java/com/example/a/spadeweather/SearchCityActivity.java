package com.example.a.spadeweather;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a.spadeweather.GSON.City;
import com.example.a.spadeweather.adapter.CitySearchAdapter;
import com.example.a.spadeweather.util.HttpUtil;
import com.example.a.spadeweather.util.Utility;

import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SearchCityActivity extends AppCompatActivity {

    private EditText searchText;
    private RecyclerView cityList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Button searchBtn=findViewById(R.id.search_btn);
        searchText=findViewById(R.id.search_city_text);
        cityList=findViewById(R.id.city_list);
        requestHotCity();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName=searchText.getText().toString();
                requestCity(cityName);
            }
        });
    }
    public void requestHotCity(){
        String hotCityUrl="https://search.heweather.com/top?group=cn" +
                "&key=294858754f4f457fba305b0aed27f8e3";
        HttpUtil.sendOkHttpRequest(hotCityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(cityList, "获取热门城市信息失败", Snackbar.LENGTH_LONG)
                        .setAction("重新获取", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SearchCityActivity.this,
                                        "请检查网络", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final City hotCity=Utility.handleCityResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (hotCity!=null&&hotCity.status.equals("ok")){
                            showCityInfo(hotCity);
                        }
                    }
                });
            }
        });
    }
    public void requestCity(final String cityName){
        String searchCityUrl="https://search.heweather.com/find?location="+cityName
                +"&key=294858754f4f457fba305b0aed27f8e3";
        HttpUtil.sendOkHttpRequest(searchCityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(cityList, "获取城市信息失败", Snackbar.LENGTH_LONG)
                        .setAction("重新搜索", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SearchCityActivity.this,
                                        "请输入正确的城市或地区", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
                final City city=Utility.handleCityResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (city!=null&&city.status.equals("ok")){
                            showCityInfo(city);
                        }
                    }
                });
            }
        });
    }
    public void showCityInfo(City city){
        final List<String> cityNameList=new ArrayList<>();
        for (int i = 0; i < city.basic.length; i++) {
            if (city.basic[i].cityName.equals(city.basic[i].adminArea)) {
                cityNameList.add(city.basic[i].cityName );
            }else if(city.basic[i].cityName.equals(city.basic[i].parentCity)){
                cityNameList.add(city.basic[i].cityName+ " - " + city.basic[i].adminArea);
            }else {
                cityNameList.add(city.basic[i].cityName+" - "+city.basic[i].parentCity);
            }
        }
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        cityList.setLayoutManager(layoutManager);
        CitySearchAdapter adapter=new CitySearchAdapter(cityNameList);
        cityList.setAdapter(adapter);
    }
}
