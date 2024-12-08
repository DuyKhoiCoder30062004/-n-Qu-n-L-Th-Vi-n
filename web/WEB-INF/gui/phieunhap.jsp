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
        <meta rel="icon" type="image/x-icon" href="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lí phiếu nhập </title>
        <style>
            body,
            html {
                margin: 0;
                padding: 0;
                height: 100%;
                font-family: 'Times New Roman', Times, serif;
            }

            #form {
                display: flex;
                height: 100vh;

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
                background-color:#FFB6C1;
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

            .componentMenu {
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

            .componentMenu:hover {
                background-color:burlywood;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .componentMenu img {
                width: 20%;
                height: auto;
                margin-right: 10%;
                margin-left: 10%;
            }

            #iconAndName img {
                width: 40%;
                border-radius: 50%;
                margin-right: 0%;
                margin-left: 0%;
            }

            #btnPhieuNhap {
                background-color: #5A4E9B;
            }

            #titleDetail {
                font-size: 20px;
                align-items: center;
                display: flex;
                color:  #333333;
                font-weight: bolder;
                text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
                ;
            }

            #imgDetail {
                width: 3%;
                height: auto;
                margin-left: 38%;
                margin-right: 1%;
            }

            .input-group {
                display: inline;
                align-items: center;
                margin-left: 9%;
            }

            .nameFeature {
                font-weight: bold;
                margin-right: 7px;
            }

            .input-group input,
            .input-groupCTPN input {
                width: 100px;
                margin-bottom: 5px;
            }

            #iconNgayNhap,
            #iconHanChot {
                width: 10px;
                height: auto;
            }

            .iconChucNang {
                width: 20px;
                height: auto;
                margin-left: 30px;
                vertical-align: middle;
                cursor: pointer;
            }

            .table {
                width: 90%;
                border-collapse: collapse;
                margin-top: 2px;
                margin-left: 2%;
                table-layout: fixed;
                border: 2px solid black;
                height: 200px;
                display: block;
                overflow-y: auto;
                overflow-x: hidden;
            }

            .table thead {
                display: table;
                /* Để hiển thị hàng tiêu đề */
                width: 100%;
                table-layout: fixed;
                position: sticky;
                /* Cố định vị trí hàng tiêu đề */
                top: 0;
                /* Gắn hàng tiêu đề ở trên cùng */
                background-color: #D9D9D9;
                /* Màu nền cho hàng tiêu đề */
                z-index: 1;
                /* Đảm bảo hàng tiêu đề ở trên các hàng khác */
            }

            .table tr {
                display: table;
                width: 100%;
                table-layout: fixed;
                height: 25px;
            }

            .table tbody {
                display: block;
                overflow-y: auto;
                overflow-x: hidden;
                width: 100%;
            }

            .table th,td {
                border: 1px solid black;
                text-align: center;
                word-wrap: break-word;
                /* Ngắt dòng nếu nội dung quá dài */
                white-space: nowrap;
                /* Không cho phép ngắt dòng nếu cần */
                overflow: hidden;
                /* Ẩn phần nội dung tràn ra ngoài */
                text-overflow: ellipsis;
                /* Hiển thị "..." khi nội dung quá dài */
            }

            .table td:hover {
                white-space: normal;
                /* Cho phép ngắt dòng */
                z-index: 1;
                background-color: #f1f1f1;
                /* Tô màu nền để dễ đọc */
            }

            #detailCTPN {
                margin-top: 1%;
            }

            #sectionImportImformationCTPN {
                margin-left: 5%;
                width: 19%;
                margin-right: 30px;

            }

            #sectionTableCTPN {
                width: 76%;
            }
            .input-groupCTPN label{
                width: 90px;
            }
            .input-groupCTPN {
                display: flex;
                margin-top: 10px;
                align-items: center;
            }

            .nameFeatureCTPN {
                font-weight: bold;
                margin-right: 10px;
            }

            #titleCTPN {
                width: 100%;
                text-align: center;
                font-size: 20px;
                color: #333333;
                font-weight: bolder;
                text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
            }

            .iconChucNangCTPN {
                width: 20px;
                height: auto;
                margin-right: 10px;
                margin-top: 10px;
                cursor: pointer;
            }

            input:focus{
                outline: none;
                border-color: darkturquoise;
            }
        </style>

    </head>
    <body>
        <form id="form">
            <div id="title">Hệ thống quản lí thư viện của trường đại học ABC</div>
            <div id="menu">
                <div class="componentMenu" id="iconAndName">
                    <img src="img/account.svg" alt="icon">
                    ${nv.getHo()}  ${nv.getTen()}<br>
                    ${nv.getChucVu()}
                </div>
                <button id="btnSach" class="componentMenu" onclick="reDirect(this, '/cnpm/sach')">
                    <img src="img/sach.jpg" alt="icon" value="sách" > Sách
                </button>
                <button id="btnNhanVien" class="componentMenu" value="nhân viên" onclick="reDirect(this, '/cnpm/nhanvien')">
                    <img src="img/stafff.svg" alt="icon"> Nhân viên
                </button>
                <button id="btnDocGia" class="componentMenu" value="độc giả" onclick="reDirect(this, '/cnpm/docgia')">
                    <img src="img/customerr.svg" alt="icon"> Độc giả
                </button>
                <button id="btnNhaXuatBan" class="componentMenu" value="nhà xuất bản" onclick="reDirect(this, '/cnpm/nxb')">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
                </button>
                <button id="btnNhaCungCap" class="componentMenu" value="nhà cung cấp" onclick="reDirect(this, '/cnpm/ncc')">
                    <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
                </button>
                <button id="btnKhuVuc" class="componentMenu" value="khu vực" onclick="reDirect(this, '/cnpm/kv')">
                    <img src="img/khuvuc.jpg" alt="icon"> Khu vực
                </button>
                <button id="btnPhieuMuon" class="componentMenu" value="phiếu mượn" onclick="reDirect(this, '/cnpm/phieumuon')">
                    <img src="img/export.svg" alt="icon"> Phiếu mượn
                </button>
                <button id="btnPhieuTra" class="componentMenu" value="phiếu trả" onclick="reDirect(this, '/cnpm/phieutra')">
                    <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
                </button>
                <button id="btnPhieuPhat" class="componentMenu" value="phiếu phạt" onclick="reDirect(this, '/cnpm/phieuphat')">
                    <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
                </button>
                <button id="btnPhieuNhap" class="componentMenu" value="phiếu nhập" onclick="reDirect(this, '/cnpm/phieunhap')">
                    <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
                </button>
                <button id="btnPhanQuyen" class="componentMenu" value="phân quyền" onclick="reDirect(this, '/cnpm/phanquyen')">
                    <img src="img/permission.svg" alt="icon"> Phân quyền
                </button>
                <button id="btnThongKe" class="componentMenu" value="thống kê" onclick="reDirect(this, '/cnpm/thongke')">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon"> Thống kê
                </button>
                <button id="btnDangXuat" class="componentMenu">
                    <img src="img/logout.jpg" alt="icon"> Đăng xuất
                </button>
            </div>
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="img/phieunhap.jpg" alt="icon"> Quản lí phiếu nhập
                </p>
                <section id="detailPN">
                    <div class="input-group">
                        <label class="nameFeature">Mã phiếu </label>
                        <input type="text" id="txtmaPN" placeholder="Nhập mã phiếu">
                    </div>
                    <div class="input-group">
                        <label class="nameFeature">Mã NCC</label>
                        <input type="text" id="txtMaNCC" placeholder="Nhập mã ncc">
                    </div>
                    <div class="input-group">
                        <label class="nameFeature" style="margin-right:25px;">Mã NV </label>
                        <input type="text" id="txtMaNV" value="${nv.getMaNV()}">
                        <img class="iconChucNang" id="iconThem" src="img/add.svg" title="Thêm PN" onclick="sendDataPN('add')">
                        <img class="iconChucNang" id="iconXoa" src="img/delete.svg" title="Xóa PN" onclick="sendDataPN('delete')">
                    </div><br>
                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 11px;">Ngày lập </label>
                        <input type="date" id="txtNgayLap" style="width: 104px;" placeholder="Chọn ngày nhập">
                    </div>
                    <div class="input-group" >
                        <label class="nameFeature" style="margin-right:10px;" >Tổng SL </label>
                        <input type="text" id="txtTongSL" placeholder="Tổng số lượng" readonly value="0" >
                    </div>
                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 4px">Tổng Tiền </label>
                        <input type="text" id="txtTongTien" value="0" readonly>
                        <img class="iconChucNang" id="iconSua" src="img/edit.svg" title="Sửa PN" onclick="sendDataPN('edit')">
                        <img class="iconChucNang" id="iconClear" title="Clear input"  onclick="clearInputPN()" src="img/clear.png" alt="icon">
                    </div><br>
                    <div class="input-group" style="margin-top: 10px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã phiếu">Mã phiếu</option>
                            <option value="Mã NCC">Mã NCC</option>
                            <option value="Mã NV">Mã NV</option>
                        </select>
                        <input type="text" id="txtSearchPN" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearch" title="Tìm kiếm PN" style="margin-left: 0px;" src="img/search1.png" alt="icon" onclick="sendDataPN('search')">
                        <img class="iconChucNang" id="iconImportExcel" style="margin-left: 44.9%;" src="img/import_excel.svg"
                             alt="icon" onclick="selectFile('import')"title="importExcel">
                        <img class="iconChucNang" id="iconExportExcel" src="img/export_excel.svg" alt="icon" onclick="selectFile('export')" title="exportESxcel">
                        <input  type="file" id="fileExcel" style="display:none;">
                        <img class="iconChucNang" id="iconPrint" title="PrintPN"  src="img/print.jpg" alt="icon" onclick="sendDataPN('print')">
                    </div>
                    <table class="table" id="tablePN">
                        <thead>
                            <tr>
                                <th>Mã phiếu</th>
                                <th>Mã NCC</th>
                                <th>Mã NV</th>
                                <th>Ngày lập</th>
                                <th>Tổng SL</th>
                                <th>Tổng Tiền</th>
                            </tr>
                        </thead>
                        <tbody id="tbodyPN">
                            <c:forEach var="pn" items="${requestScope.listPN}">
                                <tr>
                                    <td>${pn.maPN}</td>
                                    <td>${pn.maNCC}</td>
                                    <td>${pn.maNV}</td>
                                    <td>${pn.ngayLap}</td>
                                    <td>${pn.tongSL}</td>
                                    <td>${pn.tongTien}</td>
                                </tr>
                            </c:forEach>
                            <%
                            for (int i = 1; i <= 10; i++) {
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
                </section>

                <section id="detailCTPN">
                    <p id="titleCTPN">Chi tiết phiếu nhập</p>
                    <div style="display: flex;
                         justify-content: space-between; ">
                        <div id="sectionImportImformationCTPN">
                            <div class="input-groupCTPN" style="margin-top: 40px;">
                                <label class="nameFeatureCTPN" >Mã phiếu</label>
                                <input type="text" id="txtmaPNCTPN" placeholder="Nhập mã phiếu">
                            </div>
                            <div class="input-groupCTPN">
                                <label class="nameFeatureCTPN" >Mã sách</label>
                                <input type="text" id="txtSachCTPN" placeholder="Nhập mã sách" style="margin-right: 2px">
                                <img src="img/add.svg"
                                     style="cursor: pointer;
                                     width: 12px;
                                     height:auto;" />
                            </div>
                            <div class="input-groupCTPN">
                                <label class="nameFeatureCTPN" style="margin-right: 10px;" >Danh sách mã vạch</label>
                                <input  type="text" id="txtListMaVachCTPN" placeholder="Nhập mã vạch">
                            </div>
                            <div class="input-groupCTPN">
                                <label class="nameFeatureCTPN" >Đơn giá</label>
                                <input type="text" id="txtDonGiaCTPN" placeholder="Nhập đơn giá">
                            </div>
                            <div class="input-groupCTPN">
                                <label class="nameFeatureCTPN" >Số lượng</label>
                                <input type="text" id="txtSLCTPN" value="0">
                            </div>
                            <div>
                                <img class="iconChucNangCTPN" id="iconThemCTPN" style="margin-left: 24%;"
                                     src="img/add.svg" title="Thêm CTPN" onclick="sendDataCTPN('addCTPN')">
                                <img class="iconChucNangCTPN" id="iconXoaCTPN" src="img/delete.svg" title="Xóa CTPN" onclick="sendDataCTPN('deleteCTPN')">
                                <img class="iconChucNangCTPN" id="iconSuaCTPN" src="img/edit.svg" title="Sửa CTPN" onclick="sendDataCTPN('updateCTPN')">
                                <img class="iconChucNangCTPN" id="iconClearCTPN" onclick="clearInputCTPN()"
                                     src="img/clear.png" title="clear input CTPN">
                            </div>
                        </div>
                        <div id="sectionTableCTPN">
                            <div class="input-groupCTPN" style="margin-left:20px;">
                                <select id="comBoBoxSearchCTPN" name="options">
                                    <option value="Mã phiếu">Mã phiếu</option>
                                    <option value="Mã sách">Mã sách</option>
                                    <option value="Mã vạch">Mã vạch</option>
                                    <option value="Đơn giá">Đơn giá</option>
                                    <option value="Số lượng">Số lượng</option>
                                </select>
                                <input type="text" id="txtSearchCTPN" style="margin-left: 5px;
                                       margin-right: 5px;"
                                       placeholder="Nhập thông tin">
                                <img class="iconChucNang" id="iconSearchCTPN" style="margin-right: 58%;
                                     margin-left: 0px;"
                                     src="img/search1.png" title="Tìm kiếm CTPN" onclick="sendDataCTPN('searchCTPN')">
                            </div>
                            <table class="table" id="tableCTPN">
                                <thead>
                                    <tr>
                                        <th>Mã phiếu</th>
                                        <th>Mã sách</th>
                                        <th>Danh sách mã vạch</th>
                                        <th>Đơn giá</th>
                                        <th>Số lượng</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyCT">
                                    <c:forEach var="ctpn" items="${requestScope.listCTPN}">
                                        <tr>
                                            <td>${ctpn.maPN}</td>
                                            <td>${ctpn.maSach}</td>
                                            <td>${ctpn.maVach}</td>
                                            <td>${ctpn.donGia}</td>
                                            <td>${ctpn.soLuong}</td>
                                        </tr>
                                    </c:forEach>
                                    <%
                                    for (int i = 1; i <= 10; i++) {
                                    %>
                                    <tr>
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
                </section>
            </div>
        </form>
        <script>
            let namePath = "";

            // Hàm xử lý khi một dòng trong bảng Phiếu Nhập được click
            function clickPN(row) {
                const cells = row.getElementsByTagName('td');

                // Lấy giá trị từ từng ô của dòng được click và gán cho các trường tương ứng
                document.getElementById('txtmaPN').value = cells[0].innerText.trim();
                document.getElementById('txtMaNCC').value = cells[1].innerText.trim();
                document.getElementById('txtMaNV').value = cells[2].innerText.trim();

                // Định dạng giá trị ngày lập
                const dateValueNL = cells[3].innerText.trim();
                document.getElementById('txtNgayLap').value = dateValueNL;

                // Gán các giá trị khác
                document.getElementById('txtTongSL').value = cells[4].innerText.trim();
                document.getElementById('txtTongTien').value = cells[5].innerText.trim();
            }

            // Hàm xử lý khi một dòng trong bảng Chi Tiết Phiếu Nhập được click
            function clickCTPN(row) {
                const cells = row.getElementsByTagName('td');

                // Lấy giá trị từ từng ô của dòng được click và gán cho các trường tương ứng
                document.getElementById('txtmaPNCTPN').value = cells[0].innerText.trim();
                document.getElementById('txtSachCTPN').value = cells[1].innerText.trim();
                document.getElementById('txtSLCTPN').value = cells[2].innerText.trim();
                document.getElementById('txtDonGiaCTPN').value = cells[3].innerText.trim();
            }

            // Gắn sự kiện click vào từng dòng trong bảng Phiếu Nhập
            function addEventListenersForPN() {
                const rowsPN = document.querySelectorAll('#tablePN tbody tr');
                rowsPN.forEach(row => {
                    row.addEventListener('click', () => clickPN(row));
                });
            }

            // Gắn sự kiện click vào từng dòng trong bảng Chi Tiết Phiếu Nhập
            function addEventListenersForCTPN() {
                const rowsCTPN = document.querySelectorAll('#tableCTPN tbody tr');
                rowsCTPN.forEach(row => {
                    row.addEventListener('click', () => clickCTPN(row));
                });
            }

            // Khởi tạo sự kiện khi trang được tải
            document.addEventListener('DOMContentLoaded', () => {
                addEventListenersForPN();
                addEventListenersForCTPN();
            });

            function formatDate(date) {
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
                const day = String(date.getDate()).padStart(2, '0');
                return `${year}-${month}-${day}`;
                    }

                    function clearInputPN() {
                        document.getElementById('txtmaPN').value = '';
                        document.getElementById('txtMaNCC').value = '';
                        document.getElementById('txtMaNV').value = '';
                        document.getElementById('txtTongSL').value = '0';
                        document.getElementById('txtTongTien').value = '0';
                        updateDefaultDates();
                    }

                    function clearInputCTPN() {
                        document.getElementById('txtmaPNCTPN').value = '';
                        document.getElementById('txtSachCTPN').value = '';
                        document.getElementById('txtSLCTPN').value = '';
                        document.getElementById('txtDonGiaCTPN').value = '';
                    }

                    function sendDataPN(action) {
                        const formData = new URLSearchParams({
                            action: action,
                            maPN: document.getElementById('txtmaPN').value,
                            maNCC: document.getElementById('txtMaNCC').value,
                            maNV: document.getElementById('txtMaNV').value,
                            ngayLap: document.getElementById('txtNgayLap').value,
                            tongSL: document.getElementById('txtTongSL').value,
                            tongTien: document.getElementById('txtTongTien').value,
                            optionSearch: document.getElementById('comBoBoxSearch').value,
                            valueSearch: document.getElementById('txtSearchPN').value,
                            nameFileExcel: namePath,
                        });
                        console.log(formData.toString());

                        fetch('http://localhost:9999/cnpm/phieunhap', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            body: formData.toString()
                        })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('Đã xảy ra lỗi trong quá trình gửi dữ liệu');
                                    }
                                    return response.text();
                                })
                                .then(content => {
                                    try {
                                        const data = JSON.parse(content);
                                        if (data.thongbao) {
                                            alert(data.thongbao);
                                        }
                                        if (data.resultsPN && data.resultsPN.length > 0) {
                                            alert(data.resultsPN);
                                            alert(data.resultsCTPN);
                                            kQTimKiemPN(data.resultsPN); // Hàm xử lý kết quả Phiếu Nhập
                                            kQTimKiemCTPN(data.resultsCTPN); // Hàm xử lý kết quả Chi Tiết Phiếu Nhập
                                        }
                                        if (data.hopLe) {
                                            window.location.reload();
                                        }
                                    } catch (e) {
                                        if (content.startsWith('<')) {
                                            if (action === 'print') {
                                                // Hiển thị nội dung HTML và in
                                                const printWindow = window.open('', '_blank');
                                                printWindow.document.write('<html><head><title>In Phiếu Nhập</title></head><body>');
                                                printWindow.document.write(content);
                                                printWindow.document.write('</body></html>');
                                                printWindow.document.close();
                                                printWindow.print();
                                            }
                                        } else {
                                            alert('Đã xảy ra lỗi trong quá trình xử lý dữ liệu.');
                                        }
                                    }
                                })
                                .catch(error => alert('Đã xảy ra lỗi: ' + error));
                    }
                    function sendDataCTPN(action) {
                        const formData = new URLSearchParams({
                            action: action,
                            maPN: document.getElementById('txtmaPNCTPN').value,
                            maSach: document.getElementById('txtSachCTPN').value,
                            listMaVach: document.getElementById('txtListMaVachCTPN').value,
                            soLuong: document.getElementById('txtSLCTPN').value,
                            donGia: document.getElementById('txtDonGiaCTPN').value,
                            optionSearch: document.getElementById('comBoBoxSearchCTPN').value,
                            valueSearch: document.getElementById('txtSearchCTPN').value,
                        });

                        fetch('http://localhost:9999/cnpm/ctpn', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            body: formData.toString(),
                        })
                                .then(response => {
                                    if (!response.ok)
                                        throw new Error('Đã xảy ra lỗi trong quá trình gửi dữ liệu ' + response.message);
                                    return response.json();
                                })
                                .then(data => {
                                    if (data.thongbao)
                                        alert(data.thongbao); // Hiển thị thông báo từ server
                                    if (data.results && data.results.length > 0)
                                    {
                                        kQTimKiemCTPN(data.results);
                                    }
                                    if (data.hopLe)
                                        window.location.reload();
                                })
                                .catch(error => alert('Đã xảy ra lỗi: ' + error));
                    }

                    function kQTimKiemPN(results) {
                        const tableBody = document.getElementById('tbodyPN');
                        tableBody.innerHTML = '';
                        results.forEach(item => {
                            const row = document.createElement('tr');
                            const cell1 = document.createElement('td');
                            cell1.textContent = item.maPN || '';
                            row.appendChild(cell1);
                            const cell2 = document.createElement('td');
                            cell2.textContent = item.maNCC || '';
                            row.appendChild(cell2);
                            const cell3 = document.createElement('td');
                            cell3.textContent = item.maNV || '';
                            row.appendChild(cell3);
                            const cell4 = document.createElement('td');
                            cell4.textContent = item.ngayLap || '';
                            row.appendChild(cell4);
                            const cell5 = document.createElement('td');
                            cell5.textContent = item.tongSL || '';
                            row.appendChild(cell5);
                            const cell6 = document.createElement('td');
                            cell6.textContent = item.tongTien || '';
                            row.appendChild(cell6);
                            tableBody.appendChild(row);
                        });
                    }

                    // Hiển thị kết quả tìm kiếm Chi Tiết Phiếu Nhập
                    function kQTimKiemCTPN(results) {
                        const tableBody = document.getElementById('tbodyCT');
                        tableBody.innerHTML = '';
                        results.forEach(item => {
                            //                            const listMaVach = item.listMaVach.replace(/\[|\]/g, '').split(',').map(task => task.trim()).join(', ');
                            const row = document.createElement('tr');
                            const cell1 = document.createElement('td');
                            cell1.textContent = item.maPN || '';
                            row.appendChild(cell1);
                            const cell2 = document.createElement('td');
                            cell2.textContent = item.maSach || '';
                            row.appendChild(cell2);
                            const cell3 = document.createElement('td');
                            cell3.textContent = item.listMaVach || '';
                            row.appendChild(cell3);
                            const cell4 = document.createElement('td');
                            cell4.textContent = item.donGia || '';
                            row.appendChild(cell4);
                            const cell5 = document.createElement('td');
                            cell5.textContent = item.soLuong || '';
                            row.appendChild(cell5);
                            tableBody.appendChild(row);
                        });
                    }

                    function selectFile(action) {
                        // Lắng nghe sự kiện thay đổi của input file
                        alert("Vui lòng chọn hoặc tạo file excel để " + action + " ở trong thư mục C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/");
                        const fileInput = document.getElementById("fileExcel");
                        fileInput.onchange = function () {
                            if (fileInput.files.length > 0) {
                                // Gán đường dẫn file vào biến toàn cục
                                namePath = fileInput.files[0].name;
                                sendDataPN(action);
                            }
                        };

                        // Hiển thị cửa sổ chọn file
                        fileInput.click();
                    }
                    //                    document.addEventListener('DOMContentLoaded', function () {
                    //                        document.getElementById('btnAddPN').addEventListener('click', function () {
                    //                            sendDataPN();
                    //                        });
                    //                    });
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