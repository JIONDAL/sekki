package com.care.sekki.customerCenter;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.velocity.runtime.directive.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.common.PageService;
import com.care.sekki.member.MemberDTO;
import com.care.sekki.member.MemberMapper;
import com.care.sekki.member.SmsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class centerService {
	@Autowired
	private centerMapper centerMapper;
	@Autowired
	private HttpSession session;
	
	//공통
	public centerDTO centerContent(String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO centerContent = centerMapper.centerContent(num);
		if (centerContent == null) {
			System.out.println("no가 널입니다.");
			return null;
		}

		centerContent.setHits(centerContent.getHits() + 1);
		incHit(centerContent.getNum());

		if (centerContent.getFiles() != null && centerContent.getFiles().isEmpty() == false) {
			String fn = centerContent.getFiles();
			String[] fileName = fn.split("-", 2);
			centerContent.setFiles(fileName[1]);
		}
		return centerContent;
	}

	public void incHit(int num) {
		centerMapper.incHit(num);
	}
	
	public boolean fileDownload(String n, HttpServletResponse res) {
		// TODO Auto-generated method stub
		int num = 0;

		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return false;
		}

		String fileName = centerMapper.fileDownload(num);
		if (fileName == null)
			return false;
		
		String location = "C:\\javas\\upload\\";
		/*String location = "/opt/tomcat/tomcat-10/webapps/upload/";*/
		File file = new File(location + fileName);

		try {
			String[] original = fileName.split("-", 2);
			res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(original[1], "UTF-8"));
			FileInputStream fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, res.getOutputStream());
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public void deleteCenterProc(String n) {

		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return;
		}

		centerDTO centerDto = centerMapper.centerContent(num);
		if (centerDto == null)
			return;

		centerMapper.deleteCenterProc(num);

		String path = "C:\\javas\\upload\\" + centerDto.getFiles();
		File file = new File(path);
		if (file.exists() == true) {
			file.delete();
		}

		return;
	}
	
	// 공지사항 등록
	public void writeAnnouncementProc(MultipartHttpServletRequest multi) {
		
		centerDTO announcement = new centerDTO();
		announcement.setCategory("announcement");
		announcement.setWriter("관리자");
		announcement.setTitle(multi.getParameter("title"));
		announcement.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		announcement.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		announcement.setWriteTime(formateTime);

		announcement.setFiles("");

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			announcement.setFiles(fileName);

			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);

			try {
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		centerMapper.writeAnnouncementProc(announcement);
	}
	
	//공지사항 목록
	public void announcement(String cp, String search, Model model) {
		if (search == null) {
			search = "";
		}

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		int pageBlock = 5;
		int end = pageBlock * currentPage;
		int begin = end - pageBlock + 1;

		String category = "announcement";
		
		ArrayList<centerDTO> announcements;
		
		if (search.isEmpty() || search.equals("")) {
			announcements = centerMapper.allAnnouncement(begin, end, category);
		} else {
			announcements = centerMapper.partAnnouncement(begin, end, search, category);
		}

		int totalCount = centerMapper.count(search, "announcement");
		
		String url = "announcement?search=" + search + "&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("announcements", announcements);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
	}

	

	public centerDTO modifyAnnouncement(String n) {
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO announcement = centerMapper.centerContent(num);
		if (announcement == null)
			return null;

		announcement.setContent(announcement.getContent().replaceAll("<br>", "\n"));

		if (announcement.getFiles() != null && announcement.getFiles().isEmpty() == false) {
			String fn = announcement.getFiles();
			String[] fileName = fn.split("-", 2);
			announcement.setFiles(fileName[1]);
		}
		
		return announcement;
	}

	public String modifyAnnouncementProc(MultipartHttpServletRequest multi, String n) {
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO announcement = centerMapper.centerContent(num);
		if (announcement == null) {
			return null;
		}

		announcement.setTitle(multi.getParameter("title"));
		announcement.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		announcement.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		announcement.setWriteTime(formateTime + "(수정됨)");

		announcement.setLikes(announcement.getLikes());

		if (announcement.getFiles() == null) {
			announcement.setFiles("");
		} else {
			announcement.setFiles(announcement.getFiles());
		}

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			System.out.println(fileName);
			// 기존 파일 삭제
			String path = "C:\\javas\\upload\\" + announcement.getFiles();
			File originFile = new File(path);
			if (originFile.exists() == true) {
				originFile.delete();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			announcement.setFiles(fileName);

			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);

			try {
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		centerMapper.modifyAnnouncementProc(announcement);
		return "게시글 수정 완료";
	}

	public int checkLikeStatus(String user_id, int center_num, String category) {
		return centerMapper.likespersonCheck(user_id, center_num, category);
	}

	public void likesPersonDelete(String user_id, int center_num, String category) {
		// TODO Auto-generated method stub
		centerDTO announcement = centerMapper.centerContent(center_num);
		announcement.setLikes(announcement.getLikes() - 1);
		centerMapper.setHeart(announcement);
		centerMapper.likesPersonDelete(user_id, center_num, category);
	}

	public void likesPersonSave(String user_id, int center_num, String category) {
		centerDTO announcement = centerMapper.centerContent(center_num);
		announcement.setLikes(announcement.getLikes() + 1);
		centerMapper.setHeart(announcement);
		centerMapper.likesPersonSave(user_id, center_num, category);
	}

	public ArrayList<centerReplyDTO> announcementReply(int center_num) {
		String category = "announcement";
		ArrayList<centerReplyDTO> announcementReplys = centerMapper.replys(center_num, category);

		return announcementReplys;
	}

	public List<centerReplyDTO> myReplyOnly(int center_num) {
		String wrtier = (String) session.getAttribute("id");
		String category = "announcement";
		List<centerReplyDTO> myReplys = centerMapper.myReplyOnly(center_num, wrtier, category);
		return myReplys;
	}

	public ArrayList<centerReplyDTO> writeAnnouncementReply(int center_num, String reply) {
		centerReplyDTO replyDTO = new centerReplyDTO();
		replyDTO.setCenterNum(center_num);
		replyDTO.setReply(reply);

		String user_id = (String) session.getAttribute("id");
		if(user_id  == null)
			return null;
		replyDTO.setWriter(user_id);

		String category = "announcement";
		replyDTO.setCategory(category);

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		replyDTO.setWriteDate(formateDate);
		
		centerMapper.writeAnnouncementReply(replyDTO);
		centerMapper.incReplys(center_num);
		
		ArrayList<centerReplyDTO> announcementReplys = centerMapper.replys(center_num, category);
		return announcementReplys;
	}

	public List<centerReplyDTO> deleteReply(int reply_num, int center_num) {
		centerMapper.deleteReply(reply_num);
		centerMapper.decReplys(center_num);
		
		String writer = (String) session.getAttribute("id");
		String category = "announcement";

		List<centerReplyDTO> announcementReplys = centerMapper.myReplyOnly(center_num, writer, category);
		return announcementReplys;
	}

	public List<centerReplyDTO> announcementReplyModifyClick(int reply_num, int center_num) {
		return centerMapper.announcementReplyModifyClick(reply_num);
	}

	public centerReplyDTO announcementReplyModify(int reply_num, int center_num, String reply) {
		centerReplyDTO announcementReply = centerMapper.replyContent(reply_num);
		if (announcementReply == null) {
			return null;
		}
		
		announcementReply.setReplyNum(reply_num);
		announcementReply.setCenterNum(center_num);
		announcementReply.setReply(reply.replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		announcementReply.setWriteDate(formateDate + "(수정됨)");
		
		centerMapper.modifyAdminReply(announcementReply);
		announcementReply.setReply(announcementReply.getReply().replaceAll("<br>", "\n"));
		return announcementReply;
	}
	
	/* 문의하기 */
	public void inquiry(String cp, String search, Model model) {
		if (search == null) {
			search = "";
		}

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(cp);
		} catch (Exception e) {
			currentPage = 1;
		}

		int pageBlock = 5;
		int end = pageBlock * currentPage;
		int begin = end - pageBlock + 1;

		String category = "inquiry";
		
		ArrayList<centerDTO> inquirys;
		
		if (search.isEmpty() || search.equals("")) {
			inquirys = centerMapper.allInquiry(begin, end, category);
		} else {
			inquirys = centerMapper.partInquiry(begin, end, search, category);
		}
		
		int totalCount = centerMapper.count(search, "inquiry");
		
		String url = "inquiry?search=" + search + "&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("inquirys", inquirys);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
		
 	}

	public void writeInquiryProc(MultipartHttpServletRequest multi) {

		centerDTO inquiry = new centerDTO();
		inquiry.setCategory("inquiry");
		inquiry.setWriter((String) session.getAttribute("id"));
		inquiry.setTitle(multi.getParameter("title"));
		inquiry.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		inquiry.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		inquiry.setWriteTime(formateTime);

		inquiry.setFiles("");

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			inquiry.setFiles(fileName);

			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);

			try {
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String secretCheckboxValue = multi.getParameter("secretCheckbox");
		if (secretCheckboxValue != null && secretCheckboxValue.equals("secretChecked")) {
			inquiry.setSecret("비밀글");
			inquiry.setSecretPw(multi.getParameter("secretPw"));
		} else {
			inquiry.setSecret("공개글");
			inquiry.setSecretPw("");
		}

		centerMapper.writeInquiryProc(inquiry);
	}

	public String inquirySecretCheck(int num) {
		String category = "inquiry";
		centerDTO inquiry = centerMapper.inquirySecretCheck(num, category);
		String user_id = (String)session.getAttribute("id");
		
		if(user_id == null)
			user_id = "";
		
		if(inquiry.getSecret().equals("공개글") || user_id.equals("admin"))
			return "0";
		
		if (inquiry.getSecret().equals("비밀글"))
			return "1";
		
		return "0";
	}

	public String secretPwCheck(int num, String secretPw) {
		// TODO Auto-generated method stub
		String category = "inquiry";
		centerDTO inquiry = centerMapper.inquirySecretCheck(num, category);
		if (inquiry != null && inquiry.getSecretPw() != null) {
			if (inquiry.getSecretPw().equals(secretPw)) {
				return "1";
			} else {
				return "0";
			}
		}
		return "0";
	}
	
	@Autowired 
	private MemberMapper memberMapper;
	public String adminReplyProc(String n, String replyContent) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerReplyDTO adminReply = new centerReplyDTO();

		adminReply.setCenterNum(num);
		adminReply.setCategory("inquiry");
		adminReply.setReply(replyContent.replaceAll("\n", "<br>"));
		adminReply.setWriter("관리자");

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		adminReply.setWriteDate(formateDate);

		centerMapper.adminReplyProc(adminReply);
		centerMapper.incReplys(num);
		
		//작성자 전화번호 가져오기
		centerDTO inquiry = centerMapper.centerContent(num);
		String writer = inquiry.getWriter();
		
		MemberDTO member = memberMapper.loginProc(writer);
		String phoneNumber = member.getMobile();
		
		return phoneNumber;
	}
	
	@Autowired private SmsService smsService;
	public void sendAdminReply(String phoneNumber){
		smsService.sendAdminReply(phoneNumber);
	}
	
	public ArrayList<centerReplyDTO> inquiryReplys(String n) {
		// TODO Auto-generated method stub
		int center_num = 0;
		try {
			center_num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		String category = "inquiry";
		ArrayList<centerReplyDTO> inquiryReplys = centerMapper.replys(center_num, category);
		return inquiryReplys;
	}
	
	public String modifyAble(int centerNum) {
		int reply = centerMapper.modifyAble(centerNum);
		if(reply == 0)
			return "수정 가능";
		
		return "수정 불가";
	}
	
	public centerDTO modifyInquiry(String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}
		centerDTO inquiry = centerMapper.centerContent(num);
		if (inquiry == null)
			return null;

		inquiry.setContent(inquiry.getContent().replaceAll("<br>", "\n"));

		if (inquiry.getFiles() != null && inquiry.getFiles().isEmpty() == false) {
			String fn = inquiry.getFiles();
			String[] fileName = fn.split("-", 2);
			inquiry.setFiles(fileName[1]);
		}
		return inquiry;
	}

	public String modifyInquiryProc(MultipartHttpServletRequest multi, String n) {
		// TODO Auto-generated method stub
		int num = 0;
		try {
			num = Integer.parseInt(n);
		} catch (Exception e) {
			return null;
		}

		centerDTO inquiry = centerMapper.centerContent(num);
		if (inquiry == null) {
			return null;
		}

		inquiry.setTitle(multi.getParameter("title"));
		inquiry.setContent(multi.getParameter("content").replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		inquiry.setWriteDate(formateDate);

		String formateTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
		inquiry.setWriteTime(formateTime + "(수정됨)");

		inquiry.setLikes(inquiry.getLikes());

		if (inquiry.getFiles() == null) {
			inquiry.setFiles("");
		} else {
			inquiry.setFiles(inquiry.getFiles());
		}

		MultipartFile file = multi.getFile("upfile");
		String fileName = file.getOriginalFilename();
		if (file.getSize() != 0) {
			System.out.println(fileName);
			// 기존 파일 삭제
			String path = "C:\\javas\\upload\\" + inquiry.getFiles();
			File originFile = new File(path);
			if (originFile.exists() == true) {
				originFile.delete();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf = new SimpleDateFormat("yyyyMMddHHmmss-");
			Calendar cal = Calendar.getInstance();
			fileName = sdf.format(cal.getTime()) + fileName;
			inquiry.setFiles(fileName);

			// 업로드 파일 저장 경로
			String fileLocation = "C:\\javas\\upload\\";
			File save = new File(fileLocation + fileName);

			try {
				file.transferTo(save);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String secretCheckboxValue = multi.getParameter("secretCheckbox");
		if (secretCheckboxValue != null && secretCheckboxValue.equals("secretChecked")) {
			inquiry.setSecret("비밀글");
			inquiry.setSecretPw(multi.getParameter("secretPw"));
		} else {
			inquiry.setSecret("공개글");
			inquiry.setSecretPw("");
		}

		centerMapper.modifyinquiryProc(inquiry);
		return "게시글 수정 완료";
	}

	public List<centerReplyDTO> modifyAdminReplyClick(int reply_num, int center_num) {
		// TODO Auto-generated method stub
		List<centerReplyDTO> inquirys = centerMapper.announcementReplyModifyClick(reply_num);
		centerReplyDTO inquiry = inquirys.get(0);
		inquiry.setReply(inquiry.getReply().replaceAll("<br>", "\n"));
		inquirys.add(inquiry);
		return inquirys;
	}

	public centerReplyDTO modifyAdminReply(int reply_num, int center_num, String modifiedContent) {
		// TODO Auto-generated method stub
		centerReplyDTO adminReply = centerMapper.replyContent(reply_num);
		if (adminReply == null) {
			return null;
		}
		adminReply.setReplyNum(reply_num);
		adminReply.setCenterNum(center_num);
		adminReply.setReply(modifiedContent.replaceAll("\n", "<br>"));

		LocalDateTime now = LocalDateTime.now();
		String formateDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		adminReply.setWriteDate(formateDate + "(수정됨)");

		centerMapper.modifyAdminReply(adminReply);
		adminReply.setReply(adminReply.getReply().replaceAll("<br>", "\n"));
		return adminReply;
	}

	public void deleteAdminReply(int replyNum, int centerNum) {
		centerMapper.deleteReply(replyNum);
		centerMapper.decReplys(centerNum);
	}
	
}
