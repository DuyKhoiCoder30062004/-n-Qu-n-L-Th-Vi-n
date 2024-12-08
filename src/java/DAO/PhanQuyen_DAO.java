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
import DTO.PhanQuyen_DTO;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class PhanQuyen_DAO {

    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;

    public ArrayList<PhanQuyen_DTO> getListPQ() {
        ArrayList<PhanQuyen_DTO> listPQ = new ArrayList<PhanQuyen_DTO>();
        try {
            String qry = "Select * from phanquyen ";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhanQuyen_DTO pq = new PhanQuyen_DTO();
                pq.setMaNV(Integer.parseInt(rs.getString(1)));
                pq.setMatKhau(rs.getString(2));
                String tam = rs.getString(3);
                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
                pq.setTacVu(listTam);
                listPQ.add(pq);
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
        return listPQ;
    }

    public boolean addPQ(PhanQuyen_DTO pq) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String tam = String.join(",", pq.getTacVu());
            String qry = "Insert into phanquyen values (";
            qry += pq.getMaNV() + ",'" + pq.getMatKhau() + "','" + tam + "')";
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

    public boolean updatePQ(PhanQuyen_DTO pq) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String tam = String.join(",", pq.getTacVu());
            String qry = "update phanquyen set ";
            qry += "mk='" + pq.getMatKhau() + "',tacvu='" + tam + "' Where matk=" + pq.getMaNV();
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (Exception e) {
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

    public boolean deletePQ(int manv) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();

            // Thực hiện cập nhật trạng thái quyền
            String qry = "UPDATE phanquyen SET matk = ? WHERE matk = ?";
            ps = conn.prepareStatement(qry);

            
            ps.setInt(1, -1 * manv); 
            ps.setInt(2, manv);  

            ps.executeUpdate();

        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        } finally {
            try {
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

    public PhanQuyen_DTO searchByMaNV(int manv) {
        PhanQuyen_DTO pq = null;
        try {
            String qry = "select matk,mk,tacvu from phanquyen where matk = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, manv);
            rs = ps.executeQuery();
            if (rs.next()) {
                pq = new PhanQuyen_DTO();
                pq.setMaNV(manv);
                pq.setMatKhau(rs.getString(2));
                String tam = rs.getString(3);
                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
                pq.setTacVu(listTam);
            }
        } catch (Exception e) {
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
        return pq;
    }
}
