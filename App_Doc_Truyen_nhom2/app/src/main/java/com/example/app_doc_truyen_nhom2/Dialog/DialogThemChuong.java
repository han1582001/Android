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
import androidx.annotation.Nullable;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DialogThemChuong extends Dialog {
    public Context context;
    private EditText edtitle, ednd;
    private ImageButton btnOK, btnCANCEL;
    private static long idtruyen;
    private static List<DSchuong> dSchuongList;

    public DialogThemChuong(@NonNull Context context, long idtruyen, Context context2) {
        super(context);
        this.idtruyen=idtruyen;
        this.context=context2;
    }


    public DialogThemChuong(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogThemChuong(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogthemchuong);
        this.btnOK=(ImageButton) findViewById(R.id.btnOK);
        this.btnCANCEL=(ImageButton) findViewById(R.id.btnCANCEL);
        this.edtitle=(EditText) findViewById(R.id.txtTenChuong);
        this.ednd=(EditText) findViewById(R.id.txtndChuong);
        dSchuongList=new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dSchuongList=new ArrayList<>();
                for (DataSnapshot theloaisnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = theloaisnapshot.getValue(DSchuong.class);
                    if(dSchuong.getIdTruyen()==idtruyen){
                        dSchuongList.add(dSchuong);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        BookDatabase database2=new BookDatabase(context);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myChuong=database.getReference("DSchuong");



        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long idChuong= Calendar.getInstance().getTimeInMillis();
                String tenChuong= edtitle.getText().toString();
                String noidungChuong=ednd.getText().toString();
                myChuong.child(""+idChuong).child("idChuong").setValue(idChuong);
                myChuong.child(""+idChuong).child("tenChuong").setValue(tenChuong);
                myChuong.child(""+idChuong).child("thutuchuong").setValue(dSchuongList.size()+1);
                myChuong.child(""+idChuong).child("nDchuong").setValue(noidungChuong);
                myChuong.child(""+idChuong).child("idTruyen").setValue(idtruyen);
                BookDatabase database1=new BookDatabase(context);
                Toast.makeText(context,  "Thêm Chương Thành Công",

                        Toast.LENGTH_LONG).show();

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
