package com.spring.web.dto;

public class ReqTicketCreationDTO {

	private String description;
	private int environment;
	private String customerLogin;
	private int status;
	private int urgence;
	private int software;

	public ReqTicketCreationDTO() {
		super();
	}

	public ReqTicketCreationDTO(String description, int environment, String customer, int status, int urgence,
			int software) {
		super();
		this.description = description;
		this.environment = environment;
		this.customerLogin = customer;
		this.status = status;
		this.urgence = urgence;
		this.software = software;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEnvironment() {
		return environment;
	}

	public void setEnvironment(int environment) {
		this.environment = environment;
	}

	public String getCustomerLogin() {
		return customerLogin;
	}

	public void setCustomerLogin(String customerLogin) {
		this.customerLogin = customerLogin;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUrgence() {
		return urgence;
	}

	public void setUrgence(int urgence) {
		this.urgence = urgence;
	}

	public int getSoftware() {
		return software;
	}

	public void setSoftware(int software) {
		this.software = software;
	}
	
	
}
