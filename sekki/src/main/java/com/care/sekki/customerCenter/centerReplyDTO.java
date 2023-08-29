package com.care.sekki.customerCenter;

public class centerReplyDTO {
/*
CREATE TABLE center_reply(
	reply_num NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	center_num NUMBER,
	category VARCHAR2(20),
	reply VARCHAR2(1000),
	writer VARCHAR2(20),
	write_date VARCHAR2(30),
   	primary key(reply_num),
	FOREIGN KEY (center_num)
   	REFERENCES sekki_center_board(num)
   	ON DELETE CASCADE
);
 */
	private int replyNum;
	private int centerNum;
	private String category;
	private String reply;
	private String writer;
	private String writeDate;
	
	public centerReplyDTO() {}
	
	
	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	
	public int getCenterNum() {
		return centerNum;
	}

	public void setCenterNum(int centerNum) {
		this.centerNum = centerNum;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	
	
}
