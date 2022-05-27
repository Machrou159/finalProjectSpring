package com.spring.web.dto;

public class ReqRefDataDTO {

	private String label;
	
	public ReqRefDataDTO() {
		super();
	}
	
	public ReqRefDataDTO(String label) {
		super();
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
