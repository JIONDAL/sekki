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
		<c:choose>
			<c:when test="${id == null}">
				<tr>
					<td class="findIdReultTd">
						<h2>제공하신 정보로 아이디를 찾을 수 없습니다.</h2>
						<p>확인 후 이용바랍니다.</p>
						<input type="button" value="아이디 찾기" onclick="location.href='findId'">
						<input type="button" value="로그인" onclick="location.href='login'">
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td class="findIdReultTd">
						<h2>제공하신 정보로 아이디를 찾았습니다.</h2>
						<p><span style="font-weight:bold;">${userName }</span> 님의 아이디는 <span style="font-weight:bold; color:rgb(255, 150, 45);" > ${id } </span>입니다.</p>
						<input type="button" value="비밀번호 찾기" onclick="location.href='findPw'">
						<input type="button" value="로그인" onclick="location.href='login'">
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		
	</table>
</div>

<c:import url="/footer"/>
</body>
</html>