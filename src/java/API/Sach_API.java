/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package API;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DTO.Sach_DTO;
import DTO.CTPM_DTO;
import DTO.CTPN_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import Payload.ResponseDataSach;
import BUS.Sach_BUS;
import BUS.CTSach_BUS;
import Common.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet(name="Sach_API", urlPatterns={Constant.URL_SACH_ADD, Constant.URL_SACH_UPDATE,Constant.URL_SACH_DELETE,Constant.URL_SACH_LOOK_FOR})
public class Sach_API extends HttpServlet {
    private Sach_BUS sach_BUS = new Sach_BUS();
    private CTSach_BUS ctsach_BUS = new CTSach_BUS();
    private Gson gson = new Gson();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // processRequest(request, response);
       

    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String urlPath = request.getServletPath();
        switch (urlPath) {
            case Constant.URL_SACH_ADD:
                themSach(request, response);
                break;
            case Constant.URL_SACH_UPDATE:
                suaSach(request, response);
                break;
            case Constant.URL_SACH_DELETE:
                xoaSach(request, response);
                break;
            case Constant.URL_SACH_LOOK_FOR:
                timKiemSach(request, response);
                break;
            default:
                break;
        }
        
        
       
    }
    private void themSach(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
         Map<String, String> errorsAdd = new HashMap<>(); 
        String maSach = request.getParameter("maSach");
        int maSachInt = Integer.parseInt(maSach);
        String tenSach = request.getParameter("tenSach");
        String tacGia = request.getParameter("tacGia");
        String maNXB = request.getParameter("maNXB");
        int maNXBInt = Integer.parseInt(maNXB);
        String maNCC = request.getParameter("maNCC");
        int maNCCInt = 0;
        String maKhuVuc = request.getParameter("maKhuVuc");
        int maKhuVucInt = Integer.parseInt(maKhuVuc);
        String giaTien = request.getParameter("giaTien");
        int giaTienInt = Integer.parseInt(giaTien);
        String soLuong = request.getParameter("soLuong");
        int soLuongInt = 0;
        String moTa = request.getParameter("moTa");
        String namXuatBan = request.getParameter("namXuatBan");
        int namXuatBanInt = Integer.parseInt(namXuatBan);
        String anh = request.getParameter("anh");
        if(sach_BUS.kiemTraSach_TonTai(maSachInt)) {
            errorsAdd.put("maSach", "Sách đã tồn tại");
        }
        else {
            if(!sach_BUS.kiemTraSach_CTPN(maSachInt)) {
                errorsAdd.put("maSach", "Sách chưa được nhập");
            }
        }
        if(!sach_BUS.kiemTraNhaXuatBan(maNXBInt)) {
            errorsAdd.put("maNXB", "Nhà xuất bản không tồn tại");
        }
        if(!sach_BUS.kiemTraKhuVuc(maKhuVucInt)) {
            errorsAdd.put("maKhuVuc", "Khu vực không tồn tại");
        }
        if(!errorsAdd.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ResponseDataSach data = new ResponseDataSach();
            data.setIsSuccess(false);
            data.setDescription("");
            data.setErrors(errorsAdd);
            data.setData("");
            String jsonResponse = gson.toJson(data);
            // Gửi phản hồi JSON về client
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);

        }
        else {
        List<Map<String, Object>> list = sach_BUS.getMaNCC_and_SoLuong(maSachInt);
        for (Map<String, Object> item : list) {
            soLuongInt  = (Integer) item.get("tongSoLuong");
            maNCCInt = (Integer) item.get("maNhaCungCap");
            break;
        }
        Sach_DTO sachDTO = new Sach_DTO();
        sachDTO.setMaSach(maSachInt);
        sachDTO.setTenSach(tenSach);
        sachDTO.setTacGia(tacGia);
        sachDTO.setMaNXB(maNXBInt);
        sachDTO.setMaNCC(maNCCInt);
        sachDTO.setMaKhuVuc(maKhuVucInt);
        sachDTO.setGiaTien(giaTienInt);
        sachDTO.setSoLuong(soLuongInt);
        sachDTO.setMoTa(moTa);
        sachDTO.setNamXuatBan(namXuatBanInt);
        sachDTO.setAnh(anh);
        boolean result = sach_BUS.themSach(sachDTO);
        ResponseDataSach data = new ResponseDataSach();
        data.setIsSuccess(result);
        data.setDescription("");
        data.setData(sachDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = gson.toJson(data);
        // Gửi phản hồi JSON về client
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
        }
        
    }
    private void suaSach(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String maSach = request.getParameter("maSach");
        int maSachInt = Integer.parseInt(maSach);
        String tenSach = request.getParameter("tenSach");
        String tacGia = request.getParameter("tacGia");
        String maNXB = request.getParameter("maNXB");
        int maNXBInt = Integer.parseInt(maNXB);
        String maNCC = request.getParameter("maNCC");
        int maNCCInt = Integer.parseInt(maNCC);
        String maKhuVuc = request.getParameter("maKhuVuc");
        int maKhuVucInt = Integer.parseInt(maKhuVuc);
        String giaTien = request.getParameter("giaTien");
        int giaTienInt = Integer.parseInt(giaTien);
        String soLuong = request.getParameter("soLuong");
        int soLuongInt = Integer.parseInt(soLuong);
        String moTa = request.getParameter("moTa");
        String namXuatBan = request.getParameter("namXuatBan");
        int namXuatBanInt = Integer.parseInt(namXuatBan);
        String anh = request.getParameter("anh");
        Sach_DTO sachDTO = new Sach_DTO();
        sachDTO.setMaSach(maSachInt);
        sachDTO.setTenSach(tenSach);
        sachDTO.setTacGia(tacGia);
        sachDTO.setMaNXB(maNXBInt);
        sachDTO.setMaNCC(maNCCInt);
        sachDTO.setMaKhuVuc(maKhuVucInt);
        sachDTO.setGiaTien(giaTienInt);
        sachDTO.setSoLuong(soLuongInt);
        sachDTO.setMoTa(moTa);
        sachDTO.setNamXuatBan(namXuatBanInt);
        sachDTO.setAnh(anh);
        boolean result = sach_BUS.suaSach(sachDTO);
        ResponseDataSach data = new ResponseDataSach();
        data.setIsSuccess(result);
        data.setDescription("");
        data.setData(sachDTO);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = gson.toJson(data);
        // Gửi phản hồi JSON về client
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);

    }
    private void xoaSach(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String maSach = request.getParameter("maSach");
        int maSachInt = Integer.parseInt(maSach);
        // xoaTableChild(maSachInt);
        // chi tiết sách
        ArrayList<CTSach_DTO> listCTSach = ctsach_BUS.getCTSach_as_maSach(maSachInt);
        if(listCTSach != null && !listCTSach.isEmpty()) {
            for (CTSach_DTO ctSach : listCTSach) {
                ctsach_BUS.deleteCTSach_as_maSach(maSachInt);
            }
        }
        // chi tiết phiếu mượn
        ArrayList<CTPM_DTO> listCTPM = sach_BUS.layDanhSachCTPM_as_maSach(maSachInt);
        if(listCTPM != null && !listCTPM.isEmpty()) {
            for (CTPM_DTO ctpm : listCTPM) {
                sach_BUS.xoaCTPM_as_maSach(maSachInt);
            }
        }
        // chi tiết phiếu trả
        ArrayList<CTPT_DTO> listCTPT = sach_BUS.layDanhSachCTPT_as_maSach(maSachInt);
        if(listCTPT != null && !listCTPT.isEmpty()) {
            for (CTPT_DTO ctpt : listCTPT) {
                sach_BUS.xoaCTPT_as_maSach(maSachInt);
            }
        }
        // chi tiết phiếu nhập
        ArrayList<CTPN_DTO> listCTPN = sach_BUS.layDanhSachCTPN_as_maSach(maSachInt);
        if(listCTPN != null && !listCTPN.isEmpty()) {
            for (CTPN_DTO ctpn : listCTPN) {
                sach_BUS.xoaCTPN_as_maSach(maSachInt);
            }
        }
        // chi tiết phiếu phạt
        ArrayList<CTPP_DTO> listCTPP = sach_BUS.layDanhSachCTPP_as_maSach(maSachInt);
        if(listCTPP != null && !listCTPP.isEmpty()) {
            for (CTPP_DTO ctpp : listCTPP) {
                sach_BUS.xoaCTPP_as_maSach(maSachInt);
            }
        }
        boolean result = sach_BUS.xoaSach(maSachInt);
        if(result) {
            // chi tiết sách
            if(listCTSach != null && !listCTSach.isEmpty()) {
                for (CTSach_DTO ctSach : listCTSach) {
                    ctSach.setMaSach(ctSach.getMaSach() * -1);
                    ctsach_BUS.addCTSach_when_deleteSach(ctSach);
                }
            }
            // chi tiết phiếu mượn
        if(listCTPM != null && !listCTPM.isEmpty()) {
            for (CTPM_DTO ctpm : listCTPM) {
                ctpm.setMaSach(ctpm.getMaSach() * -1);
                sach_BUS.themCTPM_as_maSach(ctpm);
            }
        }
        // chi tiết phiếu trả
        if(listCTPT != null && !listCTPT.isEmpty()) {
            for (CTPT_DTO ctpt : listCTPT) {
                ctpt.setMaSach(ctpt.getMaSach() * -1);
                sach_BUS.themCTPT_as_maSach(ctpt);
            }
        }
        // chi tiết phiếu nhập
        if(listCTPN != null && !listCTPN.isEmpty()) {
            for (CTPN_DTO ctpn : listCTPN) {
                ctpn.setMaSach(ctpn.getMaSach() * -1);
                sach_BUS.themCTPN_as_maSach(ctpn);
            }
        }
        // chi tiết phiếu phạt
        if(listCTPP != null && !listCTPP.isEmpty()) {
            for (CTPP_DTO ctpp : listCTPP) {
                ctpp.setMaSach(ctpp.getMaSach() * -1);
                sach_BUS.themCTPP_as_maSach(ctpp);
            }
        }
        }
        else {
            // chi tiết sách
            if(listCTSach != null && !listCTSach.isEmpty()) {
                for (CTSach_DTO ctSach : listCTSach) {
                    ctsach_BUS.addCTSach_when_deleteSach(ctSach);
                }
            }
            // chi tiết phiếu mượn
        if(listCTPM != null && !listCTPM.isEmpty()) {
            for (CTPM_DTO ctpm : listCTPM) {
                sach_BUS.themCTPM_as_maSach(ctpm);
            }
        }
        // chi tiết phiếu trả
        if(listCTPT != null && !listCTPT.isEmpty()) {
            for (CTPT_DTO ctpt : listCTPT) {
                sach_BUS.themCTPT_as_maSach(ctpt);
            }
        }
        // chi tiết phiếu nhập
        if(listCTPN != null && !listCTPN.isEmpty()) {
            for (CTPN_DTO ctpn : listCTPN) {
                sach_BUS.themCTPN_as_maSach(ctpn);
            }
        }
        // chi tiết phiếu phạt
        if(listCTPP != null && !listCTPP.isEmpty()) {
            for (CTPP_DTO ctpp : listCTPP) {
                sach_BUS.themCTPP_as_maSach(ctpp);
            }
        }
        }
        ResponseDataSach data = new ResponseDataSach();
        data.setIsSuccess(result);
        data.setDescription("");
        data.setData("");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = gson.toJson(data);
        // Gửi phản hồi JSON về client
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
    }
    private void timKiemSach(HttpServletRequest request, HttpServletResponse response) throws IOException  {
        String tuKhoaTimKiem = request.getParameter("tuKhoaTimKiem");
        System.err.println("tuKhoaTimKiem 1: " + tuKhoaTimKiem);
        String moneyMin = request.getParameter("moneyMinApi");
        String moneyMax = request.getParameter("moneyMaxApi");
        int moneyMinInt, moneyMaxInt;
        String tenKhuVuc = request.getParameter("tenKhuVuc");
        // Kiểm tra nếu tuKhoaTimKiem là null hoặc bằng "-1"
            // Kiểm tra nếu tuKhoaTimKiem là null hoặc bằng "-1"
if (tuKhoaTimKiem != null && tuKhoaTimKiem.equals("-1")) {
    tuKhoaTimKiem = "";
    System.err.println("tuKhoaTimKiem 2: " + tuKhoaTimKiem);
}

// Kiểm tra nếu moneyMin là null, chuỗi rỗng hoặc "0", gán giá trị mặc định là -1
if (moneyMin == null || moneyMin.trim().isEmpty() || moneyMin.equals("0")) {
    moneyMinInt = -1;
} else {
    moneyMinInt = Integer.parseInt(moneyMin);
}

// Kiểm tra nếu moneyMax là null, chuỗi rỗng hoặc "0", gán giá trị mặc định là -1
if (moneyMax == null || moneyMax.trim().isEmpty() || moneyMax.equals("0")) {
    moneyMaxInt = -1;
} else {
    moneyMaxInt = Integer.parseInt(moneyMax);
}

// Kiểm tra nếu moneyMax là "0", thay đổi giá trị của moneyMaxInt
if ("-2".equals(moneyMax)) {
    moneyMaxInt = Integer.MAX_VALUE;
}

// Kiểm tra nếu tenKhuVuc là null hoặc bằng "-1"
if (tenKhuVuc != null && tenKhuVuc.equals("-1")) {
    tenKhuVuc = "";
}


        System.out.println("tuKhoaTimKiem: " + tuKhoaTimKiem);
        System.out.println("moneyMin: " + moneyMinInt);
        System.out.println("moneyMax: " + moneyMaxInt);
        System.out.println("tenKhuVuc: " + tenKhuVuc);
        ArrayList<Sach_DTO> listSach = sach_BUS.listSach_LookFor(tuKhoaTimKiem, moneyMinInt, moneyMaxInt, tenKhuVuc);
        if(listSach != null && !listSach.isEmpty()) {
        ResponseDataSach data = new ResponseDataSach();
        data.setIsSuccess(true);
        data.setDescription("Danh sách sách được lấy thành công.");
        data.setData(listSach);;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = gson.toJson(data);
        // Gửi phản hồi JSON về client
        PrintWriter out = response.getWriter();
        out.println(jsonResponse);
        } else {
            ResponseDataSach data = new ResponseDataSach();
            data.setIsSuccess(false);
            data.setDescription("Không tìm thấy sách nào.");
            data.setData("");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = gson.toJson(data);
            // Gửi phản hồi JSON về client
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);
        }
}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
