/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPM_DAO;
import DAO.PhieuMuon_DAO;
import DTO.CTPM_DTO;
import DTO.PhieuMuon_DTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
public class PhieuMuon_BUS {

    private ArrayList<PhieuMuon_DTO> listPM;
    private PhieuMuon_DAO pm_DAO = new PhieuMuon_DAO();
    private CTPM_BUS ctpm_BUS = new CTPM_BUS();

    public ArrayList<PhieuMuon_DTO> getList() {
        listPM = pm_DAO.getList();
        return listPM;
    }

    public boolean addPM(PhieuMuon_DTO pm) {
        return pm_DAO.addPM(pm);
    }

    public boolean updatePM(PhieuMuon_DTO pm) {
        return pm_DAO.updatePM(pm);
    }

    public boolean updateTongSL(int mapm, int tongSLMoi) {
        return pm_DAO.updateSL(mapm, tongSLMoi);
    }

    public boolean deletePM(int mapm) {
        return pm_DAO.deletePM(mapm);
    }

    public PhieuMuon_DTO searchByMaPM(int mapm) {
        return pm_DAO.searchByMaPM(mapm);
    }

    public StringBuilder searchPM(String option, String value) {
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (PhieuMuon_DTO pm : pm_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pm.getMaPM()).contains(value)
                    || option.equals("Mã độc giả") & String.valueOf(pm.getMaKhach()).contains(value)
                    || option.equals("Mã NV") & String.valueOf(pm.getMaNV()).contains(value)
                    || option.equals("Ngày lập") & pm.getNgayLap().format(formatter).contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pm.getMaPM() + "\","
                        + "\"maKhach\": \"" + pm.getMaKhach() + "\","
                        + "\"maNV\": \"" + pm.getMaNV() + "\","
                        + "\"ngayLap\": \"" + pm.getNgayLap() + "\","
                        + "\"hanChot\": \"" + pm.getHanChot() + "\","
                        + "\"tongSL\": \"" + pm.getTongSL() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
            }
        }
        jsonResult.append("]"); // Kết thúc mảng JSON
        return jsonResult; // Trả về StringBuilder
    }

    public String printPM(int mapm) {
        // Khởi tạo chuỗi HTML cho phiếu mượn
        String pm = "<h1 style='text-align:center;'>PHIẾU MƯỢN SÁCH TỪ THƯ VIÊN TRƯỜNG ĐẠI HỌC ABC</h1>";

        PhieuMuon_DTO phieu = pm_DAO.searchByMaPM(mapm);

        // Kiểm tra xem phiếu có tồn tại không
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu mượn.</p>";
        }

        // Thêm thông tin vào chuỗi
        pm += "Mã phiếu mượn: " + phieu.getMaPM() + "<br/>"
                + "Mã nhân viên: " + phieu.getMaNV() + "  "
                + "Tên nhân viên: " + "hii" + "<br/>" // Thêm tên nhân viên
                + "Mã độc giả: " + phieu.getMaKhach() + "  "
                + "Tên độc giả: " + "tạm" + "<br/>" // Thêm tên độc giả
                + "SĐT độc giả: " + "000" + "<br/>" // Thêm số điện thoại độc giả
                + "Ngày mượn: " + phieu.getNgayLap() + "  "
                + "Ngày dự đoán trả: " + phieu.getHanChot() + "<br/>"
                + "<div style='text-align:center;'>DANH SÁCH SÁCH ĐÃ MƯỢN</div><br/>";

        // Tạo bảng sách mượn
        pm += "<table style='width:100%; border-collapse:collapse;'>"
                + "<tr>"
                + "<th style='border:1px solid; padding:5px;'>Mã Sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Tên Sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Số Lượng</th>"
                + "</tr>";

        ArrayList<CTPM_DTO> listCTPM = ctpm_BUS.searchByMaPM(mapm);

        // Duyệt danh sách chi tiết phiếu mượn
        if (listCTPM != null && listCTPM.size() > 0) {
            for (CTPM_DTO ct : listCTPM) {
                //String tenSach = ctpm_BUS.getTenSach(ct.getMaSach()); // Lấy tên sách từ DAO
                pm += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + "tam" + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getSoLuong() + "</td>"
                        + "</tr>";
            }
        }

        pm += "</table><br/>";

        // Tổng số lượng
        pm += "Tổng số lượng: " + phieu.getTongSL() + "</br>";
        pm += "<div style='text-align:center;'>XIN CẢM ƠN!</div>";

        return pm;
    }

    public boolean checkInfor(String maPhieu, String maKhach, String maNV, String ngayLap, String hanChot, ArrayList<String> listMaSach, ArrayList<String> listSL, ArrayList listTrangThai) {
        if (maPhieu.isEmpty() || maKhach.isEmpty() || maNV.isEmpty() || ngayLap.isEmpty() || hanChot.isEmpty()
                || listMaSach.isEmpty() || listSL.isEmpty() || listTrangThai.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(maPhieu);
            Integer.parseInt(maKhach);
            Integer.parseInt(maNV);
            for (int i = 0; i < listMaSach.size(); i++) {
                Integer.parseInt(listMaSach.get(i));
                Integer.parseInt(listSL.get(i));
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
                PhieuMuon_DTO pm=new PhieuMuon_DTO();
                pm.setMaPM(Integer.parseInt(row.getCell(0).getStringCellValue()));
                pm.setMaKhach(Integer.parseInt(row.getCell(1).getStringCellValue()));
                pm.setMaNV(Integer.parseInt(row.getCell(2).getStringCellValue()));
                pm.setNgayLap(LocalDate.parse(row.getCell(3).getStringCellValue()));
                pm.setHanChot(LocalDate.parse(row.getCell(4).getStringCellValue()));
                String[] listMS=row.getCell(5).getStringCellValue().split(",");
                String[] listSL=row.getCell(6).getStringCellValue().split(",");
                String[] listTT=row.getCell(7).getStringCellValue().split(",");
                for (int i=0;i<listMS.length;i++)
                {
                    CTPM_DTO ct=new CTPM_DTO();
                    ct.setMaPM(Integer.parseInt(row.getCell(1).getStringCellValue()));
                    ct.setMaSach(Integer.parseInt(listMS[i]));
                    ct.setSoLuong(Integer.parseInt(listSL[i]));
                    ct.setTrangthai(listTT[i]);
                    ctpm_BUS.addCTPM(ct);
                }
                pm.setTongSL(Integer.parseInt(row.getCell(8).getStringCellValue()));
                pm_DAO.addPM(pm);
            }
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
        
    }

    public boolean exportExcel(String fileName) {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        System.out.println("Đường dẫn đầy đủ: " + filePath);

        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Tạo thư mục nếu chưa tồn tại
        }

        System.out.println("Bắt đầu khối try");
        XSSFWorkbook excelWorkbook = new XSSFWorkbook();
        System.out.println("tạo 0");
        XSSFSheet excelSheet = (XSSFSheet) excelWorkbook.createSheet("Danh sách phiếu mượn của thư viện");
        System.out.println("tạo 1");
        XSSFRow row = null;
        Cell cell = null;
        // Merge các cột thành một và căn giữa tiêu đề
        excelSheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));
        row = excelSheet.createRow(2);
        row.setHeight((short) 500);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("DANH SÁCH PHIẾU MƯỢN CỦA THƯ VIỆN TRƯỜNG ABC");
        CellStyle centerStyle = excelWorkbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(centerStyle);
        System.out.println("tạo 2");
        // Ghi tiêu đề cột
        row = excelSheet.createRow(3);
        String[] headers = {"Mã phiếu mượn", "Mã khách", "Mã nhân viên", "Ngày lập", "Hạn chót",
            "Danh sách mã sách", "Danh sách số lượng", "Danh sách trạng thái", "Tổng số lượng"};
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
            excelSheet.autoSizeColumn(i);
        }
        CellStyle cellStyle = excelWorkbook.createCellStyle();
        cellStyle.setWrapText(true);
        System.out.println("tạo 3");
        for (int i = 0; i < listPM.size(); i++) {
            PhieuMuon_DTO phieuMuon = listPM.get(i);
            row = excelSheet.createRow(4 + i);
            row.createCell(0).setCellValue(phieuMuon.getMaPM());
            row.createCell(1).setCellValue(phieuMuon.getMaKhach());
            row.createCell(2).setCellValue(phieuMuon.getMaNV());
            row.createCell(3).setCellValue(phieuMuon.getNgayLap().toString());
            row.createCell(4).setCellValue(phieuMuon.getHanChot().toString());

            String listMS = "";
            String listSL = "";
            String listTT = "";

            for (CTPM_DTO ctpm : ctpm_BUS.searchByMaPM(phieuMuon.getMaPM())) {
                listMS += ctpm.getMaSach() + "\n";
                listSL += ctpm.getSoLuong() + "\n";
                listTT += ctpm.getTrangthai() + "\n";
            }

            if (!listMS.isEmpty()) {
                listMS = listMS.substring(0, listMS.length() - 1);
                listSL = listSL.substring(0, listSL.length() - 1);
                listTT = listTT.substring(0, listTT.length() - 1);
            }

            Cell cellMS = row.createCell(5);
            cellMS.setCellValue(listMS);
            cellMS.setCellStyle(cellStyle); 

            Cell cellSL = row.createCell(6);
            cellSL.setCellValue(listSL);
            cellSL.setCellStyle(cellStyle);

            Cell cellTT = row.createCell(7);
            cellTT.setCellValue(listTT);
            cellTT.setCellStyle(cellStyle);
 
            row.createCell(8).setCellValue(phieuMuon.getTongSL());
            excelSheet.autoSizeColumn(i);

        }
        System.out.println("Ghi lên workbook thành công");
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            System.out.println("Mở file thành công");
            excelWorkbook.write(fileOutputStream);
            System.out.println("Đã ghi tệp thành công");
            excelWorkbook.close();
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
            return false;
        }
        return true;
    }

}
