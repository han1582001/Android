package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.R;

public class DialogXoaTk extends Dialog {
private Context context;
private String idtaiKhoan;
private ImageButton btnOK, btnCANCEL;
    public DialogXoaTk(@NonNull Context context, String idtaiKhoan) {

        super(context);
        this.context=context;
        this.idtaiKhoan=idtaiKhoan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diaglog_xacnhanxoa);
        btnOK=findViewById(R.id.btnOK);
        btnCANCEL=findViewById(R.id.btnCANCEL);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseQuery.XoaTaiKhoan();
            }
        });

    }
}
