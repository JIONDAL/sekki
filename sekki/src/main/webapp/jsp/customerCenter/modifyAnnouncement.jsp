<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="css/customerCenter.css" rel="stylesheet"/> 
</head>
<body>

<c:import url="/header"></c:import>
	<div id="visual">
		<h1>공지사항</h1>
	</div>
<div id="wrap">
        <h1>공지사항 수정</h1>
        <form action="modifyAnnouncementProc?num=${announcement.num}" method="post" enctype="multipart/form-data">
            <div class="writeForm">
                <table class="writeTable">
               		<colgroup>
						<col width="25%"></col>
						<col width="*"></col>
					</colgroup>
                    <tr>
                        <th>제목 수정<label id="titleLabel" class="titleLabel" style="display: none"> (*필수)</label></th>
                        <td><input type="text" name="title" id="title" value="${announcement.title}"  maxlength="30" oninput="titleCheck()" placeholder="제목(최대 30자)"/></td>
                    </tr>
                    <tr>
                        <th>내용 수정<label id="contentLabel" class="contentLabel" style="display: none"> (*필수)</label></th>
                        <td>
                            <textarea rows="5" cols="50" name="content" id="content" maxlength="1000" oninput="contentCheck()" placeholder="내용을 입력하세요.(최대 1000자)">${announcement.content}</textarea>
                        </td>
                    </tr>
                    <tr class="fileModify">
                        <th>파일 수정</th>
                        <td>
                            <ul>
                                <c:choose>
                                    <c:when test="${empty announcement.files}">
                                        <li>없음</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>${announcement.files}</li>
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
                </table>
            </div>

            <div class="writebtnDiv">
                <input type="submit" value="수정" onclick="modifyCheck()">
                <input type="button" value="취소" onclick="history.back()">
            </div>
        </form>
    </div>
<c:import url="/footer"></c:import>
</body>
</html>