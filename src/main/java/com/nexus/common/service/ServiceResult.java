package com.nexus.common.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceResult {
	
	private Boolean success;
	private List<String> messages = new ArrayList<String>();
	private Object object;
	
	public Boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public void addMessage(String message){
		this.messages.add(message);
	}
	
	
	
}
