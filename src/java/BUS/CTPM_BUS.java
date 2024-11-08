/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPM_DAO;
import DTO.CTPM_DTO;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class CTPM_BUS {

    private ArrayList<CTPM_DTO> listCTPM;
    private CTPM_DAO ctpm_DAO = new CTPM_DAO();

    public ArrayList<CTPM_DTO> getList() {
        listCTPM = ctpm_DAO.getList();
        return listCTPM;
    }

    public boolean addCTPM(CTPM_DTO ctpm) {
        return ctpm_DAO.addCTPM(ctpm);
    }

    public boolean updateCTPM(CTPM_DTO ctpm) {
        return ctpm_DAO.update(ctpm);
    }

    public boolean deleteCTPM(int mapm, int masach) {
        return ctpm_DAO.deleteCTPM(mapm, masach);
    }

    public boolean deleteByMaPM(int mapm) {
        return ctpm_DAO.deleteByMaPM(mapm);
    }

    public ArrayList<CTPM_DTO> searchByMaPM(int mapm) {
        return ctpm_DAO.searchByMaPM(mapm);
    }
    public CTPM_DTO searchByMaPM_MaSach(int mapm,int masach)
    {
        return ctpm_DAO.searchByMaPM_MaSach(mapm, masach);
    }
    public StringBuilder searchCTPM(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên

        for (CTPM_DTO pq : ctpm_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pq.getMaPM()).contains(value)
                    || option.equals("Mã sách") && String.valueOf(pq.getMaSach()).contains(value)
                    || option.equals("Số lượng") && pq.getSoLuong() == Integer.parseInt(value)
                    || option.equals("Trạng thái") && pq.getTrangthai().contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPM\": \"" + pq.getMaPM() + "\","
                        + "\"maSach\": \"" + pq.getMaSach() + "\","
                        + "\"soLuong\": \"" + pq.getSoLuong() + "\","
                        + "\"trangThai\": \"" + pq.getTrangthai() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
