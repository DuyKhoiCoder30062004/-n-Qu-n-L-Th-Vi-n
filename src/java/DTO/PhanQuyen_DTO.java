/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhanQuyen_DTO {
    private int maNV;
    private String matKhau;
    private ArrayList<String> tacVu;

    public PhanQuyen_DTO() {
    }
    

    public PhanQuyen_DTO(int maNV, String matKhau,ArrayList<String> tacVu) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.tacVu=tacVu;
    }
    

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public ArrayList<String> getTacVu() {
        return tacVu;
    }

    public void setTacVu(ArrayList<String> tacVu) {
        this.tacVu = tacVu;
    }
    
    

}
