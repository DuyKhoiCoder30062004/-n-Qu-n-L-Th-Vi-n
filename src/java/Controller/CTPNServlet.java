//package Controller;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import BUS.CTPN_BUS;
//import BUS.PhieuNhap_BUS;
//import DTO.CTPN_DTO;
//import DTO.PhieuNhap_DTO;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
//@WebServlet(name = "CTPNServlet", urlPatterns = {"/ctpn"})
//public class CTPNServlet extends HttpServlet{
//  private PhieuNhap_BUS pn_BUS = new PhieuNhap_BUS();
//  private CTPN_BUS ctpn_BUS = new CTPN_BUS();
//
//  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//  }
//
//  @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ArrayList<PhieuNhap_DTO> listPN = pn_BUS.getList();
//        ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.getList();
//        request.setAttribute("listCTPN", listCTPN);
//        request.setAttribute("listPN", listPN);
//        request.getRequestDispatcher("/WEB-INF/gui/phieunhap.jsp").forward(request, response);
//  }
//
//  public boolean checkInfor(HttpServletRequest request, HttpServletResponse response, String maPN, String maSach, String soLuong) throws IOException{
//    if(maPN.isEmty()|| maPN == null ){
//      response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống vui lòng nhập\", \"hopLe\": false}");
//      return false;
//    }
//    if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
//      response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại\", \"hopLe\": false}");
//      return false;
//    }
//    try {
//      Integer.parseInt(soLuong);
//  } catch (NumberFormatException e) {
//      response.getWriter().write("{\"thongbao\": \"Số lượng không phải số nguyên vui lòng nhập lại\", \"hopLe\": false}");
//      return false;
//  }
//  return true;
//  }
//
//  private boolean checkDelete(HttpServletRequest request, HttpServletResponse response,
//  String maPN, String maSach) throws IOException{
//    if (maPN.isEmpty() || maPN == null) {
//      response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống vui lòng nhập để xóa\", \"hopLe\": false}");
//      return false;
//    }
//    if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
//      response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại để xóa\", \"hopLe\": false}");
//      return false;
//  }
//  if (ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach)) == null) {
//      response.getWriter().write("{\"thongbao\": \"ctpm không tồn tại vui lòng chọn ctpm trên table để sửa\", \"hopLe\": false}");
//      return false;
//  }
//  return true;
//  }
//
//  private boolean addCTPN(String maPN, String maSach, String soLuong, String donGia){
//    CTPN_DTO ctpn = new CTPN_DTO();
//    ctpn.setMaPN(Integer.parseInt(maPN));
//    ctpn.setMaSach(Integer.parseInt(maSach));
//    ctpn.setSoLuong(Integer.parseInt(soLuong));
//    ctpn.setDonGia(Float.parseFloat(donGia));
//    return ctpn_BUS.addCTPN(ctpn);
//  }
//
//  private boolean updateCTPN(String maPN, String maSach, String soLuong, String donGia){
//    CTPN_DTO ctpn = new CTPN_DTO(Integer.parseInt(maPN),Integer.parseInt(maSach), Integer.parseInt(soLuong), Float.parseFloat(donGia));
//    return ctpn_BUS.updateCTPN(ctpn);
//  }
//
//  @Override
//  protected void doPost(HttpServletRequest request, HttpServletResponse response)
//  throws ServletException, IOException{
//    response.setCharacterEncoding("UTF-8");
//    response.setContentType("application/json");
//
//    String action = request.getParameter("action");
//    String maPN = request.getParameter("maPN");
//    String maSach = request.getParameter("maSach");
//    String soLuong = request.getParameter("soLuong");
//    String donGia = request.getParameter("donGia");
//    String optionSearch = request.getParameter("optionSearch");
//    String valueSearch = request.getParameter("valueSearch");
//
//    switch (action) {
//        case "addCTPN":
//            if(!checkInfor(request, response, maPN, maSach, soLuong)) return;
//
//            if(addCTPN(maPN, maSach, soLuong, donGia)){
//              response.getWriter().write("{\"thongbao\": \"Thêm CTPN thành công\", \"hopLe\": true}");
//            }else{
//              response.getWriter().write("{\"thongbao\": \"Thêm CTPN thất bại\", \"hopLe\": false}");
//            }
//        case "updateCTPN":
//            if(!checkInfor(request, response, maPN, maSach, soLuong)) return;
//
//            if(ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach)) == null){
//              response.getWriter().write("{\"thongbao\": \"ctpn không tồn tại \" \"hopLe\": false}");
//            }
//            
//            if(updateCTPN(maPN, maSach, soLuong, donGia)){
//              response.getWriter().write("{\"thongbao\": \"Update CTPN thành công\", \"hopLe\": true}");
//            }else {
//              response.getWriter().write("{\"thongbao\": \"Update CTPN thất bại\", \"hopLe\": false}");
//            }
//            break;
//        case "deleteCTPN":
//            if(!checkDelete(request, response, maPN, maSach)) return;
//            if(ctpn_BUS.deleteCTPN(Integer.parseInt(maPN), Integer.parseInt(maSach))){
//              response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
//            }else {
//              response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
//            }
//            break;
//        case "searchCTPN":
//          if (valueSearch == null || valueSearch.trim().isEmpty()) {
//              response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
//              return;
//          }
//          StringBuilder result = ctpn_BUS.searchCTPN(optionSearch, valueSearch);
//                if (result.length() > 2) {
//                    // Có dữ liệu
//                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": " + result.toString() + "}");
//                } else {
//                    // Không có dữ liệu
//                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
//                }  
//        break;
//        default:
//            throw new AssertionError();
//    }
//  }
//
//  @Override
//    public String getServletInfo() {
//        return "Short description";
//    }
//}
