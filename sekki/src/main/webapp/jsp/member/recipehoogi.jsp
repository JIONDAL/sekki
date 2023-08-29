<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/header" />
<html>
<head>
  <title>요리 후기 페이지</title>
  <link rel="stylesheet" type="text/css" href="board.css"> <!-- CSS 파일 경로 설정 -->
</head>
<style>
	.header{
	border-bottom:1px solid #ccc;
	padding-bottom:30px;
	}
	.content {
	border-bottom:1px solid #ccc;
	padding-bottom:30px;
	}
	.footer{
	padding-bottom:30px;
	}
  /* h2 재료, 양념 */	
  .content .ingredients h2 {
    border-bottom: 1px solid #ccc;
    padding: 30px;
  }

  /* 공통 스타일을 정의합니다. */
  .ingredients ul li div {
    border-bottom: 1px solid #ccc;
    border-right:1px solid #ccc;
    padding: 16px;
    box-sizing: border-box; /* 올바른 box-sizing 값으로 수정 */
  }
  
  .ingredients ul li div span {
    color: #ccc;
    padding-top: 16px;
  }

  /* 구매 버튼에 추가 스타일을 정의합니다. */
  .buybutton {
    background-color: #007BFF;
    color: white;
    border: none;
    padding: 8px 16px;
    cursor: pointer;
    border-radius: 32px;
  }
  
  .content2 {
    display: flex;
    border-bottom:1px solid #ccc;
    padding-bottom:30px;
    align-items: center;
    flex-wrap: wrap;
    margin:20px;
    
  }
  
 
  .MAPS,.reviews{
  	flex: 1;
  	display: flex;
    flex-direction: column;
    align-items: center; /* 세로 방향 가운데 정렬 */
    margin: 10px; /* 각 섹션 간격을 위한 마진 */
  
  }
  .MAPS {
  border-right:1px solid #ccc;
  }
     
  .MAPS h2{
  padding:10px;
  border-bottom:1px solid #ccc;
  
  }
  .reviews .review-item{
  	display: flex;
    align-items: center; /* 가로 방향 가운데 정렬 */
    margin-bottom: 10px; /* 각 후기 글 사이 간격 */
  }
  .reviews h2{
  padding:10px;
  border-bottom:1px solid #ccc;
  }
  
  
</style>

<body>


  

  <div class="header" style="text-align: center;">
    <!-- 이미지를 표시할 부분 -->
    <img src="src/main/resources/static/image/logo.png" alt="이미지">
    <h1>[후기]</h1>
    <textarea id="reviewText" rows="5" cols="50" placeholder="글쓴이 후기를 작성해주세요"></textarea>
    
  </div>


  <div class="content" style="display: flex; justify-content: center;">
    <div class="ingredients" style="flex: 1;">
      <!-- 사용자 입력 폼을 통해 선택한 재료를 받아오는 예시 -->
    <!-- 재료 목록 -->
    <h2 style="text-align: center;">[재료]</h2>
    	<ul class="ingredientList">
    	<c:forEach items="${listofIngredients}" var="ingredient">
        <li>
          <div style="display: flex; justify-content: space-between; ">
            <span>${ingredient}</span>
            <div>
              <select id="martSelect">
                <option value="emart">E-Mart</option>
                <option value="homeplus">Home-Plus</option>
                <option value="wholesalemart">식자재 왕 도매마트</option>
                <option value="lottemart">롯데마트</option>
              </select>
              <button class="buybutton" data-ingredient="${ingredient}">구매</button>
            </div>
          </div>
        </li>
      </c:forEach>	      
	    </ul>
	  </div>
	<div class="ingredients" style="flex: 1;">
	    <!-- 양념 목록 -->	    
	  <h2 style="text-align: center;">[양념]</h2>
	  <ul class="seasoningList">
              <!-- 반복문으로 양념 목록 생성 -->
	    	<c:forEach items="${listofSeasoning}" var="seasoning">
	        <li>
	          <div style="display: flex; justify-content: space-between; ">
	            <span>${seasoning}</span>
	            <div>
	              <select id="martSelect">
	                <option value="emart">E-Mart</option>
	                <option value="homeplus">Home-Plus</option>
	                <option value="wholesalemart">식자재 왕 도매마트</option>
	                <option value="lottemart">롯데마트</option>
	              </select>
	              <button class="buybutton" data-ingredient="${seasoning}">구매</button>
	            </div>
	          </div>
	        </li>
	      </c:forEach>	      
	   </ul>
	   	  
		
 	 </div>
  	
 </div>
	<div class="content2" style="display: flex; justify-content: center;">
    <div class="MAPS">
      <div style="display: flex; flex-direction: column; align-items: center;">
        <h2 style="text-align: center;">KAKAO 지도</h2>
        <input type="text" id="searchInput" placeholder="검색할 지역을 입력하세요">
  		<button id="searchButton">검색</button>
        <div id="map" style="width: 600px; height: 400px; margin-right:20px; margin-top:20px;"></div>
        
      </div>
    </div>

    <div class="reviews" style="text-align: center;">
        <!-- 받은 요리 후기 글 -->
        <h2 style="text-align: center;">받은 요리 후기 글</h2>
        <div class="review-item" >
            <p>요리 후기 내용1</p>
            <button class="reply-button">답글 달기</button>
            <button class="delete-button">삭제</button>
        </div>
        <div class="review-item" >
            <p>요리 후기 내용2</p>
            <button class="reply-button">답글 달기</button>
            <button class="delete-button">삭제</button>
        </div>
    </div>
</div>
	      
	

 <div class="footer" style="text-align: center;">
    <!-- 푸터 부분 -->
    <button id="home">홈으로</button>
  </div>
  
  <script>
  
//페이지 로드 시 구매 버튼들에 클릭 이벤트 리스너 등록
  document.addEventListener('DOMContentLoaded', function () {
    var buyButtons = document.getElementsByClassName('buybutton');

    for (var i = 0; i < buyButtons.length; i++) {
      buyButtons[i].addEventListener('click', function (event) {
        var ingredient = event.target.getAttribute('data-ingredient');
        var martSelect = event.target.parentElement.querySelector('select');
        var selectedMart = martSelect.options[martSelect.selectedIndex].value;

        // 여기서 선택한 재료와 마트 정보를 활용하여 원하는 동작을 수행하면 됩니다.
        // 예를 들어, 선택한 재료와 마트로 구매 처리를 진행하거나 다른 작업을 수행할 수 있습니다.

        // 선택한 마트에 대응하는 URL을 설정해주세요.
        var url = '';

        // 선택한 마트에 따라 URL 설정
        if (selectedMart === 'emart') {
          url = 'https://emart.ssg.com/';
        } else if (selectedMart === 'homeplus') {
          url = 'https://front.homeplus.co.kr/';
        } else if (selectedMart === 'wholesalemart') {
          url = 'https://www.ewangmart.com/main/index.do';
        } else if (selectedMart === 'lottemart') {
          url = 'https://www.lotteon.com/p/display/main/lottemart?mall_no=4';
        }

        // 선택한 마트에 대응하는 URL로 새 창을 열기
        if (url !== '') {
          window.open(url, '_blank');
        }
      });
    }
  });		/*구매버튼 끝*/
  
  
  
  
  	<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=47ccec590c1f3b7c282f048e546230c2&autoload=false"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=47ccec590c1f3b7c282f048e546230c2&libraries=services,clusterer,drawing"></script>
	<script>
	var id = '<%=session.getAttribute("id")%>';
	var address = '<%=session.getAttribute("address")%>';
	var map;
	var markers = [];
	  // 페이지 로드 시 사용자 주소를 기반으로 지도를 표시
	     document.addEventListener('DOMContentLoaded', function() {
	    showUserAddressOnMap();
	    
	 // 검색 버튼 클릭 이벤트 처리
	    document.getElementById('searchButton').addEventListener('click', function() {
	      searchPlace();  
	  });
	// 사용자 주소로부터 위도와 경도 가지고오기
	
	
	
	
	
	function showUserAddressOnMap() {
	     if (address) {
	      var geocoder = new kakao.maps.services.Geocoder();
	      geocoder.addressSearch(address, function(result, status) {
	        if (status === kakao.maps.services.Status.OK) {
	          var latitude = result[0].y;
	          var longitude = result[0].x;
	          var mapContainer = document.getElementById('map');
	          var options = {
	            center: new kakao.maps.LatLng(latitude, longitude),
	            level: 4
	          };
	          map = new kakao.maps.Map(mapContainer, options);
	          
	      	   // 사용자 주소를 기반으로 주변 마트 검색 및 마커 표시
              showMartLocationsAroundUser(latitude, longitude);
	        }
	      });
	    }
	  }
	function showMartLocationsAroundUser(latitude, longitude) {
		// 이전에 표시된 마커들을 모두 제거 (초기화)
		removeAllMarkers();
		
		// 사용자 위치를 중심으로 주변 마트 검색을 위한 장소 검색 서비스 객체 생성
		  var placesService = new kakao.maps.services.Places();

		  // 주변 마트 검색 요청을 위한 장소 검색 객체 생성
		  var request = {
		    location: new kakao.maps.LatLng(latitude, longitude), // 사용자 위치
		    radius: 3000, // 검색 반경 (미터 단위, 여기서는 3km)
		    keyword: '마트' // 검색 키워드 (이 부분은 마트에 해당하는 키워드로 수정)
		  };

		  // 주변 마트 검색 요청
		  placesService.keywordSearch(request, function (result, status) {
		    if (status === kakao.maps.services.Status.OK) {
		      // 검색 결과가 있을 경우, 얻어온 마트 정보를 지도에 표시
		      for (var i = 0; i < result.length; i++) {
		        var place = result[i];
		        var marker = new kakao.maps.Marker({
		          position: new kakao.maps.LatLng(place.y, place.x), // 마트 위치
		          map: map // 표시할 지도 객체
		        });
		        markers.push(marker);
		      }
		    }
		  });
		}
	
	// 검색 폼의 입력값을 이용하여 장소 검색
	  function searchPlace() {
	    var keyword = document.getElementById('searchInput').value;
	    			

	    // 장소 검색 서비스 객체 생성
	    var placesService = new kakao.maps.services.Places();

	    // 장소 검색 요청
	    placesService.keywordSearch(keyword, function(result, status) {
	      if (status === kakao.maps.services.Status.OK) {
	        // 검색 결과가 있을 경우, 얻어온 장소의 위치를 지도에 표시
	        var place = result[0];
	        var latitude = place.y;
	        var longitude = place.x;
	        

	        // 검색된 장소로 지도 이동
	        map.setCenter(new kakao.maps.LatLng(latitude, longitude));

	        // 이전에 표시된 마커들을 모두 제거
	        removeAllMarkers();

	        // 검색된 장소에 마커 표시
	        var marker = new kakao.maps.Marker({
	          position: new kakao.maps.LatLng(latitude, longitude),
	          map: map
	        });
	        markers.push(marker); // 마커 배열에 추가
	      }
	    });
	  }
	
	// 이전에 표시된 마커들을 모두 제거하는 함수
	  function removeAllMarkers() {
	    for (var i = 0; i < markers.length; i++) {
	      markers[i].setMap(null);
	    }
	    markers = [];
	  }

	  var markers = []; // 마커들을 담을 배열	
	
  });  
	  
	/*KAKAO MAP API END*/
	
	// Edamam API 호출 함수
  function getRecipeData() {
    var xhr = new XMLHttpRequest();
    // Edamam API URL 및 API Key 설정
    var apiURL = 'https://api.edamam.com/api/recipes/v2?type=public';
    var appId = '7d8f664a';
    var apiKey = 'a6f332294d947d0141a2d499a7ac1688';
    var requestURL = apiURL + '?app_id=7d8f664a' + 
    		appId + '&app_key=a6f332294d947d0141a2d499a7ac1688' + apiKey;
  	
    xhr.open('GET', requestURL, true);

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        var response = JSON.parse(xhr.responseText);
        var recipeData = response.hits[0].recipe; // 첫 번째 레시피 정보 가져오기

        // 재료 목록 추출
        var ingredients = recipeData.ingredientLines;

        // 양념 목록 추출
        var seasoning = recipeData.dietLabels;

        // JSP 페이지에 데이터 전달
        var ingredientList = document.getElementById('ingredientList');
        var seasoningList = document.getElementById('seasoningList');
        
     // 기존 목록을 초기화
        ingredientList.innerHTML = '';
        seasoningList.innerHTML = '';

        ingredients.forEach(function (ingredient) {
          var li = document.createElement('li');
          li.textContent = ingredient;
          ingredientList.appendChild(li);
        });

        seasoning.forEach(function (seasoning) {
          var li = document.createElement('li');
          li.textContent = seasoning;
          seasoningList.appendChild(li);
        });
      }
    };

    xhr.send();
  }

  // 페이지 로드 시 Edamam API 호출
  document.addEventListener('DOMContentLoaded', function () {
    getRecipeData();
  });
    

    // 버튼 요소를 가져옵니다.
    var homeButton = document.getElementById("home");

    // 버튼을 클릭할 때 실행할 함수를 정의합니다.
    homeButton.onclick = function() {
      // 메인 페이지로 리다이렉트합니다.
      window.location.href = "/index"; // 메인 페이지의 파일 이름에 맞게 수정해주세요.
    };
  </script>


</body>
</html>



<c:import url="/footer" />