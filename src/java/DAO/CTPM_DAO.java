/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;
import DTO.CTPM_DTO;
import java.util.Arrays;
/**
 *
 * @author ADMIN
 */
public class CTPM_DAO {
    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;
    public ArrayList<CTPM_DTO> getList(){
        ArrayList<CTPM_DTO> listCTPM=new ArrayList<CTPM_DTO>();
        try {
            String qry = "Select * from ctpm ";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while(rs.next()){
                CTPM_DTO ctpm=new CTPM_DTO();
                ctpm.setMaPM(Integer.parseInt(rs.getString(1)));
                ctpm.setMaSach(Integer.parseInt(rs.getString(2)));
                ctpm.setSoLuong(Integer.parseInt(rs.getString(3)));
                ctpm.setTrangthai(rs.getString(4));
                listCTPM.add(ctpm);
            }
        }catch (Exception e) {
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
        return listCTPM;
    }
    public boolean addCTPM(CTPM_DTO ctpm)
    {
         boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "Insert into ctpm values (";
            qry += ctpm.getMaPM() + "," + ctpm.getMaSach() + "," + ctpm.getSoLuong() +",'"+ctpm.getTrangthai()+"')";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
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
    public boolean update(CTPM_DTO ctpm)
    {
         boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "update ctpm set";
            qry += "soluong='" + ctpm.getMaSach()+",trangthai='"+ctpm.getTrangthai() + "' Where mapm=" + ctpm.getMaPM()+" and masach="+ctpm.getMaSach();
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
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
    public boolean deleteCTPM(int mapm,int masach){
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from ctpm where matk=" + mapm+" and masach="+masach;
            st.executeUpdate(qry);
        } catch (Exception e) {
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
    public boolean deleteByMaPM(int mapm){
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from ctpm where matk=" + mapm;
            st.executeUpdate(qry);
        } catch (Exception e) {
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
    public ArrayList<CTPM_DTO> searchByMaPM(int mapm) {
        ArrayList<CTPM_DTO> listCTPM=new ArrayList<>();;
        try {
            String qry = "select mapm,masach,soluong,trangthai from ctpm where mapm = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapm);
            rs = ps.executeQuery();
            if (rs.next()) {
                CTPM_DTO ctpm=new CTPM_DTO();
                ctpm.setMaPM(mapm);
                ctpm.setMaSach(Integer.parseInt(rs.getString(2)));
                ctpm.setSoLuong(Integer.parseInt(rs.getString(3)));
                ctpm.setTrangthai(rs.getString(4));
                listCTPM.add(ctpm);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) dnDB.closeConnection(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listCTPM;
    }
}
