<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="/js/member.js"></script>
<link href="css/member.css" rel="stylesheet" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Gamja+Flower&display=swap"
	rel="stylesheet">
</head>
<!-- 
		# 인가 코드 요청 #
		https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-request
	 -->
<body>
	<c:import url="/header"></c:import>
	<div id="visual">
		<h1>로그인</h1>
	</div>
	<div id="wrap">
		<div id="memberMenu">
			<ul>
				<a href="login"><li>로그인</li></a>
				<a href="login" class="selected"><li>로그인</li></a>
				<a href="agreeCondition"><li>회원가입</li></a>
				<a href="findId"><li>아이디 찾기</li></a>
				<a href="findPw"><li>비밀번호 찾기</li></a>
			</ul>
		</div>
		<c:choose>
			<c:when test="${sessionScope.id == null}">
				<form action="loginProc" class="loginProc" method="post" id="f">
					<table class="loginTable">
						<colgroup>
							<col width="50%"></col>
							<col width="50%"></col>
						</colgroup>
						<tr>
							<td><h2>
									반갑습니다.<br>
									<span class="titleSpan">자취세끼</span>입니다.
								</h2></td>
						</tr>
						<tr>
							<td colspan="2"><input type="text" name="id"
								placeholder="아이디" id="id"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="password" name="pw"
								placeholder="비밀번호" id="pw"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="button" value="로그인"
								onclick="loginCheck()"></td>
						</tr>
						<tr class="textBox">
							<td><p onclick="location.href='findId'">아이디/비밀번호 찾기</p></td>
							<td><p onclick="location.href='agreeCondition'">회원가입</p></td>
						</tr>
						<tr>
							<td colspan="2"><a
								href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=2e151bc9c1174ea0e8836b77c28803c3&redirect_uri=http://localhost/kakaoLogin">
									<img
									src="https://t1.daumcdn.net/cfile/tistory/99BEE8465C3D7D1214" />
							</a></td>
						</tr>
					</table>
				</form>
			</c:when>
			<c:otherwise>
				<table class="logoutTable">
					<tr>
						<td><h2>
								<span class="titleSpan">${sessionScope.userName}</span>님,<br>
								반갑습니다.
							</h2></td>
					</tr>
					<tr>
						<td><input type="button" onclick="logout()" value="로그아웃"></td>
					</tr>
					<tr>
						<td><input type="button" onclick="" value="요리하러 가기"></td>
					</tr>

				</table>
			</c:otherwise>
		</c:choose>
	</div>

	<c:import url="/footer" />
</body>
</html>