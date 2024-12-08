<%-- 
    Document   : Sach
    Created on : Nov 20, 2024, 10:36:44 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="DTO.Sach_DTO" %>
<%@ page import="DTO.Nhanvien_DTO" %>
<%
    Nhanvien_DTO nv = (Nhanvien_DTO) session.getAttribute("nv");
    String tasks = (String) session.getAttribute("tasks");
    if (nv == null || nv.getChucVu().equals("admin")) {
        response.sendRedirect("/cnpm/login");
        return;
    }
    String chucvu= nv.getChucVu();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quản Lý Thư Viện</title>
        <link rel="preconnect" href="https://cdnjs.cloudflare.com" />
        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
            integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
            crossorigin="anonymous"
            referrerpolicy="no-referrer"
            />
        <link rel="stylesheet" href="css/reset.css" />
        <!--<link rel="stylesheet" href="./assets/font/stylesheet.css" />-->
        <link rel="stylesheet" href="css/stylesach.css" />
    </head>
    <body>
        <div class="home-screen">
            <!-- <div class="main-content"> -->
            <!-- Menu navigation -->
            <div class="blog-nav" id="menu">
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
                <button id="btnDangXuat" value="đăng xuất" class="conponentMenu">
                    <img src="img/logout.jpg" alt="icon"> Đăng xuất
                </button>

            </div>
            <!-- Nội dung trang -->
            <div class="blog-content">
                <!-- Phần giao diện sách  -->
                <div class="body-mainbooks page-item is-show">

                    <!-- Đầu trang -->
                    <div class="header">
                        <div class="blog-search">
                            <div class="content-search">
                                <button class="seach-btn">
                                    <div class="icon">
                                        <img src="img/search.svg" alt="">
                                    </div>
                                </button>
                                <div class="text-search">
                                    <input type="text" name="" class="input-search" placeholder="Tìm kiếm..." />
                                </div>
                            </div>
                            <button class="search-adv">
                                <div class="icon">
                                    <img src="img/Searchadv.svg" alt="">
                                </div>
                            </button>
                        </div>
                        <!-- <div class="blog-admin">
                            <div class="info">
                              <div class="info-content">
                                <span class="id">ID:001</span>
                                <span class="admin">Admin</span>
                              </div>
                              <div class="icon"><img src="img/imgAdmin.svg" alt=""></div>
                            </div>
                        </div> -->
                    </div>
                    <!-- Thân chính của trang -->
                    <div class="body">
                        <!-- Phần sách  mới -->
                        <div class="blog-booknew">
                            <!-- Heaading và button thêm  -->
                            <div class="header-booknew">
                                <div class="title">
                                    <h2 class="heading">Sách mới</h2>
                                </div>
                                <button class="btn-add">
                                    <a href="#" class="btn">
                                        <div class="icon">
                                            <img src="img/add01.svg" alt="">
                                        </div>
                                        <span>Thêm sách mới</span>
                                    </a>
                                </button>
                            </div>
                            <!-- Hình ảnh sách mới -->
                            <div class="tab">
                                <i class="fa fa-angle-left tab-prev disabled"></i>
                                <div class="tab-list">
                                    <!--  -->
                                    <c:forEach var="item" items="${requestScope.listSach_New}">
                                        <div class="tab-item" data-idnew="${item.maSach}">
                                            <a href="#">
                                                <img src="img/${item.anh}" alt="" class="image">
                                            </a>
                                        </div>
                                    </c:forEach>

                                    <!--  -->

                                </div>
                                <i class="fa fa-angle-right tab-next"></i>
                            </div>
                        </div>
                        <!-- Phần sản phẩm sách -->
                        <div class="blog-books">
                            <div class="heading">
                                <h2 class="title">
                                    Danh mục sách
                                </h2>
                            </div>
                            <div class="blog-listbooks">
                                <c:set var="pageIndex" value="1" /> 
                                <c:forEach var="page" items="${requestScope.paginatedProducts}">
                                    <ul class="list-books ${pageIndex == 1 ? 'active' : ''}" data-page="${pageIndex}">
                                        <!-- Item  -->
                                        <c:forEach var="item" items="${page}">
                                            <li data-id="${item.maSach}">
                                                <div class="blog-item">
                                                    <a href="#">
                                                        <img src="img/${item.anh}" alt="${item.tenSach}" class="image">
                                                        <div class="info">
                                                            <div class="title">
                                                                <h3 class="line-clamp">${item.tenSach}</h3>
                                                            </div>
                                                            <div class="blog-value">
                                                                <p class="value">${item.giaTien} đ</p>
                                                                <p class="quality">Tồn kho<strong >${item.soLuong}</strong></p>
                                                            </div>
                                                        </div>
                                                    </a>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <c:set var="pageIndex" value="${pageIndex + 1}" />
                                </c:forEach>
                            </div>
                        </div>
                        <div class="tab-page">
                            <i class="fa fa-angle-left tab-prev disabled"></i>
                            <div class="tab-list-page">
                                <c:forEach var="i" begin="1" end="${requestScope.tongTrang}">
                                    <div class="tab-item-page ${i == 1 ? 'active' : ''}" data-page="${i}">
                                        ${i}
                                    </div>
                                </c:forEach>
                            </div>
                            <i class="fa fa-angle-right tab-next"></i>
                        </div>
                        <!-- .... -->
                    </div>
                    <!-- kết thúc phần sản phẩm -->
                </div> 
                <!-- kết thúc phần sản phẩm -->
                <!-- Form add -->
                <div class="form-add page-item">
                    <div class="title">
                        <h2 class="heading">Thêm sách mới</h2>
                    </div>
                    <form action="">
                        <div class="body-left">
                            <div class="form-group">
                                <label for="book-id">Mã sách</label><br>
                                <input class="input-add" type="text" id="book-id" name="book-id" placeholder="Ví dụ: 1223" value = "">
                                <p class="form-error error-maSach"></p>
                            </div>
                            <div class="form-group">
                                <label for="book-name">Tên sách</label><br>
                                <input class="input-add" type="text" id="book-name" name="book-name" placeholder="Ví dụ: Đắc nhân tâm" value ="">
                                <p class="form-error error-tenSach"></p>
                            </div>
                            <div class="form-group">
                                <label for="book-author">Tác giả</label><br>
                                <input class="input-add" type="text" id="book-author" name="book-author" placeholder="Ví dụ: Trần Duy Hoàng" value ="">
                                <p class="form-error error-tacGia"></p>
                            </div>
                            <div class="form-group">
                                <label for="publisher-code">Mã nhà xuất bản</label><br>
                                <input class="input-add" type="text" id="publisher-code" name="publisher-code" placeholder="Ví dụ: 111" value="">
                                <p class="form-error error-NXB"></p>
                            </div>
                            <div class="form-group">
                                <label for="supplier-code">Mã nhà cung cấp</label><br>
                                <input type="text" id="supplier-code" name="supplier-code" placeholder="Không được nhập" value="" disabled>
                                <p class="form-error error-NCC"></p>
                            </div>
                            <div class="form-group">
                                <label for="zone-code">Mã khu vực</label><br>
                                <input class="input-add" type="text" id="zone-code" name="zone-code" placeholder="Ví dụ: 2233" value="">
                                <p class="form-error error-khuVuc"></p>
                            </div>
                            <div class="form-group">
                                <label for="book-price">Giá(VNĐ)</label><br>
                                <input class="input-add" type="text" id="book-price" name="book-price" placeholder="Ví dụ: 100000" value="">
                                <p  class="form-error error-Gia"></p>
                            </div>
                            <div class="form-group">
                                <label for="number-of-books">Số lượng</label><br>
                                <input type="text" id="number-of-books" name="number-of-books" placeholder="Không được nhập" value="" disabled>
                                <p class="form-error error-soLuong"></p>
                            </div>
                            <div class="form-group">
                                <label  for="book-year">Năm xuất bản</label><br>
                                <input class="input-add" type="text" id="book-year" name="book-year" placeholder="Ví dụ: 1998" value="">
                                <p class="form-error error-NamXB"></p>
                            </div>

                        </div>
                        <div class="body-right">
                            <div class="blog-loadimg">
                                <div class="load-left">
                                    <img src="img/image 8.png" alt="">
                                </div>
                                <div class="load-right">
                                    <div class="right-top">
                                        <div class="form-group">
                                            <label for="avatar">
                                                <img
                                                    id="preview"
                                                    class="avatar-preview"
                                                    src="img/image-booksmall.png"
                                                    alt=""
                                                    />
                                            </label>
                                            <input
                                                type="file"
                                                id="avatar"
                                                name="avatar"
                                                hidden

                                                />
                                            <label for="avatar">
                                                <img
                                                    class="avatar-preview"
                                                    src="img/imgloadsvg.svg"
                                                    alt=""
                                                    />
                                            </label>
                                        </div>
                                        <!--  -->
                                        <button type="button" class="delete-img">
                                            <span>Xóa ảnh</span>
                                        </button>
                                    </div>
                                </div>
                                <!--  -->
                            </div>
                            <p class="form-error error-anh">Chỉ cho phép chọn ảnh (JPG, PNG, GIF).</p>
                            <!-- keets thucs load -->
                            <div class="form-group">
                                <label for="book-describe">Mô tả</label><br>
                                <textarea class="input-add"  name="book-describe" id="book-describe" placeholder="Nhập mô tả sơ lược hoặc các thông tin về sách" value="" ></textarea>
                                <p class="form-error error-moTa"></p>
                            </div>

                            <div class="group-btn">
                                <button class="btn-clear btn">
                                    <div class="icon">
                                        <img src="img/clear.svg" alt="">
                                    </div>
                                    <span>Xóa dữ liệu</span>
                                </button>
                                <button class="btn-skip btn" type="button" value="bỏ qua">
                                    <div class="icon"><img src="img/skip.svg" alt=""></div>
                                    <span>Bỏ qua</span>
                                </button>
                                <!-- <button class="btn-save btn">
                                  <div class="icon"><img src="img/save.svg" alt=""></div>
                                  <span>Lưu</span>
                                </button> -->
                                <div class="form-row submit-box">
                                    <button class="submit-btn btn-save btn" value="bỏ qua" type="submit">
                                        <img src="img/save.svg" alt="">
                                        Lưu</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- kết thúc form add -->
                <!-- Phần form Chi tiết -->
                <div class="form-detail page-item">
                    <div class="title">
                        <h2 class="heading">Thông tin sách</h2>
                    </div>
                    <form action="">
                        <div class="body-left">
                            <div class="form-group">
                                <label for="book-iddetail">Mã sách</label><br>
                                <input type="text" id="book-iddetail" name="book-iddetail" placeholder="" value = "" disabled>
                            </div>
                            <div class="form-group">
                                <label for="book-namedetail">Tên sách</label><br>
                                <input class="input-detail" type="text" id="book-namedetail" name="book-namedetail" placeholder="" value ="">
                                <p class="form-error error-tenSachDetail"></p>
                            </div>
                            <div class="form-group">
                                <label for="book-authordetail">Tác giả</label><br>
                                <input class="input-detail" type="text" id="book-authordetail" name="book-authordetail" placeholder="" value ="">
                                <p class="form-error error-tacGiaDetail"></p>
                            </div>
                            <div class="form-group">
                                <label for="publisher-codedetail">Mã nhà xuất bản</label><br>
                                <input type="text" id="publisher-codedetail" name="publisher-codedetail" placeholder="" value="" disabled>
                            </div>
                            <div class="form-group">
                                <label for="supplier-codedetail">Mã nhà cung cấp</label><br>
                                <input type="text" id="supplier-codedetail" name="supplier-codedetail" placeholder="" value="" disabled>
                            </div>
                            <div class="form-group">
                                <label for="zone-codedetail">Mã khu vực</label><br>
                                <input type="text" id="zone-codedetail" name="zone-codedetail" placeholder="" value="" disabled>
                            </div>
                            <div class="form-group">
                                <label for="book-pricedetail">Giá(VNĐ)</label><br>
                                <input class="input-detail" type="text" id="book-pricedetail" name="book-pricedetail" placeholder="" value="">
                                <p class="form-error error-giaDetail"></p>
                            </div>
                            <div class="form-group">
                                <label for="number-of-booksdetail">Số lượng</label><br>
                                <input  type="text" id="number-of-booksdetail" name="number-of-booksdetail" placeholder="0" value="" disabled>
                            </div>
                            <div class="form-group">
                                <label for="book-yeardetail">Năm xuất bản</label><br>
                                <input class="input-detail" type="text" id="book-yeardetail" name="book-yeardetail" placeholder="" value="">
                                <p class="form-error error-namXuatBanDetail"></p>
                            </div>

                        </div>
                        <div class="body-right">
                            <!--  -->
                            <div class="blog-loadimg">
                                <div class="load-left">
                                    <img src="img/image 8.png" alt="">
                                </div>
                                <div class="load-right">
                                    <div class="right-top">
                                        <div class="form-group">
                                            <label for="avatardetail">
                                                <img
                                                    id="previewdetail"
                                                    class="avatar-previewdetail"
                                                    src="img/image-booksmall.png"
                                                    alt=""
                                                    />
                                            </label>
                                            <input
                                                type="file"
                                                id="avatardetail"
                                                name="avatardetail"
                                                hidden
                                                />
                                            <label for="avatardetail">
                                                <img
                                                    class="avatar-previewdetai"
                                                    src="img/imgloadsvg.svg"
                                                    alt=""
                                                    />
                                            </label>
                                        </div>
                                        <!--  -->
                                        <button type="button" class="delete-img" >
                                            <span>Xóa ảnh</span>
                                        </button>
                                    </div>
                                </div>
                                <!--  -->
                            </div>
                            <p class="form-error error-anhDetail"></p>
                            <!-- keets thucs load -->
                            <!--  -->
                            <div class="group-detail">
                                <button type="button" value="cts" class="btn btn-detail">
                                    <span>Chi tiết sách</span>
                                </button>
                            </div>
                            <div class="form-group">
                                <label for="book-describedetail">Mô tả</label><br>
                                <textarea class="input-detail"  name="book-describedetail" id="book-describedetail" placeholder="" value=""></textarea>
                                <p class="form-error error-moTaDetail"></p>
                            </div>
                            <div class="group-btn">
                                <button type="button" class="btn-delete btn">
                                    <div class="icon">
                                        <img src="img/deletewhite.svg" alt="">
                                    </div>
                                    <span>Xóa sách</span>
                                </button>
                                <button type="button" class="btn-skip btn" value="bỏ qua">
                                    <div class="icon"><img src="img/skip.svg" alt=""></div>
                                    <span>Bỏ qua</span>
                                </button>
                                <div class="form-row submit-box">
                                    <button class="submit-btn btn-save btn" type="submit">
                                        <img src="img/save.svg" alt="">
                                        Lưu</button>
                                </div>
                            </div>

                        </div>
                    </form>
                </div>
                <!-- kết thúc form detail -->

            </div>
        </div>
        <div class="modal">
            <div class="overlay"></div>
            <!-- Phần tím kiếm -->
            <div class="modal-content">
                <button class="close-btn">
                    <i class="fa-solid fa-xmark"></i>
                </button>
                <label for="" class="form-label large"> Giá </label>
                <div class="radio-group radio-money">
                    <div class="radio-btn">
                        <input type="radio" name="money" id="Level_one"/>
                        <label for="Level_one">Dưới 99.000(VNĐ)</label>
                    </div>

                    <div class="radio-btn">
                        <input type="radio" name="money" id="Level_two" />
                        <label for="Level_two">100.000(VNĐ)-199.000(VNĐ)</label>
                    </div>

                    <div class="radio-btn">
                        <input type="radio" name="money" id="Level_three" />
                        <label for="Level_three">200.000(VNĐ)-299(VNĐ)</label>
                    </div>

                    <div class="radio-btn">
                        <input type="radio" name="money" id="Level_four" />
                        <label for="Level_four">300.000(VNĐ) Trở lên</label>
                    </div>
                </div>
                <!-- Khu vực -->
                <label for="" class="form-label large"> Khu vực </label>
                <div class="radio-group radio-money">
                    <c:forEach var="item" items="${requestScope.listKhuVuc}">
                        <div class="radio-btn">
                            <input type="radio" name="area" id="${item.maKV}" />
                            <label for="${item.maKV}">${item.tenKV}</label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <!--  -->
            <!--  -->
        </div>
        <div class="modal-form">
            <div class="overlay-form"></div>
            <div class="modal-body">
                <button class="close-btn" value="đóng cts">
                    <i class="fa-solid fa-xmark"></i>
                </button>
                <form class="form-modal" action="">
                    <div class="form-group">
                        <label for="book-id-modal" class="form-label">Mã sách</label>
                        <input
                            type="text"
                            id="book-id-modal"
                            class="form-input form-input_not"
                            name="book-id-modal"
                            value = ""
                            placeholder="Mã sách"
                            disabled
                            />
                        <p class="form-errorModal error-maSach-Modal">!Vui lòng chọn thông tin</p>
                    </div>
                    <div class="form-group">
                        <label for="code-detail-modal" class="form-label">Mã vạch</label>
                        <input
                            type="text"
                            id="code-detail-modal"
                            class="form-input form-input_not"
                            name="code-detail-modal"
                            value=""
                            placeholder="Mã vạch"
                            />
                        <p class="form-errorModal error-maVach-Modal">!Vui lòng chọn thông tin</p>
                    </div>
                    <div class="form-group"> 
                        <label for="condition-book-modal" class="form-label"
                               >Tình trạng sách</label
                        >
                        <input
                            type="text"
                            id="condition-book-modal"
                            class="form-input"
                            name="condition-book-modal"
                            value=""
                            placeholder="Tình trạng(Có thể để trống)"
                            />
                        <p class="form-errorModal error-tinhTrang-Modal">!Vui lòng chọn thông tin</p>
                    </div>
                    <div class="table-container">
                        <table border="" class="table-detail custom-table">
                            <thead>
                                <tr>
                                    <th>Mã sách</th>
                                    <th>Mã vạch</th>
                                    <th>Tình trạng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for(int i=0; i < 5; i++) { %>
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
                    <div class="group-btn">
                        <button type="submit" class="btn btn-save-book">Lưu</button>
                        <button type="button" class="btn btn-delete-book">Xóa</button>
                    </div>
                </form>
            </div>
        </div>
        <script>
        let chucvu = "<%= chucvu %>";

        if (chucvu === "Độc giả") {
            const buttons = document.querySelectorAll("button");
            buttons.forEach(button => {
                button.disabled = true;
                if (button.value === "sách" || button.value === "đăng xuất"
                        || button.value === "bỏ qua" || button.value === "cts"
                        || button.value === "đóng cts") {
                    button.disabled = false;
                }
            });
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
        <script src="js/Sach.js"></script>
    </body>
</html>
