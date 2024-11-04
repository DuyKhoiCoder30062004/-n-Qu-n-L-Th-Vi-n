package BUS;

import java.util.ArrayList;

import DAO.CTPN_DAO;
import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;

public class CTPN_BUS {
    private CTPN_DAO ctpnDAO; 
    private PhieuNhap_DAO phieuNhap_DAO;

    public CTPN_BUS() {
        ctpnDAO = new CTPN_DAO();
        phieuNhap_DAO = new PhieuNhap_DAO();
    }

    public boolean addCTPN(CTPN_DTO ctpn) {
        if(!phieuNhap_DAO.isExit(ctpn.getMaPN())){
            System.out.println("Mã phiếu nhập không tồn tại!"); return false;
        }
        return ctpnDAO.addCTPN(ctpn);
    }
    
    public boolean updateCTPN(CTPN_DTO ctpn){
        if(!phieuNhap_DAO.isExit(ctpn.getMaPN())){
            System.out.println("Mã phiếu nhập không tồn tại!"); return false;
        }
        return ctpnDAO.updateCTPN(ctpn);
    }

    public boolean deleteByMaPN(int maPN){
        if(!phieuNhap_DAO.isExit(maPN)){
            System.out.println("Mã phiếu nhập không tồn tại!"); return false;
        }
        return ctpnDAO.deleteByMaPN(maPN);
    }

    public boolean deleteByMaSach(int maPN, int maSach){
        if(!phieuNhap_DAO.isExit(maPN)){
            System.out.println("Mã phiếu nhập không tồn tại!"); return false;
        }
        return ctpnDAO.deleteCTPN(maPN, maSach);
    }

    public ArrayList<CTPN_DTO> searchByMaPN(int maPN){
        if(!phieuNhap_DAO.isExit(maPN)){
            System.out.println("Mã phiếu nhập không tồn tại!"); return null;
        }
        return ctpnDAO.searchByMaPN(maPN);
    }
}
