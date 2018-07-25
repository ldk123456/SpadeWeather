package com.example.a.spadeweather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a.spadeweather.GSON.LifeStyle;
import com.example.a.spadeweather.R;
import com.example.a.spadeweather.util.ShowUtil;

import java.util.ArrayList;
import java.util.List;

public class LifestyleAdapter extends RecyclerView.Adapter<LifestyleAdapter.ViewHolder> {

    private List<LifeStyle.Suggestion> mSuggestionList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView suggestionImage;
        TextView suggestionType;
        TextView suggestionBrf;
        TextView suggestionText;

        public ViewHolder(View itemView) {
            super(itemView);
            suggestionImage=itemView.findViewById(R.id.suggestion_image);
            suggestionType=itemView.findViewById(R.id.suggestion_type);
            suggestionBrf=itemView.findViewById(R.id.suggestion_brf);
            suggestionText=itemView.findViewById(R.id.suggestion_text);
        }
    }

    public LifestyleAdapter(List<LifeStyle.Suggestion> suggestionList){
        mSuggestionList=suggestionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lifestyle_item, parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LifeStyle.Suggestion suggestion=mSuggestionList.get(position);
        int lifestyleImageId=ShowUtil.showLifestyleImage(suggestion.suggestionType);
        holder.suggestionImage.setImageResource(lifestyleImageId);
        String lifestyleType=ShowUtil.showLifestyleType(suggestion.suggestionType);
        holder.suggestionType.setText(lifestyleType);
        holder.suggestionBrf.setText(suggestion.suggestionBrf);
        holder.suggestionText.setText(suggestion.suggestionText);
    }

    @Override
    public int getItemCount() {
        return mSuggestionList.size();
    }
}
