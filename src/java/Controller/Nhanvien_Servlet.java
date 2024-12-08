package Controller;

import BUS.Nhanvien_BUS;
import DTO.Nhanvien_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@WebServlet(name = "Nhanvien_Servlet", urlPatterns = {"/nhanvien"})
public class Nhanvien_Servlet extends HttpServlet {

    private Nhanvien_BUS nhanvien_BUS = new Nhanvien_BUS();  // Khởi tạo đối tượng BUS

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Nhanvien_DTO> listNhanVien = nhanvien_BUS.getList();
        request.setAttribute("listNhanVien", listNhanVien);
        request.getRequestDispatcher("/WEB-INF/gui/nhanvien.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String manv, String ho, String ten, String sdt, String luong, String chucVu, String ngaysinh) throws IOException {
        
        // Kiểm tra mã nhân viên
        if (manv == null || manv.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã nhân viên\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }

        try {
            Integer.parseInt(manv);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã nhân viên phải là số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra họ tên
        if (ho == null || ho.trim().isEmpty() || ten == null || ten.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Họ và tên không được để trống\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra số điện thoại
        if (sdt == null || sdt.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập số điện thoại\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra lương
        try {
            Double.parseDouble(luong);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Lương phải là số\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra chức vụ
        if (chucVu == null || chucVu.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn chức vụ\", \"hopLe\": false}");
            return false;
        }

        // Kiểm tra ngày sinh
        if (ngaysinh == null || ngaysinh.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập ngày sinh\", \"hopLe\": false}");
            return false;
        }
        
        // Kiểm tra định dạng ngày sinh
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(ngaysinh);
        } catch (ParseException e) {
            response.getWriter().write("{\"thongbao\": \"Ngày sinh không đúng định dạng (yyyy-MM-dd)\", \"hopLe\": false}");
            return false;
        }
        
        return true; // Không có lỗi
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String manv = request.getParameter("manv");  // Sửa thành manv
        String ho = request.getParameter("ho");
        String ten = request.getParameter("ten");
        String sdt = request.getParameter("sdt");
        String luong = request.getParameter("luong");
        String chucVu = request.getParameter("chucVu");
        String ngaysinh = request.getParameter("ngaysinh");
        String valueSearch = request.getParameter("valueSearch");
        
        switch (action) {
            case "addNhanVien":
                if (!checkInfor(request, response, manv, ho, ten, sdt, luong, chucVu, ngaysinh)) {
                    return;
                }
                if (nhanvien_BUS.timKiemNhanVien(manv).size() > 0) {
                    response.getWriter().write("{\"thongbao\": \"Mã nhân viên này đã tồn tại\", \"hopLe\": false}");
                    return;
                }
                Nhanvien_DTO newNhanVien = createNhanVien(manv, ho, ten, sdt, luong, chucVu, ngaysinh);
                nhanvien_BUS.themNhanVien(newNhanVien);
                response.getWriter().write("{\"thongbao\": \"Thêm nhân viên thành công\", \"hopLe\": true}");
                break;

            case "updateNhanVien":
                if (!checkInfor(request, response, manv, ho, ten, sdt, luong, chucVu, ngaysinh)) {
                    return;
                }
                Nhanvien_DTO updatedNhanVien = createNhanVien(manv, ho, ten, sdt, luong, chucVu, ngaysinh);
                nhanvien_BUS.suaNhanVien(updatedNhanVien);
                response.getWriter().write("{\"thongbao\": \"Cập nhật nhân viên thành công\", \"hopLe\": true}");
                break;

            case "deleteNhanVien":
                if (manv == null || manv.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã nhân viên bạn muốn xóa\", \"hopLe\": false}");
                    return;
                }
                if (nhanvien_BUS.timKiemNhanVien(manv).size() == 0) {
                    response.getWriter().write("{\"thongbao\": \"Không tìm thấy nhân viên để xóa\", \"hopLe\": false}");
                    return;
                }
                nhanvien_BUS.xoaNhanVien(Integer.parseInt(manv));
                response.getWriter().write("{\"thongbao\": \"Xóa nhân viên thành công\", \"hopLe\": true}");
                break;

           case "searchNhanVien":
    if (valueSearch == null || valueSearch.trim().isEmpty()) {
        response.getWriter().write("<div class='alert alert-warning'>Vui lòng nhập thông tin bạn muốn tìm kiếm</div>");
        return;
    }
    ArrayList<Nhanvien_DTO> searchResults = nhanvien_BUS.timKiemNhanVien(valueSearch);
    if (!searchResults.isEmpty()) {
        StringBuilder htmlResult = new StringBuilder("<table class='table' id='nhanvienTable'><thead><tr><th>Mã NV</th><th>Họ</th><th>Tên</th><th>Số điện thoại</th><th>Lương</th><th>Ngày sinh</th><th>Chức vụ</th></tr></thead><tbody>");
        for (Nhanvien_DTO nv : searchResults) {
            htmlResult.append("<tr>")
                .append("<td>").append(nv.getMaNV()).append("</td>")
                .append("<td>").append(nv.getHo()).append("</td>")
                .append("<td>").append(nv.getTen()).append("</td>")
                .append("<td>").append(nv.getSoDT()).append("</td>")
                .append("<td>").append(nv.getLuong()).append("</td>")
                .append("<td>").append(new SimpleDateFormat("yyyy-MM-dd").format(nv.getNgaySinh())).append("</td>")
                .append("<td>").append(nv.getChucVu()).append("</td>")
                .append("</tr>");
        }
        htmlResult.append("</tbody></table>");
        response.getWriter().write(htmlResult.toString());
    } else {
        response.getWriter().write("<div class='alert alert-danger'>Không tìm thấy nhân viên bạn cần tìm</div>");
    }
    break;
        }
    }

private Nhanvien_DTO createNhanVien(String manv, String ho, String ten, String sdt, String luong, String chucVu, String ngaysinh) {
    // Khởi tạo đối tượng Nhanvien_DTO mới
    Nhanvien_DTO nhanVien = new Nhanvien_DTO(0, ho, ten, sdt, 0.0, chucVu, null);  // Sử dụng giá trị mặc định ban đầu cho các thuộc tính

    // Set giá trị cho các thuộc tính của đối tượng Nhanvien_DTO
    nhanVien.setMaNV(Integer.parseInt(manv));  // Thiết lập mã nhân viên
    nhanVien.setHo(ho);  // Thiết lập họ
    nhanVien.setTen(ten);  // Thiết lập tên
    nhanVien.setSoDT(sdt);  // Thiết lập số điện thoại
    nhanVien.setChucVu(chucVu);  // Thiết lập chức vụ

    // Chuyển đổi ngày sinh từ chuỗi sang Date
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
        Date date = sdf.parse(ngaysinh);  // Chuyển đổi chuỗi ngaysinh thành đối tượng Date
        nhanVien.setNgaySinh(date);  // Thiết lập ngày sinh
    } catch (ParseException e) {
        e.printStackTrace();
    }

    // Thiết lập lương
    nhanVien.setLuong(Double.parseDouble(luong));  // Thiết lập lương

    return nhanVien;  // Trả về đối tượng Nhanvien_DTO đã được thiết lập đầy đủ
}


    @Override
    public String getServletInfo() {
        return "Nhanvien Servlet quản lý nhân viên";
    }
}