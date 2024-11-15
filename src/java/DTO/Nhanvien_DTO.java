package DTO;

import java.util.Date;

public class Nhanvien_DTO {
    private int maNV;
    private String ho;
    private String ten;
    private String soDT;
    private Date ngaySinh;
    private String chucVu;
    private double luong;

    // Constructor
    public Nhanvien_DTO(int maNV, String ho, String ten, String soDT, double luong, String chucVu, Date ngaySinh) {
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.soDT = soDT;
        this.luong = luong;
        this.chucVu = chucVu;
        this.ngaySinh = ngaySinh;
    }

    // Getters và Setters
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }
}

