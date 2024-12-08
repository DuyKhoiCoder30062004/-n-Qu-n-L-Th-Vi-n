/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DTO.NXB_DTO;
import DAO.NXB_DAO;
import java.util.ArrayList;
/**
 *
 * @author HP
 */
public class NXB_BUS {
    private ArrayList<NXB_DTO> listNXB = null;
    private NXB_DAO NXBDAO = new NXB_DAO();
    public ArrayList<NXB_DTO> getListNXB()
    {
        listNXB=NXBDAO.getList();
        return listNXB;
    }

    public boolean addNXB(NXB_DTO nxb){
        return NXBDAO.addNXB(nxb);
    }
    
    public boolean deleteNXB(int maNXB) {
        return NXBDAO.deleteNXB(maNXB);
    }
    
    public boolean updateNXB(NXB_DTO nxb) {
        return NXBDAO.updateNXB(nxb);
    }
    public NXB_DTO searchByMaNXB(int manxb)
    {
        return NXBDAO.searchByMaNXB(manxb);
    }
    public StringBuilder searchNXB(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên
        
        ArrayList<NXB_DTO> listNXB = NXBDAO.getList();
        if (listNXB == null) {
            System.out.println("Danh sách NCC trống.");
        }
        for (NXB_DTO nxb : listNXB) {
            System.out.println("mã: " + nxb.getMaNXB());
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã NXB") && nxb.getMaNXB() == Integer.parseInt(value)
                    || option.equals("Tên NXB") && nxb.getTenNXB().contains(value)
                    || option.equals("Địa chỉ") && nxb.getDcNXB().contains(value)) 
            {
                System.out.println("mã: " + nxb.getMaNXB());
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
//                System.out.println("mã: " + ncc.getMaNCC());
                jsonResult.append("{"
                        + "\"maNXB\": \"" + nxb.getMaNXB() + "\","
                        + "\"tenNXB\": \"" + nxb.getTenNXB() + "\","
                        + "\"dcNXB\": \"" + nxb.getDcNXB()+ "\""
                        + "}");
//                System.out.println("sau thêm js");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
