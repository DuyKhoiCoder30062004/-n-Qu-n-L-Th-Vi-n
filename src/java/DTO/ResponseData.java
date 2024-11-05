/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ADMIN
 */
public class ResponseData {
    private String status;
    private String thongbao;

    public ResponseData(String status, String thongbao) {
        this.status = status;
        this.thongbao = thongbao;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getThongbao() { return thongbao; }
    public void setThongbao(String thongbao) { this.thongbao = thongbao; }
}
