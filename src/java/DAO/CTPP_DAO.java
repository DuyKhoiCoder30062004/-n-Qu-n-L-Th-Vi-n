/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.CTPP_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
public class CTPP_DAO {

    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;

    public ArrayList<CTPP_DTO> getList() {
        ArrayList<CTPP_DTO> listPQ = new ArrayList<CTPP_DTO>();
        try {
            String qry = "Select * from ctpp ";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTPP_DTO pq = new CTPP_DTO();
                pq.setMaPP(Integer.parseInt(rs.getString(1)));
                pq.setMaSach(Integer.parseInt(rs.getString(2)));
                pq.setMaVach(rs.getString(3));
                pq.setNgayLap(rs.getDate(4).toLocalDate());
                String tam = rs.getString(5);

                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
                pq.setLiDo(listTam);
                pq.setTien(rs.getFloat(6));
                listPQ.add(pq);
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
                e.printStackTrace();
            }
        }
        return listPQ;
    }

    public boolean addCTPP(CTPP_DTO pq) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String tam = String.join(",", pq.getLiDo());
            String qry = "Insert into ctpp values (";
            qry += pq.getMaPP() + "," + pq.getMaSach() + ",'" + pq.getMaVach() + "'," + pq.getNgayLap() + ",'" + tam + "'," + pq.getTien() + ")";
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

    public boolean updateCTPP(CTPP_DTO pq) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String tam = String.join(",", pq.getLiDo());
            String qry = "update ctpp set ";
            qry += "masach=" + pq.getMaSach() + ",ngaylap=" + pq.getNgayLap() + ",lido='" + tam + "',tien=" + pq.getTien() + "' Where mapp=" + pq.getMaPP() + " and mavachloi='" + pq.getMaVach() + "'";
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

    public boolean deleteCTPP(int mapp, String mavach) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from ctpp where mapp=" + mapp + ",mavach='" + mavach + "'";
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
    public boolean deleteByMaPP(int mapp) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            st = conn.createStatement();
            String qry = "Delete from ctpp where mapp=" + mapp;
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


    public ArrayList<CTPP_DTO> searchByMaPP(int mapp) {
        ArrayList<CTPP_DTO> listPQ = new ArrayList<>();;
        try {
            String qry = "select mapp,masach,mavachloi,ngaylap,lido,tien from ctpp where mapp = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapp);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTPP_DTO pq = new CTPP_DTO();
                pq.setMaPP(Integer.parseInt(rs.getString(1)));
                pq.setMaSach(Integer.parseInt(rs.getString(2)));
                pq.setMaVach(rs.getString(3));
                pq.setNgayLap(rs.getDate(4).toLocalDate());
                String tam = rs.getString(5);

                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
                pq.setLiDo(listTam);
                pq.setTien(rs.getFloat(6));
                listPQ.add(pq);
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
        return listPQ;
    }
    public CTPP_DTO searchByMaPP_MaVach(int mapp,String maVach) {
        CTPP_DTO ctpp=null;
        try {
            String qry = "select mapp,masach,mavachloi,ngaylap,lido,tien from ctpp where mapp = ? and mavach = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapp);
            ps.setString(2, maVach);
            rs = ps.executeQuery();
            if (rs.next()) {
                ctpp = new CTPP_DTO();
                ctpp.setMaPP(Integer.parseInt(rs.getString(1)));
                ctpp.setMaSach(Integer.parseInt(rs.getString(2)));
                ctpp.setMaVach(rs.getString(3));
                ctpp.setNgayLap(rs.getDate(4).toLocalDate());
                String tam = rs.getString(5);

                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
                ctpp.setLiDo(listTam);
                ctpp.setTien(rs.getFloat(6));
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
        return ctpp;
    }
}
