package com.example.a.spadeweather.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a.spadeweather.CityManageActivity;
import com.example.a.spadeweather.MainActivity;
import com.example.a.spadeweather.R;
import com.example.a.spadeweather.database.SearchedCity;

import org.litepal.LitePal;

import java.util.List;

public class CityManageAdapter extends RecyclerView.Adapter<CityManageAdapter.ViewHolder> {

    private List<SearchedCity> mSearchedCityList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView cityManageText;
        ImageView currentCityImage;
        TextView currentCityText;

        public ViewHolder(View itemView) {
            super(itemView);
            cityManageText=itemView.findViewById(R.id.city_manage_text);
            currentCityImage=itemView.findViewById(R.id.current_city_image);
            currentCityText=itemView.findViewById(R.id.current_city_text);
        }
    }

    public CityManageAdapter(List<SearchedCity> searchedCityList){
        mSearchedCityList=searchedCityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_manage_item, parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position=holder.getAdapterPosition();
                final SearchedCity searchedCity=mSearchedCityList.get(position);
                Intent intent=new Intent(parent.getContext(), MainActivity.class);
                intent.putExtra("city_name", searchedCity.getCityName());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                parent.getContext().startActivity(intent);
                CityManageActivity activity = (CityManageActivity) parent.getContext();
                activity.finish();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position=holder.getAdapterPosition();
                final SearchedCity searchedCity=mSearchedCityList.get(position);
                final AlertDialog.Builder builder=new AlertDialog.Builder(parent.getContext());
                builder.setMessage("确定删除城市？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSearchedCityList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, mSearchedCityList.size());
                        LitePal.deleteAll(SearchedCity.class, "cityName = ?",
                                searchedCity.getCityName());
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchedCity searchedCity=mSearchedCityList.get(position);
        holder.cityManageText.setText(searchedCity.getCityName());
        holder.currentCityImage.setImageResource(searchedCity.getCurrentCityImage());
        holder.currentCityText.setText(searchedCity.getCurrentCityText());
    }

    @Override
    public int getItemCount() {
        return mSearchedCityList.size();
    }

}
