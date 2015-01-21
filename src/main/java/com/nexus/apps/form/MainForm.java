package com.nexus.apps.form;

import java.util.ArrayList;
import java.util.List;

public class MainForm {

	List<String> errors = new ArrayList<String>();
	List<String> warnings = new ArrayList<String>();
	List<String> infos = new ArrayList<String>();
	boolean hasError = false;
	boolean hasWarning = false;
	boolean hasInfo = false;
	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
		this.hasError = getHasError();
	}
	public List<String> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
		this.hasWarning = getHasWarning();
	}
	public List<String> getInfos() {
		return infos;
	}
	public void setInfos(List<String> infos) {
		this.infos = infos;
		this.hasInfo = getHasInfo();
	}

	public boolean isHasError() {
		return hasError;
	}

	public boolean isHasWarning() {
		return hasWarning;
	}

	public boolean isHasInfo() {
		return hasInfo;
	}

	
	public void setError(String error) {
		this.hasError = true;
		this.errors.add(error);
	}

	public void setWarning(String warning) {
		this.hasWarning = true;
		this.warnings.add(warning);
	}

	public void setInfo(String info) {
		this.hasInfo = true;
		this.infos.add(info);
	}
	
	public boolean getHasError(){
		this.hasError = errors.isEmpty() ? false : true;
		return hasError;
	}
	
	public boolean getHasWarning(){
		this.hasWarning = warnings.isEmpty() ? false : true;
		return hasWarning;
	}
	
	public boolean getHasInfo(){
		this.hasInfo = infos.isEmpty() ? false : true;
		return hasInfo;
	}
	
	
}
