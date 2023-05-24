package com.example.app_doc_truyen_nhom2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Interface.CompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.HienThiChuong;
import com.example.app_doc_truyen_nhom2.R;

import java.util.List;

public class dialogchuongAdapter extends RecyclerView.Adapter<dialogchuongAdapter.ViewHolder> {
private Context context;
private List<DSchuong> dSchuongList;
private XoaTruyenThanhCongListener listener;
private CompleteListener completeListener;
   private long chuonghientai;

    public dialogchuongAdapter(Context context, List<DSchuong> dSchuongList, long chuonghientai, XoaTruyenThanhCongListener listener, CompleteListener completeListener) {
        this.context = context;
        this.dSchuongList = dSchuongList;
        this.listener = listener;
        this.chuonghientai = chuonghientai;
        this.completeListener=completeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.itemdialogchuong, parent, false);
        ViewHolder viewHolder= new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
DSchuong dSchuong=dSchuongList.get(position);
holder.tTitle.setText("Chương "+dSchuong.getThutuchuong()+": "+dSchuong.getTenChuong());
if(dSchuong.getThutuchuong()==chuonghientai){
    holder.lnitem.setBackgroundColor(ContextCompat.getColor(context,R.color.maudo));

}
holder.lnitem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(dSchuong.getThutuchuong()!=chuonghientai){
        BookDatabase database=new BookDatabase(context);
        database.capnhatchuongdangdoc(dSchuong.getThutuchuong(), dSchuong.getIdTruyen());
        Intent intent=new Intent(context, HienThiChuong.class);
        Bundle bundle2 = new Bundle();
        bundle2.putLong("IDtruyen", dSchuong.getIdTruyen());
        bundle2.putLong("thutuchuong", dSchuong.getThutuchuong());
        intent.putExtras(bundle2);
        context.startActivity(intent);
        listener.onComplete();
        }
        if(dSchuong.getThutuchuong()==chuonghientai){
            completeListener.onComplete();
        }
    }
});

    }

    @Override
    public int getItemCount() {
        return dSchuongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
private TextView tTitle;
private LinearLayout lnitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTitle=itemView.findViewById(R.id.txttitle);
            lnitem=itemView.findViewById(R.id.itemdialogchuong);
        }
    }
}
