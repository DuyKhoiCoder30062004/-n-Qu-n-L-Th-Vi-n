/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.PhanQuyen_BUS;
import DTO.PhanQuyen_DTO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "PhanQuyen_Servlet", urlPatterns = {"/phanquyen"})
public class PhanQuyen_Servlet extends HttpServlet {

    private PhanQuyen_BUS pq_BUS = new PhanQuyen_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhanQuyen_DTO> listPQ = pq_BUS.getList();
        request.setAttribute("listPQ", listPQ);
        request.getRequestDispatcher("/WEB-INF/gui/phanquyen.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maNV, String matKhau, String[] tasks)
            throws IOException {
        // Kiểm tra mã nhân viên
        if (maNV == null || maNV.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maNV);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên phải là số nguyên\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra mật khẩu
        if (matKhau == null || matKhau.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mật khẩu không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra danh sách công việc (tasks)
        if (tasks[0] == null || tasks[0].isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Danh sách công việc không được để trống\", \"hopLe\": false}");
            return false;
        }

        return true; // Không có lỗi
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String maNV = request.getParameter("maNV");
        String matKhau = request.getParameter("matKhau");
        String[] tasks = request.getParameterValues("tasks");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");

        switch (action) {
            case "add":
                if (!checkInfor(request, response, maNV, matKhau, tasks)) {
                    return;
                }
                // Tiếp tục xử lý sau khi kiểm tra thành công
                PhanQuyen_DTO pq = new PhanQuyen_DTO();
                pq.setMaNV(Integer.parseInt(maNV));
                pq.setMatKhau(matKhau);
                pq.setTacVu(new ArrayList<>(Arrays.asList(tasks)));
                if (pq_BUS.addPQ(pq)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (maNV == null || maNV.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 tài khoản để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (!checkInfor(request, response, maNV, matKhau, tasks)) {
                    return;
                }
                PhanQuyen_DTO pqs = new PhanQuyen_DTO();
                pqs.setMaNV(Integer.parseInt(maNV));
                pqs.setMatKhau(matKhau);
                pqs.setTacVu(new ArrayList<>(Arrays.asList(tasks)));
                if (pq_BUS.editPQ(pqs)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maNV == null || maNV.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 tài khoản để xóa trên table\", \"hopLe\": false}");
                    return;
                }
                if (pq_BUS.detelePQ(Integer.parseInt(maNV))) {
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
                if (optionSearch.equals("mã nv")) {
                    try {
                        Integer.parseInt(valueSearch);
                    } catch (NumberFormatException e) {
                        response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã NV hợp lệ-mã nhân viên là số nguyên-\", \"hopLe\": false}");
                        return;
                    }
                }
                StringBuilder result = pq_BUS.searchPQ(optionSearch, valueSearch);
                System.out.print("result" + result);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": " + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có tài khoản bạn cần tìm\", \"hopLe\": false}");
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
}
