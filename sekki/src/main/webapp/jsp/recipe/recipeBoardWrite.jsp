<%@ page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="/header" />
<c:url var="context" value="/" />
<link href="css/recipes.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="/js/recipe.js"></script>
<div id="full">
	<div id="width1200">
		<form action="recipeProc" method="post" id="reci"
			enctype="multipart/form-data">
			<div class="title">레시피 등록</div>
			<%--cont_box 1--%>
			<div id="cok_info" class="cont_box pd_l_60">

				<div class="content_pdding">
					<span class="name">레시피 제목</span> <input type="text"
						class="cok_title" name="title" placeholder="예)스테이크 굽는법">
				</div>
				<%--요리소개--%>
				<div>
					<span class="name">요리소개</span>
					<textarea rows="2" cols="5" name="content"
						placeholder="기본값은 여기에 적어줍니다."></textarea>
				</div>
				<%--요리소개--%>
				
			<div class="photoBox">
				<!-- 파일 선택 버튼 (숨겨진 버튼) -->
				<input type="file" id="fileInput" accept="image/*"
					style="display: none;" name="mainphotoUrl" onchange="showSelectedImage()">

				<!-- 이미지를 표시할 img 요소 -->
				<img id="mainPhotoHolder" onclick="browseMainFile()"
					src="https://recipe1.ezmember.co.kr/img/pic_none4.gif"
					style="width: 250px; height: 250px; cursor: pointer">

			</div>

				<%--카테고리--%>
				<div class="content_pdding">
					<span class="name">카테고리</span> <select class="reci" name="category">
						<option value="nomal">요리를 선택하세요</option>
						<option value="kor">한식</option>
						<option value="chi">중식</option>
						<option value="jap">일식</option>
						<option value="skeki">양식</option>
					</select>
				</div>
				<%--카테고리--%>
				<%--요리정보--%>
				<div>
					<span class="name">요리정보</span> <span class="info_n">인원</span> <select
						class="cok" name="cuisine">
						<option>인원</option>
						<option value="1">1인분</option>
						<option value="2">2인분</option>
						<option value="3">3인분</option>
					</select> <span class="info_n">시간</span> <select class="cok" name="times">
						<option>시간</option>
						<option value="5">5분 이내</option>
						<option value="30">30분 이내</option>
						<option value="60">1시간 이내</option>
						<option value="over">2시간 이상</option>
					</select> <span class="info_n">난이도</span> <select class="cok" name="degree">
						<option>난이도</option>
						<option value="1">아무나</option>
						<option value="2">초급</option>
						<option value="3">중급</option>
						<option value="4">고급</option>
					</select>
				</div>

				<%--요리정보--%>
			</div>
			<%--cont_box 1--%>
			<div class="cont_box pd_l_60 content_borad">
				<h3>재료가 남거나 부족하지 않도록 정확한 계량정보를 적어주세요.</h3>
				<div class="material_all">
					<%--왼쪽 --%>
					<div class="left_material">
						<h3>재료</h3>

					</div>
					<%--왼쪽 --%>

					<%--오른쪽 --%>

					<div class="coumngka">
						<div id="materialContainer">
							<div class="right_boxs">
								<input type="text" name="materialname"
									class="form-control materials_css" placeholder="예) 돼지고기">
								<input type="text" name="materialamount"
									id="cok_material_amt_1_1" class="form-control materials_css"
									placeholder="예) 100g">
							</div>

						</div>
						<div class="right_btnadd">
							<button type="button" id="addMaterialButton">추가</button>
						</div>
					</div>




					<%--오른쪽 --%>
				</div>
			</div>
			<%--cont_box 1--%>

			<%--cont_box 2--%>
			<div class="cont_box pd_l_60 content_borad">
				<h3>요리순서</h3>
				<br>
				<p class="recipe_p">
					<span class="mid_title">요리의 맛이 좌우될 수 있는 중요한 부분은 빠짐없이 적어주세요!</span><br>
					예시) 10분간 익혀주세요 -> 10분간 약불로 익혀주세요<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 마늘은 익혀주세요 -> 마늘편을
					충분히 익혀주셔야 매운 맛이 사라집니다.<br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 꿀을 조금 넣어주세요 -> 꿀이
					없는 경우, 설탕 1스푼으로 대체 가능합니다.
				</p>
				<%--요리 순서 box!--%>

				<!-- 여러 Step들을 감싸는 부모 요소 -->
				<div id="stepContainer">
					<!-- Step 1 -->
					<div class="cok_step">
    <p class="cok_step_p">Step1</p>
    <div id="cok_step_box">
        <textarea name="step_text" id="step_text_STEP_1" class="form-control step_cont step_text_STEP_css" placeholder="예) 소고기 맛나게 구워드세요"></textarea>
    </div>
    <div id="divStepPhotoBox_STEP_1">
        <label for="step_photoholder_STEP_${stepCount}" class="step_photoLabel">
            <img id="stepPhotoHolder_STEP_1" class="stepPhotoHolder_STEP_css" src="https://recipe1.ezmember.co.kr/img/pic_none2.gif">
        </label>
        <input type="file" name="step_photoholder" id="step_photoholder_STEP_${stepCount}" class="step_photoholder" accept="image/*" onchange="previewImage(this, ${stepCount})" multiple>
    </div>
</div>
				</div>

				<!-- 스텝 추가 버튼 -->
				<div class="step_button">
					<button type="button" id="step_plus_btn">추가</button>
				</div>


				<%--
			 ----- 보류 ------
			<div class="cok_set_plus">
				<div class="cok_cute"><img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_material.png" style="width:22px;height:22px;">재료</div>
				<div class="cok_cute"><img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tool.png?v.1" style="width:22px;height:22px;">도구</div>
				<div class="cok_cute"><img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_fire.png?v.1" style="width:22px;height:22px;">불</div>
				<div class="cok_cute"><img src="https://recipe1.ezmember.co.kr/img/mobile/app_icon_step_tip.png?v.1" style="width:22px;height:22px;">팁</div>
			</div>
			--%>
				<%--요리 순서 box!--%>
			</div>
			<%--cont_box 2--%>
			<%--요리팁 --%>

			<div class="cont_box pd_l_60 content_borad">
				요리팁
				<textarea rows="3" cols="3" name="tip"></textarea>
			</div>

			<div class="cont_box pd_l_60 content_borad last_div">
				<button type="submit">등록하기</button>
			</div>
		</form>
		<%--요리팁 --%>

	</div>
</div>

<c:import url="/footer" />