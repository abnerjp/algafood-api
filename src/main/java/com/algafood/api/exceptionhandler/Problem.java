package com.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private String userMessage;
	
	public Problem(Integer status, String type, String title, String detail, String userMessage) {
		this.status = status;
		this.type = type;
		this.title = title;
		this.detail = detail;
		this.userMessage = userMessage;
	}
	
	public Problem(Integer status, String title, String userMessage) {
		this(status, null, title, null, userMessage);
	}

	public Problem(Integer status, String title) {
		this(status, null, title, null, "");
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public String getType() {
		return type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDetail() {
		return detail;
	}

	public String getUserMessage() {
		return userMessage;
	}

}
