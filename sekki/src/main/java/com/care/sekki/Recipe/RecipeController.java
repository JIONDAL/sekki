package com.care.sekki.Recipe;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	@Autowired private HttpSession session;
	@Autowired private RecipeMapper recipemapper;
	@RequestMapping("/recipeBoard")
    public String recipeBoard(@RequestParam(value="currentPage", required = false)String cp,
			String search, Model model) {
		recipeService.recipeBoard(cp, search ,model);
		
        return "recipe/recipeBoard";
    }
	
	
	@GetMapping("/recipeBoardWrite")
    public String recipeBoardWrite() {
		if(session.getAttribute("id") == null ) {
			return "redirect:login";
		}
        return "recipe/recipeBoardWrite";
    }
	
	@PostMapping("recipeProc")
	public String recipeProc(RecipeBoardDTO recipeDto,HttpServletRequest request, HttpServletResponse response) {
		recipeService.recipeProc(recipeDto,request, response);
		
		return "recipe/recipeBoardWrite";
	}
	@RequestMapping("recipeBoardContent")
	public String recipeBoardContent(
			@RequestParam(value="num", required = false)String n, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		RecipeBoardDTO reciDto = recipeService.recipeContent(n);
		
		if(reciDto == null) 
			return "redirect:recipeBoard"; 
		
		model.addAttribute("recipeCon", reciDto);
		
		
		//카테고리 값변환
		if(reciDto.getCategory().equals("kor")) {
			reciDto.setCategory("한식");
		}else if(reciDto.getCategory().equals("jap")) {
			reciDto.setCategory("일식");
		}else if(reciDto.getCategory().equals("chi")) {
			reciDto.setCategory("중식");
		}else
			reciDto.setCategory("양식");
		System.out.println("re_no 들어가나?  :  " + reciDto.getRe_no());
		session.setAttribute("re_no",reciDto.getRe_no());
		
		//시간 값변환
		if(reciDto.getTimes().equals("5")) {
			reciDto.setTimes("5분 이내");
		}else if(reciDto.getTimes().equals("30")){
			reciDto.setTimes("30분 이내");
		}else if(reciDto.getTimes().equals("60")){
			reciDto.setTimes("1시간 이내");
		}else
			reciDto.setTimes("2시간 이상");

		//몇인분 값변환
		if(reciDto.getCuisine().equals("1")) {
			reciDto.setCuisine("1인분");
		}else if(reciDto.getCuisine().equals("2")) {
			reciDto.setCuisine("2인분");
		}else
			reciDto.setCuisine("3인분");
			
		//난이도 값변환
		if(reciDto.getDegree().equals("1")) {
			reciDto.setDegree("아무나");
		}else if(reciDto.getDegree().equals("2")) {
			reciDto.setDegree("초급");
		}else if(reciDto.getDegree().equals("3")) {
			reciDto.setDegree("중급");
		}else if(reciDto.getDegree().equals("4")) {
			reciDto.setDegree("고급");
		}
		
		

		/*
		 * RecipeBoardDTO reciDto = recipeService.recipeContent(n);
		
		if(reciDto == null) 
			return "redirect:recipeBoard"; 
		
		model.addAttribute("recipeCon", reciDto);
		
		 * 
		 */
		List<MaterialDTO> reciMa = recipeService.recipeContent_ma(n);

		if (reciMa == null)
		    return "redirect:recipeBoard";

		model.addAttribute("reciMa", reciMa);
		
		
		
		List<CommentDTO> reciment = recipeService.recipeContent_comment(n);

		if (reciment == null) {
			 model.addAttribute("commentMessage", "아직 작성한 댓글이 없습니다.");
		    
		}else {
		model.addAttribute("reciment", reciment);
		
		for (CommentDTO comment : reciment) {
		    float rating = comment.getRating(); // CommentDTO 객체의 rating 값 가져오기

		    int maxRating = 5;
		    int intPart = (int) Math.floor(rating);
		    int fractionalPart = Math.round((rating - intPart) * 2);

		    String fullStarPath = "image/star_green.png";
		    String halfStarPath = "image/star_green_half.png";
		    String emptyStarPath = "image/star_no.png";

		    List<String> starTags = new ArrayList<>();
		    for (int i = 1; i <= maxRating; i++) {
		        if (i <= intPart) {
		            starTags.add("<img src='" + fullStarPath + "' alt='Full Star'>");
		        } else if (i == intPart + 1 && fractionalPart == 1) {
		            starTags.add("<img src='" + halfStarPath + "' alt='Half Star'>");
		        } else {
		            starTags.add("<img src='" + emptyStarPath + "' alt='Empty Star'>");
		        }
		    }

		    comment.setStarTags(starTags); // CommentDTO 객체에 starTags 설정
		}
		}

		model.addAttribute("reciment", reciment);






;

		List<StepDTO> reciStepList = recipeService.recipeContent_step(n); // 수정된 부분

		if (reciStepList == null || reciStepList.isEmpty())
		    return "redirect:recipeBoard";

		model.addAttribute("reciStepList", reciStepList); // 모든 결과 리스트를 모델에 추가

		return "recipe/recipeBoardContent";
	}
	@PostMapping("commentProc")
	public String commentProc(HttpSession session, CommentDTO commentDto, Model model) {
		recipeService.commentProc(commentDto);

		
		return "redirect:/recipeBoardContent?num=" + commentDto.getRe_no();
	}
	@GetMapping("recipeBoardUpdata")
	public String recipeBoardUpdata(@RequestParam(value="num", required = false)String n, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		RecipeBoardDTO recipeContent = recipeService.recipeContent(n);
        List<MaterialDTO> recipeContentMa = recipeService.recipeContent_ma(n);
        List<StepDTO> recipeContentStep = recipeService.recipeContent_step(n);
        session.setAttribute("re_no", recipeContent.getRe_no());
        System.out.println("re_no 찾기 : " + recipeContent.getRe_no());
       
        if (recipeContent == null || recipeContentMa == null || recipeContentStep == null) {
            // 필요한 데이터가 없으면 예외 처리 또는 에러 페이지로 이동 처리
        }
        
        model.addAttribute("recipeCon", recipeContent);
        model.addAttribute("reciMa", recipeContentMa);
        model.addAttribute("reciStepList", recipeContentStep);
        model.addAttribute("selectedCategory", recipeContent.getCategory());
        model.addAttribute("selectedCuisine", recipeContent.getCuisine());
        model.addAttribute("selectedTimes", recipeContent.getTimes());
        model.addAttribute("selectedDegree", recipeContent.getDegree());
		return "recipe/recipeBoardUpdata";
	}
	
	@PostMapping("recipeUpdataProc")
	public String recipeBoardUpdata(RecipeBoardDTO recipeDto,HttpServletRequest request, HttpServletResponse response) {
		recipeService.recipeUpdata(recipeDto,request, response);
		
		return "recipe/recipeBoard";
	}
	
	@RequestMapping("deleteRecipe")
	public String deleteRecipe(@RequestParam(value="re_no", required = false)String n) {
		
		recipeService.reciDelete(n);
		
		return "recipe/recipeBoard";
	}
	
	@PostMapping("commentUpdata")
	public String commentUpdata(@RequestBody Map<String, Object> requestData,  CommentDTO comDto) {
		
		recipeService.commentUpdata(requestData, comDto);
		
		return "recipe/recipeBoard";
	}
	
	@DeleteMapping("/deleteRecipe/{commentNo}/{reno}") // 경로 변수를 설정해줍니다.
    public ResponseEntity<String> deleteComment(@PathVariable Long commentNo, @PathVariable Long reno, CommentDTO comDto) {
        try {
            System.out.println("commentNo: " + commentNo + ", reno: " + reno); // 값 확인
            // 여기서 실제로 삭제 작업을 수행하고, 결과에 따라 적절한 ResponseEntity 반환
            comDto.setcomment_no(commentNo);
            comDto.setRe_no(reno);
            recipemapper.CommentDel(comDto);
            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
