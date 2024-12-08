package DTO;

import java.time.LocalDate;

/**
 * DTO for Chi Tiet Phieu Tra
 */
public class CTPT_DTO {
    private int maPT;
    private int maSach;
    private String maVachLoi; 
    private LocalDate ngayTra;
    private int soLuong;

    // Default constructor
    public CTPT_DTO() {
    }

    // Parameterized constructor
    public CTPT_DTO(int maPT, int maSach, String maVachLoi, LocalDate ngayTra, int soLuong) {
        this.maPT = maPT;
        this.maSach = maSach;
        this.maVachLoi = maVachLoi;
        this.ngayTra = ngayTra;
        this.soLuong = soLuong;
    }

    // Getters and setters
    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getMaVachLoi() {
        return maVachLoi;
    }

    public void setMaVachLoi(String maVachLoi) {
        this.maVachLoi = maVachLoi;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "CTPT_DTO{" +
                "maPT=" + maPT +
                ", maSach=" + maSach +
                ", maVachLoi='" + maVachLoi + '\'' +
                ", ngayTra=" + ngayTra +
                ", soLuong=" + soLuong +
                '}';
    }
}
