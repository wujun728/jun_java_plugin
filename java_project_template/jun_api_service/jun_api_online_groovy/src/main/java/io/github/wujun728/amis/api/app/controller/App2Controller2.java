package io.github.wujun728.amis.api.app.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.rest.util.HttpRequestUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class App2Controller2 {

	@RequestMapping("/api/user/login")
	public Result login(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"userinfo\":{\r\n"
				+ "		\"id\":1,\r\n"
				+ "		\"username\":\"admin\",\r\n"
				+ "		\"nickname\":\"admin1111\",\r\n"
				+ "		\"mobile\":\"13888888888\",\r\n"
				+ "		\"avatar\":\"\",\r\n"
				+ "		\"score\":0,\r\n"
				+ "		\"token\":\"c8edcb1d-8c5c-4e5d-9e53-71d7024f9030\",\r\n"
				+ "		\"user_id\":1,\r\n"
				+ "		\"createtime\":1593422850,\r\n"
				+ "		\"expiretime\":1596014850,\r\n"
				+ "		\"expires_in\":2592000}} ");
		return Result.success(null,json);
	}
	
	@RequestMapping("/api/index/getsysteminfo")
	public Result getsysteminfo(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "   \"images\": \"https://omo-oss-image.thefastimg.com/portal-saas/new2022093017543412358/cms/image/e46936f7-8a3d-4240-8b81-81541babc2d8.png\",  \r\n"
				+ "    \"name\": \"齐兴会计师事务所\",  \r\n"
				+ "    \"beian\": \"beian666\"  \r\n"
				+ "} ");
		return Result.success(null,json);
	}
	
	@RequestMapping("/api/index/getmenus")
	public Result getmenus(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		
		Map<String,Object> data = Maps.newHashMap();
		List<String> list = Lists.newArrayList();
		list.add("http://cdn.uviewui.com/uview/swiper/swiper1.png");
		list.add("https://cdn.uviewui.com/uview/swiper/swiper2.png");
		list.add("https://cdn.uviewui.com/uview/swiper/swiper3.png");
		data.put("bannerlist", list.toArray());
		
		
		List<Map> menus = Lists.newArrayList();
		Map info = Maps.newHashMap();
		info.put("src", "https://cdn.uviewui.com/uview/swiper/swiper1.png");
		info.put("title", "功能1");
		Map menu = Maps.newHashMap();
		menu.put("title", "功能模块1");
		menu.put("info", info);
		menus.add(menu);
		data.put("menus", menus);
		return Result.success(data);
	}
	
	@RequestMapping("/api/index/getmenus2")
	public Result getmenus2(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject("  {\r\n"
				+ "    \"menus\": {\r\n"
				+ "        \"title\": \"功能模块666\",\r\n"
				+ "        \"info\": [\r\n"
				+ "            {\r\n"
				+ "                \"title\": \"111\",\r\n"
				+ "                \"src\": \"http://cdn.uviewui.com/uview/swiper/swiper1.png\",\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"title\": \"222\",\r\n"
				+ "                \"src\": \"http://cdn.uviewui.com/uview/swiper/swiper2.png\",\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"title\": \"333\",\r\n"
				+ "                \"src\": \"http://cdn.uviewui.com/uview/swiper/swiper3.png\",\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"status\": 0\r\n"
				+ "    }\r\n"
				+ "} ");
		return Result.success(json);
	}
	
	Map<String,Object> userinfo = Maps.newHashMap();
	@RequestMapping("api/user/getUserDetail")
	public Result getUserDetail(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		if(CollectionUtils.isEmpty(userinfo)) {
			userinfo.put("avatar", "");
			userinfo.put("nickname", "admin");
			userinfo.put("username", "admin");
			userinfo.put("id", "666");
			userinfo.put("email", "test@qq.com");
			userinfo.put("mobile", "13012344321");
			userinfo.put("birthday", "2023-01-02");
			userinfo.put("gender", 1);
		}
		return Result.success(userinfo);
	}
	
	@RequestMapping("/api/user/setuserinfo")
	public Result setuserinfo(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
//		data.put("nickname", "admin");
//		data.put("username", "admin");
//		data.put("id", "666");
//		data.put("email", "test@qq.com");
//		data.put("mobile", "13012344321");
//		data.put("birthday", "2023-01-02");
//		data.put("gender", 1);
		userinfo.putAll(param);
		return Result.success(userinfo);
	}
	@RequestMapping("/api/user/setusergender")
	public Result setusergender(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		userinfo.putAll(param);
		return Result.success(userinfo);
	}
	
	
	@RequestMapping("/api/common/upload")
	public Result upload(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "   \"url\": \"https://omo-oss-image.thefastimg.com/portal-saas/new2022093017543412358/cms/image/e46936f7-8a3d-4240-8b81-81541babc2d8.png\",  \r\n"
				+ "    \"name\": \"name\",  \r\n"
				+ "    \"filePath\": \"filePath\"  \r\n"
				+ "} ");
		return Result.success(json);
	}
	@RequestMapping("/api/ykjp/product/unit/deleteUnit")
	public Result deleteUnit(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/product/unit/addUnit")
	public Result addUnit(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/product/type/deleteType")
	public Result deleteType(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/product/type/add")
	public Result addType(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/product/product/deleteProduct")
	public Result deleteProduct(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/information/basisinfo/supplier/deleteProduct")
	public Result deletebasisinfoProduct(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/information/basisinfo/supplier/add")
	public Result addbasisinfoProduct(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	@RequestMapping("/api/ykjp/information/basisinfo/warehouse/add")
	public Result addwarehouse(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		return Result.success();
	}
	
	@RequestMapping("/api/ykjp/product/unit/getUnitList")
	public Result getUnitList(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"data\": [{\r\n"
				+ "			\"id\": 1,\r\n"
				+ "			\"name\": \"件\",\r\n"
				+ "			\"firmid\": null,\r\n"
				+ "			\"createtime\": 1593348050,\r\n"
				+ "			\"updatetime\": 1593348050,\r\n"
				+ "			\"deletetime\": null\r\n"
				+ "		}, {\r\n"
				+ "			\"id\": 2,\r\n"
				+ "			\"name\": \"条\",\r\n"
				+ "			\"firmid\": null,\r\n"
				+ "			\"createtime\": 1593348057,\r\n"
				+ "			\"updatetime\": 1593348057,\r\n"
				+ "			\"deletetime\": null\r\n"
				+ "		}]\r\n"
				+ "	} ");
		return Result.success(json);
	}
	
	@RequestMapping("/api/ykjp/product/type/getType")
	public Result getType(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"data\": [{\r\n"
				+ "			createtime:1594827893\r\n"
				+ "			deletetime:null\r\n"
				+ "			firmid:null\r\n"
				+ "			id:8\r\n"
				+ "			image:\"http://127.0.0.1:8081/uploads/20200715/b8219f88b484274acc31cd1b81156ba7.jpg\"\r\n"
				+ "			name:\"服装\"\r\n"
				+ "			pid:0\r\n"
				+ "			product_id:null\r\n"
				+ "			prop:\"[{\"title\":\"服装\"}]\"\r\n"
				+ "			updatetime:1594827893\r\n"
				+ "			weigh:0\r\n"
				+ "		},{\r\n"
				+ "			createtime:1594827893\r\n"
				+ "			deletetime:null\r\n"
				+ "			firmid:null\r\n"
				+ "			id:8\r\n"
				+ "			image:\"http://127.0.0.1:8081/uploads/20200715/b8219f88b484274acc31cd1b81156ba7.jpg\"\r\n"
				+ "			name:\"服装2\"\r\n"
				+ "			pid:0\r\n"
				+ "			product_id:null\r\n"
				+ "			prop:\"[{\"title\":\"服装2\"}]\"\r\n"
				+ "			updatetime:1594827893\r\n"
				+ "			weigh:0\r\n"
				+ "		} ]\r\n"
				+ "	} ");
		return Result.success(json);
	}
	
	@RequestMapping("/api/ykjp/product/product/getProduct")
	public Result getProduct(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "        \"data\":[\r\n"
				+ "            {\r\n"
				+ "                \"id\":2,\r\n"
				+ "                \"firmid\":null,\r\n"
				+ "                \"name\":\"衣服\",\r\n"
				+ "                \"product_type_id\":10,\r\n"
				+ "                \"specification\":\"2件55元\",\r\n"
				+ "                \"sku\":\"\",\r\n"
				+ "                \"prop\":\"[{\"title\":\"上衣\",\"value\":\"\"}]\",\r\n"
				+ "                \"inventory\":0,\r\n"
				+ "                \"min_warning\":0,\r\n"
				+ "                \"max_warning\":0,\r\n"
				+ "                \"raise\":0,\r\n"
				+ "                \"remark\":\"\",\r\n"
				+ "                \"createtime\":1595149254,\r\n"
				+ "                \"updatetime\":1595149254,\r\n"
				+ "                \"deletetime\":null,\r\n"
				+ "                \"product_unit_id\":13,\r\n"
				+ "                \"unit\":\"件\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    } ");
		return Result.success(json);
	}
	
	@RequestMapping("/api/ykjp/information/basisinfo/supplier/index")
	public Result getProductList(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "        \"data\":[\r\n"
				+ "            {\r\n"
				+ "                \"id\":2,\r\n"
				+ "                \"avatar\":\"\",\r\n"
				+ "                \"code\":\"SP20200719184024\",\r\n"
				+ "                \"name\":\"北京冰河服饰有限公司\",\r\n"
				+ "                \"abbname\":\"\",\r\n"
				+ "                \"contact\":\"程卓\",\r\n"
				+ "                \"phone\":\"13529746799\",\r\n"
				+ "                \"city\":\"北京/北京市/崇文区\",\r\n"
				+ "                \"address\":\"北京市丰台区菜户营58号11032室(太平桥企业集中办公区)\",\r\n"
				+ "                \"fax\":\"\",\r\n"
				+ "                \"mobile\":\"0877262422\",\r\n"
				+ "                \"bank\":\"\",\r\n"
				+ "                \"banknums\":\"\",\r\n"
				+ "                \"term\":21,\r\n"
				+ "                \"identifier\":\"\",\r\n"
				+ "                \"credit\":\"\",\r\n"
				+ "                \"amount\":\"0\",\r\n"
				+ "                \"salesman_id\":0,\r\n"
				+ "                \"firmid\":null,\r\n"
				+ "                \"description\":\"说明\",\r\n"
				+ "                \"createtime\":1595155341,\r\n"
				+ "                \"updatetime\":1595155341,\r\n"
				+ "                \"deletetime\":null,\r\n"
				+ "                \"status\":0\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    } ");
		return Result.success(json);
	}
	
	
	@RequestMapping("/api/ykjp/information/basisinfo/supplier/getdetails")
	public Result getProductDetail(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "        \"data\":[\r\n"
				+ "            {\r\n"
				+ "                \"id\":2,\r\n"
				+ "                \"avatar\":\"\",\r\n"
				+ "                \"code\":\"SP20200719184024\",\r\n"
				+ "                \"name\":\"北京冰河服饰有限公司\",\r\n"
				+ "                \"abbname\":\"\",\r\n"
				+ "                \"contact\":\"程卓\",\r\n"
				+ "                \"phone\":\"13529746799\",\r\n"
				+ "                \"city\":\"北京/北京市/崇文区\",\r\n"
				+ "                \"address\":\"北京市丰台区菜户营58号11032室(太平桥企业集中办公区)\",\r\n"
				+ "                \"fax\":\"\",\r\n"
				+ "                \"mobile\":\"0877262422\",\r\n"
				+ "                \"bank\":\"\",\r\n"
				+ "                \"banknums\":\"\",\r\n"
				+ "                \"term\":21,\r\n"
				+ "                \"identifier\":\"\",\r\n"
				+ "                \"credit\":\"\",\r\n"
				+ "                \"amount\":\"0\",\r\n"
				+ "                \"salesman_id\":0,\r\n"
				+ "                \"firmid\":null,\r\n"
				+ "                \"description\":\"说明\",\r\n"
				+ "                \"createtime\":1595155341,\r\n"
				+ "                \"updatetime\":1595155341,\r\n"
				+ "                \"deletetime\":null,\r\n"
				+ "                \"status\":0\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    } ");
		return Result.success(json);
	}
	
	@RequestMapping("/api/ykjp/summary/Purchase/index")
	public Result getPurchaseDetail(HttpServletRequest request) {
		System.out.println(JSON.toJSONString(HttpRequestUtil.getAllParameters(request)));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "        \"data\": {\r\n"
				+ "            \"purchase\": {\r\n"
				+ "                \"totalMoney\": 6661\r\n"
				+ "            },\r\n"
				+ "            \"Retprodcut\": {\r\n"
				+ "                \"totalMoney\": 6662\r\n"
				+ "            },\r\n"
				+ "            \"deliveryPro\": {\r\n"
				+ "                \"totalMoney\": 6663\r\n"
				+ "            },\r\n"
				+ "            \"sellreturn\": {\r\n"
				+ "                \"totalMoney\": 6664\r\n"
				+ "            },\r\n"
				+ "            \"status\": 0\r\n"
				+ "        }\r\n"
				+ "    } ");
		return Result.success(json);
	}
	
}
