package com.jun.plugin.compile.controller;

import com.alibaba.fastjson2.JSON;
import com.jun.plugin.compile.service.ExecuteStringSourceService;
import com.jun.plugin.compile.util.CompileResult;
import com.jun.plugin.common.util.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/class")
public class JavaCodeController {
    private Logger logger = LoggerFactory.getLogger(JavaCodeController.class);

    @Autowired
    private ExecuteStringSourceService executeStringSourceService;


    private static final String defaultSource = "public class Run {\n"
            + "    public static void main(String[] args) {\n"
            + "        \n"
            + "    }\n"
            + "}";

    @RequestMapping(path = {"/code"}, method = RequestMethod.GET)
    @ResponseBody
    public String entry(Model model) {
        model.addAttribute("lastSource", defaultSource);
        return defaultSource;
    }

    @RequestMapping(path = {"/execute"}, method = RequestMethod.POST)
    @ResponseBody
    public String execute(@RequestParam("source") String source,
                          @RequestParam("systemIn") String systemIn, Model model, HttpServletRequest request) {
        HttpRequestUtil.setRequest(request);
        String runResult = executeStringSourceService.execute2(source, systemIn);
        runResult = runResult.replaceAll(System.lineSeparator(), "<br/>"); // 处理html中换行的问题
        model.addAttribute("lastSource", source);
        model.addAttribute("lastSystemIn", systemIn);
        model.addAttribute("runResult", runResult);
        //return "ide.html";
        return runResult;
    }

    @RequestMapping(path = {"/code/run"}, method = RequestMethod.POST)
    @ResponseBody
    public String runCode(@RequestParam("source") String source,
                          @RequestParam("systemIn") String systemIn, Model model, HttpServletRequest request) {
        HttpRequestUtil.setRequest(request);
        //String runResult = executeStringSourceService.execute3(source, systemIn);
        CompileResult compileResult = executeStringSourceService.execute3(source, systemIn);
        String runResult = compileResult.getExecuteMsg();
        runResult = runResult.replaceAll(System.lineSeparator(), "<br/>"); // 处理html中换行的问题
		model.addAttribute("lastSource", source);
        model.addAttribute("lastSystemIn", systemIn);
        model.addAttribute("runResult", runResult);
        //return "ide.html";
        return JSON.toJSONString(compileResult);
    }
}
