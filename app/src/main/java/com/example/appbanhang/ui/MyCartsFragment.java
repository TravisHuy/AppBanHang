package com.example.appbanhang.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.PlacedOrderActivity;
import com.example.appbanhang.adapter.MyCartsAdapter;
import com.example.appbanhang.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    List<MyCartModel> cartList;
    MyCartsAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    Button buyNow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_carts, container, false);

        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));


        overTotalAmount=root.findViewById(R.id.textView4);
        recyclerView=root.findViewById(R.id.my_carts_rec);
        buyNow=root.findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        cartList=new ArrayList<>();
        adapter= new MyCartsAdapter(getActivity(),cartList);
        recyclerView.setAdapter(adapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot document:task.getResult().getDocuments()
                                 ) {
                                String documentId=document.getId();
                                MyCartModel model=document.toObject(MyCartModel.class);
                                model.setDocumentId(documentId);
                                cartList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                            calculateTotalAmount(cartList);
                        }
                        else{
                            Toast.makeText(getActivity(),"error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) cartList);
                startActivity(intent);
            }
        });
        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> cartList) {

        double totalAmount=0.0;
        for (MyCartModel myCartModel :
                cartList) {
            totalAmount+=myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount: "+totalAmount);
    }


    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill=intent.getIntExtra("totalAmount",0);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,### đ");
            String formattedTotalBill = decimalFormat.format(totalBill);
            overTotalAmount.setText("Tổng tiền: "+formattedTotalBill);
        }
    };
}