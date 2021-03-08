package com.roncoo.jui.web.custom;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration.FreeMarkerWebConfiguration;
import org.springframework.context.annotation.Configuration;

import com.roncoo.shiro.freemarker.ShiroTags;

@Configuration
public class ShiroFreeMarkerWebConfiguration extends FreeMarkerWebConfiguration {

	@Autowired
	private freemarker.template.Configuration configuration;

	@PostConstruct
	public void setSharedVariable() {
		try {
			configuration.setSharedVariable("shiro", new ShiroTags());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
