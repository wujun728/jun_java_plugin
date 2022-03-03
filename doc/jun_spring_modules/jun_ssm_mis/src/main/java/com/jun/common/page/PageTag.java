package com.jun.common.page;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义分页标签
 * @author Wujun
 * @createTime   2011-11-22 下午09:12:35
 */
@SuppressWarnings("unchecked")
public class PageTag extends TagSupport{
	/**
	 * 页面表单name
	 */
	private String formName;
	/**
	 * 尾页
	 */
	private static final String LAST_PAGE_KEY = "LAST_PAGE_KEY";
	/**
	 * 下一页
	 */
	private static final String NEXT_PAGE_KEY = "NEXT_PAGE_KEY";
	/**
	 * 上一页
	 */
	private static final String PRE_PAGE_KEY = "PRE_PAGE_KEY";
	/**
	 * 首页
	 */
	private static final String FIRST_PAGE_KEY = "FIRST_PAGE_KEY";
	/**
	 * 总记录数
	 */
	private static final String TOTAL_RECORD_KEY = "TOTAL_RECORD_KEY";
	/**
	 * 第
	 */
	private static final String NO_KEY = "NO_KEY";
	/**
	 * 页
	 */
	private static final String PAGE_KEY = "PAGE_KEY";
	/**
	 * 没有记录
	 */
	private static final String NO_RESULT_KEY = "NO_ROW_KEY";
	
	public String getFormName() {
		if(null == formName){
			formName = "";
		}
		return formName.trim();
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * 根据本地环境获取资源Map(暂只支持中英文)
	 */
	private Map getResourceMap(Locale locale){
		Map map = new HashMap();
		
		//for Chinese
		if (locale.equals(Locale.CHINESE) || locale.equals(Locale.CHINA)){
			map.put(LAST_PAGE_KEY, "尾页");
			map.put(NEXT_PAGE_KEY, "下一页");
			map.put(PRE_PAGE_KEY, "上一页");
			map.put(FIRST_PAGE_KEY, "首页");
			map.put(TOTAL_RECORD_KEY, "总记录数");
			map.put(NO_KEY, "第");
			map.put(PAGE_KEY, "页");
			map.put(NO_RESULT_KEY, "没有找到任何记录");
			return map;
		}
		//default for English
		map.put(LAST_PAGE_KEY, "last");
		map.put(NEXT_PAGE_KEY, "next");
		map.put(PRE_PAGE_KEY, "prev");
		map.put(FIRST_PAGE_KEY, "frist|");
		map.put(TOTAL_RECORD_KEY, "totalRows:");
		map.put(NO_KEY, "page:");
		map.put(PAGE_KEY, "");
		map.put(NO_RESULT_KEY, "No result");
		return map;
	}
	
	@Override
	public int doStartTag() throws JspException {
		//适用于Struts1
		/*FormTag formTag = (FormTag) findAncestorWithClass(this, FormTag.class);
		String formName = formTag.getBeanName();
		//取出Form里的pageData分页对象
		PaginActionForm form = (PaginActionForm) TagUtils.getInstance().lookup(pageContext, formName, "request");
		if (form == null) {
			form = (PaginActionForm) TagUtils.getInstance().lookup(pageContext, formName, "session");
		}
		PageData pageData = form.getPaginator();
		*/
		//适用于Struts1 end
		
		
		//适用于Struts2
		PageData pageData = (PageData)TagUtils.getInstance().lookup(pageContext, "pageData", "request");
		if(null == pageData){
			pageData = (PageData)TagUtils.getInstance().lookup(pageContext, "pageData", "session");
		    if(null == pageData){
		    	pageData = new PageData();
		    }
		}
		//适用于Struts2 end
		
		//获取request的Locale, 装载资源MAP
		Locale locale= pageContext.getRequest().getLocale();
		Map resourceMap = getResourceMap(locale);

		//判断是否显示
		if(null == pageData){
			pageContextWrite((String)resourceMap.get(NO_RESULT_KEY));
		} else{
			if (pageData.getPageSize() > 0) {
				if (pageData.getTotalRows() > 0) {
					printNavigation(pageData, resourceMap);
				} else {
					pageContextWrite((String)resourceMap.get(NO_RESULT_KEY));
				}
			}
		}
		
		//打印隐藏翻页字段及翻页JavaScript函数
		printTurnPageJavaScript(pageData,getFormName());
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * 获取当前页码和总页数字符串，效果类似于：第2/16页
	 */
	private String getCurrentPage(PageData pageData, Map resourceMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(
			resourceMap.get(NO_KEY)).append(pageData.getCurrentPage())
			.append("/").append(pageData.getTotalPage())
			.append(resourceMap.get(PAGE_KEY));
		return buf.toString();
	}
	
	/**
	 * 获取总记录数字符串，效果类似于：总记录数：999
	 */
	private String getTotalPages(PageData pageData, Map resourceMap) {
		StringBuffer buf = new StringBuffer();
		buf.append(resourceMap.get(TOTAL_RECORD_KEY)).append(pageData.getTotalRows());
		return buf.toString();
	}
	
	/**
	 * 获取分页导航部分，效果类似于：首页 上一页 下一页 尾页
	 */
	private String getNavigations(PageData pageData, Map resourceMap) {	
		StringBuffer buf = new StringBuffer();
		//首页
		buf.append(getHref(pageData, 1, (String)resourceMap.get(FIRST_PAGE_KEY)));
		buf.append("&nbsp;");
		//上一页
		buf.append(getHref(pageData, pageData.getCurrentPage() - 1, (String)resourceMap.get(PRE_PAGE_KEY)));
		buf.append("&nbsp;");
		//下一页
		buf.append(getHref(pageData, pageData.getCurrentPage() + 1, (String)resourceMap.get(NEXT_PAGE_KEY)));
		buf.append("&nbsp;");
		//尾页
		buf.append(getHref(pageData, pageData.getTotalPage(), (String)resourceMap.get(LAST_PAGE_KEY)));
		buf.append("&nbsp;");
		return buf.toString();
	}
	
	/**
	 * 页码跳转选择框
	 * @param    pageData 分页器对象
	 */
	private String getJumpControl(PageData pageData) {
		
		String turnPageNum = pageData.getCurrentPage()+"";
		int turnPage = -1;
		if (turnPageNum != null && turnPageNum.trim().length() != 0) {
			turnPage = Integer.parseInt(turnPageNum);
		}

		StringBuffer buf = new StringBuffer();
		buf.append("\n<select onChange=\"javascript:goPage(this.value)\" >\n");
		for (int i = 1; i <= pageData.getTotalPage(); i++) {
			buf.append("<option value=").append(i);
			// 如果翻页参数等于当前页则选择该选项
			if (turnPage == i) {
				buf.append(" selected");
			}
			buf.append(">").append(i).append("</option>\n");
		}
		buf.append("</select>\n");
		return buf.toString();
	}

	
	/**
	 * 获取当前页的超链接
	 * @param      pageData  分页器对象
	 * @param      pageNum   当前页码
	 * @param      textBody  当前页的文字显示部分
	 */
	private String getHref(PageData pageData, int pageNum, String textBody) {
		if (pageNum < 1 || pageNum > pageData.getTotalPage() 
				|| pageNum == pageData.getCurrentPage()) {
			return textBody;
		} else {
			StringBuffer buf = new StringBuffer();
			buf.append("<a href=\"");
			buf.append("javascript:goPage(").append(pageNum).append(");");
			buf.append("\">");
			buf.append(textBody);
			buf.append("</a>");
			return buf.toString();
		}
	}
	
    /**
     * 打印隐藏翻页字段及翻页JavaScript函数
     * @param pageData   分页器对戏
     * @param formName   表单名
     */
	private void printTurnPageJavaScript(PageData pageData,String formName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n<input type=\"hidden\" name=\"pageData.currentPage\" id=\"pageData.currentPage\" value=\""+pageData.getCurrentPage()+"\">\n")
		      .append("<script type=\"text/javascript\">\n")
		      .append("function goPage(pageNum) {\n");
		if(!formName.equals("")){
			buffer.append("document.").append(formName).append(".elements[\"pageData.currentPage\"].value=pageNum;\n");
			buffer.append("document.").append(formName).append(".submit();\n");
		} else{
			buffer.append("document.forms[0]").append(".elements[\"pageData.currentPage\"].value=pageNum;\n");
			buffer.append("document.forms[0]").append(".submit();\n");
		}
		buffer.append("}\n")
			  .append("</script>\n");
		//隐藏设置每页行数
		buffer.append("\n<input type='hidden' name='pageData.pageSize' value='"+pageData.getPageSize()+"'>\n");
		pageContextWrite(buffer.toString());
	}
	
	/**
	 * 打印输出到前端页面
	 * @param html 待输出的HTML片段
	 */
	private void pageContextWrite(String html) {
		try {
			TagUtils.getInstance().write(pageContext, html);
		} catch (Exception e) {
			throw new RuntimeException("pageContext write IO exception");
		}
	}
	
	/**
	 * 打印整个分页导航条
	 * @param    pageData      分页器对象
	 * @param    resourceMap   存放分页组件显示字符的容器
	 */
	private void printNavigation(PageData pageData, Map resourceMap) {
		StringBuffer html_body = new StringBuffer();
		//当前页码
		html_body.append(getCurrentPage(pageData, resourceMap));
		html_body.append("&nbsp;&nbsp;");
		//总记录数
		html_body.append(getTotalPages(pageData, resourceMap));
		html_body.append("&nbsp;&nbsp;");
		//分页导航部分
		html_body.append(getNavigations(pageData, resourceMap));
		html_body.append("&nbsp;&nbsp;");
		//页码选择框部分
		html_body.append(getJumpControl(pageData));
		pageContextWrite(html_body.toString());		
	}
	
	/*
	 * 获取表单名称
	 * @param  formTag      页面表单标签UI对象
	 * @param  pageContext  页面上下文对象
	 * @return string       表单的name
	
	private String getFormName(PageContext pageContext,FormTag formTag){   
		HttpServletRequest request = (HttpServletRequest)pageContext.getResponse();
		HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
		String actionClassName = formTag.action;//action属性被protected修饰，只能子类或者同包才能访问
		return toLowerCaseInitial(actionClassName,false);
	}
	 */
	
	/** 
     * 将一个字符串的首字母改为大写或者小写 
     * 
     * @param srcString      源字符串 
     * @param flag           大小写标识，ture小写，false大写 
     * @return 改写后的新字符串 
     */ 
    private String toLowerCaseInitial(String srcString, boolean flag) { 
        StringBuilder sb = new StringBuilder(); 
        if (flag) { 
            sb.append(Character.toLowerCase(srcString.charAt(0))); 
        } else { 
            sb.append(Character.toUpperCase(srcString.charAt(0))); 
        } 
        sb.append(srcString.substring(1)); 
        return sb.toString(); 
    } 
}
