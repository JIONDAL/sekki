<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/center.js"></script>
</head>
<body>
	<div class="form-group phoneCertifyDiv">
		<label class="inputTitle">휴대폰 번호</label><br>
		<div class="phoneNum-formgroup">
			<input type="text" name="memberPhone" class="phoneNum" >
			<input type="button" id="memberPhoneCheck" class="btn memberPhoneBtn active" value="인증번호 전송">
		</div>
		<div class="phoneNum-formgroup" id="phoneCertifyDiv">
			<input type="text" name="memberPhoneCertify" class="phoneNum">
			<input type="button" id="certifyCheck" class="btn memberPhoneBtn" value="인증하기">
		</div>
	</div>
</body>
</html>