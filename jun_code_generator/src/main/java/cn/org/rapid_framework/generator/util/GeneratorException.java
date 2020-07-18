package cn.org.rapid_framework.generator.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class GeneratorException extends RuntimeException{
	
	public List<Exception> exceptions = new ArrayList();
	
	public GeneratorException() {
		super();
	}

	public GeneratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneratorException(String message) {
		super(message);
	}

	public GeneratorException(Throwable cause) {
		super(cause);
	}
	

	public List<Exception> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<Exception> exceptions) {
		if(exceptions == null) throw new NullPointerException("'exceptions' must be not null");
		this.exceptions = exceptions;
	}
	
	public GeneratorException add(Exception e) {
		exceptions.add(e);
		return this;
	}
	
	public GeneratorException addAll(List<Exception> excetpions) {
		exceptions.addAll(excetpions);
		return this;
	}

	@Override
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	@Override
	public void printStackTrace(PrintStream s) {
		s.println("GeneratorException:"+getMessage());
		for(Exception e : exceptions) {
		    e.printStackTrace(s);
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		s.println("GeneratorException:"+getMessage());
		for(Exception e : exceptions) {
		    e.printStackTrace(s);
		}
	}
	
	public String toString() {
		StringWriter out = new StringWriter();
		for(Exception e : exceptions) {
		    out.append(e.toString()+"\n");
		}
		return out.toString();
	}
	
	
	
}
