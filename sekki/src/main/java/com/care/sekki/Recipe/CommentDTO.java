package com.care.sekki.Recipe;

import java.sql.Timestamp;
import java.util.List;

public class CommentDTO {
	private Long comment_no;
	private List<String> starTags;
	public List<String> getStarTags() {
		return starTags;
	}
	public void setStarTags(List<String> starTags) {
		this.starTags = starTags;
	}
	private Long re_no;
	private String id;
	private String comment_content;
	private float rating;
	private Timestamp written_time;
	private String profile;
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Long getcomment_no() {
		return comment_no;
	}
	public void setcomment_no(Long comment_no) {
		this.comment_no = comment_no;
	}

	public Long getRe_no() {
		return re_no;
	}
	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public Timestamp getWritten_time() {
		return written_time;
	}
	public void setWritten_time(Timestamp written_time) {
		this.written_time = written_time;
	}
	
}
