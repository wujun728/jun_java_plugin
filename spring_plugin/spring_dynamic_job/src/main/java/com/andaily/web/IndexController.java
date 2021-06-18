package com.andaily.web;

import com.andaily.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Shengzhao Li
 */
@Controller
public class IndexController {


    @Autowired
    private TestService testService;

    @RequestMapping("index.xhtm")
    public String index(Model model) {
        model.addAttribute("date", new Date());
        return "index";
    }

    //add  job
    @RequestMapping("add_job.xhtm")
    public String addJob(Model model) {
        boolean result = testService.addDynamicJob();
        model.addAttribute("result", result);
        return "redirect:index.xhtm";
    }

    //remove  job
    @RequestMapping("remove_job.xhtm")
    public String removeJob() {
        testService.removeJob();
        return "redirect:index.xhtm";
    }
}