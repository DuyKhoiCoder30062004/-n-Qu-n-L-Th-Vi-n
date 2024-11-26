package DTO;

import java.time.LocalDate;

public class PhieuTra_DTO {
    private int maPT;
    private int maPM;
    private int maNV;
    private int tongSL;
    private LocalDate ngayTra; // Added to include a date field if needed for "ngayTra"

    // Default constructor
    public PhieuTra_DTO() {
    }

    // Constructor with all fields
    public PhieuTra_DTO(int maPT, int maPM, int maNV, int tongSL, LocalDate ngayTra) {
        this.maPT = maPT;
        this.maPM = maPM;
        this.maNV = maNV;
        this.tongSL = tongSL;
        this.ngayTra = ngayTra;
    }

    // Getter and setter for maPT
    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    // Getter and setter for maPM
    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    // Getter and setter for maNV
    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    // Getter and setter for tongSL
    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }

    // Getter and setter for ngayTra
    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    // Method to check if required fields are valid
    public boolean isValid() {
        return maPT > 0 && maPM > 0 && maNV > 0 && tongSL >= 0;
    }

    @Override
    public String toString() {
        return "PhieuTra_DTO{" +
               "maPT=" + maPT +
               ", maPM=" + maPM +
               ", maNV=" + maNV +
               ", tongSL=" + tongSL +
               ", ngayTra=" + ngayTra +
               '}';
    }
}
