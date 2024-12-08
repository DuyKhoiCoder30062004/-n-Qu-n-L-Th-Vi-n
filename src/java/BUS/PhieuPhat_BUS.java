/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.CTPP_DAO;
import DAO.PhieuPhat_DAO;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import DTO.DocGia_DTO;
import DTO.Loi_DTO;
import DTO.Nhanvien_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
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
import static org.apache.tomcat.jakartaee.commons.lang3.ArrayUtils.contains;

/**
 *
 * @author ADMIN
 */
public class PhieuPhat_BUS {

    private PhieuPhat_DAO pp_DAO = new PhieuPhat_DAO();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();
    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS = new CTPT_BUS();
    private Nhanvien_BUS nv_BUS = new Nhanvien_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();
    private Loi_BUS loi_BUS = new Loi_BUS();
    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private DocGiaBUS dg_BUS = new DocGiaBUS();

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
        boolean fItemCTPP = true;

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
                ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.searchByMaPP(pq.getMaPP());
                for (CTPP_DTO ctpp : listCTPP) {
                    if (!fItemCTPP) {
                        jsonRsCTPP.append(",");
                    }
                    jsonRsCTPP.append("{"
                            + "\"maPhieu\": \"" + ctpp.getMaPP() + "\","
                            + "\"maSach\": \"" + ctpp.getMaSach() + "\","
                            + "\"maVach\": \"" + ctpp.getMaVach() + "\","
                            + "\"ngayLap\": \"" + ctpp.getNgayLap() + "\","
                            + "\"liDo\": \"" + ctpp.getLiDo() + "\","
                            + "\"tien\": \"" + ctpp.getTien() + "\""
                            + "}");
                    fItemCTPP = false;
                }
            }
        }
        jsonResult.append("]");
        jsonRsCTPP.append("]");
        arrayrs[0] = jsonResult;
        arrayrs[1] = jsonRsCTPP;
        return arrayrs;
    }

    public String printPP(int maPP) {
        // Khởi tạo chuỗi HTML cho phiếu phạt
        String pp = "<h2 style='text-align:center;'>PHIẾU PHẠT TỪ THƯ VIỆN TRƯỜNG ĐẠI HỌC ABC</h2>";

        PhieuPhat_DTO phieu = pp_DAO.searchByMaPP(maPP);

        // Kiểm tra xem phiếu có tồn tại không
        if (phieu == null) {
            return "<p>Không tìm thấy phiếu phạt.</p>";
        }

        PhieuTra_DTO pt = pt_BUS.searchByMaPT(phieu.getMaPT());
        PhieuMuon_DTO pm = pm_BUS.searchByMaPM(pt.getMaPM());
        Nhanvien_DTO nv = nv_BUS.timKiemNhanVien(String.valueOf(phieu.getMaNV())).get(0);
        DocGia_DTO dg = dg_BUS.searchByMaDG(pm.getMaKhach());

        // Thêm thông tin vào chuỗi
        pp += "<table style='width:100%; border-spacing:10px 5px;'>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã phiếu phạt:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaPP() + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã nhân viên:</td>"
                + "<td style='text-align:left;'>" + phieu.getMaNV() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>Họ & tên NV:</td>"
                + "<td style='text-align:left;'>" + nv.getHo() + " " + nv.getTen() + "</td>"
                + "</tr>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Mã độc giả:</td>"
                + "<td style='text-align:left;'>" + pm.getMaKhach() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>Họ & tên ĐG:</td>"
                + "<td style='text-align:left;'>" + dg.getHoDG() + " " + dg.getTenDG() + "</td>"
                + "<td style='text-align:left; font-weight:bold;'>SĐT:</td>"
                + "<td style='text-align:left;'>" + dg.getSoDienThoai() + "</td>"
                + "</tr>"
                + "</table>"
                + "<div style='text-align:center; font-weight:bold; margin-top:20px;'>DANH SÁCH SÁCH LÀM HƯ</div><br/>";

        // Tạo bảng sách mượn
        pp += "<table style='width:100%; border-collapse:collapse; margin-bottom:20px;'>"
                + "<tr>"
                + "<th style='border:1px solid; padding:10px; text-align:center;'>Mã Sách</th>"
                + "<th style='border:1px solid; padding:10px; text-align:center;'>Mã vạch</th>"
                + "<th style='border:1px solid; padding:10px; text-align:center;'>Lí do</th>"
                + "<th style='border:1px solid; padding:10px; text-align:center;'>Tiền</th>"
                + "</tr>";

        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.searchByMaPP(maPP);

        // Duyệt danh sách chi tiết phiếu phạt
        if (listCTPP != null && listCTPP.size() > 0) {
            for (CTPP_DTO ct : listCTPP) {
                String lyDo = String.join(",", ct.getLiDo());
                pp += "<tr>"
                        + "<td style='border:1px solid; padding:10px; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; padding:10px; text-align:center;'>" + ct.getMaSach() + "</td>"
                        + "<td style='border:1px solid; padding:10px; text-align:center;'>" + lyDo + "</td>"
                        + "<td style='border:1px solid; padding:10px; text-align:center;'>" + ct.getTien() + "</td>"
                        + "</tr>";
            }
        }
        pp += "</table><br/>"
                + "<table style='width:100%;'>"
                + "<tr>"
                + "<td style='text-align:left; font-weight:bold;'>Tổng Tiền:</td>"
                + "<td style='text-align:right; font-weight:bold;'>" + phieu.getTongTien() + " VND</td>"
                + "</tr>"
                + "</table>";

        return pp;
    }

    //Kiểm tra mã vạch xem mã vạch này đúng có lỗi không
    private boolean checkMaVach(int maPT, int maSach, String maVach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(maPT)) {
            System.out.println("PT " + i.getMaSach() + "   " + i.getMaVachLoi());
            //System.out.println("kiểm tra mã vạch"+ i.getMaVachLoi())
            if (i.getMaSach() == maSach && i.getMaVachLoi() != null && i.getMaVachLoi().contains(maVach.trim())) {
                System.out.println("PT kiểm tra đúng " + i.getMaSach() + "   " + i.getMaVachLoi());
                return true;
            }
        }
        return false;
    }

    //Kiểm tra mã sách này có trong phiếu  trả k
    private boolean checkMaSach_PT_Co_Khong(int maPT, int maSach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(maPT)) {
            if (i.getMaSach() == maSach) {
                return true;
            }
        }
        return false;
    }

    //kiểm tra mã sách này có mã vạch lỗi k
    private boolean checkMaSach(int maPT, int maSach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(maPT)) {
            if (i.getMaSach() == maSach && i.getMaVachLoi() != null) {
                return true;
            }
        }
        return false;
    }

    //kiểm tra sách này có nộp trễ hạn không
    private boolean checkTreHan(int maPT, int maSach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(maPT)) {
            if (i.getMaSach() == maSach && i.getMaVachLoi() != null) {
                PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
                PhieuMuon_DTO pm = pm_BUS.searchByMaPM(pt_BUS.searchByMaPT(maPT).getMaPM());
                if (i.getNgayTra().isAfter(pm.getHanChot())) {
                    return true;
                }
            }
        }
        return false;
    }

    //kiểm tra lỗi này có trong listTT không
    private boolean checkLoi(String listTT, String loi) {
        if (listTT.contains(loi)) {
            return true;
        }
        return false;
    }

    //Tính tiền
    private float tinhTien(String maSach, String liDo) {
        float tien = 0;
        String[] listLiDo = liDo.split(",");
        Sach_DTO sach = sach_BUS.timSachTheoMaSach(maSach).get(0);
        for (String i : listLiDo) {
            System.out.println("%tiền" + loi_BUS.searchByTenLoi(i).getPhamTramTien());
            tien += sach.getGiaTien() * loi_BUS.searchByTenLoi(i).getPhamTramTien();
        }
        return tien;
    }

    //Tìm những lí do trùng với tình trạng của sách
    private String checkLiDo(String TTSach, String liDo) {
        if (TTSach != null) {
            String[] listLiDo = liDo.split(",");
            String tam = "";
            for (String i : listLiDo) {
                if (!i.equals("trả trễ") && TTSach.contains(i)) {
                    tam += i + " ";
                }
            }
            if (!tam.equals("")) {
                return tam;
            }
        }
        return "";
    }

    //Kiểm tra xem mã vạch này có của sách này không
    private boolean checkMaVachCuaSach(String maSach, String maVach) {
        for (CTSach_DTO i : cts_BUS.searchCTSachByMaSach(maSach)) {
            if (i.getMaVach().equals(maVach)) {
                return true;
            }
        }
        return false;
    }

    //Tìm kiếm ct sách
    private CTSach_DTO searchCTS(String maVach) {
        for (CTSach_DTO i : cts_BUS.getList()) {
            if (i.getMaVach().equals(maVach)) {
                return i;
            }
        }
        return null;
    }

    private boolean checkInfor(String maPP, String maNV, String maPT, String listMS, String listMV, String listNL,
            String listLiDo, String listT) {
        // Kiểm tra null và rỗng
        if (maPP == null || maNV == null || maPT == null || listMS == null || listMV == null
                || listNL == null || listLiDo == null || listT == null
                || maPP.isEmpty() || maNV.isEmpty() || maPT.isEmpty()
                || listMS.isEmpty() || listMV.isEmpty() || listNL.isEmpty()
                || listLiDo.isEmpty() || listT.isEmpty()
                || pp_DAO.searchByMaPP(Integer.parseInt(maPP)) != null
                || pt_BUS.searchByMaPT(Integer.parseInt(maPT)) == null
                || nv_BUS.timKiemNhanVien(maNV) == null) {
            return false;
        }

        String[] listMSach = listMS.split("\n");
        String[] listMVach = listMV.split("\n");
        String[] listNLap = listNL.split("\n");
        String[] listLDo = listLiDo.split("\n");
        String[] listTien = listT.split("\n");

        if (listMSach.length != listMVach.length || listMSach.length != listNLap.length
                || listMSach.length != listLDo.length || listMSach.length != listTien.length) {
            return false;
        }

        try {
            for (int i = 0; i < listMSach.length; i++) {
                // Kiểm tra dữ liệu mã sách, mã vạch, tiền
                if (sach_BUS.timSachTheoMaSach(listMSach[i]) == null) {
                    return false;
                }

                CTSach_DTO cts = searchCTS(listMVach[i]);
                if (cts == null || cts.getMaSach() != Integer.parseInt(listMSach[i])) {
                    return false;
                }

                if (!checkMaVach(Integer.parseInt(maPT), Integer.parseInt(listMSach[i]), listMVach[i])) {
                    return false;
                }
                if(!checkLiDo(cts.getTinhTrangSach(),listLDo[i]).equals(""))
                    return false;
                // Kiểm tra lỗi
                String[] listLD_Tung_Ma = listLDo[i].split(",");
                for (int j = 0; j < listLD_Tung_Ma.length; j++) {
                    listLD_Tung_Ma[j] = listLD_Tung_Ma[j].trim();
                    if (loi_BUS.searchByTenLoi(listLD_Tung_Ma[j]) == null) {
                        return false;
                    }
                }

                // Kiểm tra trả trễ
                boolean traTre = contains(listLD_Tung_Ma, "trả trễ");
                boolean treHan = checkTreHan(Integer.parseInt(maPP), Integer.parseInt(listMSach[i]));
                if ((traTre && !treHan) || (!traTre && treHan)) {
                    return false;
                }

                // Kiểm tra tiền
                float expectedTien = tinhTien(listMSach[i], listLDo[i]);
                if (Math.abs(Float.parseFloat(listTien[i]) - expectedTien) > 1e-6) {
                    return false;
                }
            }

            // Kiểm tra ngày
            for (String date : listNLap) {
                LocalDate.parse(date);
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            return false;
        }

        return true;
    }

    public String importExcel(String fileName) {
        String maDuocImport="";
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
                        getCellValue(row.getCell(6)), getCellValue(row.getCell(7)))) {
                    System.out.println("Dữ liệu không hợp lệ. Bỏ qua dòng.");
                    continue;
                }

                // Tạo đối tượng Phiếu Phạt DTO
                PhieuPhat_DTO pp = new PhieuPhat_DTO();
                pp.setMaPP(Integer.parseInt(getCellValue(row.getCell(0))));
                pp.setMaNV(Integer.parseInt(getCellValue(row.getCell(1))));
                pp.setMaPT(Integer.parseInt(getCellValue(row.getCell(2))));

                // Tách chuỗi và chuyển thành ArrayList
                ArrayList<String> listMS = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(3)).split("\n")));
                ArrayList<String> listMV = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(4)).split("\n")));
                ArrayList<String> listNL = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(5)).split("\n")));
                ArrayList<String> listLiDo = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(6)).split("\n")));
                ArrayList<String> listT = new ArrayList<>(Arrays.asList(getCellValue(row.getCell(7)).split("\n")));
                pp.setTongTien(0);
                pp_DAO.addPP(pp);
                maDuocImport+=pp.getMaPP()+",";
                double tt = 0;

                // Duyệt qua từng phần tử và thêm Chi Tiết Phiếu Phạt
                for (int i = 0; i < listMS.size(); i++) {
                    CTPP_DTO ct = new CTPP_DTO();
                    ct.setMaPP(Integer.parseInt(getCellValue(row.getCell(0))));
                    ct.setMaSach(Integer.parseInt(listMS.get(i)));
                    ct.setMaVach(listMV.get(i));
                    ct.setNgayLap(LocalDate.parse(listNL.get(i)));
                    ct.setLiDo(new ArrayList<>(Arrays.asList(listLiDo.get(i).split(","))));
                    ct.setTien(Float.parseFloat(listT.get(i)));
                    tt += ct.getTien();
                    ctpp_BUS.addCTPP(ct);
                }
                pp_DAO.updateTT(pp.getMaPP(), tt);
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

    public boolean exportExcel(String fileName) {
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
        String[] headers = {"Mã phiếu phạt", "Mã nhân viên", "Mã phiếu trả", "Danh sách mã sách", "Danh sách mã vạch lỗi",
            "Danh sách ngày lập", "Danh sách lí do", "Danh sách tiền", "Tồng tiền"};
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
            excelSheet.autoSizeColumn(i);
        }
        CellStyle cellStyle = excelWorkbook.createCellStyle();
        cellStyle.setWrapText(true);
        for (int i = 0; i < pp_DAO.getList().size(); i++) {
            PhieuPhat_DTO pp = pp_DAO.getList().get(i);
            row = excelSheet.createRow(4 + i);
            row.createCell(0).setCellValue(pp.getMaPP());
            row.createCell(1).setCellValue(pp.getMaNV());
            row.createCell(2).setCellValue(pp.getMaPT());

            String listMS = "";
            String listMV = "";
            String listNL = "";
            String listLiDo = "";
            String listT = "";

            for (CTPP_DTO ctpp : ctpp_BUS.searchByMaPP(pp.getMaPP())) {
                listMS += ctpp.getMaSach() + "\n";
                listMV += ctpp.getMaVach() + "\n";
                listNL += ctpp.getNgayLap() + "\n";
                String tam = String.join(", ", ctpp.getLiDo());
                listLiDo += tam +"\n";
                listT += ctpp.getTien() + "\n";
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
