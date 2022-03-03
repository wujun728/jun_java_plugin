package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.util.DirectiveUtils;
import org.springrain.cms.util.SiteUtils;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("wxJsapiConfDirective")
public class WxJsapiConfigDirective extends AbstractCMSDirective{
	
	/**
	 * 模板名称
	 */
	private static final String TPL_NAME = "wxJsapiConf";
	@Resource
	private IWxMpConfigService wxMpConfigService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, String> wxJsapiConf = null;
		try {
			//String url = request.getRequestURL().toString()+(request.getQueryString()==null?"":"?"+request.getQueryString());
			String url = SiteUtils.getRequestURL(getRequest());
			wxJsapiConf = wxMpConfigService.findMpJsApiParam(wxMpConfigService.findWxMpConfigById(getSiteId(params)),url);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		env.setVariable("wxJsapiConf", DirectiveUtils.wrap(wxJsapiConf));
		if (body != null) { 
			body.render(env.getOut());  
		}
	}
	
	
	@PostConstruct
	public void  registerFreeMarkerVariable(){
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
}
