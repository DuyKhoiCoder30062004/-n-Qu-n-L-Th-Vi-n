package DTO;

import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhap_DTO {
    private int maPN;
    private int maNCC;
    private int maNV;
    private LocalDate ngayLap;
    private int tongSL;
    private float tongTien;
    private ArrayList<CTPN_DTO> list;

    public PhieuNhap_DTO() {
    }

    public PhieuNhap_DTO(int maPN, int maNCC, int maNV, LocalDate ngayLap, int tongSL, float tongTien) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.tongSL = tongSL;
        this.tongTien = tongTien;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public void calculateTongTienPhieuNhap() {
        this.tongTien = 0;
        for (CTPN_DTO ctpn : list) {
            this.tongTien += ctpn.calculate(ctpn.getSoLuong(), ctpn.getDonGia());
        }
    }

    public void setList(ArrayList<CTPN_DTO> list) {
        this.list = list;
    }

    public ArrayList<CTPN_DTO> getList() {
        return list;
    }
}
