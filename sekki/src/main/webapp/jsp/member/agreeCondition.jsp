<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>약관동의</title>
<link href="css/member.css" rel="stylesheet" />
<script src="/js/member.js"></script>
</head>
<body>
	<c:import url="/header"></c:import>
	<div id="visual">
		<h1>회원가입</h1>
	</div>
	<div id="wrap">
		<div id="memberMenu">
			<ul>
				<a href="login"><li>로그인</li></a>
				<a href="login"><li>로그인</li></a>
				<a href="agreeCondition" class="selected"><li>회원가입</li></a>
				<a href="findId"><li>아이디 찾기</li></a>
				<a href="findPw"><li>비밀번호 찾기</li></a>
			</ul>
		</div>
		<form action="register" class="agreeCondition" method="post" id="f"
			enctype="multipart/form-data">
			<table class="agreeConditionTable">
				<thead>
					<tr>
						<th class="checkbox-container"><input type="checkbox"
							id="allAgree"> <label for="allAgree"> 아래 약관을 모두
								읽었으며 이에 동의합니다.</label></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="checkbox-container"><input type="checkbox"
							id="essentialAgree"> (필수)개인정보수집 및 이용동의</td>
						</td>
					<tr>
					<tr>
						<td><textarea rows="10" cols="50">(주)자취세끼(이하 ‘회사’라 한다)는 개인정보 보호법 제30조에 따라 회사의 서비스를 이용하는 회원(이하 ‘이용자’라 한다) 의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리지침을 수립·공개합니다.
1. 총칙
2. 수집하는 개인정보의 항목 및 수집방법
3. 개인정보 수집에 대한 동의
4. 개인정보의 수집목적 및 이용목적
5. 쿠키에 의한 개인정보 수집
6. 개인정보 제공
7. 개인정보의 열람·정정
8. 개인정보 수집, 이용, 제공에 대한 동의철회
9. 개인정보의 보유기간 및 이용기간, 파기절차, 파기방법
10. 개인정보보호를 위한 기술 및 관리적 대책
11. 링크사이트
12. 게시물
13. 개인정보의 위탁처리
14. 형태정보 제공에 대한 메체사 고지
15. 이용자의 권리와 의무 및 법정대리인의 권리·의무 및 행사방법
16. 개인정보관리책임자 및 담당자
17. 광고성 정보전송
18. 정책 변경에 따른 고지의무
1. 처리하는 개인정보 항목
- 필수항목 : 휴대폰번호, 이메일

2. 개인정보의 처리 목적
이벤트 운영 및 광고성 정보 전송, 서비스 관련 정보 전송의 목적으로 개인정보를 처리합니다.

3. 개인정보의 보유 및 이용기간
이용자가 동의를 철회하거나, 회원 탈퇴시까지 보유·이용

4. 개인정보 처리 및 이용 동의를 거부할 권리
위와 같은 개인정보 처리에 동의를 거부할 권리가 있습니다. 그러나 동의를 거부할 경우 각종 소식 및 이벤트 참여가 제한될 수 있습니다.
				</textarea>
					<tr>
					<tr>
						<td class="checkbox-container"><input type="checkbox"
							id="optionalAgree"> (선택) 수집한 개인정보의 제 3자 정보제공 동의</td>
						</td>
					<tr>
					<tr>
						<td><textarea rows="5" cols="150">제1조(목적)
이 약관은 "(주)자취세끼"가 운영하는 sekki 온라인 서비스(이하 “사이트”이라 한다)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버 사이트과 이용자의 권리?의무 및 책임사항을 규정함을 목적으로 합니다.
※ PC통신, 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는 한 이 약관을 준용합니다


제2조(정의)
1. “사이트” 이란 "sekki"이 재화 또는 용역(이하 “재화등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터등 정보통신설비를 이용하여 재화등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버사이트을 운영하는 사업자의 의미로도 사용합니다.
2. “이용자”란 “사이트”에 접속하여 이 약관에 따라 “사이트”이 제공하는 서비스를 받는 회원 및 비회원을 말합니다.
3. ‘회원’이라 함은 “사이트”에 개인정보를 제공하여 회원등록을 한 자로서, “사이트”의 정보를 지속적으로 제공받으며, “사이트”이 제공하는 서비스를 계속적으로 이용할 수 있는 자를 말합니다.
4. ‘비회원’이라 함은 회원에 가입하지 않고 “사이트”이 제공하는 서비스를 이용하는 자를 말합니다.

제3조 (약관등의 명시와 설명 및 개정) 
1. “사이트”은 이 약관의 내용과 상호 및 대표자 성명, 영업소 소재지 주소(소비자의 불만을 처리할 수 있는 곳의 주소를 포함), 전화번호,모사전송번호,전자우편주소, 사업자등록번호, 통신판매업신고번호, 개인정보관리책임자등을 이용자가 쉽게 알 수 있도록 "sekki" 사이버사이트의 초기 서비스화면(전면)에 게시합니다. 다만, 약관의 내용은 이용자가 연결화면을 통하여 볼 수 있도록 할 수 있습니다.

2. “사이트은 이용자가 약관에 동의하기에 앞서 약관에 정하여져 있는 내용 중 청약철회, 배송책임, 환불조건 등과 같은 중요한 내용을 이용자가 이해할 수 있도록 별도의 연결화면 또는 팝업화면 등을 제공하여 이용자의 확인을 구하여야 합니다.

3. “사이트”은 전자상거래등에서의소비자보호에관한법률, 약관의규제에관한법률, 전자거래기본법, 전자서명법, 정보통신망이용촉진등에관한법률, 방문판매등에관한법률, 소비자보호법 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.

4. “사이트”이 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 사이트의 초기화면에 그 적용일자 7일이전부터 적용일자 전일까지 공지합니다.
다만, 이용자에게 불리하게 약관내용을 변경하는 경우에는 최소한 30일 이상의 사전 유예기간을 두고 공지합니다. 이 경우 "사이트“은 개정전 내용과 개정후 내용을 명확하게 비교하여 이용자가 알기 쉽도록 표시합니다. 

5. “사이트”이 약관을 개정할 경우에는 그 개정약관은 그 적용일자 이후에 체결되는 계약에만 적용되고 그 이전에 이미 체결된 계약에 대해서는 개정전의 약관조항이 그대로 적용됩니다. 다만 이미 계약을 체결한 이용자가 개정약관 조항의 적용을 받기를 원하는 뜻을 제3항에 의한 개정약관의 공지기간내에 ‘사이트“에 송신하여 ”사이트“의 동의를 받은 경우에는 개정약관 조항이 적용됩니다.

6. 이 약관에서 정하지 아니한 사항과 이 약관의 해석에 관하여는 전자상거래등에서의소비자보호에관한법률, 약관의규제등에관한법률, 공정거래위원회가 정하는 전자상거래등에서의소비자보호지침 및 관계법령 또는 상관례에 따릅니다.

제4조(서비스의 제공 및 변경) 
1. “사이트”은 다음과 같은 업무를 수행합니다.
① 재화 또는 용역에 대한 정보 제공 및 구매계약의 체결
② 구매계약이 체결된 재화 또는 용역의 배송
③ 기타 “사이트”이 정하는 업무

2. “사이트”은 재화 또는 용역의 품절 또는 기술적 사양의 변경 등의 경우에는 장차 체결되는 계약에 의해 제공할 재화 또는 용역의 내용을 변경할 수 있습니다. 이 경우에는 변경된 재화 또는 용역의 내용 및 제공일자를 명시하여 현재의 재화 또는 용역의 내용을 게시한 곳에 즉시 공지합니다.

3. “사이트”이 제공하기로 이용자와 계약을 체결한 서비스의 내용을 재화등의 품절 또는 기술적 사양의 변경 등의 사유로 변경할 경우에는 그 사유를 이용자에게 통지 가능한 주소로 즉시 통지합니다.

4. 전항의 경우 “사이트”은 이로 인하여 이용자가 입은 손해를 배상합니다. 다만, “사이트”이 고의 또는 과실이 없음을 입증하는 경우에는 그러하지 아니합니다.</textarea>
					<tr>
				</tbody>
			</table>

			<div class="h80"></div>

			<h1>본인인증</h1>
			<table class="authenticationTable">
				<colgroup>
					<col width="50%"></col>
					<col width="*"></col>
				</colgroup>
				<thead>
					<tr>
						<th id="emailAuth" class="selected" onclick="emailAuthClick()">이메일
							인증</th>
						<th id="phoneAuth" onclick="phoneAuthClick()">휴대전화 인증</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="2" style="padding-top: 20px;"><input type="text"
							id="sendTo" name="sendTo" placeholder="이메일 입력"> <input
							type="button" id="sendAuthenticationNumBtn" value="인증번호 전송"
							onclick="sendAuthenticationNumEmail()"></td>
					</tr>
					<tr>
						<td colspan="2" class="checkAuthenticationTd"><label
							id="checkAuthenticationLabel"></label></td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="authenticationNum"
							name="authenticationNum" placeholder="인증번호 6자리 입력" maxlength="6">
							<input type="button" id="authenticationNumBtn" value="인증하기"
							onclick="checkAuthenticationNumEmail()"></td>
					</tr>
					<tr>
						<td colspan="2" class="checkAuthenticationTd"><label
							id="checkAuthenticationNumLabel"></label></td>
					</tr>
				</tbody>
			</table>

			<div class="h80"></div>
			<div class="nextStageDiv" id="nextStageDiv">
				<input type="button" id="nextStageBtn" name="nextStageBtn"
					value="다음 단계 >" onclick="agreeConditionCheck()">
			</div>
		</form>
	</div>

	<c:import url="/footer" />
</body>
</html>