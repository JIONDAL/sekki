package com.care.sekki.recipeboard;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class RecipeBoardController {
	@Autowired private RecipeBoardService recipeservice;
	@Autowired private HttpSession session;
	
	
	
	
	@RequestMapping("/recipeboardForm")
	public String recipeboardForm(
			@RequestParam(value="currentPage", required = false)String cp,
			Model model) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		System.out.println("호출되는거야?");
		recipeservice.recipeboardForm(cp, model);
		return "recipeboard/recipeboardForm.jsp";
	}
	
	
	
	
	
	@GetMapping("/recipeboardWrite")
	public String recipeboardWrite() {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		return "recipeboard/recipeboardWrite.jsp";
	}
	
	@PostMapping("/recipeboardWriteProc")
	public String recipeboardWriteProc(Model model, MultipartHttpServletRequest multi) {
		String msg = recipeservice.recipeboardWriteProc(multi);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("게시글 작성 완료"))
			return "redirect:recipeboardForm.jsp";
		
		model.addAttribute("msg", msg);
		return "recipeboard/recipeboardWrite.jsp";
	}
	
	@RequestMapping("/recipeboardContent")
	public String recipeboardContent(
			@RequestParam(value="no", required = false)String n, Model model) {
		RecipeBoardDTO recipeboard = recipeservice.recipeboardContent(n);
		if(recipeboard == null) {
			System.out.println("recipeboardContent 게시글 번호 : " + n);
			return "redirect:recipeboardForm.jsp";
		}
		model.addAttribute("recipeboard", recipeboard);
		return "recipeboard/recipeboardContent.jsp";
	}

	@RequestMapping("/recipeboardDownload")
	public void recipeboardDownload(
			@RequestParam(value="no", required = false)String n, 
			HttpServletResponse res) throws IOException{
		
		recipeservice.recipeboardDownload(n, res);

	}
	
	@GetMapping("/recipeboardModify")
	public String recipeboardModify(
			@RequestParam(value="no", required = false)String n,
			Model model) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		RecipeBoardDTO recipeboard = recipeservice.recipeboardModify(n);
		if(recipeboard == null)
			return "redirect:recipeboardForm.jsp";
		
		if(id.equals(recipeboard.getId()) == false)
			return "redirect:recipeboardContent.jsp?no="+n;
	
		model.addAttribute("recipeboard", recipeboard);
		return "recipeboard/recipeboardModify.jsp";
	}
	
	@PostMapping("/recipeboardModifyProc")
	public String recipeboardModifyProc(RecipeBoardDTO recipeboard) {
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "redirect:login";
		}
		
		String msg = recipeservice.recipeboardModifyProc(recipeboard);
		if(msg.equals("게시글 수정 완료"))
			return "redirect:recipeboardContent.jsp?no="+recipeboard.getNo();
		
		return "redirect:recipeboardModify.jsp?no="+recipeboard.getNo();
	}

	@RequestMapping("/recipeboardDeleteProc")
	public String recipeboardDeleteProc(@RequestParam(value="no", required = false)String n) {
		String msg = recipeservice.recipeboardDeleteProc(n);
		if(msg.equals("로그인"))
			return "redirect:login";
		
		if(msg.equals("작성자만 삭제 할 수 있습니다.")) {
			System.out.println("게시글 번호 : " + n);
			return "forward:recipeboardContent.jsp";
		}
		
		return "redirect:recipeboardForm.jsp";
	}
}























