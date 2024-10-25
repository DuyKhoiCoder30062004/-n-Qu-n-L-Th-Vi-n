/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.Loi_DAO;
import DTO.Loi_DTO;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Loi_BUS {
    private ArrayList<Loi_DTO> listLoi;
    private Loi_DAO loi_DAO=new Loi_DAO();
    public ArrayList<Loi_DTO> getList()
    {
        listLoi=loi_DAO.getListLoi();
        return listLoi;
    }
    public boolean addLoi(Loi_DTO loi)
    {
       return loi_DAO.addLoi(loi);
    }
    public boolean updateLoi(Loi_DTO loi)
    {
        return loi_DAO.updateLoi(loi);
    }
    public boolean deleteLoi(String tenloi)
    {
        return loi_DAO.deleteLoi(tenloi);
    }
    public Loi_DTO searchByTenLoi(String tenLoi)
    {
        return loi_DAO.searchByTenLoi(tenLoi);
    }
    public StringBuilder searchLoi(String option,String value)
    {
        StringBuilder jsonResult = new StringBuilder("[");
        boolean firstItem = true;
        for (Loi_DTO loi : loi_DAO.getListLoi()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Tên lỗi") && loi.getTenLoi().contains(value)
                    || option.equals("% tiền") && loi.getPhamTramtien()==Float.parseFloat(value)){
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"tenLoi\": \"" + loi.getTenLoi() + "\","
                        + "\"phanTramTien\": \"" + loi.getPhamTramtien() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
