package com.spring.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "customer", referencedColumnName = "login", nullable = false)
	private User customer;

	@ManyToOne
	@JoinColumn(name = "developer", referencedColumnName = "login")
	private User developer;

	@Column(length = 255, nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "urgence", referencedColumnName = "id", nullable = false)
	private Urgence urgence;

	@ManyToOne
	@JoinColumn(name = "environment", referencedColumnName = "id", nullable = false)
	private Environment environment;

	@ManyToOne
	@JoinColumn(name = "software", referencedColumnName = "id", nullable = false)
	private Software software;

	public Ticket() {
		super();
	}

	public Ticket(User customer, User developer, String description, Status status, Urgence urgence,
			Environment environment, Software software) {
		super();
		this.customer = customer;
		this.developer = developer;
		this.description = description;
		this.status = status;
		this.urgence = urgence;
		this.environment = environment;
		this.software = software;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public User getDeveloper() {
		return developer;
	}

	public void setDeveloper(User developer) {
		this.developer = developer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Urgence getUrgence() {
		return urgence;
	}

	public void setUrgence(Urgence urgence) {
		this.urgence = urgence;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Software getSoftware() {
		return software;
	}

	public void setSoftware(Software software) {
		this.software = software;
	}
}
