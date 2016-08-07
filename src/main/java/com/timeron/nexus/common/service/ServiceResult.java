package com.timeron.nexus.common.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class ServiceResult {
	@Expose
	private Boolean success;
	@Expose
	private List<String> messages = new ArrayList<String>();
	@Expose
	private List<String> errors = new ArrayList<String>();
	@Expose
	private Object object;
	@Expose
	private List<String> properties;
	
	public ServiceResult(){
		this.setSuccess(true);
	}
	
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
	public List<String> getProperties() {
		return properties;
	}
	public void setProperties(List<String> properties) {
		this.properties = properties;
	}	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public void addError(String message){
		this.setSuccess(false);
		this.errors.add(message);
	}
	public String getFirstMessage(){
		String result = "";
		if(messages.size() > 0){
			return messages.get(0);
		}
		return result;
	}
	public String getFirstError(){
		String result = "";
		if(errors.size() > 0){
			return errors.get(0);
		}
		return result;
	}
	
}
