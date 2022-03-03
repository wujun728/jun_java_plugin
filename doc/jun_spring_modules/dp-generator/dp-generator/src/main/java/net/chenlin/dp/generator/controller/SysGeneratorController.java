package net.chenlin.dp.generator.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.generator.entity.GeneratorParamEntity;
import net.chenlin.dp.generator.entity.TableEntity;
import net.chenlin.dp.generator.service.SysGeneratorService;

/**
 * 代码生成器
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月28日 下午8:56:30
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {

	@Autowired
	private SysGeneratorService sysGeneratorService;
	
	/**
	 * 表格列表
	 * @param params
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<TableEntity> list(@RequestBody Map<String, Object> params) {
		return sysGeneratorService.listTable(params);
	}
	
	/**
	 * 生成代码
	 * @param tables
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/code")
	public void generator(GeneratorParamEntity params, HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] code = sysGeneratorService.generator(params);
		String attachment = "attachment; filename=" + params.getTables()[0] + ".zip";
		response.reset();  
        response.setHeader("Content-Disposition", attachment);  
        response.addHeader("Content-Length", "" + code.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(code, response.getOutputStream());
	}
	
}
