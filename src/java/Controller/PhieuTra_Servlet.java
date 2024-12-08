package Controller;

import BUS.CTPM_BUS;
import BUS.CTPP_BUS;
import BUS.CTPT_BUS;
import BUS.CTSach_BUS;
import BUS.PhieuMuon_BUS;
import BUS.PhieuPhat_BUS;
import BUS.PhieuTra_BUS;
import BUS.Sach_BUS;
import DTO.CTPM_DTO;
import DTO.CTPP_DTO;
import DTO.CTPT_DTO;
import DTO.CTSach_DTO;
import DTO.PhieuMuon_DTO;
import DTO.PhieuPhat_DTO;
import DTO.PhieuTra_DTO;
import DTO.Sach_DTO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "PhieuTra_Servlet", urlPatterns = {"/phieutra"})
public class PhieuTra_Servlet extends HttpServlet {

    private PhieuTra_BUS pt_BUS = new PhieuTra_BUS();
    private CTPT_BUS ctpt_BUS = new CTPT_BUS();
    private PhieuMuon_BUS pm_BUS = new PhieuMuon_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();
    private CTPM_BUS ctpm_BUS = new CTPM_BUS();
    private PhieuPhat_BUS pp_BUS = new PhieuPhat_BUS();
    private CTPP_BUS ctpp_BUS = new CTPP_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuTra_DTO> listPT = pt_BUS.getListPhieuTra();
        List<CTPT_DTO> listCTPT = ctpt_BUS.getList();
        ArrayList<Sach_DTO> listSach = sach_BUS.getListSach();
        ArrayList<CTSach_DTO> listCTS = cts_BUS.getList();
        ArrayList<PhieuMuon_DTO> listPM = pm_BUS.getList();
        request.setAttribute("listCTPT", listCTPT);
        request.setAttribute("listPT", listPT);
        request.setAttribute("listSach", listSach);
        request.setAttribute("listCTS", listCTS);
        request.setAttribute("listPM", listPM);
        request.getRequestDispatcher("/WEB-INF/gui/phieutra.jsp").forward(request, response);
    }

    private boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPhieu, String maPM)
            throws IOException {
        // Validate Ma Phieu Tra
        if (maPhieu == null || maPhieu.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPhieu);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu trả phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (maPM == null || maPM.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPM);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (pm_BUS.searchByMaPM(Integer.parseInt(maPM)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn này chưa tồn tại vui lòng kiểm tra lại\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean addPT(String maPhieu, String maPM, String maNV, String tongSL) {
        PhieuTra_DTO pt = new PhieuTra_DTO();
        pt.setMaPT(Integer.parseInt(maPhieu));
        pt.setMaPM(Integer.parseInt(maPM));
        pt.setMaNV(Integer.parseInt(maNV));
        pt.setTongSL(Integer.parseInt(tongSL));
        return pt_BUS.addPhieuTra(pt);
    }

    private boolean updatePT(String maPhieu, String maPM, String maNV, String tongSL) {
        PhieuTra_DTO pt = new PhieuTra_DTO();
        pt.setMaPT(Integer.parseInt(maPhieu));
        pt.setMaPM(Integer.parseInt(maPM));
        pt.setMaNV(Integer.parseInt(maNV));
        pt.setTongSL(Integer.parseInt(tongSL));
        return pt_BUS.updatePhieuTra(pt);
    }

    private PhieuPhat_DTO searchPP_ByMaPT(int mapt) {
        for (PhieuPhat_DTO i : pp_BUS.getList()) {
            if (i.getMaPT() == mapt) {
                return i;
            }
        }
        return null;
    }

    private boolean Delete_UpdateCacPhieu(int mapt) {
        PhieuTra_DTO pt = pt_BUS.searchByMaPT(mapt);
        PhieuPhat_DTO pp = searchPP_ByMaPT(mapt);
        if (pp != null) {
            if (!ctpp_BUS.searchByMaPP(pp.getMaPP()).isEmpty()) {
                for (CTPP_DTO i : ctpp_BUS.searchByMaPP(pp.getMaPP())) {
                    CTSach_DTO cts = cts_BUS.searchCTSachByMaVach(i.getMaVach()).get(0);
                    String tts = cts.getTinhTrangSach();
                    for (String j : i.getLiDo()) {
                        tts = tts.replace(j, "").replaceAll(",\\s*,", ",").trim();
                    }
                    tts = tts.substring(0, tts.length());
                    cts.setTinhTrangSach(tts);
                    if (!cts_BUS.updateCTSach(cts)) {
                        return false;
                    }
                }
                if (!ctpp_BUS.deleteByMaPP(pp.getMaPP())) {
                    return false;
                }
            }
            if (!pp_BUS.deletePP(pp.getMaPP())) {
                return false;
            }
        }
        if (!ctpt_BUS.searchByMaPT(mapt).isEmpty()) {
            for (CTPT_DTO i : ctpt_BUS.searchByMaPT(mapt)) {
                Sach_DTO sach = sach_BUS.timSachTheoMaSach(String.valueOf(i.getMaSach())).get(0);
                sach.setSoLuong(sach.getSoLuong() - i.getSoLuong());
                CTPM_DTO ctpm = ctpm_BUS.searchByMaPM_MaSach(pt.getMaPM(), i.getMaSach());
                ctpm.setTrangthai("đang mượn");
                if (!sach_BUS.suaSach(sach)
                        || !ctpm_BUS.updateCTPM(ctpm)) {
                    return false;
                }
            }
            if (!ctpt_BUS.deleteCTPT_ByMaPT(mapt)) {
                return false;
            }
        }

        return true;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        System.out.println("Action: " + action);

        String maPhieu = request.getParameter("maPhieu");
        System.out.println("Mã phiếu: " + maPhieu);

        String maPM = request.getParameter("maPM");
        System.out.println("Mã PM: " + maPM);

        String maNV = request.getParameter("maNV");
        System.out.println("Mã NV: " + maNV);

        String tongSL = request.getParameter("tongSL");
        System.out.println("Tổng SL: " + tongSL);

        String optionSearch = request.getParameter("optionSearch");
        System.out.println("Option Search: " + optionSearch);

        String valueSearch = request.getParameter("valueSearch");
        System.out.println("Value Search: " + valueSearch);

        String namePath = request.getParameter("nameFileExcel");
        System.out.println("Name Path: " + namePath);

        switch (action) {
            case "add":
                if (!checkInfor(request, response, maPhieu, maPM)) {
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu trả đã tồn tại, vui lòng nhập lại mã phiếu trả\", \"hopLe\": false}");
                    return;
                }
                if (addPT(maPhieu, maPM, maNV, tongSL)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm thất bại\", \"hopLe\": false}");
                }
                break;
            case "edit":
                if (!checkInfor(request, response, maPhieu, maPM)) {
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu trả để sửa trên table\", \"hopLe\": false}");
                    return;
                }
                if (updatePT(maPhieu, maPM, maNV, tongSL)) {
                    response.getWriter().write("{\"thongbao\": \"Sửa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Sửa thất bại\", \"hopLe\": false}");
                }
                break;
            case "delete":
                if (maPhieu == null || maPhieu.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn mã phiếu trả để xóa\", \"hopLe\": false}");
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu trả không tồn tại\", \"hopLe\": false}");
                    return;
                }
                if (Delete_UpdateCacPhieu(Integer.parseInt(maPhieu))
                        &&pt_BUS.deletePhieuTra(Integer.parseInt(maPhieu))) {

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
                StringBuilder[] result = pt_BUS.searchPM(optionSearch, valueSearch);
                System.out.println(result[0]);
                if (result[0].length() > 2) {
                    response.getWriter().write("{"
                            + "\"thongbao\": \"\", "
                            + "\"hopLe\": false, "
                            + "\"resultsPT\": " + result[0].toString() + ", "
                            + "\"resultsCTPT\": " + result[1].toString()
                            + "}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu trả bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            case "print":
                if (maPhieu == null || maPhieu.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng chọn 1 phiếu trả trên table hoặc nhập mã phiếu trên thanh input mã phiếu để in\", \"hopLe\": false}");
                    return;
                }
                try {
                    Integer.parseInt(maPhieu);
                } catch (NumberFormatException e) {
                    response.getWriter().write("{\"thongbao\": \"Mã phiếu phải là số nguyên vui lòng kiểm tra lại để in\", \"hopLe\": false}");
                    return;
                }
                if (pt_BUS.searchByMaPT(Integer.parseInt(maPhieu)) == null) {
                    response.getWriter().write("{\"thongbao\": \"Không tìm thấy phiếu bạn cần in vui lòng chọn lại phiếu hoặc nhập lại mã phiếu trên thanh input mã phiếu\", \"hopLe\": false}");
                    return;
                }
                try {
                    String htmlContent = pt_BUS.printPM(Integer.parseInt(maPhieu));
                    response.setContentType("text/html; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(htmlContent);
                    out.close();
                } catch (Exception e) {
                    response.getWriter().write("{\"thongbao\": \"In thất bại vui lòng làm lại\", \"hopLe\": false}");
                    e.printStackTrace();
                }
                break;
            case "export":
                if (namePath.isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập tên file excel để export\", \"hopLe\": false}");
                    return;
                }
                if (pt_BUS.exportExcel(namePath)) {
                    response.getWriter().write("{\"thongbao\": \"Export excel thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Export thất bại vui lòng kiểm tra lại\", \"hopLe\": false}");
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
