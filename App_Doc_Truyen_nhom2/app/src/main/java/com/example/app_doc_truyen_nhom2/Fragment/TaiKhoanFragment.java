package com.example.app_doc_truyen_nhom2.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.TaiKhoan;
import com.example.app_doc_truyen_nhom2.Dialog.DialogDoiMK;
import com.example.app_doc_truyen_nhom2.Dialog.DialogDoiTen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogMaDangKy;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaiKhoanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaiKhoanFragment extends Fragment {
private static TaiKhoan taiKhoan;
private FirebaseUser taikhoan;
private DatabaseReference reference;
private static String idTaikhoan;

    private TextView txtName, txtloaitk, temail;
    private ImageButton btnsuaten;
    private Button btnduyettruyen, btndoimatkhau;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaiKhoanFragment(String idTaikhoan) {
        // Required empty public constructor
        this.idTaikhoan=idTaikhoan;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *


     * @return A new instance of fragment TaiKhoanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaiKhoanFragment newInstance(String username, String password) {
        TaiKhoanFragment fragment = new TaiKhoanFragment("ss");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putString(ARG_PARAM2, password);
        fragment.setArguments(args);
        return fragment;
    }
    public static TaiKhoanFragment newInstance(String idTaikhoan2) {
        TaiKhoanFragment fragment = new TaiKhoanFragment("ss");
        Bundle args = new Bundle();
        args.putString(ARG_PARAM3, idTaikhoan2);
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
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction =getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerquanly, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tai_khoan, container, false);
        txtName=view.findViewById(R.id.txtTen);
        txtloaitk=view.findViewById(R.id.txtloaitk);
        btnsuaten=view.findViewById(R.id.btnsuaten);
temail=view.findViewById(R.id.txtemail);
        btnduyettruyen=view.findViewById(R.id.gotoduyettruyen);

        btndoimatkhau=view.findViewById(R.id.btndoimatkhau);
        btnsuaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDoiTen dialogDoiTen=new DialogDoiTen(view.getContext(), view.getContext(), new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
                        loadFragment(new TaiKhoanFragment(idTaikhoan));
                    }
                });
                dialogDoiTen.show();
            }
        });
        reference= FirebaseDatabase.getInstance().getReference("taikhoan");
        reference.child(idTaikhoan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaiKhoan taiKhoan=snapshot.getValue(TaiKhoan.class);
                if(taiKhoan !=null){
                    String name= taiKhoan.getName();
                    String email=taiKhoan.getEmail();
                    txtName.setText(name);
temail.setText(email);
                    if(taiKhoan.getType()==0){
                        txtloaitk.setText("Tài Khoản Thiên Đạo");
                        btnduyettruyen.setVisibility(View.VISIBLE);
                        btndoimatkhau.setVisibility(View.VISIBLE);
                        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogDoiMK dialogDoiMK=new DialogDoiMK(view.getContext(), view.getContext());
                                dialogDoiMK.show();
                            }
                        });
                        btnduyettruyen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogMaDangKy dialogMaDangKy=new DialogMaDangKy(view.getContext(),view.getContext());
                                dialogMaDangKy.show();
                            }
                        });

                    }
                    if(taiKhoan.getType()==1){
                        txtloaitk.setText("Tài Khoản Admin");

                        btnduyettruyen.setVisibility(View.GONE);
                        btndoimatkhau.setVisibility(View.VISIBLE);

                        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogDoiMK dialogDoiMK=new DialogDoiMK(view.getContext(), view.getContext());
                                dialogDoiMK.show();
                            }
                        });

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