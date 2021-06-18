package spring_mvc.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import spring_mvc.model.user;
@Controller
public class ValidatorController {
	@RequestMapping(value="/hello/validation",method=RequestMethod.GET)
	public String validatation2(String lang,Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("lang", lang);
		if( lang == null || "".equals(lang) ){
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver (request);
		 	localeResolver.setLocale(request, response, new Locale("zh", "CN"));
		}
		return "/hello/validation";
	}
	@RequestMapping(value="/hello/validation",method=RequestMethod.POST)
	public  String validation(@Valid user user,Errors error){
		if(error.hasErrors()){
			return "forward:/WEB-INF/views/hello/validation.jsp";
		}
		return "hellow/success";
	}
}
