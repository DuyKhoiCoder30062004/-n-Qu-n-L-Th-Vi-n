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
            
            // Kiểm tra nếu không có dữ liệu
            if (!rs.isBeforeFirst()) {
                System.out.println("Không có dữ liệu nhân viên.");
                return listNhanVien; // Trả về danh sách rỗng nếu không có nhân viên
            }

            while (rs.next()) {
                Nhanvien_DTO nhanVien = new Nhanvien_DTO(
                    rs.getInt("manv"),
                    rs.getString("ho"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getDouble("luong"),
                    rs.getString("chucvu"),
                    rs.getTimestamp("ngaysinh") // Sử dụng getTimestamp cho trường datetime
                );
                listNhanVien.add(nhanVien);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
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
            String sql = "INSERT INTO nhanvien (manv, ho, ten, sdt, luong, ngaysinh, chucvu) VALUES (?, ?, ?, ?, ?, ?, ?)";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, nhanVien.getMaNV()); // Thêm manv vào vị trí đầu tiên
            ps.setString(2, nhanVien.getHo());
            ps.setString(3, nhanVien.getTen());
            ps.setString(4, nhanVien.getSoDT());
            ps.setDouble(5, nhanVien.getLuong());
            ps.setTimestamp(6, new java.sql.Timestamp(nhanVien.getNgaySinh().getTime())); // Sử dụng Timestamp cho ngày sinh
            ps.setString(7, nhanVien.getChucVu());
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
            String sql = "UPDATE nhanvien SET ho = ?, ten = ?, sdt = ?, luong = ?, ngaysinh = ?, chucvu = ? WHERE manv = ?";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, nhanVien.getHo());
            ps.setString(2, nhanVien.getTen());
            ps.setString(3, nhanVien.getSoDT());
            ps.setDouble(4, nhanVien.getLuong());
            ps.setTimestamp(5, new java.sql.Timestamp(nhanVien.getNgaySinh().getTime())); // Sử dụng Timestamp cho ngày sinh
            ps.setString(6, nhanVien.getChucVu());
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
            String sql = "DELETE FROM nhanvien WHERE manv = ?";
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

    // Tìm kiếm nhân viên theo mã hoặc tên
    public ArrayList<Nhanvien_DTO> searchNhanVien(String keyword) {
        ArrayList<Nhanvien_DTO> listNhanVien = new ArrayList<>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM nhanvien WHERE manv = ? OR ten LIKE ?";
            ps = connection.prepareStatement(sql);
            
            // Cố gắng chuyển keyword sang số nguyên cho trường hợp tìm kiếm theo mã
            try {
                int maNV = Integer.parseInt(keyword);
                ps.setInt(1, maNV);
            } catch (NumberFormatException e) {
                ps.setInt(1, -1); // Nếu không phải số, đặt giá trị không hợp lệ
            }
            
            ps.setString(2, "%" + keyword + "%"); // Tìm kiếm tên có chứa từ khóa
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Nhanvien_DTO nhanVien = new Nhanvien_DTO(
                    rs.getInt("manv"),
                    rs.getString("ho"),
                    rs.getString("ten"),
                    rs.getString("sdt"),
                    rs.getDouble("luong"),
                    rs.getString("chucvu"),
                    rs.getTimestamp("ngaysinh") // Dùng getTimestamp cho trường ngày sinh
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
}

