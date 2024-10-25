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
import DTO.Loi_DTO;


public class Loi_DAO {

    private dangNhapDatabase dnDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<Loi_DTO> getListLoi() {
        ArrayList<Loi_DTO> listLoi = new ArrayList<Loi_DTO>();
        try {
            String sql = "SELECT * FROM loi";
            dnDB = new dangNhapDatabase();
            connection = dnDB.openConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Loi_DTO loi = new Loi_DTO();
                loi.setTenLoi(rs.getString(1));
                loi.setPhamTramtien(rs.getFloat(2));
                listLoi.add(loi);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        } finally {
            try {
                if (connection != null) {
                    dnDB.closeConnection(connection);
                }
                if (ps != null) {
                    ps.close();
                }
                rs.close();
            } catch (SQLException e) {
            }
        }
        return listLoi;
    }

    public boolean addLoi(Loi_DTO loi) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            connection = dnDB.openConnection();
            String qry = "INSERT INTO loi VALUES (";
            qry += "'" + loi.getTenLoi() + "'," + loi.getPhamTramtien() + ")";
            st = connection.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
            result = false;
        } finally {
            try {
                dnDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updateLoi(Loi_DTO loi) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            connection = dnDB.openConnection();
            st = connection.createStatement();
            String qry = "Update loi Set ";
            qry += "phantramtien=" + loi.getPhamTramtien() + " WHERE tenloi='" + loi.getTenLoi() + "'";
        } catch (Exception e) {
            result = false;
        } finally {
            try {
                dnDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deleteLoi(String tenLoi) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            connection = dnDB.openConnection();
            st = connection.createStatement();
            String qry = "Delete from loi where tenloi='" + tenLoi + "'";
            st.executeUpdate(qry);

        } catch (Exception e) {
            result = false;
        } finally {
            try {
                dnDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Loi_DTO searchByTenLoi(String tenLoi) {
        Loi_DTO loi = null;
        String qry = "SELECT tenloi,phantramtien FROM loi WHERE tenloi = ?";
        try {
            dnDB = new dangNhapDatabase();
            connection = dnDB.openConnection();
            ps = connection.prepareStatement(qry);
            ps.setString(1,tenLoi);
            rs = ps.executeQuery();
            if(rs.next()){
                loi=new Loi_DTO();
                loi.setTenLoi(rs.getString(1));
                loi.setPhamTramtien(rs.getFloat(2));
            }
        }catch (Exception e) {
        } finally {
            try {
                dnDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return loi;
    }
}
