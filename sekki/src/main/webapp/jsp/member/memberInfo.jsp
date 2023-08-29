<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/header" />
	<div align="center" class="sub_div">
		<h1>회원 목록</h1>

		<c:choose>
			<c:when test="${empty members }">
				<h1> 등록된 데이터가 존재하지 않습니다. </h1>
			</c:when>
			<c:otherwise>
				<table border=1>
					<thead>
						<tr>
							<th>아이디</th>
							<th>이름</th>
							<th>전화번호</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="member" items="${ members}">
							<tr>
								<td onclick="location.href='userInfo?id=${member.id }&currentPage=${currentPage }'">
									${member.id }
								</td>
								<td>${member.userName }</td>
								<td>${member.mobile }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div> ${result}	</div>

				<form action="memberInfo">
					<select name="select">
						<option value="">전체</option>
						<option value="id">아이디</option>
						<option value="mobile">전화번호</option>
					</select>
					<input type="text" name="search" />
					<input type="submit" value="검색" />
				</form>
		</c:otherwise>
	</c:choose>
	</div>
<c:import url="/footer" />





