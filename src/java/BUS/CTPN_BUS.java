package BUS;

import DAO.CTPN_DAO;
import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;
import DTO.CTSach_DTO;
import java.util.ArrayList;

public class CTPN_BUS {
<<<<<<< HEAD

    private ArrayList<CTPN_DTO> listCTPN;
    private CTPN_DAO ctpn_DAO = new CTPN_DAO();

    public boolean addCTPN(CTPN_DTO ctpn) {
        return ctpn_DAO.addCTPN(ctpn);
    }

    public boolean updateCTPN(CTPN_DTO ctpn) {
        return ctpn_DAO.updateCTPN(ctpn);
    }

    public boolean deleteCTPN(int maPN, int maSach) {
        return ctpn_DAO.deleteCTPN(maPN, maSach);
    }

    public boolean deleteByMaPN(int maPN) {
        return ctpn_DAO.deleteByMaPN(maPN);
=======
    private ArrayList<CTPN_DTO> listCTPN;
    private CTPN_DAO ctpn_DAO = new CTPN_DAO();
    private CTSach_BUS ctSach = new CTSach_BUS();
    
    public boolean addCTPN(CTPN_DTO ctpn){
        for(String s : ctpn.getMaVach()){
            CTSach_DTO ctSachDTO = new CTSach_DTO();
            ctSachDTO.setMaSach(ctpn.getMaSach());
            ctSachDTO.setMaVach(s);
            ctSachDTO.setTinhTrangSach("");
            if(ctSach.addCTSach(ctSachDTO)){
            }else return false;
        }
        return ctpn_DAO.addCTPN(ctpn);
    }

    public boolean updateCTPN(CTPN_DTO ctpn){
        for(String s : ctpn.getMaVach()){
            CTSach_DTO ctSachDTO = new CTSach_DTO();
            ctSachDTO.setMaSach(ctpn.getMaSach());
            ctSachDTO.setMaVach(s);
            ctSachDTO.setTinhTrangSach("");
            if(ctSach.updateCTSach(ctSachDTO)){
            }else return false;
        }
        return ctpn_DAO.updateCTPN(ctpn);
    }

    public boolean deleteCTPN(int maPN, int maSach, String maVach){
        CTSach_DTO ctSachDTO = new CTSach_DTO();
        ctSachDTO.setMaSach(maSach); ctSachDTO.setMaVach(maVach);
        if(ctSach.daleteCTSach(ctSachDTO)){
        }else return false;
        return ctpn_DAO.deleteCTPN(maPN, maSach);
    }

    public boolean deleteByMaPN(CTPN_DTO ctpn){
        for(String s : ctpn.getMaVach()){
            CTSach_DTO ctSachDTO = new CTSach_DTO();
            ctSachDTO.setMaSach(ctpn.getMaSach());
            ctSachDTO.setMaVach(s);
            ctSachDTO.setTinhTrangSach("");
            if(ctSach.daleteCTSach(ctSachDTO)){
            }else return false;
        }
        return ctpn_DAO.deleteByMaPN(ctpn.getMaPN());
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d
    }

    public ArrayList<CTPN_DTO> searchByMaPN(int maPN) {
        return ctpn_DAO.searchByMaPN(maPN);
    }

    public CTPN_DTO searchByMaPN_MaSach(int maPN, int maSach) {
        return ctpn_DAO.searchByMaPN_MaSach(maPN, maSach);
    }
<<<<<<< HEAD

    public StringBuilder searchCTPN(String option, String value) {
        StringBuilder result = new StringBuilder("[");
        boolean firstItem = true;
        System.out.println("CTPN"+ ctpn_DAO.getList());
        for (CTPN_DTO pn : ctpn_DAO.getList()) {
            if (option.equals("Mã phiếu") && String.valueOf(pn.getMaPN()).contains(value)
                    || option.equals("Mã sách") && String.valueOf(pn.getMaSach()).contains(value)
                    || option.equals("Mã vạch") && pn.getMaVach().contains(value)
                    || option.equals("Số lượng") && pn.getSoLuong() == Integer.parseInt(value)
                    || option.equals("Đơn giá") && pn.getDonGia() == Integer.parseInt(value)) {
                if (!firstItem) {
                    result.append(",");
                }
                result.append("{"
                        + "\"maPN\": \"" + pn.getMaPN() + "\","
                        + "\"maSach\": \"" + pn.getMaSach() + "\","
                        + "\"listMaVach\": \"" + pn.getMaVach() + "\","
                        + "\"soLuong\": \"" + pn.getSoLuong() + "\","
                        + "\"donGia\": \"" + pn.getDonGia() + "\""
                        + "}");
                firstItem = false;
=======
    
    public StringBuilder searchCTPN(String option, String value) {
    StringBuilder result = new StringBuilder("[");
    boolean firstItem = true;

    // Duyệt qua danh sách chi tiết phiếu nhập từ cơ sở dữ liệu
    for (CTPN_DTO pn : ctpn_DAO.getList()) {
        boolean matches = false;

        // Kiểm tra các điều kiện tìm kiếm
        if ("Mã phiếu".equals(option) && String.valueOf(pn.getMaPN()).contains(value)) {
            matches = true;
        } else if ("Mã sách".equals(option) && String.valueOf(pn.getMaSach()).contains(value)) {
            matches = true;
        } else if ("Số lượng".equals(option) && String.valueOf(pn.getSoLuong()).contains(value)) {
            matches = true;
        } else if ("Đơn giá".equals(option) && String.valueOf(pn.getDonGia()).contains(value)) {
            matches = true;
        } else if ("Mã vạch".equals(option)) {
            // Tìm kiếm trong danh sách mã vạch
            for (String maVach : pn.getMaVach()) {
                if (maVach.contains(value)) {
                    matches = true;
                    break; // Nếu tìm thấy một mã vạch khớp, thoát vòng lặp
                }
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d
            }
        }

        // Nếu khớp với điều kiện, tạo đối tượng JSON và thêm vào kết quả
        if (matches) {
            if (!firstItem) {
                result.append(","); // Thêm dấu phân cách nếu không phải mục đầu tiên
            }
            result.append("{")
                  .append("\"maPN\": \"").append(pn.getMaPN()).append("\",")
                  .append("\"maSach\": \"").append(pn.getMaSach()).append("\",")
                  .append("\"soLuong\": \"").append(pn.getSoLuong()).append("\",")
                  .append("\"donGia\": \"").append(pn.getDonGia()).append("\",")
                  .append("\"maVach\": [");

            // Duyệt qua danh sách mã vạch và thêm vào JSON
            boolean firstVach = true;
            for (String maVach : pn.getMaVach()) {
                if (!firstVach) result.append(","); // Thêm dấu phân cách nếu không phải mã vạch đầu tiên
                result.append("\"").append(maVach).append("\"");
                firstVach = false;
            }

            result.append("]}");
            firstItem = false;
        }
    }
    result.append("]");

    // Trả về kết quả dưới dạng chuỗi JSON
    return result;
}

    public ArrayList<CTPN_DTO> getList() {
        listCTPN = ctpn_DAO.getList();
        return listCTPN;
    }

    public ArrayList<CTPN_DTO> getList() {
        listCTPN = ctpn_DAO.getList();
        return listCTPN;
    }

}
