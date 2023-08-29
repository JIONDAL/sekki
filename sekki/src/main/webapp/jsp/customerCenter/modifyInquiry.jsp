<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 수정</title>
<link href="css/customerCenter.css" rel="stylesheet"/> 
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<c:import url="/header"></c:import>
	<div id="wrap">
	<h2>문의 수정</h2>
		<form action="modifyInquiryProc?num=${inquiry.num}" method='post' enctype="multipart/form-data" id="f">
			<div class="writeForm">
				<table class="writeTable">
					<colgroup>
						<col width="25%"></col>
						<col width="*"></col>
					</colgroup>
				    <tr>
				        <th>제목 수정<label id="titleLabel" class="titleLabel" style="display: none"> (*필수)</label></th>
				        <td colspan="2"><input type="text" name="title" id="title" value="${inquiry.title}"  maxlength="30" oninput="titleCheck()" placeholder="제목(최대 30자)"/></td>
				    </tr>
				    <tr>
				        <th>내용 수정<label id="contentLabel" class="contentLabel" style="display: none"> (*필수)</label></th>
				        <td colspan="2">
				            <textarea rows="10" cols="30" name="content" id="content" maxlength="1000" oninput="contentCheck()" placeholder="내용을 입력하세요.(최대 1000자)">${inquiry.content }</textarea>
				        </td>
				    </tr>
				    <tr>
				        <th>파일 수정</th>
				        <td colspan="2" class="upfileStyle">
				           <ul>
                                <c:choose>
                                    <c:when test="${empty inquiry.files}">
                                        <li style="display:block;">없음</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li style="display:block;">${inquiry.files}</li>
                                    </c:otherwise>
                                </c:choose>
                                <li>
                                    <label for="upfile">파일수정</label>
                                    <input type="file" id="upfile" name="upfile">
                                    <input class="upload-name" value="첨부파일" placeholder="첨부파일">
                                </li>
                                
                            </ul>
				        </td>
				    </tr>
				    <tr>
				        <th>비밀글</th>
				        <td>비밀글 설정<input type="checkbox" value="secretChecked" name="secretCheckbox" ${inquiry.secret == '비밀글' ? 'checked' : ''}></td>
				        <td><input type="password" placeholder="글 비밀번호 입력" name="secretPw" value="${inquiry.secretPw }"></td>
				    </tr>
				</table>
		
				</div>
			
				<div class="writebtnDiv">
					<input type="button" value="수정" onclick="modifyCheck()">
					<input type="button" value="목록" onclick="location.href='inquiry'">
				</div>
		</form>
	</div>
<c:import url="/footer"/>
</body>
</html>