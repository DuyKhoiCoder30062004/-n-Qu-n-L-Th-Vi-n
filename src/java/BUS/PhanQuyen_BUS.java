/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PhanQuyen_DAO;
import DTO.PhanQuyen_DTO;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhanQuyen_BUS {

    private ArrayList<PhanQuyen_DTO> listPQ;
    private PhanQuyen_DAO pq_DAO = new PhanQuyen_DAO();

    public ArrayList<PhanQuyen_DTO> getList() {
        listPQ = pq_DAO.getListPQ();
        return listPQ;
    }

    public boolean addPQ(PhanQuyen_DTO pq) {
        return pq_DAO.addPQ(pq);
    }

    public boolean editPQ(PhanQuyen_DTO pq) {
        return pq_DAO.updatePQ(pq);
    }

    public boolean detelePQ(int maNV) {
        return pq_DAO.deletePQ(maNV);
    }
    public PhanQuyen_DTO searchByMaNV(int maNV)
    {
        return pq_DAO.searchByMaNV(maNV);
    }

    public StringBuilder searchPQ(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên

        for (PhanQuyen_DTO pq : pq_DAO.getListPQ()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("mã nv") && pq.getMaNV() == Integer.parseInt(value)
                    || option.equals("mật khẩu") && pq.getMatKhau().equals(value)
                    || option.equals("tác vụ") && pq.getTacVu().contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maNV\": \"" + pq.getMaNV() + "\","
                        + "\"matKhau\": \"" + pq.getMatKhau() + "\","
                        + "\"tacVu\": \"" + pq.getTacVu() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }

}
