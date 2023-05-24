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
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.HienThiChuong;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DanhSachChuongAdapter extends RecyclerView.Adapter<DanhSachChuongAdapter.ViewHolder> {
private List<DSchuong> dSchuongList;
private Context context;
private static long idtruyen;

    public DanhSachChuongAdapter(Context context, List<DSchuong> dSchuongList, long idtruyen) {
        this.dSchuongList = dSchuongList;
        this.context = context;
        this.idtruyen=idtruyen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View storeView = inflater.inflate(R.layout.item_chuong, parent, false);
      ViewHolder viewHolder = new ViewHolder(storeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookDatabase database = new BookDatabase(context);
DSchuong chuong=dSchuongList.get(position);

holder.tTieude.setText("Chương "+chuong.getThutuchuong()+": "+chuong.getTenChuong());
Truyen truyen= database.getTruyenID(chuong.getIdTruyen());
if(chuong.getThutuchuong()<=truyen.getChuongdangdoc()){
    holder.tTieude.setTextColor(ContextCompat.getColor(context,R.color.xam));
}else {
    holder.tTieude.setTextColor(ContextCompat.getColor(context,R.color.black));
}

holder.viewItemchuong.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen()== chuong.getIdTruyen()){
                        database.insertOrUpdateChuong(dSchuong);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot truyensnapshot: snapshot.getChildren()){
                    Truyen truyen= truyensnapshot.getValue(Truyen.class);
                   if(truyen.getIDtruyen()==idtruyen){
                       database.insertOrUpdateTruyen(truyen);
                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.capnhatchuongdangdoc(chuong.getThutuchuong(), chuong.getIdTruyen());
        Intent intent = new Intent(context, HienThiChuong.class);
        Bundle bundle23 = new Bundle();
        bundle23.putLong("IDtruyen", chuong.getIdTruyen());
        bundle23.putLong("thutuchuong", chuong.getThutuchuong());
        intent.putExtras(bundle23);
        context.startActivity(intent);
    }
});

    }

    @Override
    public int getItemCount() {
        return dSchuongList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tTieude;
        private LinearLayout viewItemchuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTieude = itemView.findViewById(R.id.tieudechuong);
            viewItemchuong=itemView.findViewById(R.id.itemchuong);
        }

    }

}
