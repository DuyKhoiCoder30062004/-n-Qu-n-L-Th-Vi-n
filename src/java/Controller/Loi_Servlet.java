/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoiServlet", urlPatterns = {"/loi"})
public class Loi_Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoiServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoiServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
private void forwardWithError(HttpServletResponse response, String errorMessage)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\": false, \"message\": \"" + errorMessage + "\"}");
            out.flush();
        }
    }

    private boolean checkImformation(HttpServletResponse response, String maNV, String matKhau, String[] tasks)
            throws IOException {
        if (maNV.trim().isEmpty()) {
            forwardWithError(response, "Mã nhân viên không được để trống!");
            return false;
        }

        try {
            Integer.parseInt(maNV);
        } catch (NumberFormatException e) {
            forwardWithError(response, "Mã nhân viên phải là số!");
            return false;
        }

        if (matKhau.trim().isEmpty()) {
            forwardWithError(response, "Mật khẩu không được để trống!");
            return false;
        }

        if (tasks == null || tasks.length == 0) {
            forwardWithError(response, "Bạn phải chọn ít nhất 1 tác vụ!");
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Đa vào post");
        String action = request.getParameter("action");
        String maNV = request.getParameter("txtMaNV");
        String matKhau = request.getParameter("txtMatKhau");
        String[] tasks = request.getParameterValues("task");
        System.out.println("Mã nhân viên: " + maNV);
        System.out.println("Mật khẩu: " + matKhau);
        System.out.println("Hành động: " + action);

        if (!checkImformation(response, maNV, matKhau, tasks)) {
            return;
        }
        String thongBao = "";
        String redirectUrl = ""; // Biến chứa đường dẫn chuyển hướng
        boolean success = false;
        PhanQuyen_DTO pq = new PhanQuyen_DTO();
        pq.setMaNV(Integer.parseInt(maNV));
        pq.setMatKhau(matKhau);
        pq.setTacVu(new ArrayList<>(Arrays.asList(tasks)));
        switch (action) {
            case "add":
                System.out.println("đã click add");
                if (pq_BUS.addPQ(pq)) {
                    thongBao = "Thêm thành công!";
                    success = true;
                    redirectUrl = "/web_inf/gui/phanquyen.jsp"; // Đường dẫn chuyển hướng
                } else {
                    thongBao = "Thêm không thành công!";
                }
                break;
            case "edit":
                // Xử lý sửa dữ liệu
                break;
            case "delete":
                // Xử lý xóa dữ liệu
                break;
            default:
                response.getWriter().write("Không tìm thấy hành động phù hợp.");
                return;
        }

        /// Gửi phản hồi JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"success\": " + success + ", \"message\": \"" + thongBao + "\", \"redirectUrl\": \"" + redirectUrl + "\"}");
            out.flush();
        }
    }
}
