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
    public ArrayList<NCC_DTO> getList()
    {
        ArrayList<NCC_DTO> dsNCC = new ArrayList<NCC_DTO>();
        try
        {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from ncc ";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while(rs.next())
            {
                NCC_DTO ncc = new NCC_DTO();
                ncc.setMaNCC(Integer.parseInt(rs.getString(1)));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDcNCC(rs.getString(3));
                ncc.setSdtNCC(rs.getString(4));
//                int status = rs.getInt(5);
//                if(status==1)
//                    ncc.setTrangThai("Không chặn");
//                else ncc.setTrangThai("Chặn"); 
                ncc.setTrangThai(rs.getString(5));
                dsNCC.add(ncc);
            }
    }catch (SQLException e){
        e.printStackTrace();
    }
    //Dong ket noi
    finally{
        try{
            xuLyDB.closeConnection(conn);
            ps.close();
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    return dsNCC;
    }
    public boolean addNCC(NCC_DTO ncc){
        boolean result = true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry="Insert into ncc Values (";
            qry = qry + "'" + ncc.getMaNCC() + "','" + ncc.getTenNCC() + "','" + ncc.getDcNCC() + "','" + ncc.getSdtNCC() + "','" + ncc.getTrangThai() + "')";
            st=conn.createStatement();
            st.executeUpdate(qry);
            }catch(Exception e){
                        JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }
            finally {
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

    public boolean deleteNCC(int ma){
        boolean result = true;
        try{
                xuLyDB = new dangNhapDatabase();
                conn = xuLyDB.openConnection();
                st = conn.createStatement();
                String qry="Delete from ncc where mancc='"+ma+"'";
                st.executeUpdate(qry);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
            }finally {
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
    public boolean updateNCC(NCC_DTO ncc){
        boolean result =true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn =  xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update ncc Set ";
            qry = qry +"tenncc='"+ ncc.getTenNCC() + "',dcncc='" + ncc.getDcNCC() + "',sdt='" + ncc.getSdtNCC() + "',trangthai='" + ncc.getTrangThai()+ "' WHERE mancc='"+ncc.getMaNCC()+"'";
            st.executeUpdate(qry);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage());
        }finally {
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
            String qry = "select mancc,tenncc,dcncc,sdt,trangthai where mancc = ?";
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mancc);
            rs = ps.executeQuery();
            if (rs.next()) {
                ncc.setMaNCC(Integer.parseInt(rs.getString(1)));
                ncc.setTenNCC(rs.getString(2));
                ncc.setDcNCC(rs.getString(3));
                ncc.setSdtNCC(rs.getString(4));
                ncc.setTrangThai(rs.getString(5));
            }
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) xuLyDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ncc;
    }
}
