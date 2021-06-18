package com.ibeetl.admin.core.gen;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.ibeetl.admin.core.util.beetl.VerifyForamtFunction;

public abstract class BaseTarget implements Target {
	
	protected GroupTemplate gt = null;
	protected String urlBase = null;
	

	@Override
	public void flush(AutoGen gen, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupTemplate getGroupTemplate() {
		if(gt!=null) {
			return gt;
		}
		
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ClasspathResourceLoader rs  = new  ClasspathResourceLoader(loader,"codeTemplate");
		try {
			
			Configuration cfg = Configuration.defaultConfiguration();
			cfg.setStatementStart("@");
			cfg.setStatementEnd(null);

			cfg.setHtmlTagSupport(false);
			cfg.build();
			gt = new GroupTemplate(rs,cfg);
            gt.registerFunction("verifyFormat", new VerifyForamtFunction());
			gt.registerFunction("upperFirst", new Function() {

				@Override
				public Object call(Object[] paras, Context ctx) {
					String s = (String)paras[0];
					return upperFirst(s);
					
				}
				
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return gt;
	}

	public static String upperFirst(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}
	
	public GroupTemplate getGt() {
		return gt;
	}

	public void setGt(GroupTemplate gt) {
		this.gt = gt;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	
}
