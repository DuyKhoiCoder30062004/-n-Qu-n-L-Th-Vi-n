/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.PhieuPhat_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class PhieuPhat_DAO {
     private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;
    public ArrayList<PhieuPhat_DTO> getList()
    {
        ArrayList<PhieuPhat_DTO> listPP=new ArrayList<PhieuPhat_DTO>();
        try {
            String qry = "Select * from phieuphat ";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuPhat_DTO pq = new PhieuPhat_DTO();
                pq.setMaPP(Integer.parseInt(rs.getString(1)));
                pq.setMaNV(Integer.parseInt(rs.getString(2)));
                pq.setMaPT(Integer.parseInt(rs.getString(3)));
                pq.setTongTien(rs.getFloat(4));
                listPP.add(pq);
            }
        } catch (Exception e) {
            System.err.println("Lỗi getList: " + e.getMessage());
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
        return listPP;
    }
    public boolean addPP(PhieuPhat_DTO pp)
    {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "Insert into phieuphat values (";
            qry += pp.getMaPP() + "," + pp.getMaNV() + "," +pp.getMaPT() +","+pp.getTongTien()+ ")";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi thêm: " + e.getMessage());
            result = false;
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
                    dnDB.closeConnection(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public boolean updatePP(PhieuPhat_DTO pp) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
           
            String qry = "update phieuphat set ";
            qry += "manv="+pp.getMaNV()+",mapt="+pp.getMaPT()+",tongtien="+pp.getTongTien()+" Where mapp="+pp.getMaPP();
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi sửa: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) dnDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public boolean updateTT(int mapp,double ttnew) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
           
            String qry = "update phieuphat set ";
            qry += "tongtien="+ttnew+" where mapp="+mapp;
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
             System.err.println("Lỗi sửa: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) dnDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public boolean deletePP(int mapp) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from phieuphat where mapp=" + mapp;
            st.executeUpdate(qry);
        } catch (Exception e) {
            System.err.println("Lỗi xóa: " + e.getMessage());
            result = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) dnDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public PhieuPhat_DTO searchByMaPP(int mapp) {
        PhieuPhat_DTO pq = null;
        try {
            String qry = "select mapp,manv,mapt,tongtien from phieuphat where mapp = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapp);
            rs = ps.executeQuery();
            if (rs.next()) {
                pq=new PhieuPhat_DTO();
                pq.setMaPP(mapp);
                pq.setMaNV(Integer.parseInt(rs.getString(2)));
                pq.setMaPT(Integer.parseInt(rs.getString(3)));
                pq.setTongTien(rs.getFloat(4));
            }
        } catch (Exception e) {
            System.err.println("Lỗi search: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) dnDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pq;
    }
}
