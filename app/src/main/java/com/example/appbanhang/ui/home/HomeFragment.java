package com.example.appbanhang.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.CategoryAdapter;
import com.example.appbanhang.adapter.PopularAdpater;
import com.example.appbanhang.adapter.RecommenedAdapter;
import com.example.appbanhang.adapter.ViewAllAdapter;
import com.example.appbanhang.databinding.FragmentHomeBinding;
import com.example.appbanhang.models.CategoryModel;
import com.example.appbanhang.models.PopularModel;
import com.example.appbanhang.models.RecommenedModel;
import com.example.appbanhang.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    FirebaseFirestore db;
    RecyclerView popularRec,categoryRec,recommenedRec;

    List<PopularModel> list;
    PopularAdpater adpater;

    // Phân loại
    List<CategoryModel> categoryModelList;
    CategoryAdapter categoryAdapter;
    //Đề xuất sản phẩm
    List<RecommenedModel> recommenedList;
    RecommenedAdapter recommenedAdapter;

    //
    EditText search_box;
    List<ViewAllModel> viewAllModelList;
    RecyclerView recyclerViewSearch;
    ViewAllAdapter viewAllAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db=FirebaseFirestore.getInstance();


        popularRec=root.findViewById(R.id.pop_rec);
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        list=new ArrayList<>();
        adpater=new PopularAdpater(getActivity(),list);
        popularRec.setAdapter(adpater);
        db.collection("PopularProducts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()
                                 ) {
                                PopularModel model=document.toObject(PopularModel.class);
                                list.add(model);
                                adpater.notifyDataSetChanged();
                            } 
                        }
                        else{
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //
        categoryRec=root.findViewById(R.id.pop_rec2);
        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        categoryModelList=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(getActivity(), categoryModelList);
        categoryRec.setAdapter(categoryAdapter);
        db.collection("HomeCategory").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()
                            ) {
                                CategoryModel model=document.toObject(CategoryModel.class);
                                categoryModelList.add(model);
                                adpater.notifyDataSetChanged();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //
        recommenedRec=root.findViewById(R.id.pop_rec3);
        recommenedRec.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recommenedList=new ArrayList<>();
        recommenedAdapter=new RecommenedAdapter(getActivity(),recommenedList);
        recommenedRec.setAdapter(recommenedAdapter);
        db.collection("RecommenedProducts").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()
                                 ) {
                                RecommenedModel recommend=document.toObject(RecommenedModel.class);
                                recommenedList.add(recommend);
                                adpater.notifyDataSetChanged();
                            }
                        }
                        else{
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        search_box=root.findViewById(R.id.search_box);
        recommenedRec=root.findViewById(R.id.search_rec);
        viewAllModelList=new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(getContext(),viewAllModelList);
        recommenedRec.setLayoutManager(new LinearLayoutManager(getContext()));
        recommenedRec.setAdapter(viewAllAdapter);
        recommenedRec.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }
                else{
                    searchProduct(editable.toString());
                }
            }
        });

        return root;

    }

    private void searchProduct(String type) {
        if(!type.isEmpty()){
            db.collection("ViewAllProducts  ").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult()!=null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc:task.getResult().getDocuments()
                                     ) {
                                    ViewAllModel viewAllModel=doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });

        }
    }

}