package BUS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.PhieuNhap_DAO;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;

public class PhieuNhap_BUS {
    private ArrayList<PhieuNhap_DTO> listPN;
    private PhieuNhap_DAO pn_DAO = new PhieuNhap_DAO();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();

    public ArrayList<PhieuNhap_DTO> getList(){
        listPN = pn_DAO.getList();
        return listPN;
    }

    public boolean addPN(PhieuNhap_DTO pn){
        return pn_DAO.addPN(pn);
    }

    public boolean updatePN(PhieuNhap_DTO pn){
        return pn_DAO.updatePN(pn);
    }

    public boolean deletePN(int maPN){
        return pn_DAO.deleteByMaPN(maPN);
    }

    public PhieuNhap_DTO searchByMaPN(int maPN){
        return pn_DAO.searchByMaPN(maPN);
    }

    public StringBuilder searchPN(String option, String value){
        StringBuilder jsonResult = new StringBuilder("["); // Sử dụng StringBuilder để dễ dàng quản lý chuỗi
        boolean firstItem = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        for(PhieuNhap_DTO pn : pn_DAO.getList()){

            if(option.equals("Mã phiếu") && String.valueOf(pn.getMaPN()).contains(value) 
                || option.equals("Mã nhà cung cấp") && String.valueOf(pn.getMaNCC()).contains(value)
                || option.equals("Mã nhân viên") && String.valueOf(pn.getMaNV()).contains(value) 
                || option.equals("Ngày lập") && pn.getNgayLap().format(formatter).contains(value)) {
                    if (!firstItem) {
                        jsonResult.append(","); // Thêm dấu phẩy trước mỗi phần tử sau phần tử đầu tiên
                    }
                    jsonResult.append("{"
                        + "\"maPhieu\": \"" + pn.getMaPN() + "\","
                        + "\"maKhach\": \"" + pn.getMaNCC() + "\","
                        + "\"maNV\": \"" + pn.getMaNV() + "\","
                        + "\"ngayLap\": \"" + pn.getNgayLap() + "\","
                        + "\"tongTien\": \"" +pn.getTongTien() + "\","
                        + "\"tongSL\": \"" + pn.getTongSL() + "\""
                        + "}");
                firstItem = false;
            }
        }
        jsonResult.append("]");
        return jsonResult;
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

        pn += "Ngày lập: " +phieu.getNgayLap() + "<br/>" +"Tổng Số Lượng: " + phieu.getTongSL() + "<br/>"
                + "Tổng Tiền: " + phieu.getTongTien() + "<br/>";
        return pn;
    }

    public boolean importExcel(String fileName) {
        String filePath = "C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/" + fileName;
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);
                PhieuNhap_DTO pn = new PhieuNhap_DTO();

                pn.setMaPN(Integer.parseInt(row.getCell(0).getStringCellValue()));
                pn.setMaNCC(Integer.parseInt(row.getCell(1).getStringCellValue()));
                pn.setMaNV(Integer.parseInt(row.getCell(2).getStringCellValue()));
                pn.setTongSL(Integer.parseInt(row.getCell(3).getStringCellValue()));
                pn.setTongTien(Float.parseFloat(row.getCell(4).getStringCellValue()));

                String[] listMaSach = row.getCell(5).getStringCellValue().split(",");
                String[] listSL = row.getCell(6).getStringCellValue().split(",");
                String[] listDG = row.getCell(7).getStringCellValue().split(",");
                for (int j=0; j < listMaSach.length; j++)
                {
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

        // Tạo tiêu đề cho Excel
        XSSFRow row = excelSheet.createRow(0);
        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("DANH SÁCH PHIẾU NHẬP");
        excelSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); // Gộp các ô từ cột 0 đến 6
        
        // Style canh giữa cho tiêu đề
        CellStyle titleStyle = excelWorkbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellStyle(titleStyle);

        // Tạo tiêu đề cột
        String[] headers = {"Mã PN", "Mã NCC", "Mã NV", "Ngày lập", "Tổng SL", "Tổng tiền", "Chi tiết phiếu nhập"};
        row = excelSheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(headers[i]);
            excelSheet.autoSizeColumn(i); // Tự động điều chỉnh kích thước cột
        }

        // Style cho các ô dữ liệu
        CellStyle wrapTextStyle = excelWorkbook.createCellStyle();
        wrapTextStyle.setWrapText(true); // Tự động xuống dòng trong ô

        // Duyệt qua danh sách phiếu nhập và ghi vào file Excel
        List<PhieuNhap_DTO> listPN = pn_DAO.getList(); // Lấy danh sách phiếu nhập từ cơ sở dữ liệu
        int rowIndex = 2; // Dòng bắt đầu ghi dữ liệu
        for (PhieuNhap_DTO pn : listPN) {
            row = excelSheet.createRow(rowIndex++);

            // Ghi thông tin phiếu nhập
            row.createCell(0).setCellValue(pn.getMaPN());
            row.createCell(1).setCellValue(pn.getMaNCC());
            row.createCell(2).setCellValue(pn.getMaNV());
            row.createCell(3).setCellValue(pn.getNgayLap().toString()); // Giả sử ngày lập là kiểu Date
            row.createCell(4).setCellValue(pn.getTongSL());
            row.createCell(5).setCellValue(pn.getTongTien());

            // Lấy danh sách chi tiết phiếu nhập theo mã phiếu nhập
            List<CTPN_DTO> listCTPN = ctpn_BUS.searchByMaPN(pn.getMaPN());
            StringBuilder ctpnDetails = new StringBuilder();

            // Duyệt qua các chi tiết phiếu nhập
            for (CTPN_DTO ctpn : listCTPN) {
                ctpnDetails.append("Mã sách: ").append(ctpn.getMaSach())
                           .append(", Số lượng: ").append(ctpn.getSoLuong())
                           .append(", Đơn giá: ").append(ctpn.getDonGia()).append("\n");
            }

            Cell ctpnCell = row.createCell(6);
            ctpnCell.setCellValue(ctpnDetails.toString());
            ctpnCell.setCellStyle(wrapTextStyle); // Áp dụng style tự động xuống dòng
        }

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            excelWorkbook.write(out);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
    return true;
    }

}
