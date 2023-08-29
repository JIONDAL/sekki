<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답변 등록</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<c:import url="/header"></c:import>
	<div id="wrap">
	<div id="centerMenu">
		<ul>
			<a href="announcement"><li>고객센터</li></a>
			<a href="announcement"><li>공지사항</li></a>
			<a href="inquiry" class="selected"><li>문의하기</li></a>
		</ul>
	</div>
	<h1>답변 등록</h1>
		<form action="adminReplyProc?num=${inquiry.num}" method='post' id="f">
		<div>
			<table class="adminReplyTable">
			<tr>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
				<th>문의 내역</th>
			</tr>
			<tr>
				<td>
					<p class="contentAreaReply">${inquiry.content }</p>
				</td>
			</tr>
			<tr>
				<th>답변 작성<label id="contentLabel" class="contentLabel"> (*필수)</label></th>
			</tr>
			<tr>
				<td>
					<textarea rows="10" cols="30" name="replyContent" id="replyContent"  maxlength="1000" oninput="replyContentCheck()" placeholder="내용을 입력하세요.(최대 1000자)"></textarea>
				</td>
			</tr>
		</table>
		</div>
	
		<div class="writeReplybtnDiv">
			<input type="submit" value="답변하기">
			<input type="button" value="취소" onclick="history.back()">
		</div>
	</form>
	</div>
<c:import url="/footer"/>
</body>
</html>