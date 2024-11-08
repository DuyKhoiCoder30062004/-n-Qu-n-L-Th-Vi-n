<%-- 
    Document   : phanquyen
    Created on : Oct 17, 2024, 10:53:06 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta rel="icon" type="image/x-icon" href="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, maximum-scale=1.0">
        <title>Quản lí phân quyền và tài khoản</title>
    </head>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height:100vh;
            width: 100vw;
            font-family:'Times New Roman', Times, serif;
        }
        #form{
            display:flex;
            height:100vh;

        }
        #title{
            width: 100%;
            height:3%;
            background-color:#D9D9D9;
            font-size: 16px;
            font-style: Italic;
            position:fixed;
            top: 0;
            left: 0;

        }
        #menu{
            width: 12%;
            height: 97%;
            background-color:#99B3A1 ;
            position:fixed;
            top: 3%;
            left: 0;
        }
        #detail{
            width: 88%;
            height: 97%;
            background-color:#F5A9FC ;
            top: 3%;
            left: 12%;
            position:fixed;
        }
        #iconAndName
        {
            height: 14.5%;
            margin-top: 0px;
            border-radius: 0px;
        }
        .conponentMenu{
            width: 100%;
            border-radius: 10px;
            height: 6%;
            margin-top: 2%;
            font-weight:bold ;
            text-align: center;
            display: flex; /* Thay đổi để sử dụng flexbox */
            align-items: center; /* Căn giữa theo chiều dọc */
            justify-content: flex-start; /* Căn giữa theo chiều ngang */
            border:#D9D9D9;
            background-color: #D9D9D9;
        }
        .conponentMenu:hover {
            background-color: #ffffff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .conponentMenu img{
            width: 20%;
            height:auto;
            margin-right: 10%;
            margin-left: 10%;
        }
        #iconAndName img{
            width: 40%;
            border-radius: 50%;
            margin-right: 0%;
            margin-left: 0%;
        }
        #tacVuThucThi
        {
            background-color: #00CED1;
        }
        #titleDetail
        {
            font-size: 20px;
            align-items: center;
            display: flex;
            color: #064BFB;
            font-weight:bolder;
            text-shadow:2px 2px 5px rgba(9, 9, 9, 0.5);
            ;
        }
        #imgDetail
        {
            width: 3%;
            height: auto;
            margin-left: 38%;
            margin-right: 1%;

        }
        #input
        {
            width: 30%;
            margin-top: 5%;
        }
        .nameFeature{
            font-weight: bolder;
        }
        #divtable
        {
            width:70% ;
            height:95%;
        }
        .input-group{
            margin-bottom: 10px;
            margin-left: 20%;
        }
        .iconChucNang
        {
            width: 22px;
            height: auto;
            margin-right: 10px;
            vertical-align: middle;
            cursor: pointer;
        }
        .checkbox{
            margin-left: 10%;
        }
        .table{
            width: 90%;
            border-collapse: collapse;
            margin-top: 2px;
            margin-left: 2%;
            table-layout: fixed;
            border: 1px solid black;
            height: 90%;
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }
        .table thead{
            display: table; /* Để hiển thị hàng tiêu đề */
            width: 100%;
            table-layout: fixed;
            position: sticky; /* Cố định vị trí hàng tiêu đề */
            top: 0; /* Gắn hàng tiêu đề ở trên cùng */
            background-color: #D9D9D9; /* Màu nền cho hàng tiêu đề */
            z-index: 1; /* Đảm bảo hàng tiêu đề ở trên các hàng khác */
        }
        .table tbody
        {
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }
        .table th,td{
            border: 1px solid black;
            text-align: center;
            word-wrap: break-word; /* Ngắt dòng nếu nội dung quá dài */
            white-space: nowrap; /* Không cho phép ngắt dòng nếu cần */
            overflow: hidden; /* Ẩn phần nội dung tràn ra ngoài */
            text-overflow: ellipsis; /* Hiển thị "..." khi nội dung quá dài */
        }
        .table tbody tr
        {
            height: 50px;
            display: table;
            width: 100%;
            table-layout: fixed;
        }
        #tablePQ tbody td {
            width: 25%; /* Cố định chiều rộng cho cột đầu và cột tiếp theo */
        }

        #tablePQ tbody td:nth-child(3) {
            width: 50%; /* Cố định chiều rộng cho cột cuối */
        }
        .table td:hover{
            white-space: normal; /* Cho phép ngắt dòng */
            z-index: 1;
            background-color: #f1f1f1;
        }
        #divtableNV
        {
            width: 50%;
            height: 50%;
            background-color: azure;
            border: 2px solid black;
            position: absolute;
            top: 20%;
            left: 20%;
            display: none; /* Ẩn bảng nhân viên mặc định */
            z-index: 1000; /* Hiển thị trên cùng */
        }
        #tableNV{
            width: 95%;
            height: 68%;
        }
        input:focus{
            outline: none;
            border-color: darkturquoise;
        }

    </style>
    <body>
        <form id="form">
            <div id="title">
                Hệ thống quản lí thư viện
            </div>

            <div id="menu">
                <div class="conponentMenu" id="iconAndName" style="background-color:#99B3A1 ;">
                    <img src="img/account.svg" alt="icon">
                    Nguyễn Trung
                    Quản lí
                </div>
                <button class="conponentMenu">
                    <img src="img/sach.jpg" alt="icon"> Sách</button>
                <button class="conponentMenu">
                    <img src="img/stafff.svg" alt="icon">Nhân viên</button>
                <button class="conponentMenu">
                    <img src="img/customerr.svg" alt="icon">Độc giả</button>
                <button class="conponentMenu">
                    <img src="img/nhaxuatban.jpg" alt="icon">Nhà xuất bản</button>
                <button class="conponentMenu">
                    <img src="img/nhacc.jpg" alt="icon">Nhà cung cấp</button>
                <button class="conponentMenu">
                    <img src="img/khuvuc.jpg" alt="icon">Khu vực</button>
                <button class="conponentMenu" >
                    <img src="img/export.svg" alt="icon">Phiếu mượn</button>
                <button class="conponentMenu">
                    <img src="img/phieutra.jpg" alt="icon">Phiếu trả</button>
                <button class="conponentMenu">
                    <img src="img/phieuphat.jpg" alt="icon">Phiếu phạt</button>
                <button class="conponentMenu">
                    <img src="img/phieunhap.jpg" alt="icon">Phiếu nhập</button>
                <button id="tacVuThucThi" class="conponentMenu">
                    <img src="img/permission.svg" alt="icon">Phân quyền</button>
                <button class="conponentMenu">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon">Thống kê</button>
                <button class="conponentMenu" >
                    <img src="img/logout.jpg" alt="icon">Đăng xuất</button>
            </div>
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="img/permission.svg" alt="icon"> Quản lí Tài khoản và Phân quyền
                </p>
                <div style="display: flex; justify-content: space-between; height:90%" >
                    <div id="input">
                        <div class="input-group">
                            <label class="nameFeature">Mã nhân viên</label><br>
                            <input type="text" name="txtMaNV" id="txtMaNV" placeholder="Nhập mã nv" readonly>
                            <img src="img/add.svg" alt="mở table nhân viên" onclick="hienThiTBNV()"  style="cursor: pointer;width: 15px;height:auto;" />
                        </div>
                        <div class="input-group">
                            <label class="nameFeature">Mật khẩu</label><br>
                            <input type="password" name="txtMatKhau" id="txtMatKhau" placeholder="Nhập mật khẩu">
                        </div>
                        <div class="input-group">
                            <label class="nameFeature">Tác vụ/Quản lí</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="sách">Sách</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhân viên">Nhân viên</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="khách hàng">Khách hàng</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhà xuất bản">Nhà xuất bản</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhà cung cấp">Nhà cung cấp</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="phiếu mượn">Phiếu mượn</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="phiếu trả">Phiếu trả</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="phiếu phạt">Phiếu phạt</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="phiếu nhập">Phiếu nhập</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="phân quyền">Phân quyền</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="thống kê">Thống kê</label><br>
                        </div>
                        <div class="input-group">
                            <img class="iconChucNang"  src="img/add.svg" title="Thêm" onclick="sendData('add')">
                            <img class="iconChucNang" src="img/edit.svg" title="Sửa" onclick="sendData('edit')">
                            <img class="iconChucNang" src="img/delete.svg" title="Xóa" onclick="sendData('delete')">
                            <img class="iconChucNang" id="iconClear" src="img/clear.png" title="Clear Input" onclick="clearInputPQ()">
                        </div>
                    </div>

                    <div id="divtable">
                        <div style="margin-left: 2%;">
                            <select id="comBoBoxSearch" name="options">
                                <option value="mã nv">Mã NV</option>
                                <option value="mật khẩu">Mật khẩu</option>
                                <option value="tác vụ">Tác vụ</option>
                            </select> 
                            <input type="text" id="txtSearch" placeholder="Nhập thông tin">
                            <img class="iconChucNang" id="iconSearch" style="margin-right: 58%;" src="img/search1.png" title="Tìm kiếm" onclick="sendData('search')">
                            <img class="iconChucNang" id="iconFinish" src="img/refresh.svg" title="Tải lại table" onclick="sendData('finish')">
                        </div>
                        <table class="table" id="tablePQ">
                            <thead>
                                <tr>
                                    <th style="width: 25%;">Mã nhân viên</th>
                                    <th style="width: 25%;" >Mật khẩu</th>
                                    <th style="width: 50%;" >Tác vụ/Quản lí</th>
                                </tr>
                            </thead>
                            <tbody id="tbodyPQ"> 
                                <c:forEach var="pq" items="${requestScope.listPQ}">
                                    <tr>
                                        <td>${pq.maNV}</td>
                                        <td>${pq.matKhau}</td>
                                        <td>${pq.tacVu}</td>
                                    </tr>
                                </c:forEach>
                                <%
                                for (int i = 1; i <= 10; i++) {
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
                    <div id="divtableNV">
                        <img src="img/cancel.svg" alt="Đóng table nhân viên" onclick="dongTBNV()"
                             style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                        <h3 style="text-align: center;"> Ban  chọn mã nhân viên ở đây!</h3>
                        <div style="margin-left: 20px;">
                            <select id="comBoBoxSearch" name="options">
                                <option value="Mã NV">Mã NV</option>
                                <option value="Tên">Mật khẩu</option>
                            </select> 
                            <input type="text" id="txtSearch" placeholder="Nhập thông tin">
                            <img class="iconChucNang" id="iconSearchNV" onclick="searchOfNV"  src="img/search1.png" alt="icon">
                        </div>
                        <table class="table" id="tableNV">
                            <thead>
                                <tr>
                                    <th >Mã nhân viên</th>
                                    <th  >Họ</th>
                                    <th  >Tên</th>
                                    <th> số điện thoại</th>
                                    <th> Ngày sinh</th>
                                    <th> chức vụ</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1234</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
        <script>
            /*click table*/
            function clickPQ(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaNV').value = cells[0].innerText;
                document.getElementById('txtMaNV').disabled = true;
                document.getElementById('txtMatKhau').value = cells[1].innerText;
                const tacvu = cells[2].innerText;
                const checkboxes = document.querySelectorAll('.checkbox input[type="checkbox"]');
                checkboxes.forEach(checkbox => {
                    if (tacvu.includes(checkbox.value)) {
                        checkbox.checked = true;
                    } else
                        checkbox.checked = false;
                });
            }

            const rowsPQ = document.querySelectorAll('#tablePQ tbody tr');
            rowsPQ.forEach(row => {
                row.addEventListener('click', () => clickPQ(row));
            });
            function sendData(action) {
                const selectedTasks = Array.from(document.querySelectorAll('.checkbox input[type="checkbox"]:checked')
                        ).map(checkbox => checkbox.value);
                const formData = new URLSearchParams({
                    action: action,
                    maNV: document.getElementById('txtMaNV').value,
                    matKhau: document.getElementById('txtMatKhau').value,
                    tasks: selectedTasks.length ? selectedTasks.join(',') : '',
                    optionSearch: document.getElementById('comBoBoxSearch').value,
                    valueSearch: document.getElementById('txtSearch').value,
                });

                //alert(formData.get('tasks').split(',').length);
                fetch('http://localhost:9999/cnpm/phanquyen', {
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
                        .catch(error => alert('Đã xảy ra lỗi: ' + error));
            }
            function updateTable(results) {
                const tableBody = document.getElementById('tbodyPQ');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results); // Chuyển đổi đối tượng thành mảng các giá trị
                }
                results.forEach(item => {
                    const tacVuValues = item.tacVu.replace(/\[|\]/g, '').split(',').map(task => task.trim()).join(', ');
                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maNV || '';
                    row.appendChild(cell1);

                    const cell2 = document.createElement('td');
                    cell2.textContent = item.matKhau || '';
                    row.appendChild(cell2);

                    const cell3 = document.createElement('td');
                    cell3.textContent = tacVuValues || '';
                    row.appendChild(cell3);

                    tableBody.appendChild(row);
                });
            }

            /*clear dữ liệu*/
            function clearInputPQ() {
                document.getElementById('txtMaNV').value = "";
                document.getElementById('txtMaNV').disabled = false;
                document.getElementById('txtMatKhau').value = "";
                const checkboxes = document.querySelectorAll('.checkbox input[type="checkbox"]');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = false;
                });
            }
            //Đóng hiện thị table nhân viên
            function dongTBNV() {
                const tableNV = document.getElementById('divtableNV');
                tableNV.style.display = 'none';
            }
            //Hiện thị table nhân viên
            function hienThiTBNV() {
                const tableNV = document.getElementById('divtableNV');
                if (tableNV.style.display === 'none') {
                    tableNV.style.display = 'block';
                } else {
                    tableNV.style.display = 'none';
                }
            }
            function clickNV(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaNV').value = cells[0].innerText;
                //document.getElementById('txtMaNV').disabled=true;
                const tableNV = document.getElementById('divtableNV');
                tableNV.style.display = "none";

            }

            const rowsNV = document.querySelectorAll('#tableNV tbody tr');
            rowsNV.forEach(row => {
                row.addEventListener('click', () => clickNV(row));
            });
            // Chặn việc sử dụng tổ hợp phím tắt để phóng to/thu nhỏ
            document.addEventListener('keydown', function (event) {
                if (event.ctrlKey && (event.key === '+' || event.key === '-' || event.key === '=')) {
                    event.preventDefault();
                }
            });
            // Chặn thao tác phóng to bằng con lăn chuột
            document.addEventListener('wheel', function (event) {
                if (event.ctrlKey) {
                    event.preventDefault();
                }
            }, {passive: false});
        </script>
    </body>
</html>
