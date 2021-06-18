package cn.yunhe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import cn.yunhe.model.Result;
import cn.yunhe.model.User;
import cn.yunhe.service.IUserService;

@RestController  
/**
 * 1.如果一个controller里面大部分返回的都是页面的话，使用@Controller，这时返回的都是页面
 * 2.如果大部分返回的都是Json或字符串的话，就使用@RestController。如果它碰到Stirng，就返回String
 * int返回int（即基本数据类型还返回相应的数据类型），但是对象会返回json
 * 3.此时(使用@RestController)如果某方法仍要返回视图，则使用ModelAndView
 */
public class UserController {

	@Resource
	private IUserService userService;

	@RequestMapping("/login")
	public ModelAndView toLogin() {
		return new ModelAndView("login");
	}

	@RequestMapping("/userLogin")
	public String userLogin(User user, HttpSession session, Model model) {
		Integer flag = userService.selectByNameAndPass(user);
		if (flag != null) {
			session.setAttribute("user", user);
			return "redirect:/user/userlist";
		} else {
			model.addAttribute("msg", "登录失败");
			return "login";
		}

	}

	@RequestMapping("/userlist")
	public String findAll(Model model) {
		List<User> list = userService.selectAll();
		model.addAttribute("list", list);
		return "list";

	}
	
	/**
	 * 分页
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getPage")
	public Map queryUsersByPage(int pageNum, int pageSize,User user) {		
		Map map=new HashMap();
 		map.put("pageSize", pageSize);
 		map.put("pageNum", pageNum);
 		map.put("user", user);
 		Page page=userService.queryLikeUsers(map);
 		map.put("page", page);
 		map.put("totalPage", page.getPages());
		return map;
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("list");
	}
	
	
	/**
	 * 查询单个用户详细信息
	 * 
	 * @return
	 */
	@RequestMapping("/editpage")
	public ModelAndView findUser(User user){
		User rsUser = userService.getUser(user);
        Map<String,Object> rsMap = new HashMap<String,Object>();
        rsMap.put("data", rsUser);
		return new ModelAndView("edit",rsMap);
	}
	
	@RequestMapping("/edit")
	public Result editUser(User user){
		userService.editUser(user);
		return new Result();
	}
	

	@RequestMapping("/add")
	public Result addUser(User user){
		userService.addUser(user);
		return new Result();
	}
	
	
	@RequestMapping("/del")
	public Result delUser(User user){
		userService.delUser(user);
		return new Result();
	}
	
	
	
	@RequestMapping("/get/{id}")
	public String getUser(@PathVariable int id, Model model) {
		User user = userService.selectByPrimaryKey(id);
		model.addAttribute("u", user);
		return "user_detail";
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
