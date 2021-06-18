package com.springthymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HelloController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getMovie(@PathVariable String name, ModelMap model) {
        model.addAttribute("name", name);
        model.addAttribute("query", "");
        model.addAttribute("submit", "");
        return "hello";
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String query(@RequestParam("name") String name, ModelMap model) {
        model.addAttribute("name", "");
        model.addAttribute("query", name);
        model.addAttribute("submit", "");
        return "hello";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String submit(@RequestParam("name") String name, ModelMap model) {
        model.addAttribute("name", "");
        model.addAttribute("query", "");
        model.addAttribute("submit", name);
        return "hello";
    }

}