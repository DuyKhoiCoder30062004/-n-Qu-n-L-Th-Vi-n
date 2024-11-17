package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.CTPT_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Chi Tiet Phieu Tra
 */
public class CTPT_DAO {
    private dangNhapDatabase dnDB;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // Method to get list of CTPT records
    public List<CTPT_DTO> getList() {
        List<CTPT_DTO> listCTPT = new ArrayList<>();
        try {
            String query = "SELECT * FROM ctpt";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTPT_DTO ctpt = new CTPT_DTO();
                ctpt.setMaPT(rs.getInt("mapt"));
                ctpt.setMaSach(rs.getInt("masach"));
                ctpt.setMaVachLoi(rs.getString("mavachloi")); 
                ctpt.setNgayTra(rs.getDate("ngaytra").toLocalDate());
                ctpt.setSoLuong(rs.getInt("soluong"));
                listCTPT.add(ctpt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return listCTPT;
    }

    // Method to add a new CTPT record
    public boolean addCTPT(CTPT_DTO ctpt) {
        boolean success = false;
        try {
            String query = "INSERT INTO ctpt (mapt, masach, mavachloi, ngaytra, soluong) VALUES (?, ?, ?, ?, ?)";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, ctpt.getMaPT());
            ps.setInt(2, ctpt.getMaSach());
            ps.setString(3, ctpt.getMaVachLoi()); 
            ps.setDate(4, java.sql.Date.valueOf(ctpt.getNgayTra()));
            ps.setInt(5, ctpt.getSoLuong());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return success;
    }

    // Method to update an existing CTPT record
    public boolean updateCTPT(CTPT_DTO ctpt) {
        boolean success = false;
        try {
            String query = "UPDATE ctpt SET masach = ?, mavachloi = ?, ngaytra = ?, soluong = ? WHERE mapt = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, ctpt.getMaSach());
            ps.setString(2, ctpt.getMaVachLoi()); 
            ps.setDate(3, java.sql.Date.valueOf(ctpt.getNgayTra()));
            ps.setInt(4, ctpt.getSoLuong());
            ps.setInt(5, ctpt.getMaPT());
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return success;
    }

    // Method to delete a CTPT record by maPT
    public boolean deleteCTPT(int maPT) {
        boolean success = false;
        try {
            String query = "DELETE FROM ctpt WHERE mapt = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, maPT);
            ps.executeUpdate();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return success;
    }

    // Method to search CTPT records by maPT
    public List<CTPT_DTO> searchByMaPT(int maPT) {
        List<CTPT_DTO> ctptList = new ArrayList<>();
        try {
            String query = "SELECT * FROM ctpt WHERE mapt = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, maPT);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTPT_DTO ctpt = new CTPT_DTO();
                ctpt.setMaPT(rs.getInt("mapt"));
                ctpt.setMaSach(rs.getInt("masach"));
                ctpt.setMaVachLoi(rs.getString("mavachloi")); 
                ctpt.setNgayTra(rs.getDate("ngaytra").toLocalDate());
                ctpt.setSoLuong(rs.getInt("soluong"));
                ctptList.add(ctpt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return ctptList;
    }

    // Method to close database resources
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) dnDB.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
