/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.DocGia_DTO;
import ConnectDB.dangNhapDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DocGia_DAO {

    private dangNhapDatabase xuLyDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<DocGia_DTO> getList() {
        ArrayList<DocGia_DTO> docGiaList = new ArrayList<>();
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from docgia WHERE madg NOT LIKE '-%'";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while (rs.next()) {
                DocGia_DTO dg = new DocGia_DTO();
                dg.setMaDG(Integer.parseInt(rs.getString(1)));
                dg.setHoDG(rs.getString(2));
                dg.setTenDG(rs.getString(3));
                dg.setDiaChi(rs.getString(4));
                dg.setSoDienThoai(rs.getString(5));
                LocalDate ngaySinh = rs.getDate(6).toLocalDate();
                dg.setNgaySinh(ngaySinh);
                docGiaList.add(dg);
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
        return docGiaList;
    }

    public boolean addDocGia(DocGia_DTO dg) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Insert into docgia Values (";
            qry = qry + "'" + dg.getMaDG() + "','" + dg.getHoDG() + "','" + dg.getTenDG() + "','" + dg.getDiaChi() + "','" + dg.getSoDienThoai() + "','" + dg.getNgaySinh() + "')";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            result = false;
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

    public boolean updateDocGia(DocGia_DTO dg) {
        boolean result = true;
        try {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update docgia Set ";
            qry = qry + "ho='" + dg.getHoDG() + "',ten='" + dg.getTenDG() + "',dc='" + dg.getDiaChi() + "',sdt='"
                    + dg.getSoDienThoai() + "',ngaysinh='" + dg.getNgaySinh() + "' WHERE madg='" + dg.getMaDG() + "'";
            st.executeUpdate(qry);
        } catch (SQLException e) {
            result = false;
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

//    public boolean deleteDocGia(int ma){
//        boolean result = true;
//        try{
//                xuLyDB = new dangNhapDatabase();
//                conn = xuLyDB.openConnection();
//                st = conn.createStatement();
//                String qry="Delete from docgia where madg='"+ma+"'";
//                st.executeUpdate(qry);
//            }catch(Exception e){
//                result=false;
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
    // ẩn không xóa
    public boolean deleteDocGia(int ma) {
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

            // 2. Cập nhật bảng docgia
            String qryUpdateDG = "UPDATE docgia SET madg = CONCAT('-', madg) WHERE madg = ?";
            PreparedStatement psUpdateDG = conn.prepareStatement(qryUpdateDG);
            psUpdateDG.setInt(1, ma);
            psUpdateDG.executeUpdate();

            // 4. Cập nhật bảng phiuemuon
            String qryUpdatePM = "UPDATE phieumuon SET mapm = CONCAT('-', mapm) WHERE mapm = ?";
            PreparedStatement psUpdatePM = conn.prepareStatement(qryUpdatePM);
            psUpdatePM.setInt(1, ma);
            psUpdatePM.executeUpdate();

            // 5. Bật lại ràng buộc khóa ngoại
            String enableFK = "SET FOREIGN_KEY_CHECKS = 1";
            PreparedStatement psEnableFK = conn.prepareStatement(enableFK);
            psEnableFK.execute();

            // Xác nhận transaction
            conn.commit();
            System.out.println("Cập nhật madg và đồng bộ các bảng liên quan thành công.");
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

    public DocGia_DTO searchByMaDG(int madg) {
        DocGia_DTO dg = null;
        try {
            String qry = "select * from docgia where madg = ?";
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, madg);
            rs = ps.executeQuery();
            if (rs.next()) {
                dg = new DocGia_DTO();
                dg.setMaDG(Integer.parseInt(rs.getString(1)));
                dg.setHoDG(rs.getString(2));
                dg.setTenDG(rs.getString(3));
                dg.setDiaChi(rs.getString(4));
                dg.setSoDienThoai(rs.getString(5));
                LocalDate ngaySinh = rs.getDate(6).toLocalDate();
                dg.setNgaySinh(ngaySinh);

            }
        } catch (NumberFormatException | SQLException e) {
            System.err.println("Lỗi khi tìm độc giả: " + e.getMessage());
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
        return dg;
    }

//    public DocGiaDTO findDocGiaByMaKhach(int maKhach) throws SQLException {
//        String query = "SELECT * FROM DocGia WHERE MaKhach = ?";
//        PreparedStatement statement = conn.prepareStatement(query);
//        statement.setInt(1, maKhach);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            DocGiaDTO docGia = new DocGiaDTO();
//                docGia.setMaDG(Integer.parseInt(rs.getString(1)));
//                docGia.setHoDG(rs.getString(2));
//                docGia.setTenDG(rs.getString(3));
//                docGia.setDiaChi(rs.getString(4));
//                docGia.setSoDienThoai(rs.getString(5));
//                LocalDate ngaySinh = rs.getDate(6).toLocalDate();
//                docGia.setNgaySinh(ngaySinh);
//            return docGia;
//        }
//        
//        return null;
//    }
}
