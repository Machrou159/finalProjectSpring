package com.spring.web.form;

public class TicketForm {

	private String id;
	private String description;
	private String status;
	private String urgence;
	private String environment;
	private String software;

	public TicketForm() {
		super();
	}

	public TicketForm(String description, String status, String urgence, String environment, String software) {
		super();
		this.id = "-1";
		this.description = description;
		this.status = status;
		this.urgence = urgence;
		this.environment = environment;
		this.software = software;
	}

	public TicketForm(String id, String description, String status, String urgence, String environment,
			String software) {
		super();
		this.id = id;
		this.description = description;
		this.status = status;
		this.urgence = urgence;
		this.environment = environment;
		this.software = software;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrgence() {
		return urgence;
	}

	public void setUrgence(String urgence) {
		this.urgence = urgence;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}
}
