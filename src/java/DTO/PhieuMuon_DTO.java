/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ADMIN
 */
public class PhieuMuon_DTO {
    private int maPM;
    private int maKhach;
    private int maNV;
    private LocalDate  ngayLap;
    private LocalDate  hanChot;
    private int tongSL;

    public PhieuMuon_DTO() {
    }

    public PhieuMuon_DTO(int maPhieu, int maKhach, int maNV, LocalDate  ngayLap, LocalDate  hanChot, int tongSL) {
        this.maPM = maPhieu;
        this.maKhach = maKhach;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.hanChot = hanChot;
        this.tongSL = tongSL;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPhieu) {
        this.maPM = maPhieu;
    }

    public int getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public LocalDate  getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate  ngayLap) {
        this.ngayLap = ngayLap;
    }

    public LocalDate  getHanChot() {
        return hanChot;
    }

    public void setHanChot(LocalDate  hanChot) {
        this.hanChot = hanChot;
    }

    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }
    
    
}
