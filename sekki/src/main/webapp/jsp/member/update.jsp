<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />

<div align="center" class="sub_div">
	<h1>회원 수정</h1>
	<table ><tr><td>
	<form action="updateProc" method="post" id="f">
		<input type="text" value="${sessionScope.id }" id="id"> (*필수 항목) <br>
		<input type="password" name="pw" placeholder="비밀번호" id="pw"><br>
		<input type="password" name="confirm" placeholder="비밀번호 확인 " id="confirm"
		onchange="pwCheck()">
		<label id="label" ></label><br>
		<input type="text" name="userName" id="userName" value="${sessionScope.userName }" ><br>
		<input type="text" name="address" value="${sessionScope.address }"><br>
		<input type="text" name="mobile" value="${sessionScope.mobile }"><br>
		<input type="file" name="profilePicture" id="profilePicture" ><br>
		
		<input type="text" name="height" placeholder="키(cm)" value="${sessionScope.height }" class="sub_input"><br>
		<input type="text" name="weight" placeholder="몸무게(kg)" value="${sessionScope.weight }" class="sub_input"><br>
		
		<input type="button" value="회원수정" onclick="allCheck()" class="btn">
		<input type="button" value="취소" onclick="location.href='mypage'" class="btn"><br>
	</form>
	</td></tr></table>
</div>

<c:import url="/footer" />