package com.care.sekki.member;

public class MemberDTO {
	private String id;
	private String pw;
	private String userName;
	private String email;
	private String emailSelect;
	private String address;
	private String mobile;
	private String profilePictureUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailSelect() {
		return emailSelect;
	}

	public void setEmailSelect(String emailSelect) {
		this.emailSelect = emailSelect;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	/*
	 * private String height; private String weight;
	 * 
	 * public String getHeight() { return height; } public void setHeight(String
	 * height) { this.height = height; } public String getWeight() { return weight;
	 * } public void setWeight(String weight) { this.weight = weight; }
	 */
}
