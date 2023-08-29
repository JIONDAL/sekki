<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style type="text/css">
   a {text-decoration: none; color:black;}
   ul {padding: 20px;}
   ul li {display: inline; padding: 15px;}
   .main_div{height: 150px; padding-top : 80px;}
</style>    



<script src="/js/center.js"></script>
<script src="/js/member.js"></script>
<link href="css/reset.css" rel="stylesheet" type="text/css" />    


<div id="header">

   <div class="mainLogo">
      <a href="index">
         <img src="image/logo.png" alt="logo" />
      </a>
   </div>
   <ul class="nav">
      <li><%-- 세션에서 "profilePictureUrl" 속성 가져오기 --%>

    <%
        HttpSession imgurl = request.getSession();
        String profilePictureUrl = (String) session.getAttribute("profilePictureUrl");
    %>

    <%-- "profilePictureUrl" 값이 null이 아니면 이미지 표시 --%>
    <% if (profilePictureUrl != null) { %>
        <img src="<%= profilePictureUrl %>" class="profile">
    <% }%></li>


      <li><a href="${context }login">로그인</a></li>
      <li><a href="${context }index">레시피</a></li>

      <li><a href="${context }recipeBoard">레시피</a></li>
      <li><a href="${context }mypage">마이페이지</a></li>

      <li><a href="${context }boardForm">커뮤니티</a></li>
      <li><a href="${context }memberInfo">스토어</a></li>
      <li><a href="${context }announcement">고객센터</a></li>
   </ul>

</div>
<hr>
<c:url var="context" value="/"/>
