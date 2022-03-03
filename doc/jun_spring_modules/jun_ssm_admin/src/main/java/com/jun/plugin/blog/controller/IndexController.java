package com.jun.plugin.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.blog.entity.Blog;
import com.jun.plugin.blog.entity.PageBean;
import com.jun.plugin.blog.service.BlogService;
import com.jun.plugin.blog.util.PageUtil;
import com.jun.plugin.blog.util.StringUtil;

/**
 * ��ҳController
 * 
 * @author Wujun
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Resource
	private BlogService blogService;

	/**
	 * ������ҳ
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		List<Blog> blogList = blogService.list(map);
		for (Blog blog : blogList) {
			List<String> imagesList = blog.getImagesList();
			String blogInfo = blog.getContent();
			Document doc = Jsoup.parse(blogInfo);
			Elements jpgs = doc.select("img[src$=.jpg]"); // ��������չ����jpg��ͼƬ
			for (int i = 0; i < jpgs.size(); i++) {
				Element jpg = jpgs.get(i);
				imagesList.add(jpg.toString());
				if (i == 2) {
					break;
				}
			}
		}
		mav.addObject("blogList", blogList);
		StringBuffer param = new StringBuffer(); // ��ѯ����
		if (StringUtil.isNotEmpty(typeId)) {
			param.append("typeId=" + typeId + "&");
		}
		if (StringUtil.isNotEmpty(releaseDateStr)) {
			param.append("releaseDateStr=" + releaseDateStr + "&");
		}
		mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath() + "/index.html",
				blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
		mav.addObject("mainPage", "foreground/blog/list2.jsp");
		mav.addObject("pageTitle", "博客列表");
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * ������ҳ
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articlelist")
	public ModelAndView articlelist(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		List<Blog> blogList = blogService.list(map);
		for (Blog blog : blogList) {
			List<String> imagesList = blog.getImagesList();
			String blogInfo = blog.getContent();
			Document doc = Jsoup.parse(blogInfo);
			Elements jpgs = doc.select("img[src$=.jpg]"); // ��������չ����jpg��ͼƬ
			for (int i = 0; i < jpgs.size(); i++) {
				Element jpg = jpgs.get(i);
				imagesList.add(jpg.toString());
				if (i == 2) {
					break;
				}
			}
		}
		mav.addObject("blogList", blogList);
		StringBuffer param = new StringBuffer(); // ��ѯ����
		if (StringUtil.isNotEmpty(typeId)) {
			param.append("typeId=" + typeId + "&");
		}
		if (StringUtil.isNotEmpty(releaseDateStr)) {
			param.append("releaseDateStr=" + releaseDateStr + "&");
		}
		mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath() + "/articlelist.html",
				blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.addObject("pageTitle", "博客列表");
		mav.setViewName("articlelist");
		return mav;
	}

	/**
	 * ������ҳ
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/timeline")
	public ModelAndView timeline(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("timeline");
		return mav;
	}
	
	@RequestMapping("/production")
	public ModelAndView production(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("production");
		return mav;
	}
	@RequestMapping("/about")
	public ModelAndView about(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("about");
		return mav;
	}
	@RequestMapping("/comment")
	public ModelAndView comment(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("comment");
		return mav;
	}
	@RequestMapping("/note")
	public ModelAndView note(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("note");
		return mav;
	}
	@RequestMapping("/record")
	public ModelAndView record(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("record");
		return mav;
	}
	@RequestMapping("/foot")
	public ModelAndView foot(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
					throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		mav.setViewName("particle/foot.jsp");
		return mav;
	}

	/**
	 * Դ������
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "foreground/system/download.jsp");
		mav.addObject("pageTitle", "正在下载");
		mav.setViewName("mainTemp");
		return mav;
	}
}
