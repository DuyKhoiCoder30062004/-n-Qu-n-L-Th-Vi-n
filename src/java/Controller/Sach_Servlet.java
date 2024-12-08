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
import DTO.CTSach_DTO;
import DTO.Sach_DTO;
import BUS.CTSach_BUS;
import DTO.CTSach_DTO;
import DTO.KhuVuc_DTO;
import BUS.Sach_BUS;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet(name="Sach_Servlet", urlPatterns={"/sach"})
public class Sach_Servlet extends HttpServlet {
    private Sach_BUS sach_BUS = new Sach_BUS();
    private Gson gson = new Gson();

   private CTSach_BUS ctsach_BUS = new CTSach_BUS();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         // Lấy danh sách sản phẩm
         ArrayList<Sach_DTO> listSach = new ArrayList<Sach_DTO>();
        listSach =  sach_BUS.getListSach_not_delete();
         // Xác định số trang (15 sản phẩm mỗi trang)
         int itemMax = 15;
         int tongTrang = (int) Math.ceil((double) listSach.size() / itemMax);
 
         // Tạo một ArrayList chứa các danh sách sản phẩm cho từng trang
         ArrayList<ArrayList<Sach_DTO>> paginatedProducts = new ArrayList<>();
         for (int i = 0; i < tongTrang; i++) {
             int start = i * itemMax;
             int end = Math.min(start + itemMax, listSach.size());
             paginatedProducts.add(new ArrayList<>(listSach.subList(start, end)));  // Tạo một ArrayList con cho mỗi trang
         }
        //  Lấy các sách mới
        ArrayList<Sach_DTO> listSach_New = new ArrayList<Sach_DTO>();
        listSach_New =  sach_BUS.listSach_New();
        // lấy khu vực
        ArrayList<KhuVuc_DTO> listKhuVuc = new ArrayList<KhuVuc_DTO>();
        listKhuVuc =  sach_BUS.listKhuVuc();
        request.setAttribute("listSach_New", listSach_New);
        request.setAttribute("listKhuVuc", listKhuVuc);
         // Đưa dữ liệu vào request để JSP có thể truy cập
         request.setAttribute("paginatedProducts", paginatedProducts);
         request.setAttribute("tongTrang", tongTrang);
        if (listSach != null && !listSach.isEmpty()) {
            request.getRequestDispatcher("WEB-INF/gui/sach.jsp").forward(request, response);

        } 
    } 
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy tham số từ request
        String action = request.getParameter("action");
        String maSach = request.getParameter("maSach");
        String bookId = request.getParameter("bookId");
        switch (action) {
            case "seeDetails":
            seeDetails(request, response,maSach);
            break;
            case "viewModalTableBook":
            viewModalTableBook(request, response,bookId);
            break;
            default:
                break;
        }

        // request.getRequestDispatcher("WEB-INF/gui/sach.jsp").forward(request, response);
    }
    private void seeDetails(HttpServletRequest request, HttpServletResponse response, String maSach) throws IOException {
        if(maSach != null && !maSach.equals("")) {
            int maSachInt = Integer.parseInt(maSach);
            Sach_DTO sach = sach_BUS.timSachTheoMaSach(maSachInt);
            if(sach != null) {
                // Đặt kiểu nội dung trả về là JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = gson.toJson(sach);
    
                // Gửi phản hồi JSON về client
                PrintWriter out = response.getWriter();
                out.println(jsonResponse);
            }
            else {
                // Nếu không tìm thấy sách
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"message\": \"Không tìm thấy sách\"}");
            }
        }
    }
    private void viewModalTableBook(HttpServletRequest request, HttpServletResponse response, String bookId) throws IOException {
        if(bookId != null && !bookId.equals("")) {
            int bookIdInt = Integer.parseInt(bookId);
            ArrayList<CTSach_DTO> listCTSach = ctsach_BUS.getCTSach_not_delete(bookIdInt);
            if(listCTSach != null && !listCTSach.isEmpty() ) {
                // Đặt kiểu nội dung trả về là JSON
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = gson.toJson(listCTSach);
    
                // Gửi phản hồi JSON về client
                PrintWriter out = response.getWriter();
                out.println(jsonResponse);
            }
            else {
                // Nếu không tìm thấy sách, trả về mảng rỗng
                String jsonResponse = gson.toJson(new ArrayList<CTSach_DTO>());
                PrintWriter out = response.getWriter();
                out.println(jsonResponse);
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
