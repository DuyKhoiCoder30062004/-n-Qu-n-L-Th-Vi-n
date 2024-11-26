package BUS;

import DAO.PhieuTra_DAO;
import DTO.PhieuTra_DTO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class PhieuTra_BUS {
    private PhieuTra_DAO phieuTraDAO;

    public PhieuTra_BUS() {
        phieuTraDAO = new PhieuTra_DAO();
    }

    public ArrayList<PhieuTra_DTO> getListPhieuTra() {
        return phieuTraDAO.getList();
    }

    public boolean addPhieuTra(PhieuTra_DTO pt) {
        return phieuTraDAO.addPT(pt);
    }

    public boolean updatePhieuTra(PhieuTra_DTO pt) {
        return phieuTraDAO.updatePT(pt);
    }

    public boolean deletePhieuTra(int mapt) {
        return phieuTraDAO.deletePT(mapt);
    }

    public PhieuTra_DTO searchByMaPT(int mapt) {
        return phieuTraDAO.searchByMaPT(mapt);
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
                row.createCell(4).setCellValue(pt.getNgayTra().toString());
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
                    LocalDate ngayTra = LocalDate.parse(ngayTraStr);

                    PhieuTra_DTO phieuTra = new PhieuTra_DTO(maPT, maPM, maNV, tongSL, ngayTra);
                    phieuTraList.add(phieuTra);
                }
            }
        } catch (IOException e) {
            System.err.println("Error importing from Excel: " + e.getMessage());
        }
        return phieuTraList;
    }
}
