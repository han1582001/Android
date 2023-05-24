package com.example.app_doc_truyen_nhom2.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.R;

import java.util.List;

import kotlin.jvm.internal.Lambda;

public class TaiKhoanAdapter extends RecyclerView.Adapter<TaiKhoanAdapter.ViewHolder> {
    private Context context;
private List<TaiKhoan> taiKhoanList;

    public TaiKhoanAdapter(Context context, List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.taiKhoanList = taiKhoanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View item=layoutInflater.inflate(R.layout.itemtaikhoan,parent,false);
        ViewHolder viewHolder=new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
TaiKhoan taiKhoan=taiKhoanList.get(position);
holder.tFullName.setText(taiKhoan.getName());
holder.tEmail.setText(taiKhoan.getEmail());
holder.btnxoa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});

    }

    @Override
    public int getItemCount() {
        return taiKhoanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
private TextView tFullName, tEmail;
private ImageButton btnxoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tFullName=itemView.findViewById(R.id.txtname);
            tEmail=itemView.findViewById(R.id.txtemail);
            btnxoa=itemView.findViewById(R.id.btnxoatk);
        }
    }
}
