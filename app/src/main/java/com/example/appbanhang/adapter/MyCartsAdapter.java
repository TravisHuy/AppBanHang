package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.MyCartModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyCartsAdapter extends RecyclerView.Adapter<MyCartsAdapter.ViewHolder> {
    Context context;
    List<MyCartModel> list;
    int totalPrice=0;
    public MyCartsAdapter(Context context,List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyCartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_carts_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getProductImg()).into(holder.productImg);
        holder.productName.setText(list.get(position).getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### đ");
        String productPriceFormatted = decimalFormat.format(list.get(position).getProductPrice());
        String totalPriceFormatted = decimalFormat.format(list.get(position).getTotalPrice());

        holder.productPrice.setText(productPriceFormatted); // Gán giá sản phẩm đã định dạng
        holder.totalPrice.setText(totalPriceFormatted);
        holder.currentTime.setText(list.get(position).getCurrentTime());
        holder.currentDate.setText(list.get(position).getCurrentDate());

        totalPrice=totalPrice+list.get(position).getProductPrice();
        Intent intent=new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView productName,productPrice,currentDate,currentTime,totalPrice,totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg=itemView.findViewById(R.id.cart_img);
            productName=itemView.findViewById(R.id.cart_name);
            productPrice=itemView.findViewById(R.id.cart_price);
            currentDate=itemView.findViewById(R.id.cart_date);
            currentTime=itemView.findViewById(R.id.cart_time);
            totalPrice=itemView.findViewById(R.id.cart_totalPrice);
            totalQuantity=itemView.findViewById(R.id.cart_totalQuantity);

        }
    }
}
