package BUS;

import DTO.Nhanvien_DTO;
import DAO.Nhanvien_DAO;
import java.util.ArrayList;

public class Nhanvien_BUS {
    private ArrayList<Nhanvien_DTO> listNhanvien;
    private Nhanvien_DAO nhanvien_DAO = new Nhanvien_DAO();

    // Lấy danh sách nhân viên
    public ArrayList<Nhanvien_DTO> getList() {
        listNhanvien = nhanvien_DAO.getListNhanVien();
        return listNhanvien;
    }

    // Thêm nhân viên
    public boolean themNhanVien(Nhanvien_DTO nhanVien) {
        return nhanvien_DAO.addNhanVien(nhanVien);
    }

    // Sửa nhân viên
    public boolean suaNhanVien(Nhanvien_DTO nhanVien) {
        return nhanvien_DAO.updateNhanVien(nhanVien);
    }

    // Xóa nhân viên
    public boolean xoaNhanVien(int maNV) {
        return nhanvien_DAO.deleteNhanVien(maNV);
    }

    // Tìm kiếm nhân viên theo mã hoặc tên
    public ArrayList<Nhanvien_DTO> timKiemNhanVien(String keyword) {
        return nhanvien_DAO.searchNhanVien(keyword);
    }
}
