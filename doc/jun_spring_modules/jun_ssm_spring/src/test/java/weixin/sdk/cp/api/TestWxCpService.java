package weixin.sdk.cp.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.frame.util.JsonUtils;
import org.springrain.weixin.entity.WxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxCpConfig;
import org.springrain.weixin.sdk.common.bean.menu.WxMenu;
import org.springrain.weixin.sdk.common.bean.menu.WxMenuButton;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.cp.api.IWxCpService;
import org.springrain.weixin.sdk.cp.bean.WxCpDepart;
import org.springrain.weixin.sdk.cp.bean.WxCpMessage;
import org.springrain.weixin.sdk.cp.bean.WxCpTag;
import org.springrain.weixin.sdk.cp.bean.WxCpUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestWxCpService {
	@Resource
	IWxCpService wxCpService;
	
	private String corpId = "";
	private String secret = "";
	
	/**
	 * 测试获取accesstoken、jsapiToken
	 */
	@Test
	public void testAccessToken(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			String accessToken = wxCpService.getAccessToken(config);
			System.out.println("accessToken:"+accessToken);
			String jsapiTicket = wxCpService.getJsApiTicket(config);
			System.out.println("jsapiTicket:"+jsapiTicket);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取应用id
	 */
	@Test
	public void testGetAppInfo(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		try {
			String accessToken = wxCpService.getAccessToken(config);
			String agentId = "14";
			String url = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token="+accessToken+"&agentid="+agentId;
			String result = HttpClientUtils.sendHttpGet(url);
			System.out.println("返回应用信息:"+result);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置应用信息
	 */
	@Test
	public void testSetApp(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			String accessToken = wxCpService.getAccessToken(config);
			String httpUrl = "https://qyapi.weixin.qq.com/cgi-bin/agent/set";
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("access_token", accessToken);
			maps.put("agentid", "34");
			maps.put("report_location_flag", "1");
			maps.put("logo_mediaid", "");
			maps.put("name", "test");
			maps.put("description", "testetst");
			maps.put("redirect_domain", "www.baidu.com");
			maps.put("isreportuser", "1");
			maps.put("isreportenter", "0");
			maps.put("home_url", "www.baidu.com");
			maps.put("chat_extension_url", "www.baidu.com");
			//httpUrl = "http://t78uitdfh2.proxy.qqbrowser.cc/springrain/weixin/test/tooauth";
			String result = HttpClientUtils.sendHttpPost(httpUrl, maps);
			System.out.println("修改返回信息:"+result);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取应用概况列表
	 */
	@Test
	public void testGetAppsInfo(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			String accessToken = wxCpService.getAccessToken(config);
			String httpUrl = "https://qyapi.weixin.qq.com/cgi-bin/agent/list?access_token="+accessToken;
			String result = HttpClientUtils.sendHttpGet(httpUrl);
			System.out.println("获取应用概况列表返回列表:"+result);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 测试创建菜单
	 */
	@Test
	public void testMenuCreate(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		WxMenu menu = new WxMenu();
		List<WxMenuButton> buttons = new ArrayList<>();
		WxMenuButton button1 = new WxMenuButton();
		button1.setKey("test");
		button1.setName("test");
		button1.setType("view");
		button1.setUrl("http://www.baidu.com");
		buttons.add(button1);
		menu.setButtons(buttons);
		
		try {
			wxCpService.menuCreate(config, 36, menu);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取应用菜单
	 */
	@Test
	public void testMenuGet(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		try {
			WxMenu menu = wxCpService.menuGet(config, 14);
			System.out.println("获取应用菜单:"+menu.toJson());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试邀请关注
	 */
	public void testInviteUser(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			int result = wxCpService.invite(config, "xingjunhui", "请关注企业号");
			System.out.println("邀请方式:"+result);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户二次认证
	 */
	@Test
	public void testUserAuthenticated(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		try {
			wxCpService.userAuthenticated(config, "xingjunhui");
			System.out.println("用户二次认证，成功");
		} catch (WxErrorException e) {
			System.out.println("用户二次认证，失败");
		}
	}
	
	/**
	 * 获取部门列表
	 */
	@Test
	public void testDeptListGet(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			List<WxCpDepart> list = wxCpService.departGet(config);
			System.out.println("部门列表:"+list.toString());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试创建、修改、删除部门
	 */
	@Test
	public void testDeptCreate(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		
		try {
			WxCpDepart depart = new WxCpDepart();
			depart.setName("test部门");
			depart.setOrder(2);
			depart.setParentId(1);
			int result = wxCpService.departCreate(config, depart);
			System.out.println("创建部门id:"+result);
			depart.setId(result);
			depart.setName("test部门11");
			wxCpService.departUpdate(config, depart);
			System.out.println("修改成功");
			wxCpService.departDelete(config, result);
			System.out.println("删除成功");
			
			
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取成员列表
	 */
	@Test
	public void testGetUserList(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			List<WxCpUser> userList = wxCpService.userList(config, 1, true, 0);
			System.out.println("获取部门下的成员详情:"+userList);
			
			userList = wxCpService.departGetUsers(config, 1, true, 0);
			System.out.println("获取部门下成员:"+userList);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户增删改操作
	 */
	@Test
	public void testUserOperate(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		WxCpUser user1 = new WxCpUser();
		WxCpUser user2 = new WxCpUser();
		WxCpUser user3 = new WxCpUser();
		
		user1.setUserId("testuser1");
		user1.setName("testuserName1");
		user1.setDepartIds(new Integer[]{1});
		user1.setMobile("13017585211");
		
		user2.setUserId("testuser2");
		user2.setName("testuserName2");
		user2.setDepartIds(new Integer[]{1});
		user2.setMobile("13017585311");
		
		user3.setUserId("testuser3");
		user3.setName("testuserName3");
		user3.setDepartIds(new Integer[]{1});
		user3.setMobile("13017585411");
		
		try {
			wxCpService.userCreate(config, user1);
			wxCpService.userCreate(config, user2);
			wxCpService.userCreate(config, user3);
			System.out.println("创建3个用户成功");
			user1.setName("user11111");
			user2.setName("user22222");
			user3.setName("user33333");
			wxCpService.userUpdate(config, user1);
			wxCpService.userUpdate(config, user2);
			wxCpService.userUpdate(config, user3);
			System.out.println("更新成功");
			wxCpService.userDelete(config, user1.getUserId());
			System.out.println("删除单个用户成功");
			wxCpService.userDelete(config, new String[]{user2.getUserId(),user3.getUserId()});
			System.out.println("删除多个用户成功");
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取标签列表
	 */
	@Test
	public void testGetTags(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			List<WxCpTag> tagList = wxCpService.tagGet(config);
			System.out.println("标签列表:");
			for (WxCpTag wxCpTag : tagList) {
				System.out.print(wxCpTag.getName()+"\t");
			}
			System.out.println();
		} catch (WxErrorException e) {
			System.out.println("没有取到标签列表");
		}
	}
	
	/**
	 * 标签操作
	 */
	@Test
	public void testTagsOperate(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		
		try {
			String tagId = wxCpService.tagCreate(config, "我是测试标签");
			System.out.println("创建标签:"+tagId);
			wxCpService.tagUpdate(config, tagId, "改名测试标签");
			System.out.println("标签修改成功");
			List<String> userIds = new ArrayList<>();
			userIds.add("AngeryFeather");
			wxCpService.tagAddUsers(config, tagId, userIds, null);
			System.out.println("标签用户添加成功");
			List<WxCpUser> userList = wxCpService.tagGetUsers(config, tagId);
			System.out.println("获取标签下的用户:"+JsonUtils.writeValueAsString(userList));
			wxCpService.tagRemoveUsers(config, tagId, userIds);
			System.out.println("标签用户删除成功");
			wxCpService.tagDelete(config, tagId);
			System.out.println("删除标签成功");
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试临时素材管理
	 * @throws IOException 
	 */
	@Test
	public void testTmpMedia() throws IOException{
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		config.setTmpDirFile("E://1//");
		File file = new File("test.txt");
		if(!file.exists()){
			file.createNewFile();
		}
		try {
			file.getName();
			file.getAbsolutePath();
			file.length();
			/*WxMediaUploadResult result = wxCpService.mediaUpload(config, "file", file);
			System.out.println("上传临时文件:"+result.getMediaId());*/
			File tmpFile = wxCpService.mediaDownload(config, "1_lhBt5cJUm1oQEOwNeUzHYfDb5ZNuwIBXiQMYAq4ul2AqOxIw9NmkOmdtJCX7ApIKIFAwJ5jByOFpU07J-7wOA");
			System.out.println("获取临时文件:"+tmpFile.length());
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试消息发送
	 */
	@Test
	public void testMessageSend(){
		IWxCpConfig config = new WxCpConfig();
		config.setCorpId(corpId);
		config.setCorpSecret(secret);
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("type", "image");
		maps.put("offset", "0");
		maps.put("count", "10");
		
		
		try {
			String token = wxCpService.getAccessToken(config);
			String result = HttpClientUtils.sendHttpPost("https://qyapi.weixin.qq.com/cgi-bin/material/batchget?access_token="+token,maps);
			System.out.println("返回素材列表:"+result);
			
			WxCpMessage message = WxCpMessage
			  .TEXT()
			  .agentId(14) // 企业号应用ID
			  .toUser("angeryFeather")
			  .content("sfsfdsdf")
			  .build();
			wxCpService.messageSend(config, message);
			System.out.println("发送文本消息成功");
			message = WxCpMessage
					  .IMAGE()
					  .agentId(14) // 企业号应用ID
					  .toUser("AngeryFeather")
					  .mediaId("MEDIA_ID")
					  .build();
		} catch (WxErrorException e) {
			System.out.println("发送文本消息失败");
		}
	}
	
	
}
