/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.PhieuMuon_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HELLO
 */
public class ThongkeDAO {

    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;

//sachDAO (getTotalSoLuong), CTPMDAO (getTotal_SachMuon)  , PhieuPhatDAO (getTotal_tienPhat), CTPMDAO (getSoLuongAndYear), CTPPDAO (getTien_AndYear)
    public int getTotalSoLuong() {
        int totalSoLuong = 0;  // Variable to hold the sum of soLuong
        String query = "SELECT SUM(soluong) AS TotalSoLuong FROM sach";  // Query to get the sum of soLuong
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(query);  // Prepare the statement
            rs = ps.executeQuery();  // Execute the query
            if (rs.next()) {
                totalSoLuong = rs.getInt("TotalSoLuong");  // Retrieve the sum from the result set
            }
        } catch (Exception e) {
            System.out.println(e);  // Handle exceptions
        }
        return totalSoLuong;  // Return the sum
    }

    public int getTotal_SachMuon() {
        int totalSoLuong = 0;  // Variable to hold the sum of soLuong
        String query = "SELECT SUM(tongsl) AS TotalSoLuong FROM pheumuon";  // Query to get the sum of soLuong
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();  // Establish the connection
            ps = conn.prepareStatement(query);  // Prepare the statement
            rs = ps.executeQuery();  // Execute the query
            if (rs.next()) {
                totalSoLuong = rs.getInt("TotalSoLuong");  // Retrieve the sum from the result set
            }
        } catch (Exception e) {
            System.out.println(e);  // Handle exceptions
        }
//    System.out.print("query: " + query);
        return totalSoLuong;  // Return the sum
    }

    public int getTotal_tienPhat() {
        int totalFine = 0;  // Variable to hold the sum of soLuong
        String query = "SELECT SUM(tongtien) AS TotalSoLuong FROM phieuphat";  // Query to get the sum of soLuong
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection(); // Establish the connection
            ps = conn.prepareStatement(query);  // Prepare the statement
            rs = ps.executeQuery();  // Execute the query
            if (rs.next()) {
                totalFine = rs.getInt("TotalSoLuong");  // Retrieve the sum from the result set
            }
        } catch (Exception e) {
            System.out.println(e);  // Handle exceptions
        }
//    System.out.print("query: " + query);
        return totalFine;  // Return the sum
    }

    public List<PhieuMuon_DTO> getSoLuongAndYear() {
        List<PhieuMuon_DTO> pmList = new ArrayList<>();
        String sql = "SELECT tongsl,ngaylap FROM pheumuon";
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(sql);  
            rs = ps.executeQuery();  

            // Process the result setS
            while (rs.next()) {
                int soLuong = rs.getInt("tongsl");
//                int ngayMuonYear = rs.getInt("ngayMuonYear");
//                int ngayMuonMonth = rs.getInt("ngayMuonMonth");
                // Create a new CTPM object and set the values
                PhieuMuon_DTO pm=new PhieuMuon_DTO();
                pm.setTongSL(soLuong);

                // Set 'ngayMuon' to the first day of the retrieved year using Calendar
                pm.setNgayLap(rs.getDate("ngaylap").toLocalDate());

                // Add the CTPM object to the list
                pmList.add(pm);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());  // Handle any SQL exceptions
        } catch (Exception ex) {
            Logger.getLogger(PhieuMuon_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pmList;  // Return the list of CTPM objects
    }

    public List<CTPP_DTO> getTien_AndYear() {
        List<CTPP_DTO> ctppList = new ArrayList<>();
        String sql = "SELECT tien,ngaylap FROM ctpp";
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(sql);  // Prepare the SQL query
            rs = ps.executeQuery();  // Execute the query and get the result set

            // Process the result set
            while (rs.next()) {
                int soTienPhat = rs.getInt("tien");
//                int ngayLapYear = rs.getInt("ngayLapYear");
//                int ngayLapMonth = rs.getInt("ngayLapMonth");
                // Create a new CTPM object and set the values
                CTPP_DTO ctpp = new CTPP_DTO();
                ctpp.setTien(soTienPhat);

                // Set 'ngayMuon' to the first day of the retrieved year using Calendar
//                LocalDate ngayLap = LocalDate.of(ngayLapYear, ngayLapMonth, 1);
                ctpp.setNgayLap(rs.getDate("ngaylap").toLocalDate());

                // Add the CTPM object to the list
                ctppList.add(ctpp);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());  // Handle any SQL exceptions
        } catch (Exception ex) {
            Logger.getLogger(CTPP_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctppList;  // Return the list of CTPM objects
    }
}
