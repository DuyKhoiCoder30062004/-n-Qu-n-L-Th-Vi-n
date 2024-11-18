/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPM_BUS;
import BUS.DocGiaBUS;
import BUS.PhieuMuon_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPM_DTO;
import DTO.DocGiaDTO;
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
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CTPMServlet", urlPatterns = {"/ctpm"})
public class CTPMServlet extends HttpServlet {

    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private CTPM_BUS ctpm_BUS = new CTPM_BUS();
    private DocGiaBUS dg_BUS=new DocGiaBUS();
    private Sach_BUS sach_BUS=new Sach_BUS();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuMuon_DTO> listPM = pm_BUS.getList();
        ArrayList<CTPM_DTO> listCTPM = ctpm_BUS.getList();
        ArrayList<DocGiaDTO> listDG = dg_BUS.getList();
        ArrayList<Sach_DTO> listSach = sach_BUS.getListSach();
        request.setAttribute("listCTPM", listCTPM);
        request.setAttribute("listPM", listPM);
        request.setAttribute("listDG", listDG);
        request.setAttribute("listSach", listSach);
        request.getRequestDispatcher("/WEB-INF/gui/phieumuon.jsp").forward(request, response);
    }
    
    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPM, String maSach, String soLuong) throws IOException {
        if (maPM.isEmpty() || maPM == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống vui lòng nhập\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPM);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (pm_BUS.searchByMaPM(Integer.parseInt(maPM)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng nhập lại mã phiếu mượn\", \"hopLe\": false}");
            return false;
        }
        if (maSach.isEmpty() || maSach == null) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã sách bạn muốn  mượn\", \"hopLe\": false}");
            return false;
        }
        if (pm_BUS.searchByMaPM(Integer.parseInt(maPM)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean checkDelete(HttpServletRequest request, HttpServletResponse response,
            String maPM, String maSach) throws IOException {
        if (maPM.isEmpty() || maPM == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống vui lòng nhập để xóa\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPM);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (pm_BUS.searchByMaPM(Integer.parseInt(maPM)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại để xóa\", \"hopLe\": false}");
            return false;
        }
        if (maSach.isEmpty() || maSach == null) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã sách bạn muốn  mượn\", \"hopLe\": false}");
            return false;
        }
        if (ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)) == null) {
            response.getWriter().write("{\"thongbao\": \"ctpm không tồn tại vui lòng chọn ctpm trên table để sửa\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String action = request.getParameter("action");
        String maPM = request.getParameter("maPM");
        String maSach = request.getParameter("maSach");
        String maNV=request.getParameter("maNV");
        String soLuong = request.getParameter("soLuong");
        String trangThai = request.getParameter("trangThai");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        switch (action) {
            case "addCTPM":
                if (!checkInfor(request, response, maPM, maSach, soLuong)) {
                    return; 
                }
                if (ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)) != null) {
                    response.getWriter().write("{\"thongbao\": \"ctpm đã tồn tại vui lòng kiểm tra lại dữ liệu hoặc bạn có thể sửa trên ctpm đó\", \"hopLe\": false}");
                    return;
                }
                if (addCTPM(maPM, maSach, soLuong, trangThai) &&
                        pm_BUS.updateTongSL(Integer.parseInt(maPM), pm_BUS.searchByMaPM(Integer.parseInt(maPM)).getTongSL() + Integer.parseInt(soLuong))) {
                    
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPM thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPM thất bại\", \"hopLe\": false}");
                }
                break;
            case "updateCTPM":
                if (!checkInfor(request, response, maPM, maSach, soLuong)) {
                    return;
                }
                if (ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)) == null) {
                    response.getWriter().write("{\"thongbao\": \"ctpm không tồn tại vui lòng chọn ctpm trên table để sửa\", \"hopLe\": false}");
                    return;
                }
                int slcu = ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)).getSoLuong();
                if (updateCTPM(maPM, maSach, soLuong, trangThai) &&
                       pm_BUS.updateTongSL(Integer.parseInt(maPM), pm_BUS.searchByMaPM(Integer.parseInt(maPM)).getTongSL() + Integer.parseInt(soLuong) - slcu) ) {
                    
                    response.getWriter().write("{\"thongbao\": \"Update CTPM thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Update CTPM thất bại\", \"hopLe\": false}");
                }
                break;
            case "deleteCTPM":
                if (!checkDelete(request, response, maPM, maSach)) {
                    return;
                }
                int sl = ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)).getSoLuong();
                if (ctpm_BUS.deleteCTPM(Integer.parseInt(maPM), Integer.parseInt(maSach)) &&
                       pm_BUS.updateTongSL(Integer.parseInt(maPM), pm_BUS.searchByMaPM(Integer.parseInt(maPM)).getTongSL() - sl) ) {
                    
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
                }
                break;
            case "searchCTPM":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder result = ctpm_BUS.searchCTPM(optionSearch, valueSearch);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": false, \"results\": " + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "finishCTPM":
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
        if( (action.equals("addCTPM") || action.equals("updateCTPM") || action.equals("deleteCTPM")) 
                &&pm_BUS.searchByMaPM(Integer.parseInt(maPM)).getMaNV()!= Integer.parseInt(maNV) )
        {
            PhieuMuon_DTO pm=pm_BUS.searchByMaPM(Integer.parseInt(maPM));
            pm.setMaNV(Integer.parseInt(maNV));
            pm_BUS.updatePM(pm);
        }
    }

    private boolean addCTPM(String maPM, String maSach, String soLuong, String trangThai) {
        CTPM_DTO ctpm = new CTPM_DTO();
        ctpm.setMaPM(Integer.parseInt(maPM));
        ctpm.setMaSach(Integer.parseInt(maSach));
        ctpm.setSoLuong(Integer.parseInt(soLuong));
        ctpm.setTrangthai(trangThai);
        return ctpm_BUS.addCTPM(ctpm);
    }

    private boolean updateCTPM(String maPM, String maSach, String soLuong, String trangThai) {
        CTPM_DTO ctpm = new CTPM_DTO();
        int slcu = ctpm_BUS.searchByMaPM_MaSach(Integer.parseInt(maPM), Integer.parseInt(maSach)).getSoLuong();
        ctpm.setMaPM(Integer.parseInt(maPM));
        ctpm.setMaSach(Integer.parseInt(maSach));
        ctpm.setSoLuong(Integer.parseInt(soLuong));
        ctpm.setTrangthai(trangThai);
        int slmoi = slcu - ctpm.getSoLuong();
        return ctpm_BUS.updateCTPM(ctpm);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
