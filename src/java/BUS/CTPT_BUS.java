package BUS;

import DAO.CTPT_DAO;
import DTO.CTPT_DTO;
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
    public boolean deleteCTPT(int maPT, int maSach) {
        return ctptDAO.deleteCTPT(maPT, maSach); // Delete CTPT from database
    }

    // Search CTPT records by maPT
    public List<CTPT_DTO> searchByMaPT(int maPT) {
        return ctptDAO.searchByMaPT(maPT); // Search for CTPT by maPT
    }

    // Search CTPT records by maPT and maSach
    public CTPT_DTO searchByMaPT_MaSach(int maPT, int maSach) {
        return ctptDAO.searchByMaPT_MaSach(maPT, maSach); // Search for specific CTPT by both maPT and maSach
    }

    // Search CTPT records based on an option and value (used for searching in the servlet)
    public StringBuilder searchCTPT(String optionSearch, String valueSearch) {
        return ctptDAO.searchCTPT(optionSearch, valueSearch); // Search for CTPT based on option and value
    }
}
