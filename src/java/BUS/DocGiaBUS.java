
package BUS;
import DAO.DocGia_DAO;
import DTO.DocGia_DTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author HP
 */
public class DocGiaBUS {

    private ArrayList<DocGia_DTO> listDG = null;
    private DocGia_DAO dgDAO = new DocGia_DAO();
    public ArrayList<DocGia_DTO> getListDG()
    {
        listDG = dgDAO.getList();
        return listDG;
    }

    public boolean addDocGia(DocGia_DTO dg){
        return dgDAO.addDocGia(dg);
    }
    
    public boolean deleteDocGia(int maDG) {
        return dgDAO.deleteDocGia(maDG);
    }
    
    public boolean updateDocGia(DocGia_DTO dg) {
        return dgDAO.updateDocGia(dg);
    }
    
    public DocGia_DTO searchByMaDG(int madg) {
        return dgDAO.searchByMaDG(madg);
    }
    
    public StringBuilder searchDocGia(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        
        ArrayList<DocGia_DTO> listDocGia = dgDAO.getList();
        if (listDocGia == null) {
            System.out.println("Danh sách DocGia trống.");
//            return new StringBuilder("[]"); // Trả về chuỗi rỗng nếu danh sách không có dữ liệu
        }
        for (DocGia_DTO dg : listDocGia) {
            System.out.println("mã: " + dg.getMaDG());
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("maDG") && String.valueOf(dg.getMaDG()).contains(value)
                    || option.equals("hoDG") && dg.getHoDG().contains(value)
                    || option.equals("tenDG") && dg.getTenDG().contains(value)
                    || option.equals("diaChi") && dg.getDiaChi().contains(value)
                    || option.equals("sdt") && dg.getSoDienThoai().contains(value)
                    || option.equals("ngaySinh") && dg.getNgaySinh().format(formatter).contains(value)) 
            {
                System.out.println("vào if");
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
//                System.out.println("mã: " + dg.getMaDocGia());
                jsonResult.append("{"
                        + "\"maDG\": \"" + dg.getMaDG() + "\","
                        + "\"hoDG\": \"" + dg.getHoDG() + "\","
                        + "\"tenDG\": \"" + dg.getTenDG() + "\","
                        + "\"diaChi\": \"" + dg.getDiaChi()+ "\","
                        + "\"soDienThoai\": \"" + dg.getSoDienThoai()+ "\","
                        + "\"ngaySinh\": \"" + dg.getNgaySinh()+ "\""
                        + "}");
//                System.out.println("sau thêm js");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
    

//    public DocGia_DTO findDocGiaByMaKhach(int maKhach) {
//        try {
//            return dgDAO.findDocGiaByMaKhach(maKhach);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}

