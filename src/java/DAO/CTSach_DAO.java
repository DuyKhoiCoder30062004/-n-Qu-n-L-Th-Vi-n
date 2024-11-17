package DAO;
import DTO.CTSach_DTO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;
public class CTSach_DAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    // get list chi tiết sách
    public ArrayList<CTSach_DTO> getListCTSach() {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setMaVach(rs.getString(1));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
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
        return listCTSach;
    }
    // get list chi tiết sách chưa bị xóa
    public ArrayList<CTSach_DTO> getListCTSach_not_delete() {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach where masach <> (masach * -1) AND mavach <> " + "-" + " mavach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setMaVach(rs.getString(1));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
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
        return listCTSach;
    }
    // Thêm chi tiết sách
    public boolean addCTSach(CTSach_DTO ctSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "INSERT INTO ctsach(masach, mavach, trangthai) VALUES (?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(2, ctSach.getMaSach());
            ps.setString(1, ctSach.getMaVach());
            ps.setString(3, ctSach.getTinhTrangSach());
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    // Sửa CT SÁCH
    public boolean updateCTSach(CTSach_DTO ctSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "UPDATE ctsach SET trangthai =? WHERE mavach =? AND masach =?";
            ps = connection.prepareStatement(sql);
            ps.setString(2, ctSach.getTinhTrangSach());
            ps.setString(1, ctSach.getMaVach());
            ps.setInt(3, ctSach.getMaSach());
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
    // Xóa CT sÁCH
    public boolean deleteCTSach(CTSach_DTO ctSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "UPDATE ctsach SET mavach =? WHERE mavach =? AND masach =?";
            ps = connection.prepareStatement(sql);
            ps.setString(2, "-" + ctSach.getMaVach());
            ps.setString(1, ctSach.getMaVach());
            ps.setInt(3, ctSach.getMaSach());
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;

    }
    // Tìm theo mã sách
    public ArrayList<CTSach_DTO> searchCTSachByMaSach(String maSach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach WHERE CAST(masach AS CHAR)" + " LIKE '%" + maSach + "%'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setMaVach(rs.getString(1));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
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
        return listCTSach;
    }
    // Tìm theo mã vạch
    public ArrayList<CTSach_DTO> searchCTSachByMaVach(String maVach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach WHERE mavach" + " LIKE '%" + maVach + "%'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setMaVach(rs.getString(1));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
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
        return listCTSach;
    }
}
