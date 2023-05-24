package com.example.app_doc_truyen_nhom2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogTruyenFull;
import com.example.app_doc_truyen_nhom2.Dialog.DialogXoaTruyen;
import com.example.app_doc_truyen_nhom2.QuanlychuongActivity;
import com.example.app_doc_truyen_nhom2.R;
import com.example.app_doc_truyen_nhom2.SuaTruyenActivity;
import com.example.app_doc_truyen_nhom2.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class QuanLyTruyenAdapter extends RecyclerView.Adapter<QuanLyTruyenAdapter.ViewHolder> {
    private Context context;
    private List<Truyen> listtruyen;
    private static List<DSchuong> dSchuongList;

    public QuanLyTruyenAdapter(Context context, List<Truyen> listtruyen) {

        this.context = context;
        this.listtruyen = listtruyen;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View storeView = inflater.inflate(R.layout.itemquanlytruyen, parent, false);
        QuanLyTruyenAdapter.ViewHolder viewHolder = new QuanLyTruyenAdapter.ViewHolder(storeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookDatabase database = new BookDatabase(context);
        dSchuongList = new ArrayList<>();
        Truyen truyen = listtruyen.get(position);
        holder.tTenTruyen.setText(truyen.getTenTruyen());
        holder.tTacGia.setText(truyen.getTacGia());
        holder.tTheLoai.setText(database.getTenTheLoaiID(truyen.getTheLoai()));
        if (truyen.getMoTa().length() <= 150) {
            holder.tMoTa.setText(truyen.getMoTa());
        }
        if (truyen.getMoTa().length() > 150) {
            holder.tMoTa.setText(truyen.getMoTa().substring(0, 150) + "...");
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dSchuongList.clear();
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen() == truyen.getIDtruyen()) {
                        dSchuongList.add(dSchuong);
                        holder.tSoChuong.setText("" + dSchuongList.size());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (truyen.getTrangThai() == 0) {
            holder.tTinhTrang.setText("Đang ra");
            holder.btncheckfull.setVisibility(View.VISIBLE);
            holder.btncheckfull.setEnabled(true);
            holder.btncheckdangra.setVisibility(View.GONE);
            holder.btncheckdangra.setEnabled(false);

        } else {
            holder.tTinhTrang.setText("Đã Full");
            holder.btncheckfull.setVisibility(View.GONE);
            holder.btncheckfull.setEnabled(false);
            holder.btncheckdangra.setVisibility(View.VISIBLE);
            holder.btncheckdangra.setEnabled(true);
        }
        holder.btncheckdangra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogTruyenFull dialogTruyenFull = new DialogTruyenFull(view.getContext(), truyen.getIDtruyen(), 1);
                dialogTruyenFull.show();
            }
        });
        holder.btncheckfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogTruyenFull dialogTruyenFull = new DialogTruyenFull(view.getContext(), truyen.getIDtruyen(), 0);
                dialogTruyenFull.show();
            }
        });
        holder.tSoChuong.setText(database.getSoChuong(truyen.getIDtruyen()));
        holder.iAnhbia.setImageBitmap(Utils.decode(truyen.getAnh()));
        holder.btnchuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (truyen.getTrangThai() == 0) {
                    Intent intent = new Intent(context, QuanlychuongActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("IDtruyen", truyen.getIDtruyen());

                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Truyện Đã Hoàn Không Thể Chỉnh Sửa", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogXoaTruyen dialogXoaTruyen = new DialogXoaTruyen(view.getContext(), truyen.getIDtruyen(), view.getContext());
                dialogXoaTruyen.show();
            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (truyen.getTrangThai() == 0) {
                    Intent intent = new Intent(context, SuaTruyenActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("IDtruyen", truyen.getIDtruyen());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Truyện Đã Hoàn Không Thể Chỉnh Sửa", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listtruyen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tTenTruyen;
        private TextView tTacGia;
        private TextView tTheLoai;
        private TextView tMoTa;
        private TextView tTinhTrang;
        private TextView tSoChuong;
        private ImageView iAnhbia;
        private RelativeLayout itemQuanLyTruyen;
        private ImageButton btnsua, btnxoa, btnchuong, btncheckfull, btncheckdangra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTenTruyen = itemView.findViewById(R.id.txttentruyen);
            tTacGia = itemView.findViewById(R.id.txttacgia2);
            tTheLoai = itemView.findViewById(R.id.txttheloai2);
            tMoTa = itemView.findViewById(R.id.txtreview);
            tTinhTrang = itemView.findViewById(R.id.txttinhtrang);
            tSoChuong = itemView.findViewById(R.id.txtsochuong2);
            iAnhbia = itemView.findViewById(R.id.imgtruyen);
            btnsua = itemView.findViewById(R.id.btnupdatetruyen);
            btnxoa = itemView.findViewById(R.id.btnremove);
            btnchuong = itemView.findViewById(R.id.btnexspand);
            btncheckfull = itemView.findViewById(R.id.checkfull);
            btncheckdangra = itemView.findViewById(R.id.checkdangra);
            itemQuanLyTruyen = itemView.findViewById(R.id.item_quanlytruyen);
        }

        public void bindData(Truyen truyen) {

        }
    }

}
