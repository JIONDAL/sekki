package com.care.sekki.Recipe;

public class StepDTO {
	private Long step_no;
	
	private Long re_no;
	private String step_text;
	private String step_photoholder;

	
	public Long getStep_no() {
		return step_no;
	}
	public void setStep_no(Long step_no) {
		this.step_no = step_no;
	}
	public Long getRe_no() {
		return re_no;
	}
	public void setRe_no(Long re_no) {
		this.re_no = re_no;
	}
	public String getStep_text() {
		return step_text;
	}
	public void setStep_text(String step_text) {
		this.step_text = step_text;
	}
	public String getstep_photoholder() {
		return step_photoholder;
	}
	public void setstep_photoholder(String step_photoholder) {
		this.step_photoholder = step_photoholder;
	}

}
