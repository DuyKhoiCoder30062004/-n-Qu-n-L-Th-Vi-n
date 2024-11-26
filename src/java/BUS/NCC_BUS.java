/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DTO.NCC_DTO;
import DAO.NCC_DAO;

import java.util.ArrayList;
public class NCC_BUS {
    private ArrayList<NCC_DTO> dsNCC = null;
    private NCC_DAO NCCDAO = new NCC_DAO();
    public ArrayList<NCC_DTO> getListNCC()
    {
        dsNCC=NCCDAO.getList();
        return dsNCC;
    }

    public boolean addNCC(NCC_DTO ncc){
        return NCCDAO.addNCC(ncc);
    }
    
    public boolean deleteNCC(int maNCC) {
        return NCCDAO.deleteNCC(maNCC);
    }
    
    public boolean updateNCC(NCC_DTO ncc) {
        return NCCDAO.updateNCC(ncc);
    }
    
    public NCC_DTO searchByMaNCC(int maNCC)
    {
        return NCCDAO.searchByMaNCC(maNCC);
    }
    
    public StringBuilder searchNCC(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên
        
        ArrayList<NCC_DTO> listNCC = NCCDAO.getList();
        if (listNCC == null) {
            System.out.println("Danh sách NCC trống.");
//            return new StringBuilder("[]"); // Trả về chuỗi rỗng nếu danh sách không có dữ liệu
        }
        for (NCC_DTO ncc : listNCC) {
            System.out.println("mã: " + ncc.getMaNCC());
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("maNCC") && ncc.getMaNCC()== Integer.parseInt(value)
                    || option.equals("tenNCC") && ncc.getTenNCC().contains(value)
                    || option.equals("diaChi") && ncc.getDcNCC().contains(value)
                    || option.equals("sdt") && ncc.getSdtNCC().contains(value)) 
            {
                System.out.println("vào if");
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
//                System.out.println("mã: " + ncc.getMaNCC());
                jsonResult.append("{"
                        + "\"maNCC\": \"" + ncc.getMaNCC() + "\","
                        + "\"tenNCC\": \"" + ncc.getTenNCC() + "\","
                        + "\"diaChi\": \"" + ncc.getDcNCC()+ "\","
                        + "\"sdt\": \"" + ncc.getSdtNCC()+ "\","
                        + "\"trangThai\": \"" + ncc.getTrangThai()+ "\""
                        + "}");
//                System.out.println("sau thêm js");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
