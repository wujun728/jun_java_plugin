package org.springrain.cms.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
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
@Component("cmsJsoupHtmlDirective")
public class CmsJsoupHtmlDirective extends AbstractCMSDirective{

	private static final String TPL_NAME = "cms_jsoup_html";
	
	@Resource
	private ICmsContentService cmsContentService;
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String url = DirectiveUtils.getString("url", params);
		String content = DirectiveUtils.getString("content", params);
		String method = DirectiveUtils.getString("method", params);
		String select=DirectiveUtils.getString("select", params);
		String returnType=DirectiveUtils.getString("returnType", params);
		
		
		//Controller 已经通过 addModelParameter 方法,把所有的参数传递到了前台
		
		
		
		String tagName=DirectiveUtils.getString("tagName", params);
		if(StringUtils.isBlank(tagName)){
			tagName="jsouphtml";
		}
		
		
		Document document=null;
		
		
		if(StringUtils.isNotBlank(url)){
			Connection connect = Jsoup.connect(url);
			if(StringUtils.isBlank(method)||"get".equals(method)){
				document=connect.get();
			}else{
				document=connect.post();
			}
			
		}else{
			document=Jsoup.parse(content);
		}
		
		
		if(document==null){
			body.render(env.getOut());
			return;
		}
		
		
		
		
		Object _s=null;
		
		if(StringUtils.isNotBlank(select)){
			
			if("list".equals(returnType)){
				_s = document.select(select);
				
			}else{
				_s = document.select(select).first();
			}
			
			
			
		}else{
			_s=document;
		}
		env.setVariable(tagName, DirectiveUtils.wrap(_s));
		if(body!=null){
			body.render(env.getOut());
		}
	}
	
	@PostConstruct
	public void registerFreeMarkerVariable() {
		setFreeMarkerSharedVariable(TPL_NAME, this);
	}

}
