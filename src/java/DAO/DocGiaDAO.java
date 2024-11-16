package DAO;
import DTO.DocGiaDTO;
import ConnectDB.dangNhapDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DocGiaDAO {
    private dangNhapDatabase xuLyDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st;
    private ResultSet rs = null;

    public ArrayList<DocGiaDTO> getList() {
        ArrayList<DocGiaDTO> docGiaList = new ArrayList<>();
        try
        {
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry = "Select * from docgia ";
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery(qry);
            while(rs.next())
            {
                DocGiaDTO dg = new DocGiaDTO();
                dg.setMaDG(Integer.parseInt(rs.getString(1)));
                dg.setHoDG(rs.getString(2));
                dg.setTenDG(rs.getString(3));
                dg.setDiaChi(rs.getString(4));
                dg.setSoDienThoai(rs.getString(5));
                LocalDate ngaySinh = rs.getDate(6).toLocalDate();
                dg.setNgaySinh(ngaySinh);
                docGiaList.add(dg);
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
    return docGiaList;
    }

    public boolean addDocGia(DocGiaDTO dg){
        boolean result = true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn = xuLyDB.openConnection();
            String qry="Insert into docgia Values (";
            qry = qry + "'" + dg.getMaDG() + "','" + dg.getHoDG() + "','" + dg.getTenDG() + "','" + dg.getDiaChi()+ "','" + dg.getSoDienThoai()+ "','" + dg.getNgaySinh()+ "')";
            st=conn.createStatement();
            st.executeUpdate(qry);
            }catch(Exception e){
                result=false;
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

    public boolean updateDocGia(DocGiaDTO dg){
        boolean result =true;
        try{
            xuLyDB = new dangNhapDatabase();
            conn =  xuLyDB.openConnection();
            st = conn.createStatement();
            String qry = "Update docgia Set ";
            qry = qry +"ho='"+ dg.getHoDG() + "',ten='" + dg.getTenDG() + "',dc='" + dg.getDiaChi() + "',sdt='" 
                    + dg.getSoDienThoai()+"',ngaysinh='" + dg.getNgaySinh() + "' WHERE madg='"+dg.getMaDG()+"'";
            st.executeUpdate(qry);
        }catch(SQLException e){
            result=false;
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

    public boolean deleteDocGia(int ma){
        boolean result = true;
        try{
                xuLyDB = new dangNhapDatabase();
                conn = xuLyDB.openConnection();
                st = conn.createStatement();
                String qry="Delete from docgia where madg='"+ma+"'";
                st.executeUpdate(qry);
            }catch(Exception e){
                result=false;
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

    public DocGiaDTO findDocGiaByMaKhach(int maKhach) throws SQLException {
        String query = "SELECT * FROM DocGia WHERE MaKhach = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, maKhach);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            DocGiaDTO docGia = new DocGiaDTO();
            docGia.setMaDG(Integer.parseInt(rs.getString(1)));
            docGia.setHoDG(rs.getString(2));
            docGia.setTenDG(rs.getString(3));
            docGia.setDiaChi(rs.getString(4));
            docGia.setSoDienThoai(rs.getString(5));
            LocalDate ngaySinh = rs.getDate(6).toLocalDate();
            docGia.setNgaySinh(ngaySinh);
            return docGia;
        }
        return null;
    }
}
