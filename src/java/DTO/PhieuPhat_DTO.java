/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ADMIN
 */
public class PhieuPhat_DTO {
    private int maPP;
    private int maNV;
    private int maPT;
    private double tongTien;

    public PhieuPhat_DTO() {
    }

    public PhieuPhat_DTO(int maPP, int maNV, int maPT, double tongTien) {
        this.maPP = maPP;
        this.maNV = maNV;
        this.maPT = maPT;
        this.tongTien = tongTien;
    }

    public int getMaPP() {
        return maPP;
    }

    public void setMaPP(int maPP) {
        this.maPP = maPP;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    
}
