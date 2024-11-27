package com.example.appbtl;

public class NhomSanPham {
    private String ten;
    private String logoUrl;

    // Constructor không tham số
    public NhomSanPham() {}

    // Constructor với tham số
    public NhomSanPham(String ten, String logoUrl) {
        this.ten = ten;
        this.logoUrl = logoUrl;
    }

    // Getters và setters
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}

