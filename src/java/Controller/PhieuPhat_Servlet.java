/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.Loi_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
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
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */

@WebServlet(name = "PhieuPhat_Servlet", urlPatterns = {"/phieuphat"})
public class PhieuPhat_Servlet extends HttpServlet {

    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private Loi_BUS loi_BUS = new Loi_BUS();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();
    private PhieuTra_BUS pt_BUS=new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS=new CTPT_BUS();
    private Sach_BUS sach_BUS=new Sach_BUS();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }
//    private boolean checkMaSach(ArrayList<Sach_DTO> listSach,int masach)
//    {
//        for(Sach_DTO i:listSach)
//        {
//            if(i.getMaSach()==masach)
//                return false;
//        }
//        return true;
//    }
//
//    private ArrayList<Sach_DTO> getListSachOfPT(int mapt)
//    {
//        ArrayList<Sach_DTO> listSach=new ArrayList<>();
//        for (CTPT_DTO i: ctpt_BUS.searchCTPTByMaPT(mapt))
//        {
//            if(checkMaSach(listSach,i.getMaSach()))
//            {
//                listSach.add(sach_BUS.timSachTheoMaSach(String.valueOf(i.getMaSach())).get(0));
//            }
//        }
//        return listSach;
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuPhat_DTO> listPP = pp_BUS.getList();
        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.getList();
        ArrayList<Loi_DTO> listLoi = loi_BUS.getList();
        ArrayList<PhieuTra_DTO> listPT=pt_BUS.getListPhieuTra();
        ArrayList<Sach_DTO> listSach=sach_BUS.getListSach();
        request.setAttribute("listLoi", listLoi);
        request.setAttribute("listCTPP", listCTPP);
        request.setAttribute("listPP", listPP);
        request.setAttribute("listPT", listPT);
        request.setAttribute("listSach", listSach);
       System.out.println("lisstSách" +listSach);
        request.getRequestDispatcher("/WEB-INF/gui/phieuphat.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPP, String maPT, String maNV)
            throws IOException {
        if (maPP == null || maPP.isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã phiếu phạt\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPP);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (maPT == null || maPT.isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã phiếu trả\", \"hopLe\": false}");
            return false;
        }
        if (maNV == null || maNV.isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã nhân viên\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String maPP = request.getParameter("maPP");
        String maPT = request.getParameter("maPT");
        String maNV = request.getParameter("maNV");
        String tongTien = request.getParameter("tongTien");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        String namePath=request.getParameter("nameFileExcel");
        System.out.println("path: "+namePath);
        switch (action) {
            case "addPP":
                if (!checkInfor(request, response, maPP, maPT, maNV)) {
                    return;
                }
                if (pp_BUS.searchByMaPP(Integer.parseInt(maPP)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt đã tồn tại vui lòng nhập lại mã phiếu phạt\", \"hopLe\": false}");
                    return;
                }
                if (pp_BUS.addPP(createPP(maPP, maPT, maNV, tongTien))) {
                    response.getWriter().write("{\"thongbao\": \"Thêm phiếu phạt thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm phiếu phạt thất bại\", \"hopLe\": false}");
                }
                break;
            case "updatePP":
                if (!checkInfor(request, response, maPP, maPT, maNV)) {
                    return;
                }
                if (pp_BUS.searchByMaPP(Integer.parseInt(maPP)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt không tồn tại vui lòng chọn lại phiếu phạt trên table\", \"hopLe\": false}");
                    return;
                }
                if (pp_BUS.updatePP(createPP(maPP, maPT, maNV, tongTien))) {
                    response.getWriter().write("{\"thongbao\": \"Sửa phiếu phạt thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa phiếu phạt thất bại\", \"hopLe\": false}");
                }
                break;
            case "deletePP":
                if (maPP == null || maPP.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập phiếu phạt hoặc trên phiếu phạt trên table để xóa\", \"hopLe\": false}");
                    return;
                }
                if (pp_BUS.searchByMaPP(Integer.parseInt(maPP)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt không tồn tại vui lòng chọn lại phiếu phạt trên table\", \"hopLe\": false}");
                    return;
                }
                if (pp_BUS.deletePP(Integer.parseInt(maPP))) {
                    ctpp_BUS.deleteByMaPP(Integer.parseInt(maPP));
                    response.getWriter().write("{\"thongbao\": \"Xóa phiếu phạt thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa phiếu phạt thất bại\", \"hopLe\": false}");
                }
                break;
            case "searchPP":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder[] result = pp_BUS.searchPP(optionSearch, valueSearch);
                if (result[0].length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": false, "
                            + "\"resultPP\": " + result[0].toString() + ", "
                            + "\"resultsCTPP\": " + result[1].toString()
                            + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "export":
                if(namePath.isEmpty())
                {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để export\", \"hopLe\": false}");
                    return;
                }
                if(pp_BUS.exportExcel(namePath))
                {
                    response.getWriter().write("{\"thongbao\": \"Export excel thành công\", \"hopLe\": true}");
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Export thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
                }
                break;
            case "print":
                if(maPP==null || maPP.isEmpty())
                {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu phạt trên table hoặc nhập mã phiếu trên thanh input mã phiếu để in\", \"hopLe\": false}");
                    return;
                }
                try {
                    Integer.parseInt(maPP);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên vui lòng kiểm tra lại để in\", \"hopLe\": false}");
                    return;
                }
                if (pp_BUS.searchByMaPP(Integer.parseInt(maPP)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không tìm thấy phiếu bạn cần in vui lòng chọn lại phiếu hoặc nhập lại mã phiếu trên thanh input mã phiếu\", \"hopLe\": false}");
                    return;
                }
                try {
                    String htmlContent = pp_BUS.printPP(Integer.parseInt(maPP));
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(htmlContent);
                    out.close();
                } catch (Exception e) {
                    response.getWriter().write("{\"thongbao\": \"In thất bại vui lòng làm lại\", \"hopLe\": false}");
                    e.printStackTrace();
                }
                break;
            case "finishPP":
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
    }

    private PhieuPhat_DTO createPP(String maPP, String maPT, String maNV, String tongTien) {
        PhieuPhat_DTO pp = new PhieuPhat_DTO();
        pp.setMaPP(Integer.parseInt(maPP));
        pp.setMaPT(Integer.parseInt(maPT));
        pp.setMaNV(Integer.parseInt(maNV));
        pp.setTongTien(Double.parseDouble(tongTien));
        return pp;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
