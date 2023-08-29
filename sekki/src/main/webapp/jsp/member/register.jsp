<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta charset="UTF-8">
<title>회원가입</title>
<link href="css/member.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/member.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script>

$(document).ready(function() {
    $("#profilePicture").on('change', function() {
        var fileName = $(this).val().split('\\').pop();
        $(".upload-name").val(fileName);
    });
});
</script> 
<body>
<c:import url="/header"></c:import>
<div id="visual">
		<h1>회원가입</h1>
	</div>
<div id="wrap">
<div id="memberMenu">
			<ul>
				<a href="login"><li>로그인</li></a>
				<a href="login"><li>로그인</li></a>
				<a href="agreeCondition" class="selected"><li>회원가입</li></a>
				<a href="findId"><li>아이디 찾기</li></a>
				<a href="findPw"><li>비밀번호 찾기</li></a>
			</ul>
		</div>
	<form action="registerProc" class="registerProc" method="post" id="f" enctype="multipart/form-data">
		<table class="registerTable">
			<tr>
				<td colspan="2" class="idInput">
					<input type="text" name="id" placeholder="아이디(20자 이내)" id="id" maxlength="20" oninput="regIdCheck()">
					<span class="material-symbols-outlined">person</span>
					<label id="regIdLabel">* 아이디를 입력하세요.</label>	
				</td>
			</tr>
			<tr>
				<td colspan="2" class="pwInput">
					<input type="password" name="pw" placeholder="비밀번호" id="pw" maxlength="16" oninput="regPwCheck()">
					<span class="material-symbols-outlined">lock</span>
					<label id="regPwLabel">* 영문, 숫자, 특수문자 세 가지를 모두 포함 (8~15자)</label>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="confirmInput">
					<input type="password" name="confirm" placeholder="비밀번호 확인 " id="confirm" oninput="regConfirmCheck()">
					<span class="material-symbols-outlined">lock_clock</span>
					<label id=regConfirmLabel>* 비밀번호를 확인하세요.</label>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="text" name="userName" id="userName" placeholder="이름"  oninput="regUserNameCheck()">
					<label id="regUserNameLabel">* 이름을 입력하세요.</label>	
				</td>
			</tr>
			<tr>
				<td><input type="button" onclick="execDaumPostcode()" value="주소 검색" class="searchDaumPostcode"></td>
				<td><input type="text" id="address" name="address" placeholder="주소" ></td>
			</tr>
			<tr>
				<td colspan="2"><input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소 입력"></td>
			</tr>
			<tr>
				<td colspan="2" class="mobileInput">
					<input type="text" name="mobile" placeholder="휴대폰번호 ex) 01000000000" id="mobile" oninput="regMobileCheck()">
					<span class="material-symbols-outlined">call</span>
					<label id="regMobileLabel">* (-) 없이 번호만 입력하세요.</label>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="emailInput">
					<input type="text" name="email" placeholder="비밀번호 분실시 확인용 이메일" id="email" oninput="regEmailCheck()">
					<select name="emailSelect" id="emailSelect" onchange="regEmailSelectCheck()" class="emailSelect">
						<option>@ 선택</option>
						<option>@naver.com</option>
						<option>@gmail.com</option>
						<option>@daum.net</option>
						<option>@hanmail.net</option>
					</select>
					<span class="material-symbols-outlined">mail</span>
					<label id="regEmailLabel">* 이메일 주소를 입력하세요.</label>
				</td>
			</tr>
			<tr>
			    <td class="upfileStyle" colspan="2">
			        <label class="fileLabel" for="profilePicture">프로필</label>
			        <input type="file" name="profilePicture" id="profilePicture">
			        <input class="upload-name" value="첨부파일" placeholder="첨부파일">
			    </td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="회원가입" onclick="allCheck()" class="registerBtn"></td>
			</tr>
			
		</table>
	</form>
</div>

<c:import url="/footer"/>
</body>

<!-- 
<form action="registerProc" method="post" id="f" enctype="multipart/form-data">

		<input type="text" name="id" placeholder="아이디" id="id" class="sub_input"> (*필수 항목) <br>
		<input type="password" name="pw" placeholder="비밀번호" id="pw" class="sub_input"><br>
		<input type="password" name="confirm" placeholder="비밀번호 확인 " id="confirm"
		onchange="pwCheck()" class="sub_input">
		<label id="label" ></label><br>
		<input type="text" name="userName" id="userName" placeholder="이름" class="sub_input"><br>
		<input type="text" id="postcode" name="postcode" placeholder="우편번호" class="sub_input">
		<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기" class="send_input"><br>
		<input type="text" id="address" name="address" placeholder="주소" class="sub_input"><br>
		<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소" class="sub_input"><br>
		<input type="text" name="mobile" placeholder="전화번호" class="sub_input"><br>
		<input type="file" name="profilePicture" id="profilePicture">
		<br>
		<input type="text" id="email" placeholder="이메일" class="sub_input">
		<input type="button" id="emailBtn" onclick="sendEmail()" value="이메일 주소 전송" class="send_input" style="width:120px"><br>
		<input type="text" id="auth"  placeholder="인증번호" class="sub_input">
		<input type="button" id="authBtn" onclick="sendAuth()" value="인증번호 전송" class="send_input"><br>
		<input type="button" value="회원가입" onclick="allCheck()" class="btn">
		<input type="button" value="취소" onclick="location.href='index'" class="btn"><br>
	</form>
 -->


