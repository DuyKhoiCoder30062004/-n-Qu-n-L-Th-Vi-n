/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Date;

public class Nhanvien_DTO {
    private int manv;         // Mã nhân viên
    private String ho;        // Họ
    private String ten;       // Tên
    private String sdt;       // Số điện thoại
    private double luong;     // Lương
    private Date ngaysinh;    // Ngày sinh
    private String chucvu;    // Chức vụ

    // Constructor
    public Nhanvien_DTO(int manv, String ho, String ten, String sdt, double luong, Date ngaysinh, String chucvu) {
        this.manv = manv;
        this.ho = ho;
        this.ten = ten;
        this.sdt = sdt;
        this.luong = luong;
        this.ngaysinh = ngaysinh;
        this.chucvu = chucvu;
    }

    // Getter and Setter methods
    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
}
