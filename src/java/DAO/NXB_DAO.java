/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.NXB_DTO;
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
public class NXB_DAO {

    private dangNhapDatabase xuLyDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<NXB_DTO> getList() {
        ArrayList<NXB_DTO> dsNXB = new ArrayList<NXB_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from nxb WHERE manxb NOT LIKE '-%' ";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while (rs.next()) {
                NXB_DTO nxb = new NXB_DTO();
                nxb.setMaNXB(Integer.parseInt(rs.getString(1)));
                nxb.setTenNXB(rs.getString(2));
                nxb.setDcNXB(rs.getString(3));
                dsNXB.add(nxb);
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
        return dsNXB;
    }

    public boolean addNXB(NXB_DTO nxb) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Insert into nxb Values (";
            qry = qry + "'" + nxb.getMaNXB() + "','" + nxb.getTenNXB() + "','" + nxb.getDcNXB() + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
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

//    public boolean deleteNXB(int ma){
//        boolean result = true;
//        try{
//                xuLyDB = new dangNhapDatabase();
//                conn = xuLyDB.openConnection();
//                st = conn.createStatement();
//                String qry="Delete from nxb where manxb='"+ma+"'";
//                st.executeUpdate(qry);
//            }catch(Exception e){
//                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
//            }finally {
//                try {
//                    xuLyDB.closeConnection(conn);
//                    ps.close();
//                    rs.close();
//                } catch (SQLException e) {
//                     e.printStackTrace();
//                }
//            }
//        return result;
//    }
//    public boolean deleteNXB(int ma) {
//        boolean result = true;
//        try {
//            xuLyDB = new dangNhapDatabase();
//            conn = xuLyDB.openConnection();
//
//            // Sử dụng PreparedStatement để an toàn hơn
//            String qry = "UPDATE nxb SET manxb = CONCAT('-', manxb) WHERE manxb = ?";
//            ps = conn.prepareStatement(qry);
//            ps.setInt(1, ma);
//
//            int rowsUpdated = ps.executeUpdate();
//            if (rowsUpdated > 0) {
//                System.out.println("Đã đánh dấu xóa thành công cho mã NXB: " + ma);
//            } else {
//                result = false;
//                System.err.println("Không tìm thấy mã NXB cần xóa: " + ma);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
//            result = false;
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (conn != null) {
//                    xuLyDB.closeConnection(conn);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }
    
    // Ẩn khongo xóa
    public boolean deleteNXB(int ma) {
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

            // 2. Cập nhật bảng nxb
            String qryUpdateNXB = "UPDATE nxb SET manxb = CONCAT('-', manxb) WHERE manxb = ?";
            PreparedStatement psUpdateNXB = conn.prepareStatement(qryUpdateNXB);
            psUpdateNXB.setInt(1, ma);
            psUpdateNXB.executeUpdate();

            // 4. Cập nhật bảng sach
            String qryUpdateSach = "UPDATE sach SET manxb = CONCAT('-', manxb) WHERE manxb = ?";
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
                if (conn != null) {
                    xuLyDB.closeConnection(conn);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updateNXB(NXB_DTO nxb) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update nxb Set ";
            qry = qry + "tennxb='" + nxb.getTenNXB() + "', dcnxb='" + nxb.getDcNXB() + "' WHERE manxb='" + nxb.getMaNXB() + "'";
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

    public NXB_DTO searchByMaNXB(int manxb) {
        NXB_DTO nxb = null;
        try {
            String qry = "select * from nxb where manxb = ?";
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, manxb);
            rs = ps.executeQuery();
            if (rs.next()) {
                nxb = new NXB_DTO();
                nxb.setMaNXB(Integer.parseInt(rs.getString(1)));
                nxb.setTenNXB(rs.getString(2));
                nxb.setDcNXB(rs.getString(3));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm nxb: " + e.getMessage());
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
        return nxb;
    }
}
