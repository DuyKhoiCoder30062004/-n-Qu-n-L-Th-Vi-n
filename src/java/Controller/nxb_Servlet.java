/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import BUS.NXB_BUS;
import DTO.NXB_DTO;
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
@WebServlet(name="nxb_Servlet", urlPatterns={"/nxb"})
public class nxb_Servlet extends HttpServlet {
   
    private NXB_BUS nxb_BUS = new NXB_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        //response.sendRedirect("nxb.html");
        ArrayList<NXB_DTO> listNXB = nxb_BUS.getListNXB();
        request.setAttribute("listNXB", listNXB);
        request.getRequestDispatcher("/WEB-INF/gui/nxb.jsp").forward(request, response);
        System.out.println("Danh sách NXB: " + listNXB.get(1).getMaNXB());
    }  
    
    private boolean checkImformation(HttpServletRequest request, HttpServletResponse response, String maNXB, String tenNXB, String dcNXB)
            throws IOException {
        // Kiểm tra mã nxb
        if (maNXB == null || maNXB.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà xuất bản không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maNXB);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà xuất bản phải là số nguyên\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra tên nxb
        if (tenNXB == null || tenNXB.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Tên nhà xuất bản không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra địa chỉ nxb
        if (dcNXB == null || dcNXB.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Địa chỉ nhà xuất bản không được để trống\", \"hopLe\": false}");
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
        String maNXB = request.getParameter("maNXB");
        String tenNXB = request.getParameter("tenNXB");
        String dcNXB = request.getParameter("dcNXB");
        
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        System.out.println("maNXB " + maNXB);
        System.out.println("tenNXB " + tenNXB);
        System.out.println("dcNXB " + dcNXB);
        System.out.println("optionSearch " + optionSearch);
        System.out.println("valueSearch " + valueSearch);
        System.out.println("action " + action);

        switch (action) {
            case "add":
                if (!checkImformation(request, response, maNXB, tenNXB, dcNXB)) {
                    return;
                }
                // Tiếp tục xử lý sau khi kiểm tra thành công
                NXB_DTO nxb = new NXB_DTO();
                nxb.setMaNXB(Integer.parseInt(maNXB));
                nxb.setTenNXB(tenNXB);
                nxb.setDcNXB(dcNXB);
                if (nxb_BUS.addNXB(nxb)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "update":
                if (maNXB == null || maNXB.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (!checkImformation(request, response, maNXB, tenNXB, dcNXB)) {
                    return;
                }
                NXB_DTO nxbs = new NXB_DTO();
                nxbs.setMaNXB(Integer.parseInt(maNXB));
                nxbs.setTenNXB(tenNXB);
                nxbs.setDcNXB(dcNXB);
                if (nxb_BUS.updateNXB(nxbs)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maNXB == null || maNXB.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để xóa trên table\", \"hopLe\": false}");
                    return;
                }
                if (nxb_BUS.deleteNXB(Integer.parseInt(maNXB))) {
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
                if (optionSearch.equals("Mã NXB")) {
                    try {
                        Integer.parseInt(valueSearch);
                    } catch (NumberFormatException e) {
                        response.getWriter().write(
                                "{\"thongbao\": \"Vui lòng nhập mã NXB hợp lệ-mã NXB là số nguyên-\", \"hopLe\": false}");
                        return;
                    }
                }
                StringBuilder result = nxb_BUS.searchNXB(optionSearch, valueSearch);
                System.out.print("result" + result);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": "
                            + result.toString() + "}");
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

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
