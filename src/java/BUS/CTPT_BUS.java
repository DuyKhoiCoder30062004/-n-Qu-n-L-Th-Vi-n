package BUS;

import DAO.CTPT_DAO;
import DTO.CTPT_DTO;
import java.time.LocalDate;
import java.util.List;

/**
 * BUS for Chi Tiet Phieu Tra
 */
public class CTPT_BUS {
    private CTPT_DAO ctptDAO;

    public CTPT_BUS() {
        ctptDAO = new CTPT_DAO();
    }

    // Get list of CTPT records
    public List<CTPT_DTO> getList() {
        return ctptDAO.getList(); // Get list from DAO
    }

    // Add a new CTPT
    public boolean addCTPT(CTPT_DTO ctpt) {
        return ctptDAO.addCTPT(ctpt); // Add CTPT to database
    }

    // Update an existing CTPT
    public boolean updateCTPT(CTPT_DTO ctpt) {
        return ctptDAO.updateCTPT(ctpt); // Update CTPT in database
    }

    // Delete a CTPT by maPT and maSach
    public boolean deleteCTPT(int maPT, int maSach,LocalDate ngayTra) {
        return ctptDAO.deleteCTPT(maPT, maSach,ngayTra); // Delete CTPT from database
    }
    public boolean deleteCTPT_ByMaPT(int maPT)
    {
        return ctptDAO.deleteCTPT_ByMaPT(maPT);
    }
    // Search CTPT records by maPT
    public List<CTPT_DTO> searchByMaPT(int maPT) {
        return ctptDAO.searchByMaPT(maPT); // Search for CTPT by maPT
    }

    // Search CTPT records by maPT and maSach
    public CTPT_DTO searchByMaPT_MaSach(int maPT, int maSach,LocalDate ngayTra) {
        return ctptDAO.searchByMaPT_MaSach(maPT, maSach,ngayTra); // Search for specific CTPT by both maPT and maSach
    }

    public StringBuilder searchCTPT(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên

        for (CTPT_DTO pq : ctptDAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pq.getMaPT()).contains(value)
                    || option.equals("Mã sách") && String.valueOf(pq.getMaSach()).contains(value)
                    || option.equals("Mã vạch lỗi") && pq.getMaVachLoi().contains(value)
                    || option.equals("Ngày trả") && pq.getNgayTra().isEqual(LocalDate.parse(value))) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPT\": \"" + pq.getMaPT()+ "\","
                        + "\"maSach\": \"" + pq.getMaSach() + "\","
                        + "\"maVachLoi\": \"" + pq.getMaVachLoi()+ "\","
                        + "\"ngayTra\": \"" + pq.getNgayTra() + "\","
                        + "\"soLuong\": \"" + pq.getSoLuong() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
