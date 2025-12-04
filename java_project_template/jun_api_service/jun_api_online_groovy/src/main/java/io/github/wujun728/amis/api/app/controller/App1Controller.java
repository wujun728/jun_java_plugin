package io.github.wujun728.amis.api.app.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import io.github.wujun728.common.base.Result;
import io.github.wujun728.rest.util.HttpRequestUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class App1Controller {

//	@Resource
//	SysDictService sysDictService;
//
//	@Resource
//	UserService userService;

	@RequestMapping("/api/mobile/login/loginByPassword")
	@ResponseBody
	public Result loginByPassword(HttpServletRequest request) throws IOException {
		System.out.println("Request URI: " + request.getRequestURI());
		//HttpServletRequest requestWrapper = new RequestWrapper((HttpServletRequest) request);
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		String loginType = MapUtil.getStr(param, "loginType");
		if("0".equals(loginType)) {//验证码登录,首次登录后自动注册，默认手机尾号后四位密码
			String phoneNo = MapUtil.getStr(param, "phoneNo");
			String validCode = MapUtil.getStr(param, "validCode");
		}
		if("1".equals(loginType)) {//用户名密码登录
			String username = MapUtil.getStr(param, "username");
			String password = MapUtil.getStr(param, "password");
			String validCode = MapUtil.getStr(param, "validCode");
//			SysUser vo = new SysUser();
//			vo.setUsername(username);
//			vo.setPassword(password);
//			LoginRespVO loginRespVO = userService.login(vo);
//			return Result.success(loginRespVO);
		}
		if("3".equals(loginType)) {//本机号码一键登录
			String phoneNo = MapUtil.getStr(param, "phoneNo");
			String validCode = MapUtil.getStr(param, "validCode");
		}
		if("other".equals(loginType)) {
			String phoneNo = MapUtil.getStr(param, "phoneNo");
			String validCode = MapUtil.getStr(param, "validCode");
		}
		if("currentPhone".equals(loginType)) {
			String phoneNo = MapUtil.getStr(param, "phoneNo");
			String validCode = MapUtil.getStr(param, "validCode");
		}
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"userinfo\":{\r\n"
//				+ "		\"id\":1,\r\n"
//				+ "		\"username\":\"admin\",\r\n"
//				+ "		\"nickname\":\"admin\",\r\n"
//				+ "		\"mobile\":\"13888888888\",\r\n"
//				+ "		\"avatar\":\"\",\r\n"
//				+ "		\"score\":0,\r\n"
//				+ "		\"token\":\"c8edcb1d-8c5c-4e5d-9e53-71d7024f9030\",\r\n"
//				+ "		\"user_id\":1,\r\n"
//				+ "		\"createtime\":1593422850,\r\n"
//				+ "		\"expiretime\":1596014850,\r\n"
				+ "		\"expires_in\":2592000}} ");
		return new Result(200,"登录成功！",json);
	}
	Map loginInfo = Maps.newHashMap();
	@RequestMapping("/api/mobile/login/logout")
	public Result logout(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"userinfo\":{\r\n"
//				+ "		\"id\":1,\r\n"
//				+ "		\"username\":\"admin\",\r\n"
//				+ "		\"nickname\":\"admin\",\r\n"
//				+ "		\"mobile\":\"13888888888\",\r\n"
//				+ "		\"avatar\":\"\",\r\n"
//				+ "		\"score\":0,\r\n"
//				+ "		\"token\":\"c8edcb1d-8c5c-4e5d-9e53-71d7024f9030\",\r\n"
//				+ "		\"user_id\":1,\r\n"
//				+ "		\"createtime\":1593422850,\r\n"
//				+ "		\"expiretime\":1596014850,\r\n"
				+ "		\"expires_in\":2592000}} ");
		return new Result(200,"已退出登录！",json);
	}
	
	
	@RequestMapping("/api/mobile/index")
	public Result index(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {} ");
//		if(RandomUtil.randomInt(200)>100) {
//			return new Result(200,"首页渲染信息！",json);
//		}else {
//			return new Result(0,"首页渲染信息！",json);
//		}
		return new Result(0,"首页渲染信息！",json);
	}
	
	@RequestMapping("/api/mobile/login/sendCode")
	public Result sendCode(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {} ");
		return new Result(200,"发送短信成功！",json);
	}
	@RequestMapping("/api/mobile/user/registerUser")
	public Result registerUser(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {} ");
		return new Result(200,"发送短信成功！",json);
	}
	@RequestMapping("/api/test/testData/listData")
	public String listData(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {\"list\":[{\"id\":\"1148170340563320832\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"安安\",\"createDate\":\"2019-07-08 18:01\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:30\",\"testInput\":\"测试数据1\",\"testTextarea\":\"文本域\",\"testSelect\":\"1\",\"testSelectMultiple\":\"\",\"testRadio\":\"1\",\"testCheckbox\":\"1,2\",\"testDate\":\"2019-07-07\",\"testDatetime\":\"2019-07-08 18:02\",\"testUser\":{\"id\":\"user1_ur60\",\"isNewRecord\":false,\"userCode\":\"user1_ur60\",\"userName\":\"用户01\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"id\":\"SDJN01\",\"isNewRecord\":false,\"officeCode\":\"SDJN01\",\"officeName\":\"企管部\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"370102\",\"testAreaName\":\"历下区\",\"testDataChildList\":[]},{\"id\":\"1148197403533664256\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2019-07-08 19:49\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:29\",\"testInput\":\"测试数据2\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"2\",\"testCheckbox\":\"1\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1148197456335757312\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2019-07-08 19:49\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:28\",\"testInput\":\"测试数据3\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"2\",\"testRadio\":\"\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162753403039744\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:11\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:27\",\"testInput\":\"测试数据4\",\"testTextarea\":\"\",\"testSelect\":\"2\",\"testSelectMultiple\":\"1\",\"testRadio\":\"2\",\"testCheckbox\":\"1\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162782335348736\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:11\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:26\",\"testInput\":\"测试数据5\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"2\",\"testRadio\":\"2\",\"testCheckbox\":\"1\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162924073463808\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:25\",\"testInput\":\"测试数据6\",\"testTextarea\":\"\",\"testSelect\":\"2\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"1\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162951009284096\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:24\",\"testInput\":\"测试数据7\",\"testTextarea\":\"\",\"testSelect\":\"2\",\"testSelectMultiple\":\"2\",\"testRadio\":\"1\",\"testCheckbox\":\"1\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162977416622080\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:23\",\"testInput\":\"测试数据8\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"2\",\"testRadio\":\"2\",\"testCheckbox\":\"1,2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760384\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:22\",\"testInput\":\"测试数据9\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760385\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:21\",\"testInput\":\"测试数据10\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760386\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:19\",\"testInput\":\"测试数据11\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760387\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:18\",\"testInput\":\"测试数据12\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760388\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:17\",\"testInput\":\"测试数据13\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997343760389\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:16\",\"testInput\":\"测试数据14\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760380\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:15\",\"testInput\":\"测试数据15\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760381\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:14\",\"testInput\":\"测试数据16\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760382\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:13\",\"testInput\":\"测试数据17\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760383\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:12\",\"testInput\":\"测试数据18\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760384\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:11\",\"testInput\":\"测试数据19\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]},{\"id\":\"1384162997345760385\",\"isNewRecord\":false,\"status\":\"0\",\"remarks\":\"\",\"createDate\":\"2021-04-19 15:12\",\"updateBy\":\"system\",\"createBy\":\"system\",\"updateDate\":\"2021-04-19 15:10\",\"testInput\":\"测试数据20\",\"testTextarea\":\"\",\"testSelect\":\"1\",\"testSelectMultiple\":\"1\",\"testRadio\":\"1\",\"testCheckbox\":\"2\",\"testUser\":{\"isNewRecord\":true,\"userCode\":\"\",\"avatarUrl\":\"/ctxPath/static/images/user1.jpg\"},\"testOffice\":{\"isNewRecord\":true,\"officeCode\":\"\",\"isTreeLeaf\":false,\"isRoot\":true},\"testAreaCode\":\"\",\"testAreaName\":\"\",\"testDataChildList\":[]}],\"count\":23,\"pageNo\":1,\"pageSize\":20} ");
		return json.toJSONString();
	}
	@RequestMapping("/api/test/testData/form")
	public String form(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject("{ testData: {\r\n"
				+ "            \"testRadio\": \"1\",\r\n"
				+ "            \"updateDate\": \"2021-04-19 15:30\",\r\n"
				+ "            \"testInput\": \"测试数据1\",\r\n"
				+ "            \"testSelectMultiple\": \"\",\r\n"
				+ "            \"isNewRecord\": false,\r\n"
				+ "            \"testTextarea\": \"文本域\",\r\n"
				+ "            \"testSelect\": \"1\",\r\n"
				+ "            \"testAreaName\": \"历下区\",\r\n"
				+ "            \"testDatetime\": \"2019-07-08 18:02\",\r\n"
				+ "            \"testAreaCode\": \"370102\",\r\n"
				+ "            \"createBy\": \"system\",\r\n"
				+ "            \"testOffice\": {\r\n"
				+ "                \"officeName\": \"企管部\",\r\n"
				+ "                \"isRoot\": true,\r\n"
				+ "                \"isTreeLeaf\": false,\r\n"
				+ "                \"officeCode\": \"SDJN01\",\r\n"
				+ "                \"id\": \"SDJN01\",\r\n"
				+ "                \"isNewRecord\": false\r\n"
				+ "            },\r\n"
				+ "            \"updateBy\": \"system\",\r\n"
				+ "            \"testDataChildList\": [],\r\n"
				+ "            \"testCheckbox\": \"1,2\",\r\n"
				+ "            \"id\": \"1148170340563320832\",\r\n"
				+ "            \"testUser\": {\r\n"
				+ "                \"avatarUrl\": \"/ctxPath/static/images/user1.jpg\",\r\n"
				+ "                \"id\": \"user1_ur60\",\r\n"
				+ "                \"isNewRecord\": false,\r\n"
				+ "                \"userName\": \"用户01\",\r\n"
				+ "                \"userCode\": \"user1_ur60\"\r\n"
				+ "            },\r\n"
				+ "            \"remarks\": \"安安\",\r\n"
				+ "            \"testDate\": \"2019-07-07\",\r\n"
				+ "            \"status\": \"0\",\r\n"
				+ "            \"createDate\": \"2019-07-08 18:01\"\r\n"
				+ "        } } ");
		json.forEach((k,v)->{System.err.println("k="+k+",v="+v);});
		return json.toJSONString();
	}
	@RequestMapping("/api/sys/office/treeData")
	public String treeData(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONArray json= JSON.parseArray("   [\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SD\",\r\n"
				+ "        \"isParent\": true,\r\n"
				+ "        \"name\": \"山东公司\",\r\n"
				+ "        \"pId\": \"0\",\r\n"
				+ "        \"id\": \"SD\",\r\n"
				+ "        \"title\": \"山东公司\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDJN\",\r\n"
				+ "        \"isParent\": true,\r\n"
				+ "        \"name\": \"济南公司\",\r\n"
				+ "        \"pId\": \"SD\",\r\n"
				+ "        \"id\": \"SDJN\",\r\n"
				+ "        \"title\": \"山东济南公司\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDJN01\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"企管部\",\r\n"
				+ "        \"pId\": \"SDJN\",\r\n"
				+ "        \"id\": \"SDJN01\",\r\n"
				+ "        \"title\": \"山东济南企管部\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDJN02\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"财务部\",\r\n"
				+ "        \"pId\": \"SDJN\",\r\n"
				+ "        \"id\": \"SDJN02\",\r\n"
				+ "        \"title\": \"山东济南财务部\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDJN03\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"研发部\",\r\n"
				+ "        \"pId\": \"SDJN\",\r\n"
				+ "        \"id\": \"SDJN03\",\r\n"
				+ "        \"title\": \"山东济南研发部\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDQD\",\r\n"
				+ "        \"isParent\": true,\r\n"
				+ "        \"name\": \"青岛公司\",\r\n"
				+ "        \"pId\": \"SD\",\r\n"
				+ "        \"id\": \"SDQD\",\r\n"
				+ "        \"title\": \"山东青岛公司\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDQD01\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"企管部\",\r\n"
				+ "        \"pId\": \"SDQD\",\r\n"
				+ "        \"id\": \"SDQD01\",\r\n"
				+ "        \"title\": \"山东青岛企管部\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDQD02\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"财务部\",\r\n"
				+ "        \"pId\": \"SDQD\",\r\n"
				+ "        \"id\": \"SDQD02\",\r\n"
				+ "        \"title\": \"山东青岛财务部\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"code\": \"SDQD03\",\r\n"
				+ "        \"isParent\": false,\r\n"
				+ "        \"name\": \"研发部\",\r\n"
				+ "        \"pId\": \"SDQD\",\r\n"
				+ "        \"id\": \"SDQD03\",\r\n"
				+ "        \"title\": \"山东青岛研发部\"\r\n"
				+ "    }\r\n"
				+ "]  ");
		return json.toJSONString();
	}
	
	@RequestMapping("/api/test/testData/delete")
	public JSONObject delete(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"message\": \"删除成功！\" , "
				+ "		\"result\": 'true' , "
				+ "		\"msg111\": true  "
				+ "} ");
		json.forEach((k,v)->{System.err.println("k="+k+",v="+v);});
		return json;
	}
	
	@RequestMapping("/api/test/testData/save")
	public JSONObject save(HttpServletRequest request) {
		Map param = HttpRequestUtil.getAllParameters(request);
		System.out.println(JSON.toJSONString(param));
		JSONObject json= JSON.parseObject(" {\r\n"
				+ "		\"message\": \"保存成功！\" , "
				+ "		\"result\": 'true' , "
				+ "		\"msg111\": true  "
				+ "} ");
		json.forEach((k,v)->{System.err.println("k="+k+",v="+v);});
		return json;
	}
	
}
