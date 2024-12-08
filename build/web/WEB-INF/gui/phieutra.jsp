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
        <title>Quản lí phiếu trả</title>
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
            background-color:lightblue;
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

        #btnPhieuTra {
            background-color: #5A4E9B;
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
            margin-left: 5%;
        }

        .nameFeature {
            font-weight: bold;
            margin-right: 7px;
        }

        .input-group input,
        .input-groupCTPT input {
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
            width: 100%;
            table-layout: fixed;
            position: sticky;
            top: 0;
            background-color: #D9D9D9;
            z-index: 1;
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
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .table td:hover {
            white-space: normal;
            z-index: 1;
            background-color: #f1f1f1;
        }

        #detailCTPT {
            margin-top: 1%;
        }

        #sectionImportImformationCTPT {
            margin-left: 3%;
            width: 18%;

        }

        #sectionTableCTPT {
            width: 76%;
        }

        .input-groupCTPT {
            display: flex;
            margin-top: 10px;
            align-items: center;
        }
        .nameFeatureCTPT {
            font-weight: bold;
            margin-right: 10px;
        }

        #titleCTPT {
            width: 100%;
            text-align: center;
            font-size: 20px;
            color: #FB0606;
            font-weight: bolder;
            text-shadow: 2px 2px 5px rgba(9, 9, 9, 0.5);
        }

        .iconChucNangCTPT {
            width: 20px;
            height: auto;
            margin-right: 10px;
            margin-top: 10px;
            cursor: pointer;
        }

        #tableCTPT {
            width: 90%;
            height: 260px;
            width: 90%;
            border-collapse: collapse;
            table-layout: fixed;
            border: 1px solid black;
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }
        input:focus{
            outline: none;
            border-color: darkturquoise;
        }
        .divTT {
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

        .divTT table {
            border: 2px solid darkgray;
            border-collapse: collapse;
            width: 95%;
            height: 65%;
            margin-left: 20px;
        }
    </style>

    <body>
        <form id="form">
            <div id="title">
                Hệ thống quản lí thư viện của trường Đại học ABC
            </div>
            <div id="menu">
                <div class="conponentMenu" id="iconAndName" >
                    <img src="img/account.svg" alt="icon">
                    ${nv.getHo()}  ${nv.getTen()}<br>
                    ${nv.getChucVu()} 
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
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="img/phieutra.jpg" alt="icon"> Quản lí phiếu trả
                </p>
                <section id="detailPT">
                    <div class="input-group">
                        <label class="nameFeature">Mã phiếu </label>
                        <input type="text" id="txtMaPhieu" placeholder="Nhập mã phiếu">
                    </div>
                    <div class="input-group">
                        <label class="nameFeature">Mã NV</label>
                        <input type="text" id="txtMaNV" value="${nv.getMaNV()}" readonly>
                    </div>
                    <div class="input-group">
                        <label class="nameFeature" ">Mã PM </label>
                        <input type="text" id="txtMaPM"  placeholder="Chọn mã PM" readonly>
                        <img src="img/add.svg"  onclick="hienThiPM()" style="cursor: pointer;width: 15px;height:auto;" />
                    </div>

                    <div class="input-group">
                        <label class="nameFeature" >Tổng SL </label>
                        <input type="text" id="txtTongSL" value="0"  readonly>
                        <img class="iconChucNang" id="iconThem" src="img/add.svg" title="Thêm PT" onclick="sendData('add')">
                        <img class="iconChucNang" id="iconXoa" src="img/delete.svg" title="Xóa PT">
                        <img class="iconChucNang" id="iconSua" src="img/edit.svg" title="Sửa PT" onclick="sendData('edit')">
                        <img class="iconChucNang" id="iconClear" title="Clear input"  onclick="clearInputPT()" src="img/clear.png" alt="icon">
                    </div><br>
                    <div class="input-group" style="margin-top: 10px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="Mã phiếu">Mã phiếu</option>
                            <option value="Mã NV">Mã NV</option>
                            <option value="Mã PM">Mã PM</option>
                        </select>
                        <input type="text" id="txtSearchPT" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearch" title="Tìm kiếm PT" style="margin-left: 0px;" src="img/search1.png" alt="icon"
                             onclick="sendData('search')">
                        <img class="iconChucNang" id="iconImportExcel" style="margin-left: 56%;" src="img/import_excel.svg"
                             alt="icon" onclick="selectFile('import')"title="importExcel">
                        <img class="iconChucNang" id="iconExportExcel" src="img/export_excel.svg" alt="icon"
                             onclick="selectFile('export')" title="exportESxcel">
                        <input  type="file" id="fileExcel" style="display:none;">
                        <img class="iconChucNang" id="iconPrint" title="PrintPT"  src="img/print.jpg" alt="icon"
                             onclick="sendData('print')">
                        <img class="iconChucNang" id="iconFinish" title="Load lại table" src="img/refresh.svg" alt="icon"
                             onclick="sendData('finish')">
                    </div>
                    <table class="table" id="tablePT">
                        <thead>
                            <tr>
                                <th>Mã phiếu trả</th>
                                <th>Mã PM</th>
                                <th>Mã NV</th>
                                <th>Tổng số lượng</th>
                            </tr>
                        </thead>
                        <tbody id="tbodyPT">
                            <c:forEach var="pm" items="${requestScope.listPT}">
                                <tr>
                                    <td>${pm.maPT}</td>
                                    <td>${pm.maPM}</td>
                                    <td>${pm.maNV}</td>
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
                            </tr>
                            <%
                                }
                            %>

                        </tbody>
                    </table>
                </section>

                <section id="detailCTPT">
                    <p id="titleCTPT">Chi tiết phiếu trả</p>
                    <div style="display: flex; justify-content: space-between; ">
                        <div id="sectionImportImformationCTPT">
                            <div class="input-groupCTPT" style="margin-top: 40px;">
                                <label class="nameFeatureCTPT" style="margin-right: 18px;">Mã phiếu</label>
                                <input type="text" id="txtMaPhieuCTPT" placeholder="Nhập mã phiếu">
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 27px;">Mã sách</label>
                                <input type="text" id="txtSachCTPT" placeholder="Chọn mã sách" style="margin-right: 2px" readonly>
                                <img src="img/add.svg" onclick="hienThiSach()"
                                     style="cursor: pointer;width: 15px;height:auto;" />
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 3px;">Mã vạch lỗi</label>
                                <input type="text" id="txtmaVachLoiCTPT" placeholder="Chọn mã vạch lỗi" style="margin-right: 2px">
                                <img src="img/add.svg" title="mở table CT Sách" onclick="hienThiCTSach()"
                                     style="cursor: pointer;width: 15px;height:auto;" readonly/>
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 23px;">Ngày trả</label>
                                <input type="date" id="txtNgayTraCTPT"  style="margin-right: 2px">
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 21px;">Số lượng</label>
                                <input type="text" id="txtSLCTPT" placeholder="Nhập số lượng">
                            </div>
                            <div>
                                <img class="iconChucNangCTPT" id="iconThemCTPT" style="margin-left: 24%;"
                                     src="img/add.svg" title="Thêm CTPT" onclick="sendDataCTPT('addCTPT')">
                                <img class="iconChucNangCTPT" id="iconXoaCTPT" src="img/delete.svg" title="Xóa CTPT" >
                                <img class="iconChucNangCTPT" id="iconSuaCTPT" src="img/edit.svg" title="Sửa CTPT" onclick="sendDataCTPT('updateCTPT')">
                                <img class="iconChucNangCTPT" id="iconClearCTPT" onclick="clearInputCTPT()"
                                     src="img/clear.png" title="clear input CTPT">
                            </div>
                        </div>
                        <div id="sectionTableCTPT">
                            <div class="input-groupCTPT" style="margin-left:2%;">
                                <select id="comBoBoxSearchCTPT"  name="options">
                                    <option value="Mã phiếu">Mã phiếu trả</option>
                                    <option value="Mã sách">Mã sách</option>
                                    <option value="Mã vạch lỗi">Mã vạch lỗi</option>
                                    <option value="Ngày trả">Ngày trả</option>
                                </select>
                                <input type="text" id="txtSearchCTPT" style="margin-left: 5px;margin-right: 5px;"
                                       placeholder="Nhập thông tin">
                                <img class="iconChucNang" id="iconSearchCTPT" style="margin-right: 58%;margin-left: 0px;"
                                     src="img/search1.png" title="Tìm kiếm CTPT" onclick="sendDataCTPT('searchCTPT')">
                                <img class="iconChucNang" id="iconFinishCTPT" src="img/refresh.svg" title="Tải lại table" onclick="sendDataCTPT('finishCTPT')">
                            </div>
                            <table class="table" id="tableCTPT">
                                <thead>
                                    <tr>
                                        <th>Mã phiếu trả</th>
                                        <th>Mã sách</th>
                                        <th>Mã vạch lỗi</th>
                                        <th>Ngày trả</th>
                                        <th>Số lượng</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyCT">
                                    <c:forEach var="ctpm" items="${requestScope.listCTPT}">
                                        <tr>
                                            <td>${ctpm.maPT}</td>
                                            <td>${ctpm.maSach}</td>
                                            <td>${ctpm.maVachLoi}</td>
                                            <td>${ctpm.ngayTra}</td>
                                            <td>${ctpm.soLuong}</td>
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
            <div class="divTT" id="divtableSach" onclick="showListAllSach()">
                <img src="img/cancel.svg" alt="Đóng table" onclick="dongTBTT()"

                     style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                <h3 style="text-align: center;"> Ban chọn mã Sách ở đây!</h3>
                <div style="margin-left: 20px;">
                    <select id="comBoBoxSearchSach" name="options">
                        <option value="Mã Sách">Mã Sách</option>
                        <option value="Tên sách">Tên sách </option>
                    </select>
                    <input type="text" id="txtSearchSach" placeholder="Nhập thông tin">
                    <img class="iconChucNang" id="iconSearchSach" src="img/search1.png" alt="icon">
                </div>
                <table class="table" id="tableSach">
                    <thead>
                        <tr>
                            <th>Mã sách</th>
                            <th>Tên sách</th>
                            <th>Tác giả</th>
                            <th>Số lượng</th>
                            <th>Mô tả</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pm" items="${requestScope.listSach}">
                            <tr>
                                <td>${pm.maSach}</td>
                                <td>${pm.tenSach}</td>
                                <td>${pm.tacGia}</td>
                                <td>${pm.soLuong}</td>
                                <td>${pm.moTa}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="divTT" id="divtableCTSach" onclick="showListAllCTSach()">
                <img src="img/cancel.svg" alt="Đóng table" onclick="dongTBTT()"
                     style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                <h3 style="text-align: center;"> Ban chọn mã vạch ở đây!</h3>
                <div style="margin-left: 20px;">
                    <select id="comBoBoxSearchCTSach" name="options">
                        <option value="Mã sách">Mã sách</option>
                        <option value="Mã vạch">Mã vạch</option>
                    </select>
                    <input type="text" id="txtSearchCTSach" placeholder="Nhập thông tin">
                    <img class="iconChucNang" id="iconSearchCTSach"src="img/search1.png"
                         alt="icon">
                </div>
                <table class="table" id="tableCTSach">
                    <thead>
                        <tr>
                            <th>Mã vạch</th>
                            <th>Mã sách</th>
                            <th>Tình trạng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pm" items="${requestScope.listCTS}">
                            <tr>
                                <td>${pm.maVach}</td>
                                <td>${pm.maSach}</td>
                                <td>${pm.tinhTrangSach}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="divTT" id="divtablePM" onclick="showListAllPM()">
                <img src="img/cancel.svg" alt="Đóng table" onclick="dongTBTT()"
                     style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                <h3 style="text-align: center;">Mời bạn chọn mã phiếu mượn ở đây!</h3>
                <div style="margin-left: 20px;">
                    <select id="comBoBoxSearchPM" name="options">
                        <option value="Mã phiếu mượn">Mã phiếu mượn</option>
                        <option value="Mã độc giả">Mã độc giả</option>
                    </select>
                    <input type="text" id="txtSearchPM" placeholder="Nhập thông tin">
                    <img class="iconChucNang" id="iconSearchPM"src="img/search1.png"
                         alt="icon">
                </div>
                <table class="table" id="tablePM">
                    <thead>
                        <tr>
                            <th>Mã phiếu mượn</th>
                            <th>Mã độc giả</th>
                            <th>Mã Nhân viên</th>
                            <th>Ngày mượn</th>
                            <th>Hạn chả</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pm" items="${requestScope.listPM}">
                            <tr>
                                <td>${pm.maPM}</td>
                                <td>${pm.maKhach}</td>
                                <td>${pm.maNV}</td>
                                <td>${pm.ngayLap}</td>
                                <td>${pm.hanChot}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </form>


        <script>
            
            
            let namePath = "";
            /*click table*/
            function clickPT(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieu').value = cells[0].innerText;
                document.getElementById('txtMaPhieuCTPT').value = cells[0].innerText;
                document.getElementById('txtMaPhieu').readOnly = true;
                document.getElementById('txtMaPM').value = cells[1].innerText;
                document.getElementById('txtTongSL').value = cells[3].innerText;
            }
            function clickCTPT(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieuCTPT').value = cells[0].innerText;
                document.getElementById('txtMaPhieuCTPT').readOnly = true;
                document.getElementById('txtSachCTPT').value = cells[1].innerText;
                document.getElementById('txtSachCTPT').readOnly = true;
                document.getElementById('txtmaVachLoiCTPT').value = cells[2].innerText;
                document.getElementById('txtNgayTraCTPT').value = cells[3].innerText;
                document.getElementById('txtSLCTPT').value = cells[4].innerText;
            }
            const rowsPT = document.querySelectorAll('#tablePT tbody tr');
            rowsPT.forEach(row => {
                row.addEventListener('click', () => clickPT(row));
            });
            const rowsCTPT = document.querySelectorAll('#tableCTPT tbody tr');
            rowsCTPT.forEach(row => {
                row.addEventListener('click', () => clickCTPT(row));
            });
            ;

            /*clear d? li?u*/
            function clearInputPT() {
                document.getElementById('txtMaPhieu').value = "";
                document.getElementById('txtMaPhieu').readOnly = false;
                document.getElementById('txtMaPM').value = "";
                document.getElementById('txtTongSL').value = "0";
            }
            function clearInputCTPT() {
                document.getElementById('txtMaPhieuCTPT').value = "";
                document.getElementById('txtMaPhieuCTPT').readOnly = false;
                document.getElementById('txtSachCTPT').value = "";
                document.getElementById('txtSachCTPT').readOnly = false;
                document.getElementById('txtmaVachLoiCTPT').value = "";
                document.getElementById('txtNgayTraCTPT').value = "";
                document.getElementById('txtSLCTPT').value = 0;
            }
            function selectFile(action) {
                // L?ng nghe s? ki?n thay ??i c?a input file
                alert("Vui lòng ch?n ho?c t?o file excel ?? " + action + " ? trong th? m?c C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/");
                const fileInput = document.getElementById("fileExcel");
                fileInput.onchange = function () {
                    if (fileInput.files.length > 0) {
                        // Gán ???ng d?n file vào bi?n toàn c?c
                        namePath = fileInput.files[0].name;
                        sendData(action);
                    }
                };

                // Hi?n th? c?a s? ch?n file
                fileInput.click();
            }
            // Lắng nghe sự kiện khi bàn phím ?? tìm ki?m phi?u tr?
            document.getElementById('txtSearchPT').addEventListener('keyup', function (event) {
                sendData('search');
            });
            function sendData(action) {
                const formData = new URLSearchParams({
                    action: action,
                    maPhieu: document.getElementById('txtMaPhieu').value,
                    maNV: document.getElementById('txtMaNV').value,
                    maPM: document.getElementById('txtMaPM').value,
                    tongSL: document.getElementById('txtTongSL').value,
                    optionSearch: document.getElementById('comBoBoxSearch').value,
                    valueSearch: document.getElementById('txtSearchPT').value,
                    nameFileExcel: namePath,
                });

                fetch('http://localhost:9999/cnpm/phieutra', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: formData.toString()
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('đã xảy ra lỗii trong quá trình gửi dữ liệu');
                            }
                            return response.text();
                        })
                        .then(content => {
                            try {
                                const data = JSON.parse(content);
                                if (data.thongbao) {
                                    alert(data.thongbao);
                                }
                                if (data.resultsPT && data.resultsPT.length > 0) {
                                    kQTimKiemPT(data.resultsPT);
                                    kQTimKiemCTPT(data.resultsCTPT);
                                }
                                if (data.hopLe) {
                                    window.location.reload();
                                }
                            } catch (e) {
                                if (content.startsWith('<')) {
                                    if (action === 'print') {
                                        // Hi?n th? n?i dung HTML và in ra
                                        const printWindow = window.open('', '_blank');
                                        printWindow.document.write('<html><head><title>In Phiếu trả</title></head><body>');
                                        printWindow.document.write(content); // Thêm n?i dung HTML
                                        printWindow.document.write('</body></html>');
                                        printWindow.document.close();
                                        printWindow.print(); // In n?i dung
                                    }
                                } else {
                                    alert('Đã xảy ra lỗi trong quá trình xử lý dữ liệu.' + e.message);
                                }
                            }
                        })
                        .catch(error => alert('Đã xảy ra lỗi: ' + error));
            }

            function  kQTimKiemPT(results)
            {
                const tableBody = document.getElementById('tbodyPT');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results);
                }
                results.forEach(item => {
                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maPhieu || '';
                    row.appendChild(cell1);
                    const cell2 = document.createElement('td');
                    cell2.textContent = item.maPM || '';
                    row.appendChild(cell2);
                    const cell3 = document.createElement('td');
                    cell3.textContent = item.maNV || '';
                    row.appendChild(cell3);
                    const cell4 = document.createElement('td');
                    cell4.textContent = item.tongSL || '';
                    row.appendChild(cell4);
                    tableBody.appendChild(row);
                });
            }
            document.getElementById('txtSearchCTPT').addEventListener('keyup', function (event) {
                sendDataCTPT('searchCTPT');
            });
            function sendDataCTPT(action)
            {
                const formData = new URLSearchParams({
                    action: action,
                    maPT: document.getElementById('txtMaPhieuCTPT').value,
                    maSach: document.getElementById('txtSachCTPT').value,
                    maVachLoi: document.getElementById('txtmaVachLoiCTPT').value,
                    ngayTra: document.getElementById('txtNgayTraCTPT').value,
                    soLuong: document.getElementById('txtSLCTPT').value,
                    optionSearch: document.getElementById('comBoBoxSearchCTPT').value,
                    valueSearch: document.getElementById('txtSearchCTPT').value,
                });

                fetch('http://localhost:9999/cnpm/ctpt', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: formData.toString()
                })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('đã xảy ra lỗii trong quá trình gửi dữ liệu');
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.thongbao)
                                alert(data.thongbao);
                            if (data.results && data.results.length > 0) {
                                kQTimKiemCTPT(data.results);
                            }
                            if (data.hopLe) {
                                window.location.reload();
                            }
                        })
                        .catch(error => alert('Đã xảy ra lỗi trong quá trình xử lý dữ liệu: ' + error));
            }
            function  kQTimKiemCTPT(results)
            {
                const tableBody = document.getElementById('tbodyCT');
                tableBody.innerHTML = '';
                if (!Array.isArray(results)) {
                    results = Object.values(results);
                }
                results.forEach(item => {
                    const row = document.createElement('tr');
                    const cell1 = document.createElement('td');
                    cell1.textContent = item.maPT || '';
                    row.appendChild(cell1);
                    const cell2 = document.createElement('td');
                    cell2.textContent = item.maSach || '';
                    row.appendChild(cell2);
                    const cell3 = document.createElement('td');
                    cell3.textContent = item.maVachLoi || '';
                    row.appendChild(cell3);
                    const cell4 = document.createElement('td');
                    cell4.textContent = item.ngayTra || '';
                    row.appendChild(cell4);
                    const cell5 = document.createElement('td');
                    cell5.textContent = item.soLuong || '';
                    row.appendChild(cell5);
                    tableBody.appendChild(row);
                });
            }
            //hi?n th? xác nh?n xóa phi?u tr?
            document.getElementById('iconXoa').addEventListener('click', function (event) {
                // Hi?n th? h?p tho?i xác nh?n
                const confirmDelete = confirm("Bạn có chắc muốn xóa không phiếu trả này không?");
                if (!confirmDelete) {
                    event.preventDefault();
                } else {
                    sendData('delete');
                }
            });
            //hi?n th? xác nh?n xóa chi ti?t phi?u tr?
            document.getElementById('iconXoaCTPT').addEventListener('click', function (event) {
                // Hi?n th? h?p tho?i xác nh?n
                const confirmDelete = confirm("Bạn có chắc muốn xóa không chi tiết phiếu trả này không?");
                if (!confirmDelete) {
                    event.preventDefault();
                } else {
                    sendDataCTPT('deleteCTPT');
                }
            });
            //Đóng các table
            function dongTBTT() {
                const divs = document.querySelectorAll('.divTT'); // Chọn tất cả div có class 'popupDiv'
                divs.forEach(div => div.style.display = 'none'); // Ẩn từng div
            }
            //Hiện thị table Sách
            function hienThiSach() {
                const table = document.getElementById('divtableSach');
                if (table.style.display === 'none') {
                    table.style.display = 'block';
                } else {
                    table.style.display = 'none';
                }
            }
            function clickSach(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtSachCTPT').value = cells[0].innerText;
                //document.getElementById('txtMaNV').disabled=true;
                const tableNV = document.getElementById('divtableSach');
                tableNV.style.display = "none";

            }
            const rows = document.querySelectorAll('#tableSach tbody tr');
            rows.forEach(row => {
                row.addEventListener('click', () => clickSach(row));
            });
            //hiện thị chi tiết sách
            function hienThiCTSach() {
                const table = document.getElementById('divtableCTSach');
                if (table.style.display === 'none') {
                    table.style.display = 'block';
                } else {
                    table.style.display = 'none';
                }
            }
            function clickCTSach(row) {
                const cells = row.getElementsByTagName('td');
                let currentValue = document.getElementById('txtmaVachLoiCTPT').value;
                if (currentValue.includes(cells[0].innerText)) {
                            alert("Mã vạch lỗi này bạn đã có!");
                } else
                    document.getElementById('txtmaVachLoiCTPT').value = currentValue ? currentValue + "," + cells[0].innerText : cells[0].innerText;
                const tableNV = document.getElementById('divtableCTSach');
                tableNV.style.display = "none";

            }
            const rowsCTSah = document.querySelectorAll('#tableCTSach tbody tr');
            rowsCTSah.forEach(row => {
                row.addEventListener('click', () => clickCTSach(row));
            });
            //hiện thị Phiếu trả
            function hienThiPM() {
                const table = document.getElementById('divtablePM');
                if (table.style.display === 'none') {
                    table.style.display = 'block';
                } else {
                    table.style.display = 'none';
                }
            }
            function clickPM(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPM').value = cells[0].innerText;
                const tableNV = document.getElementById('divtablePM');
                tableNV.style.display = "none";
            }
            const rowsPM = document.querySelectorAll('#tablePM tbody tr');
            rowsPM.forEach(row => {
                row.addEventListener('click', () => clickPM(row));
            });

            //Tìm kiếm với table sách
            function searchSach()
            {
                var value = document.getElementById('txtSearchSach').value.toLowerCase();
                var option = document.getElementById('comBoBoxSearchSach').value;
                var rows = document.querySelectorAll('#tableSach tbody tr');
                if (!value) {
                    alert("Vui lòng nhập thông tin để tìm kiếm Sách!");
                    return;
                }
                var columnIndex = 0;
                if (option === "Tên sách")
                    columnIndex = 1;
                rows.forEach(row => {
                    var cell = row.getElementsByTagName('td')[columnIndex];
                    if (cell && cell.textContent.toLowerCase().includes(value)) {
                        row.style.display = "";
                    } else
                        row.style.display = "none";
                });
            }
            document.getElementById('iconSearchSach').addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn chặn sự kiện click lan ra divTableNV
                searchSach();
            });
            //hiện thị hết dữ liệu của table sách
            function showListAllSach() {
                var rows = document.querySelectorAll('#tableSach tbody tr');
                rows.forEach(row => {
                    row.style.display = "";
                });
                document.getElementById('txtSearchSach').value = "";
            }

            //tìm kiếm với table ct sách
            function searchCTSach()
            {
                var value = document.getElementById('txtSearchCTSach').value.toLowerCase();
                var option = document.getElementById('comBoBoxSearchCTSach').value;
                var rows = document.querySelectorAll('#tableCTSach tbody tr');
                if (!value) {
                    alert("Vui lòng nhập thông tin để tìm kiếm mã vạch!");
                    return;
                }
                var columnIndex = 0;
                if (option === "Mã sách")
                    columnIndex = 1;
                rows.forEach(row => {
                    var cell = row.getElementsByTagName('td')[columnIndex];
                    if (cell && cell.textContent.toLowerCase().includes(value)) {
                        row.style.display = "";
                    } else
                        row.style.display = "none";
                });
            }
            document.getElementById('iconSearchCTSach').addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn chặn sự kiện click lan ra divTableNV
                searchCTSach();
            });
            //hiện thị hết dữ liệu của table sách
            function showListAllCTSach() {
                var rows = document.querySelectorAll('#tableCTSach tbody tr');
                rows.forEach(row => {
                    row.style.display = "";
                });
                document.getElementById('txtSearchCTSach').value = "";
            }

            //tìm kiếm với table phiếu trả
            function searchPM()
            {
                var value = document.getElementById('txtSearchPM').value.toLowerCase();
                var option = document.getElementById('comBoBoxSearchPM').value;
                var rows = document.querySelectorAll('#tablePM tbody tr');
                if (!value) {
                    alert("Vui lòng nhập thông tin để tìm kiếm phiếu trả!");
                    return;
                }
                var columnIndex = 0;
                if (option === "Mã độc giả")
                    columnIndex = 1;
                rows.forEach(row => {
                    var cell = row.getElementsByTagName('td')[columnIndex];
                    if (cell && cell.textContent.toLowerCase().includes(value)) {
                        row.style.display = "";
                    } else
                        row.style.display = "none";
                });
            }
            document.getElementById('iconSearchPM').addEventListener('click', function (event) {
                event.stopPropagation(); // Ngăn chặn sự kiện click lan ra divTableNV
                searchPM();
            });
            //hiện thị hết dữ liệu của table PM
            function showListAllPM() {
                var rows = document.querySelectorAll('#tablePM tbody tr');
                rows.forEach(row => {
                    row.style.display = "";
                });
                document.getElementById('txtSearchPM').value = "";
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
            // Ch?n vi?c s? d?ng t? h?p phím t?t ?? phóng to/thu nh?
            document.addEventListener('keydown', function (event) {
                if (event.ctrlKey && (event.key === '+' || event.key === '-' || event.key === '=')) {
                    event.preventDefault();
                }
            });
            // Ch?n thao tác phóng to b?ng con l?n chu?t
            document.addEventListener('wheel', function (event) {
                if (event.ctrlKey) {
                    event.preventDefault();
                }
            }, {passive: false}
            );
        </script>
    </body>
</html>