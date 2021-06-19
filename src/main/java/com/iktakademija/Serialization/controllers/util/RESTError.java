package com.iktakademija.Serialization.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.Serialization.security.Views;


public class RESTError {
	
	@JsonView(Views.Public.class)
	private Integer code;	
	
	@JsonView(Views.Public.class)
	private String message;

	public RESTError() {
		super();
	}

	public RESTError(String message, Integer code) {
		super();
		this.message = message;
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
// najcesce nema settere
	
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public Integer getCode() {
		return code;
	}

//	public void setCode(Integer code) {
//		this.code = code;
//	}

}
