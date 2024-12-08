<%-- 
    Document   : nxb
    Created on : Oct 26, 2024, 11:37:47 AM
    Author     : HP
--%>
<%@ page import="DTO.Nhanvien_DTO" %>
<%
    Nhanvien_DTO nv = (Nhanvien_DTO) session.getAttribute("nv");
    String tasks = (String) session.getAttribute("tasks");
    if (nv == null || nv.getChucVu().equals("admin")) {
        response.sendRedirect("/cnpm/login");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Library Management System</title>
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
            /*            .user-details {
                            display: flex;
                            flex-direction: row;
                            gap: 10px;
                        }
            
                        .user-info img {
                            width: 30px;
                             Adjust icon size 
                            height: 30px;
                            border-radius: 50%;
                        }*/

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

            #btnNhaXuatBan {
                background-color: #5A4E9B;
            }

            .main-content {
                width: 88%;
                flex: 1;
                display: flex;
                flex-direction: column;
                background-color: #66c2ff;
                padding: 20px;
                gap: 20px;
            }

            .header {
                font-size: 1.5rem;
                color: red;
                font-weight: bold;
                text-align: center;
                padding-bottom: 20px;
                border-bottom: 1px solid #ccc;
            }

            .content-wrapper {
                display: flex;
                justify-content: space-between;
                gap: 30px;
                flex-wrap: wrap;
            }

            .left-content,
            .right-content {
                display: flex;
                flex-direction: column;
                gap: 10px;
            }

            .left-content {
                width: 25%;
                flex: 1;
            }

            .right-content {
                width: 61%;
            }

            .form-container {
                margin-top: 10%;
                display: flex;
                flex-direction: column;
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
                flex: 1;
                min-width: 90px;
            }

            .form-group input[type="text"] {
                flex: 2;
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .icon-buttons,
            .search-bar {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .icon-buttons img, .search-bar img {
                width: 20px;
                height: 20px;
                cursor: pointer;
            }

            .search-bar {
                margin-bottom: 10px;
            }

            .search-bar select,
            .search-bar input[type="text"] {
                padding: 5px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .table-container {
                height: 90%;
                overflow-y: auto;
                border: 1px solid #333;
                background-color: white;
                width: 100%; /* Đảm bảo chiều rộng 100% của container */
                box-sizing: border-box; /* Để padding và border không làm tăng chiều rộng */
            }

            .table {
                width: 100%;
                min-width: 500px; /* Thay đổi chiều rộng tối thiểu để đảm bảo không bị co lại quá mức */
                max-height: 100%; /* Đảm bảo bảng không cao hơn chiều cao của khung chứa */
                border-collapse: collapse;
                table-layout: auto; /* Để các cột có thể điều chỉnh kích thước tự động */
            }

            .table th,
            .table td {
                height: 30px;
                border: 1px solid #333;
                text-align: center;
                word-wrap: break-word;
                overflow: hidden;
                text-overflow: ellipsis;
                min-width: 100px;
            }

            .table thead th,
            .table tbody td {
                width: 33.33%;
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
            @media (max-width: 1024px) {
                .sidebar {
                    width: 25%; /* Mở rộng sidebar trên màn hình nhỏ */
                }

                .main-content {
                    width: 75%; /* Giảm bớt phần diện tích chính để vừa với sidebar */
                }

                .table-container {
                    max-height: 40vh; /* Giảm chiều cao của bảng */
                }
            }

            @media (max-width: 768px) {
                .content-wrapper {
                    flex-direction: column;
                }

                .left-content,
                .right-content {
                    width: 100%;
                    padding: 0;
                    overflow: hidden;
                }

                .left-content {
                    margin-bottom: 20px;
                }

                .form-group {
                    flex-direction: column;
                    align-items: flex-start;
                    gap: 10px;
                }

                .table-container {
                    max-width: 100%; /* Đảm bảo không bị tràn ngang trên màn hình nhỏ */
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
                <div class="header">Quản lí NXB</div>
                <div class="content-wrapper">
                    <div class="left-content">
                        <div class="form-container">
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Mã NXB:</label>
                                    <input type="text" placeholder="Nhập mã" id="maNXB">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Tên NXB:</label>
                                    <input type="text" placeholder="Nhập tên" id="tenNXB">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label>Địa chỉ:</label>
                                    <input type="text" placeholder="Nhập địa chỉ" id="dcNXB">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="icon-buttons">
                                    <img src="img/add.svg" alt="Add" title="Thêm" onclick="sendData('add')">
                                    <img src="img/edit.svg" alt="Edit" title="Sửa" onclick="sendData('update')">
                                    <img src="img/delete.svg" alt="Delete" title="Xóa" onclick="sendData('delete')">
                                    <img src="img/clear.png" alt="Clear" title="Clear" onclick="clearInput()">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right-content">
                        <div class="search-bar">
                            <select id="optionSearch">
                                <option>Mã NXB</option>
                                <option>Tên NXB</option>
                                <option>Địa chỉ</option>
                            </select>
                            <input type="text" id="searchInput" placeholder="Tìm kiếm">
                            <img src="img/search1.png" alt="Search" title="Tìm kiếm" onclick="sendData('search')">
                            <img src="img/refresh.svg" alt="Finish" title="Finish" onclick="sendData('finish')">
                        </div>
                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Mã NXB</th>
                                        <th>Tên NXB</th>
                                        <th>Địa chỉ</th>
                                    </tr>
                                </thead>
                                <tbody id="tableNXB">
                                    <c:forEach var="nxb" items="${listNXB}">
                                        <tr>
                                            <td>${nxb.maNXB}</td>
                                            <td>${nxb.tenNXB}</td>
                                            <td>${nxb.dcNXB}</td>
                                        </c:forEach>
                                        <%
                                        for (int i = 1; i <= 15; i++) {
                                        %>
                                    <tr>
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
            function clickNXB(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('maNXB').value = cells[0].innerText;
                document.getElementById('maNXB').disabled = true;
                document.getElementById('tenNXB').value = cells[1].innerText;
                document.getElementById('dcNXB').value = cells[2].innerText;
            }
            document.querySelectorAll('#tableNXB tr').forEach(row => {
                row.addEventListener('click', () => clickNXB(row));
            });

            function sendData(action) {
                const formData = new URLSearchParams({
                    action: action,
                    maNXB: document.getElementById('maNXB').value,
                    tenNXB: document.getElementById('tenNXB').value,
                    dcNXB: document.getElementById('dcNXB').value,

                    optionSearch: document.getElementById('optionSearch').value,
                    valueSearch: document.getElementById('searchInput').value
                });

                //alert(formData.get('tasks').split(',').length);
                fetch('http://localhost:9999/cnpm/nxb', {
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
                const tableBody = document.getElementById('tableNXB');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results); // Chuyển đổi đối tượng thành mảng các giá trị
                }
                results.forEach(item => {

                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maNXB || '';
                    row.appendChild(cell1);

                    const cell2 = document.createElement('td');
                    cell2.textContent = item.tenNXB || '';
                    row.appendChild(cell2);

                    const cell3 = document.createElement('td');
                    cell3.textContent = item.dcNXB || '';
                    row.appendChild(cell3);

                    tableBody.appendChild(row);
                });
            }

            /*clear dữ liệu*/
            function clearInput() {
                document.getElementById('maNXB').value = "";
                document.getElementById('maNXB').disabled = false;
                document.getElementById('tenNXB').value = "";
                document.getElementById('dcNXB').value = "";
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