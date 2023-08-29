<%-- <%@page import="java.text.SimpleDateFormat"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>문의하기</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 	
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0" />
</head>
<body>
<c:import url="/header"></c:import>
	<div id="visual">
		<h1>문의하기</h1>
	</div>
<div id="wrap">
	<div id="centerMenu">
		<ul>
			<a href="announcement"><li>고객센터</li></a>
			<a href="announcement"><li>공지사항</li></a>
			<a href="inquiry" class="selected"><li>문의하기</li></a>
		</ul>
	</div>
		<h1 class="subtitle">1:1 문의</h1>
		<div class="searchDiv">
			<form action="inquiry" class="searchDivForm">
				<input type="text" name="search" placeholder="검색어 입력"/>
				<input type="submit" value="검색" />
			</form>
		</div>
	<table class="centerListTable">
		<colgroup>
			<col width="15%"></col>
			<col width="37%"></col>
			<col width="13%"></col>
			<col width="18%"></col>
			<col width="5%"></col>
			<col width="13%"></col>
		</colgroup>
		<thead>
		<tr>
			<th>답변</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
			<th>공개</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach var="inquiry" items="${ inquirys}">
				<tr>
					<td>
						 <c:choose>
					        <c:when test="${inquiry.replys >= 1}">
					            <p style="color: #2E64FE; font-weight: 600">완료</p>
					        </c:when>
					        <c:otherwise>
					            <p style="color: #DF0101; font-weight: 600">대기</p>
					        </c:otherwise>
					    </c:choose>
					</td>
					<td class="contentTdHover" onclick="inquirySecretCheck(${inquiry.num })">
						${inquiry.title }
					</td>
					<td>${inquiry.writer }</td>
					<td>${inquiry.writeDate }</td>
					<td>${inquiry.hits }</td>
					<c:choose>
					    <c:when test="${inquiry.secret eq '비밀글'}">
					        <td><span class="material-symbols-outlined">vpn_key</span></td>
					    </c:when>
					    <c:otherwise>
					        <td></td>
					    </c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	<div class="pages"> ${result}	</div>
	<div class="announcementBtnDiv">
		 <button type="button" onclick="writeInquiryLoginCheck()">문의하기</button> 
	</div>
</div>
<c:import url="/footer"/>
</body>
</html>