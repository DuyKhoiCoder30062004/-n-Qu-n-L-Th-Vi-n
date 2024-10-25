/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.PhieuPhat_DAO;
import DTO.PhieuPhat_DTO;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhieuPhat_BUS {

    private PhieuPhat_DAO pp_DAO = new PhieuPhat_DAO();

    public ArrayList<PhieuPhat_DTO> getList() {
        return pp_DAO.getList();
    }

    public boolean addPP(PhieuPhat_DTO pp) {
        return pp_DAO.addPP(pp);
    }

    public boolean updatePP(PhieuPhat_DTO pp) {
        return pp_DAO.updatePP(pp);
    }

    public boolean updateTT(int mapp, double ttnew) {
        return pp_DAO.updateTT(mapp, ttnew);
    }

    public boolean deletePP(int mapp) {
        return pp_DAO.deletePP(mapp);
    }

    public PhieuPhat_DTO searchByMaPP(int mapp) {
        return pp_DAO.searchByMaPP(mapp);
    }

    public StringBuilder searchPP(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên

        for (PhieuPhat_DTO pq : pp_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pq.getMaPP()).contains(value)
                    || option.equals("Mã phiếu trả") && String.valueOf(pq.getMaPT()).contains(value)
                    || option.equals("Mã NV") && String.valueOf(pq.getMaPP()).contains(value)
                    || option.equals("Tổng tiền") && pq.getTongTien() == Double.parseDouble(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pq.getMaPP() + "\","
                        + "\"maPT\": \"" + pq.getMaPT() + "\","
                        + "\"maNV\": \"" + pq.getMaNV() + "\","
                        + "\"tongTien\": \"" + pq.getTongTien() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }

}
