<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="/js/member.js"></script>
<link href="css/member.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" /> 
</head>
<body>
<c:import url="/header"></c:import>
<div id="visual">
		<h1>회원정보 찾기</h1>
	</div>
<div id="wrap">
<div id="memberMenu">
			<ul>
				<a href="login"><li>로그인</li></a>
				<a href="login"><li>로그인</li></a>
				<a href="agreeCondition" ><li>회원가입</li></a>
				<a href="findId" class="selected"><li>아이디 찾기</li></a>
				<a href="findPw"><li>비밀번호 찾기</li></a>
			</ul>
		</div>
	<table class="findTable">
		<tr>
			<td>
				<ul>
					<li class="findIdTab selected" onclick="location.href='findId'">아이디 찾기</li>
					<li class="findPwTab" onclick="location.href='findPw'">비밀번호 찾기</li>
				</ul>
			</td>
		<tr>
		<form action="findIdByMobile" method="post" id="f" >
			<tr><td><h3 class="findH3"><span class="material-symbols-outlined">call</span> 휴대폰 번호로 찾기</h3></td></tr>
			<tr><td><input type="text" placeholder="이름" name="userName" oninput="regUserNameCheck()"></td></tr>
			<tr>
				<td>
					<input type="text" placeholder="휴대폰번호 ex) 01000000000" id="mobile" name="mobile" oninput="regMobileCheck()">
				</td>
			</tr>
			<tr><td><label id="regMobileLabel">* (-) 없이 번호만 입력하세요.</label></td></tr>
			<tr><td class="findIdBtnTd"><input type="button" value="아이디 찾기" onclick="findIdByMobile()"></td></tr>
		</form>
		
		<tr><td class="tableHr"></td></tr>
		
		<form action="findIdByEmail" method="post" id="ff" >
			<tr><td><h3><span class="material-symbols-outlined">mail</span> 이메일로 찾기</h3></td></tr>
			<tr><td><input type="text" placeholder="이름" name="userName" oninput="regUserNameCheck()"></td></tr>
			<tr>
				<td>
					<input type="text" placeholder="이메일" id="email" name="email" oninput="regEmailCheck()">
					<select name="emailSelect" id="emailSelect" class="emailSelect" onchange="regEmailSelectCheck()">
							<option>@ 선택</option>
							<option>@naver.com</option>
							<option>@gmail.com</option>
							<option>@daum.net</option>
							<option>@hanmail.net</option>
					</select>
				</td>
			</tr>
			<tr><td><label id="regEmailLabel">* 이메일 주소를 입력하세요.</label></td></tr>
			<tr><td class="findIdBtnTd"><input type="button" value="아이디 찾기" onclick="findIdByEmail()"></td></tr>
		</form>
	</table>
</div>

<c:import url="/footer"/>
</body>
</html>