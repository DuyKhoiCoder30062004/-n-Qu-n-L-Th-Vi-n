/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPP_DAO;
import DTO.CTPP_DTO;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class CTPP_BUS {

    private CTPP_DAO ctpp_DAO = new CTPP_DAO();

    public ArrayList<CTPP_DTO> getList() {
        return ctpp_DAO.getList();
    }

    public boolean addCTPP(CTPP_DTO ctpp) {
        return ctpp_DAO.addCTPP(ctpp);
    }

    public boolean updateCTPP(CTPP_DTO ctpp) {
        return ctpp_DAO.updateCTPP(ctpp);
    }

    public boolean deleteCTPP(int mapp, String mavach) {
        return ctpp_DAO.deleteCTPP(mapp, mavach);
    }

    public boolean deleteByMaPP(int mapp) {
        return ctpp_DAO.deleteByMaPP(mapp);
    }

    public ArrayList<CTPP_DTO> searchByMaPP(int mapp) {
        return ctpp_DAO.searchByMaPP(mapp);
    }

    public StringBuilder searchPP(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (CTPP_DTO pq : ctpp_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pq.getMaPP()).contains(value)
                    || option.equals("Mã sách") && String.valueOf(pq.getMaSach()).contains(value)
                    || option.equals("Mã vạch") && String.valueOf(pq.getMaVach()).contains(value)
                    || option.equals("Ngày lập") && pq.getNgayLap().format(formatter).contains(value)
                    || option.equals("Lí do") && pq.getLiDo().contains(value)
                    || option.equals("Tiền") && pq.getTien() == Float.parseFloat(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pq.getMaPP() + "\","
                        + "\"maSach\": \"" + pq.getMaSach()+ "\","
                        + "\"maVach\": \"" + pq.getMaVach()+ "\","
                        + "\"ngayLap\": \"" + pq.getNgayLap()+ "\","
                        + "\"liDo\": \"" + pq.getLiDo()+ "\","
                        + "\"tien\": \"" + pq.getTien()+ "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }

}
