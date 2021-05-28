package com.itstyle.common.config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 通用访问拦截匹配
 * 创建者 爪哇笔记
 * 创建时间	2019年5月25日
 */
@Controller
public class IndexController {
	
	/**
	 * 页面跳转
	 * @param module
	 * @param url
	 * @return
	 */
	@RequestMapping("{url}.shtml")
	public String page(@PathVariable("url") String url) {
		return  url;
	}
	/**
	 * 页面跳转(一级目录)
	 * @param module
	 * @param function
	 * @param url
	 * @return
	 */
	@RequestMapping("{module}/{url}.shtml")
	public String page(@PathVariable("module") String module,@PathVariable("url") String url) {
		return module + "/" + url;
	}
	/**
	 * 页面跳转（二级目录)
	 * @Author	张志朋
	 * @param module
	 * @param url
	 * @return  String
	 * @Date	2019年1月25日
	 * 更新日志
	 * 2019年1月25日 张志朋  首次创建
	 */
	@RequestMapping("{module}/{sub}/{url}.shtml")
	public String page(@PathVariable("module") String module,@PathVariable("sub") String sub,@PathVariable("url") String url) {
		return module + "/" + sub + "/" + url;
	}
	
}
