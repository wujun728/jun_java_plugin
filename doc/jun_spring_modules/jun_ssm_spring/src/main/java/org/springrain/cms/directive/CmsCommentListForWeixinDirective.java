package org.springrain.cms.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsComment;
import org.springrain.cms.service.ICmsCommentService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 通过微信用户ID与站点信息、业务信息获取微信用户的评论
 * @author dmin93
 * @date 2017年4月6日
 */
@Component("cmsCommentListForWeixinDirective")
public class CmsCommentListForWeixinDirective extends AbstractCMSDirective{

	private static final String TPL_NAME = "cms_comment_list_weixin";

	@Resource
	private ICmsCommentService cmsCommentService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			List<CmsComment> commentList;
			String siteId = this.getSiteId(params);
			String businessId = this.getBusinessId(params);
			String openId = String.valueOf(this.getRequest().getSession().getAttribute("openId"));
			
			if(StringUtils.isNotEmpty(siteId) && StringUtils.isNotEmpty(businessId) &&
					StringUtils.isNotEmpty(openId)){
				commentList = cmsCommentService.findListByOpenId(
						openId,siteId,businessId,DirectiveUtils.getInt("type", params));
			}else{
				commentList = null;
			}
			env.setVariable(DirectiveUtils.OUT_LIST, DirectiveUtils.wrap(commentList));
			if (body != null) {
				body.render(env.getOut());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	@PostConstruct
	public void registerFreeMarkerVariable() {
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
	
}
