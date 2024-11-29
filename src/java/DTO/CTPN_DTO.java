package DTO;

public class CTPN_DTO {
    private int maPN;
    private int maSach;
    private int soLuong;
    private int donGia;
    
    public CTPN_DTO() {
    }

    public CTPN_DTO(int maPN, int maSach, int soLuong, int donGia) {
        this.maPN = maPN;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getMaPN() {
        return maPN;
    }

    public void setMaPN(int maPN) {
        this.maPN = maPN;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int calculate(int soLuong, int donGia ){
        return soLuong * donGia;
    }
}
