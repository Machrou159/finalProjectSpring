package com.spring.web.dto;

public class ReqTicketAffectationDTO {

	private int id;
	private String developerLogin;

	public ReqTicketAffectationDTO() {
		super();
	}

	public ReqTicketAffectationDTO(int id, String developerLogin) {
		super();
		this.id = id;
		this.developerLogin = developerLogin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeveloperLogin() {
		return developerLogin;
	}

	public void setDeveloperLogin(String developerLogin) {
		this.developerLogin = developerLogin;
	}
}
