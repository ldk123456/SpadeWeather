package com.example.a.spadeweather;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.a.spadeweather.GSON.City;
import com.example.a.spadeweather.GSON.DailyForecast;
import com.example.a.spadeweather.GSON.HourlyForecast;
import com.example.a.spadeweather.GSON.NowAir;
import com.example.a.spadeweather.GSON.NowWeather;
import com.example.a.spadeweather.adapter.DailyForecastAdapter;
import com.example.a.spadeweather.adapter.HourlyForecastAdapter;
import com.example.a.spadeweather.database.SearchedCity;
import com.example.a.spadeweather.util.HttpUtil;
import com.example.a.spadeweather.util.ShowUtil;
import com.example.a.spadeweather.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;

    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private CollapsingToolbarLayout toolbarLayout;
    private long exitTime;
    private  ActionBar mActionBar;
    public LocationClient mLocationClient;
    private static int LOCATION_FLAG = 0;
    private static String KEY0="294858754f4f457fba305b0aed27f8e3";
    private static String KEY="b172b66828f44348818e44e94bf76ce1";

    private ImageView nowWeatherImage;
    private TextView nowDegreeText;
    private TextView nowWeatherText;
    private TextView nowAirNumber;
    private TextView nowAirText;
    private TextView updateTimeText;

    private RecyclerView dailyForecastLayout;
    private RecyclerView hourlyForecastLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar title=findViewById(R.id.title);
        setSupportActionBar(title);
        editor=PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this).edit();
        mDrawerLayout=findViewById(R.id.drawer_layout);
        mSwipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mProgressBar=findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        toolbarLayout=findViewById(R.id.toolbar_layout);
        mActionBar=getSupportActionBar();
        if (mActionBar!=null){
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            String cityName=getIntent().getStringExtra("city_name");
            if (cityName!=null){
                editor.putString("title_name", cityName);
                editor.apply();
                requestWeather();
            }
        }
        final NavigationView navView=findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_city_manage:
                        Intent manageIntent=new Intent(MainActivity.this,
                                CityManageActivity.class);
                        manageIntent.putExtra("city_name", mActionBar.getTitle());
                        startActivity(manageIntent);
                        break;
                    case R.id.nav_relocation:
                        mProgressBar.setVisibility(View.VISIBLE);
                        checkPermissions();
                        break;
                    case R.id.nav_exit:
                        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("确定退出程序？");
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(1);
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton searchButton=findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent=new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(searchIntent);
            }
        });
        if (isNetWorkAvailable(this)){
            if (LOCATION_FLAG==0){
                checkPermissions();
                LOCATION_FLAG=1;
            }
        }else {
            Snackbar.make(this.mSwipeRefreshLayout, "无网络连接",
                    Snackbar.LENGTH_LONG).show();
        }

        nowWeatherImage=findViewById(R.id.now_weather_image);
        nowDegreeText=findViewById(R.id.now_degree_text);
        nowWeatherText=findViewById(R.id.now_weather_text);
        nowAirNumber=findViewById(R.id.now_air_number);
        nowAirText=findViewById(R.id.now_air_text);
        updateTimeText=findViewById(R.id.updateTime_text);

        dailyForecastLayout=findViewById(R.id.daily_forecast_layout);
        hourlyForecastLayout=findViewById(R.id.hourly_forecast_layout);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Snackbar.make(this.mSwipeRefreshLayout, "再按一次退出程序",
                    Snackbar.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
    private boolean isNetWorkAvailable(Context context){
        ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            return false;
        }
        return true;
    }
    private void checkPermissions(){
        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions,1);
        }
        requestLocation();
    }
    private void requestLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                          int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意权限才能使用程序",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this, "发生未知错误",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            default:
        }
    }
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int length=bdLocation.getDistrict().length();
            String name1;
            if (length==2){
                name1=bdLocation.getDistrict();
            }else {
                name1=bdLocation.getDistrict().substring(0, length-1);
            }
            String name2=bdLocation.getCity().split("市")[0];
            String cityName=name1+","+name2;
            editor.putString("title_name", cityName);
            editor.apply();
            requestWeather();
            List<SearchedCity> searchedCities= LitePal.where("cityName = ?", cityName)
                    .find(SearchedCity.class);
            if (searchedCities.size()==0){
                SearchedCity searchedCity=new SearchedCity();
                searchedCity.setCityName(cityName);
                searchedCity.save();
            }
        }
    }
    private void requestWeather(){
        SharedPreferences preferences=PreferenceManager
                .getDefaultSharedPreferences(this);
        String cityName=preferences.getString("title_name",null);
        toolbarLayout.setTitleEnabled(true);
        toolbarLayout.setTitle(cityName);
        mActionBar.setTitle(cityName);
        requestNowWeather(cityName);
        requestNowAir(cityName);
        requestHourlyForecast(cityName);
        requestDailyForecast(cityName);
        mProgressBar.setVisibility(View.INVISIBLE);
        Snackbar.make(mSwipeRefreshLayout, "成功加载最新天气",
                Snackbar.LENGTH_SHORT).show();
    }
    private void requestNowWeather(final String cityName){
        final String nowWeatherUrl="https://free-api.heweather.com/s6/weather/now?location="
                +cityName+"&key="+KEY;
        HttpUtil.sendOkHttpRequest(nowWeatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(mSwipeRefreshLayout, "请求天气信息失败",
                        Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String nowWeatherResponseText=response.body().string();
                final NowWeather nowWeather=Utility.handleNowWeatherResponse(nowWeatherResponseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (nowWeather!=null&&nowWeather.status.equals("ok")){
                            showNowWeatherInfo(nowWeather);
                        }
                    }
                });
            }
        });
    }
    private void showNowWeatherInfo(NowWeather nowWeather){
        String weatherImage=nowWeather.now.weatherImage;
        String nowDegree=nowWeather.now.temperature;
        String nowWeatherInfo=nowWeather.now.weatherText;
        String updateTime=nowWeather.update.updateTime;
        String time= ShowUtil.showTime(updateTime);
        int nowWeatherImageId=ShowUtil.showWeatherImage(weatherImage);
        nowDegreeText.setText(nowDegree);
        nowWeatherText.setText(nowWeatherInfo);
        updateTimeText.setText(time);
        nowWeatherImage.setImageResource(nowWeatherImageId);
    }
    private void requestNowAir(final String cityName){
        final String searchCityUrl="https://search.heweather.com/find?location="+cityName
                +"&key=294858754f4f457fba305b0aed27f8e3&group=cn";
        HttpUtil.sendOkHttpRequest(searchCityUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String nowAirResponse=response.body().string();
                final City city=Utility.handleCityResponse(nowAirResponse);
                if (city!=null&&city.status.equals("ok")){
                    String location=city.basic[0].parentCity;
                    final String nowAirUrl="https://free-api.heweather.com/s6/air/now?location="
                            +location+"&key="+KEY;
                    HttpUtil.sendOkHttpRequest(nowAirUrl, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Snackbar.make(mSwipeRefreshLayout, "请求空气质量信息失败",
                                    Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String nowAirResponseText=response.body().string();
                            final NowAir nowAir=Utility.handleNowAirResponse(nowAirResponseText);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (nowAir!=null&&nowAir.status.equals("ok")){
                                        showNowAirInfo(nowAir);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }
    private void showNowAirInfo(NowAir nowAir){
        String airNumber=nowAir.aqi.airNumber;
        String airText=nowAir.aqi.airText;
        nowAirNumber.setText(airNumber);
        nowAirText.setText(airText);
    }
    private void requestHourlyForecast(final String cityName){
        final String hourlyForecastUrl="https://free-api.heweather.com/s6/weather/hourly?location="
                +cityName+"&key="+KEY;
        HttpUtil.sendOkHttpRequest(hourlyForecastUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(mSwipeRefreshLayout, "请求逐小时天气信息失败",
                        Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String hourlyForecastResponseText=response.body().string();
                final HourlyForecast hourlyForecast=Utility.handleHourlyForecastResponse(hourlyForecastResponseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (hourlyForecast!=null&&hourlyForecast.status.equals("ok")){
                            showHourlyForecastInfo(hourlyForecast);
                        }
                    }
                });
            }
        });
    }
    private void showHourlyForecastInfo(HourlyForecast hourlyForecast){
        List<HourlyForecast.Forecast> hourlyForecastList=hourlyForecast.hourlyForecastList;
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        hourlyForecastLayout.setLayoutManager(layoutManager);
        HourlyForecastAdapter hourlyForecastAdapter=new
                HourlyForecastAdapter(hourlyForecastList);
        hourlyForecastLayout.setAdapter(hourlyForecastAdapter);
    }
    private void requestDailyForecast(final String cityName){
        final String dailyForecastUrl="https://free-api.heweather.com/s6/weather/forecast?location="
                +cityName+"&key="+KEY;
        HttpUtil.sendOkHttpRequest(dailyForecastUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(mSwipeRefreshLayout, "请求未来三天天气信息失败",
                        Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String dailyForecastResponseText=response.body().string();
                final DailyForecast dailyForecast=Utility.handleDailyForecastResponse(dailyForecastResponseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dailyForecast!=null&&dailyForecast.status.equals("ok")){
                            showDailyForecastInfo(dailyForecast);
                        }
                    }
                });
            }
        });
    }
    private void showDailyForecastInfo(DailyForecast dailyForecast){
        List<DailyForecast.Forecast> dailyForecastList=dailyForecast.dailyForecastList;
        StaggeredGridLayoutManager layoutManager=new
                StaggeredGridLayoutManager(dailyForecastList.size(),
                StaggeredGridLayoutManager.VERTICAL);
        dailyForecastLayout.setLayoutManager(layoutManager);
        DailyForecastAdapter dailyForecastAdapter=new DailyForecastAdapter(dailyForecastList);
        dailyForecastLayout.setAdapter(dailyForecastAdapter);
    }
}
