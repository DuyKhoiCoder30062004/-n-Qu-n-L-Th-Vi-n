package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectDB.dangNhapDatabase;
import DTO.PhieuNhap_DTO;

public class PhieuNhap_DAO {
    private dangNhapDatabase loginDB = null;
    private Connection connect = null;
    private PreparedStatement preStatement = null;
    private ResultSet rs = null;

    public ArrayList<PhieuNhap_DTO> getList() {
        ArrayList<PhieuNhap_DTO> resultList = new ArrayList<PhieuNhap_DTO>();
        try {
            String query = "Select * from phieunhap";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);
            rs = preStatement.executeQuery();
            while(rs.next()){
                PhieuNhap_DTO pn = new PhieuNhap_DTO();
                
                pn.setMaPN(rs.getInt("mapn"));
                pn.setMaNCC(rs.getInt("mancc"));
                pn.setMaNV(rs.getInt("manv"));
                pn.setNgayLap(rs.getDate("ngaylap").toLocalDate());
                pn.setTongSL(rs.getInt("tongsl"));
                pn.setTongTien(rs.getDouble("tongtien"));
                
                resultList.add(pn);
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

    public boolean addPN(PhieuNhap_DTO pn){
        boolean result = false;
        try {
            String query = "Insert into phieunhap(mapn, mancc, manv, ngaylap, tongsl, tongtien) Values (?,?,?,?,?,?)";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);
            
            preStatement.setInt(1, pn.getMaPN());
            preStatement.setInt(2, pn.getMaNCC());
            preStatement.setInt(3, pn.getMaNV());
            preStatement.setDate(4, java.sql.Date.valueOf(pn.getNgayLap()));
            preStatement.setInt(5, pn.getTongSL());
            preStatement.setDouble(6, pn.getTongTien());

            int cnt = preStatement.executeUpdate();
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

    public boolean updatePN(PhieuNhap_DTO pn){
        boolean result = false;
        try {
            String query = "Update phieunhap set manv = ?, mancc = ?, ngaylap = ?  where mapn = ? ";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);

            preStatement.setInt(1, pn.getMaNV());
            preStatement.setInt(2, pn.getMaNCC());
            preStatement.setDate(3, java.sql.Date.valueOf(pn.getNgayLap()));
            preStatement.setInt(4, pn.getMaPN());

            int cnt = preStatement.executeUpdate();
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
            String query = "Delete from phieunhap where mapn = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(query);

            preStatement.setInt(1, maPN);

            int cnt = preStatement.executeUpdate();

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

    public PhieuNhap_DTO searchByMaPN(int maPN) {
        PhieuNhap_DTO list = null;
        try {
            String qry = "Select mapn, mancc, manv,ngaylap ,tongsl, tongtien from phieunhap where mapn = ?";
            loginDB = new dangNhapDatabase();
            connect = loginDB.openConnection();
            preStatement = connect.prepareStatement(qry);

            preStatement.setInt(1, maPN);
            
            rs = preStatement.executeQuery();
            while (rs.next()) {
                list = new PhieuNhap_DTO();
                list.setMaPN(rs.getInt("mapn"));
                list.setMaNCC(rs.getInt("mancc"));
                list.setMaNV(rs.getInt("manv"));
                java.sql.Date sqlDate = rs.getDate("ngaylap");
                if (sqlDate != null) {
                    LocalDate localDate = sqlDate.toLocalDate(); 
                    list.setNgayLap(localDate); 
                }
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
        return list;
    }

}