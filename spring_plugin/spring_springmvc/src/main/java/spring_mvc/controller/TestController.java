package spring_mvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.Conventions;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;

import spring_mvc.model.address;
import spring_mvc.model.user;

@Controller
public class TestController {

	/*@RequestMapping("/user")
	public String user(user user){
		System.out.println(user);
		return "helloworld";
	}*/
	@RequestMapping("/toarray")
	public String arrays(String [] books){
		System.out.println(Arrays.toString(books));
		return "helloworld";
	}
	@RequestMapping("/tolist")
	public String list(@ModelAttribute("user")user user){
		/*System.out.println("list输出:"+user);*/
		List<address> list=new ArrayList<address>();
		list.add(new address("1235213a","中央大街"));
		list.add(new address("12213213a","西南大街"));
		list.add(new address("12782113a","中南大街"));
		user.setAddresss(list);
		return "hello/hellow";
	}	
	@RequestMapping("/tomap")
	public String map(user user){
		System.out.println("map输出:"+user);
		return "helloworld";
	}
	@RequestMapping("/todate")
	public String date(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
		DateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		System.out.println("date输出:"+dateFormat.format(date));
		return "helloworld";
	}
	@RequestMapping("/todate2")
	public String date2(user user){
		DateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
		System.out.println("date2输出:"+dateFormat.format(user.getBirthday()));
		return "helloworld";
	}
	@RequestMapping("/tojson")
	public String json(@RequestBody user user){
		System.out.println(user);
		return "helloworld";
	}
	@RequestMapping("/toxml")
	public String xml(@RequestBody user user){
		System.out.println(user);
		return "helloworld";
	}
	@RequestMapping(value="/request",method=RequestMethod.DELETE)
	public String Request(HttpServletRequest request,HttpServletResponse response){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String nick=request.getParameter("nick");
		System.out.println(nick);
		return "redirece:request/" + 1;
	}
	@RequestMapping("/session")
	public String session(HttpSession session,Model model){
		session.setAttribute("user", "张三");
		model.addAttribute("user","张三");
		model.addAttribute("age",18);
		return "forward:modelAndView";
	}
	@RequestMapping("/modelAndView")
	public ModelAndView modelAndView(){
		ModelAndView mav=new ModelAndView();
		String name="康力";
		System.out.println(Conventions.getVariableName(name));
		mav.addObject(name);
		mav.addObject("user", "康力");
		JstlView view=new JstlView();
		view.setContentType("application/json");
		view.setUrl("/WEB-INF/views/helloworld.jsp");
		mav.setView(view);
		return mav;
	}
	@RequestMapping("/mapadd")
	public void mapadd(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("user", "张三");
		try {
			request.getRequestDispatcher("/WEB-INF/views/helloworld.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@InitBinder("user")
	public void user(WebDataBinder wb){
		wb.setFieldDefaultPrefix("user.");
	}
	@ModelAttribute("addresss")
	public List<address> getAddresss(){
		List<address> list=new ArrayList<address>();
		list.add(new address("1235213","大街1"));
		list.add(new address("12213213","大街2"));
		list.add(new address("12782113","大街3"));
		return list;
	}
	@ModelAttribute("books")
	public address[] getBooks(){
		address[] addresss={
				new address("1235213","大街a"),
				new address("12213213","大街b"),
				new address("12782113","大街c")
		};
		return addresss;
	}
}
