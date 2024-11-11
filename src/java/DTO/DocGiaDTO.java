package DTO;
import java.util.Date;

public class DocGiaDTO {
    private int maKhach;
    private String tenKhach;
    private String diaChi;
    private String soDienThoai;
    private Date ngaySinh;

    // Constructor không tham số
    public DocGiaDTO() {}

    // Constructor có tham số
    public DocGiaDTO(int maKhach, String tenKhach, String diaChi, String soDienThoai, Date ngaySinh) {
        this.maKhach = maKhach;
        this.tenKhach = tenKhach;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.ngaySinh = ngaySinh;
    }

    // Getter và Setter cho từng thuộc tính
    public int getMaKhach() {
        return maKhach;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
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

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
