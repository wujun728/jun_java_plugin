package com.jun.plugin.annotation;

import java.lang.annotation.Annotation;

public class Phone {

	public String getParameters() {

		String result = "";
		Annotation[] attrs = this.getClass().getAnnotations();
		for (Annotation attr : attrs) {
			result += attr + "\r";
		}

		return result;

	}

}
