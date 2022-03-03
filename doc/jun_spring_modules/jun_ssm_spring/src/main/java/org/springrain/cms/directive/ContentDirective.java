package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsContent;
import org.springrain.cms.service.ICmsCommentService;
import org.springrain.cms.service.ICmsContentService;
import org.springrain.cms.service.ICmsPraiseService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("contentDirective")
public class ContentDirective  extends AbstractCMSDirective  {
	
	@Resource
	private ICmsContentService cmsContentService;
	
	@Resource
	private ICmsCommentService cmsCommentService;
	@Resource
	private ICmsPraiseService cmsPraiseService;
	


	private static final String TPL_NAME = "s_content";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		
		String id = DirectiveUtils.getString("id", params);
		String siteId=getSiteId(params);
		
		String cacheKey=TPL_NAME+"_cache_key_"+siteId+"_"+id;
		
		CmsContent content=(CmsContent) getDirectiveData(cacheKey);
		if(content==null){
			try {
				content = cmsContentService.findCmsContentById(siteId,id);
				if(content!=null){
					Integer commentsNum = cmsCommentService.findCommentsNumByBusinessId(siteId,content.getId());
					Integer praiseNum = cmsPraiseService.findPraiseNumByBusinessId(siteId,content.getId());
					content.setCommentsNum(commentsNum);
					content.setPraiseNum(praiseNum);
				}else{
					content = new CmsContent();
				}
			} catch (Exception e) {
				content = null;
				logger.error(e.getMessage(), e);
			}
			setDirectiveData(cacheKey,content);
		}
		
		env.setVariable("content", DirectiveUtils.wrap(content));
		if (body != null) { 
			body.render(env.getOut());  
		}
	}
	
	@PostConstruct
	public void  registerFreeMarkerVariable(){
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}

}
