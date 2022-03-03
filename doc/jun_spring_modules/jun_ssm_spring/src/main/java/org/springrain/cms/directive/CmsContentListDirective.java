package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
 * 内容列表
 * 
 * @author dmin93
 * @date 2017年3月6日
 */
@Component("cmsContentListDirective")
public class CmsContentListDirective extends AbstractCMSDirective {

	private static final String TPL_NAME = "cms_content_list";

	@Resource
	private ICmsContentService cmsContentService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			
			List<CmsContent> contentList = getList(params, env, this.getSiteId(params),this.getBusinessId(params));
			env.setVariable(DirectiveUtils.OUT_LIST, DirectiveUtils.wrap(contentList));
			
			if (body != null) {
				body.render(env.getOut());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取内容列表
	 * @param params
	 * @param env
	 * @param siteId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public List<CmsContent> getList(Map params, Environment env, String siteId, String channelId)
			throws Exception {
		List<CmsContent> resList;
		String[] idArr = DirectiveUtils.getStringArr(PARAM_IDS, params,PARAM_SPLIT);
		if(idArr != null && idArr.length > 0 ){
			// 有ID以ID作为主参数进行查询，排斥其他筛选参数
			resList = cmsContentService.findListByIdsForTag(Arrays.asList(idArr),getOrderBy(params));
		} else {
			// 前台使用还是后台使用
			Boolean isFront = DirectiveUtils.getBool("isFront", params);
			if(isFront == null){
				isFront = true;
			}
			
			if(isFront){
				// 默认查询可用的内容
				Integer active = DirectiveUtils.getInt("active", params);
				if(active == null){
					params.put("active", DirectiveUtils.wrap(1));
				}
			}else{
				Integer active = DirectiveUtils.getInt("active", params);
				if(active == null){
					params.remove("active");
				}
			}
			resList = cmsContentService.findListForTag(params, env, siteId,channelId);
		}
		return resList;
	}

	@PostConstruct
	public void registerFreeMarkerVariable() {
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}

}
