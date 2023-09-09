package com.example.appbanhang.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.RecommenedModel;

import java.util.List;

public class RecommenedAdapter extends RecyclerView.Adapter<RecommenedAdapter.ViewHolder> {
    Context context;
    List<RecommenedModel> list;

    public RecommenedAdapter(Context context, List<RecommenedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecommenedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_recommened,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecommenedAdapter.ViewHolder holder, int position) {
        RecommenedModel model=list.get(position);
        Glide.with(context).load(model.getImg_url()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.rating.setText(model.getRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.rec_img);
            name=itemView.findViewById(R.id.rec_name);
            description=itemView.findViewById(R.id.rec_des);
            rating=itemView.findViewById(R.id.rec_rating);
        }
    }
}
