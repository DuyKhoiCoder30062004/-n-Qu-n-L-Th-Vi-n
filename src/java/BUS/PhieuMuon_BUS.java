/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPM_DAO;
import DAO.PhieuMuon_DAO;
import DTO.CTPM_DTO;
import DTO.PhieuMuon_DTO;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhieuMuon_BUS {

    private ArrayList<PhieuMuon_DTO> listPM;
    private PhieuMuon_DAO pm_DAO = new PhieuMuon_DAO();
    private CTPM_DAO ctpm_DAO = new CTPM_DAO();

    public ArrayList<PhieuMuon_DTO> getList() {
        listPM = pm_DAO.getList();
        return listPM;
    }

    public boolean addPM(PhieuMuon_DTO pm) {
        return pm_DAO.addPM(pm);
    }

    public boolean updatePM(PhieuMuon_DTO pm) {
        return pm_DAO.updatePM(pm);
    }

    public boolean updateTongSL(int mapm, int tongSLMoi) {
        return pm_DAO.updateSL(mapm, tongSLMoi);
    }

    public boolean deletePM(int mapm) {
        return pm_DAO.deletePM(mapm);
    }

    public PhieuMuon_DTO searchByMaPM(int mapm) {
        return pm_DAO.searchByMaPM(mapm);
    }

    public StringBuilder searchPM(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (PhieuMuon_DTO pm : pm_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pm.getMaPM()).contains(value)
                    || option.equals("Mã độc giả") & String.valueOf(pm.getMaKhach()).contains(value)
                    || option.equals("Mã NV") & String.valueOf(pm.getMaNV()).contains(value)
                    || option.equals("Ngày lập") & pm.getNgayLap().format(formatter).contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pm.getMaPM() + "\","
                        + "\"maKhach\": \"" + pm.getMaKhach() + "\","
                        + "\"maNV\": \"" + pm.getMaNV() + "\","
                        + "\"ngayLap\": \"" + pm.getNgayLap() + "\","
                        + "\"hanChot\": \"" + pm.getHanChot() + "\","
                        + "\"tongSL\": \"" + pm.getTongSL() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }

    public String printPM(int mapm) {
        // Khởi tạo chuỗi HTML cho phiếu mượn
        String pm = "<h1 style='text-align:center;'>PHIẾU MƯỢN SÁCH TỪ THƯ VIÊN TRƯỜNG ĐẠI HỌC ABC</h1>";

        PhieuMuon_DTO phieu = pm_DAO.searchByMaPM(mapm);

        // Kiểm tra xem phiếu có tồn tại không
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu mượn.</p>";
        }

        // Thêm thông tin vào chuỗi
        pm += "Mã phiếu mượn: " + phieu.getMaPM() + "<br/>"
                + "Mã nhân viên: " + phieu.getMaNV() + "  "
                + "Tên nhân viên: " + "hii" + "<br/>" // Thêm tên nhân viên
                + "Mã độc giả: " + phieu.getMaKhach()+ "  "
                + "Tên độc giả: " +"tạm" + "<br/>" // Thêm tên độc giả
                + "SĐT độc giả: " + "000" + "<br/>" // Thêm số điện thoại độc giả
                + "Ngày mượn: " + phieu.getNgayLap() + "  "
                + "Ngày dự đoán trả: " + phieu.getHanChot() + "<br/>"
                + "<div style='text-align:center;'>DANH SÁCH SÁCH ĐÃ MƯỢN</div><br/>";

        // Tạo bảng sách mượn
        pm += "<table style='width:100%; border-collapse:collapse;'>"
                + "<tr>"
                + "<th style='border:1px solid; padding:5px;'>Mã Sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Tên Sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Số Lượng</th>"
                + "</tr>";

        ArrayList<CTPM_DTO> listCTPM = ctpm_DAO.searchByMaPM(mapm);

        // Duyệt danh sách chi tiết phiếu mượn
        if (listCTPM != null && listCTPM.size() > 0) {
            for (CTPM_DTO ct : listCTPM) {
                //String tenSach = ctpm_DAO.getTenSach(ct.getMaSach()); // Lấy tên sách từ DAO
                pm += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + "tam" + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getSoLuong() + "</td>"
                        + "</tr>";
            }
        }

        pm += "</table><br/>";

        // Tổng số lượng
        pm += "Tổng số lượng: " + phieu.getTongSL() + "</br>";
        pm += "<div style='text-align:center;'>XIN CẢM ƠN!</div>";

        return pm;
    }

}
