package DAO;
import java.util.ArrayList;
import DTO.Sach_DTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;
public class Sach_DAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    // Lấy danh sách
    public ArrayList<Sach_DTO> getListSach() {
        ArrayList<Sach_DTO> listSach = new ArrayList<Sach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM sach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Sach_DTO sach = new Sach_DTO();
                sach.setMaSach(rs.getInt(0));
                sach.setTenSach(rs.getString(1));
                sach.setTacGia(rs.getString(2));
                sach.setMaNXB(rs.getInt(3));
                sach.setMaNCC(rs.getInt(4));
                sach.setMaKhuVuc(rs.getInt(5));
                sach.setGia(rs.getInt(6));
                sach.setSoLuong(rs.getInt(7));
                sach.setMoTa(rs.getString(8));
                sach.setNamXuatBan(rs.getInt(9));
                sach.setAnh(rs.getString(10));
                listSach.add(sach);
            }
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return listSach;
    }
    // 
    // Lấy danh sách chưa bị xóa
    public ArrayList<Sach_DTO> getListSach_not_delete() {
        ArrayList<Sach_DTO> listSach = new ArrayList<Sach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM sach WHERE masach <> -masach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Sach_DTO sach = new Sach_DTO();
                sach.setMaSach(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setMaNXB(rs.getInt(4));
                sach.setMaNCC(rs.getInt(5));
                sach.setMaKhuVuc(rs.getInt(6));
                sach.setGia(rs.getInt(7));
                sach.setSoLuong(rs.getInt(8));
                sach.setMoTa(rs.getString(9));
                sach.setNamXuatBan(rs.getInt(10));
                sach.setAnh(rs.getString(11));
                listSach.add(sach);
            }
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return listSach;
    }
    // Thêm sách
    public boolean addSach(Sach_DTO sach) {
        boolean result = false;
        try {
            String sql = "INSERT INTO sach VALUES (?,?,?,?,?,?,?, ?, ?, ?, ?)";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, sach.getMaSach());
            ps.setString(2, sach.getTenSach());
            ps.setString(3, sach.getTacGia());
            ps.setInt(4, sach.getMaNXB());
            ps.setInt(5, sach.getMaNCC());
            ps.setInt(6, sach.getMaKhuVuc());
            ps.setInt(7, sach.getGia());
            ps.setInt(8, sach.getSoLuong());
            ps.setString(9, sach.getMoTa());
            ps.setInt(10, sach.getNamXuatBan());
            ps.setString(11, sach.getAnh());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return result;
    }
    // Sửa 
    public boolean updateSach(Sach_DTO sach) {
        boolean result = false;
        try {
            String sql = "UPDATE sach SET tensach = ?, tacgia = ?, manxb = ?, mancc = ?, makv = ?, gia = ?, soluong = ?, mota = ?, +"
            + "anh =?, namxb =? WHERE masach =?";
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, sach.getTenSach());
            ps.setString(2, sach.getTacGia());
            ps.setInt(3, sach.getMaNXB());
            ps.setInt(4, sach.getMaNCC());
            ps.setInt(5, sach.getMaKhuVuc());
            ps.setInt(6, sach.getGia());
            ps.setInt(7, sach.getSoLuong());
            ps.setString(8, sach.getMoTa());
            ps.setInt(9, sach.getNamXuatBan());
            ps.setString(10, sach.getAnh());
            ps.setInt(11, sach.getMaSach());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;

        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return result;
    }
    // Xóa
    public boolean deleteSach(Sach_DTO sach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            connection.setAutoCommit(false); // Tắt tự động commit
            // Update table sách
            String sql = "UPDATE sach SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // Update table ctpm
            String sql2 = "UPDATE ctpm SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql2);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // update table ctpn
            String sql3 = "UPDATE ctpn SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql3);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // Update table ctpp
            String sql4 = "UPDATE ctpp SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql4);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // Update table ctpt
            String sql5 = "UPDATE ctpt SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql5);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // Update table ctsach
            String sql6 = "UPDATE ctsach SET masach = ? WHERE masach =?";
            ps = connection.prepareStatement(sql6);
            ps.setInt(1, (sach.getMaSach() * -1));
            ps.setInt(2, sach.getMaSach());
            ps.executeUpdate();
            // Commit transaction
            connection.commit();
            result = true; // Đánh dấu thành công
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) xuLyDB.closeConnection(connection);
                if (ps != null ) ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    // Tìm kiếm theo mã sách
    public ArrayList<Sach_DTO> findSachByMaSach(String maSach) {
        ArrayList<Sach_DTO> listSach = new ArrayList<>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM sach WHERE CAST(ma_sach AS CHAR) " + " LIKE '%" + maSach + "%'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Sach_DTO sach = new Sach_DTO();
                sach.setMaSach(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setMaNXB(rs.getInt(4));
                sach.setMaNCC(rs.getInt(5));
                sach.setMaKhuVuc(rs.getInt(6));
                sach.setGia(rs.getInt(7));
                sach.setSoLuong(rs.getInt(8));
                sach.setMoTa(rs.getString(9));
                sach.setNamXuatBan(rs.getInt(10));
                sach.setAnh(rs.getString(11));
                listSach.add(sach);
            }
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return listSach;
    }
}
    
