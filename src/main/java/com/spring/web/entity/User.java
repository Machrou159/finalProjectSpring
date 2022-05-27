package com.spring.web.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@Column(length = 50)
	private String login;

	@Column(length = 255)
	@NotNull
	private String password;

	@Column(length = 255, nullable = false)
	private String firstname;

	@Column(length = 255, nullable = false)
	private String lastname;

	@Column(length = 50, nullable = false)
	private String role;

	@OneToMany(mappedBy = "customer")
	private Set<Ticket> customerTickets;

	@OneToMany(mappedBy = "developer")
	private Set<Ticket> developerTickets;

	public User() {
		super();
	}

	public User(String login, @NotNull String password, String firstname, String lastname, String role) {
		super();
		this.login = login;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return login;
	}
}