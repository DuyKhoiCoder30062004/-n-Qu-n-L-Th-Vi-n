/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import BUS.DocGia_BUS;
import DTO.DocGia_DTO;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
@WebServlet(name = "docgia_Servlet", urlPatterns = {"/docgia"})
public class docgia_Servlet extends HttpServlet {

    private DocGia_BUS dg_BUS = new DocGia_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        //response.sendRedirect("dg.html");
        ArrayList<DocGia_DTO> listDG = dg_BUS.getListDG();
        request.setAttribute("listDG", listDG);
        request.getRequestDispatcher("/WEB-INF/gui/dg.jsp").forward(request, response);
        System.out.println("Danh sách DocGia: " + listDG.get(0).getMaDG());

    }

    private boolean checkImformation(HttpServletRequest request, HttpServletResponse response, String maDG, String hoDG, String tenDG, String dc, String sdt, String ngaySinh)
            throws IOException {
        // Kiểm tra mã dg
        if (maDG == null || maDG.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã đọc giả không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.valueOf(maDG);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã đọc giả phải là số nguyên\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra họ dg
        if (hoDG == null || hoDG.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Họ của đọc giả không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra tên dg
        if (tenDG == null || tenDG.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Tên đọc giả không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra địa chỉ dg
        if (dc == null || dc.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Địa chỉ đọc giả không được để trống\", \"hopLe\": false}");
            return false;
        }

        // kiểm tra sđt
        if (sdt == null || sdt.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Sđt đọc giả không được để trống\", \"hopLe\": false}");
            return false;
        }
        
        // Kiểm tra số điện thoại có phải là số nguyên và đủ 10 ký tự
        if (!sdt.matches("\\d{10}")) {
            response.getWriter().write("{\"thongbao\": \"Số điện thoại phải là số nguyên gồm đúng 10 ký tự\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra ngày sinh
        if (ngaySinh == null || ngaySinh.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày sinh của đọc giả không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            LocalDate birthDate = LocalDate.parse(ngaySinh); // Chuyển đổi chuỗi ngày sang đối tượng LocalDate
            LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại

            if (!birthDate.isBefore(currentDate)) {
                response.getWriter().write("{\"thongbao\": \"Ngày sinh phải bé hơn ngày hiện tại\", \"hopLe\": false}");
                return false;
            }
        } catch (DateTimeParseException e) {
            response.getWriter().write("{\"thongbao\": \"Ngày sinh không hợp lệ. Định dạng đúng là YYYY-MM-DD\", \"hopLe\": false}");
            return false;
        }

        return true; // Không có lỗi
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String maDG = request.getParameter("maDG");
        String hoDG = request.getParameter("hoDG");
        String tenDG = request.getParameter("tenDG");
        String dc = request.getParameter("dc");
        String sdt = request.getParameter("sdt");
        String ngaySinh = request.getParameter("ngaySinh");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        System.out.println("maDG " + maDG);
        System.out.println("hoDG " + hoDG);
        System.out.println("tenDG " + tenDG);
        System.out.println("dc " + dc);
        System.out.println("sdt" + sdt);
        System.out.println("ngaySinh " + ngaySinh);
        System.out.println("optionSearch " + optionSearch);
        System.out.println("valueSearch " + valueSearch);
        System.out.println("action " + action);

        switch (action) {
            case "add" -> {
                if (!checkImformation(request, response, maDG, hoDG, tenDG, dc, sdt, ngaySinh)) {
                    return;
                }
                if (dg_BUS.searchByMaDG(Integer.parseInt(maDG)) != null || dg_BUS.searchByMaDG(-1*Integer.parseInt(maDG))!=null) {
                    response.getWriter().write("{\"thongbao\": \"Mã độc giả đã tồn tại vui lòng nhập lại mã độc giả\", \"hopLe\": false}");
                    return;
                }
                if (addDG(maDG, hoDG, tenDG, dc, sdt, ngaySinh)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
            }
            case "update" -> {
                if (maDG == null || maDG.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (!checkImformation(request, response, maDG, hoDG, tenDG, dc, sdt, ngaySinh)) {
                    return;
                }
                if (dg_BUS.searchByMaDG(Integer.parseInt(maDG)) == null|| dg_BUS.searchByMaDG(-1*Integer.parseInt(maDG))==null) {
                    response.getWriter().write("{\"thongbao\": \"Mã độc giả chưa tồn tại vui lòng nhập lại mã độc giả\", \"hopLe\": false}");
                    return;
                }
                if (updateDG(maDG, hoDG, tenDG, dc, sdt, ngaySinh)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
            }
            case "delete" -> {
                if (maDG == null || maDG.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để xóa trên table\", \"hopLe\": false}");
                    return;
                }
                if (dg_BUS.searchByMaDG(Integer.parseInt(maDG)) == null|| dg_BUS.searchByMaDG(-1*Integer.parseInt(maDG))==null) {
                    response.getWriter().write("{\"thongbao\": \"Mã độc giả chưa tồn tại vui lòng nhập lại mã độc giả\", \"hopLe\": false}");
                    return;
                }
                if (dg_BUS.deleteDocGia(Integer.parseInt(maDG))) {
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
                }
            }
            case "search" -> {
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                if (optionSearch.equals("Mã DocGia")) {
                    try {
                        Integer.parseInt(valueSearch);
                    } catch (NumberFormatException e) {
                        response.getWriter().write(
                                "{\"thongbao\": \"Vui lòng nhập mã DocGia hợp lệ-mã DocGia là số nguyên-\", \"hopLe\": false}");
                        return;
                    }
                }
                StringBuilder result = dg_BUS.searchDocGia(optionSearch, valueSearch);

                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": "
                            + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có tài khoản bạn cần tìm\", \"hopLe\": false}");
                }
            }
            case "finish" ->
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
            default -> {
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
            }
        }
    }

    private boolean addDG(String maDG, String hoDG, String tenDG, String dc, String sdt, String ngaySinh) {
        DocGia_DTO dg = new DocGia_DTO();
        dg.setMaDG(Integer.parseInt(maDG));
        dg.setHoDG(hoDG);
        dg.setTenDG(tenDG);
        dg.setDiaChi(dc);
        dg.setSoDienThoai(sdt);
        dg.setNgaySinh(LocalDate.parse(ngaySinh));
        return dg_BUS.addDocGia(dg);
    }

    private boolean updateDG(String maDG, String hoDG, String tenDG, String dc, String sdt, String ngaySinh) {
        DocGia_DTO dg = new DocGia_DTO();
        dg.setMaDG(Integer.parseInt(maDG));
        dg.setHoDG(hoDG);
        dg.setTenDG(tenDG);
        dg.setDiaChi(dc);
        dg.setSoDienThoai(sdt);
        dg.setNgaySinh(LocalDate.parse(ngaySinh));
        return dg_BUS.updateDocGia(dg);
    }
}