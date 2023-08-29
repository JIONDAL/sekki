package com.care.sekki.Recipe;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class RecipeBoardDTO {
	private Long re_no;
	private String title;
	private String content;
	private String id;
	private String profile;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	private String category;
	private String cuisine;

	private String times;
	private String degree;
	private Timestamp written_time;

	private String tip;
	private MultipartFile mainphotoUrl;
	private String mainphoto;

	private int views;
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getStar_rating() {
		return star_rating;
	}

	public void setStar_rating(int star_rating) {
		this.star_rating = star_rating;
	}

	private int star_rating;

	private List<MaterialDTO> mararials;
	private List<StepDTO> steps;

	
	

	
	public List<MaterialDTO> getMararials() {
		return mararials;
	}

	public void setMararials(List<MaterialDTO> mararials) {
		this.mararials = mararials;
	}

	public List<StepDTO> getSteps() {
		return steps;
	}

	public void setSteps(List<StepDTO> steps) {
		this.steps = steps;
	}



	public Long getRe_no() {
		return re_no;
	}



	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}


	public MultipartFile getMainphotoUrl() {
		return mainphotoUrl;
	}

	public void setMainphotoUrl(MultipartFile mainphotoUrl) {
		this.mainphotoUrl = mainphotoUrl;
	}

	public String getMainphoto() {
		return mainphoto;
	}

	public void setMainphoto(String mainphoto) {
		this.mainphoto = mainphoto;
	}

	public Timestamp getWritten_time() {
		return written_time;
	}

	public void setWritten_time(Timestamp written_time) {
		this.written_time = written_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



}