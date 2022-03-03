package com.jun.plugin.blog.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.blog.entity.Blog;
import com.jun.plugin.blog.lucene.BlogIndex;
import com.jun.plugin.blog.service.BlogService;
import com.jun.plugin.blog.service.CommentService;
import com.jun.plugin.blog.util.StringUtil;

/**
 * ����Controller��
 * @author Wujun
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Resource
	private BlogService blogService;
	
	@Resource
	private CommentService commentService;
	
	// ��������
	private BlogIndex blogIndex=new BlogIndex();
	
	
	/**
	 * ���󲩿���ϸ��Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=blogService.findById(id);
		String keyWords=blog.getKeyWord();
		if(StringUtil.isNotEmpty(keyWords)){
			String arr[]=keyWords.split(" ");
			mav.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));			
		}else{
			mav.addObject("keyWords",null);			
		}
		mav.addObject("blog", blog);
		blog.setClickHit(blog.getClickHit()+1); // ���͵��������1
		blogService.update(blog);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("blogId", blog.getId());
		map.put("state", 1); // ��ѯ���ͨ��������
		mav.addObject("commentList", commentService.list(map)); 
		mav.addObject("commentListCount", commentService.list(map).size()); 
		mav.addObject("pageCode", this.genUpAndDownPageCode(blogService.getLastBlog(id),blogService.getNextBlog(id),request.getContextPath()));
		mav.addObject("mainPage", "foreground/blog/view.jsp");
		mav.addObject("pageTitle",blog.getTitle()+"博客");
		mav.setViewName("detail");
		return mav;
	}
	
	/**
	 * ���ݹؼ��ֲ�ѯ��ز�����Ϣ
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false)String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "foreground/blog/result.jsp");
		List<Blog> blogList=blogIndex.searchBlog(q.trim());
		Integer toIndex=blogList.size()>=Integer.parseInt(page)*10?Integer.parseInt(page)*10:blogList.size();
		mav.addObject("blogList",blogList.subList((Integer.parseInt(page)-1)*10, toIndex));
		mav.addObject("blogListCount",blogList.size());
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q,10,request.getContextPath()));
		mav.addObject("q",q);
		mav.addObject("resultTotal",blogList.size());
		mav.addObject("pageTitle","�����ؼ���'"+q+"'���ҳ��_Java��Դ����ϵͳ");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * ��ȡ��һƪ���ͺ���һƪ���ʹ���
	 * @param lastBlog
	 * @param nextBlog
	 * @return
	 */
	private String genUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇:&nbsp&nbsp</p>");
		}else{
			pageCode.append("<p>上一篇:&nbsp&nbsp<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇:&nbsp&nbsp</p>");
		}else{
			pageCode.append("<p>下一篇:&nbsp&nbsp<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 * ��ȡ��һҳ����һҳ���� ��ѯ�����õ�
	 * @param page ��ǰҳ
	 * @param totalNum �ܼ�¼��
	 * @param q ��ѯ�ؼ���
	 * @param pageSize ÿҳ��С
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager' >");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>��һҳ</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>��һҳ</a></li>");				
			}else{
				pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");				
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
}
