<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            text-align: center;
            color: #064BFB;
            font-weight: bolder;
            text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
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
            margin-top: 40px;
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
            data.addColumn('number', 'Sales');

            data.addRows([
                [2001, 37.8],
                [2002, 30.9],
                [2003, 25.4],
                [2004, 11.7],
                [2005, 10.5],
                [2006, 5.7],
                [2007, 1.4],
                [2008, 3.1],
                [2009, 3.8],
                [2010, 7.2]
            ]);

            var options = {
                title: 'Sales Over Time',
                curveType: 'function',
                legend: { position: 'bottom' }
            };

            var chart = new google.visualization.LineChart(document.getElementById('chart_div_1'));
            chart.draw(data, options);
        }

        // Draw the second chart
        google.charts.setOnLoadCallback(drawChart2);
        function drawChart2() {
            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Year');
            data.addColumn('number', 'Revenue');

            data.addRows([
                [2001, 20.3],
                [2002, 26.2],
                [2003, 25.7],
                [2004, 16.2],
                [2005, 12.5],
                [2006, 5.3],
                [2007, 3.2],
                [2008, 2.1],
                [2009, 4.0],
                [2010, 8.4]
            ]);

            var options = {
                title: 'Revenue Over Time',
                curveType: 'function',
                legend: { position: 'bottom' }
            };

            var chart = new google.visualization.LineChart(document.getElementById('chart_div_2'));
            chart.draw(data, options);
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
        <button class="conponentMenu" value="thống kê" onclick="reDirect(this, '/cnpm/thongke')">
            <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
        </button>
        <button class="conponentMenu" value="phiếu phạt" onclick="reDirect(this, '/cnpm/thongke')">
            <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
        </button>
        <button class="conponentMenu" value="phiếu nhập" onclick="reDirect(this, '/cnpm/thongke')">
            <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
        </button>
        <button class="conponentMenu" value="phân quyền" onclick="reDirect(this, '/cnpm/thongke')">
            <img src="img/phanquyen.jpg" alt="icon"> Phân quyền
        </button>
        <button id="btnThongKe" class="conponentMenu" value="thống kê" onclick="reDirect(this, '/cnpm/thongke')">
            <img src="img/thongke.jpg" alt="icon"> Thống kê
        </button>
        <button class="conponentMenu" value="đăng xuất" onclick="reDirect(this, '/cnpm/thongke')">
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
                    <div class="title">Tổng số sách</div>
                    <div class="price">100</div>
                </div>
            </div>
            <div class="child-div">
                <div class="icon">📚</div>
                <div class="text-container">
                    <div class="title">Số sách mượn</div>
                    <div class="price">456</div>
                </div>
            </div>
            
            
            
            
            
            <div class="child-div">
    <div class="icon">💰</div> <!-- Icon for money/fines -->
    <div class="text-container">
        <div class="title">Tổng số tiền phạt</div>
        <%-- <c:forEach items="${soSachMuon}" var="o" ><!-- Title --> --%>
        <div class="price">500000 VNĐ</div> <!-- Example price -->
       <%-- </c:forEach> --%>
        </div>
    
</div>
        </div>
        <form>
        <div>
        <br/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
        <input type="datetime-local" style="width: 300px; height: 40px;"/>&nbsp;&nbsp;&nbsp;<input type="button" value="Thống kê" onclick="alert('Button clicked!')" style="width: 200px; height: 40px;"/>

        
        </div>
        </form>
        <br/>
        
        <div class="chart-container">
            <div id="chart_div_1"></div>
            <div id="chart_div_2"></div>
        </div>
    </div>

</body>
</html>
