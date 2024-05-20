package com.jun.plugin.compile.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.io.FileUtil;
import com.jun.plugin.compile.service.ExecuteStringSourceService;
import com.jun.plugin.common.util.HttpRequestUtil;
//import com.jun.plugin.compile.util.JsonResult;
//import com.ibeetl.olexec.entity.DepartmentEntity;
//import com.ibeetl.olexec.entity.UserEntity;
//import com.ibeetl.olexec.service.ExecuteStringSourceService;
//import com.ibeetl.olexec.util.*;
import com.jun.plugin.compile.util.JsonResult;
import com.jun.plugin.compile.util.SourceChecker;
import com.jun.plugin.compile.util.VerifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
//import org.beetl.sql.core.page.PageResult;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
public class RunSqlController {
	private static final String defaultSource =
			"public class Run {\n" + "    public static void main(String[] args) {\n" + "        \n" + "    }\n" + "}";

	private Logger logger = LoggerFactory.getLogger(RunSqlController.class);
	@Autowired
	private ExecuteStringSourceService executeStringSourceService;

	private AtomicInteger count = new AtomicInteger();

	public RunSqlController(){

	}

	@RequestMapping(path = {"/code"}, method = RequestMethod.GET)
	public String entry(Model model) {
		URL fileUrl = this.getClass().getResource("/code");
		System.out.println(fileUrl);
		List<File> fileList = FileUtil.loopFiles(FileUtil.file(fileUrl));
		TreeSet<String> fileNames = new TreeSet<>();
		for (File file : fileList) {
			fileNames.add(file.getName());
		}
		model.addAttribute("fileNames", fileNames);
		return "index.html";
	}

	@RequestMapping(path = {"/run"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult runCode(@RequestParam("source") String source, @RequestParam("verifyCode") String verifyCode, Model model, HttpServletRequest request) {
		count.incrementAndGet();
		try{
			Map<String, Object> result = new HashMap();
			if(!SourceChecker.isValidate(source)){
				result.put("status", 1);
				result.put("statusText", "执行失败");
				result.put("lastSource", source);
				result.put("runResult", "沙箱不允许的代码 ");
				return JsonResult.success(result);
			}
			boolean verifyResult = VerifyCodeUtil.getInstance().verifyRunCount(request,result,verifyCode);
			if (!verifyResult){
				return JsonResult.success(result);
			}
			long s=System.currentTimeMillis();
			String runResult = executeStringSourceService.execute2(source, "");
			runResult = "编译和运行总共消耗:"+(System.currentTimeMillis()-s)+"\n"+runResult;

			result.put("status", 0);
			result.put("statusText", "执行成功");
			result.put("lastSource", source);
			result.put("runResult", runResult);
			return JsonResult.success(result);
		}catch(Throwable error){
			//任何意外，可以查看代码
			log.error("ERROR FOR CODE ");
			log.info(source);
			log.error(error.getMessage(),error);
			throw error;
		}

	}

	/**
	 * 查看运行次数
	 * @return
	 */
	@RequestMapping(path = {"/codeCount"}, method = RequestMethod.GET)
	@ResponseBody
	public JsonResult<String> userList() {
		return JsonResult.success("all:"+count.intValue()+" ip:"+ VerifyCodeUtil.getInstance().ips());
	}

//	@RequestMapping(path = {"/user/list"}, method = RequestMethod.POST)
//	@ResponseBody
//	public JsonResult<PageResult<UserEntity>> userList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
//		PageResult<UserEntity> userPageResult = SessionSQLManager.getSQLManger().query(UserEntity.class).pageSimple(page, limit);
//		return JsonResult.success(userPageResult);
//	}
//
//	@RequestMapping(path = {"/department/list"}, method = RequestMethod.POST)
//	@ResponseBody
//	public JsonResult<PageResult<DepartmentEntity>> departmentList(@RequestParam("limit") Integer limit, @RequestParam("page") Integer page) {
//		PageResult<DepartmentEntity> rolePageResult = SessionSQLManager.getSQLManger().query(DepartmentEntity.class).pageSimple(page, limit);
//		return JsonResult.success(rolePageResult);
//	}

	@RequestMapping(path = {"/verifyCode"}, method = RequestMethod.GET)
	public void getVerifyCode(HttpServletResponse response) {
		HttpSession session = HttpRequestUtil.getRequest().getSession();
		response.setContentType("image/png");
		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(100, 60, 4, 10);
		//图形验证码写出，可以写出到文件，也可以写出到流
		try {
			OutputStream os = response.getOutputStream();
			captcha.write(os);
			session.setAttribute("captcha",captcha);
		}
		catch (IOException e){
			logger.error("验证码生成失败"+e.getMessage());
		}
	}

	@RequestMapping(path = {"/code"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult code(@RequestParam("fileName") String fileName) {
		URL fileUrl = this.getClass().getResource("/code/" + fileName);
		if (fileUrl == null) {
			return JsonResult.failMessage("文件不存在");
		}
		String code = FileUtil.readString(fileUrl,"UTF-8");
		return JsonResult.success(code);
	}
}
