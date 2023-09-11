package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.NavCategoryAdapter;
import com.example.appbanhang.adapter.ViewAllAdapter;
import com.example.appbanhang.models.NavCategoryModel;
import com.example.appbanhang.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView rec;
    List<ViewAllModel> viewallList;
    ViewAllAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        db=FirebaseFirestore.getInstance();

        String type= getIntent().getStringExtra("type");
        rec=findViewById(R.id.view_all_rec);
        rec.setLayoutManager(new LinearLayoutManager(this));

        viewallList=new ArrayList<>();
        adapter=new ViewAllAdapter(this,viewallList);
        rec.setAdapter(adapter);

        if(type!=null&& type.equalsIgnoreCase("iphone")){
            db.collection("ViewAllProducts").whereEqualTo("type","iphone").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot document:task.getResult().getDocuments()
                                     ) {
                                    ViewAllModel model=document.toObject(ViewAllModel.class);
                                    viewallList.add(model);
                                    adapter.notifyDataSetChanged();
                                }
                        }
                    });
        }
        if(type!=null&& type.equalsIgnoreCase("vivo")){
            db.collection("ViewAllProducts").whereEqualTo("type","vivo").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot document:task.getResult().getDocuments()
                            ) {
                                ViewAllModel model=document.toObject(ViewAllModel.class);
                                viewallList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        if(type!=null&& type.equalsIgnoreCase("xioami")){
            db.collection("ViewAllProducts").whereEqualTo("type","xioami").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot document:task.getResult().getDocuments()
                            ) {
                                ViewAllModel model=document.toObject(ViewAllModel.class);
                                viewallList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        if(type!=null&& type.equalsIgnoreCase("samsung")){
            db.collection("ViewAllProducts").whereEqualTo("type","samsung").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot document:task.getResult().getDocuments()
                            ) {
                                ViewAllModel model=document.toObject(ViewAllModel.class);
                                viewallList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        if(type!=null&& type.equalsIgnoreCase("oppo")){
            db.collection("ViewAllProducts").whereEqualTo("type","oppo").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot document:task.getResult().getDocuments()
                            ) {
                                ViewAllModel model=document.toObject(ViewAllModel.class);
                                viewallList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}