package com.care.sekki.customerCenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface centerMapper {
	//공통 
	int count(@Param("search")String search, String category);
	
	void incHit(int num);
	
	void incReplys(int center_num);
	
	void decReplys(int center_num);
	
	centerDTO centerContent(int num);
	
	ArrayList<centerReplyDTO> replys(@Param("center_num") int center_num, 
			@Param("category")String category);
	
	String fileDownload(int num);
	
	void deleteCenterProc(int num);
	
	void deleteReply(int replyNum);
	
	//공지사항 
	void writeAnnouncementProc(centerDTO announceDTO);

	ArrayList<centerDTO> partAnnouncement(
			@Param("begin")int begin, @Param("end")int end,
			@Param("search")String search, @Param("category")String category);

	ArrayList<centerDTO> allAnnouncement(@Param("begin")int begin, 
			@Param("end")int end, @Param("category")String category);

	void modifyAnnouncementProc(centerDTO announcement);

	int likespersonCheck(@Param("user_id")String user_id, 
			@Param("center_num")int center_num, @Param("category")String category);

	void setHeart(centerDTO announcement);
	
	void likesPersonSave(@Param("user_id") String user_id, 
			@Param("center_num") int center_num, @Param("category")String category);
	void likesPersonDelete(@Param("user_id") String user_id, 
			@Param("center_num") int center_num, @Param("category")String category);
	
	List<centerReplyDTO> myReplyOnly(@Param("center_num")int center_num, @Param("writer")String writer, 
			@Param("category")String category);

	void writeAnnouncementReply(centerReplyDTO replyDTO);

	List<centerReplyDTO> announcementReplyModifyClick(int reply_num);

	void announcementReplyModify(centerReplyDTO modifyReply);
	
	
	//문의하기
	ArrayList<centerDTO> allInquiry(@Param("begin")int begin, 
			@Param("end")int end, @Param("category")String category);

	ArrayList<centerDTO> partInquiry(@Param("begin")int begin, 
			@Param("end")int end, @Param("search")String search, @Param("category")String category);

	void writeInquiryProc(centerDTO inquiry);

	centerDTO inquirySecretCheck(int num, @Param("category")String category);

	void adminReplyProc(centerReplyDTO adminReply);

	int modifyAble(int centerNum);

	void modifyinquiryProc(centerDTO inquiry);

	centerReplyDTO replyContent(int reply_num);

	void modifyAdminReply(centerReplyDTO adminReply);

	

	

	


	




}
