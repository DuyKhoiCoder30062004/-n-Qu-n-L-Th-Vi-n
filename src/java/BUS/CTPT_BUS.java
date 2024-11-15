package BUS;

import DAO.CTPT_DAO;
import DTO.CTPT_DTO;
import java.util.ArrayList;
import java.util.List;

/**
 * BUS for Chi Tiet Phieu Tra
 */
public class CTPT_BUS {
    private CTPT_DAO ctptDAO;

    public CTPT_BUS() {
        ctptDAO = new CTPT_DAO();
    }

    public List<CTPT_DTO> getListCTPT() {
        return ctptDAO.getList();
    }

    public boolean addCTPT(CTPT_DTO ctpt) {
        return ctptDAO.addCTPT(ctpt);
    }

    public boolean updateCTPT(CTPT_DTO ctpt) {
        return ctptDAO.updateCTPT(ctpt);
    }

    public boolean deleteCTPT(int maPT) {
        return ctptDAO.deleteCTPT(maPT);
    }

    // Method to search CTPT by maPT
    public ArrayList<CTPT_DTO> searchCTPTByMaPT(int maPT) {
        return ctptDAO.searchByMaPT(maPT);
    }
}
