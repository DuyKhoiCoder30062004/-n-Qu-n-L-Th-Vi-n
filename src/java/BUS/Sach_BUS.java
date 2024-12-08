package BUS;
import DTO.Sach_DTO;
import DAO.Sach_DAO;
import DTO.CTPM_DTO;
import DTO.CTPN_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.KhuVuc_DTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sach_BUS {
    private ArrayList<Sach_DTO> listSach;
    private  Sach_DAO sach_DAO = new Sach_DAO();

    public ArrayList<Sach_DTO> getListSach() {
        listSach = sach_DAO.getListSach();
        return listSach;
    }
   public ArrayList<Sach_DTO> getListSach_not_delete() {
       listSach = sach_DAO.getListSach_not_delete();
       return listSach;
   }
//    //   Kiểm tra sách đã tồn tại chưa nếu rồi trả về true còn chưa thì trả về false
   public boolean kiemTraSach_TonTai(int maSach) {
    return sach_DAO.checkBooks_or_not(maSach);
   }
    // kiểm tra xem sách này đã được nhập chưa nếu nhập rồi thì trả về true chưa thì trả về false
   public boolean kiemTraSach_CTPN(int maSach) {
    return sach_DAO.CheckBook_CTPN(maSach);
   }
//    Kiểm tra xem nhà xuất bản này có hay không nếu có trả về true nếu không trả về false
   public boolean kiemTraNhaXuatBan(int maNXB) {
    return sach_DAO.CheckBook_NXB(maNXB);
   }
   //    Kiểm tra xem khu vực này có hay không nếu có trả về true nếu không trả về false
   public boolean kiemTraKhuVuc(int maKhuVuc) {
    return sach_DAO.CheckBook_KhuVuc(maKhuVuc);
   }
   // Lấy số lượng, mã nhà cung cấp
   public List<Map<String, Object>> getMaNCC_and_SoLuong(int maSach) {
    return sach_DAO.getNCC_and_SoLuong(maSach);
   }
   public boolean themSach(Sach_DTO sach) {
       return sach_DAO.addSach(sach);
   }

   public boolean suaSach(Sach_DTO sach) {
       return sach_DAO.updateSach(sach);
   }
// ===========================Xóa sách============================
   public boolean xoaSach(int maSach) {
       return sach_DAO.deleteSach(maSach);
   }
// =========lấy danh sách các bảng liên quan đến mã sách
// lẤY CHI TIẾT PHIẾU MƯỢN
   public ArrayList<CTPM_DTO> layDanhSachCTPM_as_maSach(int maSach) {
       return sach_DAO.getListCTPM_as_maSach(maSach);
   }
// lẤY CHI TIẾT PHIẾU NHẬP
   public ArrayList<CTPN_DTO> layDanhSachCTPN_as_maSach(int maSach) {
       return sach_DAO.getListCTPN_as_maSach(maSach);
   }
// lẤY CHI TIẾT PHIẾU PHẠT
   public ArrayList<CTPP_DTO> layDanhSachCTPP_as_maSach(int maSach) {
       return sach_DAO.getListCTPP_as_maSach(maSach);
   }
//  LẤY CHI TIẾT PHIẾU TRẢ
   public ArrayList<CTPT_DTO> layDanhSachCTPT_as_maSach(int maSach) {
       return sach_DAO.getListCTPT_as_maSach(maSach);
   }
//    ============xÓA CÁC DANH SÁCH ĐÓ
 // xóa CHI TIẾT PHIẾU MƯỢN
   public boolean xoaCTPM_as_maSach(int maSach) {
       return sach_DAO.deleteCTPM_as_maSach(maSach);
   }
//    XÓA CHI TIẾT PHIẾU NHẬP
   public boolean xoaCTPN_as_maSach(int maSach) {
       return sach_DAO.deleteCTPN_as_maSach(maSach);
   }
//    XÓA CHI TIẾT PHIẾU PHẠT
   public boolean xoaCTPP_as_maSach(int maSach) {
       return sach_DAO.deleteCTPP_as_maSach(maSach);
   }
//    XÓA CHI TIẾT PHIẾU TRẢ
   public boolean xoaCTPT_as_maSach(int maSach) {
       return sach_DAO.deleteCTPT_as_maSach(maSach);
   }
// ========= THÊM VÀO LẠI MỚI MÃ SÁCH LÀ ÂM
   public boolean themCTPM_as_maSach(CTPM_DTO ctpm) {
    return sach_DAO.addCTPM(ctpm);
   }
//    thêm chi tiết phiếu nhập
   public boolean themCTPN_as_maSach(CTPN_DTO ctpn) {
    return sach_DAO.addCTPN(ctpn);
   }
//    thêm chi tiết phiếu phạt
   public boolean themCTPP_as_maSach(CTPP_DTO ctpp) {
    return sach_DAO.addCTPP(ctpp);
   }
//    thêm chi tiết phiếu trả
   public boolean themCTPT_as_maSach(CTPT_DTO ctpt) {
    return sach_DAO.addCTPT(ctpt);
   }
   public ArrayList<Sach_DTO> timSachTheoMaSach(String maSach) {
       return sach_DAO.findSachByMaSach(maSach);
   }
   public Sach_DTO timSachTheoMaSach(int maSach) {
    return sach_DAO.findSachByMaSach(maSach);
}
// Lấy các quyển sách mới
   public ArrayList<Sach_DTO> listSach_New() {
    return sach_DAO.listSach_New();
   }
// Lấy ra các khu vực
   public ArrayList<KhuVuc_DTO> listKhuVuc() {
    return sach_DAO.listKhuVuc();
   }
   // tìm kiếm
   public ArrayList<Sach_DTO> listSach_LookFor(String tuKhoaTimKiem, int moneyMin, int moneyMax, String tenKhuVuc) {
       return sach_DAO.listSach_LookFor(tuKhoaTimKiem, moneyMin, moneyMax, tenKhuVuc);
   }
}
