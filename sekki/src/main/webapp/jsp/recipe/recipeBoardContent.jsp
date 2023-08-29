<%@ page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />
<c:url var="context" value="/" />
<link href="css/recipes.css" rel="stylesheet" />
 <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"
  />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="/js/recipe.js"></script>
<div id="full">
	<div id="width1200">
		<%--메인 인포 --%>
		<div id="main_con_info">
			<div class="main_photo">
				<img src="">
			</div>
			<div class="user_inffo">
				<img src="${recipeCon.mainphoto }" class="main_img_con">
				<div id="user_info_con">
					<img src="${recipeCon.profile}" class="user_profile_con">
					<p id="user_info_con_text">${recipeCon.id}</p>
				</div>
			</div>

			<div class="main_content">
				<h1>[${recipeCon.category}]${recipeCon.title}</h1>
				<p>${recipeCon.content}</p>
				<ul id="cook_info">
					<li><img src="image/icon_man.png"> ${recipeCon.cuisine }
					</li>
					<li><img src="image/icon_star.png"> ${recipeCon.times }
					</li>
					<li><img src="image/icon_time2.png"> ${recipeCon.degree }
					</li>

				</ul>
			</div>
		</div>
		<%--메인 인포 --%>

		<%--메인 재료 --%>
		<div class="cont_box pd_l_60 content_borad">
			<div id="materialss">
				<h3>재료</h3>
				<ul class="mate_con">
					<c:forEach var="reciMa" items="${reciMa}">
						<li class="mate_con_list"><span class="mate_con_name">${reciMa.materialname}</span><span
							class="mate_con_alam">${reciMa.materialamount}</span></li>


					</c:forEach>
				</ul>
			</div>
		</div>
		<%--메인 재료 --%>
		<%--step --%>
		<div class="cont_box pd_l_60 content_borad">
			<div id="stepss">
				<h1>조리 순서</h1>
				<c:forEach var="reciStepList" items="${reciStepList}">
					<ul>
						<li class="step_con_list">
							<div class="step_index">${loop.index + 1}</div> <span
							class="step_texts">${reciStepList.step_text}</span> <img
							class="step_con_img" src="${reciStepList.step_photoholder }">
						</li>
					</ul>
				</c:forEach>
			</div>
		</div>
		<%--step --%>

		<%-- 별점 --%>
		<div class="cont_box pd_l_60 content_borad">
			<h2>댓글</h2>
			<c:choose>
			<c:when test="${not empty reciment}">
			<c:forEach var="reciment" items="${reciment}">
			<ul id="comment-${reciment.comment_no}">
			<li ><div class="coments" id="coment">
				<div class="coment_left">
					<div class="coment_profile_div">
						<img class="coment_profile" src="${reciment.profile }">
					</div>
				</div>
				<div class="coment_right">
					<div class="coment_inner">
						<div>
							<b>${reciment.id}</b> <span class="coment_time">${reciment.written_time}</span>
							<span class="coment_star">
       						<c:forEach items="${reciment.starTags}" var="starTag">
     						   ${starTag}
   							 </c:forEach>
    						 </span>
    						   <c:if test="${sessionScope.id eq reciment.id}">
    						 <div class="comment_drop">
						    <i class="fas fa-ellipsis-h dropdown-icon"></i>
						    <ul class="dropdown-menu">
						      <li>
						                 <button onclick="editComment('${reciment.id}', '${reciment.comment_content}', '${recipeCon.re_no}', '${reciment.comment_no}')">
수정</button>

						            </li>
						            <li>
						                <button type="button" onclick="deleteComment(${reciment.comment_no}, ${recipeCon.re_no})">삭제</button>
						            </li>
						            
						        
						    </ul>
						</div>
						</c:if>
						</div>
						<div class="coment_content">
							<p >${reciment.comment_content}</p>
						</div>
					</div>
				</div>

			</div></li>
			</ul>
			</c:forEach>
			</c:when>
			 <c:otherwise>
       		 <p>아직 작성한 댓글이 없습니다.</p>
   			 </c:otherwise>
			</c:choose>
			<form action="commentProc" method="post">

				<div class="starpoint_wrap">
					<div class="starpoint_box">
						<label for="starpoint_1" class="label_star" title="0.5"><span
							class="blind">0.5점</span></label> <label for="starpoint_2"
							class="label_star" title="1"><span class="blind">1점</span></label>
						<label for="starpoint_3" class="label_star" title="1.5"><span
							class="blind">1.5점</span></label> <label for="starpoint_4"
							class="label_star" title="2"><span class="blind">2점</span></label>
						<label for="starpoint_5" class="label_star" title="2.5"><span
							class="blind">2.5점</span></label> <label for="starpoint_6"
							class="label_star" title="3"><span class="blind">3점</span></label>
						<label for="starpoint_7" class="label_star" title="3.5"><span
							class="blind">3.5점</span></label> <label for="starpoint_8"
							class="label_star" title="4"><span class="blind">4점</span></label>
						<label for="starpoint_9" class="label_star" title="4.5"><span
							class="blind">4.5점</span></label> <label for="starpoint_10"
							class="label_star" title="5"><span class="blind">5점</span></label>
						<input type="radio" name="rating" id="starpoint_1" class="star_radio" value="0.5">
						<input type="radio" name="rating" id="starpoint_2" class="star_radio" value="1">
						<input type="radio"name="rating" id="starpoint_3" class="star_radio" value="1.5">
						<input type="radio" name="rating" id="starpoint_4" class="star_radio" value="2">
						<input type="radio" name="rating" id="starpoint_5"class="star_radio" value="2.5">
						<input type="radio" name="rating"id="starpoint_6" class="star_radio"value="3">
						<input type="radio"name="rating" id="starpoint_7" class="star_radio" value="3.5">
						<input type="radio" name="rating" id="starpoint_8" class="star_radio" value="4">
						<input type="radio" name="rating" id="starpoint_9"	class="star_radio" value="4.5">
						<input type="radio" name="rating"id="starpoint_10" class="star_radio" value="5">
						  <span	class="starpoint_bg"></span>
					</div>
				</div>
				<div class="comnet_written">

					<!-- 서버_처리_URL은 실제 서버로 요청을 보낼 URL로 변경해야 합니다. -->

					<textarea rows="5" cols="40" name="comment_content"
						placeholder="기본값은 여기에 적어줍니다."></textarea>
					<button type="submit">등록</button>

				</div>
			</form>
		</div>
		
		<c:if test="${sessionScope.id eq recipeCon.id}">
		    <div class="announcementBtnDiv">
		        <button type="button" onclick="deleteRecipe(${recipeCon.re_no})" class="reci_up_da" >삭제</button>
		        <button class="reci_up_da" onclick="location.href='recipeBoardUpdata?re_no=${recipeCon.re_no }'">수정</button>
		    </div>
		</c:if>

			
		<%-- 별점 --%>

	</div>
</div>

<c:import url="/footer" />