package Controller;

import BUS.CTPM_BUS;
import BUS.CTPP_BUS;
import java.io.IOException;
import java.util.ArrayList;

import BUS.CTPT_BUS; // Changed to CTPT_BUS
import BUS.CTSach_BUS;
import BUS.PhieuMuon_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS; // Changed to PhieuTra_BUS
import BUS.Sach_BUS;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO; // Changed to CTPT_DTO
import DTO.CTSach_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
import DTO.PhieuTra_DTO; // Changed to PhieuTra_DTO
import DTO.Sach_DTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CTPTServlet", urlPatterns = {"/ctpt"}) // Changed the servlet name and URL pattern
public class CTPTServlet extends HttpServlet {

    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS(); // Changed to PhieuTra_BUS
    private CTPT_BUS ctpt_BUS = new CTPT_BUS(); // Changed to CTPT_BUS
    private CTPM_BUS ctpm_BUS = new CTPM_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuTra_DTO> listPT = pt_BUS.getListPhieuTra(); // Changed to PhieuTra_DTO
        List<CTPT_DTO> listCTPT = ctpt_BUS.getList(); // Changed to CTPT_DTO
        request.setAttribute("listCTPT", listCTPT); // Changed to listCTPT
        request.setAttribute("listPT", listPT); // Changed to listPT
        request.getRequestDispatcher("/WEB-INF/gui/phieutra.jsp").forward(request, response); // Changed JSP path to phieutra.jsp
    }

    private boolean checkMaSach(int maSach, int maPT) {
        int mapm = pt_BUS.searchByMaPT(maPT).getMaPM();
        for (CTPM_DTO i : ctpm_BUS.searchByMaPM(mapm)) {
            if (i.getMaSach() == maSach) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTrangThaiSach(int maSach, int maPT) {
        int mapm = pt_BUS.searchByMaPT(maPT).getMaPM();
        for (CTPM_DTO i : ctpm_BUS.searchByMaPM(mapm)) {
            if (i.getMaSach() == maSach && i.getTrangthai().equals("đang mượn")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkMaVach(String maVach, String maSach) {
        for (CTSach_DTO i : cts_BUS.searchCTSachByMaSach(maSach)) {
            if (i.getMaVach().equals(maVach)) {
                return true;
            }
        }
        return false;
    }

    private String checkMaVachLoi(String maSach, String maVachLoi) {
        List<String> listMVL = Arrays.asList(maVachLoi.split(","));
        String missing_maVach = "";
        for (String i : listMVL) {
            if (!checkMaVach(i, maSach)) {
                missing_maVach += i + ",";
            }
        }
        return missing_maVach;
    }

    private int returnSoLuong(int maPT, int maSach) {
        int mapm = pt_BUS.searchByMaPT(maPT).getMaPM();
        for (CTPM_DTO i : ctpm_BUS.searchByMaPM(mapm)) {
            if (i.getMaSach() == maSach) {
                return i.getSoLuong();
            }
        }
        return -1;
    }

    private int countSoLuong_TheoMaSach(int maPT, int maSach) {
        int t = 0;
        for (CTPT_DTO i : ctpt_BUS.searchByMaPT(maPT)) {
            if (i.getMaSach() == maSach) {
                t += i.getSoLuong();
            }
        }
        return t;
    }

    public boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPT, String maSach, String maVachLoi, String ngayTra, String soLuong) throws IOException {
        if (maPT.trim().isEmpty() || maPT == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống vui lòng nhập\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        try {
            Integer.parseInt(maPT);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (pt_BUS.searchByMaPT(Integer.parseInt(maPT)) == null) { // Changed to searchByMaPT
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại vui lòng nhập  lại\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (maSach.trim().isEmpty() || maSach == null) {
            response.getWriter().write("{\"thongbao\": \"Mã sách không được để trống vui lòng nhập\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        try {
            Integer.parseInt(maSach);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (!checkMaSach(Integer.parseInt(maSach), Integer.parseInt(maPT))) { // Changed to searchByMaPT
            response.getWriter().write("{\"thongbao\": \"Mã sách bạn nhập không tồn tại trong phiếu mượn của bạn vui lòng nhập  lại\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (!checkTrangThaiSach(Integer.parseInt(maSach), Integer.parseInt(maPT))) { // Changed to searchByMaPT
            response.getWriter().write("{\"thongbao\": \"Mã sách bạn nhập đã được trả đủ  vui lòng nhập  lại\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (!maVachLoi.trim().isEmpty() && !checkMaVachLoi(maSach, maVachLoi).equals("")) {
            response.getWriter().write("{\"thongbao\": \"Những mã vạch "
                    + checkMaVachLoi(maSach, maVachLoi)
                    + " không phải là mã vạch của mã sách vui lòng nhập lại"
                    + maSach + "\", \"hopLe\": false}");
            return false;
        }
        if (ngayTra == null || ngayTra.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Ngày trả không được để trống\", \"hopLe\": false}");
            return false;
        }
        if (LocalDate.parse(ngayTra).isBefore(pm_BUS.searchByMaPM(pt_BUS.searchByMaPT(Integer.parseInt(maPT)).getMaPM()).getNgayLap())) {
            response.getWriter().write("{\"thongbao\": \"Bạn đã nhập ngày trả trước ngày bạn mượn sách vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        if (soLuong.trim().isEmpty() || soLuong == null) {
            response.getWriter().write("{\"thongbao\": \"Số lượng không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(soLuong);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Số lượng không phải số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        int slcl = returnSoLuong(Integer.parseInt(maPT), Integer.parseInt(maSach)) - countSoLuong_TheoMaSach(Integer.parseInt(maPT), Integer.parseInt(maSach));
        if (Integer.parseInt(soLuong) <= 0 || Integer.parseInt(soLuong) > slcl) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập số lượng hợp lệ, giá trị từ 1 đến "
                    + slcl + "\", \"hopLe\": false}");
            return false;
        }
        if (!maVachLoi.trim().isEmpty() && maVachLoi.split(",").length > Integer.parseInt(soLuong)) {
            response.getWriter().write("{\"thongbao\": \"Bạn nhập số lương trả là  "
                    + soLuong
                    + "nhưng bạn nhập "
                    + maVachLoi.split(",").length
                    + " nhiều hơn số lượng trả vui lòng nhập lại"
                    + "\", \"hopLe\": false}");
            return false;
        }

        return true;
    }

    private boolean checkDelete(HttpServletRequest request, HttpServletResponse response, String maPT, String maSach, String ngayTra) throws IOException {
        if (maPT.isEmpty() || maPT == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống vui lòng nhập để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (pt_BUS.searchByMaPT(Integer.parseInt(maPT)) == null) { // Changed to searchByMaPT
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại vui lòng chọn lại để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (maSach.isEmpty() || maSach == null) {
            response.getWriter().write("{\"thongbao\": \"Mã sách không được để trống vui lòng nhập để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (ngayTra.isEmpty() || ngayTra == null) {
            response.getWriter().write("{\"thongbao\": \"Vui lòng nhập ngày trả để xóa\", \"hopLe\": false}"); // Changed from Mã phiếu mượn to Mã phiếu trả
            return false;
        }
        if (ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra)) == null) { // Changed to searchByMaPT_MaSach
            response.getWriter().write("{\"thongbao\": \"ctpt không tồn tại vui lòng chọn ctpt trên table để sửa\", \"hopLe\": false}"); // Changed from ctpn to ctpt
            return false;
        }
        return true;
    }

    private boolean addCTPT(String maPT, String maSach, String maVachLoi, String ngayTra, String soLuong) {
        CTPT_DTO ctpt = new CTPT_DTO(); // Changed to CTPT_DTO
        ctpt.setMaPT(Integer.parseInt(maPT)); // Changed to MaPT
        ctpt.setMaSach(Integer.parseInt(maSach));
        ctpt.setSoLuong(Integer.parseInt(soLuong));
        ctpt.setMaVachLoi(maVachLoi);
        ctpt.setNgayTra(LocalDate.parse(ngayTra));
        return ctpt_BUS.addCTPT(ctpt); // Changed to addCTPT
    }

    private boolean updateCTPT(String maPT, String maSach, String maVachLoi, String ngayTra, String soLuong) {
        CTPT_DTO ctpt = new CTPT_DTO(); // Changed to CTPT_DTO
        ctpt.setMaPT(Integer.parseInt(maPT)); // Changed to MaPT
        ctpt.setMaSach(Integer.parseInt(maSach));
        ctpt.setSoLuong(Integer.parseInt(soLuong));
        ctpt.setMaVachLoi(maVachLoi);
        ctpt.setNgayTra(LocalDate.parse(ngayTra));
        return ctpt_BUS.updateCTPT(ctpt); // Changed to updateCTPT
    }

    private PhieuPhat_DTO searchPP(int maPT) {
        for (PhieuPhat_DTO i : pp_BUS.getList()) {
            if (i.getMaPT() == maPT) {
                return i;
            }
        }
        return null;
    }

    private boolean Update_When_Delete(int maPT, String maVachLoi) {
        if (maVachLoi != null || !maVachLoi.equals("")) {
            PhieuPhat_DTO pp = searchPP(maPT);
            if (pp != null) {
                for (String i : maVachLoi.split(",")) {
                    CTPP_DTO ctpp = ctpp_BUS.searchByMaPP_MaVach(pp.getMaPP(), i);
                    if (ctpp != null) {
                        CTSach_DTO ctsUpdate = cts_BUS.searchCTSachByMaVach(i).get(0);
                        String tts = ctsUpdate.getTinhTrangSach();
                        for (String j : ctpp.getLiDo()) {
                            tts = tts.replace(j, "").replaceAll(",\\s*,", ",").trim();
                        }
                        tts = tts.substring(0, tts.length());
                        ctsUpdate.setTinhTrangSach(tts);
                        if (!cts_BUS.updateCTSach(ctsUpdate)
                                || pp_BUS.updateTT(pp.getMaPP(), pp.getTongTien() - ctpp.getTien())
                                || !ctpp_BUS.deleteCTPP(ctpp.getMaPP(), ctpp.getMaVach())) {
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String action = request.getParameter("action");
        System.out.println("action: " + action);

        String maPT = request.getParameter("maPT");
        System.out.println("maPT: " + maPT);

        String maSach = request.getParameter("maSach");
        System.out.println("maSach: " + maSach);

        String maVachLoi = request.getParameter("maVachLoi");
        System.out.println("maVachLoi: " + maVachLoi);

        String ngayTra = request.getParameter("ngayTra");
        System.out.println("ngayTra: " + ngayTra);

        String soLuong = request.getParameter("soLuong");
        System.out.println("soLuong: " + soLuong);

        String optionSearch = request.getParameter("optionSearch");
        System.out.println("optionSearch: " + optionSearch);

        String valueSearch = request.getParameter("valueSearch");
        System.out.println("valueSearch: " + valueSearch);
        Sach_DTO sach;
        CTPM_DTO ctpm;
        PhieuTra_DTO pt;
        int tongsl_sachtra;
        switch (action) {
            case "addCTPT": // Changed action name
                if (!checkInfor(request, response, maPT, maSach, maVachLoi, ngayTra, soLuong)) {
                    return;
                }
                if (ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Đã tồn tại ctpt với mã sách và và phiếu trả bạn nhập\", \"hopLe\": false}");
                    return;
                }
                sach = sach_BUS.timSachTheoMaSach(maSach).get(0);
                pt = pt_BUS.searchByMaPT(Integer.parseInt(maPT));
                ctpm = ctpm_BUS.searchByMaPM_MaSach(pt.getMaPM(), Integer.parseInt(maSach));
                sach.setSoLuong(sach.getSoLuong() + Integer.parseInt(soLuong));
                tongsl_sachtra = countSoLuong_TheoMaSach(Integer.parseInt(maPT), Integer.parseInt(maSach)) + Integer.parseInt(soLuong);
                if (addCTPT(maPT, maSach, maVachLoi, ngayTra, soLuong)
                        && sach_BUS.suaSach(sach)) {
                    if (ctpm.getSoLuong() == tongsl_sachtra) {
                        ctpm.setTrangthai("đã trả");
                        ctpm_BUS.updateCTPM(ctpm);
                    }
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPT thành công\", \"hopLe\": true}"); // Changed message
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPT thất bại\", \"hopLe\": false}"); // Changed message
                }
                break;

            case "updateCTPT": // Changed action name
                if (!checkInfor(request, response, maPT, maSach, maVachLoi, ngayTra, soLuong)) {
                    return;
                }

                if (ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra)) == null) {
                    response.getWriter().write("{\"thongbao\": \"ctpt không tồn tại\", \"hopLe\": false}");
                }
                int slc = ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra)).getSoLuong();
                sach = sach_BUS.timSachTheoMaSach(maSach).get(0);
                pt = pt_BUS.searchByMaPT(Integer.parseInt(maPT));
                ctpm = ctpm_BUS.searchByMaPM_MaSach(pt.getMaPM(), Integer.parseInt(maSach));
                sach.setSoLuong(sach.getSoLuong() + Integer.parseInt(soLuong) - slc);
                tongsl_sachtra = countSoLuong_TheoMaSach(Integer.parseInt(maPT), Integer.parseInt(maSach)) + Integer.parseInt(soLuong) - slc;
                if (updateCTPT(maPT, maSach, maVachLoi, ngayTra, soLuong)
                        && sach_BUS.suaSach(sach)) {
                    if (ctpm.getSoLuong() == tongsl_sachtra) {
                        ctpm.setTrangthai("đã trả");
                        ctpm_BUS.updateCTPM(ctpm);
                    } else {
                        ctpm.setTrangthai("đang mượn");
                        ctpm_BUS.updateCTPM(ctpm);
                    }
                    response.getWriter().write("{\"thongbao\": \"Update CTPT thành công\", \"hopLe\": true}"); // Changed message
                } else {
                    response.getWriter().write("{\"thongbao\": \"Update CTPT thất bại\", \"hopLe\": false}"); // Changed message
                }
                break;

            case "deleteCTPT": // Changed action name
                if (!checkDelete(request, response, maPT, maSach, ngayTra)) {
                    return;
                }
                sach = sach_BUS.timSachTheoMaSach(maSach).get(0);
                pt = pt_BUS.searchByMaPT(Integer.parseInt(maPT));
                ctpm = ctpm_BUS.searchByMaPM_MaSach(pt.getMaPM(), Integer.parseInt(maSach));
                ctpm.setTrangthai("đang mượn");
                CTPT_DTO ctpt=ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra));
                sach.setSoLuong(sach.getSoLuong() - ctpt_BUS.searchByMaPT_MaSach(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra)).getSoLuong());
                if (Update_When_Delete(ctpt.getMaPT(),ctpt.getMaVachLoi())
                        &&ctpt_BUS.deleteCTPT(Integer.parseInt(maPT), Integer.parseInt(maSach), LocalDate.parse(ngayTra))
                        && sach_BUS.suaSach(sach)
                        && ctpm_BUS.updateCTPM(ctpm)) {
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}"); // Changed message
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}"); // Changed message
                }
                break;

            case "searchCTPT": // Changed action name
                if (valueSearch == null || valueSearch.trim().isEmpty() && !optionSearch.equals("Mã vạch lỗi")) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder result = ctpt_BUS.searchCTPT(optionSearch, valueSearch); // Changed method call
                if (result.length() > 2) {
                    response.getWriter().write("{\"thongbao\": \"Tìm kiếm thành công\", \"hopLe\": false, \"results\": " + result.toString() + "}"); // Changed message
                } else {
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu trả bạn cần tìm\", \"hopLe\": false}"); // Changed message
                }
                break;
            case "finishCTPT":
                response.getWriter().write("{\"thongbao\": \"\", \"hopLe\": true}");
                break;
            default:
                response.getWriter().write("{\"thongbao\": \"Không thấy hoạt động\", \"hopLe\": false}");
                return;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
