package DAO;

import java.util.ArrayList;
import DTO.Nhanvien_DTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;

public class Nhanvien_DAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    // Lấy danh sách nhân viên
    public ArrayList<Nhanvien_DTO> getListNhanVien() {
        ArrayList<Nhanvien_DTO> listNhanVien = new ArrayList<>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM nhanvien"; // Thay đổi tên bảng nếu cần
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Nhanvien_DTO nhanVien = new Nhanvien_DTO(
                    rs.getInt("manv"),
                    rs.getString("ho"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getString("chucvu"),
                    rs.getDouble("luong"),
                    rs.getDate("ngaysinh")
                );
                listNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listNhanVien;
    }

    // Thêm nhân viên
    public boolean addNhanVien(Nhanvien_DTO nhanVien) {
        boolean result = false;
        try {
            String sql = "INSERT INTO nhanvien (ho, ten, sdt, chucvu, luong, ngaysinh) VALUES (?, ?, ?, ?, ?, ?)";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, nhanVien.getHo());
            ps.setString(2, nhanVien.getTen());
            ps.setString(3, nhanVien.getSoDT());
            ps.setString(4, nhanVien.getChucVu());
            ps.setDouble(5, nhanVien.getLuong());
            ps.setDate(6, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Sửa nhân viên
    public boolean updateNhanVien(Nhanvien_DTO nhanVien) {
        boolean result = false;
        try {
            String sql = "UPDATE nhanvien SET ho = ?, ten = ?, soDT = ?, chucVu = ?, luong = ?, ngaySinh = ? WHERE maNV = ?";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, nhanVien.getHo());
            ps.setString(2, nhanVien.getTen());
            ps.setString(3, nhanVien.getSoDT());
            ps.setString(4, nhanVien.getChucVu());
            ps.setDouble(5, nhanVien.getLuong());
            ps.setDate(6, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
            ps.setInt(7, nhanVien.getMaNV());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Xóa nhân viên
    public boolean deleteNhanVien(int maNV) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "DELETE FROM nhanvien WHERE maNV = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maNV);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}