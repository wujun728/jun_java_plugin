package cn.kiiwii.framework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhong on 2016/9/17.
 */
@Controller
@RequestMapping("/")
public class SampleController {

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){

        return "index";
    }
    @RequestMapping("/set")
    public String set(HttpServletRequest request, HttpServletResponse response){

        Object o = request.getSession().getAttribute("name");
        if(o!=null){
            logger.info("获得session-string的值："+o.toString());
        }else{
            request.getSession().setAttribute("name","session-with-redis");
            logger.info("设置了session-string的值");
        }
        return "index";
    }
    @RequestMapping("/seto")
    public String setObject(HttpServletRequest request, HttpServletResponse response){

        Object o = request.getSession().getAttribute("user");
        if(o!=null){
            if(o instanceof User){
                User user = (User)o;
                logger.info("获得session-user的值："+user.toString());
            }else{
                logger.info("获得session-object："+o.toString());
            }
        }else{
            request.getSession().setAttribute("user",new User("zhangsan",18,170.0f));
            logger.info("设置了session-object的值");
        }
        return "index";
    }
    @RequestMapping("/setl")
    public String setList(HttpServletRequest request, HttpServletResponse response){

        Object o = request.getSession().getAttribute("users");
        if(o!=null){
            if (o instanceof List){
                List<User> users = (List<User>)o;
                logger.info("获得session-list的值："+users.toString());
            }else{
                logger.info("获得session-object的值："+o.toString());
            }
        }else{
            List<User> users = new ArrayList<User>();
            users.add(new User("lisi",18,170.0f));
            users.add(new User("wangwu",24,175.0f));
            users.add(new User("zhaoliu",28,180.0f));
            request.getSession().setAttribute("users",users);
            logger.info("设置了session-list 的值");
        }
        return "index";
    }

}
