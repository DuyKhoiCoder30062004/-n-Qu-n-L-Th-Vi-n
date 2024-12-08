/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author HP
 */
public class DocGia_DTO {
    private int maDG;
    private String hoDG;
    private String tenDG;
    private String diaChi;
    private String soDienThoai;
    private LocalDate ngaySinh;

    // Constructor không tham số
    public DocGia_DTO() {}

    // Constructor có tham số
    public DocGia_DTO(int maDG, String hoDG, String tenDG, String diaChi, String soDienThoai, LocalDate ngaySinh) {
        this.maDG = maDG;
        this.hoDG = hoDG;
        this.tenDG = tenDG;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }

    // Getter và Setter cho từng thuộc tính
    public int getMaDG() {
        return maDG;
    }

    public void setMaDG(int maDG) {
        this.maDG = maDG;
    }
    
    public String getHoDG() {
        return hoDG;
    }

    public void setHoDG(String hoDG) {
        this.hoDG = hoDG;
    }

    public String getTenDG() {
        return tenDG;
    }

    public void setTenDG(String tenDG) {
        this.tenDG = tenDG;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
