package com.example.app_doc_truyen_nhom2.Database;

import android.os.Parcel;
import android.os.Parcelable;

public class DSchuong {
    private long idChuong;
    private long idTruyen;
    private long thutuchuong;
    private String tenChuong;
    private String nDchuong;

    public DSchuong(long idChuong, long idTruyen, long thutuchuong, String tenChuong, String nDchuong) {
        this.idChuong = idChuong;
        this.idTruyen = idTruyen;
        this.thutuchuong = thutuchuong;
        this.tenChuong = tenChuong;
        this.nDchuong = nDchuong;
    }

    public DSchuong(long idTruyen, long thutuchuong, String tenChuong, String nDchuong) {
        this.idTruyen = idTruyen;
        this.thutuchuong = thutuchuong;
        this.tenChuong = tenChuong;
        this.nDchuong = nDchuong;
    }

    public DSchuong() {
    }


    public long getIdChuong() {
        return idChuong;
    }

    public void setIdChuong(long idChuong) {
        this.idChuong = idChuong;
    }

    public long getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(long idTruyen) {
        this.idTruyen = idTruyen;
    }

    public long getThutuchuong() {
        return thutuchuong;
    }

    public void setThutuchuong(long thutuchuong) {
        this.thutuchuong = thutuchuong;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public String getnDchuong() {
        return nDchuong;
    }

    public void setnDchuong(String nDchuong) {
        this.nDchuong = nDchuong;
    }

}
