package BUS;
import DAO.DocGiaDAO;
import DTO.DocGiaDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocGiaBUS {

   private ArrayList<DocGiaDTO> dgList = null;
    private DocGiaDAO dgDAO = new DocGiaDAO();
    public ArrayList<DocGiaDTO> getListNCC()
    {
        dgList = dgDAO.getList();
        return dgList;
    }

    public boolean addDocGia(DocGiaDTO dg){
        return dgDAO.addDocGia(dg);
    }
    
    public boolean deleteDocGia(int maDG) {
        return dgDAO.deleteDocGia(maDG);
    }
    
    public boolean updateDocGia(DocGiaDTO dg) {
        return dgDAO.updateDocGia(dg);
    }
    

    public DocGiaDTO findDocGiaByMaKhach(int maKhach) {
        try {
            return dgDAO.findDocGiaByMaKhach(maKhach);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
