/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.NCC_DTO;
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
public class NCC_DAO {

    private dangNhapDatabase xuLyDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<NCC_DTO> getList() {
        ArrayList<NCC_DTO> dsNCC = new ArrayList<NCC_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "SELECT * FROM ncc WHERE mancc NOT LIKE '-%'";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while (rs.next()) {
                NCC_DTO ncc = new NCC_DTO();
                ncc.setMaNCC(Integer.parseInt(rs.getString(1)));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDcNCC(rs.getString(3));
                ncc.setSdtNCC(rs.getString(4));
                ncc.setTrangThai(rs.getString(5));
                dsNCC.add(ncc);
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
        return dsNCC;
    }

    public boolean addNCC(NCC_DTO ncc) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Insert into ncc Values (";
            qry = qry + "'" + ncc.getMaNCC() + "','" + ncc.getTenNCC() + "','" + ncc.getDcNCC() + "','" + ncc.getSdtNCC() + "','" + ncc.getTrangThai() + "')";
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

//    public boolean deleteNCC(int ma){
//        boolean result = true;
//        try{
//                xuLyDB = new dangNhapDatabase();
//                conn = xuLyDB.openConnection();
//                st = conn.createStatement();
//                String qry="Delete from ncc where mancc='"+ma+"'";
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
    // Ẩn không xóa
//    public boolean deleteNCC(int ma) {
//        boolean result = true;
//        try {
//            xuLyDB = new dangNhapDatabase();
//            conn = xuLyDB.openConnection();
//
//            // Bắt đầu transaction
//            conn.setAutoCommit(false);
//
//            // Sử dụng PreparedStatement để an toàn hơn
//            String qry = "UPDATE ncc SET mancc = CONCAT('-', mancc) WHERE mancc = ?";
//            ps = conn.prepareStatement(qry);
//            ps.setInt(1, ma);
//
//            int rowsUpdated = ps.executeUpdate();
//            if (rowsUpdated > 0) {
//            // Cập nhật bảng phieunhap: Đồng bộ mã NCC
//            String qryUpdatePhieuNhap = "UPDATE phieunhap SET mancc = CONCAT('-', ?) WHERE mancc = ?";
//            ps = conn.prepareStatement(qryUpdatePhieuNhap);
////            ps.setInt(1, ma);
//            ps.setInt(2, ma);
//            ps.executeUpdate();
//
//            // Cập nhật bảng sach: Đồng bộ mã NCC
//            String qryUpdateSach = "UPDATE sach SET mancc = CONCAT('-', ?) WHERE mancc = ?";
//            ps = conn.prepareStatement(qryUpdateSach);
////            ps.setInt(1, ma);
//            ps.setInt(5, ma);
//            ps.executeUpdate();
//
//            // Xác nhận transaction
//            conn.commit();
//            System.out.println("Đã đánh dấu xóa mã NCC và cập nhật các bảng liên quan thành công.");
//        } else {
//            result = false;
//            System.err.println("Không tìm thấy mã NCC cần xóa: " + ma);
//        }
//    } catch (Exception e) {
//        try {
//            conn.rollback(); // Rollback nếu có lỗi
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
//        result = false;
//    } finally {
//        try {
//            if (ps != null) ps.close();
//            if (conn != null) xuLyDB.closeConnection(conn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    return result;
//}
    public boolean deleteNCC(int ma) {
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

            // 2. Cập nhật bảng ncc
            String qryUpdateNCC = "UPDATE ncc SET mancc = CONCAT('-', mancc) WHERE mancc = ?";
            PreparedStatement psUpdateNCC = conn.prepareStatement(qryUpdateNCC);
            psUpdateNCC.setInt(1, ma);
            psUpdateNCC.executeUpdate();

            // 3. Cập nhật bảng phieunhap
            String qryUpdatePhieuNhap = "UPDATE phieunhap SET mancc = CONCAT('-', mancc) WHERE mancc = ?";
            PreparedStatement psUpdatePhieuNhap = conn.prepareStatement(qryUpdatePhieuNhap);
            psUpdatePhieuNhap.setInt(1, ma);
            psUpdatePhieuNhap.executeUpdate();

            // 4. Cập nhật bảng sach
            String qryUpdateSach = "UPDATE sach SET mancc = CONCAT('-', mancc) WHERE mancc = ?";
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

    public boolean isActiveNCC(int mancc) {
        boolean isActive = false;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();

            // Truy vấn trạng thái NCC
            String qry = "SELECT trangthai FROM ncc WHERE mancc = ?";
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mancc);
            rs = ps.executeQuery();

            if (rs.next()) {
                String status = rs.getString("trangthai");
                isActive = "Hoạt động".equals(status); //Trạng thái "hoạt động"
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return isActive;
    }

    public boolean updateNCC(NCC_DTO ncc) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update ncc Set ";
            qry = qry + "tenncc='" + ncc.getTenNCC() + "',dcncc='" + ncc.getDcNCC() + "',sdt='" + ncc.getSdtNCC() + "',trangthai='" + ncc.getTrangThai() + "' WHERE mancc='" + ncc.getMaNCC() + "'";
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

    public NCC_DTO searchByMaNCC(int mancc) {
        NCC_DTO ncc = null;
        try {
            String qry = "select * from ncc where mancc = ?";
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mancc);
            rs = ps.executeQuery();
            if (rs.next()) {
                ncc = new NCC_DTO();
                ncc.setMaNCC(Integer.parseInt(rs.getString(1)));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDcNCC(rs.getString(3));
                ncc.setSdtNCC(rs.getString(4));
                ncc.setTrangThai(rs.getString(5));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm ncc: " + e.getMessage());
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
        return ncc;
    }
}
