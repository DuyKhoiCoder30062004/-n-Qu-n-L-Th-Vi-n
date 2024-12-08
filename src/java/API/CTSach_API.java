/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package API;

import java.io.IOException;
import java.io.PrintWriter;
import BUS.CTSach_BUS;
import BUS.Sach_BUS;
import Common.Constant;
import DTO.CTSach_DTO;
import Payload.ResponseDataSach;
import DTO.CTPN_DTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name="CTSach_API", urlPatterns={Constant.URL_CTSACH_UPDATE,Constant.URL_CTSACH_DELETE})
public class CTSach_API extends HttpServlet {
    private CTSach_BUS ctSach_BUS = new CTSach_BUS();
    private Gson gson = new Gson();   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // processRequest(request, response);
        String urlPath = request.getServletPath();
        switch (urlPath) {
            case Constant.URL_CTSACH_UPDATE:
                suaCTSach(request, response);
                break;
            case Constant.URL_CTSACH_DELETE:
            xoaCTSach(request, response);
            break;
            default:
                break;
        }
    }
    private void suaCTSach(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String maVach = request.getParameter("maVach");
        String maSach = request.getParameter("maSach");
        int maSachInt = Integer.parseInt(maSach);
        String tinhTrangSach = request.getParameter("tinhTrangSach");
        Map<String, String> errorsUpdate = new HashMap<>(); 
        if(!ctSach_BUS.checkBooksDetail_as_maVach(maVach, maSachInt)) {
            errorsUpdate.put("maVach", "Mã vạch không tồn tại");
        }
        if(!errorsUpdate.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ResponseDataSach data = new ResponseDataSach();
            data.setIsSuccess(false);
            data.setDescription("");
            data.setErrors(errorsUpdate);
            data.setData("");
            String jsonResponse = gson.toJson(data);
            // Gửi phản hồi JSON về client
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);
        }
        else {
            CTSach_DTO ctSach_DTO = new CTSach_DTO();
            ctSach_DTO.setMaVach(maVach);
            ctSach_DTO.setMaSach(maSachInt);
            ctSach_DTO.setTinhTrangSach(tinhTrangSach);
            boolean result = ctSach_BUS.updateCTSach(ctSach_DTO);
            ResponseDataSach data = new ResponseDataSach();
            data.setIsSuccess(result);
            data.setDescription("");
            data.setData(ctSach_DTO);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = gson.toJson(data);
            // Gửi phản hồi JSON về client
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);

        }
    }
    private void xoaCTSach(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String maVach = request.getParameter("maVach");
        String maSach = request.getParameter("maSach");
        int maSachInt = Integer.parseInt(maSach);
        Map<String, String> errorsUpdate = new HashMap<>(); 
        if(!ctSach_BUS.checkBooksDetail_as_maVach(maVach, maSachInt)) {
            errorsUpdate.put("maVach", "Mã vạch không tồn tại");
        }
        if(!errorsUpdate.isEmpty()) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ResponseDataSach data = new ResponseDataSach();
            data.setIsSuccess(false);
            data.setDescription("");
            data.setErrors(errorsUpdate);
            data.setData("");
            String jsonResponse = gson.toJson(data);
            // Gửi phản hồi JSON về client
            PrintWriter out = response.getWriter();
            out.println(jsonResponse);
        }
        else {
            ArrayList<CTPN_DTO> listCTPN = ctSach_BUS.getListCTPN_as_maSach(maSachInt);
            if(listCTPN != null && !listCTPN.isEmpty()) {
                for(CTPN_DTO ctpn : listCTPN) {
                    ctSach_BUS.deleteCTPN_as_maSach(maSachInt);
                }
            }
            boolean result = ctSach_BUS.deleteCTSach_as_maVach_and_maSach(maVach, maSachInt);
            if(result) {
                if(listCTPN != null && !listCTPN.isEmpty()) {
                    for(CTPN_DTO ctpn : listCTPN) {
                        for(int i = 0; i < ctpn.getMaVach().size(); i++) {
                            if(ctpn.getMaVach().get(i).equals(maVach))
                            {
                                ctpn.getMaVach().set(i,"-" + maVach); 
                            }
                        }
                        
                        ctSach_BUS.addCTPN_when_DeleteCTSach(ctpn);
                    }
                }
            }
            else {
                if(listCTPN != null && !listCTPN.isEmpty()) {
                    for(CTPN_DTO ctpn : listCTPN) {
                        ctSach_BUS.addCTPN_when_DeleteCTSach(ctpn);
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
        
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
