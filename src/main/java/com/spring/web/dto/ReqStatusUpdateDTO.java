package com.spring.web.dto;

public class ReqStatusUpdateDTO {

	private int id;
	private int statusId;

	public ReqStatusUpdateDTO() {
		super();
	}

	public ReqStatusUpdateDTO(int id, int statusId) {
		super();
		this.id = id;
		this.statusId = statusId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
