<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 등록</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<c:import url="/header"></c:import>
	<div id="visual">
		<h1>공지사항</h1>
	</div>
	<div id="wrap">
	<h1>공지사항 등록</h1>
		<form action="writeAnnouncementProc" method='post' enctype="multipart/form-data" id="f">
		<div>
			<table class="writeTable">
			<colgroup>
				<col width="25%"></col>
				<col width="*"></col>
			</colgroup>
			<tr>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
				<th>제목<label id="titleLabel" class="titleLabel"> (*필수)</label></th>
				<td><input type="text" name="title" id="title" maxlength="30" oninput="titleCheck()" placeholder="제목(최대 30자)"></td>
			</tr>
			<tr>
				<th>내용<label id="contentLabel" class="contentLabel"> (*필수)</label></th>
				<td>
					<textarea rows="10" cols="30" name="content" id="content" maxlength="1000" oninput="contentCheck()" placeholder="내용을 입력하세요.(최대 1000자)"></textarea>
				</td>
			</tr>
			<tr>
				<th>파일 첨부</th>
				<td class="upfileStyle">
					  <label for="upfile">파일첨부</label>
                      <input type="file" id="upfile" name="upfile">
                      <input class="upload-name" value="첨부파일" placeholder="첨부파일">
				</td>
			</tr>
		</table>
		</div>
	
		<div class="writebtnDiv">
			<input type="button" value="글쓰기" onclick="nullCheck()">
			<input type="button" value="취소" onclick="location.href='announcement'">
		</div>
	</form>
	</div>
<c:import url="/footer"/>
</body>
</html>