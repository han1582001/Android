package com.example.app_doc_truyen_nhom2.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Adapter.dialogchuongAdapter;
import com.example.app_doc_truyen_nhom2.Interface.CompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.R;

import java.util.List;

public class DialogChonChuong extends Dialog {
private Context context1;
private long chuonghientai;
private List<DSchuong> dSchuongList;
private RecyclerView recyclerViewChuong;
private dialogchuongAdapter adapter;
private XoaTruyenThanhCongListener listener;
private int position;
    public DialogChonChuong(@NonNull Context context, Context context1,List<DSchuong> dSchuongList, long chuonghientai, int position, XoaTruyenThanhCongListener listener) {
        super(context);
        this.context1=context1;
        this.dSchuongList=dSchuongList;
        this.chuonghientai=chuonghientai;
        this.listener=listener;
        this.position=position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogmucluc);
        this.recyclerViewChuong=findViewById(R.id.recylermucluc);
        this.adapter=new dialogchuongAdapter(context1, dSchuongList, chuonghientai, new XoaTruyenThanhCongListener() {
            @Override
            public void onComplete() {
                dismiss();
                listener.onComplete();
            }
        }, new CompleteListener() {
            @Override
            public void onComplete() {
                dismiss();
            }
        });
        this.recyclerViewChuong.setAdapter(this.adapter);
        this.recyclerViewChuong.scrollToPosition(this.position);
        this.recyclerViewChuong.setLayoutManager(new LinearLayoutManager(context1));

    }
}
