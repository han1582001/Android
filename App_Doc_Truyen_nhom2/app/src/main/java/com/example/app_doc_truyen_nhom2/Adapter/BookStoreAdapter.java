package com.example.app_doc_truyen_nhom2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogXoaTruyen3;
import com.example.app_doc_truyen_nhom2.Interface.OnDemSoChuongCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.OnLoadDataCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.R;
import com.example.app_doc_truyen_nhom2.Review_Detail;
import com.example.app_doc_truyen_nhom2.Utils;

import java.util.ArrayList;
import java.util.List;

public class BookStoreAdapter extends RecyclerView.Adapter<BookStoreAdapter.ViewHolder> {
    private Context context;
    private List<Truyen> listtruyen;
    private static List<DSchuong> dSchuongList;

    private static BookDatabase database;
    private final int VIEW_TYPE_ITEM=0;
    private final int VIEW_TYPE_LOADING=1;

    public BookStoreAdapter(Context context, List<Truyen> listtruyen) {
        this.context = context;
        this.listtruyen=listtruyen;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View storeView = inflater.inflate(R.layout.book_store_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(storeView);
            return viewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Truyen truyen2=listtruyen.get(position);
holder.bindData(truyen2);
        holder.itemBookstore.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogXoaTruyen3 dialogXoaTruyen3 = new DialogXoaTruyen3(context, truyen2.getIDtruyen(), context, new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
holder.bindData(truyen2);
                    }
                });
                dialogXoaTruyen3.show();
                return true;
            }
        });
holder.btndownload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseQuery.loadTruyenVaChuong(context, truyen2.getIDtruyen(), new OnLoadDataCompleteListener() {
            @Override
            public void onComplete(long idtruyen) {
                holder.bindData(truyen2);
            }
        });
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
        private RelativeLayout itemBookstore;
        private TextView tprogress;
        private ImageButton btndownload;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTenTruyen = itemView.findViewById(R.id.txttentruyen);
            tTacGia = itemView.findViewById(R.id.txttacgia2);
            tTheLoai = itemView.findViewById(R.id.txttheloai2);
            tMoTa = itemView.findViewById(R.id.txtreview);
            tTinhTrang = itemView.findViewById(R.id.txttinhtrang);
            tSoChuong = itemView.findViewById(R.id.txtsochuong2);
            iAnhbia = itemView.findViewById(R.id.imgtruyen);
            itemBookstore = itemView.findViewById(R.id.item_bookstore);
            progressBar = itemView.findViewById(R.id.progesstaitruyen);
            tprogress = itemView.findViewById(R.id.txtprogess);
            btndownload = itemView.findViewById(R.id.btndowloadtruyen);
        }
        public void bindData(Truyen truyen){
            database = new BookDatabase(context);
            dSchuongList = new ArrayList<>();
            tTenTruyen.setText(truyen.getTenTruyen());
            tTacGia.setText(truyen.getTacGia());
           tTheLoai.setText(database.getTenTheLoaiID(truyen.getTheLoai()));
            if (truyen.getMoTa().length() <= 150) {
               tMoTa.setText(truyen.getMoTa());
            }
            if (truyen.getMoTa().length() > 150) {
                tMoTa.setText(truyen.getMoTa().substring(0, 150) + "...");
            }
            tprogress.setVisibility(View.VISIBLE);
            FirebaseQuery.loadChuong(truyen.getIDtruyen(), new OnDemSoChuongCompleteListener() {
                @Override
                public void onComplete(int sochuong) {
                    tSoChuong.setText(""+sochuong);
                }
            });

            if (truyen.getTrangThai() == 0) {
               tTinhTrang.setText("Đang ra");
               tTinhTrang.setTextColor(ContextCompat.getColor(context,R.color.xanhduong));
            } else {
               tTinhTrang.setText("Đã Full");
                tTinhTrang.setTextColor(ContextCompat.getColor(context,R.color.xanhla));
            }
            iAnhbia.setImageResource(R.drawable.backgroundtutruyen2);

           iAnhbia.setImageBitmap(Utils.decode(truyen.getAnh()));
            if (database.isTruyenExist2(truyen) == true) {
                progressBar.setVisibility(View.GONE);
                btndownload.setVisibility(View.GONE);
                tprogress.setVisibility(View.VISIBLE);
               itemBookstore.setEnabled(true);
                Truyen truyen3 = database.getTruyenID(truyen.getIDtruyen());
                if (truyen3.getChuongdangdoc() == 0) {
                    tprogress.setText("Chưa đọc");
                }
                if (truyen3.getChuongdangdoc() > 0) {
                    tprogress.setText("Đang đọc chương " + truyen3.getChuongdangdoc());
                }

                itemBookstore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Truyen truyen2 = database.getTruyenID(truyen.getIDtruyen());
                        Intent intent = new Intent(context, Review_Detail.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("IDtruyen", truyen2.getIDtruyen());
                        intent.putExtras(bundle);
                        context.startActivity(intent);


                    }
                });

            }
            if (database.isTruyenExist2(truyen) == false) {
                tprogress.setVisibility(View.GONE);
               progressBar.setVisibility(View.GONE);
                btndownload.setVisibility(View.VISIBLE);
                itemBookstore.setEnabled(false);
            }


        }
    }

}
