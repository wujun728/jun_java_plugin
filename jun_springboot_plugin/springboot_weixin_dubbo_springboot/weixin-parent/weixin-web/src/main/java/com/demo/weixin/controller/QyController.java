package com.demo.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.demo.weixin.api.IQyService;
import com.demo.weixin.api.ICacheService;
import com.demo.weixin.entity.QYDept;
import com.demo.weixin.entity.QYMenu;
import com.demo.weixin.entity.QYUserDetail;
import com.demo.weixin.entity.message.TextMessage;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.enums.MessageType;
import com.demo.weixin.enums.ProjectType;
import com.demo.weixin.exception.WeixinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Wujun
 * @description 企业微信服务
 * @date 2017/7/25
 * @since 1.0
 */
@Controller
@RequestMapping("/qy")
public class QyController {

    @Autowired
    private IQyService qyService;

    @Autowired
    private ICacheService cacheService;

    /**
     * 跳转到授权地址
     *
     * @param projectType
     * @param clientType
     * @param from
     * @return
     * @throws UnsupportedEncodingException
     * @throws WeixinException
     */
    @RequestMapping("/{projectType}/{clientType}/toAuth.html")
    public String toAuth(@PathVariable String projectType, @PathVariable String clientType,
                         String from) throws UnsupportedEncodingException, WeixinException {

        ProjectType projectTypeEnum = ProjectType.findByLowerCaseValue(projectType);
        ClientType clientTypeEnum = ClientType.findByLowerCaseValue(clientType);
        String authUrl = qyService.getAuthUrl(projectTypeEnum, clientTypeEnum, from);
        return "redirect:" + authUrl;
    }

    /**
     * 获取授权成功后的用户id 并跳转到回调地址
     *
     * @param code
     * @param state
     * @return
     * @throws WeixinException
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping("/{projectType}/{clientType}/callBack.html")
    public String getLoginUserId(@PathVariable String projectType, @PathVariable String clientType, @RequestParam String code,
                                 @RequestParam String state) throws WeixinException, IOException, URISyntaxException {
        // 项目类型
        ProjectType projectTypeEnum = ProjectType.findByLowerCaseValue(projectType);
        ClientType clientTypeEnum = ClientType.findByLowerCaseValue(clientType);

        String authCallBackUrl = cacheService.getAuthCallBackUrl(state);
        String userId = qyService.getLoginUserId(projectTypeEnum, clientTypeEnum, code);

        return "redirect:" + authCallBackUrl + "?userId=" + userId;
    }


    /**
     * 创建菜单
     *
     * @param agentId  必填。
     * @param menuList 必填。
     * @return
     */
    @RequestMapping(value = "/menu/create.html", method = RequestMethod.POST)
    @ResponseBody
    public String createMenu(@RequestParam Integer agentId, @RequestParam List<QYMenu> menuList)
            throws IOException, WeixinException, URISyntaxException {
        qyService.createMenu(agentId, menuList);
        // TODO 返回什么 返回到哪里
        return "ok";
    }

    /**
     * 创建成员
     *
     * @param userDetail
     * @return
     */
    @RequestMapping(value = "/user/create.html", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(QYUserDetail userDetail) throws IOException, WeixinException, URISyntaxException {
        qyService.createUser(userDetail);
        return "ok";
    }

    /**
     * 删除成员
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/delete.html", method = RequestMethod.POST)
    @ResponseBody
    public String deleteUser(@RequestParam String userId) throws IOException, WeixinException, URISyntaxException {
        qyService.deleteUser(userId);
        return "ok";
    }

    /**
     * 修改成员
     *
     * @param userDetail
     * @return
     */
    @RequestMapping(value = "/user/update.html", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(QYUserDetail userDetail) throws IOException, WeixinException, URISyntaxException {
        qyService.updateUser(userDetail);
        return "ok";
    }

    /**
     * 获取部门成员列表
     *
     * @param deptId     必填。部门ID
     * @param fetchChild 是否递归获取子部门下面的成员
     * @return
     */
    @RequestMapping(value = "/dept/userList.html")
    @ResponseBody
    public List<QYUserDetail> getDeptUserList(@RequestParam Integer deptId, Boolean fetchChild) throws IOException, WeixinException, URISyntaxException {
        return qyService.getDeptUserList(deptId, fetchChild);
    }

    /**
     * 获取部门成员详情列表
     *
     * @param deptId     必填。部门ID
     * @param fetchChild 是否递归获取子部门下面的成员
     * @return
     */
    @RequestMapping(value = "/dept/userDetailList.html")
    @ResponseBody
    public String getDeptUserDetailList(@RequestParam Integer deptId, Boolean fetchChild)
            throws WeixinException, IOException, URISyntaxException {
        List<QYUserDetail> userDetailList = qyService.getDeptUserDetailList(deptId, fetchChild);
        return JSON.toJSONString(userDetailList);
    }


    /**
     * 获取指定部门的子部门o
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/dept/list.html")
    @ResponseBody
    public String getDeptList(Integer deptId)
            throws IOException, WeixinException, URISyntaxException {
        List<QYDept.QYDeptInfo> deptInfoList = qyService.getQYDeptList(deptId);
        return JSON.toJSONString(deptInfoList);
    }

    @RequestMapping(value = "/message/send.html")
    @ResponseBody
    public String sendMessage() throws IOException, WeixinException, URISyntaxException {
        TextMessage msg = new TextMessage();
        msg.setAgentId(14);
        msg.setContent("这是测试消息，呵呵哒。<a href=\"http://www.google.com\">哈哈</a>");
        msg.setToUser("13688888888|13566666666");
        //msg.setToParty("2");
        //msg.setToTag("1");
        msg.setMessageType(MessageType.TEXT);
        String accessToken = qyService.getQYAccessToken("your appId", "your app's corpSecret");
        qyService.sendMessage(accessToken, msg);
        return "ok";
    }
}
