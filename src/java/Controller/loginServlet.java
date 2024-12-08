/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.Nhanvien_BUS;
import BUS.PhanQuyen_BUS;
import DTO.Nhanvien_DTO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class loginServlet extends HttpServlet {

    private PhanQuyen_BUS pq_BUS = new PhanQuyen_BUS();
    private Nhanvien_BUS nv_BUS = new Nhanvien_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        request.getRequestDispatcher("/WEB-INF/gui/login.jsp").forward(request, response);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response,
            String username, String pass)
            throws IOException {
        if (username.isEmpty() || username == null) {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập mã nhân viên để đăng nhập\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(username);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên phải là số mời bạn nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (Integer.parseInt(username)<0) {
            response.getWriter().write("{\"thongbao\": \"Không có nhân viên có mã nhân viên âm vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (pass.isEmpty() || pass == null) {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập mật khẩu để đăng nhập\", \"hopLe\": false}");
            return false;
        }
        if (pq_BUS.searchByMaNV(Integer.parseInt(username)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên bạn nhập không tồn tại vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        System.out.print("mat khau" + pq_BUS.searchByMaNV(Integer.parseInt(username)).getMatKhau());
        if (!pq_BUS.searchByMaNV(Integer.parseInt(username)).getMatKhau().equals(pass)) {
            response.getWriter().write("{\"thongbao\": \"Mật khẩu bạn nhập đã sai vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Trả về JSON bao gồm thông báo và URL để điều hướng
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String actor = request.getParameter("actor");
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        System.out.print("actor " + actor);
        //mặc định người thông tin tài khoản là độc giả
        Nhanvien_DTO nv = new Nhanvien_DTO();
        nv.setHo("");
        nv.setTen("");
        nv.setChucVu("Độc giả");
        String redirectUrl = "/cnpm/sach";
        String tasks="sách";
        if (actor.equals("nv")) {
            if (!check(request, response, username, pass)) {
                return;
            }
            nv = nv_BUS.timKiemNhanVien(username).get(0);
            if (nv_BUS.timKiemNhanVien(username).get(0).getChucVu().equals("admin")) {
                redirectUrl = "/cnpm/phanquyen";
            } 
//            else {
//                redirectUrl = "/cnpm/phieunhap";
//            }
            tasks = String.join(",", pq_BUS.searchByMaNV(Integer.parseInt(username)).getTacVu());
        }
        System.out.println("task bên login :"+tasks);
        HttpSession session = request.getSession();
        session.setAttribute("nv", nv);
        session.setAttribute("tasks", tasks);
        response.getWriter().write("{\"thongbao\": \"Đăng nhập thành công\", \"hopLe\": true, \"redirectUrl\": \"" + redirectUrl + "\"}");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
