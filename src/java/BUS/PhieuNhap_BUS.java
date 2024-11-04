package BUS;

import java.util.ArrayList;

import DAO.CTPN_DAO;
import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;

public class PhieuNhap_BUS {
    private PhieuNhap_DAO phieuNhapDAO;
    private CTPN_DAO ctpn;

    public PhieuNhap_BUS() {
        phieuNhapDAO = new PhieuNhap_DAO();
        ctpn = new CTPN_DAO();
    }

    public boolean validatePN(PhieuNhap_DTO pn, ArrayList<CTPN_DTO> ctpnList) {
        // Kiểm tra thông tin phiếu nhập
        if (pn == null || pn.getNgayLap() == null) {
            System.out.println("Thông tin phiếu nhập không hợp lệ");
            return false;
        }
    
        // Kiểm tra danh sách chi tiết phiếu nhập
        if (ctpnList == null || ctpnList.isEmpty()) {
            System.out.println("Danh sách chi tiết phiếu nhập không được để trống");
            return false;
        }
    
        for (CTPN_DTO ctpn : ctpnList) {
            if (ctpn.getSoLuong() <= 0 || ctpn.getDonGia() <= 0) {
                System.out.println("Số lượng và đơn giá phải lớn hơn 0");
                return false;
            }
        }
    
        return true;
    }
    

    public boolean addPN(PhieuNhap_DTO phieuNhap_DTO, ArrayList<CTPN_DTO> ctpn_DTO){
        phieuNhap_DTO.setList(ctpn_DTO);
        return phieuNhapDAO.addPN(phieuNhap_DTO);
    }

    public boolean updatePN(PhieuNhap_DTO phieuNhap_DTO, ArrayList<CTPN_DTO> ctpn_DTO){
        boolean result = false;
        result = phieuNhapDAO.updatePN(phieuNhap_DTO);
        if(result){
            for(CTPN_DTO ctpn : ctpn_DTO){
                this.ctpn.updateCTPN(ctpn);
            }
        }
        return result;
    }

    public boolean deletePN(int maPN) {
        // Kiểm tra nếu có chi tiết phiếu nhập liên quan
        ArrayList<CTPN_DTO> ctpnList = ctpn.searchByMaPN(maPN);
        if (ctpnList != null && !ctpnList.isEmpty()) {
            // Xóa từng chi tiết phiếu nhập trước
            for (CTPN_DTO ctpn : ctpnList) {
                this.ctpn.deleteByMaPN(maPN);
            }
        }
    
        // Sau khi xóa các chi tiết, xóa phiếu nhập
        return phieuNhapDAO.deleteByMaPN(maPN);
    }
    
    public ArrayList<PhieuNhap_DTO> getList(){
        return phieuNhapDAO.getList();
    }

    public PhieuNhap_DTO searchByMaPN(int maPN){
        return phieuNhapDAO.searchByMaPN(maPN);
    }

}
