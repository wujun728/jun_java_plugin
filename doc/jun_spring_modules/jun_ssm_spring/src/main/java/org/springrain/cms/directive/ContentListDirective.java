package org.springrain.cms.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.util.DirectiveUtils;
import org.springrain.frame.util.Page;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("contentListDirective")
public class ContentListDirective  extends AbstractCMSDirective  {
	
	@Resource
	private ICmsContentService cmsContentService;
	
	
	
	/**
	 * 模板名称
	 */
	private static final String TPL_NAME = "content_list";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
			List<CmsContent> contentList = null;
			StringModel stringModel = (StringModel) params.get("page");
			Page page = (Page) stringModel.getAdaptedObject(Page.class);
			String type = DirectiveUtils.getString("type", params);
			
			
			try {
				if("0".equals(type)){//查询站点首页下的内容
					contentList = cmsContentService.findListBySiteId(getSiteId(params), page);
				}else if ("1".equals(type)){//查询栏目下的内容
					contentList = cmsContentService.findContentByChannelId(getSiteId(params),getBusinessId(params), page);
				}else{
					//目前暂时没有其它类型，先占位
					contentList = new ArrayList<>();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				contentList = new ArrayList<>();
			}
		
		env.setVariable("content_list", DirectiveUtils.wrap(contentList));
		if (body != null) { 
			body.render(env.getOut());  
		}
	}
	
	
	@PostConstruct
	public void  registerFreeMarkerVariable(){
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
	
}
