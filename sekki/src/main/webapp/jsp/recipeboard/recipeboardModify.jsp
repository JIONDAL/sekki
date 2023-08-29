<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" /> 

<div align="center" class="main_div">
<form action="recipeboardModifyProc" method="post">
	<input type="hidden" name="no" value="${recipeboard.no }" />
	<h1>글 수정</h1>
	<table border='1'>
		<tr>
			<th width="100">작성자</th>
			<td width="200">${recipeboard.id }</td>
			<th width="100">조회수</th>
			<td width="200">${recipeboard.hits }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${recipeboard.writeDate }</td>
			<th>다운로드</th>
			<td>${recipeboard.fileName }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="3">
				<input style="width:100%;" type="text" name="title" value="${recipeboard.title }" />
			</td>
		</tr>
		<tr>
			<th>문서내용 수정</th>
			<td colspan="3">
				<textarea rows="10" cols="30" style="width:100%" name="content">${recipeboard.content }</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button type="button" onclick="location.href='recipeboardForm.jsp'">목록</button>
				<input type="submit" value="수정" class="btn">
				<button type="button" onclick="history.back()">이전</button> 
			</td>
		</tr>
	</table>
</form>
</div>
<c:import url="/footer" />





















