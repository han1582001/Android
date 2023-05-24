package com.example.app_doc_truyen_nhom2.Database;

public class TheLoai {
    private long idTheloai;
    private String tenTheloai;
    private String moTa;

    public TheLoai(long idTheloai, String tenTheloai, String moTa) {
        this.idTheloai = idTheloai;
        this.tenTheloai = tenTheloai;
        this.moTa = moTa;
    }
public TheLoai(){

}
    public TheLoai(String tenTheloai, String moTa) {
        this.tenTheloai = tenTheloai;
        this.moTa = moTa;
    }

    public long getIdTheloai() {
        return idTheloai;
    }

    public void setIdTheloai(long idTheloai) {
        this.idTheloai = idTheloai;
    }

    public String getTenTheloai() {
        return tenTheloai;
    }

    public void setTenTheloai(String tenTheloai) {
        this.tenTheloai = tenTheloai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
