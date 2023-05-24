package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.Interface.getStringCompleteListener;
import com.example.app_doc_truyen_nhom2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogDoiTen extends Dialog {
private Context context1;
private EditText edtTen;
private Button btnOK, btnCANCEL;
private XoaTruyenThanhCongListener listener;
    public DialogDoiTen(@NonNull Context context, Context context1, XoaTruyenThanhCongListener listener ) {
        super(context);
        this.context1=context1;
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogdoiten);
      this.edtTen = findViewById(R.id.txtTen);
      this.btnOK=findViewById(R.id.btnOK);
      this.btnCANCEL=findViewById(R.id.btnCANCEL);
        FirebaseQuery.getTen(new getStringCompleteListener() {
            @Override
            public void onComplete(String name) {
                edtTen.setText(name);
            }
        });
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names= edtTen.getText().toString().trim();
                if(names.isEmpty()){
                    edtTen.setError("Không được để trống");
                    edtTen.requestFocus();
                    return;
                }

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("taikhoan").child(user.getUid());
                databaseReference.child("name").setValue(names).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context1, "Đổi Tên Thành Công", Toast.LENGTH_LONG).show();
                            listener.onComplete();
                            dismiss();
                        }else {
                            Toast.makeText(context1, "Đổi Tên Thất Bại", Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    }
                });
            }
        });
        this.btnCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
