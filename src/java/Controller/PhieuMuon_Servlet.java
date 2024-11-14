/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPM_BUS;
import BUS.DocGiaBUS;
import BUS.PhieuMuon_BUS;
import DTO.CTPM_DTO;
import DTO.DocGiaDTO;
import DTO.PhieuMuon_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "PhieuMuon_Servlet", urlPatterns = {"/phieumuon"})
public class PhieuMuon_Servlet extends HttpServlet {

    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private CTPM_BUS ctpm_BUS = new CTPM_BUS();
    private DocGiaBUS dg_BUS=new DocGiaBUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuMuon_DTO> listPM = pm_BUS.getList();
        ArrayList<CTPM_DTO> listCTPM = ctpm_BUS.getList();
        //List<DocGiaDTO> listDG =dg_BUS.getAllDocGia();
        request.setAttribute("listCTPM", listCTPM);
        request.setAttribute("listPM", listPM);
        //request.setAttribute("listDG",listDG);
        request.getRequestDispatcher("/WEB-INF/gui/phieumuon.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPhieu, String ngayLap, String hanChot)
            throws IOException {
        // Kiểm tra mã phiếu
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maPhieu);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        // Kiểm tra ngày lập
        if (ngayLap == null || ngayLap.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày lập không được để trống\", \"hopLe\": false}");
            return false;
        }
        // Kiểm tra hạn chót
        if (hanChot == null || hanChot.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Hạn chót không được để trống\", \"hopLe\": false}");
            return false;
        }
        return true; // Không có lỗi
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        // Lấy dữ liệu từ form
        String action = request.getParameter("action");
        String maPhieu = request.getParameter("maPhieu");
        String maKhach = request.getParameter("maKhach");
        String maNV = request.getParameter("maNV");
        String ngayLap = request.getParameter("ngayLap");
        String hanChot = request.getParameter("hanChot");
        String tongSL = request.getParameter("tongSL");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        String namePath=request.getParameter("nameFileExcel");
        System.out.println("path: "+namePath);
        switch (action) {
            case "add":
                if (!checkInfor(request, response, maPhieu, ngayLap, hanChot)) {
                    return;
                }
                if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn đã tồn tại vui lòng nhập lại mã phiếu mượn\", \"hopLe\": false}");
                    return;
                }
                if (addPM(maPhieu, maKhach, maNV, ngayLap, hanChot)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (!checkInfor(request, response, maPhieu, ngayLap, hanChot)) {
                    return;
                }
                if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu mượn để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (updatePM(maPhieu, maKhach, maNV, ngayLap, hanChot, tongSL)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maPhieu == null || maPhieu.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập phiếu mượn hoặc trên phiếu mượn trên table để xóa\", \"hopLe\": false}");
                    return ;
                }
                if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu không tồn tại vui lòng chọn trên table để xóa\", \"hopLe\": false}");
                    return;
                }
                if (pm_BUS.deletePM(Integer.parseInt(maPhieu))) {
                    ctpm_BUS.deleteByMaPM(Integer.parseInt(maPhieu));
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
                }
                break;
            case "search":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder[] result = pm_BUS.searchPM(optionSearch, valueSearch);
                if (result[0].length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{"
                        + "\"thongbao\": \"Tìm kiếm thành công\", "
                        + "\"hopLe\": false, "
                        + "\"resultsPM\": " + result[0].toString() + ", "
                        + "\"resultsCTPM\": " + result[1].toString()
                        + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "print":
                if (maPhieu == null || maPhieu.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu mượn trên table hoặc nhập mã phiếu trên thanh input mã phiếu\", \"hopLe\": false}");
                    return;
                }
                try {
                    Integer.parseInt(maPhieu);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên vui lòng kiểm tra lại để in\", \"hopLe\": false}");
                    return;
                }
                if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không tìm thấy phiếu bạn cần in vui lòng chọn lại phiếu hoặc nhập lại mã phiếu trên thanh input mã phiếu\", \"hopLe\": false}");
                    return;
                }
                try {
                    String htmlContent = pm_BUS.printPM(Integer.parseInt(maPhieu));
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(htmlContent);
                    out.close();
                } catch (Exception e) {
                    response.getWriter().write("{\"thongbao\": \"In thất bại vui lòng làm lại\", \"hopLe\": false}");
                    e.printStackTrace();
                }
                break;
            case "import":
//                System.out.print("đã vô import");
//                if(pathExcel.isEmpty())
//                {
//                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn file excel bạn muốn import vô hệ thống\", \"hopLe\": false}");
//                    return;
//                }
//             
//                if(pm_BUS.importExcel(pathExcel))
//                {
//                    response.getWriter().write("{\"thongbao\": \"Import excel thành công\", \"hopLe\": true}");
//                }
//                else
//                {
//                    response.getWriter().write("{\"thongbao\": \"Import thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
//                }
                break;
            case "export":
                if(namePath.isEmpty())
                {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để export\", \"hopLe\": false}");
                    return;
                }
                if(pm_BUS.exportExcel(namePath))
                {
                    response.getWriter().write("{\"thongbao\": \"Export excel thành công\", \"hopLe\": true}");
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Export thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
                }
                break;
            case "finish":
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }

    }

    private boolean addPM(String maPhieu, String maKhach, String maNV, String ngayLap, String hanChot) {
        PhieuMuon_DTO pm = new PhieuMuon_DTO();
        pm.setMaPM(Integer.parseInt(maPhieu));
        pm.setMaKhach(Integer.parseInt(maKhach));
        pm.setMaNV(Integer.parseInt(maNV));
        pm.setNgayLap(LocalDate.parse(ngayLap));
        System.out.print(pm.getNgayLap());
        pm.setHanChot(LocalDate.parse(hanChot));
        pm.setTongSL(0);
        return pm_BUS.addPM(pm);
    }

    private boolean updatePM(String maPhieu, String maKhach, String maNV, String ngayLap, String hanChot, String tongSL) {
        PhieuMuon_DTO pm = new PhieuMuon_DTO();
        pm.setMaPM(Integer.parseInt(maPhieu));
        pm.setMaKhach(Integer.parseInt(maKhach));
        pm.setMaNV(Integer.parseInt(maNV));
        pm.setNgayLap(LocalDate.parse(ngayLap));
        pm.setHanChot(LocalDate.parse(hanChot));
        pm.setTongSL(Integer.parseInt(tongSL));
        return pm_BUS.updatePM(pm);
    }

    private String printDPFPM(int mapm) {
        String selectedPath = "";
        try {
            //chọn nơi để lưu
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
            // Chỉ cho phép lưu với định dạng PDF
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn file từ người dùng
                selectedPath = fileChooser.getSelectedFile().getAbsolutePath();

                // Nếu người dùng không thêm đuôi .pdf, tự động thêm
                if (!selectedPath.toLowerCase().endsWith(".pdf")) {
                    selectedPath += ".pdf";
                }
                // Gọi hàm tạo PDF từ nội dung HTML
                try (OutputStream os = new FileOutputStream(selectedPath)) {
                    String htmlContent = pm_BUS.printPM(mapm); // Lấy nội dung HTML từ mã phiếu mượn
//                    PdfRendererBuilder builder = new PdfRendererBuilder();
//                    builder.withHtmlContent(htmlContent, null);
//                    builder.toStream(os);
//                    builder.run();  
                    System.out.println("PDF đã được tạo thành công tại: " + selectedPath);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedPath;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
