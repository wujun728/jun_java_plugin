package com.jd.ssh.sshdemo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jd.ssh.sshdemo.bean.Pager;
import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.service.AdminService;
import com.jd.ssh.sshdemo.util.FileUploadUtils;


@Controller
public class UsersController {

	@Resource
	private AdminService adminService;
	
	//返回一个列表循环到前台
    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ModelAndView list(HttpServletRequest request, Model model) {

        System.out.println("------");
        ModelAndView mav = new ModelAndView("users/list");
		List<Admin> users = adminService.getAll();
		
		System.out.println(users.size());
		mav.addObject("users", users);
        
        return mav;
    }
    
    @RequestMapping("/users/{id}")
	public ModelAndView form(@PathVariable("id") String id,HttpServletRequest request) {
		System.out.println(id);

		ModelAndView mav = new ModelAndView("users/form");
		Admin users = adminService.get(id);
		mav.addObject("user", users);
		mav.addObject("keyId", users.getId());
	    return mav;
	}
    
    @RequestMapping(value = "/users/create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new Admin());
		model.addAttribute("action", "create");
		return "users/form";
	}
    
    @RequestMapping(value = "/users/create" ,method=RequestMethod.POST)
	public String add(Admin user,HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes){
    	
    	System.out.println(user.getName());
    	
    	user.setIsAccountEnabled(true);
    	user.setIsAccountExpired(false);
    	user.setIsCredentialsExpired(false);
    	user.setIsAccountLocked(false);
    	user.setPassword("password");
    	adminService.save(user);
    	redirectAttributes.addFlashAttribute("message", "账号已添加");
		return "redirect:/users";
    }
    
    //读取param
    @RequestMapping(value = "/users/modify" ,method=RequestMethod.POST)
	public String modify(Admin user,HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam String name) {
    	String name1 = request.getParameter("name");
    	
    	System.out.println(name1);
    	redirectAttributes.addFlashAttribute("message", "账号已修改");
		return "redirect:/users";
    }
    
    // 例子1：@RequestMapping(value=“/header/test1”, headers = “Accept”)：
    //表示请求的URL必须为“/header/test1”且请求头中必须有Accept参数才能匹配。
    //http://.............? // -. post Accept=123
    @RequestMapping(value="/header/test1", headers = "Accept")
    public String header(Admin user,HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes,@RequestParam String name) {
    	String name1 = request.getParameter("name");
    	
    	System.out.println(name1);
    	redirectAttributes.addFlashAttribute("message", "账号已修改");
		return "redirect:/users";
    }
    
    //验证框架 通过注解到Users上面
    //@NotNull(message="{username.not.empty}")
    //private String username;
    //$(error)
    /*@RequestMapping("/validate/hello")
    public String validate(@Valid @ModelAttribute("user")Users user,Errors errors){
    	if(errors.hasErrors()) {
    			return "validate/error"; }
    	return "redirect:/success";
    }}*/
    
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(
			HttpServletRequest request,
			@RequestParam(value = "myFile", required = false) MultipartFile[] files) {
		try {
			for (int i = 0; i < files.length; i++) {
				FileUploadUtils.upload(request, files[i], null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	@RequestMapping("/rest/json")
	@ResponseBody
	public Object json(HttpServletRequest request) {

		Pager pager = new Pager(0);
		pager = adminService.findByPager(pager);
		List<Admin> listAdmin = (List<Admin>) pager.getList();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xumin");
		map.put("no", "001");

		return listAdmin;
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {  
        String header = request.getHeader("X-Requested-With");  
        if (header != null && "XMLHttpRequest".equals(header))  
            return true;  
        else  
            return false;  
    }  
}

/*
 * 拦截器是单例的，线程不安全
 *  
1、@RequestParam绑定单个请求参数值；
2、@PathVariable绑定URI模板变量值；
3、@CookieValue绑定Cookie数据值
4、@RequestHeader绑定请求头数据；
5、@ModelValue绑定参数到命令对象；
6、@SessionAttributes绑定命令对象到session；
7、@RequestBody绑定请求的内容区数据并能进行自动类型转换等。
8、@RequestPart绑定“multipart/data”数据，除了能绑定@RequestParam能做到的请求参数外，还能绑定上传的文件等。

@Controller：用于标识是处理器类；
@RequestMapping：请求到处理器功能方法的映射规则；
@RequestParam：请求参数到处理器功能处理方法的方法参数上的绑定；
@ModelAttribute：请求参数到命令对象的绑定；
@SessionAttributes：用于声明session级别存储的属性，放置在处理器类上，通常列出模型
属性（如@ModelAttribute）对应的名称，则这些属性会透明的保存到session中；
@InitBinder：自定义数据绑定注册支持，用于将请求参数转换到命令对象属性的对应类型；
注意：需要通过处理器映射DefaultAnnotationHandlerMapping和处理器适配器
AnnotationMethodHandlerAdapter来开启支持@Controller 和@RequestMapping注解的处
理器。


例子：public String test1(@ModelAttribute("user") UserModel user)
说明：1：和前面命令/表单对象一样，只是此处多了一个注解@ModelAttribute(“user”)，
它的作用是将该绑定的命令对象以“user”为名称添加到模型对象中供视图页面展示使
用。我们此时可以在视图页面使用${user.username}来获取绑定的命令对象的属性。


 * */
