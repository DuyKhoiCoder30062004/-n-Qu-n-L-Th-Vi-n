package BUS;
import DTO.CTSach_DTO;
import java.util.ArrayList;
import DAO.CTSach_DAO;
public class CTSach_BUS {
    private CTSach_DAO ctSach_DAO = new CTSach_DAO();
    private ArrayList<CTSach_DTO> listCTSach;

    public ArrayList<CTSach_DTO> getList() {
        listCTSach = ctSach_DAO.getListCTSach();
        return listCTSach;
    }

    public boolean addCTSach(CTSach_DTO ctSach) {
        return ctSach_DAO.addCTSach(ctSach);
    }

    public boolean updateCTSach(CTSach_DTO ctSach) {
        return ctSach_DAO.updateCTSach(ctSach);
    }
    public boolean daleteCTSach(CTSach_DTO ctSach) {
        return ctSach_DAO.deleteCTSach(ctSach);
    }
    // Tìm bằng mã sách
    public ArrayList<CTSach_DTO> searchCTSachByMaSach(String maSach) {
        return ctSach_DAO.searchCTSachByMaSach(maSach);
    }
    // Tìm bằng mã vạch
    public ArrayList<CTSach_DTO> searchCTSachByMaVach(String maVach) {
        return ctSach_DAO.searchCTSachByMaVach(maVach);
    }
}
