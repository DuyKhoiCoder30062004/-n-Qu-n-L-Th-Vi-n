/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.CTSach_BUS;
import BUS.Loi_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import DTO.Loi_DTO;
import DTO.PhieuPhat_DTO;
import DTO.PhieuTra_DTO;
import DTO.Sach_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "CTPP_Servlet", urlPatterns = {"/ctpp"})
public class CTPP_Servlet extends HttpServlet {

    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private Loi_BUS loi_BUS = new Loi_BUS();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();
    private PhieuTra_BUS pt_BUS=new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS=new CTPT_BUS();
    private Sach_BUS sach_BUS=new Sach_BUS();
    private CTSach_BUS cts_BUS=new CTSach_BUS();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuPhat_DTO> listPP = pp_BUS.getList();
        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.getList();
        ArrayList<Loi_DTO> listLoi = loi_BUS.getList();
        ArrayList<PhieuTra_DTO> listPT=pt_BUS.getListPhieuTra();
        ArrayList<Sach_DTO> listSach=sach_BUS.getListSach();
        ArrayList<CTSach_DTO> listCTS=cts_BUS.getList();
        System.out.print("list CTS"+ listCTS);
        request.setAttribute("listLoi", listLoi);
        request.setAttribute("listCTPP", listCTPP);
        request.setAttribute("listPP", listPP);
        request.setAttribute("listPT", listPT);
        request.setAttribute("listSach", listSach);
        request.setAttribute("listCTS", listCTS);
        request.getRequestDispatcher("/WEB-INF/gui/phieuphat.jsp").forward(request, response);
    }
    
    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPP, String maSach, String maVach, String ngayLap, ArrayList<String> listLiDo)
            throws IOException {
        // Kiểm tra mã phiếu
        if (maPP == null || maPP.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã phiếu phạt\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maPP);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt phải là số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        // Kiểm tra ngày lập
        if (ngayLap == null || ngayLap.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày lập không được để trống\", \"hopLe\": false}");
            return false;
        }
        // Kiểm tra hạn chót
        if (maSach == null || maSach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã sách\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
    
        if (maVach == null || maVach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã vạch\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        if (listLiDo.size()==0) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn lí do bị phạt\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        return true; // Không có lỗi
    }
    private boolean CheckMaVach(String maVach)
    {
        
    }
    private boolean checkMaSach(int maPP,int maSach)
    {
        for(CTPT_DTO i:ctpt_BUS.searchCTPTByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()))
        {
            if(i.getMaSach()==maSach && i.getMaSach())
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String maPP = request.getParameter("maPP");
        String maSach = request.getParameter("maSach");
        String maVach = request.getParameter("maVach");
        String ngayLap = request.getParameter("ngayLap");
        String liDo = request.getParameter("liDo");
        String[] items = liDo.split(",");
        ArrayList<String> listLiDo = new ArrayList<>(Arrays.asList(items));
        String tien = request.getParameter("tien");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        switch (action) {
            case "addCTPP":
                if (!checkInfor(request, response, maPP, maSach, maVach, ngayLap, listLiDo)) {
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), Integer.parseInt(maVach)) != null) {
                    response.getWriter().write("{\"thongbao\": \"ctpp này đã tồn tại vui lòng nhập lại mã phiếu phạt và mã vạch\", \"hopLe\": false}");
                    return;
                }
                break;
            case "updateCTPP":
                if (!checkInfor(request, response, maPP, maSach, maVach, ngayLap, listLiDo)) {
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), Integer.parseInt(maVach)) == null) {
                    response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn sửa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
                    return;
                }
                break;
            case "deleteCTPP":
                if (maPP == null || maPP.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã phiếu phạt bạn muốn xóa\", \"hopLe\": false}");
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), Integer.parseInt(maVach)) == null) {
                    response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn xóa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
                    return;
                }
                break;
            case "searchCTPP":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder result = ctpp_BUS.searchPP(optionSearch, valueSearch);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": false, \"results\": " + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "finishCTPP":
                response.getWriter().write("{\"thongbao\": \"đÃ CLICK  RELOAFD\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
    }
    private boolean checktrangThaiCuaSach(int maVach,String lido)
    {
        return true;
    }
    private float tinhTien(int maSach,int maVach,ArrayList<String> listLiDo)
    {
        return 0;
    }
    private CTPP_DTO createCTPP(String maPP,String maSach,String maVach,String ngayLap,ArrayList<String> listLiDo,String tien)
    {
        CTPP_DTO ctpp=new CTPP_DTO();
        ctpp.setMaPP(Integer.parseInt(maPP));
        ctpp.setMaSach(Integer.parseInt(maSach));
        ctpp.setMaVach(maVach);
        ctpp.setNgayLap(LocalDate.parse(ngayLap));
        
        ctpp.setLiDo(listLiDo);
        ctpp.setTien(Float.parseFloat(tien));
        return ctpp;
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
