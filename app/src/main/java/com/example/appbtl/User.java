package com.example.appbtl;

public class User {
    private int id;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    // Constructor với tham số
    public User(int id, String tenDangNhap, String matKhau, String vaiTro) {
        this.id = id;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    // Constructor mặc định (không có tham số)
    public User() {
        // Bạn có thể gán giá trị mặc định cho các trường nếu cần
        this.id = -1; // Ví dụ, ID mặc định là -1
        this.tenDangNhap = "";
        this.matKhau = "";
        this.vaiTro = "";
    }

    // Các getter và setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }


}

