package BUS;

import DAO.PhieuTra_DAO;
import DTO.CTPM_DTO;
import DTO.CTPT_DTO;
import DTO.DocGia_DTO;
import DTO.Nhanvien_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuTra_DTO;
import DTO.Sach_DTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ADMIN
 */
public class PhieuTra_BUS {

    private PhieuTra_DAO phieuTraDAO;
    private CTPT_BUS ctpt_BUS = new CTPT_BUS();
    private DocGiaBUS dg_BUS = new DocGiaBUS();
    private Nhanvien_BUS nv_BUS = new Nhanvien_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();

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

    public StringBuilder[] searchPM(String option, String value) {
        StringBuilder[] arrayrs = new StringBuilder[2];
        StringBuilder jsonResult = new StringBuilder("[");
        StringBuilder jsonRsCTPM = new StringBuilder("[");
        boolean firstItem = true;
        boolean fItemCTPM = true;
        for (PhieuTra_DTO pm : phieuTraDAO.getList()) {
            // Kiểm tra điều kiện để thêm vào JSON
            if (option.equals("Mã phiếu") && String.valueOf(pm.getMaPT()).contains(value)
                    || option.equals("Mã PM") && String.valueOf(pm.getMaPM()).contains(value)
                    || option.equals("Mã NV") && String.valueOf(pm.getMaNV()).contains(value)) {
                if (!firstItem) {
                    jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                }
                jsonResult.append("{"
                        + "\"maPhieu\": \"" + pm.getMaPT() + "\","
                        + "\"maPM\": \"" + pm.getMaPM() + "\","
                        + "\"maNV\": \"" + pm.getMaNV() + "\","
                        + "\"tongSL\": \"" + pm.getTongSL() + "\""
                        + "}");
                firstItem = false; // Đánh dấu rằng phần tử đầu tiên đã được thêm
                List<CTPT_DTO> listCTPM = ctpt_BUS.searchByMaPT(pm.getMaPT());
                if (listCTPM.isEmpty()) {
                    System.out.print("Không có ctpt");
                } else {
                    for (CTPT_DTO ctpm : listCTPM) {
                        if (!fItemCTPM) {
                            jsonRsCTPM.append(",");
                        }
                        jsonRsCTPM.append("{"
                                + "\"maPT\": \"" + ctpm.getMaPT() + "\","
                                + "\"maSach\": \"" + ctpm.getMaSach() + "\","
                                + "\"maVachLoi\": \"" + ctpm.getMaVachLoi() + "\","
                                + "\"ngayTra\": \"" + ctpm.getNgayTra() + "\","
                                + "\"soLuong\": \"" + ctpm.getSoLuong() + "\""
                                + "}");
                        fItemCTPM = false;
                    }
                }
            }
        }
        jsonResult.append("]");
        jsonRsCTPM.append("]");
        arrayrs[0] = jsonResult;
        arrayrs[1] = jsonRsCTPM;
        return arrayrs;
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
        XSSFSheet excelSheet = (XSSFSheet) excelWorkbook.createSheet("Danh sách phiếu trả của thư viện");
        System.out.println("tạo 1");
        XSSFRow row = null;
        Cell cell = null;
        // Merge các cột thành một và căn giữa tiêu đề
        excelSheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
        row = excelSheet.createRow(2);
        row.setHeight((short) 500);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("DANH SÁCH PHIẾU TRẢ CỦA THƯ VIỆN TRƯỜNG ABC");
        CellStyle centerStyle = excelWorkbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(centerStyle);
        System.out.println("tạo 2");
        // Ghi tiêu đề cột
        row = excelSheet.createRow(3);
        String[] headers = {"Mã phiếu trả", "Mã phiếu mượn", "Mã nhân viên", "Danh sách mã sách",
            "Danh sách mã vạch lỗi", "Danh sách ngày trả", "Danh sách số lượng", "Tổng số lượng"};
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
            excelSheet.autoSizeColumn(i);
        }
        CellStyle cellStyle = excelWorkbook.createCellStyle();
        cellStyle.setWrapText(true);
        System.out.println("tạo 3");
        for (int i = 0; i < phieuTraDAO.getList().size(); i++) {
            PhieuTra_DTO pt = phieuTraDAO.getList().get(i);
            row = excelSheet.createRow(4 + i);
            row.createCell(0).setCellValue(pt.getMaPT());
            row.createCell(1).setCellValue(pt.getMaPM());
            row.createCell(2).setCellValue(pt.getMaNV());
            String listMS = "";
            String listMVLoi = "";
            String listSL = "";
            String listNT = "";

            for (CTPT_DTO ctpt : ctpt_BUS.searchByMaPT(pt.getMaPT())) {
                listMS += ctpt.getMaSach() + "\n";
                listMVLoi += ctpt.getMaVachLoi() + "\n";
                listSL += ctpt.getSoLuong() + "\n";
                listNT += ctpt.getNgayTra() + "\n";
            }

            if (!listMS.isEmpty()) {
                listMS = listMS.substring(0, listMS.length() - 1);
                listSL = listSL.substring(0, listSL.length() - 1);
                listMVLoi = listMVLoi.substring(0, listMVLoi.length() - 1);
                listNT = listNT.substring(0, listNT.length() - 1);
            }

            Cell cellMS = row.createCell(3);
            cellMS.setCellValue(listMS);
            cellMS.setCellStyle(cellStyle);

            Cell cellMVL = row.createCell(4);
            cellMVL.setCellValue(listMVLoi);
            cellMVL.setCellStyle(cellStyle);

            Cell cellNT = row.createCell(5);
            cellNT.setCellValue(listNT);
            cellNT.setCellStyle(cellStyle);

            Cell cellSL = row.createCell(6);
            cellSL.setCellValue(listSL);
            cellSL.setCellStyle(cellStyle);

            row.createCell(7).setCellValue(pt.getTongSL());
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

    private boolean checkInfor(String maPT, String maNV, String maPM, String listMS,
            String listMV, String listNT, String listSL) {
        // Kiểm tra null và rỗng
        if (maPT == null || maNV == null || maPM == null || listMS == null || listMV == null
                || listNT == null || listSL == null
                || maPT.isEmpty() || maNV.isEmpty() || maPT.isEmpty()
                || listMS.isEmpty() || listMV.isEmpty() || listNT.isEmpty()
                || listSL.isEmpty()
                || phieuTraDAO.searchByMaPT(Integer.parseInt(maPT)) != null
                || pm_BUS.searchByMaPM(Integer.parseInt(maPM)) == null
                || nv_BUS.timKiemNhanVien(maNV) == null) {
            return false;
        }

        String[] listMSach = listMS.split("\n");
        String[] listMVach = listMV.split("\n");
        String[] listNTra = listNT.split("\n");
        String[] listSluong = listSL.split("\n");

        if (listMSach.length != listMVach.length || listMSach.length != listNTra.length
                || listMSach.length != listSluong.length) {
            return false;
        }
        for (int i = 0; i < listMSach.length; i++) {
            if (sach_BUS.timSachTheoMaSach(listMSach[i]).get(0) == null) {
                return false;
            }
            String[] MVLoi_TheoTungMa = listMVach[i].split(",");
            for (int j = 0; j < MVLoi_TheoTungMa.length; j++) {
                if (cts_BUS.searchCTSachByMaVach(MVLoi_TheoTungMa[j].trim()).get(0) == null) {
                    return false;
                } else {
                    if (cts_BUS.searchCTSachByMaVach(MVLoi_TheoTungMa[j].trim()).get(0).getMaSach() != Integer.parseInt(listMSach[i])) {
                        return false;
                    }
                }
            }
            try {
                // Kiểm tra ngày
                LocalDate ngayLapDate = LocalDate.parse(listNTra[i].trim());
            } catch (DateTimeParseException e) {
                System.out.println("Kiểm tra ngay that bai");
                return false;
            }
            try {
                Integer.parseInt(listSluong[i]);
            } catch (NumberFormatException e) {
                return false;
            }
            if(Integer.parseInt(listSluong[i])<=0)
                return false;

        }

        return true;
    }

    public String importExcel(String fileName) {
        String maDuocImport = "";
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();  // Tạo thư mục nếu chưa tồn tại
        }
        try (FileInputStream fis = new FileInputStream(filePath); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Bỏ qua dòng đầu tiên (tiêu đề)
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Kiểm tra dữ liệu hợp lệ
                if (!checkInfor(
                        getCellValue(row.getCell(0)), getCellValue(row.getCell(1)), getCellValue(row.getCell(2)),
                        getCellValue(row.getCell(3)), getCellValue(row.getCell(4)), getCellValue(row.getCell(5)),
                        getCellValue(row.getCell(6)))) {
                    System.out.println("Dữ liệu không hợp lệ. Bỏ qua dòng.");
                    continue;
                }

                // Tạo đối tượng Phiếu Phạt DTO
                PhieuTra_DTO pp = new PhieuTra_DTO();
                pp.setMaPT(Integer.parseInt(getCellValue(row.getCell(0))));
                pp.setMaPM(Integer.parseInt(getCellValue(row.getCell(1))));
                pp.setMaNV(Integer.parseInt(getCellValue(row.getCell(2))));
                // Tách chuỗi và chuyển thành ArrayList
                ArrayList<String> listMS = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(3)).split("\n")));
                ArrayList<String> listMV = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(4)).split("\n")));
                ArrayList<String> listNT = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(5)).split("\n")));
                ArrayList<String> listSL = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(6)).split("\n")));
                pp.setTongSL(0);
                phieuTraDAO.addPT(pp);
                maDuocImport += pp.getMaPT() + ",";
                int tsl = 0;

                // Duyệt qua từng phần tử và thêm Chi Tiết Phiếu Phạt
                for (int i = 0; i < listMS.size(); i++) {
                    CTPT_DTO ct = new CTPT_DTO();
                    ct.setMaPT(Integer.parseInt(getCellValue(row.getCell(0))));
                    ct.setMaSach(Integer.parseInt(listMS.get(i)));
                    ct.setMaVachLoi(listMV.get(i));
                    ct.setNgayTra(LocalDate.parse(listNT.get(i)));
                    ct.setSoLuong(Integer.parseInt(listSL.get(i)));
                    tsl += ct.getSoLuong();
                    ctpt_BUS.addCTPT(ct);
                }
                pp.setTongSL(tsl);
                phieuTraDAO.updatePT(pp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "fasle";
        }
        return maDuocImport;
    }

    // Hàm chuyển đổi giá trị ô trong Excel thành chuỗi
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return "";
        }
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
        String pm = "<h2 style='text-align:center;'>PHIẾU TRẢ SÁCH TỪ THƯ VIÊN TRƯỜNG ĐẠI HỌC ABC</h2>";

        PhieuTra_DTO phieu = phieuTraDAO.searchByMaPT(mapm);
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu mượn.</p>";
        }
        Nhanvien_DTO nv = searchNV(phieu.getMaNV());
        PhieuMuon_DTO phieumuon = pm_BUS.searchByMaPM(phieu.getMaPM());
        DocGia_DTO dg = dg_BUS.searchByMaDG(phieumuon.getMaKhach());

        // Kiểm tra null cho nhân viên và độc giả
        if (nv == null || dg == null) {
            return "<p>Không tìm thấy thông tin nhân viên hoặc độc giả.</p>";
        }
        pm += "<table style='width:100%; border-spacing:10px 5px;'>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã phiếu trả:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaPT() + "</td>"
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
                + "<td style='text-align:left;'>" + dg.getMaDG() + "</td>"
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
                + "<th style='border:1px solid; padding:5px;'>Mã vạch lỗi</th>"
                + "<th style='border:1px solid; padding:5px;'>Ngày trả</th>"
                + "<th style='border:1px solid; padding:5px;'>Số Lượng</th>"
                + "</tr>";

        List<CTPT_DTO> listCTPM = ctpt_BUS.searchByMaPT(mapm);

        // Duyệt danh sách chi tiết phiếu trả
        if (listCTPM != null && listCTPM.size() > 0) {
            for (CTPT_DTO ct : listCTPM) {
                String tenSach = searchSach(ct.getMaSach()).getTenSach();
                pm += "<tr>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + tenSach + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getMaVachLoi() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getNgayTra() + "</td>"
                        + "<td style='border:1px solid; text-align:center;'>" + ct.getSoLuong() + "</td>"
                        + "</tr>";
            }
        }

        pm += "</table><br/>"
                + "<table style='width:100%;'>"
                + "<tr>"
                + "<td style='text-align:left;width:80%; font-weight:bold;'>Tổng số lượng:</td>"
                + "<td style='width:20%; text-align:center; font-weight:bold;'>" + phieu.getTongSL() + "</td>"
                + "</tr>"
                + "</table>";
        pm += "<div style='text-align:center;margin-top:20px;'>XIN CẢM ƠN!</div>";
        return pm;
    }
}
