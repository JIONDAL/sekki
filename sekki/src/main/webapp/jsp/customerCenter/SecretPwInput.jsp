<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 입력</title>
<style>
	div {
		background-color: #ffd60a; 
		text-align: center; 
		width: 100%;
		height: 100%;
		padding: 30px 0 40px;
	}

	input[type="password"] {
		background-color: #fff; 
		text-align: center; 
		width: 250px;
		height: 30px; 
	}

	button {
		padding: 7px 20px; 
		background-color: rgb(251, 189, 74); 
		border: none;
		border-radius: 5px; 
		cursor: pointer;
		width: 80px; 
		font-size: 15px; 
		font-weight: 600;
		height: 38px;
	}
</style>
</head>
<body>
	   <div>
		    <h2>비밀번호를 입력하세요</h2>
		    <input type="password" id="passwordInput" >
		    <button id="pwCheckButton" onclick="secretPwCheck(${param.num})">열람</button>
	   </div>
	   
	  <script>
		function secretPwCheck(num) {
			var xhr = new XMLHttpRequest();
			xhr.open('post', 'secretPwCheck?num=' + num);
			var passwordInput = document.getElementById('passwordInput').value;
			xhr.send(passwordInput);
			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4 && xhr.status === 200) {
					var secretPwCheck = parseInt(xhr.responseText);
					if (secretPwCheck === 1) {
						window.close();
						var width = window.screen.width;
						var height = window.screen.height;
						var newWindow = window.open('inquiryContent?num=' + num);
						newWindow.resizeTo(width, height);
						newWindow.moveTo(0, 0);
					} else {
						alert('비밀번호 오류')
						window.close();
					}
				}
			};
		}
	</script>
</body>
</html>