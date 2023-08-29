package com.care.sekki.Recipe;

import java.math.BigDecimal;

public class MaterialDTO {
	private Long mate_no;
	
	private Long re_no;
	private String materialname;
    private String materialamount;
    
    public MaterialDTO(){
    	
    }
    
    public Long getMate_no() {
		return mate_no;
	}

	public void setMate_no(Long mate_no) {
		this.mate_no = mate_no;
	}


	public Long getRe_no() {
		return re_no;
	}

	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}

	public String getmaterialname() {
		return materialname;
	}
	public void setmaterialname(String materialname) {
		this.materialname = materialname;
	}
	public String getmaterialamount() {
		return materialamount;
	}
	public void setmaterialamount(String materialamount) {
		this.materialamount = materialamount;
	}
	
	public MaterialDTO(String materialname, String materialamount) {
		this.materialname = materialname;
		this.materialamount = materialamount;
	}
}
