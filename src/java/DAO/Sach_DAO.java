package DAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import DTO.Sach_DTO;
import DTO.CTPM_DTO;
import DTO.CTPN_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.PhieuNhap_DTO;
import DTO.KhuVuc_DTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import ConnectDB.dangNhapDatabase;
public class Sach_DAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    // Lấy danh sách
    public ArrayList<Sach_DTO> getListSach() {
        ArrayList<Sach_DTO> listSach = new ArrayList<Sach_DTO>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT * FROM sach";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Sach_DTO sach = new Sach_DTO();
                sach.setMaSach(rs.getInt(1));
                sach.setTenSach(rs.getString(2));
                sach.setTacGia(rs.getString(3));
                sach.setMaNXB(rs.getInt(4));
                sach.setMaNCC(rs.getInt(5));
                sach.setMaKhuVuc(rs.getInt(6));
                sach.setGiaTien(rs.getInt(7));
                sach.setSoLuong(rs.getInt(8));
                sach.setMoTa(rs.getString(9));
                sach.setNamXuatBan(rs.getInt(10));
                sach.setAnh(rs.getString(11));
                listSach.add(sach);
            }
        }
        catch (Exception e) {
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
        return listSach;
    }
    
    // Lấy danh sách chưa bị xóa
   public ArrayList<Sach_DTO> getListSach_not_delete() {
       ArrayList<Sach_DTO> listSach = new ArrayList<Sach_DTO>();
       try {
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           String sql = "SELECT * FROM sach WHERE masach > 0";
           ps = connection.prepareStatement(sql);
           rs = ps.executeQuery();
           while(rs.next()) {
               Sach_DTO sach = new Sach_DTO();
               sach.setMaSach(rs.getInt(1));
               sach.setTenSach(rs.getString(2));
               sach.setTacGia(rs.getString(3));
               sach.setMaNXB(rs.getInt(4));
               sach.setMaNCC(rs.getInt(5));
               sach.setMaKhuVuc(rs.getInt(6));
               sach.setGiaTien(rs.getInt(7));
               sach.setSoLuong(rs.getInt(8));
               sach.setMoTa(rs.getString(9));
               sach.setNamXuatBan(rs.getInt(10));
               sach.setAnh(rs.getString(11));
               listSach.add(sach);
           }
       }
       catch (Exception e) {
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
       return listSach;
   }
    //   Kiểm tra sách đã tồn tại chưa nếu rồi trả về true còn chưa thì trả về false
    public boolean checkBooks_or_not(int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT masach FROM sach WHERE masach = ? OR masach = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            ps.setInt(2, -maSach);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else{
                result = false;
            }
        }
        catch (Exception e) {
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
    // kiểm tra xem sách này đã được nhập chưa nếu nhập rồi thì trả về true chưa thì trả về false
    public boolean CheckBook_CTPN(int maSach) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT masach FROM ctpn WHERE masach =?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else{
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
    //    Kiểm tra xem nhà xuất bản này có hay không nếu có trả về true nếu không trả về false
    public boolean CheckBook_NXB(int maNXB) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT manxb FROM nxb WHERE manxb = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maNXB);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else{
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
    //    Kiểm tra xem khu vực này có hay không nếu có trả về true nếu không trả về false
    public boolean CheckBook_KhuVuc(int maKhuVuc) {
        boolean result = false;
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT makv FROM khuvuc WHERE makv = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maKhuVuc);
            rs = ps.executeQuery();
            if(rs.next()) {
                result = true;
            }
            else{
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
// Lấy số lượng, mã nhà cung cấp
    public List<Map<String, Object>> getNCC_and_SoLuong(int maSach) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            xuLyDB = new dangNhapDatabase();
            connection = xuLyDB.openConnection();
            String sql = "SELECT  pn.mancc,ctpn.mapn,SUM(ctpn.soluong) AS tongSoLuong" + 
            "FROM ctpn ctpn" + 
            "JOIN phieunhap pn ON ctpn.mapn = pn.mapn" + 
            "WHERE ctpn.maSach = ?" +
            "GROUP BY ctpn.mapn, pn.mancc";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, maSach);
            rs = ps.executeQuery();
            while (rs.next()) {
            Map<String, Object> item = new HashMap<>();
            item.put("maNhaCungCap", rs.getInt("mancc"));
            item.put("maPhieuNhap", rs.getInt("mapn"));
            item.put("tongSoLuong", rs.getInt("tongSoLuong"));
            resultList.add(item);
        } 
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu: " + e.getMessage());
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
           return resultList;
    }
    // Thêm sách
   public boolean addSach(Sach_DTO sach) {
       boolean result = false;
       try {
           String sql = "INSERT INTO sach VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setInt(1, sach.getMaSach());
           ps.setString(2, sach.getTenSach());
           ps.setString(3, sach.getTacGia());
           ps.setInt(4, sach.getMaNXB());
           ps.setInt(5, sach.getMaNCC());
           ps.setInt(6, sach.getMaKhuVuc());
           ps.setInt(7, sach.getGiaTien());
           ps.setInt(8, sach.getSoLuong());
           ps.setString(9, sach.getMoTa());
           ps.setInt(10, sach.getNamXuatBan());
           ps.setString(11, sach.getAnh());
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
   // Sửa 
   public boolean updateSach(Sach_DTO sach) {
       boolean result = false;
       try {
           String sql = "UPDATE sach SET tensach = ?, tacgia = ?, gia = ?, soluong = ?, mota = ?,"
           + " namxb =?, anh =? WHERE masach =?";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setString(1, sach.getTenSach());
           ps.setString(2, sach.getTacGia());
           ps.setInt(3, sach.getGiaTien());
           ps.setInt(4, sach.getSoLuong());
           ps.setString(5, sach.getMoTa());
           ps.setInt(6, sach.getNamXuatBan());
           ps.setString(7, sach.getAnh());
           ps.setInt(8, sach.getMaSach());
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
   // Xóa
   public boolean deleteSach(int maSach) {
       boolean result = false;
       try {
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
        // Update table sách
           String sql = "UPDATE sach SET masach = ? WHERE masach = ?";
           ps = connection.prepareStatement(sql);
           ps.setInt(1, (maSach * -1));
           ps.setInt(2, maSach);
           result = ps.executeUpdate() > 0;
       } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu HÀM XÓA SÁCH: " + e.getMessage());
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
//    update masach ở bảng chi tiết sách
// lấy các phiếu có mã sách trong ctpm
// Lấy danh sách CHI TIẾT PHIẾU MƯỢN
public ArrayList<CTPM_DTO> getListCTPM_as_maSach(int maSach) {
    ArrayList<CTPM_DTO> listCTPM = new ArrayList<CTPM_DTO>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM ctpm WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        while(rs.next()) {
            CTPM_DTO ctpm_DTO = new CTPM_DTO();
            ctpm_DTO.setMaPM(rs.getInt(1));
            ctpm_DTO.setMaSach(rs.getInt(2));
            ctpm_DTO.setSoLuong(rs.getInt(3));
            ctpm_DTO.setTrangthai(rs.getString(4));
            listCTPM.add(ctpm_DTO);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu LẤY LIST CTPM: " + e.getMessage());
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
    return listCTPM;
}
// Lấy danh sách CHI TIẾT PHIẾU NHẬP
public ArrayList<CTPN_DTO> getListCTPN_as_maSach(int maSach) {
    ArrayList<CTPN_DTO> listCTPN = new ArrayList<CTPN_DTO>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM ctpn WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        while(rs.next()) {
            CTPN_DTO ctpn_DTO = new CTPN_DTO();
            ctpn_DTO.setMaPN(rs.getInt(1));
            ctpn_DTO.setMaSach(rs.getInt(2));
            ctpn_DTO.setSoLuong(rs.getInt(3));
            ctpn_DTO.setDonGia(rs.getInt(4));
            String maVachString = rs.getString(5);
            // Tách chuỗi thành mảng
            String[] maVachArray = maVachString.split(",\\s*");
            // Chuyển mảng thành ArrayList
            ArrayList<String> listMaVach = new ArrayList<>(Arrays.asList(maVachArray));
            ctpn_DTO.setMaVach(listMaVach);
            listCTPN.add(ctpn_DTO);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu TRONG HÀM LẤY LIST PHIẾU NHẬP: " + e.getMessage());
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
// Lấy danh sách CHI TIẾT PHIẾU phạt
public ArrayList<CTPP_DTO> getListCTPP_as_maSach(int maSach) {
    ArrayList<CTPP_DTO> listCTPP = new ArrayList<CTPP_DTO>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM ctpp WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        while(rs.next()) {
            CTPP_DTO ctpp_DTO = new CTPP_DTO();
            ctpp_DTO.setMaPP(rs.getInt(1));
            ctpp_DTO.setMaSach(rs.getInt(2));
            ctpp_DTO.setMaVach(rs.getString(3));
            ctpp_DTO.setNgayLap(rs.getDate(4).toLocalDate());
            String tam = rs.getString(5);

                String[] arrTam = tam.split(",");
                ArrayList<String> listTam = new ArrayList<>(Arrays.asList(arrTam));
            ctpp_DTO.setLiDo(listTam);
            ctpp_DTO.setTien(rs.getFloat(6));
            listCTPP.add(ctpp_DTO);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu LIST CTPP: " + e.getMessage());
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
    return listCTPP;
}
// Lấy danh sách CHI TIẾT PHIẾU trả
public ArrayList<CTPT_DTO> getListCTPT_as_maSach(int maSach) {
    ArrayList<CTPT_DTO> listCTPT = new ArrayList<CTPT_DTO>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM ctpt WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        while(rs.next()) {
            CTPT_DTO ctpt_DTO = new CTPT_DTO();
            ctpt_DTO.setMaPT(rs.getInt(1));
            ctpt_DTO.setMaSach(rs.getInt(2));
            ctpt_DTO.setMaVachLoi(rs.getString(3));
            ctpt_DTO.setNgayTra(rs.getDate(4).toLocalDate());
            listCTPT.add(ctpt_DTO);
        }
    }
    catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu LIST CTPT: " + e.getMessage());
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
    return listCTPT;
}
//  Xóa chi tiết phiếu mượn bằng mã sách
public boolean deleteCTPM_as_maSach(int maSach) {
    boolean result = false;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
     // Update table sách
        String sql = "DELETE FROM ctpm WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        result = ps.executeUpdate() > 0;
    } catch (Exception e) {
     e.printStackTrace();
     System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm xóa ctpm: " + e.getMessage());
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
//  Xóa chi tiết phiếu phạt bằng mã sách
public boolean deleteCTPP_as_maSach(int maSach) {
    boolean result = false;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
     // Update table sách
        String sql = "DELETE FROM ctpp WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        result = ps.executeUpdate() > 0;
    } catch (Exception e) {
     e.printStackTrace();
     System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm xóa ctpp: " + e.getMessage());
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
//  Xóa chi tiết phiếu trả bằng mã sách
public boolean deleteCTPT_as_maSach(int maSach) {
    boolean result = false;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
     // Update table sách
        String sql = "DELETE FROM ctpt WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        result = ps.executeUpdate() > 0;
    } catch (Exception e) {
     e.printStackTrace();
     System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm xóa ctpt: " + e.getMessage());
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
// Thêm chi tiết phiếu mượn
public boolean addCTPM(CTPM_DTO ctpm ) {
    boolean result = false;
       try {
           String sql = "INSERT INTO ctpm VALUES (?, ?, ?, ?)";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setInt(1, ctpm.getMaPM());
           ps.setInt(2, (ctpm.getMaSach()));
           ps.setInt(3, ctpm.getSoLuong());
           ps.setString(4, ctpm.getTrangthai());
           result = ps.executeUpdate() > 0;
       } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm thêm ctpm: " + e.getMessage());
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
// Thêm chi tiết phiếu phạt
public boolean addCTPP(CTPP_DTO ctpp ) {
    boolean result = false;
       try {
           String sql = "INSERT INTO ctpp VALUES (?, ?, ?, ?, ?, ?)";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setInt(1, ctpp.getMaPP());
           ps.setInt(2, (ctpp.getMaSach()));
           ps.setString(3, ctpp.getMaVach());
           ps.setDate(4, java.sql.Date.valueOf(ctpp.getNgayLap()));
           String liDo = String.join(",", ctpp.getLiDo());
           ps.setString(5, liDo);
           ps.setFloat(6, ctpp.getTien());
           result = ps.executeUpdate() > 0;
       } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm thêm chi tiết phiếu phạt: " + e.getMessage());
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
 // Thêm chi tiết phiếu Trả
 public boolean addCTPT(CTPT_DTO ctpt ) {
    boolean result = false;
       try {
           String sql = "INSERT INTO ctpt VALUES (?, ?, ?, ?, ?)";
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           ps = connection.prepareStatement(sql);
           ps.setInt(1, ctpt.getMaPT());
           ps.setInt(2, (ctpt.getMaSach()));
           ps.setString(3, ctpt.getMaVachLoi());
           ps.setDate(4, java.sql.Date.valueOf(ctpt.getNgayTra()));
           ps.setInt(5, ctpt.getSoLuong());
           result = ps.executeUpdate() > 0;
       } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Lỗi xảy ra trong quá trình lấy dữ liệu hàm ct phiếu trả: " + e.getMessage());
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
   // Tìm kiếm theo mã sách
   public ArrayList<Sach_DTO> findSachByMaSach(String maSach) {
       ArrayList<Sach_DTO> listSach = new ArrayList<>();
       try {
           xuLyDB = new dangNhapDatabase();
           connection = xuLyDB.openConnection();
           String sql = "SELECT * FROM sach WHERE CAST(masach AS CHAR) " + " LIKE '%" + maSach + "%'";
           ps = connection.prepareStatement(sql);
           rs = ps.executeQuery();
           while(rs.next()) {
               Sach_DTO sach = new Sach_DTO();
               sach.setMaSach(rs.getInt(1));
               sach.setTenSach(rs.getString(2));
               sach.setTacGia(rs.getString(3));
               sach.setMaNXB(rs.getInt(4));
               sach.setMaNCC(rs.getInt(5));
               sach.setMaKhuVuc(rs.getInt(6));
               sach.setGiaTien(rs.getInt(7));
               sach.setSoLuong(rs.getInt(8));
               sach.setMoTa(rs.getString(9));
               sach.setNamXuatBan(rs.getInt(10));
               sach.setAnh(rs.getString(11));
               listSach.add(sach);
           }
       }
       catch (Exception e) {
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
       return listSach;
   }
   public Sach_DTO findSachByMaSach(int maSach) {
    Sach_DTO sach = null;
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT * FROM sach WHERE masach = ?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, maSach);
        rs = ps.executeQuery();
        if(rs.next()) {
            sach = new Sach_DTO();
            sach.setMaSach(rs.getInt(1));
            sach.setTenSach(rs.getString(2));
            sach.setTacGia(rs.getString(3));
            sach.setMaNXB(rs.getInt(4));
            sach.setMaNCC(rs.getInt(5));
            sach.setMaKhuVuc(rs.getInt(6));
            sach.setGiaTien(rs.getInt(7));
            sach.setSoLuong(rs.getInt(8));
            sach.setMoTa(rs.getString(9));
            sach.setNamXuatBan(rs.getInt(10));
            sach.setAnh(rs.getString(11));
        }
    }
    catch (Exception e) {
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
    return sach;
}
// Lấy các quyển sách mới 
public ArrayList<Sach_DTO> listSach_New() {
    ArrayList<Sach_DTO> listSach_New = new ArrayList<>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = 
            "WITH MaxDate AS (" +
            "    SELECT MAX(ngaylap) AS max_date FROM phieunhap" +
            "), " +
            "RecentImports AS (" +
            "    SELECT mapn " +
            "    FROM phieunhap pn " +
            "    JOIN MaxDate md " +
            "    ON pn.ngaylap <= md.max_date " +
            "    ORDER BY pn.ngaylap DESC " +
            "    LIMIT 7" +
            ") " +
            "SELECT s.* " +  // Lấy tất cả các thuộc tính của sách
            "FROM sach s " +
            "JOIN ctpn c ON s.masach = c.masach " +
            "JOIN RecentImports ri ON c.mapn = ri.mapn " +
            "WHERE s.masach > 0";  // Điều kiện mã sách lớn hơn 0

        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()) {
            Sach_DTO sach = new Sach_DTO();
            sach.setMaSach(rs.getInt(1));
            sach.setTenSach(rs.getString(2));
            sach.setTacGia(rs.getString(3));
            sach.setMaNXB(rs.getInt(4));
            sach.setMaNCC(rs.getInt(5));
            sach.setMaKhuVuc(rs.getInt(6));
            sach.setGiaTien(rs.getInt(7));
            sach.setSoLuong(rs.getInt(8));
            sach.setMoTa(rs.getString(9));
            sach.setNamXuatBan(rs.getInt(10));
            sach.setAnh(rs.getString(11));
            listSach_New.add(sach);
        }
    }
    catch (Exception e) {
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
    return listSach_New;
}
// Lấy ra list khu vực
public ArrayList<KhuVuc_DTO> listKhuVuc() {
    ArrayList<KhuVuc_DTO> listKhuVuc = new ArrayList<>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        String sql = "SELECT MIN(makv) AS makv, tenkv" +
        "  FROM khuvuc" +
        "  WHERE makv > 0" +
        "  GROUP BY tenkv" +
        "  ORDER BY tenkv";
        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()) {
            KhuVuc_DTO khuVuc = new KhuVuc_DTO();
            khuVuc.setMaKV(rs.getInt(1));
            khuVuc.setTenKV(rs.getString(2));
            listKhuVuc.add(khuVuc);
        }
    }
    catch (Exception e) {
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
    return listKhuVuc;
}
// Hàm tím kiếm
public ArrayList<Sach_DTO> listSach_LookFor(String tuKhoaTimKiem, int moneyMin, int moneyMax, String tenKhuVuc) {
    ArrayList<Sach_DTO> listSach = new ArrayList<>();
    try {
        xuLyDB = new dangNhapDatabase();
        connection = xuLyDB.openConnection();
        StringBuilder sql = new StringBuilder("SELECT s.* ");
        sql.append("FROM sach s ");
        sql.append("JOIN khuvuc k ON s.makv = k.makv ");
        sql.append("WHERE 1=1 "); // Điều kiện cơ bản luôn đúng
        sql.append(" AND s.masach > 0");
        if(!tuKhoaTimKiem.isEmpty() && !tuKhoaTimKiem.equals("")) {
            String sqlThuocTinh =   " AND (LOWER(s.tensach) LIKE LOWER(?)" + 
                                        " OR LOWER(s.tacgia) LIKE LOWER(?)" +
                                        " OR LOWER(s.mota) LIKE LOWER(?))";

            sql.append(sqlThuocTinh);
        }
        if ((moneyMin > -1) && (moneyMax > -1)) {
            sql.append(" AND s.gia >= ?");
        }
        if ((moneyMin > -1) && (moneyMax > -1)) {
            sql.append(" AND s.gia <= ?");
        }
        if (tenKhuVuc != null && !tenKhuVuc.trim().isEmpty()) {
            sql.append(" AND k.tenkv = ?");
        }
        ps = connection.prepareStatement(sql.toString());
        int paramIndex = 1; 
        if(!tuKhoaTimKiem.isEmpty() && !tuKhoaTimKiem.equals("")) {
            String keyword = "%" + tuKhoaTimKiem + "%";
            ps.setString(paramIndex++, keyword);
            ps.setString(paramIndex++, keyword);
            ps.setString(paramIndex++, keyword);
        }
        if ((moneyMin > -1) && (moneyMax > -1)) {
            ps.setInt(paramIndex++, moneyMin);
        }
        if ((moneyMin > -1) && (moneyMax > -1)) {
            ps.setInt(paramIndex++, moneyMax);
        }
        if (tenKhuVuc != null && !tenKhuVuc.trim().isEmpty()) {
            ps.setString(paramIndex++, tenKhuVuc);
        }
        rs = ps.executeQuery();
        while(rs.next()) {
            Sach_DTO sach = new Sach_DTO();
            sach.setMaSach(rs.getInt(1));
            sach.setTenSach(rs.getString(2));
            sach.setTacGia(rs.getString(3));
            sach.setMaNXB(rs.getInt(4));
            sach.setMaNCC(rs.getInt(5));
            sach.setMaKhuVuc(rs.getInt(6));
            sach.setGiaTien(rs.getInt(7));
            sach.setSoLuong(rs.getInt(8));
            sach.setMoTa(rs.getString(9));
            sach.setNamXuatBan(rs.getInt(10));
            sach.setAnh(rs.getString(11));
            listSach.add(sach);
        }
    }
    catch (Exception e) {
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
    return listSach;
}
}
    
