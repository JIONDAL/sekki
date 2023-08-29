package com.care.sekki.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.care.sekki.S3.S3UploadService;
import com.care.sekki.common.PageService;

import jakarta.servlet.http.HttpSession;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private S3UploadService s3UploadService;

	/* 로그인======================================================= */
	public String loginProc(MemberDTO member) {
		if (member.getId() == null || member.getId().isEmpty()) {
			return "아이디를 입력하세요.";
		}

		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		MemberDTO result = memberMapper.loginProc(member.getId());
		if (result != null) {
			BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
			if (bpe.matches(member.getPw(), result.getPw())) {
				session.setAttribute("id", result.getId());
				session.setAttribute("userName", result.getUserName());
				session.setAttribute("address", result.getAddress());
				session.setAttribute("mobile", result.getMobile());
				session.setAttribute("profilePictureUrl", result.getProfilePictureUrl());
				session.setAttribute("email", result.getEmail());
				// session.setAttribute("height", result.getHeight());
				// session.setAttribute("weight", result.getWeight());
				return "로그인 성공";
			}
		}

		return "아이디/비밀번호를 확인 후 다시 시도하세요.";
	}

	/* 약관동의&본인인증======================================================= */

	private String auth;
	@Autowired
	private MailService mailService;

	public String sendAuthenticationNumEmail(String sendTo) {
		if (sendTo == null || sendTo.isEmpty())
			return "* 이메일 주소를 입력하세요.";

		Random r = new Random();
		auth = String.format("%06d", r.nextInt(1000000));

		String msg = mailService.sendAuthenticationNumEmail(sendTo, "(자취세끼) 회원가입 본인인증",
				"(자취세끼) 가입을 환영합니다. 인증번호 6자리 [" + auth + "] 를 입력해주세요.");
		if (msg.equals("* 입력하신 이메일 주소로 인증번호를 전송했습니다.") == false) {
			auth = "";
		}

		return msg;
	}

	public String checkAuthenticationNumEmail(String authenticationNum) {
		if (auth == null || auth.isEmpty())
			return "* 이메일로 인증번호가 전송되지 않았습니다.";

		if (authenticationNum == null || authenticationNum.isEmpty())
			return "* 인증번호를 입력하세요.";

		if (authenticationNum.equals(auth)) {
			return "* 인증 성공. 다음 단계로 이동하세요.";
		}

		return "* 인증번호가 틀립니다.";
	}

	@Autowired
	private SmsService smsService;
	String result;

	public String sendAuthenticationNumSms(String sendTo) {
		if (sendTo == null || sendTo.isEmpty())
			return "* 휴대폰 번호를 입력하세요.";

		Random r = new Random();
		auth = String.format("%06d", r.nextInt(1000000));

		result = smsService.sendAuthenticationNumSms(sendTo, auth);
		return result;
	}

	public String checkAuthenticationNumSms(String authenticationNum) {
		if (result.equals("* 인증번호 전송을 실패했습니다."))
			return "* 휴대폰으로 인증번호가 전송되지 않았습니다.";

		if (authenticationNum == null || authenticationNum.isEmpty())
			return "* 인증번호를 입력하세요.";

		if (authenticationNum.equals(auth)) {
			return "* 인증 성공. 다음 단계로 이동하세요.";
		}

		return "* 인증번호가 틀립니다.";
	}

	/* 회원가입======================================================= */
	public String regIdCheck(String id) {
		if (id == null || id.isEmpty())
			return "* 아이디를 입력하세요.";

		Pattern pattern = Pattern.compile("^[a-z0-9]{1}[a-z0-9_-]{4,19}$");
		Matcher matcher = pattern.matcher(id);
		if (matcher.matches() == false)
			return "* 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";

		String user_id = memberMapper.regIdCheck(id);
		if (user_id == null)
			return "";

		return "* 중복되는 아이디 입니다. 다른 아이디를 입력하세요.";
	}

	public String regPwCheck(String pw) {
		if (pw == null || pw.isEmpty())
			return "* 비빌번호를 입력하세요.";

		Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");
		Matcher matcher = pattern.matcher(pw);
		if (matcher.matches() == false)
			return "* 영문, 숫자, 특수문자 세 가지를 모두 포함 (8~15자)";

		return "";
	}

	public String regConfirmCheck(String pw) {
		if (pw == null || pw.isEmpty())
			return "* 비빌번호를 입력하세요.";

		Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$");
		Matcher matcher = pattern.matcher(pw);
		if (matcher.matches() == false)
			return "* 영문, 숫자, 특수문자 세 가지를 모두 포함 (8~15자)";

		return "";
	}

	public String regUserNameCheck(String userName) {
		if (userName == null || userName.isEmpty())
			return "* 이름을 입력하세요.";

		if (userName.length() < 2) {
			return "* 이름은 두 글자 이상 입력하세요.";
		}

		Pattern pattern = Pattern.compile("^[가-힣a-zA-Z]*$");
		Matcher matcher = pattern.matcher(userName);
		if (matcher.matches() == false)
			return "* 이름은 숫자와 특수문자를 포함할 수 없습니다.";

		return "";
	}

	public String regMobileCheck(String mobile) {
		if (mobile == null || mobile.isEmpty())
			return "* 전화번호를 입력하세요.";

		Pattern pattern = Pattern.compile("^(01[016789])([1-9]\\d{2,3})(\\d{4})$");
		Matcher matcher = pattern.matcher(mobile);
		if (matcher.matches() == false) {
			return "* 유효하지 않은 번호입니다. (-)없이 입력하세요.";
		}

		return "";
	}

	public String regEmailCheck(String email) {
		if (email == null || email.isEmpty())
			return "* 이메일을 입력하세요.";

		return "* 메일주소를 선택하세요";
	}

	public String regEmailSelectCheck(String emailSelectedOption) {
		if (emailSelectedOption == null || emailSelectedOption.isEmpty())
			return "* 메일주소를 선택하세요";

		if (emailSelectedOption.equals("@ 선택"))
			return "* 메일주소를 선택하세요";

		return "";
	}

	public String registerProc(MemberDTO member, MultipartFile profilePicture) {
		String memberId = member.getId();

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);

		member.setEmail(member.getEmail() + member.getEmailSelect());
		try {
			// 프로필 사진을 S3에 업로드하고 해당 URL을 받아옵니다.
			if (!profilePicture.isEmpty()) {
				// 프로필 사진을 S3에 업로드하고 해당 URL을 받아옵니다.
				System.out.println("사진 뜨냐  :  " + profilePicture);
				String profilePictureUrl = s3UploadService.saveFile(profilePicture, memberId);
				member.setProfilePictureUrl(profilePictureUrl); // 회원 정보에 프로필 사진 URL을 설정합니다.
			} else {
				String standardProfile = "/image/standardProfile.png";
				// String profilePictureUrl = s3UploadService.saveFile(profilePicture,
				// memberId);
				member.setProfilePictureUrl(standardProfile);
			}
			memberMapper.registerProc(member);
			return "회원 가입 완료";
		} catch (IOException e) {
			e.printStackTrace();
			return "회원 등록 실패: 프로필 사진 업로드 중 오류가 발생하였습니다.";
		}
	}

	public String findIdByMobile(String userName, String mobile) {
		return memberMapper.findIdByMobile(userName, mobile);
	}

	public String findIdByEmail(String userName, String emailInput, String emailSelect) {
		String email = emailInput + emailSelect;
		return memberMapper.findIdByEmail(userName, email);
	}

	public String findPwByMobile(String id) {
		if (id == null || id.isEmpty())
			return "아이디 미입력";
		MemberDTO member = memberMapper.loginProc(id);
		String phoneNumber = member.getMobile();

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String str = "";

		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}

		String result = smsService.sendTempPwSms(phoneNumber, str);

		if (result.equals("* 입력하신 전화번호로 임시비밀번호를 전송했습니다.") == false) {
			return null;
		}

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(str);
		member.setPw(cryptPassword);

		memberMapper.updateToTempPw(member);
		return result;
	}

	public String findPwByEmail(String id) {
		if (id == null || id.isEmpty())
			return "아이디 미입력";

		MemberDTO member = memberMapper.loginProc(id);
		String email = member.getEmail();

		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		String str = "";

		int idx = 0;
		for (int i = 0; i < 10; i++) {
			idx = (int) (charSet.length * Math.random());
			str += charSet[idx];
		}

		String result = mailService.sendTempPwEmail(email, "(자취세끼) 임시비밀번호 발급",
				"(자취세끼) 임시비밀번호 입니다. 로그인 후 반드시 비밀번호를 변경하세요. " + "임시비밀번호 [" + str + "]");

		if (result.equals("* 입력하신 이메일 주소로 임시비밀번호를 전송했습니다.") == false) {
			return null;
		}

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(str);
		member.setPw(cryptPassword);

		memberMapper.updateToTempPw(member);
		return result;
	}

	public void memberInfo(String cp, String select, String search, Model model) {
		if (select == null) {
			select = "";
		}

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		int pageBlock = 3; // 한 페이지에 보일 데이터의 수
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		ArrayList<MemberDTO> members = memberMapper.memberInfo(begin, end, select, search);
		int totalCount = memberMapper.count(select, search);
		String url = "memberInfo?select=" + select + "&search=" + search + "&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("members", members);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	public MemberDTO userInfo(String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}

		String sessionId = (String) session.getAttribute("id");
		if (sessionId.equals(id) == false && sessionId.equals("admin") == false)
			return null;

//	MemberDTO result = memberMapper.loginProc(id);
		return memberMapper.loginProc(id);
	}

	public String updateProc(MemberDTO member, String confirm) {
		if (member.getPw() == null || member.getPw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (member.getPw().equals(confirm) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}

		if (member.getUserName() == null || member.getUserName().isEmpty()) {
			return "이름을 입력하세요.";
		}

		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		String cryptPassword = bpe.encode(member.getPw());
		member.setPw(cryptPassword);

		int result = memberMapper.updateProc(member);
		if (result == 1)
			return "회원 정보 수정 완료";
		return "회원 정보 수정 실패";
	}

	public String deleteProc(String id, String pw, String confirmPw) {
		if (pw == null || pw.isEmpty()) {
			return "비밀번호를 입력하세요.";
		}

		if (pw.equals(confirmPw) == false) {
			return "두 비밀번호를 일치하여 입력하세요.";
		}
		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
		MemberDTO member = memberMapper.loginProc(id);
		if (member != null && bpe.matches(pw, member.getPw())) {
			memberMapper.delete(id);
			return "회원 정보 삭제 완료";
		}
		return "비밀번호를 확인 후 다시 시도하세요.";
	}

	/* Edamam.api 코드 */

	private final String appId = "7d8f664a";
	private final String appKey = "a6f332294d947d0141a2d499a7ac1688";

	public List<String> getIngredients() {
		// Edamam API를 호출하여 재료 데이터를 가져옴
		List<String> ingredients = new ArrayList<>();
		try {
			// Edamam API 호출하는 로직
			String apiURL = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			// API 응답 데이터를 파싱하고 필요한 정보를 추출하여 ingredients에 추가
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// API 응답 데이터를 파싱하여 재료 목록을 추출
			JSONObject responseObject = new JSONObject(response.toString());
			JSONArray hits = responseObject.getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				JSONArray ingredientsArray = recipe.getJSONArray("ingredientLines");
				for (int j = 0; j < ingredientsArray.length(); j++) {
					ingredients.add(ingredientsArray.getString(j));
				}
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return ingredients;
	}

	public List<String> getSeasoning() {
		// Edamam API를 호출하여 양념 데이터를 가져옴
		List<String> seasoning = new ArrayList<>();
		try {
			// Edamam API 호출하는 로직
			String apiURL = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;
			URL url = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			// API 응답 데이터를 파싱하고 필요한 정보를 추출하여 seasoning에 추가
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// API 응답 데이터를 파싱하여 양념 목록을 추출
			JSONObject responseObject = new JSONObject(response.toString());
			JSONArray hits = responseObject.getJSONArray("hits");
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				JSONArray dietLabelsArray = recipe.getJSONArray("dietLabels");
				for (int j = 0; j < dietLabelsArray.length(); j++) {
					seasoning.add(dietLabelsArray.getString(j));
				}
			}

		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return seasoning;
	}

	public JSONObject getRecipeData() {

		String url = "https://api.edamam.com/api/recipes/v2?type=public&app_id=" + appId + "&app_key=" + appKey;

		try {
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();

			// API 응답 데이터를 JSON 형태로 파싱
			JSONObject responseObject = new JSONObject(responseBody);

			// 필요한 데이터 추출
			JSONArray hits = responseObject.getJSONArray("hits");
			List<String> recipeDataList = new ArrayList<>();
			for (int i = 0; i < hits.length(); i++) {
				JSONObject hit = hits.getJSONObject(i);
				JSONObject recipe = hit.getJSONObject("recipe");
				String recipeData = recipe.getString("recipeData");
				recipeDataList.add(recipeData);
			}

			// 추출한 데이터를 JSON 배열로 변환하여 반환
			return new JSONObject().put("recipeDataList", new JSONArray(recipeDataList));

		} catch (IOException | InterruptedException | JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject(); // 예외 발생 시 빈 JSONObject 반환
	}
}

/* Edamam.api 코드 */
