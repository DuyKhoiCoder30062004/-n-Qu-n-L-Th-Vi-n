<%-- 
    Document   : kv
    
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

            /*        .sidebar {
                        width: 12%;  Increased width for more space 
                        min-width: 170px;
                        background-color: #4d4d4d;
                        padding-top: 15px;
                        display: flex;
                        flex-direction: column;
                        border-right: 1px solid #333;
                    }*/

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
                        }*/

            /*        .sidebar button {
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
                    }*/

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

            #btnKhuVuc {
                background-color: #5A4E9B;
            }

            .main-content {
                width: 88%;
                flex: 1;
                background-color: white;
                padding: 20px;
                box-sizing: border-box;
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
                box-shadow: 0 0 10px rgba(0,0,0,0.1); /* Thêm bóng cho tiêu đề */
            }

            .form-container {
                display: flex;
                flex-direction: column;
                gap: 10px;
                margin-bottom: 50px;
                padding-left: 100px;
                padding-top: 25px;
            }

            .form-row {
                display: flex;
                align-items: center;
                gap: 40px;
                flex-wrap: wrap; /* Để khi màn hình nhỏ hơn, các phần tử có thể tự động xuống hàng */
            }

            .form-group {
                display: flex;
                align-items: center;
                font-weight: bold;
                gap: 20px;
            }

            .form-group label {
                width: 100px;
            }

            .form-group input[type="text"],
            .form-group select {
                padding: 5px;
                width: 200px;
                flex: 1;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .icon-buttons {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .icon-buttons img {
                width: 20px;
                height: 20px;
                cursor: pointer;
            }

            .table-container {
                width: 100%;
                max-height: 74%;
                overflow-y: auto; /* Cho phép cuộn theo trục y */
                overflow-x: auto; /* Thêm cuộn ngang để tránh tràn ra màn hình */
                border: 1px solid gray;
                /*background-color: gray;*/
                display: block; /* Thay đổi từ 'relative' sang 'block' để kiểm soát hành vi cuộn */
                position: relative; /* Để giúp cuộn đúng cách */
                box-shadow: 0 0 10px rgba(0,0,0,0.3); /* Thêm bóng cho tiêu đề */
            }

            .table {
                width: 100%;
                min-width: 500px; /* Thay đổi chiều rộng tối thiểu để đảm bảo không bị co lại quá mức */
                max-height: 100%; /* Đảm bảo bảng không cao hơn chiều cao của khung chứa */
                border-collapse: collapse;
                border: 1px solid gray;
                table-layout: auto; /* Để các cột có thể điều chỉnh kích thước tự động */
            }

            .table th, .table td {
                width: 50%; /* Chia đều các cột  */
                border: 2px solid gray;
                height: 30px;
                text-align: center;
                background-color: white;
                box-sizing: border-box;
                white-space: nowrap; /* Không ngắt dòng tự động để nội dung dài không bị tràn */
                overflow: hidden;
                text-overflow: ellipsis; /* Hiển thị dấu "..." khi nội dung quá dài */
                max-width: 200px; /* Đặt giới hạn chiều rộng tối đa cho mỗi ô */
            }

            .table thead {
                display: table-header-group; /* Giữ nguyên hiển thị của phần đầu bảng */
                width: 100%;
                position: sticky;
                top: 0;
                background-color: white;
                z-index: 1;
            }

            .table tbody {
                display: table-row-group; /* Hiển thị thân bảng như một nhóm hàng */
                width: 100%;
                /* max-height: calc(70% - 30px);
                overflow-y: auto; */
            }

            .table tbody tr {
                display: table-row; /* Sử dụng table-row để căn chỉnh từng cột */
                overflow: hidden;
                width: 100%;
                /*text-overflow: ellipsis; /* Hiển thị "..." khi nội dung quá dài */
            }

            .table th, .table td {
                border: 1px solid black;
                text-align: center;
                word-wrap: break-word; /* Ngắt dòng nếu nội dung quá dài */
                white-space: nowrap; /* Không cho phép ngắt dòng nếu cần */
                overflow: hidden; /* Ẩn phần nội dung tràn ra ngoài */
                text-overflow: ellipsis; /* Hiển thị "..." khi nội dung quá dài */
            }

            .table th:hover, .table td:hover {
                white-space: normal;
                /* Cho phép ngắt dòng */
                z-index: 1;
                background-color: #f1f1f1;
                /* Tô màu nền để dễ đọc */
            }

            .search-bar {
                display: flex;
                align-items: center;
                flex-wrap: wrap;
                justify-content: flex-start;
                gap: 10px;
                margin-bottom: 10px;
            }

            .search-bar select {
                padding: 5px;
                width: 150px;
                max-width: 300px; /* Để không vượt quá kích thước màn hình nhỏ */
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .search-bar input[type="text"] {
                padding: 5px;
                width: 200px;
                border-radius: 5px;
                max-width: 300px; /* Để không vượt quá kích thước màn hình nhỏ */
            }

            .search-bar img {
                width: 20px;
                height: 20px;
                cursor: pointer;
            }

            /* Media queries */
            @media (max-width: 1024px) {
                .sidebar {
                    width: 25%; /* Mở rộng sidebar trên màn hình nhỏ */
                }

                .main-content {
                    width: 75%; /* Giảm bớt phần diện tích chính để vừa với sidebar */
                }

                .table-container {
                    height: 50%;
                }
            }

            @media (max-width: 768px) {
                .sidebar {
                    width: 30%; /* Tăng kích thước sidebar */
                }

                .header {
                    width: 93%;
                }

                .main-content {
                    width: 70%;
                    padding: 10px;
                }

                .form-container {
                    padding: 0px;
                }

                .form-group label {
                    width: 80px; /* Giảm kích thước nhãn trên màn hình nhỏ */
                }

                .table-container {
                    max-width: 97%; /* Đảm bảo không bị tràn ngang trên màn hình nhỏ */
                    max-height: 65%;
                    overflow-x: auto;
                }

                .table th, .table td {
                    width: 50%;
                    max-width: 100px; /* Giảm kích thước tối đa của ô trên màn hình nhỏ */
                }
            }

            @media (max-width: 480px) {
                .container {
                    flex-direction: column; /* Chuyển sang bố cục dọc khi màn hình quá nhỏ */
                    height: 100vh; /* Đảm bảo container chiếm toàn bộ chiều cao màn hình */
                    overflow: hidden;
                }

                .sidebar {
                    width: 100%;
                    padding: 10px;
                    height: 30vh; /* Phần nội dung chính chiếm 70% chiều cao */
                    overflow-y: auto; /* Cho phép cuộn dọc nội dung nếu vượt quá */
                }

                .main-content {
                    width: 100%;
                    padding: 10px;
                    height: 70vh; /* Phần nội dung chính chiếm 70% chiều cao */
                    overflow-y: auto; /* Cho phép cuộn dọc nội dung nếu vượt quá */
                }

                .table-container {
                    max-width: 100%; /* Đảm bảo bảng không bị tràn ra ngoài */
                    height: 50%;
                    overflow-x: scroll; /* Thêm cuộn ngang nếu cần trên màn hình rất nhỏ */
                }

                .table th, .table td {
                    width: 50%; /* Chia đều các cột  */
                    width: auto;
                    max-width: 100px; /* Giới hạn kích thước nhỏ hơn cho ô */
                    padding: 5px; /* Giảm padding cho bảng trên màn hình nhỏ */
                }

                .header {
                    font-size: 1.5rem; /* Giảm kích thước font tiêu đề */
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
                <div class="header">Quản lí khu vực</div>
                <div class="form-container">
                    <!-- Row 1: MaKV và TenKV with icons -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>Mã khu vực:</label><input type="text" id="maKV" placeholder="Nhập mã">
                        </div>
                        <div class="form-group">
                            <label>Tên khu vực:</label><input type="text" id="tenKV" placeholder="Nhập tên">
                        </div>
                        <div class="icon-buttons">
                            <img src="img/add.svg" alt="Add" title="Thêm" onclick="sendData('add')">
                            <img src="img/edit.svg" alt="Edit" title="Sửa" onclick="sendData('update')">
                            <img src="img/delete.svg" alt="Delete" title="Xóa" onclick="sendData('delete')">
                            <img src="img/clear.png" alt="Clear" title="Clear" onclick="clearInput()">
                        </div>
                    </div>
                </div>
                <!-- Search bar -->
                <div class="search-bar">
                    <select id="optionSearch">
                        <option value="maKV">Mã khu vực</option>
                        <option value="tenKV">Tên khu vực</option>
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
                                <th>Mã khu vực</th>
                                <th>Tên khu vực</th>
                            </tr>
                        </thead>
                        <tbody id="tableKV">
                            <c:forEach var="kv" items="${listKV}">
                                <tr>
                                    <td>${kv.maKV}</td>
                                    <td>${kv.tenKV}</td>
                                </tr>
                            </c:forEach>
                            <%
                            for (int i = 1; i <= 20; i++) {
                            %>
                            <tr>
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
        <script>
            //        click table
            function clickKV(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('maKV').value = cells[0].innerText;
                document.getElementById('maKV').disabled = true;
                document.getElementById('tenKV').value = cells[1].innerText;
            }
            document.querySelectorAll('#tableKV tr').forEach(row => {
                row.addEventListener('click', () => clickKV(row));
            });

            function sendData(action) {
                const formData = new URLSearchParams({
                    action: action,
                    maKV: document.getElementById('maKV').value,
                    tenKV: document.getElementById('tenKV').value,
                    optionSearch: document.getElementById('optionSearch').value,
                    valueSearch: document.getElementById('searchInput').value
                });

                //alert(formData.get('tasks').split(',').length);
                fetch('http://localhost:9999/cnpm/kv', {
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
                const tableBody = document.getElementById('tableKV');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results); // Chuyển đổi đối tượng thành mảng các giá trị
                }
                results.forEach(item => {

                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maKV || '';
                    row.appendChild(cell1);

                    const cell2 = document.createElement('td');
                    cell2.textContent = item.tenKV || '';
                    row.appendChild(cell2);

                    tableBody.appendChild(row);
                });
            }

            /*clear dữ liệu*/
            function clearInput() {
                document.getElementById('maKV').value = "";
                document.getElementById('maKV').disabled = false;
                document.getElementById('tenKV').value = "";
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

    </body>

</html>
