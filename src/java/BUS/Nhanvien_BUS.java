package BUS;

import DTO.Nhanvien_DTO;
import DAO.Nhanvien_DAO;
import java.util.ArrayList;

public class Nhanvien_BUS {
    private ArrayList<Nhanvien_DTO> listNhanvien;
    private Nhanvien_DAO nhanvien_DAO = new Nhanvien_DAO();

    public ArrayList<Nhanvien_DTO> getList() {
        listNhanvien = nhanvien_DAO.getListNhanVien();
        return listNhanvien;
    }

    public boolean themNhanVien(Nhanvien_DTO nhanVien) {
        return nhanvien_DAO.addNhanVien(nhanVien);
    }

    public boolean suaNhanVien(Nhanvien_DTO nhanVien) {
        return nhanvien_DAO.updateNhanVien(nhanVien);
    }

    public boolean xoaNhanVien(int maNV) { // Thay đổi kiểu tham số
        return nhanvien_DAO.deleteNhanVien(maNV); // Truyền mã nhân viên
    }
}