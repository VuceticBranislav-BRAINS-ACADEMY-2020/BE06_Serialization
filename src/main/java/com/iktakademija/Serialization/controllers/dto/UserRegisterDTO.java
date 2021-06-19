package com.iktakademija.Serialization.controllers.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.Serialization.security.Views;

public class UserRegisterDTO {

	private String name;
	private String email;
	private LocalDate dateOfBirth;
	private String pasword;

	// DTO moze i u entities paket
	public UserRegisterDTO() {
		super();
	}
	
	@JsonView(Views.Public.class)
	public String getName() {
		return name;
	}

	@JsonView(Views.Public.class)
	public void setName(String name) {
		this.name = name;
	}

	@JsonView(Views.Public.class)
	public String getEmail() {
		return email;
	}

	@JsonView(Views.Public.class)
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonView(Views.Private.class)
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	@JsonView(Views.Private.class)
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@JsonView(Views.Private.class)
	public String getPasword() {
		return pasword;
	}

	@JsonView(Views.Private.class)
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}

}
