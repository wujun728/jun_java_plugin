package cn.kiiwii.framework.controller;

import com.baidu.ueditor.define.MultiState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IndexController {

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response){
        MultiState multiState = new MultiState(false);
        return "pages/home";
    }
}
