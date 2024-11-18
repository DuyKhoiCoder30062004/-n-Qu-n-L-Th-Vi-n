/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.CTSach_BUS;
import BUS.Loi_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPP_DTO;
import DTO.CTSach_DTO;
import DTO.Loi_DTO;
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
@WebServlet(name = "LoiServlet", urlPatterns = {"/loi"})
public class Loi_Servlet extends HttpServlet {

    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private Loi_BUS loi_BUS = new Loi_BUS();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();
    private PhieuTra_BUS pt_BUS=new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS=new CTPT_BUS();
    private Sach_BUS sach_BUS=new Sach_BUS();
    private CTSach_BUS cts_BUS=new CTSach_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuPhat_DTO> listPP = pp_BUS.getList();
        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.getList();
        ArrayList<Loi_DTO> listLoi = loi_BUS.getList();
        ArrayList<PhieuTra_DTO> listPT=pt_BUS.getListPhieuTra();
        ArrayList<Sach_DTO> listSach=sach_BUS.getListSach();
        ArrayList<CTSach_DTO> listCTS=cts_BUS.getList();
        System.out.print("list CTS"+ listCTS);
        request.setAttribute("listLoi", listLoi);
        request.setAttribute("listCTPP", listCTPP);
        request.setAttribute("listPP", listPP);
        request.setAttribute("listPT", listPT);
        request.setAttribute("listSach", listSach);
        request.setAttribute("listCTS", listCTS);
        request.getRequestDispatcher("/WEB-INF/gui/phieuphat.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String tenLoi, String phanTramTien)
            throws IOException {
        // Kiểm tra mã phiếu
        if (tenLoi == null || tenLoi.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên lỗi\", \"hopLe\": false}");
            return false;
        }
        float ptt;
        try {
            ptt = Float.parseFloat(phanTramTien);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Phần trăm tiền không phải số thực\", \"hopLe\": false}");
            return false;
        }
        if (ptt > 1 || ptt < 0) {
            response.getWriter().write("{\"thongbao\": \"Phần trăm tiền trong khoảng từ 0 đến 1\", \"hopLe\": false}");
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
        String tenLoi = request.getParameter("tenLoi");
        String phanTramTien = request.getParameter("phanTramTien");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        switch (action) {
            case "addLoi":
                if (!checkInfor(request, response, tenLoi, phanTramTien)) {
                    return;
                }
                if (loi_BUS.searchByTenLoi(tenLoi) != null) {
                    response.getWriter().write("{\"thongbao\": \"Lỗi này đã có trong hệ thống vui lòng kiểm tra lại tên lỗi\", \"hopLe\": false}");
                    return;
                }
                Loi_DTO loi = new Loi_DTO();
                loi.setTenLoi(tenLoi);
                loi.setPhamTramTien(Float.parseFloat(phanTramTien));
                if (loi_BUS.addLoi(loi)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm lỗi thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm lỗi thất bại\", \"hopLe\": false}");
                }
                break;
            case "updateLoi":
                if (!checkInfor(request, response, tenLoi, phanTramTien)) {
                    return;
                }
                Loi_DTO loiup = new Loi_DTO();
                loiup.setTenLoi(tenLoi);
                loiup.setPhamTramTien(Float.parseFloat(phanTramTien));
                System.out.println("phần trăm tiền"+ loiup.getPhamTramTien());
                if (loi_BUS.updateLoi(loiup)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa lỗi thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa lỗi thất bại\", \"hopLe\": false}");
                }
                break;
            case "deleteLoi":
                if (tenLoi == null || tenLoi.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên lỗi muỗn xóa\", \"hopLe\": false}");
                    return;
                }
                if (loi_BUS.searchByTenLoi(tenLoi) == null) {
                    response.getWriter().write("{\"thongbao\": \"Lỗi này chưa có trong hệ thống vui lòng kiểm tra lại tên lỗi muốn xóa\", \"hopLe\": false}");
                    return;
                }
                if(loi_BUS.deleteLoi(tenLoi))
                {
                    response.getWriter().write("{\"thongbao\": \"Xóa lỗi thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa lỗi thất bại\", \"hopLe\": false}");
                }
                break;
            case "searchLoi":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder result = loi_BUS.searchLoi(optionSearch, valueSearch);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": false, \"results\": " + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "finishLoi":
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
