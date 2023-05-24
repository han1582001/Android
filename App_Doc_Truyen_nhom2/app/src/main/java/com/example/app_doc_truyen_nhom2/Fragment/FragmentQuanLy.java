package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen_nhom2.Database.TaiKhoan;
import com.example.app_doc_truyen_nhom2.Interface.checkcode;
import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.ProfileActivity;
import com.example.app_doc_truyen_nhom2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuanLy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQuanLy extends Fragment {
private EditText edtusername, edtpassword, edtusernamedk, edtpassworddk, edtretypepass, edtyourname, edtcodexacnhan, edtcodexacnhan2;
private Button  btndangnhap, btndangky;
private LinearLayout linearLayoutdangky, linearLayoutdangnhap;
private static List<TaiKhoan> list;
private FirebaseAuth mAuth;
private TextView btnopendk,thuongdan,btnopendn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentQuanLy() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentQuanLy.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQuanLy newInstance(String param1, String param2) {
        FragmentQuanLy fragment = new FragmentQuanLy();
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
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_quan_ly, container, false);
       edtusername=view.findViewById(R.id.txtusername);
       edtpassword=view.findViewById(R.id.txtpassword);
       edtusernamedk=view.findViewById(R.id.txtusernamedk);
       edtpassworddk=view.findViewById(R.id.txtpassworddk);
       edtyourname=view.findViewById(R.id.txtyourname);
       edtcodexacnhan2=view.findViewById(R.id.txtmaxacnhan2);
       edtretypepass=view.findViewById(R.id.txtrepassworddk);
       linearLayoutdangky=view.findViewById(R.id.containerdangky);
       btnopendk=view.findViewById(R.id.btnopendangky);
       btndangky=view.findViewById(R.id.btndangky);
       thuongdan=view.findViewById(R.id.txthuongdan);
       btnopendn=view.findViewById(R.id.btngotodangnhap);
       edtcodexacnhan=view.findViewById(R.id.txtmaxacnhan);
       linearLayoutdangnhap=view.findViewById(R.id.containerdangnhap);
btndangnhap=view.findViewById(R.id.btndangnhap);

btnopendk.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams) linearLayoutdangnhap.getLayoutParams();

        int searrchmargin=layoutParams.bottomMargin+layoutParams.topMargin+linearLayoutdangnhap.getHeight();
        linearLayoutdangnhap.animate().translationY(-searrchmargin).setInterpolator(new AccelerateInterpolator(2));
        linearLayoutdangky.setVisibility(View.VISIBLE);
        linearLayoutdangky.animate().translationY(-searrchmargin).setInterpolator(new AccelerateInterpolator(2));

    }
});

        btnopendn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayoutdangnhap.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));

                linearLayoutdangky.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
                linearLayoutdangky.setVisibility(View.GONE);
            }
        });
mAuth= FirebaseAuth.getInstance();
btndangky.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email=edtusernamedk.getText().toString().trim();
        String password=edtpassworddk.getText().toString().trim();
        String fullname=edtyourname.getText().toString().trim();
        String repass=edtretypepass.getText().toString().trim();
        String ma1=edtcodexacnhan.getText().toString().trim();
        String ma2=edtcodexacnhan2.getText().toString().trim();
        if(fullname.isEmpty()){
            edtyourname.setError("Không được để trống");
            edtyourname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edtusernamedk.setError("Không được để trống");
            edtusernamedk.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edtpassworddk.setError("Không được để trống");
            edtpassworddk.requestFocus();
            return;
        }
        if(repass.isEmpty()){
            edtretypepass.setError("Không được để trống");
            edtretypepass.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtusernamedk.setError("Email không hợp lệ");
            edtusernamedk.requestFocus();
            return;

        }
        if(!repass.equals(password)){
            edtretypepass.setError("Nhập Lại Mật Khẩu Không Đúng");
            edtretypepass.requestFocus();
            return;
        }
        FirebaseQuery.checkcodedk(ma1, ma2, new checkcode() {
            @Override
            public void onComplete(boolean x) {
                if(!x){
                    edtcodexacnhan.setError("code sai");
                    edtcodexacnhan2.setError("code sai");
                    edtcodexacnhan.requestFocus();
                    return;
                }
            }
        });
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
               TaiKhoan taiKhoan=new TaiKhoan(fullname, 1, email, password);
               FirebaseDatabase.getInstance().getReference("taikhoan").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(taiKhoan).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(view.getContext(),"Đăng Ký Thành Công",Toast.LENGTH_LONG).show();
                           linearLayoutdangnhap.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
                           linearLayoutdangky.animate().translationY(0).setInterpolator(new AccelerateInterpolator(2));
                           linearLayoutdangky.setVisibility(View.GONE);
                       }else {
                           Toast.makeText(view.getContext(),"Đăng Ký Không Thành Công", Toast.LENGTH_LONG).show();
                       }
                   }
               });
                }else {
                    Toast.makeText(view.getContext(),"Đăng Ký Không Thành", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
});
btndangnhap.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String email=edtusername.getText().toString().trim();
        String password=edtpassword.getText().toString().trim();
        if(email.isEmpty()){
            edtusername.setError("Không được để trống");
            edtusername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edtpassword.setError("Không được để trống");
            edtpassword.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtusername.setError("Email không hợp lệ");
            edtusername.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(view.getContext(), ProfileActivity.class);
                    view.getContext().startActivity(intent);
                }else {
                    Toast.makeText(view.getContext(), "Đăng Nhập Không Thành Công", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
});


                return view;
    }
}