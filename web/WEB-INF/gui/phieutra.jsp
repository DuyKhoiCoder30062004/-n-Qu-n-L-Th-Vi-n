
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta rel="icon" type="image/x-icon" href="">
        <title>Qu?n l� phi?u tr?</title>
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

        #btnPhieuMuon {
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
            margin-left: 9%;
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
            margin-left: 9%;
            width: 15%;
            margin-right: 30px;

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
            height: 240px;
            width: 90%;
            border-collapse: collapse;
            table-layout: fixed;
            border: 1px solid black;
            display: block;
            overflow-y: auto;
            overflow-x: hidden;
        }
        .divTT {
            width: 50%;
            height: 50%; /* Thay v� 50% */
            background-color: azure;
            border: 2px solid black;
            position: absolute;
            top: 20%;
            left: 20%;
            display: none;
            z-index: 1000;
            overflow: hidden; /* ??m b?o n?i dung kh�ng tr�n */
        }
        #tableSach, #tableKhach {
            border-collapse: collapse;
            width: 95%;
            height: 67%; 
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
                H? th?ng qu?n l� th? vi?n c?a tr??ng ??i h?c ABC
            </div>
            <div id="menu">
                <div class="conponentMenu" id="iconAndName" >
                    <img src="img/account.svg" alt="icon">
                    ${nv.getHo()}  ${nv.getTen()}<br>
                    ${nv.getChucVu()} 
                </div>
                <button id="btnSach" class="conponentMenu" value="s�ch" onclick="reDirect(this,'/cnpm/sach')">
                    <img src="img/sach.jpg" alt="icon"> S�ch
                </button>
                <button id="btnNhanVien" class="conponentMenu" value="nh�n vi�n" onclick="reDirect(this,'/cnpm/nhanvien')">
                    <img src="img/stafff.svg" alt="icon"> Nh�n vi�n
                </button>
                <button id="btnDocGia" class="conponentMenu" value="??c gi?" onclick="reDirect(this,'/cnpm/docgia')">
                    <img src="img/customerr.svg" alt="icon"> ??c gi?
                </button>
                <button id="btnNhaXuatBan" class="conponentMenu" value="nh� xu?t b?n" onclick="reDirect(this,'/cnpm/nhaxuatban')">
                    <img src="img/nhaxuatban.jpg" alt="icon"> Nh� xu?t b?n
                </button>
                <button id="btnNhaCungCap" class="conponentMenu" value="nh� cung c?p" onclick="reDirect(this,'/cnpm/nhacungcap')">
                    <img src="img/nhacc.jpg" alt="icon"> Nh� cung c?p
                </button>
                <button id="btnKhuVuc" class="conponentMenu" value="khu v?c" onclick="reDirect(this,'/cnpm/khuvuc')">
                    <img src="img/khuvuc.jpg" alt="icon"> Khu v?c
                </button>
                <button id="btnPhieuMuon" class="conponentMenu" value="phi?u tr?" onclick="reDirect(this,'/cnpm/phieutra')">
                    <img src="img/export.svg" alt="icon"> Phi?u tr?
                </button>
                <button id="btnPhieuTra" class="conponentMenu" value="phi?u tr?" onclick="reDirect(this,'/cnpm/phieutra')">
                    <img src="img/phieutra.jpg" alt="icon"> Phi?u tr?
                </button>
                <button id="btnPhieuPhat" class="conponentMenu" value="phi?u ph?t" onclick="reDirect(this,'/cnpm/phieuphat')">
                    <img src="img/phieuphat.jpg" alt="icon"> Phi?u ph?t
                </button>
                <button id="btnPhieuNhap" class="conponentMenu" value="phi?u nh?p" onclick="reDirect(this,'/cnpm/phieunhap')">
                    <img src="img/phieunhap.jpg" alt="icon"> Phi?u nh?p
                </button>
                <button id="btnPhanQuyen" class="conponentMenu" value="ph�n quy?n" onclick="reDirect(this,'/cnpm/phanquyen')">
                    <img src="img/permission.svg" alt="icon"> Ph�n quy?n
                </button>
                <button id="btnThongKe" class="conponentMenu" value="th?ng k�" onclick="reDirect(this,'/cnpm/thongke')">
                    <img src="img/tinhhieuqua_128px.svg" alt="icon"> Th?ng k�
                </button>
                <button id="btnDangXuat" class="conponentMenu">
                    <img src="img/logout.jpg" alt="icon"> ??ng xu?t
                </button>

            </div>
            <div id="detail">
                <p id="titleDetail">
                    <img id="imgDetail" src="img/export.svg" alt="icon"> Qu?n l� phi?u tr?
                </p>
                <section id="detailPT">
                    <div class="input-group">
                        <label class="nameFeature">M� phi?u </label>
                        <input type="text" id="txtMaPhieu" placeholder="Nh?p m� phi?u">
                    </div>
                    <div class="input-group">
                        <label class="nameFeature">M� NV </label>
                        <input type="text" id="txtMaNV" value="${nv.getMaNV()}" placeholder="Nh?p m� NV" readonly>
                        <img class="iconChucNang" id="iconThem" src="img/add.svg" title="Th�m PT" onclick="sendData('add')">
                        <img class="iconChucNang" id="iconXoa" src="img/delete.svg" title="X�a PT" >
                    </div><br>
                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 11px;">Ng�y Tr? </label>
                        <input type="date" id="txtNgayTra" style="width: 104px;" placeholder="Ch?n ng�y nh?p">
                    </div>

                    <div class="input-group">
                        <label class="nameFeature" style="margin-right: 4px">T?ng SL </label>
                        <input type="text" id="txtTongSL" value="0"  readonly>
                        <img class="iconChucNang" id="iconSua" src="img/edit.svg" title="S?a PT" onclick="sendData('edit')">
                        <img class="iconChucNang" id="iconClear" title="Clear input"  onclick="clearInputPT()" src="img/clear.png" alt="icon">
                    </div><br>
                    <div class="input-group" style="margin-top: 10px;">
                        <select id="comBoBoxSearch" name="options">
                            <option value="M� phi?u">M� phi?uu</option>
                            <option value="M� NV">M� NV</option>
                            <option value="Ng�y l?p">Ng�y tr?</option>
                        </select>
                        <input type="text" id="txtSearchPT" placeholder="Nh?p th�ng tin">
                        <img class="iconChucNang" id="iconSearch" title="T�m ki?m PT" style="margin-left: 0px;" src="img/search1.png" alt="icon"
                             onclick="sendData('search')">
                        <img class="iconChucNang" id="iconImportExcel" style="margin-left: 44.9%;" src="img/import_excel.svg"
                            alt="icon" onclick="selectFile('import')"title="importExcel">
                        <img class="iconChucNang" id="iconExportExcel" src="img/export_excel.svg" alt="icon"
                             onclick="selectFile('export')" title="exportESxcel">
                            <input  type="file" id="fileExcel" style="display:none;">
                        <img class="iconChucNang" id="iconPrint" title="PrintPT"  src="img/print.jpg" alt="icon"
                             onclick="sendData('print')">
                        <img class="iconChucNang" id="iconFinish" title="Load l?i table" src="img/refresh.svg" alt="icon"
                             onclick="sendData('finish')">
                    </div>
                    <table class="table" id="tablePT">
                        <thead>
                            <tr>
                                <th>M� phi?u</th>
                                <th>M� NV</th>
                                <th>Ng�y tr?</th>
                                <th>T?ng s? l??ng</th>
                            </tr>
                        </thead>
                        <tbody id="tbodyPT">
                            <c:forEach var="pm" items="${requestScope.listPT}">
                                <tr>
                                    <td>${pm.maPT}</td>
                                    <td>${pm.maKhach}</td>
                                    <td>${pm.maNV}</td>
                                    <td>${pm.ngayTra}</td>
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

                <section id="detailCTPT">
                    <p id="titleCTPT">Chi ti?t phi?u tr?</p>
                    <div style="display: flex; justify-content: space-between; ">
                        <div id="sectionImportImformationCTPT">
                            <div class="input-groupCTPT" style="margin-top: 40px;">
                                <label class="nameFeatureCTPT" style="margin-right: 10px;">M� phi?u</label>
                                <input type="text" id="txtMaPhieuCTPT" placeholder="Nh?p m� phi?u">
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 18px;">M� s�ch</label>
                                <input type="text" id="txtSachCTPT" placeholder="Nh?p m� s�ch" style="margin-right: 2px" readonly>
                                <img src="img/add.svg" onclick="hienThiSach()"
                                     style="cursor: pointer;width: 12px;height:auto;" />
                            </div>
                            <div class="input-groupCTPT">
                                <label class="nameFeatureCTPT" style="margin-right: 15px;">S? l??ng</label>
                                <input type="text" id="txtSLCTPT" placeholder="Nh?p s? l??ng">
                            </div>
                            <div>
                                <img class="iconChucNangCTPT" id="iconThemCTPT" style="margin-left: 24%;"
                                     src="img/add.svg" title="Th�m CTPT" onclick="sendDataCTPT('addCTPT')">
                                <img class="iconChucNangCTPT" id="iconXoaCTPT" src="img/delete.svg" title="X�a CTPT" >
                                <img class="iconChucNangCTPT" id="iconSuaCTPT" src="img/edit.svg" title="S?a CTPT" onclick="sendDataCTPT('updateCTPT')">
                                <img class="iconChucNangCTPT" id="iconClearCTPT" onclick="clearInputCTPT()"
                                     src="img/clear.png" title="clear input CTPT">
                            </div>
                        </div>
                        <div id="sectionTableCTPT">
                            <div class="input-groupCTPT">
                                <select id="comBoBoxSearchCTPT" name="options">
                                    <option value="M� phi?u">M� phi?u</option>
                                    <option value="M� s�ch">M� s�ch</option>
                                    <option value="S? l??ng">S? l??ng</option>
                                </select>
                                <input type="text" id="txtSearchCTPT" style="margin-left: 5px;margin-right: 5px;"
                                       placeholder="Nh?p th�ng tin">
                                <img class="iconChucNang" id="iconSearchCTPT" style="margin-right: 58%;margin-left: 0px;"
                                     src="img/search1.png" title="T�m ki?m CTPT" onclick="sendDataCTPT('searchCTPT')">
                                <img class="iconChucNang" id="iconFinishCTPT" src="img/refresh.svg" title="T?i l?i table" onclick="sendDataCTPT('finishCTPT')">
                            </div>
                            <table class="table" id="tableCTPT">
                                <thead>
                                    <tr>
                                        <th>M� phi?u</th>
                                        <th>M� s�ch</th>
                                        <th>S? l??ng</th>
                                    </tr>
                                </thead>
                                <tbody id="tbodyCT">
                                    <c:forEach var="ctpm" items="${requestScope.listCTPT}">
                                        <tr>
                                            <td>${ctpm.maPT}</td>
                                            <td>${ctpm.maSach}</td>
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
                                    </tr>
                                    <%
                                        }
                                    %>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </section>

                <div class="divTT" id="divtableKhach" onclick="showListAllKhach()">
                    <img src="img/cancel.svg"  onclick="dongTBTT()"  style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearchKhach" name="options">
                            <option value="M� kh�ch">M� kh�ch</option>
                            <option value="T�n">T�n </option>
                        </select>
                        <input type="text" id="txtSearchKhach" placeholder="Nh?p th�ng tin">
                        <img class="iconChucNang" id="iconSearchKhach" src="img/search1.png" alt="icon">
                    </div>
                    <table class="table" id="tableKhach">
                        <thead>
                            <tr>
                                <th>H?</th>
                                <th>T�n</th>
                                <th>??a ch?</th>
                                <th>S? ?i?n tho?i</th>
                                <th>Ng�y sinh</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dg" items="${requestScope.listDG}">
                                <tr>
                                    <td>${dg.maDG}</td>
                                    <td>${dg.hoDG}</td>
                                    <td>${dg.tenDG}</td>
                                    <td>${dg.diaChi}</td>
                                    <td>${dg.soDienThoai}</td>
                                    <td>${dg.ngaySinh}</td>
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

                <div class="divTT" id="divtableSach" onclick="showListAllSach()">
                    <img src="img/cancel.svg"  onclick="dongTBTT()"  style="cursor: pointer;width: 20px;height:auto;margin-left: 97%;" />
                    <h3 style="text-align: center;"> Ban ch?n m� S�ch ? ?�y!</h3>
                    <div style="margin-left: 20px;">
                        <select id="comBoBoxSearchSach" name="options">
                            <option value="M� S�ch">M� S�ch</option>
                            <option value="T�n s�ch">T�n s�ch </option>
                        </select>
                        <input type="text" id="txtSearchSach" placeholder="Nh?p th�ng tin">
                        <img class="iconChucNang" id="iconSearchSach"  src="img/search1.png" alt="icon">
                    </div>
                    <table class="table" id="tableSach">
                        <thead>
                            <tr>
                                <th>M� s�ch</th>
                                <th>T�n s�ch</th>
                                <th>t�c gi?</th>
                                <th>S? l??ng</th>
                                <th>M� t?</th>
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
            </div>
        </form>
        <script>
            //bi?n gi? ???ng link file excel
            let namePath = "";
            /*click table*/
            function clickPT(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieu').value = cells[0].innerText;
                document.getElementById('txtMaPhieu').readOnly = true;
                document.getElementById('txtMaKhach').value = cells[1].innerText;
                const dateValueNN = cells[3].innerText;
                document.getElementById('txtNgayTra').value = dateValueNN;

                // L?y gi� tr? ng�y t? cells[4] v� ??nh d?ng l?i
                const dateValueHC = cells[4].innerText;
                document.getElementById('txtHanChot').value = dateValueHC;

                document.getElementById('txtTongSL').value = cells[5].innerText;
            }
            function clickCTPT(row) {
                const cells = row.getElementsByTagName('td');
                document.getElementById('txtMaPhieuCTPT').value = cells[0].innerText;
                document.getElementById('txtMaPhieuCTPT').readOnly = true;
                document.getElementById('txtSachCTPT').value = cells[1].innerText;
                document.getElementById('txtSachCTPT').readOnly = true;
                document.getElementById('txtSLCTPT').value = cells[2].innerText;
                document.getElementById('txtTrangThaiCTPT').value = cells[3].innerText;
            }
            const rowsPT = document.querySelectorAll('#tablePT tbody tr');
            rowsPT.forEach(row => {
                row.addEventListener('click', () => clickPT(row));
            });
            const rowsCTPT = document.querySelectorAll('#tableCTPT tbody tr');
            rowsCTPT.forEach(row => {
                row.addEventListener('click', () => clickCTPT(row));
            });
            //set up ng�y
            function formatDate(date) {
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // Th�ng b?t ??u t? 0
                const day = String(date.getDate()).padStart(2, '0');
                return `${year}-${month}-${day}`;
                    }

                    // L?y ng�y hi?n t?i v� g�n v�o input ng�y l?p
                    document.getElementById('txtNgayTra').value = formatDate(new Date());
                    // T�nh to�n ng�y tr? (7 ng�y sau)
                    const currentDate = new Date();
                    currentDate.setDate(currentDate.getDate() + 7); // Th�m 7 ng�y v�o ng�y hi?n t?i

                    // G�n gi� tr? cho input "Ng�y tr?"
                    document.getElementById('txtHanChot').value = formatDate(currentDate);

                    /*clear d? li?u*/
                    function clearInputPT() {
                        document.getElementById('txtMaPhieu').value = "";
                        document.getElementById('txtMaKhach').value = "";
                        document.getElementById('txtMaNV').value = "";
                        document.getElementById('txtNgayTra').value = formatDate(new Date());
                        document.getElementById('txtHanChot').value = formatDate(currentDate);
                        document.getElementById('txtTongSL').value = "0";
                    }
                    function clearInputCTPT() {
                        document.getElementById('txtMaPhieuCTPT').value = "";
                        document.getElementById('txtSachCTPT').value = "";
                        document.getElementById('txtSLCTPT').value = "";
                        document.getElementById('txtTrangThaiCTPT').value = "?ang tr?";
                    }
                    function selectFile(action) {
                        // L?ng nghe s? ki?n thay ??i c?a input file
                        alert("Vui l�ng ch?n ho?c t?o file excel ?? "+action+" ? trong th? m?c C:/Users/ADMIN/OneDrive/Documents/NetBeansProjects/cnpm/");
                        const fileInput = document.getElementById("fileExcel");
                        fileInput.onchange = function() {
                            if (fileInput.files.length > 0) {
                                // G�n ???ng d?n file v�o bi?n to�n c?c
                                namePath = fileInput.files[0].name;
                                sendData(action);
                            }
                        };

                        // Hi?n th? c?a s? ch?n file
                        fileInput.click();
                    }
                    // L?ng nghe s? ki?n khi b�n ph�m ?? t�m ki?m phi?u tr?
                    document.getElementById('txtSearchPT').addEventListener('keyup', function(event) {
                            sendData('search');
                    });
                    function sendData(action) {
                        const formData = new URLSearchParams({
                            action: action,
                            maPhieu: document.getElementById('txtMaPhieu').value,
                            maKhach: document.getElementById('txtMaKhach').value,
                            maNV: document.getElementById('txtMaNV').value,
                            ngayTra: document.getElementById('txtNgayTra').value,
                            hanChot: document.getElementById('txtHanChot').value,
                            tongSL: document.getElementById('txtTongSL').value,
                            optionSearch: document.getElementById('comBoBoxSearch').value,
                            valueSearch: document.getElementById('txtSearchPT').value,
                            nameFileExcel:namePath,
                        });

                        fetch('http://localhost:8080/cnpm/phieutra', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            body: formData.toString()
                        })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('?� x?y ra l?i trong qu� tr�nh g?i d? li?u');
                                    }
                                    return response.text(); // Chuy?n ??i ph?n h?i th�nh v?n b?n
                                })
                                .then(content => {
                                    try {
                                        const data = JSON.parse(content); // C? g?ng ph�n t�ch c� ph�p JSON
                                        if (data.thongbao) {
                                            alert(data.thongbao); // Hi?n th? th�ng b�o t? server
                                        }
                                        if (data.resultsPT && data.resultsPT.length > 0) {
                                            kQTimKiemPT(data.resultsPT); // X? l� k?t qu? t�m ki?m
                                            kQTimKiemCTPT(data.resultsCTPT);
                                        }
                                        if (data.hopLe) {
                                            window.location.reload(); // T?i l?i trang n?u h?p l?
                                        }
                                    } catch (e) {
                                        if (content.startsWith('<')) {
                                            if (action === 'print') {
                                                // Hi?n th? n?i dung HTML v� in ra
                                                const printWindow = window.open('', '_blank');
                                                printWindow.document.write('<html><head><title>In Phi?u M??n</title></head><body>');
                                                printWindow.document.write(content); // Th�m n?i dung HTML
                                                printWindow.document.write('</body></html>');
                                                printWindow.document.close();
                                                printWindow.print(); // In n?i dung
                                            }
                                        } else {
                                            alert('?� x?y ra l?i trong qu� tr�nh x? l� d? li?u.');
                                        }
                                    }
                                })
                                .catch(error => alert('?� x?y ra l?i: ' + error));
                    }
                   
                    function  kQTimKiemPT(results)
                    {
                        const tableBody = document.getElementById('tbodyPT');
                        tableBody.innerHTML = '';
                        if (!Array.isArray(results)) {
                            results = Object.values(results); // Chuy?n ??i ??i t??ng th�nh m?ng c�c gi� tr?
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
                            cell4.textContent = item.ngayTra || '';
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
                    // L?ng nghe s? ki?n khi b�n ph�m ?? t�m ki?m chi ti?t phi?u tr?
                    document.getElementById('txtSearchCTPT').addEventListener('keyup', function(event) {
                            sendDataCTPT('searchCTPT');
                    });
                    function sendDataCTPT(action)
                    {
                        const formData = new URLSearchParams({
                            action: action,
                            maPT: document.getElementById('txtMaPhieuCTPT').value,
                            maSach: document.getElementById('txtSachCTPT').value,
                            maNV: document.getElementById('txtMaNV').value,
                            soLuong: document.getElementById('txtSLCTPT').value,
                            optionSearch: document.getElementById('comBoBoxSearchCTPT').value,
                            valueSearch: document.getElementById('txtSearchCTPT').value,
                        });

                        fetch('http://localhost:9999/cnpm/ctpm', {
                            method: 'POST',
                            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                            body: formData.toString()
                        })
                                .then(response => {
                                    if (!response.ok) {
                                        throw new Error('?� x?y ra l?i trong qu� tr�nh g?i d? li?u');
                                    }
                                    return response.json(); // Chuy?n ??i ph?n h?i th�nh JSON
                                })
                                .then(data => {
                                    if (data.thongbao)
                                        alert(data.thongbao); // Hi?n th? th�ng b�o t? servlet
                                    if (data.results && data.results.length > 0) {
                                        kQTimKiemCTPT(data.results);
                                    }

                                    // N?u d? li?u h?p l?, t?i l?i trang
                                    if (data.hopLe) {
                                        window.location.reload();
                                    }
                                })
                                .catch(error => alert('?� x?y ra l?i: ' + error));
                    }
                    function  kQTimKiemCTPT(results)
                    {
                        const tableBody = document.getElementById('tbodyCT');
                        tableBody.innerHTML = '';
                        if (!Array.isArray(results)) {
                            results = Object.values(results); // Chuy?n ??i ??i t??ng th�nh m?ng c�c gi� tr?
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
                            cell3.textContent = item.soLuong || '';
                            row.appendChild(cell3);
                            const cell4 = document.createElement('td');
                            cell4.textContent = item.trangThai || '';
                            row.appendChild(cell4);
                            tableBody.appendChild(row);
                        });
                    }
                    //hi?n th? x�c nh?n x�a phi?u tr?
                    document.getElementById('iconXoa').addEventListener('click', function(event) {
                        // Hi?n th? h?p tho?i x�c nh?n
                        const confirmDelete = confirm("B?n c� ch?c mu?n x�a kh�ng phi?u tr? n�y kh�ng?");
                        if (!confirmDelete) {
                            event.preventDefault();
                        } else {
                            sendData('delete');
                        }
                    });
                    //hi?n th? x�c nh?n x�a chi ti?t phi?u tr?
                    document.getElementById('iconXoaCTPT').addEventListener('click', function(event) {
                        // Hi?n th? h?p tho?i x�c nh?n
                        const confirmDelete = confirm("B?n c� ch?c mu?n x�a kh�ng chi ti?t phi?u tr? n�y kh�ng?");
                        if (!confirmDelete) {
                            event.preventDefault();
                        } else {
                            sendDataCTPT('deleteCTPT');
                        }
                    });
                    //?�ng c�c table
                    function dongTBTT() {
                        const divs = document.querySelectorAll('.divTT'); // Ch?n t?t c? div c� class 'popupDiv'
                        divs.forEach(div => div.style.display = 'none'); // ?n t?ng div
                    }
                    //Hi?n th? table s�ch
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
                        document.getElementById('txtSachCTPT').value = cells[0].innerText;
                        //document.getElementById('txtMaNV').disabled=true;
                        const tableSach = document.getElementById('divtableSach');
                        tableSach.style.display = "none";
                        document.getElementById('txtSLCTPT').focus();
                    }

                    const rowsSach = document.querySelectorAll('#tableSach tbody tr');
                    rowsSach.forEach(row => {
                        row.addEventListener('click', () => clickSach(row));
                    });
                    //Hi?n th? table kh�ch
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
                        document.getElementById('txtNgayTra').focus();
                    }

                    const rowsKhach = document.querySelectorAll('#tableKhach tbody tr');
                    rowsKhach.forEach(row => {
                        row.addEventListener('click', () => clickKhach(row));
                    });
                    //T�m ki?m kh�ch trong table kh�ch
                    function searchKhach()
                    {
                        var value=document.getElementById('txtSearchKhach').value.toLowerCase();
                        var option=document.getElementById('comBoBoxSearchKhach').value;
                         var rows = document.querySelectorAll('#tableKhach tbody tr');
                        if (!value) {
                            alert("Vui l�ng nh?p th�ng tin ?? t�m ki?m kh�ch!");
                            return; 
                        }
                        var columnIndex=0;
                        if(option ==="T�n")
                            columnIndex=2;
                        rows.forEach(row =>{
                            var cell=row.getElementsByTagName('td')[columnIndex];
                            if(cell && cell.textContent.toLowerCase().includes(value)){
                                row.style.display="";
                            }
                            else row.style.display="none";
                        });
                    }
                    document.getElementById('iconSearchKhach').addEventListener('click', function(event) {
                        event.stopPropagation(); // Ng?n ch?n s? ki?n click lan ra divTableNV
                        searchKhach(); 
                    });
                    //hi?n th? h?t d? li?u c?a table kh�ch
                    function showListAllKhach() {
                        var rows = document.querySelectorAll('#tableKhach tbody tr');
                        rows.forEach(row => {
                            row.style.display = "";
                        });
                        document.getElementById('txtSearchKhach').value="";
                    }
                    //T�m ki?m v?i table s�ch
                    function searchSach()
                    {
                        var value=document.getElementById('txtSearchSach').value.toLowerCase();
                        var option=document.getElementById('comBoBoxSearchSach').value;
                         var rows = document.querySelectorAll('#tableSach tbody tr');
                        if (!value) {
                            alert("Vui l�ng nh?p th�ng tin ?? t�m ki?m S�ch!");
                            return; 
                        }
                        var columnIndex=0;
                        if(option ==="T�n s�ch")
                            columnIndex=1;
                        rows.forEach(row =>{
                            var cell=row.getElementsByTagName('td')[columnIndex];
                            if(cell && cell.textContent.toLowerCase().includes(value)){
                                row.style.display="";
                            }
                            else row.style.display="none";
                        });
                    }
                    document.getElementById('iconSearchSach').addEventListener('click', function(event) {
                        event.stopPropagation(); // Ng?n ch?n s? ki?n click lan ra divTableNV
                        searchSach(); 
                    });
                    //hi?n th? h?t d? li?u c?a table s�ch
                    function showListAllSach() {
                        var rows = document.querySelectorAll('#tableSach tbody tr');
                        rows.forEach(row => {
                            row.style.display = "";
                        });
                        document.getElementById('txtSearchSach').value="";
                    }
                    
                    //s? ki?n enter phi?u tr?
                    document.getElementById('txtMaPhieu').addEventListener('keydown', function(event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            hienThiKhach();
                        }
                    });

                    document.getElementById('txtNgayTra').addEventListener('keydown', function(event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                            document.getElementById('txtHanChot').focus();
                        }
                    });
                    
                    //s? ki?n enter cho ctphieu tr?
                    document.getElementById('txtMaPhieuCTPT').addEventListener('keydown', function(event) {
                        if (event.key === "Enter") {
                            event.preventDefault();
                             hienThiSach();
                        }
                    });
                    document.getElementById('btnDangXuat').addEventListener('click', function(event) {
                        event.preventDefault();  // Ng?ng h�nh ??ng m?c ??nh c?a n�t
                        window.location.href = '/cnpt/login';  // Chuy?n h??ng ??n trang ??ng nh?p
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
                            alert("B?n kh�ng c� quy?n qu?n l� t�c v? "+ button.value);
                        }
                    }
                    // Ch?n vi?c s? d?ng t? h?p ph�m t?t ?? ph�ng to/thu nh?
                    document.addEventListener('keydown', function (event) {
                        if (event.ctrlKey && (event.key === '+' || event.key === '-' || event.key === '=')) {
                            event.preventDefault();
                        }
                    });
                    // Ch?n thao t�c ph�ng to b?ng con l?n chu?t
                    document.addEventListener('wheel', function (event) {
                        if (event.ctrlKey) {
                            event.preventDefault();
                        }
                    }, {passive: false}
                    );
        </script>
    </body>
</html>