package DTO;

public class PhieuTra_DTO {
    private int maPT;
    private int maPM;
    private int maNV;
    private int tongSL;

    public PhieuTra_DTO() {
    }

    public PhieuTra_DTO(int maPT, int maPM, int maNV, int tongSL) {
        this.maPT = maPT;
        this.maPM = maPM;
        this.maNV = maNV;
        this.tongSL = tongSL;
    }

    public int getMaPT() {
        return maPT;
    }

    public void setMaPT(int maPT) {
        this.maPT = maPT;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }
}
