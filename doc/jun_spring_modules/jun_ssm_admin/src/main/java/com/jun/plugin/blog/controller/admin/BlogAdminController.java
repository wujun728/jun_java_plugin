package com.jun.plugin.blog.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jun.plugin.blog.entity.Blog;
import com.jun.plugin.blog.entity.PageBean;
import com.jun.plugin.blog.lucene.BlogIndex;
import com.jun.plugin.blog.service.BlogService;
import com.jun.plugin.blog.util.ResponseUtil;
import com.jun.plugin.blog.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * ����Ա����Controller��
 * @author Wujun
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	
	// ��������
	private BlogIndex blogIndex=new BlogIndex();
	
	/**
	 * ��ӻ����޸Ĳ�����Ϣ
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog,HttpServletResponse response)throws Exception{
		int resultTotal=0; // �����ļ�¼����
		if(blog.getId()==null){
			resultTotal=blogService.add(blog);
			blogIndex.addIndex(blog); // ��Ӳ�������
		}else{
			resultTotal=blogService.update(blog);
			blogIndex.updateIndex(blog); // ���²�������
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * ��ҳ��ѯ������Ϣ
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,Blog s_blog,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> blogList=blogService.list(map);
		Long total=blogService.getTotal(map);
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * ɾ��������Ϣ
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			blogService.delete(Integer.parseInt(idsStr[i]));
			blogIndex.deleteIndex(idsStr[i]); // ɾ����Ӧ���͵�����
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * ͨ��ID����ʵ��
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		Blog blog=blogService.findById(Integer.parseInt(id));
		JSONObject jsonObject=JSONObject.fromObject(blog);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	
	
}
