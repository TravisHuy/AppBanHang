package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;

import com.example.appbanhang.activity.DetailedActivity;
import com.example.appbanhang.activity.ViewAllActivity;
import com.example.appbanhang.models.ViewAllModel;

import java.text.DecimalFormat;
import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    Context context;
    List<ViewAllModel> viewAllList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllList) {
        this.context = context;
        this.viewAllList = viewAllList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(viewAllList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(viewAllList.get(position).getName());
        holder.description.setText(viewAllList.get(position).getDescription());
        holder.rating.setText(viewAllList.get(position).getRating());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        String formattedPrice=decimalFormat.format(viewAllList.get(position).getPrice());
        holder.price.setText(formattedPrice);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailedActivity.class);
                intent.putExtra("detail",viewAllList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewAllList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,rating,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.viewall_img);
            name=itemView.findViewById(R.id.viewall_name);
            description=itemView.findViewById(R.id.viewall_des);
            rating=itemView.findViewById(R.id.viewall_rating);
            price=itemView.findViewById(R.id.viewall_price);


        }
    }
}
