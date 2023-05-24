package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.TaiKhoan;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.ProfileActivity;
import com.example.app_doc_truyen_nhom2.Adapter.QuanLyTruyenAdapter;
import com.example.app_doc_truyen_nhom2.R;
import com.example.app_doc_truyen_nhom2.ThemTruyenActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    private List<Truyen> listtruyen;
    Context context;
    private RecyclerView recyclerViewQuanlytruyen;
    private QuanLyTruyenAdapter quanLyTruyenAdapter;
    private BookDatabase database;
    private ImageButton bThemTruyen;
    private EditText edtSearch;
    private LinearLayout layoutmatketnoi;
    private static TaiKhoan taiKhoan;
    private TextView textThongbao;
    private ProgressBar progressBar;
    private String key=null;
    private boolean isloading=false;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profile(TaiKhoan taiKhoan) {
        // Required empty public constructor
        this.taiKhoan=taiKhoan;
    }

    public Profile() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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
    private void getTruyen(String y) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseQuery.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Truyen> list=new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Truyen truyen = snapshot1.getValue(Truyen.class);
                    if(truyen.getTenTruyen().toLowerCase(Locale.ROOT).contains(y))
                        list.add(truyen);
                    key=snapshot1.getKey();
                }
                listtruyen.addAll(list);
                quanLyTruyenAdapter.notifyDataSetChanged();
                isloading=false;
                progressBar.setVisibility(View.GONE);
                if (listtruyen.size() == 0) {
                    textThongbao.setVisibility(View.VISIBLE);
                    textThongbao.setText("Không Tìm Thấy Truyện");
                }
                if (listtruyen.size() > 0) {
                    textThongbao.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void reset(){
        key=null;
        listtruyen.clear();
    }
    final boolean isNetworkConnected(Context context1) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context1.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bThemTruyen = view.findViewById(R.id.btntothemtruyen);
        textThongbao = view.findViewById(R.id.txtthongbao);
        progressBar=view.findViewById(R.id.progessbarallbook);
        progressBar.setVisibility(View.VISIBLE);
        edtSearch = view.findViewById(R.id.txtsearchtutruyen2);
        layoutmatketnoi=view.findViewById(R.id.thongbaomatketnoi);
        ProfileActivity.toolbar.setTitle("Quản Lý Truyện");
        ProfileActivity.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        if(isNetworkConnected(view.getContext())==false){
            layoutmatketnoi.setVisibility(View.VISIBLE);

        }else{
            layoutmatketnoi.setVisibility(View.GONE);
        }
        bThemTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThemTruyenActivity.class);
                startActivity(intent);
            }
        });
        recyclerViewQuanlytruyen = view.findViewById(R.id.recyclerqltruyen);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("truyen");
        listtruyen=new ArrayList<>();
        quanLyTruyenAdapter = new QuanLyTruyenAdapter(view.getContext(), listtruyen);
        recyclerViewQuanlytruyen.setAdapter(quanLyTruyenAdapter);
        recyclerViewQuanlytruyen.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getTruyen("");

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
reset();
                getTruyen(edtSearch.getText().toString().toLowerCase(Locale.ROOT));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


}