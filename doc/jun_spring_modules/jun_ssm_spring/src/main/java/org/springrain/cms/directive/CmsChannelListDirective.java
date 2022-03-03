package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
 * 栏目列表
 * @author dmin93
 * @date 2017年3月23日
 */
@Component("cmsChannelListDirective")
public class CmsChannelListDirective extends AbstractCMSDirective {

	private static final String TPL_NAME = "cms_channel_list";
	
	@Resource
	private ICmsChannelService cmsChannelService;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		try {
			List<CmsChannel> channelList = getList(params,env,this.getSiteId(params));
			env.setVariable(DirectiveUtils.OUT_LIST, DirectiveUtils.wrap(channelList));
			if (body != null) {
				body.render(env.getOut());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取栏目列表
	 * @param params
	 * @param env
	 * @param siteId
	 * @return
	 */
	private List<CmsChannel> getList(Map params, Environment env, String siteId) throws Exception{
		List<CmsChannel> resList;
		String[] idArr = DirectiveUtils.getStringArr(PARAM_IDS, params,PARAM_SPLIT);
		if(idArr!=null && idArr.length>0){
			resList = cmsChannelService.findListByIdsForTag(Arrays.asList(idArr),siteId,params);
		}else{
			// 默认查询可用的内容
			Integer active = DirectiveUtils.getInt("active", params);
			if(active == null){
				params.put("active", DirectiveUtils.wrap(1));
			}
			
			resList = cmsChannelService.findListForTag(params,env,siteId);
		}
		return resList;
	}


	@PostConstruct
	public void registerFreeMarkerVariable() {
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
	
}
