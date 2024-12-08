package BUS;

import DAO.CTPN_DAO;
import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;
import DTO.CTSach_DTO;
import java.util.ArrayList;

public class CTPN_BUS {

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
    }

    public ArrayList<CTPN_DTO> searchByMaPN(int maPN) {
        return ctpn_DAO.searchByMaPN(maPN);
    }

    public CTPN_DTO searchByMaPN_MaSach(int maPN, int maSach) {
        return ctpn_DAO.searchByMaPN_MaSach(maPN, maSach);
    }

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

}
