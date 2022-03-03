package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsChannel;
import org.springrain.cms.service.ICmsChannelService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 栏目信息
 * @author dmin93
 * @date 2017年3月23日
 */
@Component("cmsChannelDirective")
public class CmsChannelDirective extends AbstractCMSDirective{

	private static final String TPL_NAME = "cms_channel";
	
	private static final String PARAM_ID = "id";
	
	@Resource
	private ICmsChannelService cmsChannelService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			CmsChannel channel;
			String id = DirectiveUtils.getString(PARAM_ID, params);
			String siteId = this.getSiteId(params);
			channel = cmsChannelService.findCmsChannelById(id,siteId);
			
			env.setVariable(DirectiveUtils.OUT_BEAN, DirectiveUtils.wrap(channel));
			if(body!=null){
				body.render(env.getOut());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}

	@PostConstruct
	public void registerFreeMarkerVariable() {
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
	
}
