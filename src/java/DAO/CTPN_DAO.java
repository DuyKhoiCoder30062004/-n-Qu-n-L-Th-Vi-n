package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.CTPN_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CTPN_DAO{
    private dangNhapDatabase loginDB = null;
    private Connection connect = null;
    private PreparedStatement preStatement = null;
    private ResultSet rs = null;

    public ArrayList<CTPN_DTO> getList() {
        ArrayList<CTPN_DTO> resultList = new ArrayList<CTPN_DTO>();
        try {
            String query = "Select * from ctpn";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);
            rs = preStatement.executeQuery();
            while(rs.next()){
                CTPN_DTO ctpn = new CTPN_DTO();
                ctpn.setMaPN(rs.getInt("mapn"));
                ctpn.setMaSach(rs.getInt("masach"));
                ctpn.setSoLuong(rs.getInt("soluong"));
                ctpn.setDonGia(rs.getInt("dongia"));
                resultList.add(ctpn);
            }
        } catch (Exception e) {
            return null;
        }
        finally{
            try {
                if (rs != null) rs.close();
                if (preStatement != null) preStatement.close();
                if (connect != null) connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public boolean addCTPN(CTPN_DTO ctpn){
        boolean result = false;
        try {
            String query = "Insert into ctpn(mapn, masach, soluong, dongia) Values (?,?,?,?)";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);
            
            preStatement.setInt(1, ctpn.getMaPN());
            preStatement.setInt(2, ctpn.getMaSach());
            preStatement.setInt(3, ctpn.getSoLuong());
            preStatement.setInt(4, ctpn.getDonGia());

            int cnt = preStatement.executeUpdate();
            updatePhieuNhapTotals(ctpn.getMaPN());
            result = cnt > 0; //Kiểm tra số dòng thay đổi

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (preStatement != null) preStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public boolean updateCTPN(CTPN_DTO ctpn){
        boolean result = false;
        try {
            String query = "Update ctpn set soluong = ?, dongia = ? where mapn = ? and masach = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);

            preStatement.setInt(1, ctpn.getSoLuong());
            preStatement.setInt(2, ctpn.getDonGia());
            preStatement.setInt(3, ctpn.getMaPN());
            preStatement.setInt(4, ctpn.getMaSach());

            int cnt = preStatement.executeUpdate();
            updatePhieuNhapTotals(ctpn.getMaPN());
            result = cnt > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (preStatement != null) preStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deleteCTPN(int maPN, int maSach){
        boolean result = false;
        try {
            String query = "Delete from ctpn where mapn = ? and masach = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);

            preStatement.setInt(1, maPN);
            preStatement.setInt(2, maSach);

            int cnt = preStatement.executeUpdate();
            updatePhieuNhapTotals(maPN);
            result = cnt > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (preStatement != null) preStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deleteByMaPN(int maPN){
        boolean result = false;
        try {
            String query = "Delete from ctpn where mapn = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);

            preStatement.setInt(1, maPN);

            int cnt = preStatement.executeUpdate();
            updatePhieuNhapTotals(maPN);
            result = cnt > 0;
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (preStatement != null) preStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ArrayList<CTPN_DTO> searchByMaPN(int maPN) {
        ArrayList<CTPN_DTO> listCTPN = new ArrayList<>();;
        try {
            String qry = "Select mapn, masach, soluong, dongia from ctpn where mapn = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(qry);

            preStatement.setInt(1, maPN);
            
            rs = preStatement.executeQuery();
            while (rs.next()) {
                CTPN_DTO ctpn=new CTPN_DTO();

                ctpn.setMaPN(rs.getInt("mapn"));
                ctpn.setMaSach(rs.getInt("masach"));
                ctpn.setSoLuong(rs.getInt("soluong"));
                ctpn.setDonGia(rs.getInt("dongia"));

                listCTPN.add(ctpn);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preStatement != null) preStatement.close();
                if (connect != null) loginDB.closeConnection(connect);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listCTPN;
    }

    public CTPN_DTO searchByMaPN_MaSach(int maPN, int maSach){
        CTPN_DTO ctpn = new CTPN_DTO();
        try {
            String qry = "Select mapn, masach, soluong, dongia from ctpn where mapn = ? and masach = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(qry);

            preStatement.setInt(1, maPN);
            preStatement.setInt(2, maSach);
            
            rs = preStatement.executeQuery();
            while (rs.next()) {
                ctpn.setMaPN(rs.getInt("mapn"));
                ctpn.setMaSach(rs.getInt("masach"));
                ctpn.setSoLuong(rs.getInt("soluong"));
                ctpn.setDonGia(rs.getInt("dongia"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (preStatement != null) preStatement.close();
                if (connect != null) loginDB.closeConnection(connect);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ctpn;
    }

    private void updatePhieuNhapTotals(int maPN) {
        try {
            // Tính lại tổng số lượng và tổng tiền từ các CTPN liên quan đến phiếu nhập này
            String query = "SELECT SUM(soLuong), SUM(soLuong * donGia) FROM ctpn WHERE maPN = ?";
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setInt(1, maPN);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int tongSL = rs.getInt(1);
                double tongTien = rs.getDouble(2);

                // Cập nhật lại Phiếu Nhập với tổng số lượng và tổng tiền mới
                String updatePN = "UPDATE phieunhap SET tongSL = ?, tongTien = ? WHERE maPN = ?";
                PreparedStatement psUpdate = connect.prepareStatement(updatePN);
                psUpdate.setInt(1, tongSL);
                psUpdate.setDouble(2, tongTien);
                psUpdate.setInt(3, maPN);
                psUpdate.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}