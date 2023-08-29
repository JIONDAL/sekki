<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 작성</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<c:import url="/header"></c:import>
	<div id="visual">
		<h1>문의하기</h1>
	</div>
	<div id="wrap">
	<h1>문의 작성</h1>
		<form action="writeInquiryProc" method='post' enctype="multipart/form-data" id="f">
			<div class="writeForm">
				<table class="writeTable">
					<colgroup>
						<col width="25%"></col>
						<col width="*"></col>
					</colgroup>
				    <tr>
				        <th>제목<label id="titleLabel" class="titleLabel"> (*필수)</label></th>
				        <td colspan="2"><input type="text" name="title" id="title" maxlength="30" oninput="titleCheck()" placeholder="제목(최대 30자)"></td>
				    </tr>
				    <tr>
				        <th>내용<label id="contentLabel" class="contentLabel"> (*필수)</label></th>
				        <td colspan="2">
				            <textarea rows="10" cols="30" name="content" id="content" maxlength="1000" oninput="contentCheck()" placeholder="내용을 입력하세요.(최대 1000자)"></textarea>
				        </td>
				    </tr>
				    <tr>
				        <th>파일 첨부</th>
				        <td colspan="2" class="upfileStyle">
				            <label for="upfile">파일선택</label>
				            <input type="file" id="upfile" name="upfile">
				            <input class="upload-name" value="첨부파일" placeholder="첨부파일">
				        </td>
				    </tr>
				    <tr>
				        <th>비밀글</th>
				        <td>비밀글 설정 <input type="checkbox" value="secretChecked" name="secretCheckbox"></td>
				        <td><input type="password" placeholder="비밀번호 설정 ex) 1234" name="secretPw"></td>
				    </tr>
				</table>
		
				</div>
			
				<div class="writebtnDiv">
					<input type="button" value="글쓰기" onclick="nullCheck()">
					<input type="button" value="목록" onclick="history.back()">
				</div>
		</form>
	</div>
<c:import url="/footer"/>
</body>
</html>