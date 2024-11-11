/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ADMIN
 */
public class Loi_DTO {
    private String tenLoi;
    private float phamTramTien;

    public Loi_DTO() {
    }

    public Loi_DTO(String tenLoi, float phamTramtien) {
        this.tenLoi = tenLoi;
        this.phamTramTien = phamTramtien;
    }

    public String getTenLoi() {
        return tenLoi;
    }

    public void setTenLoi(String tenLoi) {
        this.tenLoi = tenLoi;
    }

    public float getPhamTramTien() {
        return phamTramTien;
    }

    public void setPhamTramTien(float phamTramtien) {
        this.phamTramTien = phamTramtien;
    }
    
    
}
