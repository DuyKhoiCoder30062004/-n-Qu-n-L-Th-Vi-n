package BUS;

import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;
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
import java.util.List;
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

public class PhieuNhap_BUS {

    private ArrayList<PhieuNhap_DTO> listPN;
    private PhieuNhap_DAO pn_DAO = new PhieuNhap_DAO();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();

    public ArrayList<PhieuNhap_DTO> getList() {
        listPN = pn_DAO.getList();
        return listPN;
    }

    public boolean addPN(PhieuNhap_DTO pn) {
        return pn_DAO.addPN(pn);
    }

    public boolean updatePN(PhieuNhap_DTO pn) {
        return pn_DAO.updatePN(pn);
    }

    public boolean deletePN(int maPN) {
        return pn_DAO.deleteByMaPN(maPN);
    }

    public PhieuNhap_DTO searchByMaPN(int maPN) {
        return pn_DAO.searchByMaPN(maPN);
    }

    public StringBuilder[] searchPN(String option, String value) {
        StringBuilder[] arrayrs = new StringBuilder[2];
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        StringBuilder jsonRsCTPN = new StringBuilder("[");
        boolean firstItem = true;
        boolean fItemCTPN = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        for (PhieuNhap_DTO pn : pn_DAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pn.getMaPN()).contains(value)
                    || option.equals("Mã NCC") && String.valueOf(pn.getMaNCC()).contains(value)
                    || option.equals("Mã NV") && String.valueOf(pn.getMaNV()).contains(value)
                    || option.equals("Ngày lập") && pn.getNgayLap().format(formatter).contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPN\": \"" + pn.getMaPN() + "\","
                        + "\"maNCC\": \"" + pn.getMaNCC() + "\","
                        + "\"maNV\": \"" + pn.getMaNV() + "\","
                        + "\"ngayLap\": \"" + pn.getNgayLap() + "\","
                        + "\"tongSL\": \"" + pn.getTongSL() + "\","
                        + "\"tongTien\": \"" + pn.getTongTien() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
                ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.searchByMaPN(pn.getMaPN());
                System.out.println("CTPN" + listCTPN);
                for (CTPN_DTO ctpn : listCTPN) {
                    if (!fItemCTPN) {
                        jsonRsCTPN.append(",");
                    }
                    jsonRsCTPN.append("{"
                            + "\"maPN\": \"" + ctpn.getMaPN() + "\","
                            + "\"maSach\": \"" + ctpn.getMaSach() + "\","
                            + "\"listMaVach\": \"" + ctpn.getMaVach() + "\","
                            + "\"soLuong\": \"" + ctpn.getSoLuong() + "\","
                            + "\"donGia\": \"" + ctpn.getDonGia() + "\""
                            + "}");
                    fItemCTPN = false;
                }
            }
        }
        jsonResult.append("]");
        jsonRsCTPN.append("]");
        arrayrs[0] = jsonResult;
        arrayrs[1] = jsonRsCTPN;
        return arrayrs;
    }

    public String printPN(int maPN) {
        String pn = "<h1 style='text-align:center;'>PHIẾU NHẬP HÀNG </h1>";

        PhieuNhap_DTO phieu = pn_DAO.searchByMaPN(maPN);

        if (phieu == null) {
            return "<p>Không tìm thấy phiếu nhập.</p>";
        }

        pn += "Mã phiếu nhập: " + phieu.getMaPN() + "<br/>"
                + "Mã nhân viên: " + phieu.getMaNV() + "<br/>"
                + "Mã nhà cung cấp: " + phieu.getMaNCC() + "<br/>"
                + "<div style='text-align:center;'>CHI TIẾT PHIẾU NHẬP</div><br/>";

        pn += "<table style='width:100%; border-collapse:collapse;'>"
                + "<tr>"
                + "<th style='border:1px solid; padding:5px;'>Mã Phiếu nhập</th>"
                + "<th style='border:1px solid; padding:5px;'>Mã sách</th>"
                + "<th style='border:1px solid; padding:5px;'>Số Lượng</th>"
                + "<th style='border:1px solid; padding:5px;'>Đơn Giá</th>"
                + "</tr>";

        ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.searchByMaPN(maPN);

        if (listCTPN != null && listCTPN.size() > 0) {
            for (CTPN_DTO ct : listCTPN) {
                pn += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getSoLuong() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getDonGia() + "</td>"
                        + "</tr>";
            }
        }

        pn += "</table><br/>";

        pn += "Ngày lập: " + phieu.getNgayLap() + "<br/>" + "Tổng Số Lượng: " + phieu.getTongSL() + "<br/>"
                + "Tổng Tiền: " + phieu.getTongTien() + "<br/>";
        return pn;
    }

    public boolean importExcel(String fileName) {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        try (FileInputStream file = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                PhieuNhap_DTO pn = new PhieuNhap_DTO();

                pn.setMaPN(Integer.parseInt(row.getCell(0).getStringCellValue()));
                pn.setMaNCC(Integer.parseInt(row.getCell(1).getStringCellValue()));
                pn.setMaNV(Integer.parseInt(row.getCell(2).getStringCellValue()));
                pn.setTongSL(Integer.parseInt(row.getCell(3).getStringCellValue()));
                pn.setTongTien(Double.parseDouble(row.getCell(4).getStringCellValue()));

                String[] listMaSach = row.getCell(5).getStringCellValue().split(",");
                String[] listSL = row.getCell(6).getStringCellValue().split(",");
                String[] listDG = row.getCell(7).getStringCellValue().split(",");
                for (int j = 0; j < listMaSach.length; j++) {
                    CTPN_DTO ct = new CTPN_DTO();
                    ct.setMaPN(Integer.parseInt(row.getCell(1).getStringCellValue()));
                    ct.setMaSach(Integer.parseInt(listMaSach[j]));
                    ct.setSoLuong(Integer.parseInt(listSL[j]));
                    ct.setDonGia(Integer.parseInt(listDG[j]));
                    ctpn_BUS.addCTPN(ct);
                }

                pn_DAO.addPN(pn);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean exportExcel(String fileName) {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (XSSFWorkbook excelWorkbook = new XSSFWorkbook()) {
            XSSFSheet excelSheet = excelWorkbook.createSheet("Danh sách phiếu nhập");
            XSSFRow row = excelSheet.createRow(0);
            Cell cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH PHIẾU NHẬP");
            excelSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

            CellStyle titleStyle = excelWorkbook.createCellStyle();
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            cell.setCellStyle(titleStyle);

            String[] headers = {"Mã PN", "Mã NCC", "Mã NV", "Ngày lập",
                "DS Mã sách", "Danh sách mã vạch", "DS Đơn giá", "DS Số lượng", "Tổng SL", "Tổng tiền"};
            row = excelSheet.createRow(1);
            for (int i = 0; i < headers.length; i++) {
                cell = row.createCell(i, CellType.STRING);
                cell.setCellValue(headers[i]);
                excelSheet.autoSizeColumn(i);
            }

            CellStyle wrapTextStyle = excelWorkbook.createCellStyle();
            wrapTextStyle.setWrapText(true);

            List<PhieuNhap_DTO> listPN = pn_DAO.getList();
            int rowIndex = 2;
            for (PhieuNhap_DTO pn : listPN) {
                row = excelSheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(pn.getMaPN());
                row.createCell(1).setCellValue(pn.getMaNCC());
                row.createCell(2).setCellValue(pn.getMaNV());
                row.createCell(3).setCellValue(pn.getNgayLap().toString());

                List<CTPN_DTO> listCTPN = ctpn_BUS.searchByMaPN(pn.getMaPN());
                StringBuilder listMS = new StringBuilder();
                StringBuilder listMV = new StringBuilder();
                StringBuilder listDG = new StringBuilder();
                StringBuilder listSL = new StringBuilder();

                for (CTPN_DTO ctpn : listCTPN) {
                    listMS.append(ctpn.getMaSach()).append("\n");
                    listMV.append(String.join(",", ctpn.getMaVach())).append("\n");
                    listDG.append(ctpn.getDonGia()).append("\n");
                    listSL.append(ctpn.getSoLuong()).append("\n");
                }

                // Áp dụng xuống dòng tự động
                Cell msCell = row.createCell(4);
                msCell.setCellValue(listMS.toString().trim());
                msCell.setCellStyle(wrapTextStyle);

                Cell mvCell = row.createCell(5);
                mvCell.setCellValue(listMV.toString().trim());
                mvCell.setCellStyle(wrapTextStyle);

                Cell dgCell = row.createCell(6);
                dgCell.setCellValue(listDG.toString().trim());
                dgCell.setCellStyle(wrapTextStyle);

                Cell slCell = row.createCell(7);
                slCell.setCellValue(listSL.toString().trim());
                slCell.setCellStyle(wrapTextStyle);

                row.createCell(8).setCellValue(pn.getTongSL());
                row.createCell(9).setCellValue(pn.getTongTien());
            }

            try (FileOutputStream out = new FileOutputStream(filePath)) {
                excelWorkbook.write(out);
            }
        } catch (IOException e) {
            System.err.println("Lỗi ghi file Excel: " + e.getMessage());
            return false;
        }
        return true;
    }

}
