<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script src="/js/member.js"></script>
<link href="css/member.css" rel="stylesheet" />
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
				<a href="findId" ><li>아이디 찾기</li></a>
				<a href="findPw" class="selected"><li>비밀번호 찾기</li></a>
			</ul>
		</div>
		<table class="findTable">
			<tr>
				<td>
					<ul>
						<li class="findIdTab" onclick="location.href='findId'">아이디
							찾기</li>
						<li class="findPwTab selected" onclick="location.href='findPw'">비밀번호
							찾기</li>
					</ul>
				</td>
			<tr>
			<tr>
				<td class="findPwReultTd">
					<h2>임시 비밀번호를 발급했습니다.</h2>
					<c:choose>
						<c:when test="${deliveryMethod == 'toEmail'}">
							<p><span>${id }</span> 님의 <span>이메일</span>로 임시비밀번호를 발송했습니다.</p> 
						</c:when>
						<c:otherwise>
							<p><span>${id }</span>님의 <span>휴대폰</span>으로 임시비밀번호를 발송했습니다.</p> 
						</c:otherwise>
					</c:choose>
					
					<input type="button"
					value="로그인" onclick="location.href='login'">
				</td>
			</tr>


		</table>
	</div>

	<c:import url="/footer" />
</body>
</html>