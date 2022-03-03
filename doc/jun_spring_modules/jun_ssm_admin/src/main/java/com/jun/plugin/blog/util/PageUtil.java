package com.jun.plugin.blog.util;

/**
 * 锟斤拷页锟斤拷锟斤拷锟斤拷
 * @author Wujun
 *
 */
public class PageUtil {

	/**
	 * 锟斤拷锟缴凤拷页锟斤拷锟斤拷
	 * @param targetUrl 目锟斤拷锟街?
	 * @param totalNum 锟杰硷拷录锟斤拷
	 * @param currentPage 锟斤拷前页
	 * @param pageSize 每页锟斤拷小
	 * @return
	 */
	public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "无数据";
		}else{
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<a href='"+targetUrl+"?page=1&"+param+"'>首页 </a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a>");			
			}else{
				pageCode.append("<a href='#'>下一页</a>");		
			}
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<span class=\"laypage-curr\"><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a></span>");	
				}else{
					pageCode.append("<a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>上一页</a>");		
			}else{
				pageCode.append("<a href='#'>下一页</a>");	
			}
			pageCode.append("<a href='"+targetUrl+"?page="+totalPage+"&"+param+"'>尾页</a>");
			return pageCode.toString();
		}
	}
	

	
	
}
