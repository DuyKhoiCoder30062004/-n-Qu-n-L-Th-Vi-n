/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import BUS.KhuVuc_BUS;
import DTO.KhuVuc_DTO;
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
 * @author HP
 */
@WebServlet(name="kv_Servlet", urlPatterns={"/kv"})
public class kv_Servlet extends HttpServlet {
private KhuVuc_BUS kv_BUS = new KhuVuc_BUS();
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        //response.sendRedirect("nxb.html");
        ArrayList<KhuVuc_DTO> listKV = kv_BUS.getListKV();
        request.setAttribute("listKV", listKV);
        request.getRequestDispatcher("/WEB-INF/gui/khuvuc.jsp").forward(request, response);
        System.out.println("Danh sách NXB: " + listKV.get(1).getMaKV());
    }  
    
    private boolean checkImformation(HttpServletRequest request, HttpServletResponse response, String maKV, String tenKV)
            throws IOException {
        // Kiểm tra mã khuvuc
        if (maKV == null || maKV.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã khu vực không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maKV);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã khu vực phải là số nguyên\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra tên khuvuc
        if (tenKV == null || tenKV.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Tên khu vực không được để trống\", \"hopLe\": false}");
            return false;
        }

        return true; // Không có lỗi
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String maKV = request.getParameter("maKV");
        String tenKV = request.getParameter("tenKV");
        
        
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
//        System.out.println("maKV " + maKV);
//        System.out.println("tenKV " + tenKV);
//        
//        System.out.println("optionSearch " + optionSearch);
//        System.out.println("valueSearch " + valueSearch);
        System.out.println("action " + action);

        switch (action) {
            case "add" -> {
                if (!checkImformation(request, response, maKV, tenKV)) {
                    return;
                }
                if (kv_BUS.searchByMaKV(Integer.parseInt(maKV)) != null|| kv_BUS.searchByMaKV(-1*Integer.parseInt(maKV))!=null) {
                    response.getWriter().write("{\"thongbao\": \"Mã khuvuc đã tồn tại vui lòng nhập lại mã khuvuc\", \"hopLe\": false}");
                    return;
                }
                // Tiếp tục xử lý sau khi kiểm tra thành công
                KhuVuc_DTO kv = new KhuVuc_DTO();
                kv.setMaKV(Integer.parseInt(maKV));
                kv.setTenKV(tenKV);
                if (kv_BUS.addKV(kv)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
        }
            case "update" -> {
                if (maKV == null || maKV.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (!checkImformation(request, response, maKV, tenKV)) {
                    return;
                }
                if (kv_BUS.searchByMaKV(Integer.parseInt(maKV)) == null || kv_BUS.searchByMaKV(-1*Integer.parseInt(maKV))==null) {
                    response.getWriter().write("{\"thongbao\": \"Mã khu vực chưa tồn tại vui lòng nhập lại mã khu vực\", \"hopLe\": false}");
                    return;
                }
                KhuVuc_DTO kvs = new KhuVuc_DTO();
                kvs.setMaKV(Integer.parseInt(maKV));
                kvs.setTenKV(tenKV);
                System.out.print(kvs.getMaKV());
                if (kv_BUS.updateKV(kvs)) {
                    
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
        }
            case "delete" -> {
                if (maKV == null || maKV.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để xóa trên table\", \"hopLe\": false}");
                    return;
                }
                if (kv_BUS.searchByMaKV(Integer.parseInt(maKV)) == null || kv_BUS.searchByMaKV(-1*Integer.parseInt(maKV))==null ) {
                    response.getWriter().write("{\"thongbao\": \"Mã khu vực chưa tồn tại vui lòng nhập lại mã khu vực\", \"hopLe\": false}");
                    return;
                }
                if (kv_BUS.deleteKV(Integer.parseInt(maKV))) {
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
                if (optionSearch.equals("Mã NXB")) {
                    try {
                        Integer.valueOf(valueSearch);
                    } catch (NumberFormatException e) {
                        response.getWriter().write(
                                "{\"thongbao\": \"Vui lòng nhập mã NXB hợp lệ-mã NXB là số nguyên-\", \"hopLe\": false}");
                        return;
                    }
                }
                StringBuilder result = kv_BUS.searchKV(optionSearch, valueSearch);
                System.out.print("result" + result);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": "
                            + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có tài khoản bạn cần tìm\", \"hopLe\": false}");
                }
        }
            case "finish" -> response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
            default -> {
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
            }
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
