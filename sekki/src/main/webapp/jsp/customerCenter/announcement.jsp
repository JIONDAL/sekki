<%-- <%@page import="java.text.SimpleDateFormat"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<script src="js/center.js"></script>
<link href="css/customerCenter.css" rel="stylesheet"/> 	
</head>
<body>
<c:import url="/header"></c:import>
	<div id="visual">
		<h1>고객센터</h1>
	</div>
<div id="wrap">
	<div id="centerMenu">
		<ul>
			<a href="announcement"><li>고객센터</li></a>
			<a href="announcement" class="selected"><li>공지사항</li></a>
			<a href="inquiry"><li>문의하기</li></a>
		</ul>
	</div>
	<h1 class="subtitle">공지사항</h1>
		<div class="searchDiv">
			<form action="announcement" class="searchDivForm">
				<input type="text" name="search" placeholder="검색어 입력" />
				<input type="submit" value="검색" />
			</form>
		</div>
	<table class="centerListTable">
		<colgroup>
			<col width="10%"></col>
			<col width="40%"></col>
			<col width="15%"></col>
			<col width="25%"></col>
			<col width="10%"></col>
		</colgroup>
		<thead>
			<tr>
				<th>No.</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="announcement" items="${ announcements}">
				<tr>
					<td>${announcement.num}</td>
					<td class="contentTdHover" onclick="location.href='announcementContent?num=${announcement.num }'">
						${announcement.title }
					</td>
					<td>${announcement.writer }</td>
					<td>${announcement.writeDate }</td>
					<td>${announcement.hits }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pages"> ${result } </div>
	<c:if test="${sessionScope.id == 'admin'}">
	    <div class="announcementBtnDiv">
	        <button type="button" onclick="location.href='writeAnnouncement'">공지사항 등록</button>
	    </div>
	</c:if>
</div>

<c:import url="/footer"/>
</body>
</html>