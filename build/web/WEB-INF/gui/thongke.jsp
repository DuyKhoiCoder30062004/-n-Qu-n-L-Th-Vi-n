<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List, Model.CTPM, Model.CTPP" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map.Entry" %>
<%
    // Retrieve the 'soLuongAndYear' attribute from the request
    List<CTPM> soLuongAndYear = (List<CTPM>) request.getAttribute("soLuongAndYear");

    // Use a map to aggregate the sum of 'soLuong' by year
    Map<Integer, Integer> yearToSumMap = new HashMap<>();
    
    for (CTPM ctpm : soLuongAndYear) {
        int year = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(ctpm.getNgayMuon()));
        int soLuong = ctpm.getSoLuong();
        
        // Accumulate the 'soLuong' for the same year
        yearToSumMap.put(year, yearToSumMap.getOrDefault(year, 0) + soLuong);
    }

    // Prepare the JavaScript code to build the Google Chart data
    StringBuilder chartData = new StringBuilder();
    for (Map.Entry<Integer, Integer> entry : yearToSumMap.entrySet()) {
        int year = entry.getKey();
        int totalBooks = entry.getValue();
        chartData.append("[").append(year).append(", ").append(totalBooks).append("], ");
    }
    if (chartData.length() > 0) {
        // Remove the trailing comma and space
        chartData.setLength(chartData.length() - 2);
    }
    
    
    
List<CTPP> soTien_NamPhat = (List<CTPP>) request.getAttribute("soTien_NamPhat");
Map<Integer, Integer> yearToSumMap1 = new HashMap<>();
for(CTPP ctpp : soTien_NamPhat){
int year = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(ctpp.getNgayLap()));
int tien = ctpp.getTien();

yearToSumMap1.put(year, yearToSumMap1.getOrDefault(year, 0) + tien);
    }
    StringBuilder chartData1 = new StringBuilder();
    for (Map.Entry<Integer, Integer> entry : yearToSumMap1.entrySet()) {
        int year = entry.getKey();
        int totalFine = entry.getValue();
        chartData1.append("[").append(year).append(", ").append(totalFine).append("], ");
    }
    if (chartData1.length() > 0) {
        // Remove the trailing comma and space
        chartData1.setLength(chartData1.length() - 2);
    }
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
                <%= chartData.toString() %>
            ]);

            var options = {
                title: 'Số sách mượn qua các năm',
                vAxis: {title: 'Năm'},
                
                curveType: 'function',
                legend: { position: 'bottom' },
                 tooltip: {
                isHtml: true,  // Set this to true if you want to use HTML formatting in tooltips
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
                <%= chartData1.toString() %>
            ]);

            var options = {
                title: 'Số tiền phạt qua các năm',
                vAxis: {title: 'Năm'},
                
                curveType: 'function',
                legend: { position: 'bottom' },
                 tooltip: {
                isHtml: true,  // Set this to true if you want to use HTML formatting in tooltips
                trigger: 'both'  // Show tooltip both when hovering over the data points and the axes
            }
                
            };

            var chart1 = new google.visualization.BarChart(document.getElementById('chart_div_2'));
            chart1.draw(data, options);
        }

        // Redirect function
        function reDirect(button, url) {
            event.preventDefault();
            var tasks = ["sách", "nhân viên", "độc giả", "nhà xuất bản", "nhà cung cấp", "khu vực", "phiếu mượn", "phiếu trả", "phiếu phạt", "phiếu nhập", "phân quyền", "thống kê"];
            if (tasks.includes(button.value)) {
                window.location.href = url;
            } else {
                alert("Bạn không có quyền quản lí tác vụ " + button.value);
            }
        }
    </script>
</head>
<body>

    <div id="menu">
        <div class="conponentMenu" id="iconAndName">
            <img src="img/sach.jpg" alt="icon"> Sách
        </div>
        <button class="conponentMenu" value="sách" onclick="reDirect(this, '/cnpm/sach')">
            <img src="img/background_menu.png" alt="icon"> Sách
        </button>
        <button class="conponentMenu" value="nhân viên" onclick="reDirect(this, '/cnpm/nhanvien')">
            <img src="img/stafff.svg" alt="icon"> Nhân viên
        </button>
        <button class="conponentMenu" value="độc giả" onclick="reDirect(this, '/cnpm/docgia')">
            <img src="img/background_menu.png" alt="icon"> Độc giả
        </button>
        <button class="conponentMenu" value="nhà xuất bản" onclick="reDirect(this, '/cnpm/nhaxuatban')">
            <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
        </button>
        <button class="conponentMenu" value="nhà cung cấp" onclick="reDirect(this, '/cnpm/nhacungcap')">
            <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
        </button>
        <button class="conponentMenu" value="khu vực" onclick="reDirect(this, '/cnpm/khuvuc')">
            <img src="img/khuvuc.jpg" alt="icon"> Khu vực
        </button>
        <button class="conponentMenu" value="phiếu mượn" onclick="reDirect(this, '/cnpm/phieumuon')">
            <img src="img/phieumuon.jpg" alt="icon"> Phiếu mượn
        </button>
        <button class="conponentMenu" value="phiếu trả" onclick="reDirect(this, '/cnpm/phieutra')">
            <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
        </button>
        <button class="conponentMenu" value="phiếu phạt" onclick="reDirect(this, '/cnpm/phieuphat')">
            <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
        </button>
        <button class="conponentMenu" value="phiếu nhập" onclick="reDirect(this, '/cnpm/phieunhap')">
            <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
        </button>
        <button class="conponentMenu" value="phân quyền" onclick="reDirect(this, '/cnpm/phanquyen')">
            <img src="img/phanquyen.jpg" alt="icon"> Phân quyền
        </button>
        <button id="btnThongKe" class="conponentMenu" value="thống kê" onclick="reDirect(this, '#')">
            <img src="img/thongke.jpg" alt="icon"> Thống kê
        </button>
        <button class="conponentMenu" value="đăng xuất" onclick="reDirect(this, '/cnpm/dangxuat)">
            <img src="img/dangxuat.jpg" alt="icon"> Đăng xuất
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
            <div class="title">Total Quantity</div> <!-- Title for the section -->
            <div class="price">${soLuongTotal}</div> <!-- Display the totalSoLuong value -->
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






