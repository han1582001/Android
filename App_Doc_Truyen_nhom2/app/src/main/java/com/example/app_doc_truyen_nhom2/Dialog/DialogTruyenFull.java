package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;

public class DialogTruyenFull extends Dialog {
public Context context;
private long idtruyen;
private int type;
private ImageButton btnOK, btnCANCEL;
private TextView txttitle;
    public DialogTruyenFull(@NonNull Context context, long idtruyen, int type) {
        super(context);
        this.context=context;
        this.idtruyen=idtruyen;
        this.type=type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogxacnhan);
        this.txttitle=findViewById(R.id.txttitle);
        this.btnOK=findViewById(R.id.btnOK);
        this.btnCANCEL=findViewById(R.id.btnCANCEL);
        this.btnCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if(this.type==0){
            txttitle.setText("Xác Nhận Truyện Này Đã Full?");
            this.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseQuery.truyenFull(idtruyen, context, new XoaTruyenThanhCongListener() {
                        @Override
                        public void onComplete() {
                            dismiss();
                        }
                    });
                }
            });
        }
        if(this.type==1){
            txttitle.setText("Còn Tiếp?");
            this.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseQuery.truyenDangra(idtruyen, context, new XoaTruyenThanhCongListener() {
                        @Override
                        public void onComplete() {
                            dismiss();
                        }
                    });
                }
            });
        }
    }
}
