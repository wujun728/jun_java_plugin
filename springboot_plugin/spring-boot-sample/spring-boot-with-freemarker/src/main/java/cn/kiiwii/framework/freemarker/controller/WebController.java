package cn.kiiwii.framework.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhong on 2016/11/10.
 */
@Controller
public class WebController {

    @RequestMapping("/index")
    public String index(ModelMap modelMap) {
        return "index";
    }

}
