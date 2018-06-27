package com.example.a.spadeweather;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.PrimitiveIterator;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar title=findViewById(R.id.title);
        setSupportActionBar(title);
        mDrawerLayout=findViewById(R.id.drawer_layout);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWeather();
            }
        });
        final NavigationView navView=findViewById(R.id.nav_view);
        final ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle(getIntent().getStringExtra("city_name"));
        }
        navView.setNavigationItemSelectedListener(new NavigationView.
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_city_manage:
                        Intent intent=new Intent(MainActivity.this, CityManageActivity.class);
                        intent.putExtra("city_name", actionBar.getTitle());
                        startActivity(intent);
                        break;
                    case R.id.nav_refresh:
                        Toast.makeText(MainActivity.this, "refresh",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_night_mode:
                        Toast.makeText(MainActivity.this, "night",
                                Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });
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
    private void refreshWeather(){
        Toast.makeText(MainActivity.this, "refresh",
                Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Snackbar.make(this.mDrawerLayout, "再按一次退出程序",
                    Snackbar.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
