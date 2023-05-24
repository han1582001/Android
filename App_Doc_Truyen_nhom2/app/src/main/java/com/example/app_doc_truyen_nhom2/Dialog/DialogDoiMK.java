package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;

public class DialogDoiMK extends Dialog {
    private Context context;
    private EditText tMKcu, tMKmoi, tRetype;
    private ImageButton btnOK, btnCANCEL;

    public DialogDoiMK(@NonNull Context context, Context context1) {
        super(context);
        this.context = context1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogdoimatkhau);

        this.tMKmoi = findViewById(R.id.txtmkmoi);
        this.tRetype = findViewById(R.id.txtretype);
        this.btnOK = findViewById(R.id.btnOK);
        this.btnCANCEL = findViewById(R.id.btnCANCEL);
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mkmoi = tMKmoi.getText().toString().trim();
                String retype = tRetype.getText().toString().trim();
                if (mkmoi.isEmpty()) {
                    tMKmoi.setError("Không Được Để Trống");
                    tMKmoi.requestFocus();
                    return;
                }
                if (retype.isEmpty()) {
                    tRetype.setError("Không Được Để Trống");
                    tRetype.requestFocus();
                    return;
                }
                if(!retype.equals(mkmoi)){
                    tRetype.setError("Nhập lại mật khẩu sai");
                    tRetype.requestFocus();
                    return;
                }
                FirebaseQuery.DoiMK(mkmoi, new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Đổi Mật Khẩu Thành Công", Toast.LENGTH_LONG).show();
                        dismiss();
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
