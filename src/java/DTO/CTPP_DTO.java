/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class CTPP_DTO {
    private int maPP;
    private int maSach;
    private String maVach;
    private LocalDate ngayLap;
    private ArrayList<String> liDo;
    private float tien;

    public CTPP_DTO() {
    }

    public CTPP_DTO(int maPP, int maSach, String maVach, LocalDate ngayLap, ArrayList<String> liDo, float tien) {
        this.maPP = maPP;
        this.maSach = maSach;
        this.maVach = maVach;
        this.ngayLap = ngayLap;
        this.liDo = liDo;
        this.tien = tien;
    }

    public int getMaPP() {
        return maPP;
    }

    public void setMaPP(int maPP) {
        this.maPP = maPP;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getMaVach() {
        return maVach;
    }

    public void setMaVach(String maVach) {
        this.maVach = maVach;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public ArrayList<String> getLiDo() {
        return liDo;
    }

    public void setLiDo(ArrayList<String> liDo) {
        this.liDo = liDo;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }
    
    
}
