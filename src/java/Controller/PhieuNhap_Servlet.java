package Controller;

import BUS.CTPN_BUS;
import BUS.CTSach_BUS;
<<<<<<< HEAD
import BUS.NCC_BUS;
=======
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d
import BUS.PhieuNhap_BUS;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

<<<<<<< HEAD
@WebServlet(name = "PhieuNhap_Servlet", urlPatterns = {"/phieunhap"})
public class PhieuNhap_Servlet extends HttpServlet {
=======

  @WebServlet(name = "PhieuNhap_Servlet", urlPatterns = {"/phieunhap"})
  public class PhieuNhap_Servlet extends HttpServlet{
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d

    private PhieuNhap_BUS pn_BUS = new PhieuNhap_BUS();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();
    private CTSach_BUS ctSach_BUS = new CTSach_BUS();
    private NCC_BUS ncc_BUS = new NCC_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset = UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuNhap_DTO> listPN = pn_BUS.getList();
        ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.getList();
        request.setAttribute("listCTPN", listCTPN);
        request.setAttribute("listPN", listPN);
        request.getRequestDispatcher("/WEB-INF/gui/phieunhap.jsp").forward(request, response);

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response, String maPhieu, String ngayNhap,
            String maNCC)
            throws IOException {
        // Kiểm tra mã phiếu nhập
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPhieu);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (maNCC == null || maNCC.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà cung cấp không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maNCC);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà cung cấp phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (ncc_BUS.searchByMaNCC(Integer.parseInt(maNCC)) == null) {
            response.getWriter().write("{\"thongbao\": \"Nhà cung cấp không tồn tại vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        else if (ncc_BUS.searchByMaNCC(Integer.parseInt(maNCC)).getTrangThai().equals("Ngừng hoạt động")) {
            response.getWriter().write("{\"thongbao\": \"Thư viện chúng ta đã ngừng hợp tác với nhà cung cấp này Vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        // Kiểm tra ngày nhập
        if (ngayNhap == null || ngayNhap.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày nhập không được để trống\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String maPN = request.getParameter("maPN");
        String maNCC = request.getParameter("maNCC");
        String maNV = request.getParameter("maNV");
        String ngayLap = request.getParameter("ngayLap");
        String tongSL = request.getParameter("tongSL");
        String tongTien = request.getParameter("tongTien");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        String namePath = request.getParameter("nameFileExcel");

        switch (action) {
            case "add":
                if (!checkInfor(request, response, maPN, ngayLap, maNCC)) {
                    return;
                }
                if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập đã tồn tại vui lòng nhập lại mã phiếu nhập\", \"hopLe\": false}");
                    return;
                }
                if (addPN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (!checkInfor(request, response, maPN, ngayLap, maNCC)) {
                    return;
                }
                if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không tồn tại mã phiếu nhập bạn cần sửa \", \"hopLe\": false}");
                    return;
                }
                if (updatePN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maPN == null || maPN.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập không được để trống\", \"hopLe\": false}");
                    return;
                }
                try {
                    Integer.parseInt(maPN);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập phải là số nguyên\", \"hopLe\": false}");
                    return;
                }
                if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không có mã phiếu nhập bạn cần xóa\", \"hopLe\": false}");
                    return;
                }
                if (deletePN(Integer.parseInt(maPN))) {
  @Override
  protected void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException{
      response.setCharacterEncoding("UTF-8");
      
      String action = request.getParameter("action");
      String maPN = request.getParameter("maPN");
      String maNCC = request.getParameter("maNCC");
      String maNV = request.getParameter("maNV");
      String ngayLap = request.getParameter("ngayLap");
      String tongSL = request.getParameter("tongSL");
      String tongTien = request.getParameter("tongTien");
      String optionSearch = request.getParameter("optionSearch");
      String valueSearch = request.getParameter("valueSearch");
      String namePath = request.getParameter("nameFileExcel");
      
      
      switch(action){
          case "add" :
              if(!checkInfor(request, response, maPN, ngayLap)){
                  return;
      }
              if(pn_BUS.searchByMaPN(Integer.parseInt(maPN)) != null){
                  response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập đã tồn tại vui lòng nhập lại mã phiếu nhập\", \"hopLe\": false}");
                    return;
              }
              if(addPN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)){
                  response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
              }else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
              }
              break;
          case "edit":
              if(!checkInfor(request, response, maPN, ngayLap)){
                  return;
      }
              if(pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null){
                  response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập không để trống vui lòng nhập lại mã phiếu nhập\", \"hopLe\": false}");
                   return;
              }
              if(updatePN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)){
              response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
             break;
          case "delete":
              if(!checkInfor(request, response, maPN, ngayLap)){
                  return;
      }
              if (pn_BUS.deletePN(Integer.parseInt(maPN))) {
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
                StringBuilder[] result = pn_BUS.searchPN(optionSearch, valueSearch);
              break;
          case "search":
              if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
              StringBuilder[] result = pn_BUS.searchPN(optionSearch, valueSearch);
                if (result[0].length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{"
                            + "\"thongbao\": \"\", "
                            + "\"hopLe\": false, "
                            + "\"resultsPN\": " + result[0].toString() + ", "
                            + "\"resultsCTPN\": " + result[1].toString()
                            + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu nhập bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "print":
                try {
                    Integer.parseInt(maPN);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên vui lòng kiểm tra lại để in\", \"hopLe\": false}");
                    return;
                }
                if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không tìm thấy phiếu bạn cần in vui lòng chọn lại phiếu hoặc nhập lại mã phiếu trên thanh input mã phiếu\", \"hopLe\": false}");
                    return;
                }
                try {
                    String htmlContent = pn_BUS.printPN(Integer.parseInt(maPN));
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

                if (pn_BUS.importExcel(namePath)) {
                    response.getWriter().write("{\"thongbao\": \"Import excel thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Import thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
                }
                break;
            case "export":
                if (namePath.isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để export\", \"hopLe\": false}");
                    return;
                }
                if (pn_BUS.exportExcel(namePath)) {
                    response.getWriter().write("{\"thongbao\": \"Export excel thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Export thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
                }
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
    }

    private boolean addPN(String maPN, String maNCC, String maNV, String ngayLap, String tongSL, String tongTien) {
        PhieuNhap_DTO pn = new PhieuNhap_DTO();
        pn.setMaPN(Integer.parseInt(maPN));
        pn.setMaNCC(Integer.parseInt(maNCC));
        pn.setMaNV(Integer.parseInt(maNV));
        pn.setNgayLap(LocalDate.parse(ngayLap));
        pn.setTongSL(0);
        pn.setTongTien(0);
        return pn_BUS.addPN(pn);
    }

    private boolean updatePN(String maPN, String maNCC, String maNV, String ngayLap, String tongSL, String tongTien) {
        PhieuNhap_DTO pn = new PhieuNhap_DTO();
        pn.setMaPN(Integer.parseInt(maPN));
        pn.setMaNCC(Integer.parseInt(maNCC));
        pn.setMaNV(Integer.parseInt(maNV));
        pn.setNgayLap(LocalDate.parse(ngayLap));
        pn.setTongSL(0);
        pn.setTongTien(0);
        return pn_BUS.updatePN(pn);
    }

    private boolean deletePN(int maPN) {
        if (!ctpn_BUS.searchByMaPN(maPN).isEmpty()) {
            for (CTPN_DTO i : ctpn_BUS.searchByMaPN(maPN)) {
                for (String j : i.getMaVach()) {
                    if (!cts_BUS.deleteCTSach_as_maVach_and_maSach(j,i.getMaSach())) {
                        return false;
                    }
                }
            }
        }
        if (!ctpn_BUS.deleteByMaPN(maPN)) {
            return false;
        }
        return pn_BUS.deletePN(maPN);
    }

    private String printPDF(int maPN) {
        String selectedPath = "";

  private String printPDF(int maPN){
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
<<<<<<< HEAD
                    String htmlContent = pn_BUS.printPN(maPN);
=======
                    String htmlContent = pn_BUS.printPN(maPN); 
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d
                    System.out.println("PDF đã được tạo thành công tại: " + selectedPath);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return selectedPath;
<<<<<<< HEAD
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
=======
  }
  
  @Override
    public String getServletInfo() {
        return "Short description";
    }
  }
>>>>>>> 8e48d04dffebc201fcf502cc00087805f5dbdb8d
