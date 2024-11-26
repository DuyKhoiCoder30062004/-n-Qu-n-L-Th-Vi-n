
<%-- 
    Document   : phieuphat
    Created on : Oct 28, 2024, 1:14:00 PM
    Author     : ADMIN
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
        <meta rel="icon" type="image/x-icon" href="">
        <title>Quản lí phiếu phạt</title>
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
            background-color:beige;
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

        #btnPhieuPhat {
            background-color: #5A4E9B;
        }

        #titleDetail,
        #titleLoi,
        #titleCTPP {
            font-size: 20px;
            align-items: center;
            display: flex;
            color: #AF2298;
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

        #divLoi_CTPP {
            width: 100%;
            height: 35%;
            margin-top: 5px;
            margin-bottom: 4%;
            display: flex;
            justify-content: space-between;
        }

        .nameFeature {
            font-weight: bolder;
        }

        .iconChucNang {
            width: 22px;
            height: auto;
            margin-right: 10px;
            vertical-align: middle;
            cursor: pointer;
            margin-top: 0px;
        }

        input {
            width: 100px;
        }

        .input-group{
            margin-top: 2%;
        }


        .table{
            width: 100%;
            border-collapse: collapse;
            margin-top: 2px;
            table-layout: fixed;
            border: 2px solid #808080;
            height: 90%;
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

        .table tbody {
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }

        .table th,
        td {
            border: 1px solid #808080;
            text-align: center;
            word-wrap: break-word;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .table tbody tr {
            height: 25px;
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        .table td:hover {
            white-space: normal;
            z-index: 1;
            background-color: #f1f1f1;
        }

        .input-groupPP {
            margin-right:5%;
            display: inline;

        }

        .input-groupPP input {
            margin-bottom: 1%;
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

        input:focus {
            outline: none;
            border-color: darkturquoise;
        }
        img{
            cursor: pointer;
        }
        #txtLiDoCTPP:hover {
            width: auto; /* Tự động mở rộng theo nội dung */
            max-width: 1000px; /* Giới hạn chiều rộng tối đa khi mở rộng */
            position: absolute; /* Để thẻ input nổi lên trên các phần tử khác */
            z-index: 1000; /* Đưa input lên trên các thành phần khác */
            white-space: nowrap;
            background-color: #f1f1f1; /* Màu nền khi hover */
            padding: 5px; /* Thêm khoảng đệm cho thẻ input khi mở rộng */
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
                <!-- title -->
                <p id="titleDetail">
                    <img id="imgDetail" src="img/phieuphat.jpg" alt="icon"> Quản lí phiếu phạt
                </p>
                <p id="titleLoi" style="display: inline; margin-left: 12%;">Quản lí lỗi</p>
                <p id="titleCTPP" style="display: inline; margin-left: 40%;">Chi tiết phiếu phạt</p>

                <!-- section bao gồm lỗi và chi tiết phiếu phạt -->
                <div id="divLoi_CTPP">
                    <!--section Lỗi -->
                    <div style="width: 30%;display: flex; justify-content: space-between;">
                        <div id="inputLoi" style="margin-top: 7%; margin-left: 10px;margin-right: 15px;width: 30%; ">
                            <div class="input-group">
                                <label class="nameFeature">Tên lỗi</label>
                                <input type="text" id="txtLoi" placeholder="Nhập tên lỗi">
                            </div>
                            <div class="input-group">
                                <label class="nameFeature">% tiền</label>
                                <input type="text" id="txtPhanTramTien" placeholder="Nhập % tiền phạt">
                            </div>
                            <div class="input-group">
                                <img class="iconChucNang" id="iconThemLoi" style="margin-top:6px;"  src="img/add.svg"
                                     title="Thêm Lỗi" onclick="sendDataLoi('addLoi')">
                                <img class="iconChucNang" id="iconXoaLoi" style="margin-top:6px;" src="img/delete.svg" title="Xóa Lỗi" >
                                <img class="iconChucNang" id="iconSuaLoi" style="margin-top:6px;" src="img/edit.svg" title="Sửa lỗi" onclick="sendDataLoi('updateLoi')">
                                <img class="iconChucNang" id="iconClearLoi" style="margin-left: 33%;margin-top:6px;"
                                     onclick="clearInputLoi()" src="img/clear.png" title="Clear input Lỗi">
                                <img class="iconChucNang" id="iconFinishLoi" style="margin-top:6px;" src="img/refresh.svg" title="Tải lại table" onclick="sendDataLoi('finishLoi')">
                            </div>
                        </div>
                        <div id="divtableLoi">
                            <div>
                                <select id="comBoBoxSearchLoi" name="options">
                                    <option value="Tên lỗi">Tên lỗi</option>
                                    <option value="% tiền">% tiền</option>
                                </select>
                                <input type="text" id="txtSearchLoi" placeholder="Nhập thông tin">
                                <img class="iconChucNang" id="iconSearchLoi"  src="img/search1.png"
                                     title="Tìm kiếm Lỗi" onclick="sendDataLoi('searchLoi')">

                            </div>
                            <table class="table" id="tableLoi">
                                <thead>
                                    <tr>
                                        <th>Tên lỗi</th>
                                        <th>% tiền</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyLoi">
                                    <c:forEach var="loi" items="${requestScope.listLoi}">
                                        <tr>
                                            <td>${loi.tenLoi}</td>
                                            <td>${loi.phamTramTien}</td>
                                        </tr>
                                    </c:forEach>
                                    <%
                                    for (int i = 1; i <= 10; i++) {
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
                    <!-- section Chi tiết phiếu phạt -->
                    <div style="display: flex; justify-content: space-between;width: 65%;">
                        <div id="inputCTPP" style="margin-top: 4%;">
                            <div class="input-group">
                                <label class="nameFeature">Mã phiếu</label>
                                <input type="text" id="txtMaPhieuCTPP" placeholder="Nhập mã phiếu">
                            </div>
                            <div class="input-group">
                                <label class="nameFeature" style="margin-right: 8px;">Mã sách</label>
                                <input type="text" id="txtMaSachCTPP" placeholder="Nhập mã sách">
                                <img src="img/add.svg" title="mở table Sách" onclick="hienThiSach()"
                                     style="cursor: pointer;width: 15px;height:auto;" readonly />
                            </div>
                            <div class="input-group">
                                <label class="nameFeature" style="margin-right: 7px;">Mã vạch</label>
                                <input type="text" id="txtMaVachCTPP" placeholder="Nhập mã vạch">
                                <img src="img/add.svg" title="mở table CT Sách" onclick="hienThiCTSach()"
                                     style="cursor: pointer;width: 15px;height:auto;" readonly/>
                            </div>
                            <div class="input-group">
                                <label class="nameFeature" style="margin-right: 5px;">Ngày lập</label>
                                <input type="date" id="txtNgayLapCTPP"> 
                            </div>
                            <div class="input-group">
                                <label class="nameFeature" style="margin-right: 29px;">Lí do</label>
                                <input type="text" id="txtLiDoCTPP" placeholder="Chọn lí do">
                                <img style="width: 15px; height: auto; " id="iconThemLoi" onclick="thongBaoLayLoi()"
                                     src="img/add.svg" title="Lỗi" >
                            </div>
                            <div class="input-group">
                                <label class="nameFeature" style="margin-right: 34px;">Tiền</label>
                                <input type="text" id="txtTienCTPP" value="0" readonly style="margin-bottom:6px;" readonly>
                            </div>
                            <div class="input-group">
                                <img class="iconChucNang" style="margin-left: 25.2%;" id="iconThemCTPP"
                                     src="img/add.svg" title="Thêm CTPP" onclick="sendDataCTPP('addCTPP')">
                                <img class="iconChucNang" id="iconXoaCTPP" src="img/delete.svg" title="Xóa CTPP" >
                                <img class="iconChucNang" id="iconSuaCTPP" src="img/edit.svg" title="Sửa CTPP" onclick="sendDataCTPP('updateCTPP')">
                                <img class="iconChucNang" id="iconClearCTPP" onclick="clearInputCTPP()"
                                     src="img/clear.png" title="Clear input CTPP">
                            </div>
                        </div>
                        <div id="divtableCTPP" style="width: 75%; margin-right: 2%;">
                            <div style="display: flex; align-items: center;justify-content: flex-start;">
                                <select id="comBoBoxSearchCTPP" name="options" style="margin-right: 5px">
                                    <option value="Mã phiếu">Mã phiếu</option>
                                    <option value="Mã sách">Mã sách</option>
                                    <option value="Mã vạch">Mã Vạch</option>
                                    <option value="Ngày lập">Ngày lập</option>
                                    <option value="Lí do">Lí do</option>
                                    <option value="Tiền">Tiền</option>
                                </select>
                                <input type="text" id="txtSearchCTPP" placeholder="Nhập thông tin">
                                <img class="iconChucNang" id="iconSearchCTPP"  src="img/search1.png"
                                title="Tìm kiếm CTPP" onclick="sendDataCTPP('searchCTPP')">
                                <img class="iconChucNang" id="iconFinishCTPP" style=" margin-left: 60%;"
                                     src="img/refresh.svg" onclick="sendDataCTPP('finishCTPP')" title="Tại lại table">
                            </div>
                            <table class="table" id="tableCTPP">
                                <thead>
                                    <tr>
                                        <th>Mã phiếu</th>
                                        <th>Mã sách</th>
                                        <th>Mã vạch</th>
                                        <th>Ngày lập</th>
                                        <th>Lí do</th>
                                        <th>Tiền</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyCTPP">
                                    <c:forEach var="pm" items="${requestScope.listCTPP}">
                                        <tr>
                                            <td>${pm.maPP}</td>
                                            <td>${pm.maSach}</td>
                                            <td>${pm.maVach}</td>
                                            <td>${pm.ngayLap}</td>
                                            <td>${pm.liDo}</td>
                                            <td>${pm.tien}</td>
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
                        </div>
                    </div>
                </div>

                <!-- section phiếu phạt-->
                <div style="width: 100%; height: 60%; ">
                    <div id="inputPP" style="margin-left: 13%;">
                        <div class="input-groupPP">
                            <label class="nameFeature">Mã Phiếu</label>
                            <input type="text" id="txtMaPhieuPP" placeholder="Nhập mã phiếu">
                        </div>
                        <div class="input-groupPP">
                            <label class="nameFeature">Mã Phiếu Trả</label>
                            <input type="text" id="txtMaPTPP" placeholder="Nhập mã ptra">
                            <img src="img/add.svg" title="mở table phiếu trả" onclick="hienThiPT()"
                                 style="cursor: pointer;width: 15px;height:auto;" />
                        </div>
                        <div class="input-groupPP">
                            <label class="nameFeature">Mã NV</label>
                            <input type="text" id="txtMaNVPP" value="${nv.getMaNV()}" readonly >
                        </div>
                        <div class="input-groupPP" style="margin-right: 1%;">
                            <label class="nameFeature">Tổng tiền</label>
                            <input type="text" id="txtTongTienPP" value="0" readonly>
                        </div>
                        <div class="input-groupPP">
                            <img class="iconChucNang" id="iconThemPP"  src="img/add.svg"
                                 title="Thêm PP" onclick="sendDataPP('addPP')">
                            <img class="iconChucNang" id="iconXoaPP"  src="img/delete.svg"
                                 title="Xóa PP" >
                            <img class="iconChucNang" id="iconSuaPP" src="img/edit.svg"
                                 title="Sửa PP" onclick="sendDataPP('updatePP')">
                            <img class="iconChucNang" id="iconClearPP" onclick="clearInputPP()"
                                 src="img/clear.png" title="Clear input PP">
                        </div>
                    </div>
                    <div id="divtablePP" style="margin-left: 5%; margin-right: 1%; height: 58%;">
                        <div>
                            <select id="comBoBoxSearchPP" name="options">
                                <option value="Mã phiếu">Mã phiếu</option>
                                <option value="Mã phiếu trả">Mã phiếu trả</option>
                                <option value="Mã NV">Mã NV</option>
                                <option value="Tổng tiền">Tổng tiền</option>
                            </select>
                            <input type="text" id="txtSearchPP" placeholder="Nhập thông tin">
                            <img class="iconChucNang" id="iconSearchPP"  src="img/search1.png"
                                 title="Tìm kiếm PP" onclick="sendDataPP('searchPP')">
                            <img class="iconChucNang" id="iconImportExcelPP"
                                 style="margin-left:55%; margin-right: 3%;" src="img/import_excel.svg" title="Import Excel PP" 
                                 onclick="selectFile('import')">
                            <img class="iconChucNang" id="iconExportExcelPP" style="margin-right: 3%;"
                                 src="img/export_excel.svg" title="Export Excel PP" onclick="selectFile('export')">
                            <input  type="file" id="fileExcel" style="display:none;">
                            <img class="iconChucNang" id="iconPrintPP" style="margin-right: 3%;"
                                 src="img/print.jpg" title="print PP" onclick="sendDataPP('print')">
                            <img class="iconChucNang" id="iconFinishPP"  src="img/refresh.svg"
                                 title="Tại lại table" onclick="sendDataPP('finishPP')">
                        </div>
                        <table class="table" id="tablePP">
                            <thead>
                                <tr>
                                    <th>Mã phiếu</th>
                                    <th>Mã phiếu trả</th>
                                    <th>Mã NV</th>
                                    <th>Tổng tiền</th>
                                </tr>
                            </thead>
                            <tbody id="tbodyPP">
                                <c:forEach var="pm" items="${requestScope.listPP}">
                                    <tr>
                                        <td>${pm.maPP}</td>
                                        <td>${pm.maPT}</td>
                                        <td>${pm.maNV}</td>
                                        <td>${pm.tongTien}</td>
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
                <div class="divTT" id="divtableCTSach" onclick="showListAllCTSach">
                    <img src="img/cancel.svg" alt="Đóng table" onclick="dongTBTT()"
                         style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                    <h3 style="text-align: center;"> Ban chọn mã vạch ở đây!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearchCTSach" name="options">
                            <option value="Mã sách">Mã sách</option>
                            <option value="Mã vạch">Mã vạch</option>
                        </select>
                        <input type="text" id="txtSearchCTSach" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearchCTSach" onclick="searchOfNV" src="img/search1.png"
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

                <div class="divTT" id="divtablePT" onclick="showListAllPT()">
                    <img src="img/cancel.svg" alt="Đóng table" onclick="dongTBTT()"
                         style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                    <h3 style="text-align: center;"> Những mã phiếu trả có lỗi chưa có phiếu phạt mời bạn chọn!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearchPT" name="options">
                            <option value="Mã phiếu trả">Mã phiếu trả</option>
                            <option value="Mã phiếu mượn">Mã phiếu mượn</option>
                        </select>
                        <input type="text" id="txtSearchPT" placeholder="Nhập thông tin">
                        <img class="iconChucNang" id="iconSearchPT" onclick="searchOfNV" src="img/search1.png"
                             alt="icon">
                    </div>
                    <table class="table" id="tablePT">
                        <thead>
                            <tr>
                                <th>Mã phiếu trả</th>
                                <th>Mã phiếu mượn</th>
                                <th>Mã Nhân viên</th>
                                <th>Tổng SL</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pm" items="${requestScope.listPT}">
                                <tr>
                                    <td>${pm.maPT}</td>
                                    <td>${pm.maPM}</td>
                                    <td>${pm.maNV}</td>
                                    <td>${pm.tongSL}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <script>
                    //biến giữ đường link file excel
                    let namePath = "";
                    /*click table*/
                    function clickLoi(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtLoi').value = cells[0].innerText;
                        document.getElementById('txtLoi').readOnly = true;
                        document.getElementById('txtPhanTramTien').value = cells[1].innerText;
                        let currentValue = document.getElementById('txtLiDoCTPP').value;
                        if (currentValue.includes(cells[0].innerText)) {
                            alert("Lỗi này bạn đã có!");
                        } else
                            document.getElementById('txtLiDoCTPP').value = currentValue ? currentValue + "," + cells[0].innerText : cells[0].innerText;
                    }

                    const rowsLoi = document.querySelectorAll('#tableLoi tbody tr');
                    rowsLoi.forEach(row => {
                        row.addEventListener('click', () => clickLoi(row));
                    });

                    function clickCTPP(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtMaPhieuCTPP').value = cells[0].innerText;
                        document.getElementById('txtMaPhieuCTPP').readOnly = true;
                        document.getElementById('txtMaSachCTPP').value = cells[1].innerText;
                        document.getElementById('txtMaVachCTPP').value = cells[2].innerText;
                        document.getElementById('txtMaVachCTPP').readOnly = true;
                        document.getElementById('txtNgayLapCTPP').value = cells[3].innerText;
                        document.getElementById('txtLiDoCTPP').value = cells[4].innerText;
                        document.getElementById('txtTienCTPP').value = cells[5].innerText;
                    }
                    const rowsCTPP = document.querySelectorAll('#tableCTPP tbody tr');
                    rowsCTPP.forEach(row => {
                        row.addEventListener('click', () => clickCTPP(row));
                    });

                    function clickPP(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtMaPhieuPP').value = cells[0].innerText;
                        document.getElementById('txtMaPhieuPP').readOnly = true;
                        document.getElementById('txtMaPTPP').value = cells[1].innerText;
                        document.getElementById('txtTongTienPP').value = cells[3].innerText;


                    }
                    const rowsPP = document.querySelectorAll('#tablePP tbody tr');
                    rowsPP.forEach(row => {
                        row.addEventListener('click', () => clickPP(row));
                    });
//                    function formatDate(date) {
//                        const year = date.getFullYear();
//                        const month = String(date.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
//                        const day = String(date.getDate()).padStart(2, '0');
//                        return `${year}-${month}-${day}`;
//                    }
//
//                    // Lấy ngày hiện tại và gán vào input
//                    document.getElementById('txtNgayLapCTPP').value = formatDate(new Date());

                    /*clear dữ liệu*/
                    function clearInputLoi() {
                        document.getElementById('txtLoi').value = "";
                        document.getElementById('txtLoi').readOnly = false;
                        document.getElementById('txtPhanTramTien').value = "";
                    }

                    function clearInputCTPP() {
                        document.getElementById('txtMaPhieuCTPP').value = "";
                        document.getElementById('txtMaSachCTPP').value = "";
                        document.getElementById('txtMaVachCTPP').value = "";
                        document.getElementById('txtNgayLapCTPP').value = formatDate(new Date());
                        document.getElementById('txtLiDoCTPP').value = "";
                        document.getElementById('txtTienCTPP').value = "0";
                        document.getElementById('txtMaPhieuCTPP').readOnly = false;
                        document.getElementById('txtMaVachCTPP').readOnly = false;
                    }
                    function clearInputPP() {
                        document.getElementById('txtMaPhieuPP').value = "";
                        document.getElementById('txtMaPhieuPP').readOnly = false;
                        document.getElementById('txtMaPTPP').value = "";
                        document.getElementById('txtMaNVPP').value = "";
                        document.getElementById('txtTongTienPP').value = "0";
                    }
                    // Lắng nghe sự kiện khi bàn phím để tìm kiếm lỗi
                    document.getElementById('txtSearchLoi').addEventListener('keyup', function(event) {
                            sendDataLoi('searchLoi');
                    });
                    function sendDataLoi(action) {
                        const formData = new URLSearchParams({
                            action: action,
                            tenLoi: document.getElementById('txtLoi').value,
                            phanTramTien: document.getElementById('txtPhanTramTien').value,
                            optionSearch: document.getElementById('comBoBoxSearchLoi').value,
                            valueSearch: document.getElementById('txtSearchLoi').value,
                        });
                        fetch('http://localhost:9999/cnpm/loi', {
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
                                        kQTimKiemLoi(data.results);
                                    }

                                    // Nếu dữ liệu hợp lệ, tải lại trang
                                    if (data.hopLe) {
                                        window.location.reload();
                                    }
                                })
                                .catch(error => alert('Đã xảy ra lỗi: ' + error));
                    }
                    function kQTimKiemLoi(results)
                    {
                        const tableBody = document.getElementById('tbodyLoi');
                        tableBody.innerHTML = '';
                        if (!Array.isArray(results)) {
                            results = Object.values(results);
                        }
                        results.forEach(item => {
                            const row = document.createElement('tr');
                            const cell1 = document.createElement('td');
                            cell1.textContent = item.tenLoi || '';
                            row.appendChild(cell1);
                            const cell2 = document.createElement('td');
                            cell2.textContent = item.phanTramTien || '';
                            row.appendChild(cell2);
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
                                sendDataPP(action);
                            }
                        };
                        // Hiển thị cửa sổ chọn file
                        fileInput.click();
                    }
                    // Lắng nghe sự kiện khi bàn phím để tìm kiếm phiếu phạt
                    document.getElementById('txtSearchPP').addEventListener('keyup', function(event) {
                            sendDataPP('searchPP');
                    });
                    function sendDataPP(action) {
                        
                        const formData = new URLSearchParams({
                            action: action,
                            maPP: document.getElementById('txtMaPhieuPP').value,
                            maPT: document.getElementById('txtMaPTPP').value,
                            maNV: document.getElementById('txtMaNVPP').value,
                            tongTien: document.getElementById('txtTongTienPP').value,
                            optionSearch: document.getElementById('comBoBoxSearchPP').value,
                            valueSearch: document.getElementById('txtSearchPP').value,
                            nameFileExcel: namePath,
                        });

                        fetch('http://localhost:9999/cnpm/phieuphat', {
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

                                        if (data.resultPP && data.resultPP.length > 0) {
                                            kQTimKiemPP(data.resultPP);
                                            kQTimKiemCTPP(data.resultsCTPP);
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
                    function kQTimKiemPP(results)
                    {
                        const tableBody = document.getElementById('tbodyPP');
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
                            cell2.textContent = item.maPT || '';
                            row.appendChild(cell2);
                            const cell3 = document.createElement('td');
                            cell3.textContent = item.maNV || '';
                            row.appendChild(cell3);
                            const cell4 = document.createElement('td');
                            cell4.textContent = item.tongTien || '';
                            row.appendChild(cell4);
                            tableBody.appendChild(row);
                        });
                    }
                    // Lắng nghe sự kiện khi bàn phím để tìm kiếm phiếu mượn
                    document.getElementById('txtSearchCTPP').addEventListener('keyup', function(event) {
                            sendDataCTPP('searchCTPP');
                    });
                    function sendDataCTPP(action) {
                        const formData = new URLSearchParams({
                            action: action,
                            maPP: document.getElementById('txtMaPhieuCTPP').value,
                            maSach: document.getElementById('txtMaSachCTPP').value,
                            maVach: document.getElementById('txtMaVachCTPP').value,
                            ngayLap: document.getElementById('txtNgayLapCTPP').value,
                            maNV: document.getElementById('txtMaNVPP').value,
                            liDo: document.getElementById('txtLiDoCTPP').value,
                            tien: document.getElementById('txtTienCTPP').value,
                            optionSearch: document.getElementById('comBoBoxSearchCTPP').value,
                            valueSearch: document.getElementById('txtSearchCTPP').value,
                        });
                        fetch('http://localhost:9999/cnpm/ctpp', {
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
                                        kQTimKiemCTPP(data.results);
                                    }
                                    // Nếu dữ liệu hợp lệ, tải lại trang
                                    if (data.hopLe) {
                                        window.location.reload();
                                    }
                                })
                                .catch(error => alert('Đã xảy ra lỗi: ' + error));
                    }
                    function kQTimKiemCTPP(results)
                    {
                        const tableBody = document.getElementById('tbodyCTPP');
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
                            cell2.textContent = item.maSach || '';
                            row.appendChild(cell2);
                            const cell3 = document.createElement('td');
                            cell3.textContent = item.maVach || '';
                            row.appendChild(cell3);

                            const cell4 = document.createElement('td');
                            cell4.textContent = item.ngayLap || '';
                            row.appendChild(cell4);

                            const cell5 = document.createElement('td');
                            cell5.textContent = item.liDo || '';
                            row.appendChild(cell5);

                            const cell6 = document.createElement('td');
                            cell6.textContent = item.tien || '';
                            row.appendChild(cell6);

                            tableBody.appendChild(row);
                        });
                    }
                    //hiện thị xác nhận xóa lỗi
                    document.getElementById('iconXoaLoi').addEventListener('click', function(event) {
                        // Hiển thị hộp thoại xác nhận
                        const confirmDelete = confirm("Bạn có chắc muốn xóa lỗi này  không?");
                        if (!confirmDelete) {
                            event.preventDefault();
                        } else {
                            sendDataLoi('deleteLoi');
                        }
                    });
                    //hiện thị xác nhận xóa chi tiết phiếu phạt
                    document.getElementById('iconXoaCTPP').addEventListener('click', function(event) {
                        // Hiển thị hộp thoại xác nhận
                        const confirmDelete = confirm("Bạn có chắc muốn xóa chi tiết phiếu phạt này không?");
                        if (!confirmDelete) {
                            event.preventDefault();
                        } else {
                            sendDataCTPP('deleteCTPP');
                        }
                    });
                    //hiện thị xác nhận xóa phiếu phạt
                    document.getElementById('iconXoaPP').addEventListener('click', function(event) {
                        // Hiển thị hộp thoại xác nhận
                        const confirmDelete = confirm("Bạn có chắc muốn xóa phiếu phạt này không?");
                        if (!confirmDelete) {
                            event.preventDefault();
                        } else {
                            sendDataPP('deletePP');
                        }
                    });
                    //hiện thị thông báo lấy lỗi
                    function thongBaoLayLoi() {
                        alert("Bạn thêm lỗi bằng cách click vào table Lỗi!");
                    }
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
                        document.getElementById('txtMaSachCTPP').value = cells[0].innerText;
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
                        document.getElementById('txtMaVachCTPP').value = cells[0].innerText;
                        const tableNV = document.getElementById('divtableCTSach');
                        tableNV.style.display = "none";

                    }
                    const rowsCTSah = document.querySelectorAll('#tableCTSach tbody tr');
                    rowsCTSah.forEach(row => {
                        row.addEventListener('click', () => clickCTSach(row));
                    });
                    //hiện thị Phiếu trả
                    function hienThiPT() {
                        const table = document.getElementById('divtablePT');
                        if (table.style.display === 'none') {
                            table.style.display = 'block';
                        } else {
                            table.style.display = 'none';
                        }
                    }
                    function clickPT(row) {
                        const cells = row.getElementsByTagName('td');
                        document.getElementById('txtMaPTPP').value = cells[0].innerText;
                        const tableNV = document.getElementById('divtablePT');
                        tableNV.style.display = "none";
                        document.getElementById('txtMaNVPP').focus();

                    }
                    const rowsPT = document.querySelectorAll('#tablePT tbody tr');
                    rowsPT.forEach(row => {
                        row.addEventListener('click', () => clickPT(row));
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
                    function searchPT()
                    {
                        var value = document.getElementById('txtSearchPT').value.toLowerCase();
                        var option = document.getElementById('comBoBoxSearchPT').value;
                        var rows = document.querySelectorAll('#tablePT tbody tr');
                        if (!value) {
                            alert("Vui lòng nhập thông tin để tìm kiếm phiếu trả!");
                            return;
                        }
                        var columnIndex = 0;
                        if (option === "Mã phiếu mượn")
                            columnIndex = 1;
                        rows.forEach(row => {
                            var cell = row.getElementsByTagName('td')[columnIndex];
                            if (cell && cell.textContent.toLowerCase().includes(value)) {
                                row.style.display = "";
                            } else
                                row.style.display = "none";
                        });
                    }
                    document.getElementById('iconSearchPT').addEventListener('click', function (event) {
                        event.stopPropagation(); // Ngăn chặn sự kiện click lan ra divTableNV
                        searchPT();
                    });
                    //hiện thị hết dữ liệu của table sách
                    function showListAllSach() {
                        var rows = document.querySelectorAll('#tablePT tbody tr');
                        rows.forEach(row => {
                            row.style.display = "";
                        });
                        document.getElementById('txtSearchPT').value = "";
                    }

                    //Sự kiện enter của lỗi
                    document.getElementById('txtLoi').addEventListener('keydown', function (event) {
                        if (event.key === "Enter") {
                            event.preventDefault(); // Ngăn không cho form submit nếu có
                            document.getElementById('txtPhanTramTien').focus(); // Di chuyển đến ô input txtPhanTramTien
                        }
                    });

                    //sự kiện enter của chi tiết phiếu phạt
                    document.getElementById('txtMaPhieuCTPP').addEventListener('keydown', function (event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            hienThiSach();
                        }
                    });
                    document.getElementById('txtNgayLapCTPP').addEventListener('keydown', function (event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            document.getElementById('txtLiDoCTPP').focus();
                        }
                    });

                    document.getElementById('txtLiDoCTPP').addEventListener('keydown', function (event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            document.getElementById('txtTienCTPP').focus();
                        }
                    });

                    //Sự kiện enter của phiếu phạt
                    document.getElementById('txtMaPhieuPP').addEventListener('keydown', function(event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            hienThiPT();
                        }
                    });
                    
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
