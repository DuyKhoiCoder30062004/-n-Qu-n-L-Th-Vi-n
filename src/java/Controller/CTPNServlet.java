package Controller;

import java.io.IOException;
import java.util.ArrayList;

import BUS.CTPN_BUS;
import BUS.CTSach_BUS;
import BUS.PhieuNhap_BUS;
import BUS.Sach_BUS;
import DTO.CTPN_DTO;
import DTO.CTSach_DTO;
import DTO.PhieuNhap_DTO;
import DTO.Sach_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;

@WebServlet(name = "CTPNServlet", urlPatterns = {"/ctpn"})
public class CTPNServlet extends HttpServlet {

    private PhieuNhap_BUS pn_BUS = new PhieuNhap_BUS();
    private CTPN_BUS ctpn_BUS = new CTPN_BUS();
    private Sach_BUS sach_BUS = new Sach_BUS();
    private CTSach_BUS cts_BUS = new CTSach_BUS();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<PhieuNhap_DTO> listPN = pn_BUS.getList();
        ArrayList<CTPN_DTO> listCTPN = ctpn_BUS.getList();
        request.setAttribute("listCTPN", listCTPN);
        request.setAttribute("listPN", listPN);
        request.getRequestDispatcher("/WEB-INF/gui/phieunhap.jsp").forward(request, response);
    }

    private String checkMaVach_Exit(String listMaVach, int maSach) {
        String rs = "";
        for (String i : listMaVach.split(",")) {
            if (cts_BUS.searchCTSachByMaVach(i).get(0) != null
                    && cts_BUS.searchCTSachByMaVach(i.trim()).get(0).getMaSach() == maSach) {
                rs += i + ",";
            }
        }
        return rs;
    }

    public boolean checkInfor(HttpServletRequest request, HttpServletResponse response,
            String maPN, String maSach, String listMaVach, String donGia) throws IOException {
        if (maPN == null || maPN.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập không được để trống\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maPN);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu nhập phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại\", \"hopLe\": false}");
            return false;
        }
        if (maSach == null || maSach.trim().isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập mã sách bạn muốn nhập\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(maSach);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Mã sách phải là số nguyên\", \"hopLe\": false}");
            return false;
        }
        if (sach_BUS.timSachTheoMaSach(Integer.parseInt(maSach)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã sách bạn nhập không tồn tại\", \"hopLe\": false}");
            return false;
        }
        if (listMaVach == null || listMaVach.isEmpty()) {
            response.getWriter().write("{\"thongbao\": \"Mời bạn nhập những mã vạch của sách bạn muốn nhập\", \"hopLe\": false}");
            return false;
        }
        try {
            Integer.parseInt(donGia);
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"thongbao\": \"Đơn giả không phải số nguyên vui lòng nhập lại\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean checkDelete(HttpServletRequest request, HttpServletResponse response,
            String maPN, String maSach) throws IOException {
        if (maPN.isEmpty() || maPN == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không được để trống vui lòng nhập để xóa\", \"hopLe\": false}");
            return false;
        }
        if (pn_BUS.searchByMaPN(Integer.parseInt(maPN)) == null) {
            response.getWriter().write("{\"thongbao\": \"Mã phiếu mượn không tồn tại vui lòng chọn lại để xóa\", \"hopLe\": false}");
            return false;
        }
        if (ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach)) == null) {
            response.getWriter().write("{\"thongbao\": \"ctpm không tồn tại vui lòng chọn ctpm trên table để sửa\", \"hopLe\": false}");
            return false;
        }
        return true;
    }

    private boolean addCTPN(String maPN, String maSach, String listMaVach, String donGia) {
        boolean flag;
        CTPN_DTO ctpn = new CTPN_DTO();
        ctpn.setMaPN(Integer.parseInt(maPN));
        ctpn.setMaSach(Integer.parseInt(maSach));
        ctpn.setMaVach(new ArrayList<>(Arrays.asList(listMaVach.split(","))));
        ctpn.setDonGia(Integer.parseInt(donGia));
        ctpn.setSoLuong(listMaVach.split(",").length);
        flag = ctpn_BUS.addCTPN(ctpn);
        if (flag == true) {
            for (String i : listMaVach.split(",")) {
                CTSach_DTO cts = new CTSach_DTO();
                cts.setMaSach(Integer.parseInt(maSach));
                cts.setMaVach(i.trim());
                cts.setTinhTrangSach("");
                if (!cts_BUS.addCTSach_when_deleteSach(cts)) {
                    return false;
                }

            }
            Sach_DTO sach=sach_BUS.timSachTheoMaSach(ctpn.getMaSach());
            sach.setSoLuong(sach.getSoLuong()+ctpn.getMaVach().size());
            PhieuNhap_DTO pn = pn_BUS.searchByMaPN(ctpn.getMaPN());
            pn.setTongTien(pn.getTongTien() + (ctpn.getDonGia() * ctpn.getSoLuong()));
            pn.setTongSL(pn.getTongSL() + ctpn.getSoLuong());
            if (!pn_BUS.updatePN(pn) || !sach_BUS.suaSach(sach)) {
                return false;
            }
        }

        return flag;
    }

    private boolean updateCTPN(String maPN, String maSach, String listMaVach, String donGia) {
        boolean flag;
        CTPN_DTO ctpn_cu = ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach));
        CTPN_DTO ctpn = new CTPN_DTO();
        ctpn.setMaPN(Integer.parseInt(maPN));
        ctpn.setMaSach(Integer.parseInt(maSach));
        ctpn.setMaVach(new ArrayList<>(Arrays.asList(listMaVach.split(","))));
        ctpn.setDonGia(Integer.parseInt(donGia));
        ctpn.setSoLuong(listMaVach.split(",").length);
        flag = ctpn_BUS.updateCTPN(ctpn);
        if (flag == true) {
            for (String i : listMaVach.split(",")) {
                CTSach_DTO cts = new CTSach_DTO();
                cts.setMaSach(Integer.parseInt(maSach));
                cts.setMaVach(i.trim());
                cts.setTinhTrangSach("");
                if (!cts_BUS.addCTSach_when_deleteSach(cts)) {
                    return false;
                }
            }
            Sach_DTO sach=sach_BUS.timSachTheoMaSach(ctpn.getMaSach());
            sach.setSoLuong(sach.getSoLuong()+ctpn.getMaVach().size());
            PhieuNhap_DTO pn = pn_BUS.searchByMaPN(ctpn.getMaPN());
            pn.setTongTien(pn.getTongTien() + (ctpn.getDonGia() * ctpn.getSoLuong()) - (ctpn_cu.getDonGia() * ctpn_cu.getSoLuong()));
            pn.setTongSL(pn.getTongSL() + ctpn.getSoLuong() - ctpn_cu.getSoLuong());
            if (!pn_BUS.updatePN(pn) || !sach_BUS.suaSach(sach)) {
                return false;
            }
        }

        return flag;
    }

    private boolean deleteCTPN(String maPN, String maSach) {
        boolean flag;
        CTPN_DTO ctpn_cu = ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach));
        flag = ctpn_BUS.deleteCTPN(ctpn_cu.getMaPN(), ctpn_cu.getMaSach());
        if (flag) {
            for (String i : ctpn_cu.getMaVach()) {
                if (!cts_BUS.deleteCTSach_as_maVach_and_maSach(i, Integer.parseInt(maSach))) {
                    return false;
                }

            }
            Sach_DTO sach=sach_BUS.timSachTheoMaSach(ctpn_cu.getMaSach());
            sach.setSoLuong(sach.getSoLuong()- ctpn_cu.getMaVach().size());
            PhieuNhap_DTO pn = pn_BUS.searchByMaPN(ctpn_cu.getMaPN());
            pn.setTongTien(pn.getTongTien() - (ctpn_cu.getDonGia() * ctpn_cu.getSoLuong()));
            pn.setTongSL(pn.getTongSL() - ctpn_cu.getSoLuong());
            if (!pn_BUS.updatePN(pn) || !sach_BUS.suaSach(sach)) {
                return false;
            }
        }

        return flag;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        String action = request.getParameter("action");
        String maPN = request.getParameter("maPN");
        String maSach = request.getParameter("maSach");
        String listMaVach = request.getParameter("listMaVach");
        String soLuong = request.getParameter("soLuong");
        String donGia = request.getParameter("donGia");
        String optionSearch = request.getParameter("optionSearch");
        String valueSearch = request.getParameter("valueSearch");

        switch (action) {
            case "addCTPN":
                if (!checkInfor(request, response, maPN, maSach, listMaVach, donGia)) {
                    return;
                }
                if (!checkMaVach_Exit(listMaVach, Integer.parseInt(maSach)).equals("")) {
                    response.getWriter().write("{\"thongbao\": \"Những mã vạch sau '" + checkMaVach_Exit(listMaVach, Integer.parseInt(maSach)) + "' đã tồn tại, vui lòng nhập lại\", \"hopLe\": false}");
                    return;
                }
                if (ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach)) != null) {
                    response.getWriter().write("{\"thongbao\": \"Chi tiết phiếu nhập với mã phiêu nhập và mã sách bạn nhập đã tồn tại\", \"hopLe\": false}");
                    return;
                }
                if (addCTPN(maPN, maSach, listMaVach, donGia)) {
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPN thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Thêm CTPN thất bại\", \"hopLe\": false}");
                }
            case "updateCTPN":
                if (!checkInfor(request, response, maPN, maSach, listMaVach, donGia)) {
                    return;
                }

                if (ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach)) == null) {
                    response.getWriter().write("{\"thongbao\": \"ctpn không tồn tại \" \"hopLe\": false}");
                }
                //Xóa các ctsach cũ trước khi sửa
                CTPN_DTO ctpncu = ctpn_BUS.searchByMaPN_MaSach(Integer.parseInt(maPN), Integer.parseInt(maSach));
                for (String i : ctpncu.getMaVach()) {
                    if(!cts_BUS.deleteCTSach_as_maVach_and_maSach(i.trim(), ctpncu.getMaSach()))
                    {
                        response.getWriter().write("{\"thongbao\": \"có lỗi ở xóa các ct sách trước khi update lại \" \"hopLe\": false}");
                        return;
                    }
                }
                Sach_DTO sach=sach_BUS.timSachTheoMaSach(ctpncu.getMaSach());
                sach.setSoLuong(sach.getSoLuong()-ctpncu.getMaVach().size());
                if(!sach_BUS.suaSach(sach))
                {
                    response.getWriter().write("{\"thongbao\": \"có lỗi ở cập nhật lại số lượng của sách trước khi update lại \" \"hopLe\": false}");
                    return;
                }
                if (!checkMaVach_Exit(listMaVach, Integer.parseInt(maSach)).equals("")) {
                    response.getWriter().write("{\"thongbao\": \"Những mã vạch sau '" + checkMaVach_Exit(listMaVach, Integer.parseInt(maSach)) + "' đã tồn tại, vui lòng nhập lại\", \"hopLe\": false}");
                    return;
                }
                if (updateCTPN(maPN, maSach, soLuong, donGia)) {
                    response.getWriter().write("{\"thongbao\": \"Update CTPN thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Update CTPN thất bại\", \"hopLe\": false}");
                }
                break;
            case "deleteCTPN":
                if (!checkDelete(request, response, maPN, maSach)) {
                    return;
                }
                if (deleteCTPN(maPN, maSach)) {
                    response.getWriter().write("{\"thongbao\": \"Xóa thành công\", \"hopLe\": true}");
                } else {
                    response.getWriter().write("{\"thongbao\": \"Xóa thất bại\", \"hopLe\": false}");
                }
                break;
            case "searchCTPN":
                if (valueSearch == null || valueSearch.trim().isEmpty()) {
                    response.getWriter().write("{\"thongbao\": \"Vui lòng nhập thông tin bạn muốn tìm kiếm\", \"hopLe\": false}");
                    return;
                }
                StringBuilder result = ctpn_BUS.searchCTPN(optionSearch, valueSearch);
                System.out.print(result);
                if (result.length() > 2) {
                    // Có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"tìm kiếm thành công\", \"hopLe\": false, \"results\": " + result.toString() + "}");
                } else {
                    // Không có dữ liệu
                    response.getWriter().write("{\"thongbao\": \"Không có phiếu mượn bạn cần tìm\", \"hopLe\": false}");
                }
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
