package com.example.app_doc_truyen_nhom2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_doc_truyen_nhom2.R;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookDatabase extends SQLiteOpenHelper {
    Context context;
    private final static String DB_Name = "truyendata.db";
    private final static int DB_Version = 4;
    private final static String TruyenTABLE = "truyen";
    private final static String TacgiaTABLE = "tacgia";
    private final static String TheloaiTABLE = "theloai";
    private final static String TaikhoanTABLE = "taikhoan";
    private final static String DsChuongTABLE = "DsChuong";
    private final List<Truyen> listtryen = new ArrayList<>();
    final static String Create_tb_truyen = "create table truyen(IDtruyen integer primary key, Tentruyen text, Tacgia text, Theloai integer , Mota text, Trangthai integer, Anh text, Chuongdangdoc integer)";

    final static String Create_tb_theloai = "create table theloai(IDtheloai integer primary key, Tentheloai text, Mota text)";
    final static String Create_tb_DsChuong = "create table dsChuong(IDchuong integer primary key, IDtruyen integer, Thutuchuong integer, Tenchuong text, Noidung text)";


    public BookDatabase(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    public BookDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Create_tb_truyen);
        sqLiteDatabase.execSQL(Create_tb_theloai);
        sqLiteDatabase.execSQL(Create_tb_DsChuong);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists truyen");
        sqLiteDatabase.execSQL("Drop table if exists tacgia");
        sqLiteDatabase.execSQL("Drop table if exists theloai");
        sqLiteDatabase.execSQL("Drop table if exists DsChuong");
        sqLiteDatabase.execSQL("Drop table if exists Taikhoan");
        onCreate(sqLiteDatabase);
    }


    public void insertTruyendau(Truyen truyen) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDtruyen", truyen.getIDtruyen());
        contentValues.put("TenTruyen", truyen.getTenTruyen());
        contentValues.put("TacGia", truyen.getTacGia());
        contentValues.put("TheLoai", truyen.getTheLoai());
        contentValues.put("MoTa", truyen.getMoTa());
        contentValues.put("TrangThai", truyen.getTrangThai());
        contentValues.put("Anh", truyen.getAnh());
        contentValues.put("Chuongdangdoc", truyen.getChuongdangdoc());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("truyen", null, contentValues);
    }

    public void xoatruyen() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Delete from truyen");
    }
    public void xoaChuong() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Delete from dsChuong");
    }

    public void xoatruyenID(long idtruyen) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Delete from truyen where IDtruyen=" + idtruyen);
        database.execSQL("Delete from dsChuong where IDtruyen=" + idtruyen);
    }



    public void xoatheloai() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Delete from theloai");
    }

    public void xoaChuongID(long idchuong) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Delete from dsChuong where IDchuong=" + idchuong);
    }

    public void insertTheloaidau(TheLoai theLoai) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDtheloai", theLoai.getIdTheloai());
        contentValues.put("Tentheloai", theLoai.getTenTheloai());
        contentValues.put("Mota", theLoai.getMoTa());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("theloai", null, contentValues);
    }


    public void insertChuong(DSchuong dSchuong) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDchuong", dSchuong.getIdChuong());
        contentValues.put("IDtruyen", dSchuong.getIdTruyen());
        contentValues.put("Thutuchuong", dSchuong.getThutuchuong());
        contentValues.put("Tenchuong", dSchuong.getTenChuong());
        contentValues.put("Noidung", dSchuong.getnDchuong());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("dsChuong", null, contentValues);
    }

    public List<DSchuong> getChuong(long idtruyen) {
        String query = "Select * from dsChuong where IDtruyen=" + idtruyen+" order by Thutuchuong ASC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<DSchuong> listchuong = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            DSchuong dSchuong = new DSchuong(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4));
            listchuong.add(dSchuong);
            cursor.moveToNext();
        }
        return listchuong;
    } public List<DSchuong> getChuongMoitruoc(long idtruyen) {
        String query = "Select * from dsChuong where IDtruyen=" + idtruyen+" order by Thutuchuong DESC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<DSchuong> listchuong = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            DSchuong dSchuong = new DSchuong(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4));
            listchuong.add(dSchuong);
            cursor.moveToNext();
        }
        return listchuong;
    }

    public long getDemChuong(long idtruyen) {
        String query = "Select count(*) from dsChuong where IDtruyen=" + idtruyen;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }
    public int getDemChuong2(long idtruyen) {
        String query = "Select count(*) from dsChuong where IDtruyen=" + idtruyen;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public long getChuongcuoi(long idtruyen) {
        String query = "Select MAX(Thutuchuong) from dsChuong where IDtruyen=" + idtruyen;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }

    public List<Truyen> getAllTruyen() {
        String query = "Select * from truyen";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<Truyen> truyenList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            truyenList.add(truyen);
            cursor.moveToNext();
        }
        return truyenList;
    }  public List<Truyen> SearchTruyen(String search) {
        String query = "Select * from truyen where TenTruyen Like '%"+search+"%' or TacGia Like '%"+search+"%'  order by Chuongdangdoc DESC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<Truyen> truyenList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            truyenList.add(truyen);
            cursor.moveToNext();
        }
        return truyenList;
    }
    public List<Truyen> getTruyenDangDoc() {
        String query = "Select * from truyen order by Chuongdangdoc DESC";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<Truyen> truyenList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            truyenList.add(truyen);
            cursor.moveToNext();
        }
        return truyenList;
    }

    public List<TheLoai> getAllTheloai() {
        String query = "Select * from theloai";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<TheLoai> theLoaiList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            TheLoai theLoai = new TheLoai(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            theLoaiList.add(theLoai);
            cursor.moveToNext();
        }
        return theLoaiList;
    }


    public DSchuong getChuongIDthutu(long id, long thutu) {
        String query = "Select * from dsChuong where IDtruyen=" + id + " and Thutuchuong=" + thutu;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            DSchuong dSchuong = new DSchuong(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3), cursor.getString(4));
            return dSchuong;
        }
        return null;
    }

    public Truyen getTruyenID(long id) {
        String query = "Select * from truyen where IDtruyen=" + id;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            return truyen;
        }
        return null;
    }
    public DSchuong getChuongtheoID(long idchuong) {
        String query = "Select * from dsChuong where IDchuong=" + idchuong;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            DSchuong dSchuong = new DSchuong(cursor.getLong(0),cursor.getLong(1),cursor.getLong(2),cursor.getString(3),cursor.getString(4));
            return dSchuong;
        }
        return null;
    }


    public String getTenTheLoaiID(long id) {
        String query = "Select * from theloai where IDtheloai=" + id;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            TheLoai theLoai = new TheLoai(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            return theLoai.getTenTheloai();
        }
        return null;
    }
    public TheLoai getTenTheLoaiIDx(long id) {
        String query = "Select * from theloai where IDtheloai=" + id;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            TheLoai theLoai = new TheLoai(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            return theLoai;
        }
        return null;
    }

    public String getSoChuong(long idtruyen) {
        String query = "Select count(*) from dsChuong where IDtruyen=" + idtruyen;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return "0";
    }

    public List<Truyen> getdtTruyenTheLoai(long idtheloai, long soluong) {
        String query = "Select  * from truyen where TheLoai=" + idtheloai + " LIMIT " + soluong;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<Truyen> truyenList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            truyenList.add(truyen);
            cursor.moveToNext();
        }
        return truyenList;
    }

    public long getSoTruyenTheLoai(long idtheloai) {
        String query = "Select count(*) from truyen where Theloai=" + idtheloai;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }


    public List<Truyen> getdtTruyenTacGia(String tentacgia, long soluong) {
        String query = "Select  * from truyen where Tacgia= '" + tentacgia + "' LIMIT " + soluong;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        List<Truyen> truyenList = new ArrayList<>();

        while (cursor.isAfterLast() == false) {
            Truyen truyen = new Truyen(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getLong(3), cursor.getString(4), cursor.getLong(5), cursor.getString(6), cursor.getLong(7));
            truyenList.add(truyen);
            cursor.moveToNext();
        }
        return truyenList;
    }

    private boolean isChuongExist(DSchuong dSchuong) {
        String query = "Select count(*) from dsChuong where IDchuong=" + dSchuong.getIdChuong();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }
    private boolean isChuongExist2(long idtruyen, long thutuchuong) {
        String query = "Select count(*) from dsChuong where IDtruyen=" +idtruyen+" and Thutuchuong="+thutuchuong;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }
    public void capnhatchuongdangdoc(long thutuchuong, long idtruyen) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update truyen set Chuongdangdoc=" + thutuchuong + " where IDtruyen=" + idtruyen);
    }
public void updatetruyenFull(long idtruyen){
    if(isTruyenExistx(idtruyen)) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update truyen set Trangthai= 1 where IDtruyen=" + idtruyen);
    }
}
    public void updatetruyenDangra(long idtruyen){
        if(isTruyenExistx(idtruyen)) {
            SQLiteDatabase database = this.getWritableDatabase();
            database.execSQL("Update truyen set Trangthai= 0 where IDtruyen=" + idtruyen);
        }
        }
    private void updateChuong(DSchuong dSchuong) {
        CharSequence nd=dSchuong.getnDchuong();
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update dsChuong set IDtruyen=" + dSchuong.getIdTruyen() + ", Thutuchuong=" + dSchuong.getThutuchuong() + ", Tenchuong='" + dSchuong.getTenChuong() + "', Noidung= '" + nd + "' where IDchuong=" + dSchuong.getIdChuong());
    }

    public void insertOrUpdateChuong(DSchuong dSchuong) {
        if (isChuongExist(dSchuong)) {
            updateChuong(dSchuong);
        } else {
            insertChuong(dSchuong);
        }

    }
    public void insertOrUpdateChuong2(DSchuong dSchuong) {
        if (isTruyenExistx(dSchuong.getIdTruyen())){
            if(isChuongExist(dSchuong)){
            updateChuong(dSchuong);
            }else {
                insertChuong(dSchuong);
            }
        }

    }

    private boolean isTruyenExist(Truyen truyen) {
        String query = "Select count(*) from truyen where IDtruyen=" + truyen.getIDtruyen();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }
    public boolean isTruyenExistx(long idtruyen2) {
        String query = "Select count(*) from truyen where IDtruyen=" +idtruyen2;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }
   public boolean isTruyenExist2(Truyen truyen) {
        String query = "Select count(*) from truyen where IDtruyen=" + truyen.getIDtruyen();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }
    private void updateTruyen(Truyen truyen) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update truyen set TenTruyen='" + truyen.getTenTruyen() + "', TacGia='" + truyen.getTacGia() + "',TheLoai=" + truyen.getTheLoai() + ",MoTa='" + truyen.getMoTa() + "',Anh='" + truyen.getAnh() + "',TrangThai='" + truyen.getTheLoai() + "' where IDtruyen=" + truyen.getIDtruyen());
    }

    public void insertOrUpdateTruyen(Truyen truyen) {
        if (isTruyenExist(truyen)) {
            updateTruyen(truyen);
        } else {
            insertTruyendau(truyen);
        }

    }
public Cursor GetTruyen(){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery("Select * from truyen", null);
}
    private boolean isTheloaiExist(TheLoai theLoai) {
        String query = "Select count(*) from theloai where IDtheloai=" + theLoai.getIdTheloai();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        int count = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count > 0;
    }

    private void updateTheloai(TheLoai theLoai) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("Update theloai set Tentheloai='" + theLoai.getTenTheloai() + "', Mota='" + theLoai.getMoTa() + "' where IDtheloai=" + theLoai.getIdTheloai());
    }


    public void insertOrUpdateTheloai(TheLoai theLoai) {
        if (isTheloaiExist(theLoai)) {
            updateTheloai(theLoai);
        } else {
            insertTheloaidau(theLoai);
        }

    }

    public long getSoTruyenTacGia(String tacgia) {
        String query = "Select count(*) from truyen where Tacgia='" + tacgia + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }

    public long Demtruyen(long idtruyen) {
        String query = "Select count(*) from truyen where IDtruyen=" + idtruyen;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }


    public long Theloai(long idtheloai) {
        String query = "Select count(*) from theloai where IDtheloai=" + idtheloai;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }


    public long demTheloai() {
        String query = "Select count(*) from" + TheloaiTABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }

    public long demTruyen() {
        String query = "Select count(*) from" + TruyenTABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }
}
