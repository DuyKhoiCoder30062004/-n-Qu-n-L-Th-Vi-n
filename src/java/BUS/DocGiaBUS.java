package BUS;
import DAO.DocGiaDAO;
import DTO.DocGiaDTO;
import java.sql.SQLException;
import java.util.List;

public class DocGiaBUS {
    private DocGiaDAO docGiaDAO;

    public List<DocGiaDTO> getAllDocGia() {
        try {
            return docGiaDAO.getAllDocGia();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addDocGia(DocGiaDTO docGia) {
        try {
            docGiaDAO.addDocGia(docGia);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDocGia(DocGiaDTO docGia) {
        try {
            docGiaDAO.updateDocGia(docGia);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocGia(int maKhach) {
        try {
            docGiaDAO.deleteDocGia(maKhach);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DocGiaDTO findDocGiaByMaKhach(int maKhach) {
        try {
            return docGiaDAO.findDocGiaByMaKhach(maKhach);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
