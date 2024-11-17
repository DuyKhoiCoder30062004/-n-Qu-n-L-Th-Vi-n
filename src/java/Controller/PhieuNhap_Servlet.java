package Controller;

import BUS.CTPN_BUS;
import BUS.PhieuNhap_BUS;
import DTO.CTPN_DTO;
import DTO.PhieuNhap_DTO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


  @WebServlet(name = "PhieuMuon_Servlet", urlPatterns = {"/phieunhap"})
  public class PhieuNhap_Servlet extends HttpServlet{

    private PhieuNhap_BUS pn_BUS = new PhieuNhap_BUS();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
      response.setContentType("text/html; charset = UTF-8");
    }
  

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
  throws ServletException, IOException{
    ArrayList<PhieuNhap_DTO> listPN = pn_BUS.getList();
    ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.getList();
    request.setAttribute("listCTPN", listCTPN);
    request.setAttribute("listPN", listPN);
    request.getRequestDispatcher("/WEB-INF/gui/phieunhap.jsp").forward(request.response);
  }

  private boolean checkInfor(HttpServletRequest request, HttpServletResponse response, String maPhieu, String ngayNhap)
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
    // Kiểm tra ngày nhập
    if (ngayNhap == null || ngayNhap.trim().isEmpty()) {
      response.getWriter().write("{\"thongbao\": \"Ngày nhập không được để trống\", \"hopLe\": false}");
      return false;
    }
    return true;
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");

    String action = request.getParameter("action");
    String maPhieu = request.getParameter("maPhieu");
    String maNCC = request.getParameter("maNCC");
    String maNV = request.getParameter("maNV");
    String ngayNhap = request.getParameter("ngayNhap");
    String tongSL = request.getParameter("tongSL");
    String tongTien = request.getParameter("tongTien");

    
  }

  private boolean addPN(String maPhieu, String maNCC, String maNV, String ngayNhap, String tongSL, String tongTien) {
    PhieuNhap_DTO pn = new PhieuNhap_DTO();
    pn.setMaPN(Integer.parseInt(maPhieu));
    pn.setMaNCC(Integer.parseInt(maNCC));
    pn.setMaNV(Integer.parseInt(maNV));
    pn.setNgayLap(LocalDate.parse(ngayNhap));
    pn.setTongSL(Integer.parseInt(tongSL));
    pn.setTongTien(Float.parseFloat(tongTien));
    return pn_BUS.addPN(pn);
  }

  private boolean updatePN(String maPN, String maNCC, String maNV, String ngayLap, String tongSL, String tongTien){
    PhieuNhap_DTO pn = new PhieuNhap_DTO();
    pn.setMaPN(Integer.parseInt(maPN));
    pn.setMaNCC(Integer.parseInt(maNCC));
    pn.setMaNV(Integer.parseInt(maNV));
    pn.setNgayLap(LocalDate.parse(ngayLap));
    pn.setTongSL(Integer.parseInt(tongSL));
    pn.setTongTien(Float.parseFloat(tongTien));
    return pn_BUS.updatePN(pn);
  }

  private String printPDFPN(int maPN){
    String selectedPath = "";
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Document", "pdf");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection == JFileChooser.APPROVE_OPTION){
          selectedPath = fileChooser.getSelectedFile().getAbsolutePath();

          if(!selectedPath.toLowerCase().endsWith(".pdf")){
            selectedPath += ".pdf";
          }
          try(OutputStream os = new FileOutputStream(selectedPath)){
            String htmlContent = pn_BUS.printPN(maPN);
          }
        }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return selectedPath;
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
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
      String namePath=request.getParameter("nameFileExcel");
      tongTien = "0"; tongSL = "0";
      switch (action) {
        
          case "add":
              
              if(!checkInfor(request, response, maPN, ngayLap)){
                return;
              }
              if(pn_BUS.searchByMaPN(Integer.parseInt(maPN)) != null){
                response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn đã tồn tại vui lòng nhập lại mã phiếu mượn\", \"hopLe\": false}");
                return;
              }
              if(addPN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)){
                response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
              }else{
                response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
              }
              break;
          case "edit":
            if(!checkInfor(request, response, maPN, ngayLap)){
              return;
            }
            if(pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null){
              response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu mượn để sửa trên table\", \"hopLe\": false}");
              return;
            }
            if(updatePN(maPN, maNCC, maNV, ngayLap, tongSL, tongTien)){
              response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
            } else {
              response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
            }
              break;
          case "delete":
            if (maPN == null || maPN.trim().isEmpty()) {
              response.getWriter().write("{\"thongbao\": \"Vui lòng nhập phiếu mượn hoặc trên phiếu mượn trên table để xóa\", \"hopLe\": false}");
              return ;
            }
            if(pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null){
              response.getWriter().write("{\"thongbao\": \"Mã phiếu không tồn tại vui lòng chọn trên table để xóa\", \"hopLe\": false}");
              return;
            }
            if(pn_BUS.deletePN(Integer.parseInt(maPN))){
              ctpn_BUS.deleteByMaPN(Integer.parseInt(maPN));
              response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
            }else {
              response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
            }
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
          default:
              throw new AssertionError();
      }

  }
}
