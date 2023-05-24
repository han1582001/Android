package com.example.app_doc_truyen_nhom2.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class Truyen implements Parcelable {
private long IDtruyen;
private String tenTruyen;
private String tacGia;
private long theLoai;
private String moTa;
private long trangThai;
private String anh;
private long chuongdangdoc;

    public Truyen(long IDtruyen, String tenTruyen, String tacGia, long theLoai, String moTa, long trangThai, String anh, long chuongdangdoc) {
        this.IDtruyen = IDtruyen;
        this.tenTruyen = tenTruyen;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.anh = anh;
        this.chuongdangdoc=chuongdangdoc;
    }

    public Truyen(String tenTruyen, String tacGia, long theLoai, String moTa, long trangThai, String anh,long chuongdangdoc) {
        this.tenTruyen = tenTruyen;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.anh = anh;
        this.chuongdangdoc=chuongdangdoc;
    }

    public Truyen() {
    }

    protected Truyen(Parcel in) {
        IDtruyen = in.readLong();
        tenTruyen = in.readString();
        tacGia = in.readString();
        theLoai = in.readLong();
        moTa = in.readString();
        trangThai = in.readLong();
        anh = in.readString();
    }

    public long getChuongdangdoc() {
        return chuongdangdoc;
    }

    public void setChuongdangdoc(long chuongdangdoc) {
        this.chuongdangdoc = chuongdangdoc;
    }

    public long getIDtruyen() {
        return IDtruyen;
    }

    public void setIDtruyen(long IDtruyen) {
        this.IDtruyen = IDtruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public long getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(long theLoai) {
        this.theLoai = theLoai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public long getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(long trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public static Creator<Truyen> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Truyen> CREATOR = new Creator<Truyen>() {
        @Override
        public Truyen createFromParcel(Parcel in) {
            return new Truyen(in);
        }

        @Override
        public Truyen[] newArray(int size) {
            return new Truyen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(IDtruyen);
        parcel.writeString(tenTruyen);
        parcel.writeString(tacGia);
        parcel.writeLong(theLoai);
        parcel.writeString(moTa);
        parcel.writeLong(trangThai);
        parcel.writeString(anh);
    }
}
