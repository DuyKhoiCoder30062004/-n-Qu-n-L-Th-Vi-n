/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPM_DAO;
import DAO.PhieuMuon_DAO;
import DTO.CTPM_DTO;
import DTO.DocGiaDTO;
import DTO.Nhanvien_DTO;
import DTO.PhieuMuon_DTO;
import DTO.Sach_DTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
    private DocGiaBUS dg_BUS = new DocGiaBUS();
    private Nhanvien_BUS nv_BUS = new Nhanvien_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();

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

    public StringBuilder[] searchPM(String option, String value) {
        StringBuilder[] arrayrs = new StringBuilder[2];
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        StringBuilder jsonRsCTPM = new StringBuilder("[");
        boolean firstItem = true;
        boolean fItemCTPM = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (PhieuMuon_DTO pm : pm_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pm.getMaPM()).contains(value)
                    || option.equals("Mã độc giả") && String.valueOf(pm.getMaKhach()).contains(value)
                    || option.equals("Mã NV") && String.valueOf(pm.getMaNV()).contains(value)
                    || option.equals("Ngày lập") && pm.getNgayLap().format(formatter).contains(value)) {
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
                ArrayList<CTPM_DTO> listCTPM = ctpm_BUS.searchByMaPM(pm.getMaPM());
                for (CTPM_DTO ctpm : listCTPM) {
                    if (!fItemCTPM) {
                        jsonRsCTPM.append(",");
                    }
                    jsonRsCTPM.append("{"
                            + "\"maPM\": \"" + ctpm.getMaPM() + "\","
                            + "\"maSach\": \"" + ctpm.getMaSach() + "\","
                            + "\"soLuong\": \"" + ctpm.getSoLuong() + "\","
                            + "\"trangThai\": \"" + ctpm.getTrangthai() + "\""
                            + "}");
                    fItemCTPM = false;
                }
            }
        }
        jsonResult.append("]");
        jsonRsCTPM.append("]");
        arrayrs[0] = jsonResult;
        arrayrs[1] = jsonRsCTPM;
        return arrayrs;
    }

    private Sach_DTO searchSach(int maSach) {
        for (Sach_DTO i : sach_BUS.getListSach()) {
            if (i.getMaSach() == maSach) {
                return i;
            }
        }
        return null;
    }

    private Nhanvien_DTO searchNV(int maNV) {
        for (Nhanvien_DTO i : nv_BUS.getList()) {
            if (i.getMaNV() == maNV) {
                return i;
            }
        }
        return null;
    }

    public String printPM(int mapm) {
        // Khởi tạo chuỗi HTML cho phiếu mượn
        String pm = "<h2 style='text-align:center;'>PHIẾU MƯỢN SÁCH TỪ THƯ VIÊN TRƯỜNG ĐẠI HỌC ABC</h2>";

        PhieuMuon_DTO phieu = pm_DAO.searchByMaPM(mapm);
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu mượn.</p>";
        }
        Nhanvien_DTO nv = searchNV(phieu.getMaNV());
        DocGiaDTO dg = dg_BUS.findDocGiaByMaKhach(phieu.getMaKhach());

        // Kiểm tra null cho nhân viên và độc giả
        if (nv == null || dg == null) {
            return "<p>Không tìm thấy thông tin nhân viên hoặc độc giả.</p>";
        }
        pm += "<table style='width:100%; border-spacing:10px 5px;'>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã phiếu mượn:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaPM() + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã nhân viên:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaNV() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>Họ & tên NV:</td>"
                + "<td style='text-align:left;'>" + nv.getHo() + " " + nv.getTen() + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã độc giả:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaKhach() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>Họ & tên ĐG:</td>"
                + "<td style='text-align:left;'>" + dg.getHoDG() + " " + dg.getTenDG() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>SĐT:</td>"
                + "<td style='text-align:left;'>" + dg.getSoDienThoai() + "</td>"
                + "</tr>"
                + "</table>"
                + "<div style='text-align:center;margin-top:20px;'>DANH SÁCH SÁCH ĐÃ MƯỢN</div><br/>";
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
                String tenSach = searchSach(ct.getMaSach()).getTenSach();
                pm += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + tenSach + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getSoLuong() + "</td>"
                        + "</tr>";
            }
        }

        pm += "</table><br/>"
                + "<table style='width:100%;'>"
                + "<tr>"
                + "<td style='text-align:left;width:71%; font-weight:bold;'>Tổng số lượng:</td>"
                + "<td style='width:29%; text-align:center; font-weight:bold;'>" + phieu.getTongSL() + "</td>"
                + "</tr>"
                + "</table>";
        pm += "<div style='text-align:center;margin-top:20px;'>XIN CẢM ƠN!</div>";
        return pm;
    }

    public boolean checkInfor(String maPhieu, String maKhach, String maNV, String ngayLap, String hanChot,
            String listMaSach, String listSL, String listTrangThai) {

        try {
            // Kiểm tra ngày
            LocalDate ngayLapDate = LocalDate.parse(ngayLap.trim());
            LocalDate hanChotDate = LocalDate.parse(hanChot.trim());
            if (ngayLapDate.isAfter(hanChotDate)) {
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Kiểm tra ngay that bai");
            return false;
        }

        // Kiểm tra chuỗi rỗng hoặc null
        if (maPhieu.trim().isEmpty() || maKhach.trim().isEmpty() || maNV.trim().isEmpty()
                || listMaSach.trim().isEmpty() || listSL.trim().isEmpty() || listTrangThai.trim().isEmpty()) {
            System.out.println("Kiểm tra du lieu rong that bai");
            return false;
        }

        // Kiểm tra các thông tin liên quan trong CSDL
        try {
            int maPhieuInt = Integer.parseInt(maPhieu.trim());
            int maKhachInt = Integer.parseInt(maKhach.trim());
            // Kiểm tra mã phiếu mượn
            if (pm_DAO.searchByMaPM(maPhieuInt) != null || dg_BUS.findDocGiaByMaKhach(maKhachInt) == null || nv_BUS.timKiemNhanVien(maNV.trim()) == null) {
                System.out.println("Tìm tồn tại có lỗi");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Kiểm tra ma phieu kieu int that bai");
            return false;
        }

        // Kiểm tra danh sách sách
        String[] maSachArray = listMaSach.split(",");
        String[] slArray = listSL.split(",");
        if (maSachArray.length != slArray.length) {
            System.out.println("Độ dài các list có lỗi");
            return false;
        }
        for (int i = 0; i < maSachArray.length; i++) {
            if (maSachArray[i].trim().isEmpty()
                    || slArray[i].trim().isEmpty()
                    || sach_BUS.timSachTheoMaSach(maSachArray[i]).isEmpty()) {
                System.out.println("các ô rỗng hoặc tìm mã sách k ra");
                return false;
            }
        }
        try {
            for (int i = 0; i < maSachArray.length; i++) {
                Integer.parseInt(maSachArray[i]);
                Integer.parseInt(slArray[i]);
            }
        } catch (Exception e) {
            System.out.println("Kiểm tra danh sach that bai");
            return false;
        }

        return true;
    }

    public boolean importExcel(String fileName) {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        try (FileInputStream file = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            System.out.println("Đã mở file Excel thành công.");
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                rowIterator.next(); // Bỏ qua dòng tiêu đề
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                System.out.println("Dữ liệu dòng:");
                for (int i = 0; i <= 7; i++) {
                    System.out.println("Cột " + i + ": " + getCellValue(row.getCell(i)));
                }

                if (!checkInfor(getCellValue(row.getCell(0)), getCellValue(row.getCell(1)), getCellValue(row.getCell(2)),
                        getCellValue(row.getCell(3)), getCellValue(row.getCell(4)), getCellValue(row.getCell(5)),
                        getCellValue(row.getCell(6)), getCellValue(row.getCell(7)))) {
                    System.out.println("Dữ liệu không hợp lệ. Bỏ qua dòng.");
                    continue;
                }

                PhieuMuon_DTO pm = new PhieuMuon_DTO();
                pm.setMaPM(Integer.parseInt(getCellValue(row.getCell(0))));
                pm.setMaKhach(Integer.parseInt(getCellValue(row.getCell(1))));
                pm.setMaNV(Integer.parseInt(getCellValue(row.getCell(2))));
                pm.setNgayLap(LocalDate.parse(getCellValue(row.getCell(3))));
                pm.setHanChot(LocalDate.parse(getCellValue(row.getCell(4))));

                String[] listMS = getCellValue(row.getCell(5)).split(",");
                String[] listSL = getCellValue(row.getCell(6)).split(",");
                String[] listTT = getCellValue(row.getCell(7)).split(",");
                pm.setTongSL(0);
                pm_DAO.addPM(pm);
                int tongsl = 0;
                
                for (int i = 0; i < listMS.length; i++) {
                    CTPM_DTO ct = new CTPM_DTO();
                    ct.setMaPM(pm.getMaPM());
                    ct.setMaSach(Integer.parseInt(listMS[i]));
                    ct.setSoLuong(Integer.parseInt(listSL[i]));
                    tongsl += ct.getSoLuong();
                    ct.setTrangthai(listTT[i]);
                    ctpm_BUS.addCTPM(ct);
                }
                pm_DAO.updateSL(pm.getMaPM(), tongsl);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file Excel: " + e.getMessage());
            return false;
        }
        return true;
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toLocalDate().toString(); // Nếu là ngày
                }
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
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
