<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의내역</title>
<link href="css/customerCenter.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@48,400,0,0" />
</head>
<body>
<c:import url="/header"></c:import>
	<div id="visual">
		<h1>문의내역</h1>
	</div>
<div id="wrap">
	<div class="viewAnnounce">
		<div id="centerMenu">
			<ul>
				<a href="announcement"><li>고객센터</li></a>
				<a href="announcement"><li>공지사항</li></a>
				<a href="inquiry" class="selected"><li>문의하기</li></a>
			</ul>
		</div>
		<h1>${inquiry.title }</h1>
		<p class="settingBox">
			<span>작성자 ${inquiry.writer }</span>
		</p>
		<p>${inquiry.writeDate } ${inquiry.writeTime }</p>
		<p>조회수 ${inquiry.hits }</p>
		<hr>
		<p class="contentArea">${inquiry.content }</p>
		<hr>
		<div class="fileDiv">
			<c:choose>
				<c:when test="${empty inquiry.files }">
					<p></p>
				</c:when>
				<c:otherwise>
					<p onclick="fileDownload(${inquiry.num })">파일 다운로드 > <span>${inquiry.files }</span></p>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${sessionScope.id == inquiry.writer || sessionScope.id == 'admin'}">
			<div class="modifyOrDelete">
				<%-- <button onclick="location.href='modifyInquiry?num=${inquiry.num }'">수정</button> --%>
				<button onclick="modifyAble(${inquiry.num })">수정</button>
				<button onclick="deleteInquiryProc(${inquiry.num})">삭제</button>
			</div>
		</c:if>
		<div class="h80"></div>
		<c:forEach var="inquiryReply" items="${inquiryReplys}">
		    <div class="adminReply">
		        <h2>
		            문의하신 내용에 대한 답변입니다.
		            <c:if test="${sessionScope.id == 'admin'}">
			            <a href="#" id="settings-${inquiryReply.replyNum}">
			                <span class="material-symbols-sharp">settings</span>
			            </a>
			        </c:if>	 
		            <ul id="clickSettings-${inquiryReply.replyNum}" class="clickSettings">
		                <li onclick="modifyAdminReplyClick(${inquiryReply.replyNum}, ${inquiryReply.centerNum})">수정</li>
		                <li>|</li>
		                <li onclick="deleteAdminReply(${inquiryReply.replyNum}, centerNum=${inquiryReply.centerNum})">삭제</li>
		            </ul>
		        </h2>
		        <hr>
		        <p class="contentAreaReply">${inquiryReply.reply}</p>
		        <p class="writeDate">${inquiryReply.writeDate}</p>
		    </div>
		</c:forEach>
		
		<c:if test="${sessionScope.id == 'admin'}">
	    	<div class="adminReplyBtn">
				<button  onclick="location.href='adminReply?num='+${inquiry.num }">답변하기</button>
			</div>
		</c:if>	 
		
	</div>
	<div class="viewListBtn">
		<button  onclick="location.href='inquiry'">목록</button>
	</div>
</div>
<c:import url="/footer"></c:import>
</body>
</html>