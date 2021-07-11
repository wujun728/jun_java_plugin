package com.buxiaoxia.utils.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE)
public class DataBindingException extends RuntimeException {
	private static final long serialVersionUID = 4886698281191876208L;

	@JsonProperty
	private boolean success = true;

	@JsonProperty
	private List<String> globalErrors = new ArrayList<String>();

	@JsonProperty
	private Map<String, List<String>> fieldErrors = new HashMap<String, List<String>>();

	public DataBindingException(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			success = false;
			for (ObjectError oe : bindingResult.getGlobalErrors()) {
				this.globalErrors.add(oe.getDefaultMessage());
			}

			for (FieldError fe : bindingResult.getFieldErrors()) {
				String f = fe.getField();

				if (fieldErrors.get(f) != null) {
					fieldErrors.get(f).add(fe.getDefaultMessage());
				} else {
					List<String> list = new LinkedList<String>();
					list.add(fe.getDefaultMessage());
					fieldErrors.put(f, list);
				}
			}
		}
	}

	
	
	
	public DataBindingException(String message) {
		super(message);
	}




	// getter & setter
	// ----------------------------------------------------------------------------------------------------
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<String> getGlobalErrors() {
		return globalErrors;
	}

	public void setGlobalErrors(List<String> globalErrors) {
		this.globalErrors = globalErrors;
	}

	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
