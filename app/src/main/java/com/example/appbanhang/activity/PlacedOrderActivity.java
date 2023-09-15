package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        List<MyCartModel> list= (List<MyCartModel>) getIntent().getSerializableExtra("itemList");

        if(list!=null && list.size()>0){
            for (MyCartModel mode:list
                 ) {
                final HashMap<String,Object> cartMap=new HashMap<>();
                cartMap.put("productImg",mode.getProductImg());
                cartMap.put("productName",mode.getProductName());
                cartMap.put("productPrice",mode.getProductPrice());
                cartMap.put("currentDate",mode.getCurrentDate());
                cartMap.put("currentTime",mode.getCurrentTime());
                cartMap.put("totalQuantity",mode.getTotalQuantity());
                cartMap.put("totalPrice",mode.getTotalPrice());
                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PlacedOrderActivity.this,"Đơn hàng bạn đã được đặt",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
}