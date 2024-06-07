//package com.jun.plugin.groovy.test;
//
//import com.jun.plugin.common.Result;
//import com.jun.plugin.groovy.groovy.GroovyDynamicLoader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//public class TestGroovyScriptController {
//
//	@Resource
//	private GroovyDynamicLoader groovyDynamicLoader;
//
//	@RequestMapping("/test")
//	public int test(int number1, int number2) {
//		return number1 + number2;
//
//	}
//
//	@RequestMapping("/refresh")
//	public Result refresh() {
//		try {
//			groovyDynamicLoader.refreshNew();
//		} catch (Exception e) {
//			Result.success("緩存刷新失败！" + e.getMessage());
//			e.printStackTrace();
//		}
//		return Result.success("緩存刷新成功");
//	}
//
//
//}
