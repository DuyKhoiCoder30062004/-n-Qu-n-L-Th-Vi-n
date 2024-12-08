package DAO;

import ConnectDB.dangNhapDatabase;
import DTO.PhieuTra_DTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class PhieuTra_DAO {

    private dangNhapDatabase dnDB = null;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ArrayList<PhieuTra_DTO> getList() {
        ArrayList<PhieuTra_DTO> listPT = new ArrayList<>();
        try {
            String qry = "SELECT * FROM phieutra";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            rs = ps.executeQuery();
            while (rs.next()) {
                PhieuTra_DTO pt = new PhieuTra_DTO();
                pt.setMaPT(rs.getInt("mapt"));
                pt.setMaPM(rs.getInt("mapm"));
                pt.setMaNV(rs.getInt("manv"));
                pt.setTongSL(rs.getInt("tongsl"));
                listPT.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources();
        }
        return listPT;
    }

    public boolean addPT(PhieuTra_DTO pt) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "INSERT INTO phieutra (mapt, mapm, manv, tongsl) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(qry);
            ps.setInt(1, pt.getMaPT());
            ps.setInt(2, pt.getMaPM());
            ps.setInt(3, pt.getMaNV());
            ps.setInt(4, pt.getTongSL());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Lỗi khi thêm phiếu trả: " + e.getMessage());
            result = false;
        } finally {
            closeResources();
        }
        return result;
    }

    public boolean updatePT(PhieuTra_DTO pt) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "UPDATE phieutra SET mapm = ?, manv = ?, tongsl = ? WHERE mapt = ?";
            ps = conn.prepareStatement(qry);
            ps.setInt(1, pt.getMaPM());
            ps.setInt(2, pt.getMaNV());
            ps.setInt(3, pt.getTongSL());
            ps.setInt(5, pt.getMaPT());
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật phiếu trả: " + e.getMessage());
            result = false;
        } finally {
            closeResources();
        }
        return result;
    }

    public boolean deletePT(int mapt) {
        boolean result = true;
        try {
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            String qry = "DELETE FROM phieutra WHERE mapt = ?";
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapt);
            ps.executeUpdate();
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa phiếu trả: " + e.getMessage());
            result = false;
        } finally {
            closeResources();
        }
        return result;
    }

    public PhieuTra_DTO searchByMaPT(int mapt) {
        PhieuTra_DTO pt = null;
        try {
            String qry = "SELECT * FROM phieutra WHERE mapt = ?";
            dnDB = new dangNhapDatabase();
            conn = dnDB.openConnection();
            ps = conn.prepareStatement(qry);
            ps.setInt(1, mapt);
            rs = ps.executeQuery();
            if (rs.next()) {
                pt = new PhieuTra_DTO();
                pt.setMaPT(rs.getInt("mapt"));
                pt.setMaPM(rs.getInt("mapm"));
                pt.setMaNV(rs.getInt("manv"));
                pt.setTongSL(rs.getInt("tongsl"));
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm phiếu trả: " + e.getMessage());
        } finally {
            closeResources();
        }
        return pt;
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) dnDB.closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print a PhieuTra as an HTML String
    public String printPhieuTra(PhieuTra_DTO pt) {
        String html = "<h1 style='text-align:center;'>PHIẾU TRẢ SÁCH</h1>";
        html += "<p>Mã phiếu trả: " + pt.getMaPT() + "</p>";
        html += "<p>Mã phiếu mượn: " + pt.getMaPM() + "</p>";
        html += "<p>Mã nhân viên: " + pt.getMaNV() + "</p>";
        html += "<p>Tổng số lượng: " + pt.getTongSL() + "</p>";
        html += "<div style='text-align:center;'>XIN CẢM ƠN!</div>";
        return html;
    }

    // Method to export a list of PhieuTra to an Excel file
    public boolean exportToExcel(String filePath, List<PhieuTra_DTO> phieuTraList) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("PhieuTra");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("MaPT");
            headerRow.createCell(1).setCellValue("MaPM");
            headerRow.createCell(2).setCellValue("MaNV");
            headerRow.createCell(3).setCellValue("TongSL");
            headerRow.createCell(4).setCellValue("NgayTra");

            // Populate data rows
            int rowIdx = 1;
            for (PhieuTra_DTO pt : phieuTraList) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(pt.getMaPT());
                row.createCell(1).setCellValue(pt.getMaPM());
                row.createCell(2).setCellValue(pt.getMaNV());
                row.createCell(3).setCellValue(pt.getTongSL());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error exporting to Excel: " + e.getMessage());
            return false;
        }
    }

    // Method to import PhieuTra data from an Excel file
    public List<PhieuTra_DTO> importFromExcel(String filePath) {
        List<PhieuTra_DTO> phieuTraList = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  // Skip header row
                Row row = sheet.getRow(i);
                if (row != null) {
                    int maPT = (int) row.getCell(0).getNumericCellValue();
                    int maPM = (int) row.getCell(1).getNumericCellValue();
                    int maNV = (int) row.getCell(2).getNumericCellValue();
                    int tongSL = (int) row.getCell(3).getNumericCellValue();
                    String ngayTraStr = row.getCell(4).getStringCellValue();

                    PhieuTra_DTO phieuTra = new PhieuTra_DTO(maPT, maPM, maNV, tongSL);
                    phieuTraList.add(phieuTra);
                }
            }
        } catch (IOException e) {
            System.err.println("Error importing from Excel: " + e.getMessage());
        }
        return phieuTraList;
    }
}
