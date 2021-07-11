package cn.kiiwii.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhong on 2016/9/17.
 */
@Controller
@RequestMapping("/")
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);
//    @Autowired
//    private IUserService userService;

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/set")
    public String set(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/seto")
    public String setObject(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/setl")
    public String setList(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/seth")
    public String setHashMap(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }

}
