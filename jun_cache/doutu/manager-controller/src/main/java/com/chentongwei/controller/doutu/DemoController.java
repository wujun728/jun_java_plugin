package com.chentongwei.controller.doutu;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.doutu.io.PictureListCacheIO;
import com.chentongwei.service.doutu.IPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Demo控制
 *
 * @author TongWei.Chen 2017-05-31 15:29:05
 */
@Controller
@RequestMapping("/manager/demo")
public class DemoController {

    @Autowired
    private IPictureService pictureService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(PictureListCacheIO pictureListIO) {
        return ResultCreator.getSuccess(pictureService.listCache(pictureListIO));
    }

    @RequestMapping("/hi")
    public String hi(Model model) {
        model.addAttribute("name", "neal");
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("title", "小怪兽来了");
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 2);
        map1.put("title", "小怪兽来了2");
        model.addAttribute("list", Arrays.asList(new Object[]{map,map1}));
        return "doutu/welcome"; //自动寻找resources/templates中名字为welcome.html的文件作为模板，拼装后返回
    }
}