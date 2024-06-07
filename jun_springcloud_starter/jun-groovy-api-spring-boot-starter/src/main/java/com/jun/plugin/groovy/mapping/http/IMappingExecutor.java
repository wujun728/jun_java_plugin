package com.jun.plugin.groovy.mapping.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IMappingExecutor {
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;

}
