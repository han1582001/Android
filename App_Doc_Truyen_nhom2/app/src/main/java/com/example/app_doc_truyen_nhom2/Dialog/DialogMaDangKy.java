package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Interface.GetCodeCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;

public class DialogMaDangKy extends Dialog {
    private Context context;
    private ImageButton btnOK, btnCANCEL;
    private TextView tMa1, tMa2;

    public DialogMaDangKy(@NonNull Context context, Context context1) {
        super(context);
        this.context=context1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogdoimadk);
        this.btnOK = findViewById(R.id.btnOK);
        this.btnCANCEL = findViewById(R.id.btnCANCEL);
        this.tMa1 = findViewById(R.id.txtma1);
        this.tMa2 = findViewById(R.id.txtma2);
        FirebaseQuery.getcodedk(new GetCodeCompleteListener() {
            @Override
            public void onComplete(String code1, String code2) {
                tMa1.setText(code1);
                tMa2.setText(code2);
            }
        });
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma1 = tMa1.getText().toString().trim();
                String ma2 = tMa2.getText().toString().trim();
                FirebaseQuery.updateCodedk(ma1, ma2, new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(context, "Cập Nhật Mã Thành Công",

                                Toast.LENGTH_LONG).show();
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
