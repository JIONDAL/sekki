package com.care.sekki.recipeboard;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecipeBoardMapper {

	ArrayList<RecipeBoardDTO> recipeboardForm(int begin, int end);

	int count();

	String recipeboardWriteProc(RecipeBoardDTO recipeboard);

	RecipeBoardDTO recipeboardContent(int no);
	
	void incHit(int no);

	String recipeboardDownload(int no);
	
	String recipeboardModifyProc(RecipeBoardDTO recipeboard);

	String recipeboardDeleteProc(int no);

	

	

	

	
}

