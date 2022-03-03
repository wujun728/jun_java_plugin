package org.springrain.cms.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springrain.cms.entity.CmsProperty;
import org.springrain.cms.service.ICmsPropertyService;
import org.springrain.cms.util.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("cmsPropertyDirective")
public class CmsPropertyDirective extends AbstractCMSDirective {

	@Resource
	private ICmsPropertyService cmsPropertyService;
	
	/**
	 * 模板名称
	 */
	private static final String TPL_NAME = "cms_property_list";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String businessId = DirectiveUtils.getString("businessId", params);
		if(StringUtils.isBlank(businessId)) {
            businessId = getBusinessId(params);
        }
		String cacheKey=TPL_NAME+"_cache_key_"+businessId;
		
		List<CmsProperty> list = (List<CmsProperty>) getDirectiveData(cacheKey);
		if(list == null){
			try {
				list = cmsPropertyService.findByBusinessId(businessId,
						null);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				list = new ArrayList<>();
			}
			setDirectiveData(cacheKey,list);
		}
		
		env.setVariable("propertyList", DirectiveUtils.wrap(list));
		if (body != null) {
			body.render(env.getOut());
		}
	}
	
	
	@PostConstruct
	public void  registerFreeMarkerVariable(){
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}
	
	

}
