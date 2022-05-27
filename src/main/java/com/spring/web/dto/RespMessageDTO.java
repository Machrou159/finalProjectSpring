package com.spring.web.dto;

public class RespMessageDTO {

	private String message;

	public RespMessageDTO() {
		super();
	}

	public RespMessageDTO(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
