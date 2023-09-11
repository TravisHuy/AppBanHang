package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.ViewAllModel;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg;
    TextView price,rating,description;
    Button button;
    ImageView addItem,minusItem;
    Toolbar detailToolbar;
    ViewAllModel viewAllModel=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        detailToolbar=findViewById(R.id.detailed_toolbar);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Object object=getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel= (ViewAllModel) object;
        }
        detailedImg=findViewById(R.id.detailed_img);

        price=findViewById(R.id.detailed_price);
        rating=findViewById(R.id.detailed_rating);
        description=findViewById(R.id.detailed_des);
        button=findViewById(R.id.detailed_button);
        addItem=findViewById(R.id.detailed_plus);
        minusItem=findViewById(R.id.detailed_minus);
        if(viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText(String.valueOf(viewAllModel.getPrice()));
        }

    }
}