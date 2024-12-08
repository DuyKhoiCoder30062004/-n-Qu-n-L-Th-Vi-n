/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPM_BUS;
import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.CTSach_BUS;
import BUS.DocGiaBUS;
import BUS.PhieuMuon_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import DTO.DocGia_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
import DTO.PhieuTra_DTO;
import DTO.Sach_DTO;
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
    private DocGiaBUS dg_BUS = new DocGiaBUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS();
    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private CTSach_BUS cts_BUS=new CTSach_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuMuon_DTO> listPM = pm_BUS.getList();
        ArrayList<CTPM_DTO> listCTPM = ctpm_BUS.getList();
        ArrayList<DocGia_DTO> listDG = dg_BUS.getListDG();
        ArrayList<Sach_DTO> listSach = sach_BUS.getListSach();
        request.setAttribute("listCTPM", listCTPM);
        request.setAttribute("listPM", listPM);
        request.setAttribute("listDG", listDG);
        request.setAttribute("listSach", listSach);
        request.getRequestDispatcher("/WEB-INF/gui/phieumuon.jsp").forward(request, response);
    }

    private PhieuTra_DTO searchPM_In_PT(int maPM) {
        for (PhieuTra_DTO i : pt_BUS.getListPhieuTra()) {
            if (i.getMaPM() == maPM) {
                return i;
            }
        }
        return null;
    }

    private PhieuPhat_DTO searchPT_In_PP(int maPT) {
        for (PhieuPhat_DTO i : pp_BUS.getList()) {
            if (i.getMaPT() == maPT) {
                return i;
            }
        }
        return null;
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPhieu, String madg, String ngayLap, String hanChot)
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
        if (madg == null || madg.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã độc giả\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
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
        if(LocalDate.parse(ngayLap).isAfter(LocalDate.parse(hanChot)))
        {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập lại ngày mượn phải trước ngày hạn chót\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean checkDelete(HttpServletRequest request, HttpServletResponse response,
            String maPhieu)
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

        if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu không tồn tại vui lòng chọn trên table để xóa\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean Delete_UpdateCacPhieu(String maPhieu) {
        CTPT_BUS ctpt_BUS = new CTPT_BUS();
        CTPP_BUS ctpp_BUS = new CTPP_BUS();
        if (searchPM_In_PT(Integer.parseInt(maPhieu)) != null) {
            PhieuPhat_DTO pp=searchPT_In_PP(searchPM_In_PT(Integer.parseInt(maPhieu)).getMaPT());
            if (pp != null) 
            {
                ArrayList<CTPP_DTO> listctpp=ctpp_BUS.searchByMaPP(pp.getMaPP());
                if(!listctpp.isEmpty())
                {
                    for(CTPP_DTO i:listctpp)
                    {
                        CTSach_DTO sachBiLoi=cts_BUS.searchCTSachByMaVach(i.getMaVach()).get(0);
                        String tts=sachBiLoi.getTinhTrangSach();
                        for(String j:i.getLiDo())
                            tts = tts.replace(j, "").replaceAll(",\\s*,", ",").trim();
                        tts = tts.substring(0, tts.length());
                        sachBiLoi.setTinhTrangSach(tts);
                        if(!cts_BUS.updateCTSach(sachBiLoi))
                            return false;
                    }
                    if(!ctpp_BUS.deleteByMaPP(pp.getMaPP())
                    ||!pp_BUS.deletePP(pp.getMaPP()))
                    return false;
                }
            }   
            List<CTPT_DTO> listctpt=ctpt_BUS.searchByMaPT(searchPM_In_PT(Integer.parseInt(maPhieu)).getMaPT());
            if(!listctpt.isEmpty())
            {
                if(!ctpt_BUS.deleteCTPT_ByMaPT(searchPM_In_PT(Integer.parseInt(maPhieu)).getMaPT()))
                    return false;
            }
            if(!pt_BUS.deletePhieuTra(searchPM_In_PT(Integer.parseInt(maPhieu)).getMaPT()))
                return false;
        }
        if(!ctpm_BUS.searchByMaPM(Integer.parseInt(maPhieu)).isEmpty())
        {
            for (CTPM_DTO i:ctpm_BUS.searchByMaPM(Integer.parseInt(maPhieu)))
        {
            Sach_DTO sach=sach_BUS.timSachTheoMaSach(String.valueOf(i.getMaSach())).get(0);
            sach.setSoLuong(sach.getSoLuong()+i.getSoLuong());
            if(!sach_BUS.suaSach(sach))
                return false;
        }
            if(!ctpm_BUS.deleteByMaPM(Integer.parseInt(maPhieu)))
                return false;
            }
        return true;
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
        String namePath = request.getParameter("nameFileExcel");
        System.out.println("path: " + namePath);
        switch (action) {
            case "add":
                if (!checkInfor(request, response, maPhieu, maKhach, ngayLap, hanChot)) {
                    return;
                }
                if (pm_BUS.searchByMaPM(Integer.parseInt(maPhieu)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn đã tồn tại vui lòng nhập lại mã phiếu mượn\", \"hopLe\": false}");
                    return;
                }
                if (addPM(maPhieu, maKhach, maNV, ngayLap, hanChot)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm phiếu mượn thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm phiếu mượn thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (!checkInfor(request, response, maPhieu, maKhach, ngayLap, hanChot)) {
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
                if (!checkDelete(request, response, maPhieu)) {
                    return;
                }
                if (Delete_UpdateCacPhieu(maPhieu)
                    && pm_BUS.deletePM(Integer.parseInt(maPhieu))) {
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
                            + "\"thongbao\": \"\", "
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
                if (namePath.isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để import\", \"hopLe\": false}");
                    return;
                }
                if(!pm_BUS.importExcel(namePath).equals(""))
                {
                    response.getWriter().write("{\"thongbao\": \"Import excel thành công với các mã phiếu mượn: " + pm_BUS.importExcel(namePath) + "\", \"hopLe\": true}");
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn nào import thành công\", \"hopLe\": false}");
                }
                if(pm_BUS.importExcel(namePath).equals("fasle"))
                {
                    response.getWriter().write("{\"thongbao\": \"Mở file import thất bại\", \"hopLe\": false}");
                }
                break;
            case "export":
                if (namePath.isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để export\", \"hopLe\": false}");
                    return;
                }
                if (pm_BUS.exportExcel(namePath)) {
                    response.getWriter().write("{\"thongbao\": \"Export excel thành công\", \"hopLe\": true}");
                } else {
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
