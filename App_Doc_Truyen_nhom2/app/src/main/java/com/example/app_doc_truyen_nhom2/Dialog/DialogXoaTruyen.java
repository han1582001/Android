package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DialogXoaTruyen extends Dialog {
    public Context context;

    private ImageButton btnOK, btnCANCEL;

    private static long idtruyen;
    public DialogXoaTruyen(@NonNull Context context, long idTruyen, Context context2 ) {
        super(context);
        this.context=context2;
        this.idtruyen=idTruyen;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.diaglog_xacnhanxoa);
        this.btnOK=(ImageButton) findViewById(R.id.btnOK);
        this.btnCANCEL=(ImageButton) findViewById(R.id.btnCANCEL);
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDatabase database2=new BookDatabase(context);
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myChuong=database.getReference("truyen");
                myChuong.child(""+idtruyen).removeValue();
                database2.xoatruyenID(idtruyen);
                Toast.makeText(context,"Xóa Truyện Thành Công", Toast.LENGTH_LONG).show();

               
                dismiss();
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
