/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
import DTO.KhuVuc_DTO;
import DAO.KhuVuc_DAO;

import java.util.ArrayList;
public class KhuVuc_BUS {
    private ArrayList<KhuVuc_DTO> dsKV = null;
    private KhuVuc_DAO KVDAO = new KhuVuc_DAO();
    public ArrayList<KhuVuc_DTO> getListKV()
    {
        dsKV=KVDAO.getList();
        return dsKV;
    }

    public boolean addKV(KhuVuc_DTO kv){
        return KVDAO.addKV(kv);
    }
    
    public boolean deleteKV(int maKV) {
        return KVDAO.deleteKV(maKV);
    }
    
    public boolean updateKV(KhuVuc_DTO kv) {
        return KVDAO.updateKV(kv);
    }
    
    public KhuVuc_DTO searchByMaKV(int maKV)
    {
        return KVDAO.searchByMaKV(maKV);
    }
    
    public StringBuilder searchKV(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true; // Biến đánh dấu phần tử đầu tiên
        
        ArrayList<KhuVuc_DTO> listKV = KVDAO.getList();
        if (listKV == null) {
            System.out.println("Danh sách KV trống.");
//            return new StringBuilder("[]"); // Trả về chuỗi rỗng nếu danh sách không có dữ liệu
        }
        for (KhuVuc_DTO kv : listKV) {
            System.out.println("mã: " + kv.getMaKV());
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("maKV") && String.valueOf(kv.getMaKV()).contains(value)
                    || option.equals("tenKV") && kv.getTenKV().contains(value)) 
            {
                System.out.println("vào if");
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
//                System.out.println("mã: " + ncc.getMaKV());
                jsonResult.append("{"
                        + "\"maKV\": \"" + kv.getMaKV() + "\","
                        + "\"tenKV\": \"" + kv.getTenKV() + "\""
                        + "}");
//                System.out.println("sau thêm js");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }
}
