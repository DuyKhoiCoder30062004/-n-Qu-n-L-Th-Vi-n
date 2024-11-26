package Controller;

import BUS.CTPT_BUS;
import BUS.PhieuTra_BUS;
import DTO.CTPT_DTO;
import DTO.PhieuTra_DTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PhieuTra_Servlet", urlPatterns = {"/phieutra"})
public class PhieuTra_Servlet extends HttpServlet {

    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS = new CTPT_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuTra_DTO> listPT = pt_BUS.getList();
        ArrayList<CTPT_DTO> listCTPT = ctpt_BUS.getList();
        request.setAttribute("listCTPT", listCTPT);
        request.setAttribute("listPT", listPT);
        request.getRequestDispatcher("/WEB-INF/gui/phieutra.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response, String maPhieu, String ngayTra)
            throws IOException {
        // Validate Ma Phieu Tra
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPhieu);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        // Validate Ngay Tra
        if (ngayTra == null || ngayTra.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày trả không được để trống\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean addPT(String maPhieu, String maPM, String maNV, String ngayTra, String tongSL) {
        PhieuTra_DTO pt = new PhieuTra_DTO();
        pt.setMaPT(Integer.parseInt(maPhieu));
        pt.setMaPM(Integer.parseInt(maPM));
        pt.setMaNV(Integer.parseInt(maNV));
        pt.setNgayTra(LocalDate.parse(ngayTra));
        pt.setTongSL(Integer.parseInt(tongSL));
        return pt_BUS.addPT(pt);
    }

    private boolean updatePT(String maPhieu, String maPM, String maNV, String ngayTra, String tongSL) {
        PhieuTra_DTO pt = new PhieuTra_DTO();
        pt.setMaPT(Integer.parseInt(maPhieu));
        pt.setMaPM(Integer.parseInt(maPM));
        pt.setMaNV(Integer.parseInt(maNV));
        pt.setNgayTra(LocalDate.parse(ngayTra));
        pt.setTongSL(Integer.parseInt(tongSL));
        return pt_BUS.updatePT(pt);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String maPhieu = request.getParameter("maPhieu");
        String maPM = request.getParameter("maPM");
        String maNV = request.getParameter("maNV");
        String ngayTra = request.getParameter("ngayTra");
        String tongSL = request.getParameter("tongSL");

        switch (action) {
            case "add":
                if (!checkInfor(request, response, maPhieu, ngayTra)) {
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu trả đã tồn tại, vui lòng nhập lại mã phiếu trả\", \"hopLe\": false}");
                    return;
                }
                if (addPT(maPhieu, maPM, maNV, ngayTra, tongSL)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (!checkInfor(request, response, maPhieu, ngayTra)) {
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu trả để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (updatePT(maPhieu, maPM, maNV, ngayTra, tongSL)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maPhieu == null || maPhieu.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã phiếu trả để xóa\", \"hopLe\": false}");
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại\", \"hopLe\": false}");
                    return;
                }
                if (pt_BUS.deletePT(Integer.parseInt(maPhieu))) {
                    ctpt_BUS.deleteByMaPT(Integer.parseInt(maPhieu));
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
                }
                break;
            case "search":
                String optionSearch = request.getParameter("optionSearch");
                String valueSearch = request.getParameter("valueSearch");
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder[] result = pt_BUS.searchPT(optionSearch, valueSearch);
                if (result[0].length() > 2) {
                    response.getWriter().write("{\"thongbao\": \"Tìm kiếm thành công\", \"hopLe\": true, \"resultsPT\": " + result[0].toString() + ", \"resultsCTPT\": " + result[1].toString() + "}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu trả bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            default:
                throw new AssertionError();
        }
    }
}
