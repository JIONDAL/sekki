<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<c:import url="/header" />
<style text/css="css">
.centered-list {
  text-align: center;
}

.centered-list ul {
  list-style: none;
  padding: 0;
}

.centered-list li {
  margin: 50px 70px;
}

.centered-list li a {
  text-decoration: none;
  color: black;
  font-size: 18px;
}
</style>



<div class="centered-list">
  <ul>
    <li><a href="/update">회원정보 수정</a></li> 
    <li><a href="/recipehoogi">요리 후기</a></li>
    <li><a href="/../jsp/recipeboard/recipeboardForm.jsp">좋아요 한 글, 댓글</a></li>
    <li><a href="member/list">구독자 리스트</a></li>
  </ul>
</div>




<c:import url="/footer" />