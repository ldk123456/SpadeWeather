package com.example.a.spadeweather;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a.spadeweather.GSON.LifeStyle;
import com.example.a.spadeweather.adapter.LifestyleAdapter;
import com.example.a.spadeweather.util.HttpUtil;
import com.example.a.spadeweather.util.ShowUtil;
import com.example.a.spadeweather.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LifestyleActivity extends AppCompatActivity {

    private static final String KEY="294858754f4f457fba305b0aed27f8e3";

    private CoordinatorLayout mCoordinatorLayout;
    private RecyclerView mLifestyleLayout;
    private ImageView lifeImage;
    private TextView lifeUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifestyle);
        mCoordinatorLayout=findViewById(R.id.coordinator_layout);
        mLifestyleLayout=findViewById(R.id.lifestyle_layout);
        lifeImage=findViewById(R.id.life_image);
        lifeUpdateTime=findViewById(R.id.life_update_time);
        Intent intent=getIntent();
        String cityName=intent.getStringExtra("city_name");
        Toolbar lifeToolbar=findViewById(R.id.life_toolbar);
        CollapsingToolbarLayout collapsingToolbar=findViewById(R.id.life_collapsing_toolbar);
        setSupportActionBar(lifeToolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(cityName);
        Glide.with(this).load(R.mipmap.ic_lifestyle).into(lifeImage);
        requestLifestyle(cityName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestLifestyle(final String cityName){
        final String lifestyleUrl="https://free-api.heweather.com/s6/weather/lifestyle?location="
                +cityName+"&key="+KEY;
        HttpUtil.sendOkHttpRequest(lifestyleUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(mCoordinatorLayout, "请求生活指数信息失败",
                        Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String lifestyleResponseText=response.body().string();
                final LifeStyle lifeStyle= Utility.handleLifestyleResponse(lifestyleResponseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (lifeStyle!=null&&lifeStyle.status.equals("ok")){
                            showLifestyleInfo(lifeStyle);
                        }
                    }
                });
            }
        });
    }
    private void showLifestyleInfo(LifeStyle lifeStyle){
        String updateTime=lifeStyle.update.lifeUpdateTime;
        String time= ShowUtil.showTime(updateTime);
        lifeUpdateTime.setText("更新于 "+time);
        List<LifeStyle.Suggestion> suggestionList=lifeStyle.suggestionList;
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        mLifestyleLayout.setLayoutManager(layoutManager);
        LifestyleAdapter adapter=new LifestyleAdapter(suggestionList);
        mLifestyleLayout.setAdapter(adapter);
    }
}
