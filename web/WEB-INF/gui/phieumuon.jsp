<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta rel="icon" type="image/x-icon" href="">
        <title>Quản lí phiếu mượn</title>
    </head>
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
            background-color: #D9D9D9;
            font-size: 16px;
            font-style: Italic;
            position: fixed;

        }

        #menu {
            width: 12%;
            height: 97%;
            background-color: #99B3A1;
            position: fixed;
            top: 3%;
            left: 0;
        }

        #detail {
            width: 88%;
            height: 97%;
            background-color: #F5A9FC;
            top: 3%;
            left: 12%;
            position: fixed;
        }

        #iconAndName {
            height: 14.5%;
            margin-top: 0px;
            border-radius: 0px;
        }

        .conponentMenu {
            width: 100%;
            border-radius: 10px;
            height: 6%;
            margin-top: 2%;
            font-weight: bold;
            text-align: center;
            display: flex;
            /* Thay đổi để sử dụng flexbox */
            align-items: center;
            /* Căn giữa theo chiều dọc */
            justify-content: flex-start;
            /* Căn giữa theo chiều ngang */
            border: #D9D9D9;
            background-color: #D9D9D9;
        }

        .conponentMenu:hover {
            background-color: #ffffff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .conponentMenu img {
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

        #tacVuThucThi {
            background-color: #00CED1;
        }

        #titleDetail {
            font-size: 20px;
            align-items: center;
            display: flex;
            color: #FB0606;
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
        .input-groupCTPM input {
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

        #tablePM {
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

        #detailCTPM {
            margin-top: 1%;
        }

        #sectionImportImformationCTPM {
            margin-left: 9%;
            width: 15%;
            margin-right: 30px;

        }

        #sectionTableCTPM {
            width: 76%;
        }

        .input-groupCTPM {
            display: flex;
            margin-top: 10px;
            align-items: center;
        }

        .nameFeatureCTPM {
            font-weight: bold;
            margin-right: 10px;
        }

        #titleCTPM {
            width: 100%;
            text-align: center;
            font-size: 20px;
            color: #FB0606;
            font-weight: bolder;
            text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
        }

        .iconChucNangCTPM {
            width: 20px;
            height: auto;
            margin-right: 10px;
            margin-top: 10px;
            cursor: pointer;
        }

        #tableCTPM {
            width: 90%;
            height: 240px;
            width: 90%;
            border-collapse: collapse;
            table-layout: fixed;
            border: 1px solid black;
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }
        #divtableNV,#divtableKhach,#divtableSach {
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

        #tableNV,#tableSach,#tableKhach {
            border-collapse: collapse;
            width: 95%;
            height: 70%;
            border: 2px solid black;
            margin-left: 20px;
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
                    <img src="./anh/ionCV.png" alt="icon">
                    Nguyễn Trung
                    Quản lí
                </div>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon"> Sách</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Nhân viên</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Khách hàng</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Nhà xuất bản</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Nhà cung cấp</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Danh mục</button>
                <button class="conponentMenu" id="tacVuThucThi">
                    <img src="./anh/ionCV.png" alt="icon">Phiếu mượn</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Phiếu trả</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Phiếu phạt</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Phiếu nhập</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Phân quyền</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Thống kê</button>
                <button class="conponentMenu">
                    <img src="./anh/ionCV.png" alt="icon">Đăng xuất</button>
            </div>
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="./anh/ionCV.png" alt="icon"> Quản lí phiếu mượn
                </p>
                <section id="detailPM">
                    <div class="input-group">
                        <label class="nameFeature">Mã phiếu </label>
                        <input type="text" id="txtMaPhieu" placeholder="Nhập mã phiếu">
                    </div>
                    <div class="input-group">
                        <label class="nameFeature">Mã khách </label>
                        <input type="text" id="txtMaKhach" placeholder="Nhập mã khách">
                        <img src="./anh/ionCV.png"  onclick="hienThiKhach()" style="cursor: pointer;width: 10px;height:auto;" />
                    </div>

                    <div class="input-group">
                        <label class="nameFeature">Mã NV </label>
                        <input type="text" id="txtMaNV" placeholder="Nhập mã NV">
                        <img src="./anh/ionCV.png"  onclick="hienThiTBNV()" style="cursor: pointer;width: 10px;height:auto;" />
                        <img class="iconChucNang" id="iconThem" src="./anh/ionCV.png" title="Thêm PM" onclick="sendData('add')">
                        <img class="iconChucNang" id="iconXoa" src="./anh/ionCV.png" title="Xóa PM" onclick="sendData('delete')">
                    </div><br>
                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 11px;">Ngày lập </label>
                        <input type="date" id="txtNgayLap" style="width: 104px;" placeholder="Chọn ngày nhập">
                    </div>

                    <div class="input-group" style="margin-right: 13px;">
                        <label class="nameFeature" style="margin-right: 12px;">Hạn chót </label>
                        <input type="date" id="txtHanChot" style="width: 104px;" placeholder="Chọn hạn chót">
                    </div>

                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 0px">Tổng SL </label>
                        <input type="text" id="txtTongSL" value="0" style="margin-right: 13px" readonly>
                        <img class="iconChucNang" id="iconSua" src="./anh/ionCV.png" title="Sửa PM" onclick="sendData('edit')">
                        <img class="iconChucNang" id="iconClear" title="Clear input"  onclick="clearInputPM()" src="./anh/ionCV.png" alt="icon">
                    </div><br>
                    <div class="input-group" style="margin-top: 10px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã phiếu">Mã phiếu</option>
                            <option value="Mã độc giả">Mã độc giả</option>
                            <option value="Mã NV">Mã NV</option>
                            <option value="Ngày lập">Ngày lập</option>
                        </select>
                        <input type="text" id="txtSearchPM" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearch" title="Tìm kiếm PM" style="margin-left: 0px;" src="./anh/ionCV.png" alt="icon"
                             onclick="sendData('search')">
                        <img class="iconChucNang" id="iconImportExcel" title="ImportExcel" style="margin-left: 44.9%;" src="./anh/ionCV.png"
                             alt="icon" onclick="sendData('import')">
                        <img class="iconChucNang" id="iconExportExcel" title="ExportExcel" src="./anh/ionCV.png" alt="icon"
                             onclick="sendData('export')">
                        <img class="iconChucNang" id="iconPrint" title="PrintPM"  src="./anh/ionCV.png" alt="icon"
                             onclick="sendData('print')">
                        <img class="iconChucNang" id="iconFinish" title="Load lại table" src="./anh/ionCV.png" alt="icon"
                             onclick="sendData('finish')">
                    </div>
                    <table class="table" id="tablePM">
                        <thead>
                            <tr>
                                <th>Mã phiếu</th>
                                <th>Mã độc giả</th>
                                <th>Mã NV</th>
                                <th>Ngày lập</th>
                                <th>Hạn chót</th>
                                <th>Tổng số lượng</th>
                            </tr>
                        </thead>
                        <tbody id="tbodyPM">
                            <c:forEach var="pm" items="${requestScope.listPM}">
                                <tr>
                                    <td>${pm.maPM}</td>
                                    <td>${pm.maKhach}</td>
                                    <td>${pm.maNV}</td>
                                    <td>${pm.ngayLap}</td>
                                    <td>${pm.hanChot}</td>
                                    <td>${pm.tongSL}</td>
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

                <section id="detailCTPM">
                    <p id="titleCTPM">Chi tiết phiếu mượn</p>
                    <div style="display: flex; justify-content: space-between; ">
                        <div id="sectionImportImformationCTPM">
                            <div class="input-groupCTPM" style="margin-top: 40px;">
                                <label class="nameFeatureCTPM" style="margin-right: 10px;">Mã phiếu</label>
                                <input type="text" id="txtMaPhieuCTPM" placeholder="Nhập mã phiếu">
                            </div>
                            <div class="input-groupCTPM">
                                <label class="nameFeatureCTPM" style="margin-right: 18px;">Mã sách</label>
                                <input type="text" id="txtSachCTPM" placeholder="Nhập mã sách" style="margin-right: 2px">
                                <img src="./anh/ionCV.png" onclick="hienThiSach()"
                                     style="cursor: pointer;width: 10px;height:auto;" />
                            </div>
                            <div class="input-groupCTPM">
                                <label class="nameFeatureCTPM" style="margin-right: 15px;">Số lượng</label>
                                <input type="text" id="txtSLCTPM" placeholder="Nhập số lượng">
                            </div>
                            <div class="input-groupCTPM">
                                <label class="nameFeatureCTPM" style="margin-right: 5px;">Trạng thái</label>
                                <input type="text" id="txtTrangThaiCTPM" value="Đang mượn" readonly>
                            </div>
                            <div>
                                <img class="iconChucNangCTPM" id="iconThemCTPM" style="margin-left: 24%;"
                                     src="./anh/ionCV.png" title="Thêm CTPM">
                                <img class="iconChucNangCTPM" id="iconXoaCTPM" src="./anh/ionCV.png" title="Xóa CTPM">
                                <img class="iconChucNangCTPM" id="iconSuaCTPM" src="./anh/ionCV.png" title="Sửa CTPM">
                                <img class="iconChucNangCTPM" id="iconClearCTPM" onclick="clearInputCTPM()"
                                     src="./anh/ionCV.png" title="clear input CTPM">
                            </div>
                        </div>
                        <div id="sectionTableCTPM">
                            <div class="input-groupCTPM">
                                <select id="comBoBoxSearchCTPM" name="options">
                                    <option value="Mã phiếu">Mã phiếu</option>
                                    <option value="Mã sách">Mã sách</option>
                                    <option value="Số lượng">Số lượng</option>
                                    <option value="Trạng thái">Trạng thái</option>
                                </select>
                                <input type="text" id="txtSearchCTPM" style="margin-left: 5px;margin-right: 5px;"
                                       placeholder="Nhập thông tin">
                                <img class="iconChucNang" id="iconSearchCTPM" style="margin-right: 58%;margin-left: 0px;"
                                     src="./anh/ionCV.png" title="Tìm kiếm CTPM">
                                <img class="iconChucNang" id="iconFinishCTPM" src="./anh/ionCV.png" title="Tải lại table">
                            </div>
                            <table class="table" id="tableCTPM">
                                <thead>
                                    <tr>
                                        <th>Mã phiếu</th>
                                        <th>Mã sách</th>
                                        <th>Số lượng</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ctpm" items="${requestScope.listCTPM}">
                                        <tr>
                                            <td>${ctpm.maPM}</td>
                                            <td>${ctpm.maSach}</td>
                                            <td>${ctpm.soLuong}</td>
                                            <td>${ctpm.trangthai}</td>
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
                                    </tr>
                                    <%
                                        }
                                    %>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>
                <div class="divTT" id="divtableNV">
                    <img src="./anh/ionCV.png"  onclick="dongTBTT()"  style="cursor: pointer;width: 15px;height:auto;margin-left: 98%;" />
                    <h3 style="text-align: center;"> Ban chọn mã nhân viên ở đây!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã NV">Mã NV</option>
                            <option value="Tên">Mật khẩu</option>
                        </select>
                        <input type="text" id="txtSearch" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearchNV" onclick="searchOfNV" src="./anh/ionCV.png" alt="icon">
                    </div>
                    <table class="table" id="tableNV">
                        <thead>
                            <tr>
                                <th>Mã nhân viên</th>
                                <th>Họ</th>
                                <th>Tên</th>
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
                <div class="divTT" id="divtableKhach">
                    <img src="./anh/ionCV.png"  onclick="dongTBTT()"  style="cursor: pointer;width: 15px;height:auto;margin-left: 98%;" />
                    <h3 style="text-align: center;"> Ban chọn mã độc giả ở đây!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã khách">Mã khách</option>
                            <option value="Tên">Tên </option>
                        </select>
                        <input type="text" id="txtSearch" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearchKhach" onclick="searchOfNV" src="./anh/ionCV.png" alt="icon">
                    </div>
                    <table class="table" id="tableKhach">
                        <thead>
                            <tr>
                                <th>Mã độc giả</th>
                                <th>Họ</th>
                                <th>Tên</th>
                                <th>Địa chỉ</th>
                                <th>Số điện thoại</th>
                                <th>Ngày sinh</th>
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
                <div class="divTT" id="divtableSach">
                    <img src="./anh/ionCV.png"  onclick="dongTBTT()"  style="cursor: pointer;width: 15px;height:auto;margin-left: 98%;" />
                    <h3 style="text-align: center;"> Ban chọn mã Sách ở đây!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã Sách">Mã Sách</option>
                            <option value="Tên sách">Tên sách </option>
                        </select>
                        <input type="text" id="txtSearch" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearchKhach" onclick="searchOfNV" src="./anh/ionCV.png" alt="icon">
                    </div>
                    <table class="table" id="tableSach">
                        <thead>
                            <tr>
                                <th>Mã sách</th>
                                <th>Tên sách</th>
                                <th>tác giả</th>
                                <th>Số lượng</th>
                                <th>Mô tả</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>1234</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
        <script>
            /*click table*/
            function clickPM(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieu').value = cells[0].innerText;
                document.getElementById('txtMaKhach').value = cells[1].innerText;
                document.getElementById('txtMaNV').value = cells[2].innerText;

                const dateValueNN = cells[3].innerText;
                document.getElementById('txtNgayLap').value = dateValueNN;

                // Lấy giá trị ngày từ cells[4] và định dạng lại
                const dateValueHC = cells[4].innerText;
                document.getElementById('txtHanChot').value = dateValueHC;

                document.getElementById('txtTongSL').value = cells[5].innerText;
            }
            function clickCTPM(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieuCTPM').value = cells[0].innerText;
                document.getElementById('txtSachCTPM').value = cells[1].innerText;
                document.getElementById('txtSLCTPM').value = cells[2].innerText;
                document.getElementById('txtTrangThaiCTPM').value = cells[3].innerText;
            }
            const rowsPM = document.querySelectorAll('#tablePM tbody tr');
            rowsPM.forEach(row => {
                row.addEventListener('click', () => clickPM(row));
            });
            const rowsCTPM = document.querySelectorAll('#tableCTPM tbody tr');
            rowsCTPM.forEach(row => {
                row.addEventListener('click', () => clickCTPM(row));
            });
            //set up ngày
            function formatDate(date) {
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
                const day = String(date.getDate()).padStart(2, '0');
                return `${year}-${month}-${day}`;
                    }

                    // Lấy ngày hiện tại và gán vào input ngày lập
                    document.getElementById('txtNgayLap').value = formatDate(new Date());
                    // Tính toán ngày trả (7 ngày sau)
                    const currentDate = new Date();
                    currentDate.setDate(currentDate.getDate() + 7); // Thêm 7 ngày vào ngày hiện tại

                    // Gán giá trị cho input "Ngày trả"
                    document.getElementById('txtHanChot').value = formatDate(currentDate);

                    /*clear dữ liệu*/
                    function clearInputPM() {
                        document.getElementById('txtMaPhieu').value = "";
                        document.getElementById('txtMaKhach').value = "";
                        document.getElementById('txtMaNV').value = "";
                        document.getElementById('txtNgayLap').value = formatDate(new Date());
                        document.getElementById('txtHanChot').value = formatDate(currentDate);
                        document.getElementById('txtTongSL').value = "0";
                    }
                    function clearInputCTPM() {
                        document.getElementById('txtMaPhieuCTPM').value = "";
                        document.getElementById('txtSachCTPM').value = "";
                        document.getElementById('txtSLCTPM').value = "";
                        document.getElementById('txtTrangThaiCTPM').value = "Đang mượn";
                    }
                    function sendData(action) {
                        alert("Đã click"); // Kiểm tra xem hàm có được gọi
                        const formData = new URLSearchParams({
                            action: action,
                            maPhieu: document.getElementById('txtMaPhieu').value,
                            maKhach: document.getElementById('txtMaKhach').value,
                            maNV: document.getElementById('txtMaNV').value,
                            ngayLap: document.getElementById('txtNgayLap').value,
                            hanChot: document.getElementById('txtHanChot').value,
                            tongSL: document.getElementById('txtTongSL').value,
                            optionSearch: document.getElementById('comBoBoxSearch').value,
                            valueSearch: document.getElementById('txtSearchPM').value,
                        });

                        fetch('http://localhost:9999/cnpm/phieumuon', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            body: formData.toString()
                        })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('Đã xảy ra lỗi trong quá trình gửi dữ liệu');
                            }
                            return response.text(); // Chuyển đổi phản hồi thành văn bản
                        })
                        .then(content => {
                            try {
                                const data = JSON.parse(content); // Cố gắng phân tích cú pháp JSON
                                if (data.thongbao) {
                                    alert(data.thongbao); // Hiển thị thông báo từ server
                                }
                                if (data.results && data.results.length > 0) {
                                    kQTimKiemPM(data.results); // Xử lý kết quả tìm kiếm
                                }
                                if (data.hopLe) {
                                    window.location.reload(); // Tải lại trang nếu hợp lệ
                                }
                            } catch (e) {
                                if (content.startsWith('<')) {
                                    if (action === 'print') {
                                        // Hiển thị nội dung HTML và in ra
                                        const printWindow = window.open('', '_blank');
                                        printWindow.document.write('<html><head><title>In Phiếu Mượn</title></head><body>');
                                        printWindow.document.write(content); // Thêm nội dung HTML
                                        printWindow.document.write('</body></html>');
                                        printWindow.document.close();
                                        printWindow.print(); // In nội dung
                                    }
                                } else {
                                    alert('Đã xảy ra lỗi trong quá trình xử lý dữ liệu.');
                                }
                            }
                        })
                        .catch(error => alert('Đã xảy ra lỗi: ' + error));
                    }

                    function  kQTimKiemPM(results)
                    {
                        const tableBody = document.getElementById('tbodyPM');
                        tableBody.innerHTML = '';
                        if (!Array.isArray(results)) {
                            results = Object.values(results); // Chuyển đổi đối tượng thành mảng các giá trị
                        }
                        results.forEach(item => {
                            const row = document.createElement('tr');
                            const cell1 = document.createElement('td');
                            cell1.textContent = item.maPhieu || '';
                            row.appendChild(cell1);
                            const cell2 = document.createElement('td');
                            cell2.textContent = item.maKhach || '';
                            row.appendChild(cell2);
                            const cell3 = document.createElement('td');
                            cell3.textContent = item.maNV || '';
                            row.appendChild(cell3);
                            const cell4 = document.createElement('td');
                            cell4.textContent = item.ngayLap || '';
                            row.appendChild(cell4);
                            const cell5 = document.createElement('td');
                            cell5.textContent = item.hanChot || '';
                            row.appendChild(cell5);
                            const cell6 = document.createElement('td');
                            cell6.textContent = item.tongSL || '';
                            row.appendChild(cell6);
                            tableBody.appendChild(row);
                        });
                    }
                    //Đóng các table
                    function dongTBTT() {
                        const divs = document.querySelectorAll('.divTT'); // Chọn tất cả div có class 'popupDiv'
                        divs.forEach(div => div.style.display = 'none'); // Ẩn từng div
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
                    //Hiện thị table sách
                    function hienThiSach() {
                        const tableSach = document.getElementById('divtableSach');
                        if (tableSach.style.display === 'none') {
                            tableSach.style.display = 'block';
                        } else {
                            tableSach.style.display = 'none';
                        }
                    }
                    function clickSach(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtSachCTPM').value = cells[0].innerText;
                        //document.getElementById('txtMaNV').disabled=true;
                        const tableSach = document.getElementById('divtableSach');
                        tableSach.style.display = "none";
                    }

                    const rowsSach = document.querySelectorAll('#tableSach tbody tr');
                    rowsSach.forEach(row => {
                        row.addEventListener('click', () => clickSach(row));
                    });
                    //Hiện thị table khách
                    function hienThiKhach() {
                        const tableNV = document.getElementById('divtableKhach');
                        if (tableNV.style.display === 'none') {
                            tableNV.style.display = 'block';
                        } else {
                            tableNV.style.display = 'none';
                        }
                    }
                    function clickKhach(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtMaKhach').value = cells[0].innerText;
                        //document.getElementById('txtMaNV').disabled=true;
                        const tableKhach = document.getElementById('divtableKhach');
                        tableKhach.style.display = "none";
                    }

                    const rowsKhach = document.querySelectorAll('#tableKhach tbody tr');
                    rowsKhach.forEach(row => {
                        row.addEventListener('click', () => clickKhach(row));
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
                    }, {passive: false}
                    );
        </script>
    </body>

</html>