/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import BUS.PhanQuyen_BUS;
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
@WebServlet(name="loginServlet", urlPatterns={"/login"})
public class loginServlet extends HttpServlet {
    private PhanQuyen_BUS pq_BUS=new PhanQuyen_BUS();
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
        if(username.isEmpty()|| username==null)
        {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập mã nhân viên để đăng nhập\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(username);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên phải là số mời bạn nhập lại\", \"hopLe\": false}");
            return false;
        }
        if(pass.isEmpty()|| pass==null)
        {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập mật khẩu để đăng nhập\", \"hopLe\": false}");
            return false;
        }
        if(pq_BUS.searchByMaNV(Integer.parseInt(username))==null)
        {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên bạn nhập không tồn tại vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if(pq_BUS.searchByMaNV(Integer.parseInt(username)).getMatKhau()!=pass)
        {
            response.getWriter().write("{\"thongbao\": \"Mật khẩu bạn nhập đã sai vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        return true;
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username=request.getParameter("username");
        String pass= request.getParameter("pass");
        if(!check(request,response,username,pass))
        {
            return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
