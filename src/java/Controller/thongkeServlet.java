///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//package controller;
//
//import DAO.CTPMDAO;
//import DAO.CTPPDAO;
//import DAO.PhieuPhatDAO;
//import DAO.SachDAO;
//import Model.CTPM;
//import Model.CTPP;
//import Model.PhieuPhat;
//import Model.Sach;
//import java.io.IOException;
//import java.io.PrintWriter;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// *
// * @author HELLO
// */
//@WebServlet(name="ListServlet", urlPatterns={"/ListServlet"})
//public class ListServlet extends HttpServlet {
//   
//    /** 
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet ListServlet</title>");  
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet ListServlet at " + request.getContextPath () + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    } 
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /** 
//     * Handles the HTTP <code>GET</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
////        
////        try (PrintWriter out = response.getWriter()) {
////            /* TODO output your page here. You may use following sample code. */
////            out.println("<!DOCTYPE html>");
////            out.println("<html>");
////            out.println("<head>");
////            out.println("<title>Servlet ListServlet</title>");  
////            out.println("</head>");
////            out.println("<body>");
////            out.println("<h1>Servlet ListServlet at " + request.getContextPath () + "</h1>");
////            out.println("</body>");
////            out.println("</html>");
////                    out.println("<h4>Database Table motherfacker!!! </h4>");
////        }
//
//        SachDAO dao = new SachDAO();
//        CTPMDAO pmDao = new CTPMDAO();
//        PhieuPhatDAO ppDao = new PhieuPhatDAO();
//        CTPPDAO ppdao = new CTPPDAO();
//        
////        List<Sach> l = dao.getAllSach_Information();
//        int totalSoLuong = dao.getTotalSoLuong();
//        request.setAttribute("soLuongTotal",totalSoLuong);
//        int total_SachMuon = pmDao.getTotal_SachMuon();
//        request.setAttribute("sachMuonTotal",total_SachMuon);
//        int total_Fines = ppDao.getTotal_tienPhat();
//        request.setAttribute("tongTienPhat",total_Fines);
//         List<CTPM> l = pmDao.getSoLuongAndYear();
//         request.setAttribute("soLuongAndYear",l);
//        List<CTPP> ls = ppdao.getTien_AndYear();
//        request.setAttribute("soTien_NamPhat",ls);
////        request.getRequestDispatcher("list.jsp").forward(request, response);
//        request.getRequestDispatcher("thongke.jsp").forward(request, response);
//    } 
//
//    /** 
//     * Handles the HTTP <code>POST</code> method.
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//    throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /** 
//     * Returns a short description of the servlet.
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
