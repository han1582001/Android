package com.example.app_doc_truyen_nhom2.Database;

public class TaiKhoan {
    private String name;
    private int type;
    private String email;
    private String matkhau;

    public TaiKhoan(String name, int type, String email, String matkhau) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.matkhau = matkhau;
    }

    public TaiKhoan() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
