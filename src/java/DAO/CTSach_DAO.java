package DAO;
import DTO.CTSach_DTO;
import DTO.CTPN_DTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;
public class CTSach_DAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    // get list chi tiết sách
    public ArrayList<CTSach_DTO> getListCTSach() {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaVach(rs.getString(1));
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu sách: " + e.getMessage());
            return null;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return listCTSach;
    }
    // get list chi tiết sách chưa bị xóa
    public ArrayList<CTSach_DTO> getListCTSach_not_delete(int maSach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach where masach = ? AND masach > 0 AND mavach NOT LIKE '-%'";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaVach(rs.getString(1));
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu sách: " + e.getMessage());
        return null;
    }
    finally {
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Đã đóng ResultSet.");
            }
            if (ps != null) {
                ps.close();
                System.out.println("Đã đóng PreparedStatement.");
            }
            if (connection != null) {
                xuLyDB.closeConnection(connection);
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
        }
    }
        return listCTSach;
    }
    // get list chi tiết sách theo mã sách
    public ArrayList<CTSach_DTO> getListCTSach_as_maSach(int maSach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach where masach = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaVach(rs.getString(1));
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu sách LIST CTSACH: " + e.getMessage());
        return null;
    }
    finally {
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Đã đóng ResultSet.");
            }
            if (ps != null) {
                ps.close();
                System.out.println("Đã đóng PreparedStatement.");
            }
            if (connection != null) {
                xuLyDB.closeConnection(connection);
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
        }
    }
        return listCTSach;
    }
    // Kiểm tra xem đã có sách đó chưa nếu chưa là chưa được nhập 
    public boolean checkBooksDetail_or_not(int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String spl = "SELECT masach FROM ctpn where masach = ?  OR masach = ?";
            ps = connection.prepareStatement(spl);
            ps.setInt(1, maSach);
            ps.setInt(2, -maSach);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else {
                result = false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu: " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
           return result;
    }
    
    // Thêm chi tiết sách
    public boolean addCTSach_when_deleteSach(CTSach_DTO ctSach_DTO) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            String sql = "INSERT INTO ctsach VALUES (?,?,?)";
            connection = xuLyDB.openConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, ctSach_DTO.getMaVach());
            ps.setInt(2, (ctSach_DTO.getMaSach()));
            ps.setString(3,ctSach_DTO.getTinhTrangSach());
            result = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình sửa dữ liệu THÊM CTSACHS " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return result;

    }
    // Kiểm tra trước khi sửa 
     // Kiểm tra xem đã có sách đó chưa nếu chưa là chưa được nhập 
     public boolean checkBooksDetail_as_maVach(String maVach, int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String spl = "SELECT mavach, masach FROM ctsach where mavach = ? AND masach = ? LIMIT 1";
            ps = connection.prepareStatement(spl);
            ps.setString(1, maVach);
            ps.setInt(2, maSach);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else {
                result = false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu: " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
           return result;
    }
    
    // Sửa CT SÁCH
    public boolean updateCTSach(CTSach_DTO ctsach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "UPDATE ctsach SET trangthai =? WHERE mavach =? AND masach =?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, ctsach.getTinhTrangSach());
            ps.setString(2, ctsach.getMaVach());
            ps.setInt(3, ctsach.getMaSach());
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình sửa dữ liệu " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return result;

    }
//   Xóa chi tiết sách bằng mã vạch và mã sách
public boolean deleteCTSach_as_maVach_and_maSach(String maVach, int maSach) {
    boolean result = false;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "UPDATE ctsach SET mavach =? WHERE mavach =? AND masach =?";
        ps = connection.prepareStatement(sql);
        String maVachDelete = "-" + maVach;
        ps.setString(1, maVachDelete);
        ps.setString(2, maVach);
        ps.setInt(3, maSach);
        result = ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình sửa dữ liệu " + e.getMessage());
        return false;
    }
    finally {
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Đã đóng ResultSet.");
            }
            if (ps != null) {
                ps.close();
                System.out.println("Đã đóng PreparedStatement.");
            }
            if (connection != null) {
                xuLyDB.closeConnection(connection);
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
        }
    }
    return result;

}
    // update masach khi xóa sách
    public boolean updateMaSach(int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
         // Update table sách
            String sql = "UPDATE ctsach SET masach = ? WHERE masach = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (maSach * -1));
            ps.setInt(2, maSach);
            result = ps.executeUpdate() > 0;
        } catch (Exception e) {
         e.printStackTrace();
         System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu: " + e.getMessage());
         return false;
     }
     finally {
         try {
             if (rs != null) {
                 rs.close();
                 System.out.println("Đã đóng ResultSet.");
             }
             if (ps != null) {
                 ps.close();
                 System.out.println("Đã đóng PreparedStatement.");
             }
             if (connection != null) {
                 xuLyDB.closeConnection(connection);
                 System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
             }
         } catch (SQLException e) {
             e.printStackTrace();
             System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
         }
     }
        return result;
    }
    // Xóa CT sÁCH bằng mã sách
    public boolean deleteCTSach_as_maSach(int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "DELETE FROM ctsach WHERE masach = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            result = ps.executeUpdate() > 0;
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình xóa CT SÁCH: " + e.getMessage());
            return false;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("Đã đóng ResultSet.");
                }
                if (ps != null) {
                    ps.close();
                    System.out.println("Đã đóng PreparedStatement.");
                }
                if (connection != null) {
                    xuLyDB.closeConnection(connection);
                    System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
            }
        }
        return result;

    }
//    lấy ra chi tiết sách trong bảng CHI TIẾT PHIẾU NHẬP bằng mã sách và mã vạch
public ArrayList<CTPN_DTO> getListCTPN_as_maSach(int maSach) {
    ArrayList<CTPN_DTO> listCTPN = new ArrayList<CTPN_DTO>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM ctpn where masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        while (rs.next()) {
            CTPN_DTO ctpn = new CTPN_DTO();
            ctpn.setMaPN(rs.getInt(1));
            ctpn.setMaSach(rs.getInt(2));
            ctpn.setSoLuong(rs.getInt(3));
            ctpn.setDonGia(rs.getInt(4));
            String maVachString = rs.getString(5);
            // Tách chuỗi thành mảng
            String[] maVachArray = maVachString.split(",\\s*");
            // Chuyển mảng thành ArrayList
            ArrayList<String> listMaVach = new ArrayList<>(Arrays.asList(maVachArray));
            ctpn.setMaVach(listMaVach);

            listCTPN.add(ctpn);
        }
    } catch (Exception e) {
    e.printStackTrace();
    System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu sách LIST CTSACH: " + e.getMessage());
    return null;
}
finally {
    try {
        if (rs != null) {
            rs.close();
            System.out.println("Đã đóng ResultSet.");
        }
        if (ps != null) {
            ps.close();
            System.out.println("Đã đóng PreparedStatement.");
        }
        if (connection != null) {
            xuLyDB.closeConnection(connection);
            System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
    }
}
    return listCTPN;
}
//  Xóa chi tiết phiếu nhập bằng mã sách
public boolean deleteCTPN_as_maSach(int maSach) {
    boolean result = false;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
     // Update table sách
        String sql = "DELETE FROM ctpn WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        result = ps.executeUpdate() > 0;
    } catch (Exception e) {
     e.printStackTrace();
     System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm xóa ctpn: " + e.getMessage());
     return false;
 }
 finally {
     try {
         if (rs != null) {
             rs.close();
             System.out.println("Đã đóng ResultSet.");
         }
         if (ps != null) {
             ps.close();
             System.out.println("Đã đóng PreparedStatement.");
         }
         if (connection != null) {
             xuLyDB.closeConnection(connection);
             System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
         }
     } catch (SQLException e) {
         e.printStackTrace();
         System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
     }
 }
    return result;
}
    // Thêm chi tiết phiếu Nhập
public boolean addCTPN(CTPN_DTO ctpn ) {
    boolean result = false;
       try {
           String sql = "INSERT INTO ctpn VALUES (?, ?, ?, ?, ?)";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setInt(1, ctpn.getMaPN());
           ps.setInt(2, (ctpn.getMaSach()));
           ps.setInt(3, ctpn.getSoLuong());
           ps.setInt(4, ctpn.getDonGia());
           String maVachString = String.join(", ", ctpn.getMaVach());
           ps.setString(5, maVachString);
           result = ps.executeUpdate() > 0;
       } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm thêm ctpn: " + e.getMessage());
        return false;
    }
    finally {
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Đã đóng ResultSet.");
            }
            if (ps != null) {
                ps.close();
                System.out.println("Đã đóng PreparedStatement.");
            }
            if (connection != null) {
                xuLyDB.closeConnection(connection);
                System.out.println("Đã đóng kết nối cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra khi đóng tài nguyên: " + e.getMessage());
        }
    }
       return result;
 }
// Tìm theo mã sách
    public ArrayList<CTSach_DTO> searchCTSachByMaSach(String maSach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach WHERE CAST(masach AS CHAR)" + " LIKE '%" + maSach + "%'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaVach(rs.getString(1));
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
            return null;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return listCTSach;
    }
    // Tìm theo mã vạch
    public ArrayList<CTSach_DTO> searchCTSachByMaVach(String maVach) {
        ArrayList<CTSach_DTO> listCTSach = new ArrayList<CTSach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM ctsach WHERE mavach" + " LIKE '%" + maVach + "%'";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                CTSach_DTO ctSach = new CTSach_DTO();
                ctSach.setMaVach(rs.getString(1));
                ctSach.setMaSach(rs.getInt(2));
                ctSach.setTinhTrangSach(rs.getString(3));
                listCTSach.add(ctSach);
            }
        } catch (Exception e) {
            return null;
        }
        finally {
            try {
                xuLyDB.closeConnection(connection);
                ps.close();
                rs.close();
            } catch (SQLException e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return listCTSach;
    }
}
