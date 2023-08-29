package com.care.sekki.customerCenter;

/*
CREATE TABLE sekki_center_board (
	    num         NUMBER,
	    category    VARCHAR2(30),
	    title       VARCHAR2(100),
	    content     VARCHAR2(3000),
	    writer      VARCHAR2(30),
	    write_date  VARCHAR2(50),
	    write_time  VARCHAR2(50),
	    files       VARCHAR2(1000),
	    secret	    VARCHAR2(20),
	    secret_pw	VARCHAR2(50),
	    hits        NUMBER,
	    likes       NUMBER,
	    replys       NUMBER,
	    PRIMARY KEY(num)
  );
 */

public class centerDTO {
	private int num;
	private String category;
	private String title;
	private String content;
	private String writer;
	private String writeTime;
	private String writeDate;
	private String files;
	private String secret;
	private String secretPw;
	private int hits;
	private int likes;
	private int replys;


	public centerDTO() {}
	
	public centerDTO(centerDTO other) {
		this.num = other.num;
		this.category = other.category;
        this.title = other.title;
        this.content = other.content;
        this.writer = other.writer;
        this.writeTime = other.writeTime;
        this.files = other.files;
        this.secret = other.secret;
        this.hits = other.hits;
        this.likes = other.likes;
    }
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getWriteTime() {
		return writeTime;
	}
	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSecretPw() {
		return secretPw;
	}

	public void setSecretPw(String secretPw) {
		this.secretPw = secretPw;
	}
	
	public int getReplys() {
		return replys;
	}

	public void setReplys(int replys) {
		this.replys = replys;
	}
	
}
