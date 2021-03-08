# Spring MVC  关于jsonp跨域处理

* 新建`JsonpAdvice`控制器增强继承`org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice`类


		package com.drore.jsonp.advice;
	
		import org.springframework.web.bind.annotation.ControllerAdvice;
		import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;
	
		@ControllerAdvice
		public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{
			public JsonpAdvice() {
				super("callback","jsonp");
			}
		}


* 所有controller类使用`@RestController`注解


		package com.drore.jsonp.controller;
		
		import java.util.ArrayList;
		import java.util.HashMap;
		import java.util.List;
		import java.util.Map;
		
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RestController;
		
		@RestController
		public class HomeController {
	
		
			@RequestMapping(value="/render.json")
			public List<Map<String, Object>> render(){
				List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
				for (int i = 0; i < 10; i++) {
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("userName", "张三"+i);
					map.put("sex", "男");
					map.put("phone", "1598723212"+i);
					list.add(map);
				}
				return list;
			}
		}


* `jQuery`跨域调用：

		$.ajax({
			url:'http://localhost:9090/render.json',
			dataType:'jsonp',
			success:function(data){
				console.log(data)
			}
		})
	
		//返回json数据
		jQuery162036356921307742596_1442105501105([
		    {
		        "phone": "15987232120",
		        "sex": "男",
		        "userName": "张三0"
		    },
		   //......
		]);