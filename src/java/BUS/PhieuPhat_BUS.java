/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPP_DAO;
import DAO.PhieuPhat_DAO;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class PhieuPhat_BUS {

    private PhieuPhat_DAO pp_DAO = new PhieuPhat_DAO();
    private CTPP_BUS ctpp_BUS=new CTPP_BUS();
    private PhieuTra_BUS pt_BUS=new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS=new CTPT_BUS();
    private Nhanvien_BUS nv_BUS=new Nhanvien_BUS();
    private Sach_BUS sach_BUS=new Sach_BUS();
    private CTSach_BUS cts_BUS=new CTSach_BUS();
    private Loi_BUS loi_BUS=new Loi_BUS();
    public ArrayList<PhieuPhat_DTO> getList() {
        return pp_DAO.getList();
    }

    public boolean addPP(PhieuPhat_DTO pp) {
        return pp_DAO.addPP(pp);
    }

    public boolean updatePP(PhieuPhat_DTO pp) {
        return pp_DAO.updatePP(pp);
    }

    public boolean updateTT(int mapp, double ttnew) {
        return pp_DAO.updateTT(mapp, ttnew);
    }

    public boolean deletePP(int mapp) {
        return pp_DAO.deletePP(mapp);
    }

    public PhieuPhat_DTO searchByMaPP(int mapp) {
        return pp_DAO.searchByMaPP(mapp);
    }

    public StringBuilder[] searchPP(String option, String value) {
        StringBuilder[] arrayrs = new StringBuilder[2];
        StringBuilder jsonResult = new StringBuilder("["); 
        StringBuilder jsonRsCTPP = new StringBuilder("[");
        boolean firstItem = true;
        boolean fItemCTPP=true;

        for (PhieuPhat_DTO pq : pp_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pq.getMaPP()).contains(value)
                    || option.equals("Mã phiếu trả") && String.valueOf(pq.getMaPT()).contains(value)
                    || option.equals("Mã NV") && String.valueOf(pq.getMaNV()).contains(value)
                    || option.equals("Tổng tiền") && pq.getTongTien() == Double.parseDouble(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pq.getMaPP() + "\","
                        + "\"maPT\": \"" + pq.getMaPT() + "\","
                        + "\"maNV\": \"" + pq.getMaNV() + "\","
                        + "\"tongTien\": \"" + pq.getTongTien() + "\""
                        + "}");
                firstItem = false; 
                ArrayList<CTPP_DTO> listCTPP=ctpp_BUS.searchByMaPP(pq.getMaPP());
                for (CTPP_DTO ctpp:listCTPP)
                {
                    if(!fItemCTPP){
                        jsonRsCTPP.append(",");
                    }
                    jsonRsCTPP.append("{"
                        + "\"maPhieu\": \"" + ctpp.getMaPP() + "\","
                        + "\"maSach\": \"" + ctpp.getMaSach()+ "\","
                        + "\"maVach\": \"" + ctpp.getMaVach()+ "\","
                        + "\"ngayLap\": \"" + ctpp.getNgayLap()+ "\","
                        + "\"liDo\": \"" + ctpp.getLiDo()+ "\","
                        + "\"tien\": \"" + ctpp.getTien()+ "\""
                        + "}");
                    fItemCTPP = false;
                }
            }
        }
        jsonResult.append("]"); 
        jsonRsCTPP.append("]");
        arrayrs[0]=jsonResult;
        arrayrs[1]=jsonRsCTPP;
        return arrayrs; 
    }
    public String printPP(int maPP)
    {
        // Khởi tạo chuỗi HTML cho phiếu mượn
        String pp = "<h1 style='text-align:center;'>PHIẾU PHẠT TỪ THƯ VIÊN TRƯỜNG ĐẠI HỌC ABC</h1>";

        PhieuPhat_DTO phieu = pp_DAO.searchByMaPP(maPP);

        // Kiểm tra xem phiếu có tồn tại không
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu mượn.</p>";
        }

        // Thêm thông tin vào chuỗi
        pp += "Mã phiếu phạt: " + phieu.getMaPP()+ "<br/>"
                + "Mã nhân viên: " + phieu.getMaNV() + "  "
                + "Tên nhân viên: " + "hii" + "<br/>" // Thêm tên nhân viên
                + "Mã độc giả: " + " " + "  "
                + "Tên độc giả: " + "tạm" + "<br/>" // Thêm tên độc giả
                + "SĐT độc giả: " + "000" + "<br/>" // Thêm số điện thoại độc giả
                + "<div style='text-align:center;'>DANH SÁCH SÁCH LÀM HƯ</div><br/>";

        // Tạo bảng sách mượn
        pp += "<table style='width:100%; border-collapse:collapse;'>"
                + "<tr>"
                + "<th style='border:1px solid; padding:5px;'>Mã Sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Mã vạch</th>"
                + "<th style='border:1px solid; padding:5px;'>Lí do</th>"
                + "<th style='border:1px solid; padding:5px;'>Tiền</th>"
                + "</tr>";

        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.searchByMaPP(maPP);

        // Duyệt danh sách chi tiết phiếu mượn
        if (listCTPP != null && listCTPP.size() > 0) {
            for (CTPP_DTO ct : listCTPP) {
                //String tenSach = ctpm_DAO.getTenSach(ct.getMaSach()); // Lấy tên sách từ DAO
                String tam=String.join(",", ct.getLiDo());
                pp += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + tam + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getTien() + "</td>"
                        + "</tr>";
            }
        }

        pp += "</table><br/>";

        // Tổng số lượng
        pp += "Tổng Tiền: " + phieu.getTongTien()+ "</br>";
        pp += "<div style='text-align:center;'>XIN CẢM ƠN!</div>";

        return pp;
    }
    private boolean checkInfor(String maPP,String maNV,String maPT,String listMS,String listMV,String listNL,
            String listLiDo,String listT)
    {
        if(maPP.isEmpty() || maNV.isEmpty() || maPT.isEmpty() || listMS.isEmpty() || listMV.isEmpty()
                || listNL.isEmpty() || listLiDo.isEmpty() || listT.isEmpty()
                ||pp_DAO.searchByMaPP(Integer.parseInt(maPP))!=null || pt_BUS.searchByMaPT(Integer.parseInt(maPT))==null
                ||nv_BUS.timKiemNhanVien(maNV)==null) 
        {
            return false;
        }
        String[] list1 = listMS.split("\n");
        String[] list2 = listMV.split("\n");
        String[] list3 = listNL.split("\n");
        String[] list4 = listLiDo.split("\n");
        String[] list5 = listT.split("\n");
        boolean areLengthsEqual = (list1.length == list2.length) && 
                                   (list2.length == list3.length) && 
                                   (list3.length == list4.length) && 
                                   (list4.length == list5.length);
        if(!areLengthsEqual)
            return false;
        for(int i=0;i<list1.length;i++)
        {
            if(sach_BUS.timSachTheoMaSach(list1[i])==null 
               || !cts_BUS.searchCTSachByMaSach(list1[i]).contains(list2[i])
                ||loi_BUS.searchByTenLoi(list4[i])!=null)
                return false;
        }
        try
        {
            for (int i = 0; i < list1.length; i++) {
                LocalDate.parse(list3[i]);
            }
        }catch (DateTimeParseException e) {
            return false;  
        }
        try {
            for (int i = 0; i < list1.length; i++) {
                Float.parseFloat(list5[i]);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public boolean importExcel(String fileName)
    {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        try (FileInputStream file = new FileInputStream(filePath);
         XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            // Bỏ qua dòng đầu tiên (tiêu đề)
            if (rowIterator.hasNext()) rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if(!checkInfor(row.getCell(0).getStringCellValue(),row.getCell(1).getStringCellValue(),row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue(),
                        row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue()))
                {
                    continue;
                }
                PhieuPhat_DTO pp=new PhieuPhat_DTO();
                pp.setMaPP(Integer.parseInt(row.getCell(0).getStringCellValue()));
                pp.setMaNV(Integer.parseInt(row.getCell(1).getStringCellValue()));
                pp.setMaPT(Integer.parseInt(row.getCell(2).getStringCellValue()));
                ArrayList<String> listMS = new ArrayList<>(Arrays.asList(row.getCell(3).getStringCellValue().split("\n")));
                ArrayList<String> listMV = new ArrayList<>(Arrays.asList(row.getCell(4).getStringCellValue().split("\n")));
                ArrayList<String> listNL = new ArrayList<>(Arrays.asList(row.getCell(5).getStringCellValue().split("\n")));
                ArrayList<String> listLiDo = new ArrayList<>(Arrays.asList(row.getCell(6).getStringCellValue().split("\n")));
                ArrayList<String> listT = new ArrayList<>(Arrays.asList(row.getCell(7).getStringCellValue().split("\n")));
                double tt=0;
                for (int i=0;i<listMS.size();i++)
                {
                    CTPP_DTO ct=new CTPP_DTO();
                    ct.setMaPP(Integer.parseInt(row.getCell(1).getStringCellValue()));
                    ct.setMaSach(Integer.parseInt(listMS.get(i)));
                    ct.setMaSach(Integer.parseInt(listMV.get(i)));
                    ct.setNgayLap(LocalDate.parse(listNL.get(i)));
                    ct.setLiDo(new ArrayList<>(Arrays.asList(listLiDo.get(i).split(","))));
                    ct.setTien(Float.parseFloat(listT.get(i)));
                    tt+=ct.getTien();
                    ctpp_BUS.addCTPP(ct);
                }
                pp.setTongTien(tt);
                pp_DAO.addPP(pp);
            }
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
        
    }
    public boolean exportExcel(String fileName)
    {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Tạo thư mục nếu chưa tồn tại
        }
        XSSFWorkbook excelWorkbook = new XSSFWorkbook();
        
        XSSFSheet excelSheet = (XSSFSheet) excelWorkbook.createSheet("Danh sách phiếu mượn của thư viện");
       
        XSSFRow row = null;
        Cell cell = null;
        // Merge các cột thành một và căn giữa tiêu đề
        excelSheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));
        row = excelSheet.createRow(2);
        row.setHeight((short) 500);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("DANH SÁCH PHIẾU PHẠT CỦA THƯ VIỆN TRƯỜNG ABC");
        CellStyle centerStyle = excelWorkbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(centerStyle);
        
        // Ghi tiêu đề cột
        row = excelSheet.createRow(3);
        String[] headers = {"Mã phiếu phạt", "Mã nhân viên", "Mã phiếu trả","Danh sách mã sách","Danh sách mã vạch lỗi",
            "Danh sách ngày lập","Danh sách lí do","Danh sách tiền","Tồng tiền"};
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
            excelSheet.autoSizeColumn(i);
        }
        CellStyle cellStyle = excelWorkbook.createCellStyle();
        cellStyle.setWrapText(true);
        for (int i = 0; i <pp_DAO.getList().size(); i++) {
            PhieuPhat_DTO pp = pp_DAO.getList().get(i);
            row = excelSheet.createRow(4 + i);
            row.createCell(0).setCellValue(pp.getMaPP());
            row.createCell(1).setCellValue(pp.getMaNV());
            row.createCell(2).setCellValue(pp.getMaPT());

            String listMS = "";
            String listMV = "";
            String listNL = "";
            String listLiDo="";
            String listT="";

            for (CTPP_DTO ctpp : ctpp_BUS.searchByMaPP(pp.getMaPP())) {
                listMS += ctpp.getMaSach() + "\n";
                listMV += ctpp.getMaVach() + "\n";
                listNL += ctpp.getNgayLap() + "\n";
                String tam = String.join(", ", ctpp.getLiDo());
                listLiDo += tam;
                listT +=ctpp.getTien() + "\n";
            }

            if (!listMS.isEmpty()) {
                listMS = listMS.substring(0, listMS.length() - 1);
                listMV = listMV.substring(0, listMV.length() - 1);
                listNL = listNL.substring(0, listNL.length() - 1);
                listLiDo = listLiDo.substring(0, listLiDo.length() - 1);
                listT = listT.substring(0, listT.length() - 1);
            }

            Cell cellMS = row.createCell(3);
            cellMS.setCellValue(listMS);
            cellMS.setCellStyle(cellStyle); 

            Cell cellSL = row.createCell(4);
            cellSL.setCellValue(listMV);
            cellSL.setCellStyle(cellStyle);

            Cell cellTT = row.createCell(5);
            cellTT.setCellValue(listNL);
            cellTT.setCellStyle(cellStyle);
            
            Cell cellLD = row.createCell(6);
            cellLD.setCellValue(listLiDo);
            cellLD.setCellStyle(cellStyle); 

            Cell cellT = row.createCell(7);
            cellT.setCellValue(listT);
            cellT.setCellStyle(cellStyle);

            row.createCell(8).setCellValue(pp.getTongTien());
            excelSheet.autoSizeColumn(i);

        }
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            
            excelWorkbook.write(fileOutputStream);
            
            excelWorkbook.close();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
            return false;
        }
        return true;
    }
}
