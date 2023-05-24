package com.example.app_doc_truyen_nhom2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Dialog.DialogSuaChuong;
import com.example.app_doc_truyen_nhom2.Dialog.DialogXoaChuong;
import com.example.app_doc_truyen_nhom2.R;

import java.util.List;

public class QuanlychuongAdapter extends RecyclerView.Adapter<QuanlychuongAdapter.ViewHolder> {
private Context context;
private static List<DSchuong>  dSchuongList;

    public QuanlychuongAdapter(Context context, List<DSchuong> dSchuongList) {
        this.context = context;
        this.dSchuongList = dSchuongList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View quanlychuongView= inflater.inflate(R.layout.quanlychuongitem, parent,false);
        QuanlychuongAdapter.ViewHolder viewHolder=new QuanlychuongAdapter.ViewHolder(quanlychuongView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
DSchuong chuong= dSchuongList.get(position);
        long chuongMax= dSchuongList.size();
holder.tChuongTitle.setText("Chương"+chuong.getThutuchuong()+": "+chuong.getTenChuong());
if(chuong.getThutuchuong()==chuongMax){
    holder.btnxoachuong.setVisibility(View.VISIBLE);
    holder.btnupdatechuong.setVisibility(View.VISIBLE);
holder.btnxoachuong.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final DialogXoaChuong dialogXoaChuong=new DialogXoaChuong(view.getContext(), chuong.getIdChuong(),chuong.getIdTruyen(),view.getContext());
        dialogXoaChuong.show();
    }
});
}
if(chuong.getThutuchuong()!=chuongMax) {
    holder.btnxoachuong.setVisibility(View.GONE);
    holder.btnupdatechuong.setVisibility(View.VISIBLE);
}
        holder.btnupdatechuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogSuaChuong dialogSuachuong=new DialogSuaChuong(view.getContext(),chuong, view.getContext());
                dialogSuachuong.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dSchuongList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tChuongTitle;
        private ImageButton btnupdatechuong, btnxoachuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tChuongTitle=itemView.findViewById(R.id.txtTitlechuong);
            btnupdatechuong=itemView.findViewById(R.id.btnsuachuong);
            btnxoachuong=itemView.findViewById(R.id.btnxoachuong);
        }
    }


}
