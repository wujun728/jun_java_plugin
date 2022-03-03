package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("siteDirective")
public class CmsSiteDirective extends AbstractCMSDirective {
	
	@Resource
	private ICmsSiteService cmsSiteService;
	
	
	private static final String TPL_NAME = "cms_site";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String siteId=DirectiveUtils.getString("id", params);
		
		String cacheKey=TPL_NAME+"_cache_key_"+siteId;
		
		
		CmsSite site = (CmsSite) getDirectiveData(cacheKey);
		if(site == null){
			try {
				site = cmsSiteService.findCmsSiteById(siteId);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				site = new CmsSite();
			}
			setDirectiveData(cacheKey,site);
		}
		
		env.setVariable("site", DirectiveUtils.wrap(site));
		if (body != null) { 
			body.render(env.getOut());  
		}
	}
	
	
	@PostConstruct
	public void  registerFreeMarkerVariable(){
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}

}
