package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ConnectDB.dangNhapDatabase;
import DTO.DocGiaDTO;

public class DocGiaDAO {
     private dangNhapDatabase dnDB = null;
    private Connection connection = null;
    private PreparedStatement ps = null;
    private java.sql.Statement st = null;
    private ResultSet rs = null;

    public List<DocGiaDTO> getAllDocGia() throws SQLException {
        List<DocGiaDTO> docGiaList = new ArrayList<>();
        dnDB = new dangNhapDatabase();
        connection = dnDB.openConnection();
        String query = "SELECT * FROM DocGia";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            DocGiaDTO docGia = new DocGiaDTO();
            docGia.setMaKhach(resultSet.getInt("MaKhach"));
            docGia.setTenKhach(resultSet.getString("TenKhach"));
            docGia.setDiaChi(resultSet.getString("DiaChi"));
            docGia.setSoDienThoai(resultSet.getString("SoDienThoai"));
            docGia.setNgaySinh(resultSet.getDate("NgaySinh"));
            docGiaList.add(docGia);
        }
        
        return docGiaList;
    }

    public void addDocGia(DocGiaDTO docGia) throws SQLException {
        dnDB = new dangNhapDatabase();
        connection = dnDB.openConnection();
        String query = "INSERT INTO DocGia (MaKhach, TenKhach, DiaChi, SoDienThoai, NgaySinh) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, docGia.getMaKhach());
        statement.setString(2, docGia.getTenKhach());
        statement.setString(3, docGia.getDiaChi());
        statement.setString(4, docGia.getSoDienThoai());
        statement.setDate(5, new java.sql.Date(docGia.getNgaySinh().getTime()));
        statement.executeUpdate();
        
    }

    public void updateDocGia(DocGiaDTO docGia) throws SQLException {
        dnDB = new dangNhapDatabase();
        connection = dnDB.openConnection();
        String query = "UPDATE DocGia SET TenKhach = ?, DiaChi = ?, SoDienThoai = ?, NgaySinh = ? WHERE MaKhach = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, docGia.getTenKhach());
        statement.setString(2, docGia.getDiaChi());
        statement.setString(3, docGia.getSoDienThoai());
        statement.setDate(4, new java.sql.Date(docGia.getNgaySinh().getTime()));
        statement.setInt(5, docGia.getMaKhach());
        statement.executeUpdate();
       
        
    }

    public void deleteDocGia(int maKhach) throws SQLException {
        dnDB = new dangNhapDatabase();
        connection = dnDB.openConnection(); 
        String query = "DELETE FROM DocGia WHERE MaKhach = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, maKhach);
        statement.executeUpdate();
    }

    public DocGiaDTO findDocGiaByMaKhach(int maKhach) throws SQLException {
        String query = "SELECT * FROM DocGia WHERE MaKhach = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, maKhach);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            DocGiaDTO docGia = new DocGiaDTO();
            docGia.setMaKhach(resultSet.getInt("MaKhach"));
            docGia.setTenKhach(resultSet.getString("TenKhach"));
            docGia.setDiaChi(resultSet.getString("DiaChi"));
            docGia.setSoDienThoai(resultSet.getString("SoDienThoai"));
            docGia.setNgaySinh(resultSet.getDate("NgaySinh"));
            return docGia;
        }
        
        return null;
    }
}
