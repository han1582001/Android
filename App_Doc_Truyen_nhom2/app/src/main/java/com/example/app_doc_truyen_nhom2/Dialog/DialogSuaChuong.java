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
import com.example.app_doc_truyen_nhom2.Adapter.QuanlychuongAdapter;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DialogSuaChuong extends Dialog {
    public Context context;
    private EditText edtitle, ednd;
    private ImageButton btnOK, btnCANCEL;

    private List<DSchuong> dSchuongList;
    private DSchuong chuong;
    public DialogSuaChuong(@NonNull Context context,DSchuong chuong, Context context2) {
        super(context);
        this.chuong=chuong;
        this.context=context2;
    }

    public DialogSuaChuong(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogSuaChuong(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
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
        this.edtitle.setText(chuong.getTenChuong());
        this.ednd.setText(chuong.getnDchuong());
        this.btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookDatabase database2=new BookDatabase(context);
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                DatabaseReference myChuong=database.getReference("DSchuong");
                long idchuong= chuong.getIdChuong();
                String tenChuong= edtitle.getText().toString();
                String noidungChuong=ednd.getText().toString();
                long truyen=chuong.getIdTruyen();
                long thutuchuongx = chuong.getThutuchuong();
                if(tenChuong.isEmpty()){
                    edtitle.setError("Không Được Để Trống");
                    edtitle.requestFocus();
                    return;
                }
                if(noidungChuong.isEmpty()){
                    ednd.setError("Không Được Để Trống");
                    ednd.requestFocus();
                    return;
                }
                myChuong.child(""+idchuong).child("idChuong").setValue(idchuong);
                myChuong.child(""+idchuong).child("tenChuong").setValue(tenChuong);
                myChuong.child(""+idchuong).child("thutuchuong").setValue(thutuchuongx);
                myChuong.child(""+idchuong).child("nDchuong").setValue(noidungChuong);
                myChuong.child(""+idchuong).child("idTruyen").setValue(truyen);

                new QuanlychuongAdapter(context, database2.getChuong(chuong.getIdTruyen()));
                Toast.makeText(context,  "Cập Nhật Chương Thành Công",

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
