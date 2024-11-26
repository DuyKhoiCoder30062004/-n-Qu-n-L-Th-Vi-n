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
    public ArrayList<NXB_DTO> getList()
    {
        ArrayList<NXB_DTO> dsNXB = new ArrayList<NXB_DTO>();
        try
        {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from nxb ";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while(rs.next())
            {
                NXB_DTO nxb = new NXB_DTO();
                nxb.setMaNXB(Integer.parseInt(rs.getString(1)));
                nxb.setTenNXB(rs.getString(2));
                nxb.setDcNXB(rs.getString(3));
                dsNXB.add(nxb);
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
    return dsNXB;
    }
    public boolean addNXB(NXB_DTO nxb){
        boolean result = true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry="Insert into nxb Values (";
            qry = qry + "'" + nxb.getMaNXB() + "','" + nxb.getTenNXB() + "','" + nxb.getDcNXB() + "')";
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

    public boolean deleteNXB(int ma){
        boolean result = true;
        try{
                xuLyDB = new dangNhapDatabase();
                conn = xuLyDB.openConnection();
                st = conn.createStatement();
                String qry="Delete from nxb where manxb='"+ma+"'";
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
    public boolean updateNXB(NXB_DTO nxb){
        boolean result =true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn =  xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update nxb Set ";
//            qry = qry +"TENNXB='"+ nxb.getTenNXB() + "'"+",DCNXB='" + nxb.getDcNXB() + "',SDTNXB='" + nxb.getSdtNXB() + "',GMAIL='" + nxb.getGmail() + "'";
//            if(nxb.getTrangThai().equals("Không chặn"))
//                qry=qry+",TRANGTHAI="+1+" WHERE MANXB='"+nxb.getMaNXB()+"'";
//            else qry=qry+",TRANGTHAI="+0+" where MANXB='"+nxb.getMaNXB()+"'";
            qry = qry +"tennxb='"+ nxb.getTenNXB() + "', dcnxb='" + nxb.getDcNXB() + "' WHERE manxb='"+nxb.getMaNXB()+"'";
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
}