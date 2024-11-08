/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

public class Nhanvien_DTO {
    private int maNV;         // Mã nhân viên
    private String ho;        // Họ
    private String ten;       // Tên
    private String soDT;      // Số điện thoại
    private String chucVu;    // Chức vụ
    private double luong;     // Lương
    private Date ngaySinh;    // Ngày sinh

    // Constructor
    public Nhanvien_DTO(int maNV, String ho, String ten, String soDT, String chucVu, double luong, Date ngaySinh) {
        this.maNV = maNV;
        this.ho = ho;
        this.ten = ten;
        this.soDT = soDT;
        this.chucVu = chucVu;
        this.luong = luong;
        this.ngaySinh = ngaySinh;
    }

    // Getter and Setter methods
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
