/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/* global window, document, URL */

window.addEventListener("load", function () {
// ==================Các hàm và sự kiện liên quan đến trang chính ==================
//   Button tìm kiếm nâng cao
const modal = document.querySelector(".modal");
const btn_searchAdv = document.querySelector(".search-adv");
btn_searchAdv.addEventListener("click", function (e) {
    modal.style.display = "flex";
});
document
        .querySelector(".modal .close-btn")
        .addEventListener("click", function (e) {
            modal.style.display = "none";
        });
//   Button thêm
const pageItems = document.querySelectorAll(".page-item");
const btn_add = document.querySelector(".btn-add");
btn_add.addEventListener("click", function (e) {
    e.preventDefault();
    pageItems.forEach((item) => item.classList.remove("is-show"));
    document.querySelector(".form-add").classList.add("is-show");
    document.getElementById("preview").src = "img/photo-film-solid (1).svg";
    document.querySelector(".form-add .load-left img").src =
            "img/photo-film-solid (1).svg";
    const btn_skip = document.querySelector(".form-add .btn-skip");
    btn_skip.addEventListener("click", function () {
        pageItems.forEach((item) => item.classList.remove("is-show"));
        document.querySelector(".body-mainbooks").classList.add("is-show");
        // const listError = document.querySelectorAll(".form-add .form-error");
        // listError.forEach((item) => {
        //   item.style.visibility = "hidden";
        // });
        clearFormAdd();
    });
});

// Sự kiện thay đổi ảnh
let fileNameBookImg, fileNameDetailImg;
const errorAnh = document.querySelector(".error-anh");
const error_anhDetail = document.querySelector(".error-anhDetail");
document.getElementById("avatar").addEventListener("change", function () {
    const preview = document.getElementById("preview");
    const previewImage = document.querySelector(".form-add .load-left img");
    fileNameBookImg = this.files[0].name;
    const file = this.files[0]; // Lấy file đầu tiên
    if (file) {
        // Kiểm tra loại file
        const allowedTypes = ["image/jpeg", "image/png", "image/gif"];
        if (!allowedTypes.includes(file.type)) {
            errorAnh.textContent = "Chỉ cho phép chọn ảnh (JPG, PNG, GIF).";
            errorAnh.style.visibility = "visible";
            // Xóa file không hợp lệ
            //  if(fileNameBookImg !== "photo-film-solid (1).svg")
            document.getElementById("preview").src =
                    "./assets/img/photo-film-solid (1).svg";
            document.querySelector(".form-add .load-left img").src =
                    "./assets/img/photo-film-solid (1).svg";
            return;
        } else {
            errorAnh.style.visibility = "hidden";
        }
    }
    if (this.files && this.files[0]) {
        previewImage.src = URL.createObjectURL(this.files[0]);
        preview.src = URL.createObjectURL(this.files[0]);
    }
});
document
        .getElementById("avatardetail")
        .addEventListener("change", function () {
            const preview = document.getElementById("previewdetail");
            const previewImage = document.querySelector(
                    ".form-detail .load-left img"
                    );
            fileNameDetailImg = this.files[0].name;
            const file = this.files[0]; // Lấy file đầu tiên
            if (file) {
                // Kiểm tra loại file
                const allowedTypes = ["image/jpeg", "image/png", "image/gif"];
                if (!allowedTypes.includes(file.type)) {
                    error_anhDetail.textContent =
                            "Chỉ cho phép chọn ảnh (JPG, PNG, GIF).";
                    error_anhDetail.visibility = "visible";
                    // Xóa file không hợp lệ
                    //  if(fileNameBookImg !== "photo-film-solid (1).svg")
                    document.getElementById("preview").src =
                            "./assets/img/photo-film-solid (1).svg";
                    document.querySelector(".form-add .load-left img").src =
                            "./assets/img/photo-film-solid (1).svg";
                    return;
                } else {
                    error_anhDetail.style.visibility = "hidden";
                }
            }
            if (this.files && this.files[0]) {
                previewImage.src = URL.createObjectURL(this.files[0]);
                preview.src = URL.createObjectURL(this.files[0]);
            }
        });
document
        .querySelector(".form-add .delete-img")
        .addEventListener("click", function (e) {
            // const fileInput = document.getElementById("avatar");
            document.getElementById("preview").src = "img/photo-film-solid (1).svg";
            document.querySelector(".form-add .load-left img").src =
                    "img/photo-film-solid (1).svg";
            errorAnh.textContent = "Chỉ cho phép chọn ảnh (JPG, PNG, GIF).";
            errorAnh.style.visibility = "visible";
        });
document
        .querySelector(".form-detail .delete-img")
        .addEventListener("click", function (e) {
            // const fileInput = document.getElementById("avatar");
            document.getElementById("previewdetail").src =
                    "img/photo-film-solid (1).svg";
            document.querySelector(".form-detail .load-left img").src =
                    "img/photo-film-solid (1).svg";
            error_anhDetail.textContent = "Chỉ cho phép chọn ảnh (JPG, PNG, GIF).";
            error_anhDetail.style.visibility = "visible";
        });
// Phần sách mới
const tabItems = document.querySelectorAll(".tab-item");
const tabList = document.querySelector(".tab-list");
const tab = document.querySelector(".tab");
const tabNext = document.querySelector(".tab-next");
const tabPrev = document.querySelector(".tab-prev");
const tabScrollWidth = tabList.scrollWidth - tabList.clientWidth;
let deltaScroll = 200;
[...tabItems].forEach((item) =>
    item.addEventListener("click", handleTabClick)
);
function handleTabClick(e) {
    [...tabItems].forEach((item) => item.classList.remove("active"));
    e.target.classList.add("active");
    let leftSpacing = e.target.offsetLeft;
    tabList.scroll(leftSpacing / 2, 0);
}
tabList.addEventListener("wheel", function (e) {
    e.preventDefault();
    const delta = e.deltaY;
    this.scrollLeft += delta;
    if (this.scrollLeft > 0) {
        tabPrev.classList.remove("disabled");
    } else {
        tabNext.classList.remove("disabled");
    }
    if (this.scrollLeft >= tabScrollWidth) {
        tabNext.classList.add("disabled");
    } else if (this.scrollLeft <= 0) {
        tabPrev.classList.add("disabled");
    }
});
tabNext.addEventListener("click", function (e) {
    tabPrev.classList.remove("disabled");
    tabList.scrollLeft += deltaScroll;
    if (tabList.scrollLeft >= tabScrollWidth) {
        this.classList.add("disabled");
    }
});
tabPrev.addEventListener("click", function (e) {
    tabNext.classList.remove("disabled");
    tabList.scrollLeft -= deltaScroll;
    if (tabList.scrollLeft <= 0) {
        this.classList.add("disabled");
    }
});
// Phần chuyển trang sách
const tabItemsPage = document.querySelectorAll(".tab-item-page");
const tabListPage = document.querySelector(".tab-list-page");
const tabPage = document.querySelector(".tab-page");
const tabNextPage = document.querySelector(".tab-page .tab-next");
const tabPrevPage = document.querySelector(".tab-page .tab-prev");
const tabScrollWidthPage = tabListPage.scrollWidth - tabListPage.clientWidth;
let deltaScrollPage = 100;
[...tabItemsPage].forEach((item) => {
    item.addEventListener("click", handleTabPageClick);
});
function handleTabPageClick(e) {
    const tabItemsPage_One = document.querySelectorAll(".tab-item-page");
    [...tabItemsPage_One].forEach((item) => item.classList.remove("active"));
    e.target.classList.add("active");
    let leftSpacing = e.target.offsetLeft;
    tabListPage.scroll(leftSpacing / 2, 0);
    const listPage = document.querySelectorAll(".list-books");
    const tabPage = e.target.dataset.page;

    // pageNumber.forEach((el) => el.classList.remove("active"));
    // e.target.classList.add("active");
    listPage.forEach((el) => {
        el.classList.remove("active");
        if (el.getAttribute("data-page") === tabPage) {
            el.classList.add("active");
        }
    });
}
tabListPage.addEventListener("wheel", function (e) {
    e.preventDefault();
    const delta = e.deltaY;
    this.scrollLeft += delta;
    if (this.scrollLeft > 0) {
        tabPrevPage.classList.remove("disabled");
    } else {
        tabNextPage.classList.remove("disabled");
    }
    if (this.scrollLeft >= tabScrollWidthPage) {
        tabNextPage.classList.add("disabled");
    } else if (this.scrollLeft <= 0) {
        tabPrevPage.classList.add("disabled");
    }
});
tabNextPage.addEventListener("click", function (e) {
    tabPrevPage.classList.remove("disabled");
    tabListPage.scrollLeft += deltaScrollPage;
    if (tabListPage.scrollLeft >= tabScrollWidthPage) {
        this.classList.add("disabled");
    }
});
tabPrevPage.addEventListener("click", function (e) {
    tabNextPage.classList.remove("disabled");
    tabListPage.scrollLeft -= deltaScrollPage;
    if (tabListPage.scrollLeft <= 0) {
        this.classList.add("disabled");
    }
});
// Hàm xem chi tiết sách khi bấm vào sách
const bookItems = document.querySelectorAll(".blog-listbooks li");
bookItems.forEach((item) =>
    item.addEventListener("click", function () {
        const bookId = parseInt(item.getAttribute("data-id"));
        viewBookDetails(bookId, "seeDetails");
    })
);
const bookItemNew = document.querySelectorAll(".blog-booknew .tab-item");
bookItemNew.forEach((item) =>
    item.addEventListener("click", function () {
        const bookId = parseInt(item.getAttribute("data-idnew"));
        viewBookDetails(bookId, "seeDetails");
    })
);
function viewBookDetails(bookId, action) {
    const formData = new URLSearchParams({
        action: action,
        maSach: bookId,
    });
    // Gửi yêu cầu POST đến Servlet
    fetch("http://localhost:9999/cnpm/sach", {
        // Địa chỉ URL của Servlet
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData.toString(), // Chuyển đối tượng formData thành chuỗi URL-encoded
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Không thể lấy thông tin sách");
                }
                return response.json(); // Chuyển đổi phản hồi từ server thành JSON
            })
            .then((data) => {
                // Hiển thị thông tin sách trong form hoặc một phần tử HTML
                if (data && data.maSach) {
                    document.getElementById("book-iddetail").value = data.maSach;
                    document.getElementById("book-namedetail").value = data.tenSach;
                    document.getElementById("book-authordetail").value = data.tacGia;
                    document.getElementById("publisher-codedetail").value = data.maNXB;
                    document.getElementById("supplier-codedetail").value = data.maNCC;
                    document.getElementById("zone-codedetail").value = data.maKhuVuc;
                    document.getElementById("book-pricedetail").value = data.giaTien;
                    document.getElementById("number-of-booksdetail").value = data.soLuong;
                    document.getElementById("book-yeardetail").value = data.namXuatBan;
                    const linkAnh = "img/" + data.anh;
                    document.querySelector(
                            ".form-detail .blog-loadimg .load-left img"
                            ).src = linkAnh;
                    document.getElementById("previewdetail").src = linkAnh;
                    document.getElementById("book-describedetail").value = data.moTa;
                    // document.querySelector(".form-detail").classList.add("is-show");
                    pageItems.forEach((item) => item.classList.remove("is-show"));
                    document.querySelector(".form-detail").classList.add("is-show");
                }
            })
            .catch((error) => alert("Đã xảy ra lỗi: " + error.message));
}
// =================Kết thúc phần trang chính tiết sách ================================
// =================Sự kiện ở form thêm =====================================
// Sự kiện clear input
const btnClear_add = document.querySelector(".form-add .btn-clear");
function clearFormAdd() {
    document.getElementById("book-id").value = "";
    document.getElementById("book-name").value = "";
    document.getElementById("book-author").value = "";
    document.getElementById("publisher-code").value = "";
    document.getElementById("supplier-code").value = "";
    document.getElementById("zone-code").value = "";
    document.getElementById("book-price").value = "";
    document.getElementById("number-of-books").value = "";
    document.getElementById("book-year").value = "";
    const linkAnh = "img/photo-film-solid (1).svg";
    document.querySelector(".form-add .blog-loadimg .load-left img").src =
            linkAnh;
    document.getElementById("preview").src = linkAnh;
    document.getElementById("book-describe").value = "";
    errorAnh.textContent = "Chỉ cho phép chọn ảnh (JPG, PNG, GIF).";
    errorAnh.style.visibility = "visible";
    const listInput = document.querySelectorAll(".input-add");
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.style.visibility = "hidden";
        }
    });
}
btnClear_add.addEventListener("click", function (event) {
    event.preventDefault();
    clearFormAdd();
});
const btnSave_add = document.querySelector(".form-add .btn-save");
btnSave_add.addEventListener("click", function (event) {
    event.preventDefault();
    addBooks();
});
// Thêm sách
// function checkInfo() {
const bookId = document.getElementById("book-id");
const error_maSach = document.querySelector(".error-maSach");
bookId.addEventListener("input", function () {
    const value = bookId.value;
    checkErrorNumber(error_maSach, value);
});
const bookName = document.getElementById("book-name");
const error_tenSach = document.querySelector(".error-tenSach");
bookName.addEventListener("input", function () {
    const value = bookName.value;
    checkErrorText(error_tenSach, value);
});
const bookAuthor = document.getElementById("book-author");
const error_tacGia = document.querySelector(".error-tacGia");
bookAuthor.addEventListener("input", function () {
    const value = bookAuthor.value;
    checkErrorText(error_tacGia, value);
});
const publisherCode = document.getElementById("publisher-code");
const error_maNXB = document.querySelector(".error-NXB");
publisherCode.addEventListener("input", function () {
    const value = publisherCode.value;
    checkErrorNumber(error_maNXB, value);
});
const supplierCode = document.getElementById("supplier-code").value;
const error_NCC = document.querySelector(".error-NCC");
error_NCC.style.visibility = "hidden";
const zoneCode = document.getElementById("zone-code");
const error_maKhuVuc = document.querySelector(".error-khuVuc");
zoneCode.addEventListener("input", function () {
    const value = zoneCode.value;
    checkErrorNumber(error_maKhuVuc, value);
});
const bookPrice = document.getElementById("book-price");
const error_giaTien = document.querySelector(".error-Gia");
bookPrice.addEventListener("input", function () {
    const value = bookPrice.value;
    checkErrorNumber(error_giaTien, value);
});
const numberOfBooks = document.getElementById("number-of-books");
const error_soLuong = document.querySelector(".error-soLuong");
error_soLuong.style.visibility = "hidden";
const bookYear = document.getElementById("book-year");
const error_namXuatBan = document.querySelector(".error-NamXB");
bookYear.addEventListener("input", function () {
    const value = bookYear.value;
    checkErrorYear(error_namXuatBan, value);
});
const bookDescribe = document.getElementById("book-describe");
const error_moTa = document.querySelector(".error-moTa");
bookDescribe.addEventListener("input", function () {
    const value = bookDescribe.value;
    checkErrorText(error_moTa, value);
});
const bookImage = fileNameBookImg;
// }
function checkErrorNumber(error, value) {
    // Kiểm tra nếu giá trị trống
    if (value.trim() === "") {
        error.textContent = "! Không được để trống";
        error.style.visibility = "visible";
    } else if (/^\d+$/.test(value) && parseInt(value) > 0) {
        // Kiểm tra nếu chỉ chứa số và là số nguyên lớn hơn 0
        if (value.startsWith("0")) {
            error.textContent = "! Bắt buộc phải là số nguyên lớn hơn 0";
            error.style.visibility = "visible";
            return;
        }
        {
            const numberValue = parseInt(value);
            if (numberValue > Number.MAX_SAFE_INTEGER) {
                error.textContent = "! Vượt quá giới hạn";
                error.style.visibility = "visible";
            } else {
                error.style.visibility = "hidden";
            }
        }
    } else {
        // Nếu giá trị không phải số nguyên hoặc có kí tự không hợp lệ
        error.textContent = "! Bắt buộc phải là số nguyên lớn hơn 0";
        error.style.visibility = "visible";
    }
}
function checkErrorText(error, value) {
    if (value === "") {
        error.textContent = "! Không được để trống";
        error.style.visibility = "visible";
    } else {
        error.style.visibility = "hidden";
    }
}
function checkErrorYear(error, value) {
    if (value.trim() === "") {
        error.textContent = "! Không được để trống";
        error.style.visibility = "visible";
    } else if (/^\d+$/.test(value) && parseInt(value) > 0) {
        // Kiểm tra nếu chỉ chứa số và là số nguyên lớn hơn 0
        if (value.startsWith("0")) {
            error.textContent = "! Không hợp lệ";
            error.style.visibility = "visible";
            return;
        }
        {
            const numberValue = parseInt(value);
            const currentYear = new Date().getFullYear();
            if (numberValue > parseInt(currentYear)) {
                error.textContent = "! Không hợp lệ";
                error.style.visibility = "visible";
            } else {
                error.style.visibility = "hidden";
            }
        }
    } else {
        // Nếu giá trị không phải số nguyên hoặc có kí tự không hợp lệ
        error.textContent = "! Không hợp lệ";
        error.style.visibility = "visible";
    }
}
function addBooks() {
    const listInput = document.querySelectorAll(".input-add");
    const listError = document.querySelectorAll(".form-add .form-error");
    let hasErrorOne = false,
            hasErrorTwo = false;
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.textContent = "! Vui lòng nhập thông tin";
            error.style.visibility = "visible";
            hasErrorOne = true;
        }
    });
    listError.forEach((item) => {
        if (item.style.visibility === "visible") {
            hasErrorTwo = true;
        }
    });
    if (hasErrorOne || hasErrorTwo) {
        return;
    }
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/sachapi", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            maSach: document.getElementById("book-id").value,
            tenSach: document.getElementById("book-name").value,
            tacGia: document.getElementById("book-author").value,
            maNXB: document.getElementById("publisher-code").value,
            maNCC: document.getElementById("supplier-code").value,
            maKhuVuc: document.getElementById("zone-code").value,
            giaTien: document.getElementById("book-price").value,
            soLuong: document.getElementById("number-of-books").value,
            moTa: document.getElementById("book-describe").value,
            namXuatBan: document.getElementById("book-year").value,
            anh: fileNameBookImg,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    alert(`Thêm thành công!`);
                } else {
                    if (data.errors) {
                        if (data.errors.maSach) {
                            showError(".error-maSach", data.errors.maSach);
                        }
                        if (data.errors.maNXB) {
                            showError(".error-NXB", data.errors.maNXB);
                        }
                        if (data.errors.maKhuVuc) {
                            showError(".error-khuVuc", data.errors.maKhuVuc);
                        }
                    }
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
function showError(element, message) {
    const errorElement = document.querySelector(element);
    errorElement.textContent = message;
    errorElement.style.visibility = "visible";
}
// ===================Kết thúc sự kiện ở form thêm ===========================
// ================ Các sự kiện của form Chi tiết sách =================================
const btn_detail = document.querySelector(".form-detail .btn-detail");
const modal_form_detail = document.querySelector(".modal-form");
btn_detail.addEventListener("click", function () {
    modal_form_detail.style.display = "block";
    // Lấy dữ liệu các chi tiết hiển thị lên table
    viewModalBookDeitail();
    // Lấy dữ liệu từ table và hiển thị lên input
    clickTablieModal();
});
// Lấy dữ liệu các chi tiết hiển thị lên table
function viewModalBookDeitail() {
    const bookId_Detail = document.getElementById("book-iddetail").value;
    const action = "viewModalTableBook";

    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/sach", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            bookId: bookId_Detail,
            action: action,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (Array.isArray(data) && data.length > 0) {
                    displayBookDetails(data);
                } else {
                    const tbody = document.querySelector(".modal-form tbody");
                    tbody.innerHTML = "";
                    for (let i = 0; i < 5; i++) {
                        const row = document.createElement("tr");
                        row.innerHTML = `
                <td></td>
                <td></td>
                <td></td>
            `;
                        tbody.appendChild(row);
                    }
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
function displayBookDetails(data) {
    const tbody = document.querySelector(".modal-form tbody");
    tbody.innerHTML = "";
    data.forEach((item) => {
        const row = document.createElement("tr");
        row.dataset.maVach = item.maVach;
        const maSachCell = document.createElement("td");
        maSachCell.textContent = item.maSach;

        const maVachCell = document.createElement("td");
        maVachCell.textContent = item.maVach;

        const tinhTrangSachCell = document.createElement("td");
        tinhTrangSachCell.textContent = item.tinhTrangSach;

        row.appendChild(maSachCell);
        row.appendChild(maVachCell);
        row.appendChild(tinhTrangSachCell);
        tbody.appendChild(row);
    });
}
// Hàm Lấy dữ liệu từ table và hiển thị lên input
function clickTablieModal() {
    const table = document.querySelector(".modal-form .table-detail");
    const book_id = document.querySelector(".modal-form #book-id-modal");
    // console.log(book_id);
    const book_code = document.getElementById("code-detail-modal");
    const book_condition = document.getElementById("condition-book-modal");
    if (table) {
        table.addEventListener("click", function (e) {
            const listError = document.querySelectorAll(
                    ".modal-form .form-errorModal"
                    );
            listError.forEach((item) => {
                item.style.visibility = "hidden";
            });
            const row = e.target.closest("tr");
            if (!row || row.parentElement.tagName !== "TBODY")
                return;
            const cells = row.querySelectorAll("td");
            console.log(cells[0]);
            if (cells.length === 3) {
                book_id.value = cells[0].textContent; // Mã vạch
                book_code.value = cells[1].textContent; // Mã sách
                book_condition.value = cells[2].textContent; // Trạng thái
            }
        });
    }
}
// Sửa thông tin sách
const btn_edit = document.querySelector(".form-detail .btn-save");
btn_edit.addEventListener("click", function (event) {
    event.preventDefault();
    editBook();
});
const bookNameDetail = document.getElementById("book-namedetail");
const error_tenSachDetail = document.querySelector(".error-tenSachDetail");
bookNameDetail.addEventListener("input", function () {
    const value = bookNameDetail.value;
    checkErrorText(error_tenSachDetail, value);
});
const bookAuthor_Detail = document.getElementById("book-authordetail");
const error_tacGiaDetail = document.querySelector(".error-tacGiaDetail");
bookAuthor_Detail.addEventListener("input", function () {
    const value = bookAuthor_Detail.value;
    checkErrorText(error_tacGiaDetail, value);
});
const book_pricedetail = document.getElementById("book-pricedetail");
const error_giaDetail = document.querySelector(".error-giaDetail");
book_pricedetail.addEventListener("input", function () {
    const value = book_pricedetail.value;
    checkErrorNumber(error_giaDetail, value);
});
const book_yeardetail = document.getElementById("book-yeardetail");
const error_namXuatBanDetail = document.querySelector(
        ".error-namXuatBanDetail"
        );
book_yeardetail.addEventListener("input", function () {
    const value = book_yeardetail.value;
    checkErrorYear(error_namXuatBanDetail, value);
});
const book_describedetail = document.getElementById("book-describedetail");
const error_moTaDetail = document.querySelector(".error-moTaDetail");
book_describedetail.addEventListener("input", function () {
    const value = book_describedetail.value;
    checkErrorText(error_moTaDetail, value);
});
function editBook() {
    const listInput = document.querySelectorAll(".input-detail");
    const listError = document.querySelectorAll(".form-detail .form-error");
    let hasErrorOne = false,
            hasErrorTwo = false;
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.textContent = "! Vui lòng nhập thông tin";
            error.style.visibility = "visible";
            hasErrorOne = true;
        }
    });
    listError.forEach((item) => {
        if (item.style.visibility === "visible") {
            hasErrorTwo = true;
        }
    });
    if (hasErrorOne || hasErrorTwo) {
        return;
    }
    const userConfirm = window.confirm("Bạn có muốn thay đổi thông tin không?");
    if (!userConfirm) {
        return;
    }
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/sachapi/update", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            maSach: document.getElementById("book-iddetail").value,
            tenSach: document.getElementById("book-namedetail").value,
            tacGia: document.getElementById("book-authordetail").value,
            maNXB: document.getElementById("publisher-codedetail").value,
            maNCC: document.getElementById("supplier-codedetail").value,
            maKhuVuc: document.getElementById("zone-codedetail").value,
            giaTien: document.getElementById("book-pricedetail").value,
            soLuong: document.getElementById("number-of-booksdetail").value,
            moTa: document.getElementById("book-describedetail").value,
            namXuatBan: document.getElementById("book-yeardetail").value,
            anh: fileNameDetailImg,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    const maSach = document.getElementById("book-iddetail").value;
                    alert(`Sửa thông tin thành công! Sách có mã ${maSach}`);
                    clearFormAdd();
                } else {
                    alert(`Sửa thông tin thất bại!`);
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
// Xóa sách
const btn_delete = document.querySelector(".form-detail .btn-delete");
btn_delete.addEventListener("click", function (event) {
    event.preventDefault();
    deleteBook();
});
function deleteBook() {
    const userConfirm = window.confirm(
            "Bạn có chắc chắn muốn xóa sách này không?"
            );
    if (!userConfirm) {
        return;
    }
    // Gửi yêu cầu fetch với phương thức post
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/sachapi/delete", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            maSach: document.getElementById("book-iddetail").value,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    const maSach = document.getElementById("book-iddetail").value;
                    alert(`Xóa thành công! Sách có mã ${maSach}`);
                    pageItems.forEach((item) => item.classList.remove("is-show"));
                    document.querySelector(".body-mainbooks").classList.add("is-show");
                } else {
                    alert(`Xóa thất bại!`);
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
// Các sự kiện liên quan đến modal chi tiết

const bookId_Modal = document.getElementById("book-id-modal");
const error_maSach_Modal = document.querySelector(".error-maSach-Modal");
function checkError_bookId_Modal() {
    const value = bookId_Modal.value;
    if (value.trim() === "") {
        error_maSach_Modal.style.visibility = "visible";
        return;
    }
    error_maSach_Modal.style.visibility = "hidden";
}
const code_detail_modal = document.getElementById("code-detail-modal");
const error_maVach_Modal = document.querySelector(".error-maVach-Modal");
function checkError_code_detail_modal() {
    const value = code_detail_modal.value;
    const regex = /^[a-zA-Z0-9]*$/;
    if (value.trim() === "") {
        error_maVach_Modal.textContent = "!Vui lòng chọn thông tin";
        error_maVach_Modal.style.visibility = "visible";
        return;
    }
    if (!regex.test(value)) {
        error_maVach_Modal.textContent = "!Không được nhập các kí tự đặc biệt";
        error_maVach_Modal.style.visibility = "visible";
        return;
    }
    error_maVach_Modal.style.visibility = "hidden";
}
code_detail_modal.addEventListener("input", function () {
    checkError_code_detail_modal();
});
const condition_book_modal = document.getElementById("condition-book-modal");
const error_tinhTrang_Modal = document.querySelector(
        ".error-tinhTrang-Modal"
        );
// function checkError_condition_modal() {
//   const value = condition_book_modal.value;
//   const regex = /^[a-zA-Z0-9]*$/;
//   if (!regex.test(value)) {
//     error_tinhTrang_Modal.textContent = "!Không được nhập các kí tự đặc biệt";
//     error_tinhTrang_Modal.style.visibility = "visible";
//     return;
//   }
//   error_tinhTrang_Modal.style.visibility = "hidden";
// }
// condition_book_modal.addEventListener("input", function () {
//   checkError_condition_modal();
// });
// Sửa chi tiết sách
const btn_editDetail = document.querySelector(".modal-form .btn-save-book");
btn_editDetail.addEventListener("click", function (event) {
    event.preventDefault();
    editBookDetail();
});
function editBookDetail() {
    const listInput = document.querySelectorAll(".modal-form .form-input_not");
    const listError = document.querySelectorAll(".modal-form .form-errorModal");
    let hasErrorOne = false,
            hasErrorTwo = false;
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.style.visibility = "visible";
            hasErrorOne = true;
        }
    });
    listError.forEach((item) => {
        if (item.style.visibility === "visible") {
            hasErrorTwo = true;
        }
    });
    if (hasErrorOne || hasErrorTwo) {
        return;
    }
    const userConfirm = window.confirm("Bạn có muốn thay đổi thông tin không?");
    if (!userConfirm) {
        return;
    }
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/ctsachapi", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            maVach: document.getElementById("code-detail-modal").value,
            maSach: document.getElementById("book-id-modal").value,
            tinhTrangSach: document.getElementById("condition-book-modal").value,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    const maVach = document.getElementById("code-detail-modal").value;
                    const tinhTrang = document.getElementById(
                            "condition-book-modal"
                            ).value;
                    document.getElementById("code-detail-modal").value = "";
                    document.getElementById("condition-book-modal").value = "";
                    alert(`Sửa thành công chi tiết sách  có mã vạch : ${maVach}`);
                    const row = [...document.querySelectorAll(".modal-form tr")].find(
                            (tr) => tr.dataset.maVach === maVach
                    );
                    if (row) {
                        // Lấy các ô <td> và cập nhật nội dung
                        const cells = row.querySelectorAll("td");
                        if (cells.length >= 3) {
                            cells[2].textContent = tinhTrang; // Tình trạng sách
                        }
                    } else {
                        console.error("Không tìm thấy dòng có mã vạch:", maVach);
                    }
                } else {
                    if (data.errors) {
                        if (data.errors.maSach) {
                            showError(".error-maVach-Modal", data.errors.maVach);
                        }
                    }
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
// Xóa chi tiết sách
const btn_deleteDetail = document.querySelector(
        ".modal-form .btn-delete-book"
        );
btn_deleteDetail.addEventListener("click", function (event) {
    event.preventDefault();
    deleteBookDetail();
});
function deleteBookDetail() {
    const listInput = document.querySelectorAll(".modal-form .form-input_not");
    const listError = document.querySelectorAll(".modal-form .form-errorModal");
    const maVach = document.getElementById("code-detail-modal").value;
    const maSach = document.getElementById("book-id-modal").value;
    let hasErrorOne = false,
            hasErrorTwo = false;
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.style.visibility = "visible";
            hasErrorOne = true;
        }
    });
    listError.forEach((item) => {
        if (item.style.visibility === "visible") {
            hasErrorTwo = true;
        }
    });
    if (hasErrorOne || hasErrorTwo) {
        return;
    }
    const userConfirm = window.confirm(
            `Bạn có muốn xóa chi tiết sách có mã vạch : ${maVach} không?`
            );
    if (!userConfirm) {
        return;
    }
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/ctsachapi/delete", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            maVach: maVach,
            maSach: maSach,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    document.getElementById("code-detail-modal").value = "";
                    document.getElementById("condition-book-modal").value = "";
                    alert(`Xóa thành công chi tiết sách  có mã vạch : ${maVach}`);
                    const row = [...document.querySelectorAll(".modal-form tr")].find(
                            (tr) => tr.dataset.maVach === maVach
                    );
                    if (row) {
                        row.remove();
                    } else {
                        console.error("Không tìm thấy dòng có mã vạch:", maVach);
                    }
                } else {
                    if (data.errors) {
                        if (data.errors.maSach) {
                            showError(".error-maVach-Modal", data.errors.maVach);
                        }
                    }
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
// kết thúc
function clearFormDetail() {
    document.getElementById("book-iddetail").value = "";
    document.getElementById("book-namedetail").value = "";
    document.getElementById("book-authordetail").value = "";
    document.getElementById("publisher-codedetail").value = "";
    document.getElementById("supplier-codedetail").value = "";
    document.getElementById("zone-codedetail").value = "";
    document.getElementById("book-pricedetail").value = "";
    document.getElementById("number-of-booksdetail").value = "";
    document.getElementById("book-yeardetail").value = "";
    const linkAnh = "img/photo-film-solid (1).svg";
    document.querySelector(".form-detail .blog-loadimg .load-left img").src =
            linkAnh;
    document.getElementById("previewdetail").src = linkAnh;
    document.getElementById("book-describedetail").value = "";
    const listInput = document.querySelectorAll(".input-detail");
    listInput.forEach((item) => {
        if (item.value.trim() === "") {
            const error = item.nextElementSibling;
            error.style.visibility = "hidden";
        }
    });
}
// Kết thúc
document
        .querySelector(".modal-form .close-btn")
        .addEventListener("click", function (e) {
            const listError = document.querySelectorAll(
                    ".modal-form .form-errorModal"
                    );
            listError.forEach((item) => {
                item.style.visibility = "hidden";
            });
            document.getElementById("book-id-modal").value = "";
            document.getElementById("code-detail-modal").value = "";
            document.getElementById("condition-book-modal").value = "";
            modal_form_detail.style.display = "none";
        });
const btn_skip = document.querySelector(".form-detail .btn-skip");
btn_skip.addEventListener("click", function () {
    pageItems.forEach((item) => item.classList.remove("is-show"));
    document.querySelector(".body-mainbooks").classList.add("is-show");
    const listError = document.querySelectorAll(".form-detail .form-error");
    listError.forEach((item) => {
        item.style.visibility = "hidden";
    });
});
// Tìm kiếm và trả về kết quả
const input_search = document.querySelector(".input-search");
input_search.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        console.log("Enter");
        lookFor_Books();
    }
});
let textMoney = "",
        textArea = "",
        moneyMin = "",
        moneyMax = "";
// radio money
const radiosMoney = document.querySelectorAll('input[name="money"]');
// Lặp qua từng radio và thêm sự kiện 'change'
radiosMoney.forEach((radio) => {
    radio.addEventListener("change", function () {
        // Lấy thẻ label liên kết với radio được chọn
        const label = document.querySelector(`label[for="${this.id}"]`);
        if (label) {
            textMoney = label.innerText;
            if (textMoney.includes("-")) {
                if (textMoney) {
                    moneyMin = textMoney
                            .split("-")[0]
                            .replace(/\./g, "")
                            .replace(/\(VNĐ\)/g, "")
                            .trim();
                    moneyMax = textMoney
                            .split("-")[1]
                            .replace(/\./g, "")
                            .replace(/\(VNĐ\)/g, "")
                            .trim();
                }
            } else if (textMoney.includes("Trở lên")) {
                if (textMoney) {
                    moneyMin = textMoney
                            .replace(/\(VNĐ\)/g, "")
                            .replace(/\./g, "")
                            .replace(" Trở lên", "")
                            .trim();
                    moneyMax = "-2";
                }
            } else if (textMoney.includes("Dưới")) {
                if (textMoney) {
                    moneyMax = textMoney
                            .replace(/\(VNĐ\)/g, "")
                            .replace(/\./g, "")
                            .replace(/[^\d]/g, "")
                            .trim();
                    moneyMin = "0";
                }
            }
        }
        console.log(moneyMin);
        console.log(moneyMax);
    });
});
// radio money
const radiosArea = document.querySelectorAll('input[name="area"]');
// Lặp qua từng radio và thêm sự kiện 'change'
radiosArea.forEach((radio) => {
    radio.addEventListener("change", function () {
        // Lấy thẻ label liên kết với radio được chọn
        const label = document.querySelector(`label[for="${this.id}"]`);
        if (label) {
            textArea = label.innerText;
            if (textArea) {
                textArea = textArea.trim();
            }
        }
        console.log(textArea);
    });
});

function lookFor_Books() {
    const input = document.querySelector(".input-search");
    let searchValue = input.value.trim();
    if (
            searchValue === "" &&
            moneyMin === "" &&
            moneyMax === "" &&
            textArea === ""
            ) {
        alert("Vui lòng nhập từ khóa để tìm kiếm!");
        return;
    }
    if (moneyMin === "" && moneyMax === "") {
        moneyMin = "0"; // Hoặc một giá trị mặc định khác phù hợp
        moneyMax = "0"; // Tùy vào cách xử lý yêu cầu trên server
    }
    if (searchValue === "") {
        searchValue = "-1";
    }
    if (textArea === "") {
        textArea = "-1";
    }
    console.log(searchValue);
    console.log(moneyMin);
    console.log(moneyMax);
    console.log(textArea);
    // Gửi yêu cầu fetch với phương thức POST
    fetch("http://localhost:9999/cnpm/sachapi/lookfor", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Hoặc 'application/json' nếu gửi JSON
        },
        body: new URLSearchParams({
            tuKhoaTimKiem: searchValue,
            moneyMinApi: moneyMin,
            moneyMaxApi: moneyMax,
            tenKhuVuc: textArea,
        }), // Dữ liệu được gửi đi
    })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Lỗi khi gửi yêu cầu");
                }
                return response.json(); // Chuyển đổi phản hồi thành JSON
            })
            .then((data) => {
                if (data.isSuccess) {
                    console.log(data.data.length);
                    displayBooksLookFor(data.data);
                } else {
                    alert("Không tìm thấy");
                }
            })
            .catch((error) => {
                console.error("Lỗi:", error);
            });
}
function displayBooksLookFor(data) {
    const itemMax = 15;
    const tongTrang = Math.ceil(data.length / itemMax);
    pageLookFor(tongTrang);
    const blog_listbooks = document.querySelector(".blog-listbooks");
    blog_listbooks.innerHTML = "";
    // console.log(blog_listbooks);
    for (let pageIndex = 1; pageIndex <= tongTrang; pageIndex++) {
        // Lấy dữ liệu cho từng trang
        const start = (pageIndex - 1) * itemMax;
        const end = start + itemMax;
        const pageData = data.slice(start, end);

        // Tạo phần tử `ul` cho trang
        const ul = document.createElement("ul");
        ul.className = `list-books ${pageIndex === 1 ? "active" : ""}`; // Trang đầu tiên active
        ul.setAttribute("data-page", pageIndex);

        // Thêm từng sách vào trang
        pageData.forEach((item) => {
            const li = document.createElement("li");
            li.setAttribute("data-id", item.maSach);

            li.innerHTML = `
          <div class="blog-item">
            <a href="#">
              <img src="img/${item.anh}" alt="${item.tenSach}" class="image">
              <div class="info">
                <div class="title">
                  <h3 class="line-clamp">${item.tenSach}</h3>
                </div>
                <div class="blog-value">
                  <p class="value">${item.giaTien} đ</p>
                  <p class="quality">Tồn kho <strong>${item.soLuong}</strong></p>
                </div>
              </div>
            </a>
          </div>
        `;

            ul.appendChild(li);
        });
        // Thêm `ul` vào container
        blog_listbooks.appendChild(ul);
    }
    const bookItemsNew = document.querySelectorAll(".blog-listbooks li");
    bookItemsNew.forEach((item) =>
        item.addEventListener("click", function () {
            const bookId = parseInt(item.getAttribute("data-id"));
            viewBookDetails(bookId, "seeDetails");
        })
    );
}
function pageLookFor(tongTrang) {
    const tab_list_page = document.querySelector(".tab-list-page");
    tab_list_page.innerHTML = "";
    for (let i = 1; i <= tongTrang; i++) {
        const pageItem = document.createElement("div");
        pageItem.className = `tab-item-page ${i === 1 ? "active" : ""}`; // Trang đầu tiên active
        pageItem.setAttribute("data-page", i); // Gán data-page là số trang
        pageItem.textContent = i; // Hiển thị số trang
        // Thêm nút vào container
        tab_list_page.appendChild(pageItem);
    }
    const tabItemsPageNew = document.querySelectorAll(".tab-item-page");
    [...tabItemsPageNew].forEach((item) => {
        item.addEventListener("click", handleTabPageClick);
    });
}
});
