package com.example.app_doc_truyen_nhom2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogXoaTruyen2;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.HienThiChuong;
import com.example.app_doc_truyen_nhom2.MainActivity;
import com.example.app_doc_truyen_nhom2.R;
import com.example.app_doc_truyen_nhom2.Review_Detail;
import com.example.app_doc_truyen_nhom2.Utils;

import java.util.List;

public class BookshelfAdapter extends RecyclerView.Adapter<BookshelfAdapter.ViewHolder> {
    private Context context;
    private static List<Truyen> listtruyen;

    public BookshelfAdapter(Context context, List<Truyen> listtruyen) {
        this.context = context;
        this.listtruyen = listtruyen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View shelfView = inflater.inflate(R.layout.item_bookshelf, parent, false);
        ViewHolder viewHolder = new ViewHolder(shelfView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookDatabase database = new BookDatabase(context);
        Truyen truyen = listtruyen.get(position);
        Truyen truyen2= database.getTruyenID(truyen.getIDtruyen());
        holder.imgTruyen.setImageBitmap(Utils.decode(truyen2.getAnh()));
        if(truyen.getTenTruyen().toString().length()>15){

        holder.tTentruyen.setText(truyen.getTenTruyen().substring(0, 15)+"...");

        }if(truyen.getTenTruyen().length()<=15){

            holder.tTentruyen.setText(truyen.getTenTruyen());

        }

        if(truyen2.getChuongdangdoc()>0){
            holder.tTientrinh.setText("Chương: " + truyen.getChuongdangdoc());
        DSchuong dSchuong = database.getChuongIDthutu(truyen.getIDtruyen(), truyen.getChuongdangdoc());
        holder.itemBookshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HienThiChuong.class);
                Bundle bundle = new Bundle();
                bundle.putLong("IDtruyen", dSchuong.getIdTruyen());
                bundle.putLong("thutuchuong", dSchuong.getThutuchuong());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });}
        if(truyen2.getChuongdangdoc()==0){
            holder.tTientrinh.setText("Chưa Đọc");
            DSchuong dSchuong = database.getChuongIDthutu(truyen.getIDtruyen(), truyen.getChuongdangdoc());
            holder.itemBookshelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Review_Detail.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("IDtruyen",truyen2.getIDtruyen());
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            });}
        holder.itemBookshelf.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogXoaTruyen2 dialogXoaTruyen2=new DialogXoaTruyen2(context, truyen.getIDtruyen(), context, new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
                        Intent intent=new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context,"Xóa Truyện Khỏi Bộ Nhớ Thành Công", Toast.LENGTH_LONG).show();
                    }
                });
                dialogXoaTruyen2.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listtruyen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTruyen;
        private TextView tTentruyen, tTientrinh;
        private LinearLayout itemBookshelf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.imageTruyen);
            tTentruyen = itemView.findViewById(R.id.txttentruyen);
            tTientrinh = itemView.findViewById(R.id.txttientrinh);
            itemBookshelf = itemView.findViewById(R.id.itemtusach);
        }
    }
}
