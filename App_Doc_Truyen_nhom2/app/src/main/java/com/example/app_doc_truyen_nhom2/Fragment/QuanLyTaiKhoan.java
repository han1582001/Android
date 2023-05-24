package com.example.app_doc_truyen_nhom2.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_doc_truyen_nhom2.Database.TaiKhoan;
import com.example.app_doc_truyen_nhom2.Database.TaiKhoanAdapter;
import com.example.app_doc_truyen_nhom2.ProfileActivity;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLyTaiKhoan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLyTaiKhoan extends Fragment {
private RecyclerView recyclerViewtaikhoan;
private TaiKhoanAdapter taiKhoanAdapter;
private static List<TaiKhoan> taiKhoanList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuanLyTaiKhoan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuanLyTaiKhoan.
     */
    // TODO: Rename and change types and number of parameters
    public static QuanLyTaiKhoan newInstance(String param1, String param2) {
        QuanLyTaiKhoan fragment = new QuanLyTaiKhoan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_quan_ly_tai_khoan, container, false);
       recyclerViewtaikhoan=view.findViewById(R.id.recyclerquanlytaikhoan);
        ProfileActivity.toolbar.setTitle("Quản Lý Tài Khoản");
        ProfileActivity.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("taikhoan");
        taiKhoanList=new ArrayList<>();
        taiKhoanAdapter=new TaiKhoanAdapter(view.getContext(), taiKhoanList);
        recyclerViewtaikhoan.setAdapter(taiKhoanAdapter);
        recyclerViewtaikhoan.setLayoutManager(new LinearLayoutManager(view.getContext()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taiKhoanList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    TaiKhoan taiKhoan=snapshot1.getValue(TaiKhoan.class);
                    if(taiKhoan.getType()==1){
                        taiKhoanList.add(taiKhoan);
                        taiKhoanAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       return  view;
    }
}