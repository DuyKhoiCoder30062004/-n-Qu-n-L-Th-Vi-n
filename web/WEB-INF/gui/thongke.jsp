<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="DTO.PhieuMuon_DTO" %>
<%@ page import="DTO.CTPP_DTO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="DTO.Nhanvien_DTO" %>
<%
    Nhanvien_DTO nv = (Nhanvien_DTO) session.getAttribute("nv");
    String tasks = (String) session.getAttribute("tasks");
    if (nv == null || nv.getChucVu().equals("admin")) {
        response.sendRedirect("/cnpm/login");
        return;
    }
    List<PhieuMuon_DTO> soLuongAndYear = (List<PhieuMuon_DTO>) request.getAttribute("soLuongAndYear");
    Map<Integer, Integer> yearToSumMap = new HashMap<>();
    for (PhieuMuon_DTO pm : soLuongAndYear) {
        String formattedDate = pm.getNgayLap().toString(); 
        int year =  pm.getNgayLap().getYear();
        int soLuong = pm.getTongSL();
        yearToSumMap.put(year, yearToSumMap.getOrDefault(year, 0) + soLuong);
    }
    StringBuilder chartData = new StringBuilder();
    for (Map.Entry<Integer, Integer> entry : yearToSumMap.entrySet()) {
        int year = entry.getKey();
        int totalBooks = entry.getValue();
        chartData.append("[").append(year).append(", ").append(totalBooks).append("], ");
    }
    if (chartData.length() > 0) {
        chartData.setLength(chartData.length() - 2);
    }
    
    List<CTPP_DTO> soTien_NamPhat = (List<CTPP_DTO>) request.getAttribute("soTien_NamPhat");
    Map<Integer, Integer> yearToSumMap1 = new HashMap<>();

    for (CTPP_DTO ctpp : soTien_NamPhat) {
        String formattedDate = ctpp.getNgayLap().toString(); 
        int year = ctpp.getNgayLap().getYear();  
        int tien = (int) ctpp.getTien();

        yearToSumMap1.put(year, yearToSumMap1.getOrDefault(year, 0) + tien);
    }

    StringBuilder chartData1 = new StringBuilder();
    for (Map.Entry<Integer, Integer> entry : yearToSumMap1.entrySet()) {
        int year = entry.getKey();
        int totalFine = entry.getValue();
        chartData1.append("[").append(year).append(", ").append(totalFine).append("], ");
    }
    if (chartData1.length() > 0) {
        chartData1.setLength(chartData1.length() - 2);
    }
    System.out.print("data của tiền phạt"+chartData1);

%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Menu Example</title>
        <style>
            /* Menu Styling */
            #menu {
                width: 12%;  /* Menu width */
                height: 97%;  /* Full height of the screen */
                background-color: #2C2F48;
                position: fixed;
                top: 3%;
                left: 0;
                overflow-y: auto; /* Allow scrolling if the content overflows */
                /*            padding-top: 3%;  Padding at the top */
            }

            .conponentMenu {
                width: 100%;
                height: 6%;
                margin-top: 2%;
                font-weight: bold;
                text-align: center;
                display: flex;
                align-items: center;
                justify-content: flex-start;
                border: #4A4D66;
                background-color: #4A4D66;
                color: #FFFFFF;
                border-bottom: 1px solid white;
                transition: background-color 0.3s ease;
            }

            .conponentMenu:hover {
                background-color: burlywood;
                cursor: pointer;
            }

            .conponentMenu img {
                width: 20%;
                height: auto;
                margin-right: 10%;
                margin-left: 10%;
            }

            #iconAndName {
                height: 14.5%;
                margin-top: 0;
                border-radius: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                font-weight: bold;
            }

            #iconAndName img {
                width: 40%;
                border-radius: 50%;
            }

            body, html {
                margin: 0;
                padding: 0;
                height: 100%;
                font-family: 'Times New Roman', Times, serif;
            }

            body {
                height: 100vh;
                overflow-y: auto;
                overflow-x: hidden;
            }

            #form {
                display: flex;
                flex-direction: column;
                height: 100vh;
                margin-left: 12%;  /* Adjust for the width of the menu */
            }

            #title {
                width: 100%;
                height: 3%;
                background-color:white;
                font-size: 16px;
                font-weight: bolder;
                position: fixed;
            }

            .thongke {
                font-size: 25px;
                margin-left: -50px;
                text-align: center;
                color: #064BFB;
                font-weight: bolder;
                text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
                /*            background-color: purple;
                            z-index: -1;*/
            }

            .parent-container {
                display: flex;
                justify-content: space-between;
                gap: 20px;
                padding: 20px;
                background-color: #f4f4f4;
                margin-top: 20px;
            }

            .child-div {
                display: flex;
                align-items: center;
                background-color: #fff;
                padding: 20px;
                width: 700px;
                height: 150px;
                border: 1px solid #ddd;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .icon {
                font-size: 20px;
                margin-right: 15px;
            }
            .icon1 {
                font-size: 43px;

            }

            .text-container {
                display: flex;
                flex-direction: column;
                align-items: flex-start;
            }

            .price {
                font-size: 18px;
                font-weight: bold;
                color: #2b9e4f;
            }

            .title {
                font-size: 16px;
                font-weight: 600;
                color: #333;
            }

            .chart-container {
                display: flex;
                justify-content: space-between;
                gap: 20px;
                margin-top: 2px;
                margin-left: 10px;
                margin-right: 5px;
                background-color: #999999;

                z-index: 1100;
            }

            #chart_div_1, #chart_div_2 {
                width: 100%;
                height: 400px;
                border: 1px solid #ddd;
                border-radius: 8px;
                background-color: #fff;
                padding: 20px;
            }
            #btnThongKe{
                background-color: #5A4E9B;
            }
            /* Responsive Menu for smaller screens */
            @media (max-width: 768px) {
                #menu {
                    width: 40%;
                }

                #form {
                    margin-left: 40%;  /* Adjust for mobile */
                }
            }
        </style>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            // Load Google Charts
            google.charts.load('current', {
                packages: ['corechart', 'line']
            });

            // Draw the first chart
            google.charts.setOnLoadCallback(drawChart1);
            function drawChart1() {
                var data = new google.visualization.DataTable();
                data.addColumn('number', 'Year');
                data.addColumn('number', 'Số sách mượn');
                data.addRows([
                    [2021, 0],
                    [2022, 0],
                    [2023, 0]
                ]);
                data.addRows([
            <%= chartData.toString() %>
                ]);

                var options = {
                    title: 'Số sách mượn qua các năm',
                    vAxis: {title: 'Năm'},

                    curveType: 'function',
                    legend: {position: 'bottom'},
                    tooltip: {
                        isHtml: true, // Set this to true if you want to use HTML formatting in tooltips
                        trigger: 'both'  // Show tooltip both when hovering over the data points and the axes
                    }

                };

                var chart = new google.visualization.BarChart(document.getElementById('chart_div_1'));
                chart.draw(data, options);
            }

            // Load Google Charts
            google.charts.load('current', {
                packages: ['corechart', 'line']
            });

            // Draw the second chart
            google.charts.setOnLoadCallback(drawChart2);
            function drawChart2() {
                var data = new google.visualization.DataTable();
                data.addColumn('number', 'Year');
                data.addColumn('number', 'Số tiền phạt');
                data.addRows([
                    [2021, 0],
                    [2022, 0],
                    [2023, 0]
                ]);
                data.addRows([
            <%= chartData1.toString() %>
                ]);

                var options = {
                    title: 'Số tiền phạt qua các năm',
                    vAxis: {title: 'Năm'},

                    curveType: 'function',
                    legend: {position: 'bottom'},
                    tooltip: {
                        isHtml: true, // Set this to true if you want to use HTML formatting in tooltips
                        trigger: 'both'  // Show tooltip both when hovering over the data points and the axes
                    }

                };
                var chart1 = new google.visualization.BarChart(document.getElementById('chart_div_2'));
                chart1.draw(data, options);
            }

            document.getElementById('btnDangXuat').addEventListener('click', function (event) {
                event.preventDefault();  // Ngừng hành động mặc định của nút
                window.location.href = '/cnpm/login';  // Chuyển hướng đến trang đăng nhập
            });
            function reDirect(button, url)
            {
                event.preventDefault();
                var tasks = "<%= tasks %>";
                if (tasks.includes(button.value))
                {
                    window.location.href = url;
                } else
                {
                    alert("Bạn không có quyền quản lí tác vụ " + button.value);
                }
            }
        </script>
    </head>
    <body>

        <div id="menu">
           <div class="conponentMenu" id="iconAndName" >
                    <img src="img/account.svg" alt="icon">
                    ${nv.getHo()}  ${nv.getTen()}<br>
                    ${nv.getChucVu()} 
                </div>
                <button id="btnSach" class="conponentMenu" value="sách" onclick="reDirect(this,'/cnpm/sach')">
                    <img src="img/sach.jpg" alt="icon"> Sách
                </button>
                <button id="btnNhanVien" class="conponentMenu" value="nhân viên" onclick="reDirect(this,'/cnpm/nhanvien')">
                    <img src="img/stafff.svg" alt="icon"> Nhân viên
                </button>
                <button id="btnDocGia" class="conponentMenu" value="độc giả" onclick="reDirect(this,'/cnpm/docgia')">
                    <img src="img/customerr.svg" alt="icon"> Độc giả
                </button>
                <button id="btnNhaXuatBan" class="conponentMenu" value="nhà xuất bản" onclick="reDirect(this,'/cnpm/nxb')">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
                </button>
                <button id="btnNhaCungCap" class="conponentMenu" value="nhà cung cấp" onclick="reDirect(this,'/cnpm/ncc')">
                    <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
                </button>
                <button id="btnKhuVuc" class="conponentMenu" value="khu vực" onclick="reDirect(this,'/cnpm/kv')">
                    <img src="img/khuvuc.jpg" alt="icon"> Khu vực
                </button>
                <button id="btnPhieuMuon" class="conponentMenu" value="phiếu mượn" onclick="reDirect(this,'/cnpm/phieumuon')">
                    <img src="img/export.svg" alt="icon"> Phiếu mượn
                </button>
                <button id="btnPhieuTra" class="conponentMenu" value="phiếu trả" onclick="reDirect(this,'/cnpm/phieutra')">
                    <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
                </button>
                <button id="btnPhieuPhat" class="conponentMenu" value="phiếu phạt" onclick="reDirect(this,'/cnpm/phieuphat')">
                    <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
                </button>
                <button id="btnPhieuNhap" class="conponentMenu" value="phiếu nhập" onclick="reDirect(this,'/cnpm/phieunhap')">
                    <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
                </button>
                <button id="btnPhanQuyen" class="conponentMenu" value="phân quyền" onclick="reDirect(this,'/cnpm/phanquyen')">
                    <img src="img/permission.svg" alt="icon"> Phân quyền
                </button>
                <button id="btnThongKe" class="conponentMenu" value="thống kê" onclick="reDirect(this,'/cnpm/thongke')">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon"> Thống kê
                </button>
                <button id="btnDangXuat" class="conponentMenu">
                    <img src="img/logout.jpg" alt="icon"> Đăng xuất
                </button>

            </div>

        <div id="form">
            <div id="title">
                <h2 class="thongke">Statistics Panel</h2>
            </div>
            <div class="parent-container">
                <div class="child-div">
                    <div class="icon">📊</div>
                    <div class="text-container">
                        <div class="title">Tổng số sách còn lại của thư viện</div> 
                        <div class="price">${soLuongTotal}</div> 
                    </div>
                </div>  
                <div class="child-div">
                    <div class="icon">📚</div>
                    <div class="text-container">
                        <div class="title">Số sách mượn</div>
                        <div class="price">${sachMuonTotal}</div>
                    </div>
                </div>





                <div class="child-div">
                    <div class="icon">💰</div> <!-- Icon for money/fines -->
                    <div class="text-container">
                        <div class="title">Tổng số tiền phạt</div>
                        <%-- <c:forEach items="${soSachMuon}" var="o" ><!-- Title --> --%>
                        <div class="price">${tongTienPhat} VNĐ</div> <!-- Example price -->
                        <%-- </c:forEach> --%>
                    </div>

                </div>
            </div>

            <br/>

            <div class="chart-container">
                <div id="chart_div_1"></div>
                <div id="chart_div_2"></div>
            </div>
        </div>
    </body>
</html>



