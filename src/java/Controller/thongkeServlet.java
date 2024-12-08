/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import DAO.CTPM_DAO;
import DAO.CTPP_DAO;
import DAO.PhieuPhat_DAO;
import DAO.Sach_DAO;
import DAO.ThongkeDAO;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
import DTO.Sach_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name="thongkeServlet", urlPatterns={"/thongke"})
public class thongkeServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet thongkeServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet thongkeServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        Sach_DAO Dao = new Sach_DAO();
        CTPM_DAO pmDao = new CTPM_DAO();
        PhieuPhat_DAO pphatDao = new PhieuPhat_DAO();
        CTPP_DAO ppDao = new CTPP_DAO();
        ThongkeDAO tk_DAO=new ThongkeDAO();
//        List<Sach> l = dao.getAllSach_Information();
        int totalSoLuong = tk_DAO.getTotalSoLuong();
        request.setAttribute("soLuongTotal",totalSoLuong);
        int total_SachMuon = tk_DAO.getTotal_SachMuon();
        request.setAttribute("sachMuonTotal",total_SachMuon);
        int total_Fines = tk_DAO.getTotal_tienPhat();
        request.setAttribute("tongTienPhat",total_Fines);
         List<PhieuMuon_DTO> l = tk_DAO.getSoLuongAndYear();
         request.setAttribute("soLuongAndYear",l);
        List<CTPP_DTO> ls = tk_DAO.getTien_AndYear();
        request.setAttribute("soTien_NamPhat",ls);
//        request.getRequestDispatcher("list.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/gui/thongke.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}