<%-- 
    Document   : login
    Created on : Nov 11, 2024, 1:04:16 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Đăng Nhập Hệ Thống</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #86c2ed;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;

            }

            .login-form {
                width: 400px;
                margin: 0 auto;
                padding: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
                background-color: #F5F5F5;
                text-align: center;
            }
            .login-form img{
                width: 150px;
                display: block;
                margin: 0 auto;
            }
            .login-form h2 {
                text-align: center;
            }

            .input-container {
                position: relative;
                width: 100%;
                max-width: 400px;
                margin: 0 auto;
                margin-bottom: 20px;
            }

            .input-container input {
                width: 100%;
                padding: 10px 10px 10px 40px; /* Chừa khoảng trống bên trái cho icon */
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            .input-container .icon {
                position: absolute;
                top: 50%;
                left: 10px; /* Canh icon bên trái */
                transform: translateY(-50%);
                font-size: 18px;
                color: #888;
            }

            .login-form button {
                background-color: #007bff;
                width: 100%;
                color: #fff;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
                margin-bottom: 20px;
                margin-top: 10px;
            }

            .login-form button:hover {
                background-color: #0056b3;

            }
            .login-form a:hover{
                color:red;
            }
        </style>
    </head>

    <body>
        <div class="login-form">
            <img src="img/account.svg">
            <h2>Đăng Nhập</h2>
            <div class="input-container">
                <span class="icon">👤|</span> 
                <input id="username" type="text" placeholder="Nhập mã nhân viên" autofocus>
            </div>
            <div class="input-container">
                <span class="icon">🔒|</span>
                <input id="pass" type="password" placeholder="Password">
            </div>
            <button type="submit" onclick="sendData()">ĐĂNG NHẬP</button>
            <a href="">Tôi là độc giả</a>
        </div>
    </body>
    <script>
        function sendData() {
            event.preventDefault();
            const formData = new URLSearchParams({
                username: document.getElementById('username').value,
                pass: document.getElementById('pass').value,
            });

            fetch('http://localhost:9999/cnpm/login', {
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
                        alert(data.thongbao);
                        if (data.hopLe) {
                            window.location.href = data.redirectUrl; // Chuyển hướng tới URL từ phản hồi JSON
                        }
                    })
                    .catch(error => alert('Đã xảy ra lỗi: ' + error));
        }

        // Lắng nghe sự kiện "Enter" cho cả hai trường input
        document.getElementById('username').addEventListener('keydown', function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
                document.getElementById('pass').focus();
            }
        });

        document.getElementById('pass').addEventListener('keydown', function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
                sendData();
            }
        });

    </script>
</html>
