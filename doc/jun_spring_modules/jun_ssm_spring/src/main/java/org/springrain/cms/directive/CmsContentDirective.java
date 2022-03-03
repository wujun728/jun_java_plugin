package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 内容
 * @author dmin93
 * @date 2017年3月8日
 */
@Component("cmsContentDirective")
public class CmsContentDirective extends AbstractCMSDirective{

	private static final String TPL_NAME = "cms_content";

	/**
	 * 输入参数，内容ID
	 */
	public static final String PARAM_ID = "id";
	/**
	 * 输入参数，下一篇
	 */
	public static final String PARAM_NEXT = "next";
	/**
	 * 输入参数，栏目ID
	 */
	public static final String PARAM_CHANNEL_ID = "channelId";
	
	@Resource
	private ICmsContentService cmsContentService;
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			CmsContent cmsContent;
			
			Boolean next = DirectiveUtils.getBool(PARAM_NEXT, params);
			String id = DirectiveUtils.getString(PARAM_ID, params);
			String siteId = this.getSiteId(params);
			String channelId = DirectiveUtils.getString(PARAM_CHANNEL_ID, params);
			if(next != null ){
				cmsContent = cmsContentService.findCmsContentSide(id,siteId,channelId,next);
			}else{
				cmsContent = cmsContentService.findCmsContentById(siteId, id);
			}
			
			env.setVariable(DirectiveUtils.OUT_BEAN, DirectiveUtils.wrap(cmsContent));
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
