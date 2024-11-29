<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý nhân viên</title>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height:100vh;
            width: 100vw;
            font-family:'Times New Roman', Times, serif;
        }


#title {
    width: 100%;
    height: 3%;  /* Điều chỉnh nếu cần */
    background-color: white;
    font-size: 16px;
    font-weight: bold;
    position: fixed;
}

#menu {
    width: 12%;
    height: calc(100vh - 3%);  /* Điều chỉnh chiều cao sao cho không bị chồng lên */
    background-color: #2C2F48;
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
        body, html {
    background-color: #f3f3db;
    margin: 0;
    padding: 0;
    height: 100vh;
    width: 100vw;
    font-family: 'Arial', sans-serif;
}

/* Cải tiến giao diện của form nhập liệu */

    .form-container {
      display: flex; /* Sử dụng flexbox */
    flex-direction: column; /* Xếp các phần tử theo chiều dọc */
    gap: 70px; /* Khoảng cách giữa các phần tử */
        padding: 20px;
        width: 600px;
        margin-left: 24%;
        max-with:800px;
    }



    .form-left {
        flex: 2;
        display: flex;
        flex-direction: column;
        gap: 30px;
    }

    .form-left label {
        height:30px;
        display: flex;
        justify-content: space-between;
    }

    .form-left input {
        width: 80%;
    }

    .boxbutton {
        display: flex;
        justify-content: space-between;
    }
    .search{
        display: flex;
        justify-content: space-between;
        margin-top:30px;
    }
    
    .form-right button {
 
    }

    .form-right button:hover {
        background-color: #f3f3db;
    }
.table-container {
    margin-top: 10px;
    max-height: 350px;
    overflow-y: auto;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 60%;
    margin-left: 25%;
}

.table {
    width: 100%; /* Đảm bảo bảng lấp đầy container */
    border-collapse: collapse;
    table-layout: auto; /* Hoặc giữ fixed nếu cần */
    background-color: #fff;
    box-sizing: border-box; /* Tính cả padding và border */
}

.table th,
.table td {
    padding: 15px;
    text-align: center;
    border: 1px solid #ddd;
    color: #333;
    font-size: 14px;
}

.table th {
    background-color: #5A4E9B;
    color: white;
    font-weight: bold;
}

.table tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

.table tbody tr:hover {
    background-color: #f1f1f1;
    cursor: pointer;
}




     

        .table td:hover {
            white-space: normal;
            word-wrap: break-word;
            z-index: 1;
        }

        /* Cải tiến hiệu ứng focus cho các trường input trong bảng */
        .table input:focus {
            outline: none;
            border-color: #5A4E9B;
        }

        .table input {
            border: 1px solid #ccc;
            padding: 5px;
            border-radius: 3px;
            width: 100%;
        }

/* Responsive layout cho các màn hình nhỏ */
@media (max-width: 768px) {
  
    .form-container {
        margin-left: 5%;
        margin-right: 5%;
    }
    #menu {
        width: 20%;
    }
    #detail {
        width: 80%;
    }
}
#headerTitle {
    margin-bottom:80px;
    font-size: 36px; /* Kích thước chữ to */
    font-weight: bold;
    color: #5A4E9B; /* Màu sắc nổi bật */
    text-align: center; /* Canh giữa chữ */
    margin-top: 20px; /* Khoảng cách từ trên xuống */
    text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); /* Hiệu ứng đổ bóng */
    font-family: 'Arial', sans-serif;
}
.form-right button {
    background: none;
    border: none;
    cursor: pointer;
}
.form-right button img {
    display: block;
}
.form-right button:hover img {
    filter: brightness(1.2); /* Làm sáng biểu tượng khi hover */
}
.meanbox {
    display: flex; /* Sử dụng flexbox */
    flex-direction: column; /* Xếp các phần tử theo chiều dọc */
    gap: 30px; /* Khoảng cách giữa các phần tử */
   
}



    </style>
</head>
<body>

    <div id="headerTitle">
    Quản lý nhân viên
</div>
    <form id="form">
        
            <div id="menu">
                <div class="conponentMenu" id="iconAndName">
                    <img src="img/account.svg" alt="icon">
                    Nguyễn Trung
                    Quản lí
                </div>
                <button id="btnSach" class="conponentMenu">
                    <img src="img/sach.jpg" alt="icon"> Sách
                </button>
                <button id="btnNhanVien" class="conponentMenu">
                    <img src="img/stafff.svg" alt="icon"> Nhân viên
                </button>
                <button id="btnDocGia" class="conponentMenu">
                    <img src="img/customerr.svg" alt="icon"> Độc giả
                </button>
                <button id="btnNhaXuatBan" class="conponentMenu">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nhà xuất bản
                </button>
                <button id="btnNhaCungCap" class="conponentMenu">
                    <img src="img/nhacc.jpg" alt="icon"> Nhà cung cấp
                </button>
                <button id="btnKhuVuc" class="conponentMenu">
                    <img src="img/khuvuc.jpg" alt="icon"> Khu vực
                </button>
                <button id="btnPhieuMuon" class="conponentMenu">
                    <img src="img/export.svg" alt="icon"> Phiếu mượn
                </button>
                <button id="btnPhieuTra" class="conponentMenu">
                    <img src="img/phieutra.jpg" alt="icon"> Phiếu trả
                </button>
                <button id="btnPhieuPhat" class="conponentMenu">
                    <img src="img/phieuphat.jpg" alt="icon"> Phiếu phạt
                </button>
                <button id="btnPhieuNhap" class="conponentMenu">
                    <img src="img/phieunhap.jpg" alt="icon"> Phiếu nhập
                </button>
                <button id="btnPhanQuyen" class="conponentMenu">
                    <img src="img/permission.svg" alt="icon"> Phân quyền
                </button>
                <button id="btnThongKe" class="conponentMenu">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon"> Thống kê
                </button>
                <button id="btnDangXuat" class="conponentMenu">
                    <img src="img/logout.jpg" alt="icon"> Đăng xuất
                </button>
            </div>
    </form>
    
    
    
    
  <div class="meanbox"  ;
    <!-- Form nhập liệu nhân viên -->
<div class="form-container">
    <form id="nhanvienForm">
        <div class="form-left">
 <label for="manv">Mã NV: 
                <input type="text" id="manv" required>
            </label>
            <label for="ho">Họ: 
                <input type="text" id="ho" required>
            </label>
            <label for="ten">Tên: 
                <input type="text" id="ten" required>
            </label>
            <label for="sdt">Số điện thoại: 
                <input type="text" id="sdt" required>
            </label>
            <label for="luong">Lương: 
                <input type="number" id="luong" step="0.01" required>
            </label>
            <label for="ngaysinh">Ngày sinh: 
                <input type="date" id="ngaysinh" required>
            </label>
            <label>Chức vụ: 
                <select id="chucVu" required>
                    <option value="" disabled selected>Chọn chức vụ</option>
                     <option value="Admin">Admin</option>
                        <option value="Quản lý">Quản lý</option>
                        <option value="Nhân viên">Nhân viên</option>
                </select>
            </label><br>
            <input type="hidden" id="action" value="addNhanVien">
        </div>
        <div class="form-right">
            <div class="boxbutton">
            <!-- Nút Thêm -->
            <button type="button" onclick="submitForm('addNhanVien')" title="Thêm nhân viên">
                <img src="img/add.svg" alt="Thêm nhân viên" width="24" height="24">
            </button>
            <!-- Nút Sửa -->
            <button type="button" onclick="submitForm('updateNhanVien')" title="Sửa nhân viên">
                <img src="img/edit.svg" alt="Sửa nhân viên" width="24" height="24">
            </button>
            <!-- Nút Xóa -->
            <button type="button" onclick="submitForm('deleteNhanVien')" title="Xóa nhân viên">
                <img src="img/delete.svg" alt="Xóa nhân viên" width="24" height="24">
            </button>
            </div>
            <div class="search">
            <!-- Nút Tìm kiếm -->
           <div style="display: flex; align-items: center;">
    <input type="text" id="valueSearch" placeholder="Nhập thông tin tìm kiếm..." 
        style="width: 300px; height: 36px; border: 1px solid #ccc; border-radius: 4px; padding: 0 10px;">
    <button type="button" onclick="submitForm('searchNhanVien')" title="Tìm kiếm nhân viên" 
        style="margin-left: -1px; height: 36px; border: none; background-color: #5A4E9B; border-radius: 0 4px 4px 0; padding: 0 10px; display: flex; align-items: center; justify-content: center;">
        <img src="img/search.svg" alt="Tìm kiếm nhân viên" width="24" height="24">
    </button>
</div>
           <button type="button" onclick="resetTable()" title="Reset bảng"
    style="width: 50px; height: 50px; border: none; background-color: transparent; display: flex; align-items: center; justify-content: center; cursor: pointer;">
    <img src="img/icons8-reset-50.png" alt="Reset bảng" width="36" height="36" style="display: block;">
</button>
            </div>
    </form>
</div>
      </div>


<div class="table-container">
    <!-- Bảng hiển thị danh sách nhân viên -->
    <table class="table"  id="nhanvienTable">
        <thead>
            <tr>
                <th>Mã NV</th>
                <th>Họ</th>
                <th>Tên</th>
                <th>Số điện thoại</th>
                <th>Lương</th>
                <th>Ngày sinh</th>
                <th>Chức vụ</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="nv" items="${listNhanVien}">
                <tr>
                    <td>${nv.maNV}</td>
                    <td>${nv.ho}</td>
                    <td>${nv.ten}</td>
                    <td>${nv.soDT}</td>
                    <td>${nv.luong}</td>
                    <td><fmt:formatDate value="${nv.ngaySinh}" pattern="yyyy-MM-dd"/></td>
                    <td>${nv.chucVu}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
    <div class="table-container">
   <div id="resultContainer" class="table" style="display: none;">
    <!-- Bảng kết quả tìm kiếm sẽ hiển thị tại đây -->
</div>
      </div>
    
    
    
    
        
</div>  
        
    <script>
  function submitForm(action) {
    if (action === "searchNhanVien") {
        const valueSearch = document.getElementById("valueSearch").value;
        if (!valueSearch.trim()) {
            alert("Vui lòng nhập thông tin bạn muốn tìm kiếm");
            return;
        }

        // Ẩn bảng gốc và hiển thị bảng kết quả tìm kiếm
        document.getElementById("nhanvienTable").style.display = "none";
        document.getElementById("resultContainer").style.display = "block";

        // Gửi yêu cầu tìm kiếm
        fetch('${pageContext.request.contextPath}/nhanvien', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({ action: action, valueSearch: valueSearch })
        })
        .then(response => response.text())  // Nhận dữ liệu HTML
        .then(result => {
            const resultContainer = document.getElementById("resultContainer");
            resultContainer.innerHTML = result;  // Cập nhật HTML vào container

            // Gắn lại sự kiện click cho các hàng trong bảng mới
            addRowClickEvent();
        })
        .catch(error => console.error('Lỗi:', error));
        return;
    }

    // Mã xử lý các hành động thêm, sửa, xóa...
    const data = {
        action: action,
        manv: document.getElementById("manv").value,
        ho: document.getElementById("ho").value,
        ten: document.getElementById("ten").value,
        sdt: document.getElementById("sdt").value,
        luong: document.getElementById("luong").value,
        chucVu: document.getElementById("chucVu").value,
        ngaysinh: document.getElementById("ngaysinh").value
    };

    fetch('${pageContext.request.contextPath}/nhanvien', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams(data)
    })
    .then(response => response.json())
    .then(result => {
        alert(result.thongbao);
        if (result.hopLe) {
            location.reload();
        }
    })
    .catch(error => console.error('Lỗi:', error));
}

// Hàm gắn sự kiện click vào các hàng trong bảng
function addRowClickEvent() {
    const table = document.getElementById("resultContainer").querySelector("table");
    if (table) {
        const formFields = {
            manv: document.getElementById("manv"),
            ho: document.getElementById("ho"),
            ten: document.getElementById("ten"),
            sdt: document.getElementById("sdt"),
            luong: document.getElementById("luong"),
            ngaysinh: document.getElementById("ngaysinh"),
            chucVu: document.getElementById("chucVu"),
        };

        // Thêm sự kiện click vào từng hàng trong bảng
        table.querySelector("tbody").addEventListener("click", (event) => {
            const row = event.target.closest("tr");
            if (!row) return; // Nếu không phải là hàng, thoát

            const cells = row.children;

            // Đổ dữ liệu từ hàng vào các trường nhập liệu
            formFields.manv.value = cells[0].textContent;
            formFields.ho.value = cells[1].textContent;
            formFields.ten.value = cells[2].textContent;
            formFields.sdt.value = cells[3].textContent;
            formFields.luong.value = cells[4].textContent;
            formFields.ngaysinh.value = cells[5].textContent;
           formFields.chucVu.value = cells[6].textContent.trim(); // Lấy giá trị chức vụ từ bảng và gán vào select
        });
    }
}

document.addEventListener("DOMContentLoaded", () => {
    // Gắn sự kiện click vào các hàng trong bảng khi trang được tải xong
    addRowClickEvent();
});

    
        
        
        
        document.addEventListener("DOMContentLoaded", () => {
    const table = document.getElementById("nhanvienTable");
    const formFields = {
        manv: document.getElementById("manv"),
        ho: document.getElementById("ho"),
        ten: document.getElementById("ten"),
        sdt: document.getElementById("sdt"),
        luong: document.getElementById("luong"),
        ngaysinh: document.getElementById("ngaysinh"),
        chucVu: document.getElementById("chucVu"),
    };

    // Thêm sự kiện click vào từng hàng trong bảng
    table.querySelector("tbody").addEventListener("click", (event) => {
        const row = event.target.closest("tr");
        if (!row) return; // Nếu không phải là hàng, thoát

        const cells = row.children;

        // Đổ dữ liệu từ hàng vào các trường nhập liệu
        formFields.manv.value = cells[0].textContent;
        formFields.ho.value = cells[1].textContent;
        formFields.ten.value = cells[2].textContent;
        formFields.sdt.value = cells[3].textContent;
        formFields.luong.value = cells[4].textContent;
        formFields.ngaysinh.value = cells[5].textContent;
        formFields.chucVu.value = cells[6].textContent.trim(); // Lấy giá trị chức vụ từ bảng và gán vào select
    });
});
function resetTable() {
    // Làm mới lại toàn bộ trang
    location.reload();
}
        
        
    </script>
</body>
</html>
