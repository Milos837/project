package com.example.project.controllers.util;

import com.example.project.security.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class RESTError {

	@JsonView(Views.Public.class)
	private Integer code;
	
	@JsonView(Views.Public.class)
	private String message;
	
	public RESTError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
