package com.ibeetl.admin.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

public class FormFieldException extends PlatformException {
	List<FieldError> errors = new ArrayList<FieldError>();
    public FormFieldException() {
        super();
    }


    public FormFieldException(String objectName,String field,String error) {
        super("field "+field+" "+error);
        FieldError fields = new FieldError(objectName,field,error);
        errors.add(fields);
        
    }


	public List<FieldError> getErrors() {
		return errors;
	}


	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}

}
