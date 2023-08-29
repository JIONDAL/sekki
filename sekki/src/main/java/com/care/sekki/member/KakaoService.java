package com.care.sekki.member;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoService {
	private String accessToken;
	private String scope;

	public void getAccessToken(String code) {
		/*
		 * # 액세스 토큰 가져오기 #
		 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
		 * -sample
		 */
		String redirectUri = "http://localhost/kakaoLogin";
		String reqUrl = "https://kauth.kakao.com/oauth/token";
		String reqParam = "grant_type=authorization_code";
		reqParam += "&client_id=2e151bc9c1174ea0e8836b77c28803c3";
		reqParam += "&redirect_uri="+redirectUri;
		reqParam += "&code=" + code;

		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST"); // POST 요청을 위해 기본값 false에서 setDoOutput을 true로 변경
			conn.setDoOutput(true); // POST 메소드를 이용해서 데이터를 전달하기 위한 설정

			// 기본 outputStream을 통해 문자열로 처리할 수 있는 OutPutStreamWriter 변환 후 처리속도를 빠르게 하기위한
			// BufferedWriter로 변환해서 사용한다.
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(reqParam);
			bw.flush();

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
//			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			ObjectMapper om = new ObjectMapper();
			Map<String, String> map = null;
			map = om.readValue(isr, new TypeReference<Map<String, String>>() {
			});
			accessToken = map.get("access_token");
			scope = map.get("scope");

			//setNeedsAgreement();
//			System.out.println("access_token : " + map.get("access_token"));
//			System.out.println("scope : " + map.get("scope"));

			/*
			 * response body : {
			 * "access_token":"W-_xD-t7fIv78Lzz06tCZyrlGDlYcR3kVWmxo_t0Cj11nAAAAYlm52VZ",
			 * "token_type":"bearer",
			 * "refresh_token":"3Llflzbp_vCUMMFss78twrO3G05MmHikDxZ6c8GbCj11nAAAAYlm52VY",
			 * "expires_in":21599,
			 * "scope":"age_range account_email profile_image gender profile_nickname",
			 * "refresh_token_expires_in":5183999 }
			 */

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setNeedsAgreement() {
	/*
	 * # 추가 항목 동의 받기 #
	 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code-additional-consent
	 */
	//https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=account_email,gender	

		String redirectUri = "http://localhost/kakaoLogin";
		String reqUrl = "https://kauth.kakao.com/oauth/authorize";
		String reqParam = "?client_id=2e151bc9c1174ea0e8836b77c28803c3";
		reqParam += "&redirect_uri="+redirectUri;
		reqParam += "&response_type=code&scope="+scope;

		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(reqParam);
			bw.flush();

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
			System.out.println("responseCode : " + responseCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getUserInfo() {
		/*
		 * 사용자 정보 가져오기
		 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
		 */

		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); // Authorization: Bearer ${ACCESS_TOKEN}

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
//			System.out.println("responseCode : " + responseCode);

			ObjectMapper om = new ObjectMapper();
			JsonNode jsonTree = om.readTree(conn.getInputStream());

			System.out.println("jsonTree : " + jsonTree);
			System.out.println("kakao_account : " + jsonTree.get("kakao_account"));

			JsonNode kakaoAccount = jsonTree.get("kakao_account");
			System.out.println("profile : " + kakaoAccount.get("profile"));
			System.out.println("email : " + kakaoAccount.get("email"));
			System.out.println("age_range : " + kakaoAccount.get("age_range"));
			System.out.println("gender : " + kakaoAccount.get("gender"));

			System.out.println("profile : " + kakaoAccount.get("profile"));
			System.out.println("nickname : " + kakaoAccount.get("profile").get("nickname"));
			System.out.println(kakaoAccount.get("profile").get("nickname").textValue());

			/*
			 {"id":2916902118,"connected_at":"2023-07-18T02:28:09Z","properties":{"nickname":"김연수","profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile_image_needs_agreement":false,"profile":{"nickname":"김연수","thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg","profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","is_default_image":true},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"kyes0222@gmail.com","has_age_range":true,"age_range_needs_agreement":false,"age_range":"30~39","has_gender":true,"gender_needs_agreement":false,"gender":"male"}}
			 {"id":2916902118,"connected_at":"2023-07-18T02:28:09Z","properties":{"nickname":"김연수","profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile_image_needs_agreement":false,"profile":{"nickname":"김연수","thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg","profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","is_default_image":true},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"kyes0222@gmail.com","has_age_range":true,"age_range_needs_agreement":false,"age_range":"30~39","has_gender":true,"gender_needs_agreement":false,"gender":"male"}}
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void unLink() {
		/*
		 * # 연결 끊기 #
		 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#unlink
		 */
		String reqUrl = "https://kapi.kakao.com/v1/user/unlink";
		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); 
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); // Authorization: Bearer ${ACCESS_TOKEN}

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
			System.out.println("responseCode : " + responseCode);

			ObjectMapper om = new ObjectMapper();
			JsonNode jsonNode = om.readTree(conn.getInputStream());
			System.out.println("id : " + jsonNode.get("id"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void get() {
		/*
		 * 사용자 정보 가져오기
		 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
		 */

		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		HttpURLConnection conn;
		try {
			URL url = new URL(reqUrl); // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); // Authorization: Bearer ${ACCESS_TOKEN}

			int responseCode = conn.getResponseCode(); // 결과 코드가 200이라면 성공
//			System.out.println("responseCode : " + responseCode);

			ObjectMapper om = new ObjectMapper();
			JsonNode jsonTree = om.readTree(conn.getInputStream());

			System.out.println("jsonTree : " + jsonTree);
			System.out.println("kakao_account : " + jsonTree.get("kakao_account"));

			JsonNode kakaoAccount = jsonTree.get("kakao_account");
			System.out.println("profile : " + kakaoAccount.get("profile"));
			System.out.println("email : " + kakaoAccount.get("email"));
			System.out.println("age_range : " + kakaoAccount.get("age_range"));
			System.out.println("gender : " + kakaoAccount.get("gender"));

			System.out.println("profile : " + kakaoAccount.get("profile"));
			System.out.println("nickname : " + kakaoAccount.get("profile").get("nickname"));
			System.out.println(kakaoAccount.get("profile").get("nickname").textValue());

			/*
			 {"id":2916902118,"connected_at":"2023-07-18T02:28:09Z","properties":{"nickname":"김연수","profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile_image_needs_agreement":false,"profile":{"nickname":"김연수","thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg","profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","is_default_image":true},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"kyes0222@gmail.com","has_age_range":true,"age_range_needs_agreement":false,"age_range":"30~39","has_gender":true,"gender_needs_agreement":false,"gender":"male"}}
			 {"id":2916902118,"connected_at":"2023-07-18T02:28:09Z","properties":{"nickname":"김연수","profile_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","thumbnail_image":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile_image_needs_agreement":false,"profile":{"nickname":"김연수","thumbnail_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg","profile_image_url":"http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg","is_default_image":true},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"kyes0222@gmail.com","has_age_range":true,"age_range_needs_agreement":false,"age_range":"30~39","has_gender":true,"gender_needs_agreement":false,"gender":"male"}}
			 */
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}








