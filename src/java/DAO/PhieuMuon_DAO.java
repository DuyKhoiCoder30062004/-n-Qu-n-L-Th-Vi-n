/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.PhieuMuon_DTO;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhieuMuon_DAO {

    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;

    public ArrayList<PhieuMuon_DTO> getList() {
        ArrayList<PhieuMuon_DTO> listPM = new ArrayList<PhieuMuon_DTO>();
        try {
            String qry = "Select * from pheumuon";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuMuon_DTO pm = new PhieuMuon_DTO();
                pm.setMaPM(Integer.parseInt(rs.getString(1)));
                pm.setMaKhach(Integer.parseInt(rs.getString(2)));
                pm.setMaNV(Integer.parseInt(rs.getString(3)));
                LocalDate ngayLap = rs.getDate(4).toLocalDate();
                pm.setNgayLap(ngayLap);
                LocalDate hanCHot = rs.getDate(5).toLocalDate();
                pm.setHanChot(hanCHot);
                pm.setTongSL(Integer.parseInt(rs.getString(6)));
                listPM.add(pm);
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
            }
        }
        return listPM;
    }

    public boolean addPM(PhieuMuon_DTO pm) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "Insert into pheumuon values (";
            qry += pm.getMaPM() + ", " + pm.getMaKhach() + ", " + pm.getMaNV() + ", '" 
                + java.sql.Date.valueOf(pm.getNgayLap()) + "', '" 
                + java.sql.Date.valueOf(pm.getHanChot()) + "', " + pm.getTongSL() + ")";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm phiếu mượn: " + e.getMessage());
            result = false;

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updatePM(PhieuMuon_DTO pm) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "update pheumuon set ";
            qry += "makh=" + pm.getMaKhach() + ",manv=" + pm.getMaNV() + ",ngaylap='" + java.sql.Date.valueOf(pm.getNgayLap()) + "',hanchot='" + java.sql.Date.valueOf(pm.getHanChot())+ "' where mapm=" + pm.getMaPM();
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa phiếu mượn: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updateSL(int mapm, int tongSLMoi) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "update pheumuon set ";
            qry += "tongsl=" + tongSLMoi + " where mapm=" + mapm;
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi khi sửa tổng số lượng phiếu mượn: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deletePM(int mapm) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from pheumuon where mapm=" + mapm;
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa phiếu mượn: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return result;
    }

    public PhieuMuon_DTO searchByMaPM(int mapm) {
        PhieuMuon_DTO pm = null;
        try {
            String qry = "select mapm,makh,manv,ngaylap,hanchot,tongsl from pheumuon where mapm = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapm);
            rs = ps.executeQuery();
            if (rs.next()) {
                 pm = new PhieuMuon_DTO();
                pm.setMaPM(Integer.parseInt(rs.getString(1)));
                pm.setMaKhach(Integer.parseInt(rs.getString(2)));
                pm.setMaNV(Integer.parseInt(rs.getString(3)));
                LocalDate ngayLap = rs.getDate(4).toLocalDate();
                pm.setNgayLap(ngayLap);
                LocalDate hanCHot = rs.getDate(5).toLocalDate();
                pm.setHanChot(hanCHot);
                pm.setTongSL(Integer.parseInt(rs.getString(6)));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm phiếu mượn: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
        return pm;
    }
}
