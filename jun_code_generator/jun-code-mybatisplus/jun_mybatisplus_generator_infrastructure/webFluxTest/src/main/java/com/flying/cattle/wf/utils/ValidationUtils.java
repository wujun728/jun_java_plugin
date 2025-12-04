/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;


/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 验证对象工具类</P>
 * @version: V1.0
 * @author: BianPeng
 * 
 */
public class ValidationUtils {
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	 
    public static <T> ValidationResult validateEntity(T obj) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            int errornumber=1;
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(cv.getPropertyPath().toString(), cv.getMessage());
                if (errornumber==1) {
                	result.setFirstErrors(cv.getMessage());
                	errornumber++;
				}
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
 
    public static <T> ValidationResult validateProperty(T obj, String propertyName) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<T>> set = validator.validateProperty(obj, propertyName, Default.class);
        if (set != null && set.size() != 0) {
            result.setHasErrors(true);
            Map<String, String> errorMsg = new HashMap<String, String>();
            int errornumber=1;
            for (ConstraintViolation<T> cv : set) {
                errorMsg.put(propertyName, cv.getMessage());
                if (errornumber==1) {
                	result.setFirstErrors(cv.getMessage());
                	errornumber++;
				}
            }
            result.setErrorMsg(errorMsg);
        }
        return result;
    }
}
