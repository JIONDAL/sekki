<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,0,0" />


<c:import url="/header" />

<div align="center" class="community_div">
	<h1>
	<span>공동구매</span> ㆍ <a href="boardFriendForm">밥친구</a>
	</h1>
		<c:choose>
			<c:when test="${empty boards }">
				<h1> 등록된 데이터가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
			<ul class="boardForm">
				<c:forEach var="board" items="${ boards}">
					<li onclick="location.href='boardContent?no=${board.no }'" >
						<div class="top">
							<span class="material-symbols-outlined" id="person">person_pin_circle</span>
							<span id="Bid">${board.id }</span>
							<span id="Btitle">${board.title }</span>
							<span id="BwriteDate">${board.writeDate }</span>
							<span id="Bhits">조회수 ${board.hits }</span>
						</div>
						<div class="down">
							<span id="Bcontent">${board.content }</span>
							<span class="material-symbols-outlined" id="thumb">thumb_up</span>
							<span id="Blikes">${board.likes }</span>
						</div>
					</li>
				</c:forEach>
			</ul>
			<div class="tail">
				<form action="boardForm" class="sch">
					<input type="text" placeholder="검색어를 입력하세요." name="search"/>
					<input type="submit" value="확인"/>
				</form>
				<div class="boardList">
					${result }
				</div>
				<button type="button" onclick="location.href='boardWrite'">글쓰기</button>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<c:import url="/footer" />

