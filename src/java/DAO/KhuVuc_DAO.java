/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.KhuVuc_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ADMIN
 */
public class KhuVuc_DAO {

    private dangNhapDatabase xuLyDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<KhuVuc_DTO> getList() {
        ArrayList<KhuVuc_DTO> dsKV = new ArrayList<KhuVuc_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from khuvuc WHERE makv NOT LIKE '-%'";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while (rs.next()) {
                KhuVuc_DTO kv = new KhuVuc_DTO();
                kv.setMaKV(Integer.parseInt(rs.getString(1)));
                kv.setTenKV(rs.getString(2));
                dsKV.add(kv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } //Dong ket noi
        finally {
            try {
                xuLyDB.closeConnection(conn);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dsKV;
    }

    public boolean addKV(KhuVuc_DTO kv) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Insert into khuvuc Values (";
            qry = qry + "'" + kv.getMaKV() + "','" + kv.getTenKV() + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm phiếu mượn: " + e.getMessage());
            result = false;
        } finally {
            try {
                xuLyDB.closeConnection(conn);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//    public boolean deleteKV(int ma) {
//        boolean result = true;
//        try {
//            xuLyDB = new dangNhapDatabase();
//            conn = xuLyDB.openConnection();
//            st = conn.createStatement();
//            String qry = "Delete from khuvuc where makv='" + ma + "'";
//            st.executeUpdate(qry);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
//        } finally {
//            try {
//                xuLyDB.closeConnection(conn);
//                ps.close();
//                rs.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
    
    public boolean deleteKV(int ma) {
    boolean result = true;
    try {
        xuLyDB = new dangNhapDatabase();
        conn = xuLyDB.openConnection();

        // Bắt đầu transaction
        conn.setAutoCommit(false);

        // 1. Vô hiệu hóa ràng buộc khóa ngoại
        String disableFK = "SET FOREIGN_KEY_CHECKS = 0";
        PreparedStatement psDisableFK = conn.prepareStatement(disableFK);
        psDisableFK.execute();

        // 2. Cập nhật bảng khuvuc
        String qryUpdateKV = "UPDATE khuvuc SET makv = CONCAT('-', makv) WHERE makv = ?";
        PreparedStatement psUpdateKV = conn.prepareStatement(qryUpdateKV);
        psUpdateKV.setInt(1, ma);
        psUpdateKV.executeUpdate();

        // 4. Cập nhật bảng sach
        String qryUpdateSach = "UPDATE sach SET makv = CONCAT('-', makv) WHERE makv = ?";
        PreparedStatement psUpdateSach = conn.prepareStatement(qryUpdateSach);
        psUpdateSach.setInt(1, ma);
        psUpdateSach.executeUpdate();

        // 5. Bật lại ràng buộc khóa ngoại
        String enableFK = "SET FOREIGN_KEY_CHECKS = 1";
        PreparedStatement psEnableFK = conn.prepareStatement(enableFK);
        psEnableFK.execute();

        // Xác nhận transaction
        conn.commit();
        System.out.println("Cập nhật mã NCC và đồng bộ các bảng liên quan thành công.");
    } catch (Exception e) {
        try {
            // Rollback nếu có lỗi
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        result = false;
    } finally {
        try {
            if (conn != null) xuLyDB.closeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return result;
}

    public boolean updateKV(KhuVuc_DTO kv) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update khuvuc Set ";
            qry = qry + "tenkv='" + kv.getTenKV() + "' WHERE makv='" + kv.getMaKV() + "'";
            st.executeUpdate(qry);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage());
        } finally {
            try {
                xuLyDB.closeConnection(conn);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public KhuVuc_DTO searchByMaKV(int makv) {
        KhuVuc_DTO kv = null;
        try {
            String qry = "select makv,tenkv from khuvuc where makv = ?";
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, makv);
            rs = ps.executeQuery();
            if (rs.next()) {
                kv = new KhuVuc_DTO();
                kv.setMaKV(Integer.parseInt(rs.getString(1)));
                kv.setTenKV(rs.getString(2));
            }
        } catch (NumberFormatException | SQLException e) {
            System.err.println("Lỗi khi tìm khu vực: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    xuLyDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return kv;
    }
}

