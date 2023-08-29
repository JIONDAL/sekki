package com.care.sekki.Recipe;

import java.io.IOException;

import java.sql.Timestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.care.sekki.S3.S3UploadService;
import com.care.sekki.common.PageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class RecipeService {
	//,MultipartFile profilePicture
	@Autowired private RecipeMapper recipemapper;
	@Autowired private HttpSession session;
	@Autowired private S3UploadService s3UploadService;
//---------------------------레시피 게시판------------------------
	public void recipeBoard(String cp, String search, Model model) {
		if (search == null) {
			search = "";
		}
		int currentPage = 1;
		try{
			currentPage = Integer.parseInt(cp);
		}catch(Exception e){
			currentPage = 1;
		}

		int pageBlock = 6; // 한 페이지에 보일 데이터의 수 
		int end = pageBlock * currentPage; // 테이블에서 가져올 마지막 행번호
		int begin = end - pageBlock + 1; // 테이블에서 가져올 시작 행번호

		ArrayList<RecipeBoardDTO> recipes = recipemapper.recipeBoard(begin, end, search);
		int totalCount = recipemapper.count(search);
		String url = "recipeBoard?&search="+search+"&currentPage=";
		String result = PageService.printPage(url, currentPage, totalCount, pageBlock);

		model.addAttribute("recipes", recipes);
		model.addAttribute("result", result);
		model.addAttribute("currentPage", currentPage);
		for(RecipeBoardDTO to : recipes) {
			System.out.println(to.getRe_no());
		System.out.println(to.getMainphoto());
		
		}
	}
	 
//---------------------------레시피 게시판------------------------

//----------------------------레시피 내용------------------------
	public RecipeBoardDTO recipeContent(String n) {
		long re_no = 0;
		try {
		    re_no = Long.parseLong(n);
		} catch (Exception e) {
		    return null;
		}

		RecipeBoardDTO recieContent = recipemapper.recipeContent(re_no);
		if (recieContent == null) {
			System.out.println("no가 널입니다.");
			return null;
		}
		System.out.println("re_no 값들어옴>? : " + recieContent.getRe_no());
		
		recieContent.setViews(recieContent.getViews() + 1);
		reHit(recieContent.getRe_no());
	
		return recieContent;
	}
	public List<MaterialDTO> recipeContent_ma(String n) {
		long re_no = 0;
		try {
		    re_no = Long.parseLong(n);
		} catch (Exception e) {
		    return null;
		}
		
		List<MaterialDTO> recipeMa = recipemapper.recipeMa(re_no);
	    if (recipeMa == null || recipeMa.isEmpty()) {
	        return null;
	    }
	    
	    for(MaterialDTO to : recipeMa) {
			System.out.println(to.getmaterialamount());
			System.out.println(to.getmaterialname());
	    }

	    return recipeMa; // 모든 결과를 그대로 반환
	}
	
	public List<StepDTO> recipeContent_step(String n) {
	    long re_no = 0;
	    try {
	        re_no = Long.parseLong(n);
	    } catch (Exception e) {
	        return null;
	    }

	    List<StepDTO> recipeSt = recipemapper.recipeSt(re_no);
	    if (recipeSt == null || recipeSt.isEmpty()) {
	        return null;
	    }
	    
	    for(StepDTO to : recipeSt) {
        	System.out.println("step_no : " + to.getStep_no());
        	System.out.println("re_no : " + to.getRe_no());
        }

	    return recipeSt; // 모든 결과를 그대로 반환
	}
	
	public List<CommentDTO> recipeContent_comment(String n) {
	    long re_no = 0;
	    try {
	        re_no = Long.parseLong(n);
	    } catch (Exception e) {
	        return null;
	    }

	    List<CommentDTO> reciment = recipemapper.reciment(re_no);
	    if (reciment == null || reciment.isEmpty()) {
	        return null;
	    }

	    return reciment; // 모든 결과를 그대로 반환
	}
	
	public void reHit(Long re_no) {
		recipemapper.reHit(re_no);
	}
	
	public String commentProc(CommentDTO commentDto) {
		String id = (String)session.getAttribute("id");

		String profile = (String)session.getAttribute("profilePictureUrl");

		commentDto.setId(id);
		commentDto.setProfile(profile);
		Instant currentTime = Instant.now();
		commentDto.setWritten_time(Timestamp.from(currentTime));
		Object reNoObject = session.getAttribute("re_no");
		if (reNoObject instanceof Long) {
		    Long reNo = (Long) reNoObject;
		    commentDto.setRe_no(reNo);
		}
		System.out.println("다람쥐잉 다라주미이" + commentDto.getRating());
		System.out.println("re_no뜻냐 : " + commentDto.getRe_no());
		recipemapper.insertComment(commentDto);
		
		return "굳";

		
		//recipemapper.insertComment(commentDto);

	}
	
//----------------------------댓글-------------------------------
	
//----------------------------레시피 내용------------------------
	
//----------------------------
	
//---------------------------레시피 작성------------------------------
	public String recipeProc(
			RecipeBoardDTO reciDto,
			HttpServletRequest request, HttpServletResponse response
			) {
		
		String id = (String)session.getAttribute("id");
		if(id == null || id.isEmpty()) {
			return "로그인";
		}
		String profile = (String)session.getAttribute("profilePictureUrl");
		
		reciDto.setId(id);
		reciDto.setProfile(profile);
		// 서버의 현재 시간을 구해서 DTO에 설정
	    Instant currentTime = Instant.now();
	    reciDto.setWritten_time(Timestamp.from(currentTime));
	    
	    MultipartFile mainphotoUrl = reciDto.getMainphotoUrl();
	    try {
	        if (mainphotoUrl != null && !mainphotoUrl.isEmpty()) {
	            String mainphoto = s3UploadService.saveFile(mainphotoUrl, id); // 파일을 업로드하고 URL을 얻음
	            reciDto.setMainphoto(mainphoto); // 업로드한 파일의 URL을 DTO에 설정
	        } else {
	            reciDto.setMainphoto(""); // 이미지가 없을 경우 빈 문자열로 설정
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        reciDto.setMainphoto(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	    }
	    System.out.println("유알엘 정보에요오오옹 : " + reciDto.getMainphoto());
	    
	    

	    recipemapper.insertRecipe(reciDto);
	    Long re_no = reciDto.getRe_no();
	    System.out.println(reciDto.getRe_no());
	    System.out.println("re no" + re_no);
	    String[] materialnames = request.getParameterValues("materialname");
	    System.out.println("재료 이름  :  " + materialnames);
	    String[] materialamounts = request.getParameterValues("materialamount");
	    System.out.println("재료 이름  :  " + materialamounts);

	    // 재료 데이터를 담을 리스트 생성
	    List<MaterialDTO> materials = new ArrayList<MaterialDTO>();

	    // 재료 데이터 배열을 DTO 리스트로 변환
	    for (int i = 0; i < materialnames.length; i++) {
	        MaterialDTO materialDTO = new MaterialDTO();
	        materialDTO.setRe_no(re_no);
	        System.out.println(materialDTO.getRe_no());
	        materialDTO.setmaterialname(materialnames[i]);
	        System.out.println(materialDTO.getmaterialname());
	        
	        materialDTO.setmaterialamount(materialamounts[i]);
	        System.out.println(materialDTO.getmaterialamount());
	        materials.add(materialDTO);
	        
	        
	    }
	    
	    
	    
	    for (MaterialDTO materialDTO : materials) {
	    	recipemapper.insertMaterial(materialDTO);
	    }
	    
	    String memberId = (String) session.getAttribute("id");
	    String[] stepTexts = request.getParameterValues("step_text");
	    List<MultipartFile> stepPhotoFiles = ((MultipartHttpServletRequest) request).getFiles("step_photoholder");
	    List<StepDTO> Steps = new ArrayList<>();
	    for (int i = 0; i < stepTexts.length; i++) {
	        StepDTO stepDTO = new StepDTO();
	        stepDTO.setStep_text(stepTexts[i]);
	        stepDTO.setRe_no(re_no);

	        if (stepPhotoFiles != null && stepPhotoFiles.size() > i) {
	            MultipartFile stepPhotoFile = stepPhotoFiles.get(i);

	            try {
	                if (!stepPhotoFile.isEmpty()) {
	                    String stepPhotoUrl = s3UploadService.saveFile(stepPhotoFile, memberId);
	                    stepDTO.setstep_photoholder(stepPhotoUrl);
	                } else {
	                    stepDTO.setstep_photoholder("");
	                }
	            } catch (IOException e) {
	                // IOException 발생 시 처리할 내용을 여기에 작성합니다.
	                e.printStackTrace(); // 예시로 에러 로그를 출력합니다.
	                stepDTO.setstep_photoholder(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	            }
	        } else {
	            stepDTO.setstep_photoholder("");
	        }

	        Steps.add(stepDTO);
	    }
	    
	    for (StepDTO step : Steps) {
	    	recipemapper.insertStep(step);
        }

	    for (StepDTO stepDTO : Steps) {
	        System.out.println("스텝 텍스트 : " + stepDTO.getStep_text());
	        System.out.println("스탭 포토 : " + stepDTO.getstep_photoholder());
	    }



	      
	    System.out.println("title : " + reciDto.getTitle());
	    System.out.println("content : " + reciDto.getContent());
	    System.out.println("category : " + reciDto.getCategory());
	    System.out.println("cuisine : " + reciDto.getCuisine());
	    System.out.println("times : " + reciDto.getTimes());
	    System.out.println("degree : " + reciDto.getDegree());
	    System.out.println("tip : " + reciDto.getTip());
	    
	    System.out.println("time : " + reciDto.getWritten_time());
		System.out.println("id : " + id);
		
		return "레시피 작성에 성공했습니다.";
	}
//---------------------------레시피 작성------------------------------
	
//--------------------------레시피 수정-------------------------------
	public String recipeUpdata(RecipeBoardDTO reciDto,
			HttpServletRequest request, HttpServletResponse response) {
		Long re_no = (Long) session.getAttribute("re_no");
		RecipeBoardDTO recieContent = recipemapper.recipeContent(re_no);
		reciDto.setRe_no(re_no);
		System.out.println("rre_no 찾기 : " + reciDto.getRe_no());
		System.out.println(reciDto.getRe_no());
	    System.out.println(reciDto.getTitle());
	    System.out.println("유알엘 : " + reciDto.getMainphotoUrl());
	    MultipartFile mainphotoUrl = reciDto.getMainphotoUrl();
	    try {
	        if (mainphotoUrl != null && !mainphotoUrl.isEmpty()) {
	            String mainphoto = s3UploadService.saveFile(mainphotoUrl, reciDto.getId()); // 파일을 업로드하고 URL을 얻음
	            reciDto.setMainphoto(mainphoto); // 업로드한 파일의 URL을 DTO에 설정
	        } else {
	            reciDto.setMainphoto(recieContent.getMainphoto()); // 이미지가 없을 경우 빈 문자열로 설정
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        reciDto.setMainphoto(""); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	    }
	    System.out.println("유알엘 정보에요오오옹 : " + reciDto.getMainphoto());
	    
	    
	    //업데이트로 바꾸셈
	    recipemapper.reciUpdata(reciDto);
	    //업데이트로 바꾸셈

	    String[] materialnames = request.getParameterValues("materialname");
	    System.out.println("재료 이름  :  " + materialnames);
	    String[] materialamounts = request.getParameterValues("materialamount");
	    System.out.println("재료 이름  :  " + materialamounts);

	    // 재료 데이터를 담을 리스트 생성
	    List<MaterialDTO> recipeMa = recipemapper.recipeMa(reciDto.getRe_no());
	    List<MaterialDTO> materials = new ArrayList<MaterialDTO>();
	    
	    

	    // 재료 데이터 배열을 DTO 리스트로 변환
	    for (int i = 0; i < recipeMa.size(); i++) {
	    	MaterialDTO mateDTO = recipeMa.get(i);
	    	
	        MaterialDTO materialDTO = new MaterialDTO();
	        materialDTO.setMate_no(mateDTO.getMate_no());
	        System.out.println("mate에서 mano찾기 : " + materialDTO.getMate_no());
	        materialDTO.setRe_no(reciDto.getRe_no());
	        System.out.println("mate에서 re찾기 : " + materialDTO.getRe_no());
	        
	        materialDTO.setmaterialname(materialnames[i]);
	        System.out.println(materialDTO.getmaterialname());
	        
	        materialDTO.setmaterialamount(materialamounts[i]);
	        System.out.println(materialDTO.getmaterialamount());
	        materials.add(materialDTO);
	    }
	    
	    
	    
	    for (MaterialDTO materialDTO : materials) {
	    	//업데이트로 바꾸기
	    	recipemapper.mateUpdata(materialDTO);
	    	//업데이트로 바꾸기
	    }
	    
	    List<MultipartFile> stepPhotoFiles = ((MultipartHttpServletRequest) request).getFiles("step_photoholder");
	    List<StepDTO> recipeSt = recipemapper.recipeSt(re_no);
	    List<StepDTO> Steps = new ArrayList<>();

	    String[] stepTexts = request.getParameterValues("step_text");
	    

	    for (int i = 0; i < recipeSt.size(); i++) {
	        StepDTO stepDTO = recipeSt.get(i);
	        System.out.println("사진이들어갈까요 : " + stepDTO.getstep_photoholder());
	        StepDTO newStepDTO = new StepDTO();
	        newStepDTO.setStep_no(stepDTO.getStep_no());
	        newStepDTO.setRe_no(reciDto.getRe_no());

	        if (i < stepTexts.length) {
	            newStepDTO.setStep_text(stepTexts[i]);
	        } else {
	            newStepDTO.setStep_text(null); // 또는 빈 문자열로 설정하거나 다른 기본값을 선택할 수 있습니다.
	        } if (stepPhotoFiles != null && stepPhotoFiles.size() > i) {
	            MultipartFile stepPhotoFile = stepPhotoFiles.get(i);

	            try {
	                if (!stepPhotoFile.isEmpty()) {
	                    String stepPhotoUrl = s3UploadService.saveFile(stepPhotoFile, reciDto.getId());
	                    newStepDTO.setstep_photoholder(stepPhotoUrl);
	                } else {
	                	newStepDTO.setstep_photoholder(stepDTO.getstep_photoholder());
	                }
	            } catch (IOException e) {
	                // IOException 발생 시 처리할 내용을 여기에 작성합니다.
	                e.printStackTrace(); // 예시로 에러 로그를 출력합니다.
	                newStepDTO.setstep_photoholder(stepDTO.getstep_photoholder()); // 예외 발생 시 빈 문자열로 설정하거나 다른 처리를 하세요.
	            }
	        } else {
	        	newStepDTO.setstep_photoholder(stepDTO.getstep_photoholder());
	        	System.out.println("사진이들어갈까요 : " + newStepDTO.getstep_photoholder());
	        }
	        
	        Steps.add(newStepDTO);
	    }
	    for (StepDTO stepDTO : Steps) {
	    	System.out.println("stpeno : " + stepDTO.getStep_no());
	    	System.out.println("re_no : " + stepDTO.getRe_no());
	        System.out.println("스텝 텍스트 : " + stepDTO.getStep_text());
	        System.out.println("스탭 포토 : " + stepDTO.getstep_photoholder());
	    }
	    for (StepDTO step : Steps) {
	    	//업데이트로 바꾸기
	    	recipemapper.stepUpdata(step);
	    	//업데이트로 바꾸기
        }

		return "레시피 작성에 성공했습니다.";
	}
//--------------------------레시피 수정-------------------------------
//--------------------------레시피 삭제-------------------------------
	public void reciDelete(String n) {
		   long re_no = 0;
		    try {
		        re_no = Long.parseLong(n);
		    } catch (Exception e) {
		        return;
		    }

		RecipeBoardDTO recipeDto = recipemapper.recipeContent(re_no);
		if (recipeDto == null)
			return;

		
		
		recipemapper.reciMateDel(re_no);
		
		recipemapper.reciStepDel(re_no);
		
		recipemapper.reciCommentDel(re_no);
		
		recipemapper.reciBoardDel(re_no);


		return;
	}
//--------------------------레시피 삭제-------------------------------
	
//-----------------------------댓글 수정----------------------------
	public String commentUpdata(@RequestBody Map<String, Object> requestData, CommentDTO comDto) {
	    String updatedContent = requestData.get("updatedContent").toString();
	    Long reno = Long.parseLong(requestData.get("reno").toString());
	    Long comNo = Long.parseLong(requestData.get("comment_no").toString());
	    
	    comDto.setRe_no(reno);
	    comDto.setComment_content(updatedContent);
	    comDto.setcomment_no(comNo);
	    
	    System.out.println("reno값 : " + comDto.getRe_no());
	    System.out.println("내용 : " + comDto.getComment_content());
	    System.out.println("comno : " + comDto.getcomment_no());
	    
	    recipemapper.commentUpdata(comDto);
	    
		
		
		return "수정완료";
	}
	
//-----------------------------댓글 수정----------------------------
//-----------------------------댓글 삭제----------------------------
	
	
//-----------------------------댓글 삭제----------------------------
}
