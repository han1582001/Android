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

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;

public class DialogXoaTruyen3 extends Dialog {

    private Context context;
    private ImageButton btnOK,btnCANCEL;
    private TextView ttitle;
    private static long idtruyen;
    private XoaTruyenThanhCongListener listener;
    public DialogXoaTruyen3(@NonNull Context context, long idTruyen, Context context2, XoaTruyenThanhCongListener listener) {
        super(context);
        this.context=context2;
        this.idtruyen=idTruyen;
        this.listener=listener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diaglog_xacnhanxoa);
        this.ttitle=findViewById(R.id.txttitle);
        this.btnOK=(ImageButton) findViewById(R.id.btnOK);
        this.btnCANCEL=(ImageButton) findViewById(R.id.btnCANCEL);
        this.ttitle.setText("Xóa Truyện Này Khỏi Bộ Nhớ?");
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDatabase database2=new BookDatabase(context);
                database2.xoatruyenID(idtruyen);
                Toast.makeText(context,"Xóa Truyện Khỏi Bộ Nhớ Thành Công", Toast.LENGTH_LONG).show();
                dismiss();
                listener.onComplete();
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
