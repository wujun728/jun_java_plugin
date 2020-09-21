package com.osmp.web.system.interceptorMapping.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.interceptorMapping.entity.InterceptorMapping;
import com.osmp.web.system.interceptorMapping.service.InterceptorMappingService;

/**
 * 
 * @author heyu
 *
 */
@Controller
@RequestMapping("/interceptorMapping")
public class InterceptorMappingController {
    
    @Resource
    private InterceptorMappingService service;
    
    @RequestMapping("toList")
    public ModelAndView toList(){
        ModelAndView mv = new ModelAndView("system/interceptorMapping/toList");
        mv.addObject("interceptors", service.getAllInterceptors());
        mv.addObject("services", service.getAllServices());
        return mv;
    }
    
    @RequestMapping("getServices")
    @ResponseBody
    public List<InterceptorMapping> getServicesByInterceptor(@RequestParam("interceptor")String interceptorInfo){
        return service.getServiceListByInterceptor(interceptorInfo);
    }
    
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestParam("interceptor")String interceptorInfo,
            @RequestParam("addServices")List<String> addServices,@RequestParam("delServices")List<String> delServices){
        service.update(interceptorInfo, addServices, delServices);
        return "success";
    }
}
