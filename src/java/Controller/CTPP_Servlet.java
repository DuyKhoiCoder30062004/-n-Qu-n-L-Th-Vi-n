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
    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS = new CTPT_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }
    //Hiện thị những phiếu trả có lỗi và chưa có mã phiếu phạt
    private boolean checkCoLoi(int maPT)
    {
        for(CTPT_DTO i:ctpt_BUS.searchByMaPT(maPT))
        {
            if(i.getMaVachLoi()!=null)
                return true;
        }
        return false;
    }
    private boolean checkMaPT_CuaPP(int maPT)
    {
        for(PhieuPhat_DTO i:pp_BUS.getList())
            if(i.getMaPT()==maPT)
                return true;
        return false;
    }
    private ArrayList<PhieuTra_DTO> listPT_BiPhat()
    {
        ArrayList<PhieuTra_DTO> listPT=new ArrayList<PhieuTra_DTO>();
        for(PhieuTra_DTO i:pt_BUS.getListPhieuTra())
        {
            if(checkCoLoi(i.getMaPT()) && !checkMaPT_CuaPP(i.getMaPT()) )
            {
                listPT.add(i);
            }
        }
        return listPT;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuPhat_DTO> listPP = pp_BUS.getList();
        ArrayList<CTPP_DTO> listCTPP = ctpp_BUS.getList();
        ArrayList<Loi_DTO> listLoi = loi_BUS.getList();
        ArrayList<PhieuTra_DTO> listPT = listPT_BiPhat();
        ArrayList<Sach_DTO> listSach = sach_BUS.getListSach();
        ArrayList<CTSach_DTO> listCTS = cts_BUS.getList();
        request.setAttribute("listLoi", listLoi);
        request.setAttribute("listCTPP", listCTPP);
        request.setAttribute("listPP", listPP);
        request.setAttribute("listPT", listPT);
        request.setAttribute("listSach", listSach);
        request.setAttribute("listCTS", listCTS);
        request.getRequestDispatcher("/WEB-INF/gui/phieuphat.jsp").forward(request, response);
    }
    //Kiểm tra mã vạch xem mã vạch này đúng có lỗi không
    private boolean checkMaVach(int maPP, int maSach, String maVach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT())) {
            System.out.println("PT "+i.getMaSach()+"   "+i.getMaVachLoi());
            //System.out.println("kiểm tra mã vạch"+ i.getMaVachLoi())
            if (i.getMaSach()==maSach &&i.getMaVachLoi()!=null && i.getMaVachLoi().contains(maVach.trim())) 
            {
                System.out.println("PT kiểm tra đúng " + i.getMaSach() + "   " + i.getMaVachLoi());
                return true;
            }
        }
        return false;
    }
   
    //Kiểm tra mã sách này có trong phiếu  trả k
    private boolean checkMaSach_PT_Co_Khong(int maPP, int maSach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT())) {
            if (i.getMaSach() == maSach) {
                return true;
            }
        }
        return false;
    }

    //kiểm tra mã sách này có mã vạch lỗi k
    private boolean checkMaSach(int maPP, int maSach) {
        if (!ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()).isEmpty()) {
            for (CTPT_DTO i : ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT())) {
                if (i.getMaSach() == maSach && i.getMaVachLoi()!=null) {
                    return true;
                }
            }
        }

        return false;
    }
    //kiểm tra sách này có nộp trễ hạn không
    private boolean checkTreHan(int maPP, int maSach) {
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT())) {
            if (i.getMaSach() == maSach && i.getMaVachLoi()!=null) {
                PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
                PhieuMuon_DTO pm = pm_BUS.searchByMaPM(pt_BUS.searchByMaPT(pp_BUS.searchByMaPP(maPP).getMaPT()).getMaPM());
                if (i.getNgayTra().isAfter(pm.getHanChot())) {
                    return true;
                }
            }
        }
        return false;
    }
    //kiểm tra lỗi này có trong listTT không
    private boolean checkLoi(String listTT, String loi) {
        if (listTT.contains(loi)) {
            return true;
        }
        return false;
    }
    //Tính tiền
    private float tinhTien(String maSach, String liDo) {
        float tien = 0;
        String[] listLiDo = liDo.split(",");
        Sach_DTO sach = sach_BUS.timSachTheoMaSach(maSach).get(0);
        for (String i : listLiDo) {
            System.out.println("%tiền" + loi_BUS.searchByTenLoi(i).getPhamTramTien());
            tien += sach.getGia() * loi_BUS.searchByTenLoi(i).getPhamTramTien();
        }
        return tien;
    }
    //Tìm những lí do trùng với tình trạng của sách
    private String checkLiDo(String TTSach, String liDo) {
        if(TTSach!=null)
        {
            String[] listLiDo = liDo.split(",");
            String tam = "";
            for (String i : listLiDo) {
                if (!i.equals("trả trễ") && TTSach.contains(i)) {
                    tam += i + " ";
                }
            }
            if (!tam.equals("")) {
                return "Những lý do " + tam + " đã có trong tình trạng sách vui lòng chọn  lại";
            }
        }
        return "";
    }
    //Kiểm tra xem mã vạch này có của sách này không
    private boolean checkMaVachCuaSach(String maSach,String maVach)
    {
        for(CTSach_DTO i:cts_BUS.searchCTSachByMaSach(maSach))
            if(i.getMaVach().equals(maVach))
                return true;
        return false;
    }
    //Tìm kiếm ct sách
    private CTSach_DTO searchCTS(String maVach) {
        for (CTSach_DTO i : cts_BUS.getList()) {
            if (i.getMaVach().equals(maVach)) {
                return i;
            }
        }
        return null;
    }
    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPP, String maSach, String maVach, String ngayLap, String liDo)
            throws IOException {

        if (maPP == null || maPP.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã phiếu phạt\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPP);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt phải là số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (pp_BUS.searchByMaPP(Integer.parseInt(maPP)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt bạn nhập chưa có phiếu phạt vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (maSach == null || maSach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã sách\", \"hopLe\": false}");
            return false;
        }
        if (!checkMaSach_PT_Co_Khong(Integer.parseInt(maPP), Integer.parseInt(maSach))) {
            response.getWriter().write("{\"thongbao\": \"Mã sách bạn chọn không có trong phiếu trả của phiếu phạt này vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if (!checkMaSach(Integer.parseInt(maPP), Integer.parseInt(maSach))) {
            response.getWriter().write("{\"thongbao\": \"Mã sách trong phiếu trả này không có sách nào bị lỗi vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if (maVach == null || maVach.trim().isEmpty()) {
            System.out.println("Mã vạch ch đc chọn");
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã vạch của sách có lỗi\", \"hopLe\": false}");
            return false;
        }
        if(!checkMaVachCuaSach(maSach,maVach))
        {
            response.getWriter().write("{\"thongbao\": \"Mã vạch bạn chọn không thuộc về  mã sách bạn đã chọn vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if (!checkMaVach(Integer.parseInt(maPP), Integer.parseInt(maSach), maVach)) {
            response.getWriter().write("{\"thongbao\": \"Mã vạch của mã sách bạn chọn không có hư hỏng hay lỗi gì vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        if (ngayLap == null || ngayLap.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày lập không được để trống\", \"hopLe\": false}");
            return false;
        }
        if (liDo == null || liDo.isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng chọn lí do bị phạt\", \"hopLe\": false}");
            return false;
        }
        if (!liDo.contains("trả trễ") && checkTreHan(Integer.parseInt(maPP), Integer.parseInt(maSach))) {
            System.out.println("CÓ LỖI TRẢ TRỄ HẠN");
            response.getWriter().write("{\"thongbao\": \"Sách này  nộp trễ hạn vui lòng chọn lỗi trễ hạn trên table quản lí Lỗi\", \"hopLe\": false}");
            return false;
        }
        System.out.println("Đã qua check mã kiểm tra trễ hạn của sách");
        if (liDo.contains("trả trễ") && !checkTreHan(Integer.parseInt(maPP), Integer.parseInt(maSach))) {
            response.getWriter().write("{\"thongbao\": \"Sách này không có nộp trễ hạn vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean checkInforDelete(HttpServletRequest request, HttpServletResponse response, String maPP, String maVach)
            throws IOException {
        if (maPP == null || maPP.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập mã hoặc chọn phiếu phạt\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        try {
            Integer.parseInt(maPP);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu phạt phải là số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (maVach == null || maVach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập hoặc chọn mã vạch của ctpp bạn muốn xóa\", \"hopLe\": false}");
            return false; // Dừng hàm nếu lỗi
        }
        if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach) == null) {
            response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn xóa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
            return false;
        }
        return true;
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String maPP = request.getParameter("maPP");
        String maSach = request.getParameter("maSach");
        String maVach = request.getParameter("maVach");
        String ngayLap = request.getParameter("ngayLap");
        String maNV = request.getParameter("maNV");
        String liDo = request.getParameter("liDo");
        String tien = request.getParameter("tien");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");
        System.out.println("mã vạch nè: " + maVach);
        switch (action) {
            case "addCTPP":
                if (!checkInfor(request, response, maPP, maSach, maVach, ngayLap, liDo)) {
                    return;
                }
                System.out.println(" RA NGOAI VONG KIEM TRA ");
                if (!checkLiDo(searchCTS(maVach).getTinhTrangSach(), liDo).equals("")) {
                    response.getWriter().write("{\"thongbao\": \"" + checkLiDo(searchCTS(maVach).getTinhTrangSach(), liDo) + "\", \"hopLe\": false}");
                    return;
                }
                System.out.println("Đã qua check LiDO ");
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach) != null) {
                    response.getWriter().write("{\"thongbao\": \"ctpp này đã tồn tại vui lòng nhập lại mã phiếu phạt và mã vạch\", \"hopLe\": false}");
                    return;
                }
                CTPP_DTO ctpp = createCTPP(maPP, maSach, maVach, ngayLap, liDo, tien);
                ctpp.setTien(tinhTien(maSach, liDo));
                PhieuPhat_DTO pp = pp_BUS.searchByMaPP(ctpp.getMaPP());
                pp.setTongTien(pp.getTongTien() + ctpp.getTien());
                CTSach_DTO cts = searchCTS(maVach);
                cts.setTinhTrangSach(cts.getTinhTrangSach() + "," + liDo);
                if (ctpp_BUS.addCTPP(ctpp) && pp_BUS.updateTT(pp.getMaPP(), pp.getTongTien())
                        && cts_BUS.updateCTSach(cts)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm ctpp thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm ctpp thất bại\", \"hopLe\": false}");
                }
                break;
            case "updateCTPP":
                if (!checkInfor(request, response, maPP, maSach, maVach, ngayLap, liDo)) {
                    return;
                }
                if (ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach) == null) {
                    response.getWriter().write("{\"thongbao\": \"không tìm thấy ctpp bạn muốn sửa vui lòng chọn ctpp trên table\", \"hopLe\": false}");
                    return;
                }
                CTPP_DTO ctppUpdate = ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach);
                CTSach_DTO ctsUpdate = searchCTS(maVach);
                String tts = ctsUpdate.getTinhTrangSach();
                for (String i : ctppUpdate.getLiDo()) {
                    tts = tts.replace(i, "").replaceAll(",\\s*,", ",").trim();
                }
                tts = tts.substring(0, tts.length());
                if (!checkLiDo(tts, liDo).equals("")) {
                    response.getWriter().write("{\"thongbao\": \"" + checkLiDo(tts, liDo) + "\", \"hopLe\": false}");
                    return;
                }
                float ttc = ctppUpdate.getTien();
                ctppUpdate.setTien(tinhTien(maSach, liDo));
                PhieuPhat_DTO ppUpdate = pp_BUS.searchByMaPP(ctppUpdate.getMaPP());
                ppUpdate.setTongTien(ppUpdate.getTongTien() + ctppUpdate.getTien() - ttc);
                ctsUpdate.setTinhTrangSach(tts + "," + liDo);
                if (ctpp_BUS.updateCTPP(ctppUpdate)
                        && pp_BUS.updateTT(ppUpdate.getMaPP(), ppUpdate.getTongTien()) && cts_BUS.updateCTSach(ctsUpdate)) {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thành công\", \"hopLe\": true}");

                } else {
                    response.getWriter().write("{\"thongbao\": \"Update ctpp thất bại\", \"hopLe\": false}");
                }
                break;
            case "deleteCTPP":
                if (!checkInforDelete(request, response, maPP, maVach)) {
                    return;
                }
                CTPP_DTO ctppDele = ctpp_BUS.searchByMaPP_MaVach(Integer.parseInt(maPP), maVach);
                PhieuPhat_DTO ppDele = pp_BUS.searchByMaPP(ctppDele.getMaPP());
                ppDele.setTongTien(ppDele.getTongTien() - ctppDele.getTien());
                CTSach_DTO ctsDele = searchCTS(maVach);
                String ttDele = ctsDele.getTinhTrangSach();
                for (String i : ctppDele.getLiDo()) {
                    ttDele = ttDele.replace(i, "").replaceAll(",\\s*,", ",").trim();
                }
                ctsDele.setTinhTrangSach(ttDele);
                if (ctpp_BUS.deleteCTPP(Integer.parseInt(maPP), maVach)
                        && pp_BUS.updateTT(ppDele.getMaPP(), ppDele.getTongTien()) && cts_BUS.updateCTSach(ctsDele)) {
                    response.getWriter().write("{\"thongbao\": \"Xóa ctpp thành công\", \"hopLe\": true}");

                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm ctpp thất bại\", \"hopLe\": true}");
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
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
        if ((action.equals("addCTPP") || action.equals("updateCTPP") || action.equals("deleteCTPP"))
                && pp_BUS.searchByMaPP(Integer.parseInt(maPP)).getMaNV() != Integer.parseInt(maNV)) {
            PhieuPhat_DTO pp = pp_BUS.searchByMaPP(Integer.parseInt(maPP));
            pp.setMaNV(Integer.parseInt(maNV));
            pp_BUS.updatePP(pp);
        }
    }

    private CTPP_DTO createCTPP(String maPP, String maSach, String maVach, String ngayLap, String liDo, String tien) {
        CTPP_DTO ctpp = new CTPP_DTO();
        ctpp.setMaPP(Integer.parseInt(maPP));
        ctpp.setMaSach(Integer.parseInt(maSach));
        ctpp.setMaVach(maVach);
        ctpp.setNgayLap(LocalDate.parse(ngayLap));
        String[] items = liDo.split(",");
        ArrayList<String> listLiDo = new ArrayList<>(Arrays.asList(items));
        ctpp.setLiDo(listLiDo);
        ctpp.setTien(Float.parseFloat(tien));
        return ctpp;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
