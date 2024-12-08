/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import BUS.NCC_BUS;
import DTO.NCC_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author HP
 */
@WebServlet(name="ncc_Servlet", urlPatterns={"/ncc"})
public class ncc_Servlet extends HttpServlet {
   
    private NCC_BUS ncc_BUS = new NCC_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //processRequest(request, response);
        //response.sendRedirect("ncc.html");
        ArrayList<NCC_DTO> listNCC = ncc_BUS.getListNCC();
        request.setAttribute("listNCC", listNCC);
        request.getRequestDispatcher("/WEB-INF/gui/ncc.jsp").forward(request, response);
        System.out.println("Danh sách NCC: " + listNCC.get(0).getMaNCC());

    } 

    private boolean checkImformation(HttpServletRequest request, HttpServletResponse response, String maNCC, String tenNCC, String dcNCC, String sdtNCC, String trangthai)
            throws IOException {
        // Kiểm tra mã ncc
        if (maNCC == null || maNCC.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà cung cấp không được để trống\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maNCC);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhà cung cấp phải là số nguyên\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra tên ncc
        if (tenNCC == null || tenNCC.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Tên nhà cung cấp không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra địa chỉ ncc
        if (dcNCC == null || dcNCC.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Địa chỉ nhà cung cấp không được để trống\", \"hopLe\": false}");
            return false;
        }

        /// kiểm tra sđt
        if (sdtNCC == null || sdtNCC.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Sđt ncc không được để trống\", \"hopLe\": false}");
            return false;
        }
        
        // Kiểm tra số điện thoại có phải là số nguyên và đủ 10 ký tự
        if (!sdtNCC.matches("\\d{10}")) {
            response.getWriter().write("{\"thongbao\": \"Số điện thoại phải là số nguyên gồm đúng 10 ký tự\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra trạng thái
        if (trangthai == null || trangthai.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Trạng thái nhà cung cấp không được để trống\", \"hopLe\": false}");
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
        String maNCC = request.getParameter("maNCC");
        String tenNCC = request.getParameter("tenNCC");
        String dcNCC = request.getParameter("dcNCC");
        String sdtNCC = request.getParameter("sdtNCC");
        String trangthai = request.getParameter("trangthai");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        System.out.println("maNCC " + maNCC);
        System.out.println("tenNCC " + tenNCC);
        System.out.println("dcNCC " + dcNCC);
        System.out.println("sdtNCC " + sdtNCC);
        System.out.println("trangthai " + trangthai);
        System.out.println("optionSearch " + optionSearch);
        System.out.println("valueSearch " + valueSearch);
        System.out.println("action " + action);

        switch (action) {
            case "add":
                if (!checkImformation(request, response, maNCC, tenNCC, dcNCC, sdtNCC, trangthai)) {
                    return;
                }
                if (ncc_BUS.searchByMaNCC(Integer.parseInt(maNCC)) != null|| ncc_BUS.searchByMaNCC(-1*Integer.parseInt(maNCC))!=null) {
                    response.getWriter().write("{\"thongbao\": \"Mã ncc đã tồn tại vui lòng nhập lại mã ncc\", \"hopLe\": false}");
                    return;
                }
                // Tiếp tục xử lý sau khi kiểm tra thành công
                NCC_DTO ncc = new NCC_DTO();
                ncc.setMaNCC(Integer.parseInt(maNCC));
                ncc.setTenNCC(tenNCC);
                ncc.setDcNCC(dcNCC);
                ncc.setSdtNCC(sdtNCC);
                ncc.setTrangThai(trangthai);
                if (ncc_BUS.addNCC(ncc)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "update":
                if (maNCC == null || maNCC.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (!checkImformation(request, response, maNCC, tenNCC, dcNCC, sdtNCC, trangthai)) {
                    return;
                }
                if (ncc_BUS.searchByMaNCC(Integer.parseInt(maNCC)) == null|| ncc_BUS.searchByMaNCC(-1*Integer.parseInt(maNCC))==null) {
                    response.getWriter().write("{\"thongbao\": \"Mã ncc đã tồn tại vui lòng nhập lại mã ncc\", \"hopLe\": false}");
                    return;
                }
                NCC_DTO nccs = new NCC_DTO();
                nccs.setMaNCC(Integer.parseInt(maNCC));
                nccs.setTenNCC(tenNCC);
                nccs.setDcNCC(dcNCC);
                nccs.setSdtNCC(sdtNCC);
                nccs.setTrangThai(trangthai);
                if (ncc_BUS.updateNCC(nccs)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maNCC == null || maNCC.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 hàng để xóa trên table\", \"hopLe\": false}");
                    return;
                }
                if (ncc_BUS.searchByMaNCC(Integer.parseInt(maNCC)) == null|| ncc_BUS.searchByMaNCC(-1*Integer.parseInt(maNCC))==null) {
                    response.getWriter().write("{\"thongbao\": \"Mã ncc đã tồn tại vui lòng nhập lại mã ncc\", \"hopLe\": false}");
                    return;
                }
                if (ncc_BUS.deleteNCC(Integer.parseInt(maNCC))) {
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
                if (optionSearch.equals("Mã NCC")) {
                    try {
                        Integer.parseInt(valueSearch);
                    } catch (NumberFormatException e) {
                        response.getWriter().write(
                                "{\"thongbao\": \"Vui lòng nhập mã NCC hợp lệ-mã NCC là số nguyên-\", \"hopLe\": false}");
                        return;
                    }
                }
                StringBuilder result = ncc_BUS.searchNCC(optionSearch, valueSearch);
                
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
}