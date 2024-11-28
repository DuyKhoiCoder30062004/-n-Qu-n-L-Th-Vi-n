package Controller;

import java.io.IOException;
import java.util.ArrayList;

import BUS.CTPT_BUS; // Changed to CTPT_BUS
import BUS.PhieuTra_BUS; // Changed to PhieuTra_BUS
import DTO.CTPT_DTO; // Changed to CTPT_DTO
import DTO.PhieuTra_DTO; // Changed to PhieuTra_DTO
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CTPTServlet", urlPatterns = {"/ctpt"}) // Changed the servlet name and URL pattern
public class CTPTServlet extends HttpServlet {
  private PhieuTra_BUS pt_BUS = new PhieuTra_BUS(); // Changed to PhieuTra_BUS
  private CTPT_BUS ctpt_BUS = new CTPT_BUS(); // Changed to CTPT_BUS

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuTra_DTO> listPT = pt_BUS.getList(); // Changed to PhieuTra_DTO
        ArrayList<CTPT_DTO> listCTPT = ctpt_BUS.getList(); // Changed to CTPT_DTO
        request.setAttribute("listCTPT", listCTPT); // Changed to listCTPT
        request.setAttribute("listPT", listPT); // Changed to listPT
        request.getRequestDispatcher("/WEB-INF/gui/phieutra.jsp").forward(request, response); // Changed JSP path to phieutra.jsp
  }

  public boolean checkInfor(HttpServletRequest request, HttpServletResponse response, String maPT, String maSach, String soLuong) throws IOException {
    if (maPT.isEmpty() || maPT == null) {
      response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống vui lòng nhập\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
      return false;
    }
    if (pt_BUS.searchByMaPT(Integer.parseInt(maPT)) == null) { // Changed to searchByMaPT
      response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại vui lòng chọn lại\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
      return false;
    }
    try {
      Integer.parseInt(soLuong);
    } catch (NumberFormatException e) {
      response.getWriter().write("{\"thongbao\": \"Số lượng không phải số nguyên vui lòng nhập lại\", \"hopLe\": false}");
      return false;
    }
    return true;
  }

  private boolean checkDelete(HttpServletRequest request, HttpServletResponse response, String maPT, String maSach) throws IOException {
    if (maPT.isEmpty() || maPT == null) {
      response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống vui lòng nhập để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
      return false;
    }
    if (pt_BUS.searchByMaPT(Integer.parseInt(maPT)) == null) { // Changed to searchByMaPT
      response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại vui lòng chọn lại để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
      return false;
    }
    if (ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach)) == null) { // Changed to searchByMaPT_MaSach
      response.getWriter().write("{\"thongbao\": \"ctpt không tồn tại vui lòng chọn ctpt trên table để sửa\", \"hopLe\": false}"); // Changed from ctpn to ctpt
      return false;
    }
    return true;
  }

  private boolean addCTPT(String maPT, String maSach, String soLuong) { // Removed donGia
    CTPT_DTO ctpt = new CTPT_DTO(); // Changed to CTPT_DTO
    ctpt.setMaPT(Integer.parseInt(maPT)); // Changed to MaPT
    ctpt.setMaSach(Integer.parseInt(maSach));
    ctpt.setSoLuong(Integer.parseInt(soLuong));
    return ctpt_BUS.addCTPT(ctpt); // Changed to addCTPT
  }

  private boolean updateCTPT(String maPT, String maSach, String soLuong) { // Removed donGia
    CTPT_DTO ctpt = new CTPT_DTO(Integer.parseInt(maPT), Integer.parseInt(maSach), Integer.parseInt(soLuong)); // Removed donGia
    return ctpt_BUS.updateCTPT(ctpt); // Changed to updateCTPT
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");

    String action = request.getParameter("action");
    String maPT = request.getParameter("maPT"); // Changed to maPT
    String maSach = request.getParameter("maSach");
    String soLuong = request.getParameter("soLuong");
    String optionSearch = request.getParameter("optionSearch");
    String valueSearch = request.getParameter("valueSearch");

    switch (action) {
        case "addCTPT": // Changed action name
            if (!checkInfor(request, response, maPT, maSach, soLuong)) return;

            if (addCTPT(maPT, maSach, soLuong)) { // Changed method call
              response.getWriter().write("{\"thongbao\": \"Thêm CTPT thành công\", \"hopLe\": true}"); // Changed message
            } else {
              response.getWriter().write("{\"thongbao\": \"Thêm CTPT thất bại\", \"hopLe\": false}"); // Changed message
            }
            break;

        case "updateCTPT": // Changed action name
            if (!checkInfor(request, response, maPT, maSach, soLuong)) return;

            if (ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach)) == null) { // Changed to searchByMaPT_MaSach
              response.getWriter().write("{\"thongbao\": \"ctpt không tồn tại\", \"hopLe\": false}"); // Changed message
            }
            
            if (updateCTPT(maPT, maSach, soLuong)) { // Changed method call
              response.getWriter().write("{\"thongbao\": \"Update CTPT thành công\", \"hopLe\": true}"); // Changed message
            } else {
              response.getWriter().write("{\"thongbao\": \"Update CTPT thất bại\", \"hopLe\": false}"); // Changed message
            }
            break;

        case "deleteCTPT": // Changed action name
            if (!checkDelete(request, response, maPT, maSach)) return;
            if (ctpt_BUS.deleteCTPT(Integer.parseInt(maPT), Integer.parseInt(maSach))) { // Changed to deleteCTPT
              response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}"); // Changed message
            } else {
              response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}"); // Changed message
            }
            break;

        case "searchCTPT": // Changed action name
          if (valueSearch == null || valueSearch.trim().isEmpty()) {
              response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
              return;
          }
          StringBuilder result = ctpt_BUS.searchCTPT(optionSearch, valueSearch); // Changed method call
                if (result.length() > 2) {
                    response.getWriter().write("{\"thongbao\": \"Tìm kiếm thành công\", \"hopLe\": false, \"results\": " + result.toString() + "}"); // Changed message
                } else {
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu trả bạn cần tìm\", \"hopLe\": false}"); // Changed message
                }  
        break;

        default:
            throw new AssertionError();
    }
  }

  @Override
    public String getServletInfo() {
        return "Short description";
    }
}
