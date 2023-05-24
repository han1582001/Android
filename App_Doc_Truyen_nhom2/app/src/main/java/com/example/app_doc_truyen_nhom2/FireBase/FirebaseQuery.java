package com.example.app_doc_truyen_nhom2.FireBase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.TaiKhoan;
import com.example.app_doc_truyen_nhom2.Database.TheLoai;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Database.codeDK;
import com.example.app_doc_truyen_nhom2.Interface.GetCodeCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.OnDemSoChuongCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.OnLoadDataCompleteListener;
import com.example.app_doc_truyen_nhom2.Interface.ThemchuongListener;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.Interface.checkcode;
import com.example.app_doc_truyen_nhom2.Interface.getStringCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseQuery {
    private static List<DSchuong> dSchuongList = new ArrayList<>();

    public static void loadTheLoai(Context context) {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("theloai");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot theloaisnapshot : snapshot.getChildren()) {
                    BookDatabase database = new BookDatabase(context);
                    TheLoai theLoai = theloaisnapshot.getValue(TheLoai.class);
                    database.insertOrUpdateTheloai(theLoai);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public static int demchuong(long idtruyen) {

        List<DSchuong> dSchuongList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dSchuongList.clear();
                for (DataSnapshot theloaisnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = theloaisnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen() == idtruyen) {
                        dSchuongList.add(dSchuong);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return dSchuongList.size();
    }

    public static void loadTruyen(Context context, long idtruyen) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot truyensnapshot : snapshot.getChildren()) {
                    BookDatabase database = new BookDatabase(context);
                    Truyen truyen = truyensnapshot.getValue(Truyen.class);
                    if (truyen.getIDtruyen() == idtruyen) {
                        database.insertOrUpdateTruyen(truyen);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void loadChuong(long idtruyen, OnDemSoChuongCompleteListener listener) {
        final List<DSchuong> dSchuongList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dSchuongList.clear();
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen() == idtruyen) {
                        dSchuongList.add(dSchuong);
                    }
                }
                listener.onComplete(dSchuongList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void themChuongX(Context context) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    BookDatabase database=new BookDatabase(context);
                    database.insertOrUpdateChuong2(dSchuong);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static void getcodedk(GetCodeCompleteListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("codeDK");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                codeDK codeDK1 = snapshot.getValue(codeDK.class);
                listener.onComplete(codeDK1.getCode1(), codeDK1.getCode2());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void getTen(getStringCompleteListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("taikhoan").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                listener.onComplete(taiKhoan.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void checkLoaiTk(checkcode checkcode) {
        FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("taikhoan");
        reference2.child(user2.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaiKhoan taiKhoan = snapshot.getValue(TaiKhoan.class);
                if (taiKhoan.getType() == 0) {
                    checkcode.onComplete(true);
                }
                if (taiKhoan.getType() != 0) {
                    checkcode.onComplete(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void demchuongmoi(Context context, long idtruyen, ThemchuongListener listener) {
        final List<DSchuong> dSchuongList = new ArrayList<>();
        BookDatabase database = new BookDatabase(context);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    DSchuong dSchuong = snapshot1.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen() == idtruyen) {
                        dSchuongList.add(dSchuong);
                    }
                }
                if (database.isTruyenExistx(idtruyen)) {
                    listener.onComplete(dSchuongList.size() - database.getDemChuong2(idtruyen));
                } else {
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void DoiMK(String mkmoi, XoaTruyenThanhCongListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(mkmoi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("taikhoan").child(user.getUid());
                    listener.onComplete();
                    databaseReference.child("matkhau").setValue(mkmoi);
                }
            }
        });


    }
    public static void XoaTaiKhoan(){

    }
    public static void updateCodedk(String code1, String code2, XoaTruyenThanhCongListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("codeDK");
        databaseReference.child("code1").setValue(code1);
        databaseReference.child("code2").setValue(code2);
        listener.onComplete();
    }
public static void truyenFull(long idtruyen, Context context, XoaTruyenThanhCongListener listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference.child(""+idtruyen).child("trangThai").setValue(1);
        BookDatabase bookDatabase=new BookDatabase(context);
        bookDatabase.updatetruyenFull(idtruyen);
        listener.onComplete();
}
    public static void truyenDangra(long idtruyen, Context context, XoaTruyenThanhCongListener listener){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference.child(""+idtruyen).child("trangThai").setValue(0);
        BookDatabase bookDatabase=new BookDatabase(context);
        bookDatabase.updatetruyenDangra(idtruyen);
        listener.onComplete();
    }
    public static void checkcodedk(String codea, String codeb, checkcode listener) {
        final boolean[] checkcode = {false};
        final List<DSchuong> dSchuongList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("codeDK");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                codeDK codeDK1 = snapshot.getValue(codeDK.class);
                if (codea.equals(codeDK1.getCode1()) && codeb.equals(codeDK1.getCode2())) {
                    checkcode[0] = true;
                }
                listener.onComplete(checkcode[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void loadTruyenVaChuong(Context context, long idtruyen, OnLoadDataCompleteListener listener) {
        final boolean[] loadedTruyen = {false};
        final boolean[] loadedChuong = {false};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot truyensnapshot : snapshot.getChildren()) {
                    BookDatabase database = new BookDatabase(context);
                    Truyen truyen = truyensnapshot.getValue(Truyen.class);
                    if (truyen.getIDtruyen() == idtruyen) {
                        database.insertOrUpdateTruyen(truyen);

                    }
                }
                loadedTruyen[0] = true;
                if (loadedChuong[0]) {
                    listener.onComplete(idtruyen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    BookDatabase database = new BookDatabase(context);
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen() == idtruyen) {
                        database.insertOrUpdateChuong(dSchuong);
                    }
                }
                loadedChuong[0] = true;
                if (loadedTruyen[0]) {
                    listener.onComplete(idtruyen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public static Query get(String key){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("truyen");
        if(key ==null){
 return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);

    }
}
