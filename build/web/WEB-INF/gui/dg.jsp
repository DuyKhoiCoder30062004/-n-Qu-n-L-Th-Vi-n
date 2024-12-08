<%-- 
    Document   : dg
    Created on : Nov 30, 2024, 8:17:38 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="DTO.Nhanvien_DTO" %>
<%
    Nhanvien_DTO nv = (Nhanvien_DTO) session.getAttribute("nv");
    String tasks = (String) session.getAttribute("tasks");
    if (nv == null || nv.getChucVu().equals("admin")) {
        response.sendRedirect("/cnpm/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Library Management System</title>
        <!--    <link rel="stylesheet" href="../css/style_ncc.css">-->
        <style>
            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                font-family: 'Times New Roman', Times, serif;
                margin: 0;
                padding: 0;
                height: 100vh;
                overflow: hidden;
            }

            .container {
                display: flex;
                flex-wrap: nowrap; /* Không cho phép xuống dòng để giữ sidebar và main-content trên cùng một trang */
                height: 100vh;
                overflow: hidden;
            }

            .title-bar {
                width: 100%;
                height: 3%;
                background-color: #e0e0e0;
                font-size: 16px;
                font-weight: bolder;
                position: fixed;
            }

            #menu {
                width: 12%;
                min-width: 170px;
                background-color:#2C2F48;
                padding-top: 15px;
                display: flex;
                flex-direction: column;
                top: 3%;
                border-right: 1px solid #333;
            }

            /*        <!-- .sidebar {
                        width: 12%;
                        min-width: 170px;
                        background-color: #4d4d4d;
                        padding-top: 15px;
                        display: flex;
                        flex-direction: column;
                        border-right: 1px solid #333;
                    } -->*/

            #user-info {
                height: 14.5%;
                margin-top: 0px;
                border-radius: 0px;
            }
            #user-info img{
                width: 40%;
                border-radius: 50%;
                margin-right: 0%;
                margin-left: 0%;
            }

            /*        <!-- .sidebar button {
                        background-color: #cccccc;
                        border: 1px solid #333;
                        padding: 12px 12px 12px 22px;
                        color: black;
                        cursor: pointer;
                        font-size: 14px;
                        font-weight: bold;
                        display: flex;
                        align-items: center;
                        gap: 10px;
                        box-sizing: border-box;
                        margin-bottom: 2px;
                        border-radius: 10px;
                    }
            
                    .sidebar button.active {
                        background-color: #59c8ed;
                    }
            
                    .sidebar button:hover {
                        background-color: #ffffff;
                    }
            
                    .sidebar button img {
                        width: 20px;
                        height: 20px;
                    } -->*/

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
                border-bottom:1px solid white ;

            }

            .conponentMenu:hover {
                background-color:burlywood;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .conponentMenu img {
                width: 20%;
                height: auto;
                margin-right: 10%;
                margin-left: 10%;
            }

            #btnDocGia {
                background-color: #5A4E9B;
            }

            .main-content {
                width: 88%;
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: silver;
                padding: 20px;
                gap: 20px;
            }

            .header {
                font-size: 1.5rem;
                color: red;
                font-weight: bold;
                text-align: center;
                justify-content: center; /* Căn giữa theo chiều ngang */
                padding: 10px;
                background-color: white;
                border-bottom: 1px solid #ccc;
                border-radius: 8px; /* Bo góc cho tiêu đề */
                box-shadow: 0 0 10px rgba(0,0,0,0.25); /* Thêm bóng cho tiêu đề */
            }

            .content-wrapper {
                display: flex;
                justify-content: space-between;
                gap: 30px;
                flex-wrap: wrap;
                height: 100%;
            }

            .left-content,
            .right-content {
                display: flex;
                flex-direction: column;
                background-color: #f9f9f9;
                box-shadow: 0 0 10px rgba(0,0,0,0.25); /* Thêm bóng cho tiêu đề */
            }

            .left-content {
                margin-left: 0px;
                width: 26%;
                flex: 1;
                gap: 10px;
                border-radius: 10px;
                overflow-y: auto;
            }

            .right-content {
                width: 61%;
                height: 100%;
                border-radius: 10px;
                gap: 5px;
            }

            .form-container {
                display: flex;
                flex-direction: column;
                margin-top: 10%;
                gap: 10px;
            }

            .form-group {
                display: flex;
                align-items: center;
                font-weight: bold;
                gap: 10px;
                flex-wrap: wrap;
            }

            .form-group label {
                margin-left: 5px;
                flex: 1;
                min-width: 90px;
            }

            .form-group input {
                margin-right: 12px;
                width: 300px;
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
                height: 30px; /* Chiều cao cố định để các input đồng đều */
            }

            /* Input cho ngày sinh (nhỏ hơn và cách một khoảng) */
            .form-group input#ngaySinh {
                flex: none; /* Không cho phép flex tự động điều chỉnh */
                width: 150px; /* Độ rộng nhỏ hơn */
                margin-right: 155px; /* Khoảng cách lề bên trái */
            }

            /* Input kiểu text (kích thước lớn hơn) */
            /*            .form-group input[type="text"] {
                            flex: 2;
                            width: 180px;
                        }*/

            .icon-buttons,
            .search-bar {
                margin-left: 10px;
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .icon-buttons img, .search-bar img {
                width: 20px;
                height: 20px;
                cursor: pointer;
            }

            .search-bar {
                margin-bottom: 10px;
                margin-top: 10px;
                display: flex;
                align-items: center;
                gap: 20px;
                flex-wrap: wrap; /* Thêm dòng này để cho phép xuống dòng */
            }

            .search-bar img {
                width: 20px;
                height: 20px;
                cursor: pointer;
            }

            .search-bar select,
            .search-bar input[type="text"] {
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .table-container {
                max-height: 90%;
                height: 85%;
                overflow-y: auto;
                border: 1px solid #333;
                background-color: white;
                width: 100%; /* Đảm bảo chiều rộng 100% của container */
                box-sizing: border-box; /* Để padding và border không làm tăng chiều rộng */
            }

            .table {
                width: 100%;
                height: 70%;
                min-width: 500px; /* Thay đổi chiều rộng tối thiểu để đảm bảo không bị co lại quá mức */
                max-height: 90%; /* Đảm bảo bảng không cao hơn chiều cao của khung chứa */
                border-collapse: collapse;
                table-layout: auto; /* Để các cột có thể điều chỉnh kích thước tự động */
            }

            .table th,
            .table td {
                border: 1px solid #333;
                height: 30px;
                text-align: center;
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap; /* Không cho phép xuống dòng trong ô */
                min-width: 100px;
            }

            .table th {
                background-color: #f5f5f5;
                font-weight: bold;
            }

            .table thead {
                position: sticky;
                top: 0;
                background-color: white;
                z-index: 1;
            }

            .table td:hover {
                background-color: #f1f1f1;
            }

            /* Media query */
            @media (max-width: 1300px) {
                .sidebar {
                    width: 25%; /* Mở rộng sidebar trên màn hình nhỏ */
                }

                .main-content {
                    width: 75%; /* Giảm bớt phần diện tích chính để vừa với sidebar */
                }

                .form-group {
                    margin-left: 15px;
                }

                .table-container {
                    max-height: 90%; /* Giảm chiều cao của bảng */
                }
            }

            @media (max-width: 1024px) {
                .sidebar {
                    width: 25%; /* Mở rộng sidebar trên màn hình nhỏ */
                }

                .main-content {
                    width: 75%; /* Giảm bớt phần diện tích chính để vừa với sidebar */
                }

                .form-group {
                    margin-left: 15px;
                }

                .table-container {
                    max-height: 90%;
                }
            }

            @media (max-width: 768px) {
                .header {
                    width: 90%;
                }

                .content-wrapper {
                    flex-direction: column;
                }

                .form-group {
                    margin-left: 15px;
                }

                .left-content,
                .right-content {
                    width: 90%;
                    padding: 0;
                    overflow: hidden;
                }

                .left-content {
                    margin-bottom: 20px;
                    overflow-y: auto;
                    overflow-x: auto;
                    height: 15%;
                }

                .right-content {
                    height: 65%;
                }

                .form-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }

                .search-bar {
                    gap: 10px; /* Giảm khoảng cách để vừa khung hình hơn */
                }

                .search-bar select,
                .search-bar input[type="text"] {
                    flex: 1; /* Để input có thể co giãn theo chiều rộng */
                    width: 100%; /* Đảm bảo chúng chiếm toàn bộ chiều ngang nếu cần */
                }

                .table-container {
                    max-width: 100%; /* Đảm bảo không bị tràn ngang trên màn hình nhỏ */
                    height: 77%;
                    overflow-x: auto;
                }

                .table {
                    min-width: 500px;
                    table-layout: auto;
                }
            }


        </style>
    </head>

    <body>
        <div class="container">
            <div class="title-bar">
                Hệ thống quản lý thư viện
            </div>

            <div id="menu" class="sidebar">
                <div id="user-info" class="conponentMenu">
                    <img src="img/account.svg" alt="User Icon">
                    <div class="user-details">
                        ${nv.getHo()}  ${nv.getTen()}<br>
                        ${nv.getChucVu()} 
                    </div>
                </div>
                <button id="btnSach" class="conponentMenu" value="sách" onclick="reDirect(this, '/cnpm/sach')">
                    <img src="img/sach.jpg" alt="icon"> Sách
                </button>
                <button id="btnNhanVien" class="conponentMenu" value="nhân viên" onclick="reDirect(this, '/cnpm/nhanvien')">
                    <img src="img/stafff.svg" alt="icon"> Nhân viên
                </button>
                <button id="btnDocGia" class="conponentMenu" value="độc giả" onclick="reDirect(this, '/cnpm/docgia')">
                    <img src="img/customerr.svg" alt="icon"> Độc giả
                </button>
                <button id="btnNhaXuatBan" class="conponentMenu" value="nhà xuất bản" onclick="reDirect(this, '/cnpm/nxb')">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
                </button>
                <button id="btnNhaCungCap" class="conponentMenu" value="nhà cung cấp" onclick="reDirect(this, '/cnpm/ncc')">
                    <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
                </button>
                <button id="btnKhuVuc" class="conponentMenu" value="khu vực" onclick="reDirect(this, '/cnpm/kv')">
                    <img src="img/khuvuc.jpg" alt="icon"> Khu vực
                </button>
                <button id="btnPhieuMuon" class="conponentMenu" value="phiếu mượn" onclick="reDirect(this, '/cnpm/phieumuon')">
                    <img src="img/export.svg" alt="icon"> Phiếu mượn
                </button>
                <button id="btnPhieuTra" class="conponentMenu" value="phiếu trả" onclick="reDirect(this, '/cnpm/phieutra')">
                    <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
                </button>
                <button id="btnPhieuPhat" class="conponentMenu" value="phiếu phạt" onclick="reDirect(this, '/cnpm/phieuphat')">
                    <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
                </button>
                <button id="btnPhieuNhap" class="conponentMenu" value="phiếu nhập" onclick="reDirect(this, '/cnpm/phieunhap')">
                    <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
                </button>
                <button id="btnPhanQuyen" class="conponentMenu" value="phân quyền" onclick="reDirect(this, '/cnpm/phanquyen')">
                    <img src="img/permission.svg" alt="icon"> Phân quyền
                </button>
                <button id="btnThongKe" class="conponentMenu" value="thống kê" onclick="reDirect(this, '/cnpm/thongke')">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon"> Thống kê
                </button>
                <button id="btnDangXuat" class="conponentMenu">
                    <img src="img/logout.jpg" alt="icon"> Đăng xuất
                </button>
            </div>

            <div class="main-content">
                <div class="header">Quản lí độc giả</div>
                <div class="content-wrapper">
                    <div class="left-content">
                        <div class="form-container">
                            <!-- Row 1: MaDG, HoDG, TenDG -->
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Mã độc giả:</label><input type="text" id="maDG" placeholder="Nhập mã">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Họ độc giả:</label><input type="text" id="hoDG" placeholder="Nhập họ">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Tên độc giả:</label><input type="text" id="tenDG" placeholder="Nhập tên">
                                </div>
                            </div>
                            <!-- Row 2: Địa chỉ, SDT, ngày sinh-->
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Địa chỉ:</label><input type="text" id="dc" placeholder="Nhập địa chỉ">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label>SDT:</label><input type="text" id="sdt" placeholder="Nhập sđt">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <!--<label>Ngày sinh:</label><input type="text" id="ngaySinh" placeholder="Chọn ngày sinh">-->
                                    <label>Ngày sinh</label>
                                    <input type="date" id="ngaySinh" name="ngay-sinh" required>
                                    <small class="error-message" ></small>
                                </div>
                            </div>
                            <!-- Row 3: Thêm sửa xóa clear -->
                            <div class="form-row">
                                <div class="form-group">
                                    <div class="icon-buttons">
                                        <img src="img/add.svg" alt="Add" title="Thêm" onclick="sendData('add')">
                                        <img src="img/edit.svg" alt="Edit" title="Sửa" onclick="sendData('update')">
                                        <img src="img/delete.svg" alt="Delete" title="Xóa" onclick="sendData('delete')">
                                        <img src="img/clear.png" alt="Clear" title="Clear" onclick="clearInput()">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right-content">
                        <!-- Search bar -->
                        <div class="search-bar">
                            <select id="optionSearch">
                                <option value="maDG">Mã độc giả</option>
                                <option value="hoDG">Họ độc giả</option>
                                <option value="tenDG">Tên độc giả</option>
                                <option value="sdt">SDT</option>
                                <option value="diaChi">Địa chỉ</option>
                                <option value="ngaySinh">Ngày sinh</option>
                            </select>
                            <input type="text" id="searchInput" placeholder="Search">
                            <img src="img/search1.png" alt="Search" title="Tìm kiếm" onclick="sendData('search')">
                            <img src="img/refresh.svg" alt="Finish" title="Finish" onclick="sendData('finish')">
                        </div>

                        <!-- Table -->
                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Mã độc giả</th>
                                        <th>Họ độc giả</th>
                                        <th>Tên độc giả</th>
                                        <th>Địa chỉ</th>
                                        <th>SĐT</th>
                                        <th>Ngày sinh</th>
                                    </tr>
                                </thead>
                                <tbody id="tableDG">
                                    <c:forEach var="dg" items="${listDG}">
                                        <tr>
                                            <td>${dg.maDG}</td>
                                            <td>${dg.hoDG}</td>
                                            <td>${dg.tenDG}</td>
                                            <td>${dg.diaChi}</td>
                                            <td>${dg.soDienThoai}</td>
                                            <td>${dg.ngaySinh}</td>
                                        </tr>
                                    </c:forEach>
                                    <%
                                    for (int i = 1; i <= 20; i++) {
                                    %>
                                    <tr>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            //        click table
            function clickDG(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('maDG').value = cells[0].innerText;
                document.getElementById('maDG').disabled = true;
                document.getElementById('hoDG').value = cells[1].innerText;
                document.getElementById('tenDG').value = cells[2].innerText;

                document.getElementById('dc').value = cells[3].innerText;
                document.getElementById('sdt').value = cells[4].innerText;
                const dateValueNS = cells[5].innerText;
                document.getElementById('ngaySinh').value = dateValueNS;

            }
            document.querySelectorAll('#tableDG tr').forEach(row => {
                row.addEventListener('click', () => clickDG(row));
            });

            function sendData(action) {
                const formData = new URLSearchParams({
                    action: action,
                    maDG: document.getElementById('maDG').value,
                    hoDG: document.getElementById('hoDG').value,
                    tenDG: document.getElementById('tenDG').value,
                    dc: document.getElementById('dc').value,
                    sdt: document.getElementById('sdt').value,
                    ngaySinh: document.getElementById('ngaySinh').value,
                    optionSearch: document.getElementById('optionSearch').value,
                    valueSearch: document.getElementById('searchInput').value
                });

                //alert(formData.get('tasks').split(',').length);
                fetch('http://localhost:9999/cnpm/docgia', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: formData.toString()
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Đã xảy ra lỗi trong quá trình gửi dữ liệu');
                            }
                            return response.json(); // Chuyển đổi phản hồi thành JSON
                        })
                        .then(data => {
                            if (data.thongbao)
                                alert(data.thongbao); // Hiển thị thông báo từ servlet
                            if (data.results && data.results.length > 0) {
                                updateTable(data.results);
                            }

                            // Nếu dữ liệu hợp lệ, tải lại trang
                            if (data.hopLe) {
                                window.location.reload();
                            }
                        })
                        .catch(error => {
                            alert('Đã xảy ra lỗi: ' + error);
                            console.error(error);
                        });
            }

            function updateTable(results) {
                const tableBody = document.getElementById('tableDG');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results); // Chuyển đổi đối tượng thành mảng các giá trị
                }
                results.forEach(item => {

                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maDG || '';
                    row.appendChild(cell1);

                    const cell2 = document.createElement('td');
                    cell2.textContent = item.hoDG || '';
                    row.appendChild(cell2);

                    const cell3 = document.createElement('td');
                    cell3.textContent = item.tenDG || '';
                    row.appendChild(cell3);

                    const cell4 = document.createElement('td');
                    cell4.textContent = item.diaChi || '';
                    row.appendChild(cell4);

                    const cell5 = document.createElement('td');
                    cell5.textContent = item.soDienThoai || '';
                    row.appendChild(cell5);

                    const cell6 = document.createElement('td');
                    cell6.textContent = item.ngaySinh || '';
                    row.appendChild(cell6);

                    tableBody.appendChild(row);
                });
            }

            /*clear dữ liệu*/
            function clearInput() {
                document.getElementById('maDG').value = "";
                document.getElementById('maDG').disabled = false;
                document.getElementById('hoDG').value = "";
                document.getElementById('tenDG').value = "";
                document.getElementById('dc').value = "";
                document.getElementById('sdt').value = "";
                document.getElementById('ngaySinh').value = "";
            }

            //chuyển trang
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

    </body>

</html>