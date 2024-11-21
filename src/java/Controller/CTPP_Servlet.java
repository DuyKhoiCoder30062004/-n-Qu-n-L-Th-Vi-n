/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.CTSach_BUS;
import BUS.Loi_BUS;
import BUS.PhieuMuon_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import DTO.Loi_DTO;
import DTO.PhieuMuon_DTO;
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
    private boolean checkMaVach(int maPP,String maVach)
    {
        for(CTPT_DTO i:ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()))
        {
            if(i.getMaVachLoi().contains(maVach))
                return true;
        }
        return false;
    }
    private boolean checkMaSach(int maPP,int maSach)
    {
        for(CTPT_DTO i:ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()))
        {
            if(i.getMaSach()==maSach && i.getMaVachLoi().length()>0)
                return true;
        }
        return false;
    }
    private boolean checkTreHan(int maPP)
    {
        for(CTPT_DTO i:ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()))
        {
            PhieuMuon_BUS pm_BUS=new PhieuMuon_BUS();
            PhieuMuon_DTO pm=pm_BUS.searchByMaPM(pt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()).getMaPM());
            if(i.getNgayTra().isAfter(pm.getHanChot()));
                return true;
        }
        return false;
    }
    private boolean checkLoi(String listTT,String loi)
    {
        if(listTT.contains(loi))
            return true;
        return false;
    }
    private float tinhTien(String maSach,String maVach,ArrayList<String> listLiDo)
    {
        float tien=0;
        Sach_DTO sach=sach_BUS.timSachTheoMaSach(maSach).get(0);
        CTSach_DTO cts=cts_BUS.searchCTSachByMaVach(maVach).get(0);
        for(String i:listLiDo)
        {
            tien+=sach.getGia()*loi_BUS.searchByTenLoi(i).getPhamTramTien();
                
        }
        return tien;
    }
    private String checkLiDo(int maPP,String maVach,ArrayList<String>listLiDo)
    {
        String tam ="";
        CTSach_DTO cts=cts_BUS.searchCTSachByMaVach(maVach).get(0);
        for(String i:listLiDo)
        {
            if(!i.equals("trễ hạn") && cts.getTinhTrangSach().contains(i))
            {
                tam+=i+" ";
            }
        }
        if(tam.isEmpty())
            return "";
        else return "Những lý do "+tam+" đã có trong tình trạng sách vui lòng chọn  lại";
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
        if(checkMaSach(Integer.parseInt(maPP),Integer.parseInt(maSach))!=true)
        {
            response.getWriter().write("{\"thongbao\": \"Mã sách này không có sách nào bị lỗi vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if (maVach == null || maVach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã vạch\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        if(checkMaVach(Integer.parseInt(maPP),maVach)!=true)
        {
            response.getWriter().write("{\"thongbao\": \"Mã vạch bạn chọn không có hư hỏng hay lỗi gì vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false; 
        }
        if (listLiDo.size()==0) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn lí do bị phạt\", \"hopLe\": false}");
            return false; 
        }
        if(listLiDo.contains("trễ hạn" )&& !checkTreHan(Integer.parseInt(maPP)))
        {
            response.getWriter().write("{\"thongbao\": \"Sách này không có nộp trễ hạn vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if(!checkLiDo(Integer.parseInt(maPP),maVach,listLiDo).equals(""))
        {
            response.getWriter().write("{\"thongbao\": \""+checkLiDo(Integer.parseInt(maPP),maVach,listLiDo)+"\", \"hopLe\": false}");
            return false; 
        }
        return true;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String maPP = request.getParameter("maPP");
        String maSach = request.getParameter("maSach");
        String maVach = request.getParameter("maVach");
        String ngayLap = request.getParameter("ngayLap");
        String maNV= request.getParameter("maNV");
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
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach) != null) {
                    response.getWriter().write("{\"thongbao\": \"ctpp này đã tồn tại vui lòng nhập lại mã phiếu phạt và mã vạch\", \"hopLe\": false}");
                    return;
                }
                CTPP_DTO ctpp= createCTPP(maPP,maSach,maVach,ngayLap,listLiDo,tien);
                ctpp.setTien(tinhTien(maSach,maVach,listLiDo));
                PhieuPhat_DTO pp=pp_BUS.searchByMaPP(ctpp.getMaPP());
                pp.setTongTien(pp.getTongTien()+ctpp.getTien());
                CTSach_DTO cts=cts_BUS.searchCTSachByMaSach(maVach).get(0);
                cts.setTinhTrangSach(cts.getTinhTrangSach()+","+liDo);
                if(ctpp_BUS.addCTPP(ctpp) && pp_BUS.updateTT(pp.getMaPP(),pp.getTongTien()) &&cts_BUS.updateCTSach(cts))
                {
                    response.getWriter().write("{\"thongbao\": \"Thêm ctpp thành công\", \"hopLe\": true}");
                    
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Thêm ctpp thất bại\", \"hopLe\": false}");
                }
                break;
            case "updateCTPP":
                if (!checkInfor(request, response, maPP, maSach, maVach, ngayLap, listLiDo)) {
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP),maVach) == null) {
                    response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn sửa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
                    return;
                }
                CTPP_DTO ctppUpdate= ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP),maVach);
                ArrayList<String> listLiDoc=ctppUpdate.getLiDo();
                float ttc=ctppUpdate.getTien();
                ctppUpdate.setTien(tinhTien(maSach,maVach,listLiDo));
                PhieuPhat_DTO ppUpdate=pp_BUS.searchByMaPP(ctppUpdate.getMaPP());
                ppUpdate.setTongTien(ppUpdate.getTongTien()+ctppUpdate.getTien()-ttc);
                CTSach_DTO ctsUpdate=cts_BUS.searchCTSachByMaSach(maVach).get(0);
                String tts=ctsUpdate.getTinhTrangSach();
                for(String i:listLiDoc)
                {
                    tts=tts.replace(i,"").replaceAll(",\\s*,", ",").trim();
                }
                ctsUpdate.setTinhTrangSach(tts+","+liDo);
                if(ctpp_BUS.updateCTPP(ctppUpdate) && pp_BUS.updateTT(ppUpdate.getMaPP(),ppUpdate.getTongTien()) &&cts_BUS.updateCTSach(ctsUpdate))
                {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thành công\", \"hopLe\": true}");
                    
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thất bại\", \"hopLe\": false}");
                }
                break;
            case "deleteCTPP":
                if (maPP == null || maPP.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã phiếu phạt bạn muốn xóa\", \"hopLe\": false}");
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP),maVach) == null) {
                    response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn xóa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
                    return;
                }
                CTPP_DTO ctppDele= ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP),maVach);
                PhieuPhat_DTO ppDele=pp_BUS.searchByMaPP(ctppDele.getMaPP());
                ppDele.setTongTien(ppDele.getTongTien()-ctppDele.getTien());
                CTSach_DTO ctsDele=cts_BUS.searchCTSachByMaSach(maVach).get(0);
                String ttDele=ctsDele.getTinhTrangSach();
                for(String i:ctppDele.getLiDo())
                {
                    ttDele=ttDele.replace(i,"").replaceAll(",\\s*,", ",").trim();
                }
                ctsDele.setTinhTrangSach(ttDele);
                if(ctpp_BUS.deleteCTPP(Integer.parseInt(maPP),maVach) && pp_BUS.updateTT(ppDele.getMaPP(),ppDele.getTongTien()) &&cts_BUS.updateCTSach(ctsDele))
                {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thành công\", \"hopLe\": true}");
                    
                }
                else
                {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thất bại\", \"hopLe\": false}");
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
        if( (action.equals("addCTPP") || action.equals("updateCTPP") || action.equals("deleteCTPP")) 
                &&pp_BUS.searchByMaPP(Integer.parseInt(maPP)).getMaNV()!= Integer.parseInt(maNV) )
        {
            PhieuPhat_DTO pp=pp_BUS.searchByMaPP(Integer.parseInt(maPP));
            pp.setMaNV(Integer.parseInt(maNV));
            pp_BUS.updatePP(pp);
        }
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
