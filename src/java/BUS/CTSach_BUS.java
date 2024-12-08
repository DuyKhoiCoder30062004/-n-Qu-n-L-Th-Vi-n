package BUS;
import DTO.CTPN_DTO;
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
    public ArrayList<CTSach_DTO> getCTSach_not_delete(int maSach) {
        listCTSach = ctSach_DAO.getListCTSach_not_delete(maSach);
        return listCTSach;
    }
    // Kiểm tra sự tồn tại của chi tiết sách bằng mã vạch
    public boolean checkBooksDetail_as_maVach(String maVach, int maSach) {
        return ctSach_DAO.checkBooksDetail_as_maVach(maVach,maSach);
    }
    // Kiểm sửa chi tiết sách
    public boolean updateCTSach(CTSach_DTO ctsach) {
        return ctSach_DAO.updateCTSach(ctsach);
    }
    // lấy chi tiết sah bằng mã sách để sau đó cập nhập mã khi xóa sách
    public ArrayList<CTSach_DTO> getCTSach_as_maSach(int maSach) {
        return ctSach_DAO.getListCTSach_as_maSach(maSach);
    }
    // xóa các chi tiết sách đó
    public boolean deleteCTSach_as_maSach(int maSach) {
        return ctSach_DAO.deleteCTSach_as_maSach(maSach);
    }
    // add vào lại với mã âm đã thay đổi
    public boolean addCTSach_when_deleteSach(CTSach_DTO ctSach) {
        return ctSach_DAO.addCTSach_when_deleteSach(ctSach);
    }
    // lấy chi các chi tiết trong bảng chi tiết phiếu nhập bằng mã sách và mã vạch
    public ArrayList<CTPN_DTO> getListCTPN_as_maSach(int maSach) {
        return ctSach_DAO.getListCTPN_as_maSach(maSach);
    }
    // xóa chi tiết phiếu nhập bằng mã sách và mã vạch
    public boolean deleteCTPN_as_maSach(int maSach) {
        return ctSach_DAO.deleteCTPN_as_maSach(maSach);
    }
    // xóa chi tiết sách
    public boolean deleteCTSach_as_maVach_and_maSach(String maVach, int maSach) {
        return ctSach_DAO.deleteCTSach_as_maVach_and_maSach(maVach, maSach);
    }
    // add vào lại với mã vạch đã xóa
    public boolean addCTPN_when_DeleteCTSach(CTPN_DTO ctpn) {
        return ctSach_DAO.addCTPN(ctpn);
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
