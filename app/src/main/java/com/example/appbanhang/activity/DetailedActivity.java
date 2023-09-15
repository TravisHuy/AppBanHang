package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    ImageView detailedImg;
    TextView price,rating,description,quantity;
    Button button;
    ImageView addItem,minusItem;
    Toolbar detailToolbar;
    ViewAllModel viewAllModel=null;

    //
    int totalQuantity=1;
    int totalPrice=0;
    //
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        //
        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

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

        addItem=findViewById(R.id.detailed_plus);
        minusItem=findViewById(R.id.detailed_minus);
        quantity=findViewById(R.id.detailed_quanity);
        if(viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
//            price.setText(String.valueOf(viewAllModel.getPrice()));
            DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
            String format=decimalFormat.format(viewAllModel.getPrice());
            price.setText(format);

            totalPrice=viewAllModel.getPrice()*totalQuantity;
        }
        button=findViewById(R.id.detailed_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                    price.setText(String.valueOf(totalPrice));                }
            }
        });
        minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity>0){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = viewAllModel.getPrice() * totalQuantity;
                    price.setText(String.valueOf(totalPrice));                }
            }
        });
    }

    private void addToCart() {
        String saveCurrentDate,saveCurentTime;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurentTime=currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("productImg",viewAllModel.getImg_url());
        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",viewAllModel.getPrice());
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime",saveCurentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this,"Added to A Cart",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}