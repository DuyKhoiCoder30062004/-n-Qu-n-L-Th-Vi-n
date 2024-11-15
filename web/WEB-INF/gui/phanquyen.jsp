<%-- 
    Document   : phanquyen
    Created on : Oct 17, 2024, 10:53:06 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="DTO.Nhanvien_DTO" %>
<%
    Nhanvien_DTO nv = (Nhanvien_DTO) session.getAttribute("nv");
    String tasks = (String) session.getAttribute("tasks");
    if (nv == null) {
        response.sendRedirect("/cnpm/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta rel="icon" type="image/x-icon" href="">
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
        #title {
            width: 100%;
            height: 3%;
            background-color:white;
            font-size: 16px;
            font-weight: bolder;
            position: fixed;

            }

        #menu {
            width: 12%;
            height: 97%;
            background-color:#2C2F48;
            position: fixed;
            top: 3%;
            left: 0;
        }

        #detail {
            width: 88%;
            height: 97%;
            background-color:#D9D9D9;
            top: 3%;
            left: 12%;
            position: fixed;
            overflow-y: auto;
        }

        #iconAndName {
            height: 14.5%;
            margin-top: 0px;
            border-radius: 0px;
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
            border-bottom:1px solid white ;

        }

        .conponentMenu:hover {
            background-color:burlywood;
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
        #btnPhanQuyen
        {
            background-color: #5A4E9B;
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
            display: none; 
            z-index: 1000; 
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
                Hệ thống quản lí thư viện của trường Đại học ABC
            </div>

            <div id="menu">
                <div class="conponentMenu" id="iconAndName">
                    <img src="img/account.svg" alt="icon">
                    ${nv.ho}  ${nv.ten}<br>
                    ${nv.chucvu}
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
                <button id="btnNhaXuatBan" class="conponentMenu" value="nhà xuất bản" onclick="reDirect(this,'/cnpm/nhaxuatban')">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
                </button>
                <button id="btnNhaCungCap" class="conponentMenu" value="nhà cung cấp" onclick="reDirect(this,'/cnpm/nhacungcap')">
                    <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
                </button>
                <button id="btnKhuVuc" class="conponentMenu" value="khu vực" onclick="reDirect(this,'/cnpm/khuvuc')">
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
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="img/permission.svg" alt="icon"> Quản lí Tài khoản và Phân quyền
                </p>
                <div style="display: flex; justify-content: space-between; height:90%" >
                    <div id="input">
                        <div class="input-group">
                            <label class="nameFeature">Mã nhân viên</label><br>
                            <input type="text" name="txtMaNV" id="txtMaNV" placeholder="Nhập mã nv" readonly>
                            <img src="img/add.svg" alt="mở table nhân viên" onclick="hienThiTBNV()"
                                style="cursor: pointer;width: 15px;height:auto;" />
                        </div>
                        <div class="input-group">
                            <label class="nameFeature">Mật khẩu</label><br>
                            <input type="password" name="txtMatKhau" id="txtMatKhau" placeholder="Nhập mật khẩu">
                        </div>
                        <div class="input-group">
                            <label class="nameFeature">Tác vụ/Quản lí</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="sách">Sách</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhân viên">Nhân viên</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="đọc giả">Độc giả</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhà xuất bản">Nhà xuất bản</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="nhà cung cấp">Nhà cung cấp</label><br>
                            <label class="checkbox"><input type="checkbox" name="task" value="khu vực">Khu vực</label><br>
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
                    <div id="divtableNV" onclick="showAllRows()">
                        <img src="img/cancel.svg" alt="Đóng table nhân viên" onclick="dongTBNV()"
                             style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                        <h3 style="text-align: center;"> Ban  chọn mã nhân viên ở đây!</h3>
                        <div style="margin-left: 20px;">
                            <select id="comBoBoxSearchNV" name="options">
                                <option value="Mã NV">Mã NV</option>
                                <option value="Tên">Tên nhân viên</option>
                                <option value="Chức vụ">Chức vụ</option>
                            </select> 
                            <input type="text" id="txtSearchNV" placeholder="Nhập thông tin">
                            <img class="iconChucNang" id="iconSearchNV" src="img/search1.png" alt="icon">
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
                            <tbody id="tbodyNV">
                                <c:forEach var="nv" items="${requestScope.listNV}">
                                    <tr>
                                        <td>${nv.maNV}</td>
                                        <td>${nv.ho}</td>
                                        <td>${nv.ten}</td>
                                        <td>${nv.soDT}</td>
                                        <td>${nv.ngaySinh}</td>
                                        <td>${nv.chucVu} </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
        <script>
            alert("hiii");
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
                document.querySelectorAll('input[type="checkbox"][name="task"]').forEach(checkbox => {
                        checkbox.checked = false;
                        checkbox.disabled = false;
                 });
                if (cells[5].innerText === "admin") {
                    document.querySelectorAll('input[type="checkbox"][name="task"]').forEach(checkbox => {
                        checkbox.disabled = true;
                    });
                    document.querySelector('input[type="checkbox"][name="task"][value="phân quyền"]').checked = true;
                } else {
                    document.querySelector('input[type="checkbox"][name="task"][value="phân quyền"]').disabled = true;

                    if (cells[5].innerText === "quản lí") {
                        document.querySelector('input[type="checkbox"][name="task"][value="thống kê"]').checked = true;
                    } else {
                        document.querySelector('input[type="checkbox"][name="task"][value="thống kê"]').disabled = true;
                    }
                }
                const tableNV = document.getElementById('divtableNV');
                tableNV.style.display = "none";
                document.getElementById('txtMatKhau').focus();
            }


            const rowsNV = document.querySelectorAll('#tableNV tbody tr');
            rowsNV.forEach(row => {
                row.addEventListener('click', () => clickNV(row));
            });
            //Tìm kiếm trong bảng nhân viên
            function searchOfNV()
            {
                var value=document.getElementById('txtSearchNV').value.toLowerCase();
                var option=document.getElementById('comBoBoxSearchNV').value;
                 var rows = document.querySelectorAll('#tbodyNV tr');
                if (!value) {
                    alert("Vui lòng nhập thông tin để tìm kiếm nhân viên!");
                    return; 
                }
                var columnIndex=0;
                if(option ==="Tên")
                    columnIndex=2;
                else if(option=="Chức vụ")
                    columnIndex=4;
                rows.forEach(row =>{
                    var cell=row.getElementsByTagName('td')[columnIndex];
                    if(cell && cell.textContent.toLowerCase().includes(value)){
                        row.style.display="";
                    }
                    else row.style.display="none";
                });
            }
            document.getElementById('iconSearchNV').addEventListener('click', function(event) {
                event.stopPropagation(); // Ngăn chặn sự kiện click lan ra divTableNV
                searchOfNV(); 
            });
            //hiện thị hết dữ liệu của table nhân viên
            function showAllRows() {
                var rows = document.querySelectorAll('#tbodyNV tr');
                rows.forEach(row => {
                    row.style.display = ""; // Hiển thị tất cả các hàng
                });
                document.getElementById('txtSearchNV').value="";
            }
            
            document.getElementById('btnDangXuat').addEventListener('click', function(event) {
                event.preventDefault();  
                window.location.href = '/cnpm/login';  
            });
            function reDirect(button,url)
            {
                event.preventDefault(); 
                var tasks = "<%= tasks %>";
                if(tasks.includes(button.value))
                {
                    window.location.href =url;
                }
                else
                {
                    alert("Bạn không có quyền quản lí tác vụ "+ button.value);
                }
            }
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
