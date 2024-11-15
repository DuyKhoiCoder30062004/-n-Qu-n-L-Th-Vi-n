package Controller;

import com.sun.net.httpserver.HttpServer;

import BUS.CTPN_BUS;
import BUS.PhieuNhap_BUS;
import DTO.PhieuNhap_DTO;
import DTO.CTPN_DTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhap_Servlet {

  @WebServlet(name = "PhieuMuon_Servlet", urlPatterns = {"/phieunhap"})
  public class PhieuMuon_Servlet extends HttpServlet{

    private PhieuNhap_BUS pn_BUS = new PhieuNhap_BUS();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
      response.setContentType("text/html; charset = UTF-8");
    }
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
    pn.setNgayNhap(LocalDate.parse(ngayNhap));
    pn.setTongSL(Integer.parseInt(tongSL));
    pn.setTongTien(Double.parseDouble(tongTien));
    return pn_BUS.addPN(pn);
  }




}
