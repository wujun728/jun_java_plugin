package com.demo.weixin.clients;


import com.demo.weixin.entity.*;
import com.demo.weixin.request.handlers.CreateMenuRequestHandler;
import com.demo.weixin.request.handlers.CreateUserRequestHandler;
import com.demo.weixin.request.handlers.UpdateUserRequestHandler;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.entity.message.BasicMessage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Wujun
 * @description 企业微信客户端
 * @date 2017/7/28
 * @since 1.0
 */
public interface IQyWeixinClient {

    /**
     * 获取企业号的accessToken
     *
     * @return
     */
    QYAccessToken getQYAccessToken(String corpId, String corpSecret) throws URISyntaxException, IOException;

    // ############################## JSSKD ################################

    /**
     * 获取微信企业号jssdk临时票据
     *
     * @param accessToken
     * @return
     */
    WeixinJsApiTicket getWeixinQyJsApiTicket(String accessToken) throws URISyntaxException, IOException;


    // ################################ 部门管理 ###############################

    /**
     * 创建部门
     *
     * @param name     部门名称。长度限制为1~64个字节，字符不能包括\:*?"<>｜
     * @param parentId 父亲部门id。根部门id为1
     * @return
     */
    BaseWeixinResponse createDept(String accessToken, String name, String parentId) throws URISyntaxException, IOException;

    /**
     * 获取指定部门id下的子部门(根部门为1 )
     *
     * @param deptId
     * @return
     */
    QYDept getDeptList(String accessToken, Integer deptId) throws URISyntaxException, IOException;


    // ################################ 菜单管理 #######################################

    /**
     * 创建应用自定义菜单
     * 注意：应用必须处于回调模式；菜单最多为两级，一级菜单最多为3个，二级菜单最多为5个。
     *
     * @param createMenuRequestHandler
     * @return getErrmsg "ok" 则创建成功
     * @throws URISyntaxException
     * @throws IOException
     */
    BaseWeixinResponse createMenu(String accessToken, CreateMenuRequestHandler createMenuRequestHandler) throws URISyntaxException, IOException, WeixinException;

    /**
     * 获取应用菜单信息
     *
     * @param accessToken
     * @param agentId
     * @return
     */
    List<QYMenu> getMenu(String accessToken, Integer agentId) throws URISyntaxException, IOException;


    /**
     * 删除菜单
     *
     * @param accessToken
     * @param agentId
     * @return getErrmsg "ok" 则删除成功
     */
    BaseWeixinResponse delMenu(String accessToken, Integer agentId) throws URISyntaxException, IOException;


    // ##################################  消息推送 ####################################

    /**
     * 发送消息
     *
     * @param accessToken
     * @param sendMessageHandler
     * @param <T>
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    <T extends BasicMessage> QYMessage sendMessage(String accessToken, T sendMessageHandler) throws URISyntaxException, IOException, WeixinException;

    // #########################  通讯录管理 ############################

    /**
     * 创建成员
     *
     * @param createUserRequestHandler
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    BaseWeixinResponse createUser(String accessToken, CreateUserRequestHandler createUserRequestHandler) throws IOException, URISyntaxException, WeixinException;


    /**
     * 更新成员
     *
     * @param accessToken
     * @param updateUserRequestHandler
     * @return
     */
    BaseWeixinResponse updateUser(String accessToken, UpdateUserRequestHandler updateUserRequestHandler) throws IOException, URISyntaxException, WeixinException;


    /**
     * 删除成员
     *
     * @param accessToken
     * @param userId
     * @return
     */
    BaseWeixinResponse deleteUser(String accessToken, String userId) throws URISyntaxException, IOException;


    /**
     * 批量删除成员
     *
     * @param accessToken
     * @param userIdList
     * @return
     */
    BaseWeixinResponse batchDeleteUser(String accessToken, List<String> userIdList) throws IOException, URISyntaxException;

    /**
     * 读取成员(详情)
     *
     * @param accessToken
     * @param userId
     * @return
     */
    QYUserDetail getQYUserDetail(String accessToken, String userId) throws URISyntaxException, IOException;

    /**
     * 获取部门成员列表（只返回成员的 userid、name、department）
     *
     * @param accessToken 必填。调用接口凭证
     * @param deptId      必填。获取的部门id
     * @param fetchChild  是否递归获取子部门下面的成员
     * @return
     */
    QYUserList getDeptQYUserList(String accessToken, Integer deptId, Boolean fetchChild) throws URISyntaxException, IOException;

    /**
     * 获取部门成员详情列表(返回成员的详细信息列表)
     *
     * @param accessToken 必填。调用接口凭证
     * @param deptId      必填。获取的部门id
     * @param fetchChild  是否递归获取子部门下面的成员
     * @return
     */
    QYUserList getDeptUserDetailList(String accessToken, Integer deptId, Boolean fetchChild) throws URISyntaxException, IOException;

    /**
     * userId 转为 openId
     *
     * @param accessToken
     * @param userId
     * @param agentId
     * @return
     */
    String userIdConvertToOpenId(String accessToken, String userId, Integer agentId) throws URISyntaxException, IOException;

    /**
     * openId 转为 userId
     *
     * @param accessToken
     * @param openId
     * @return
     */
    String openIdConvertToUserId(String accessToken, String openId) throws URISyntaxException, IOException;

    /**
     * 获取企业号登录用户信息
     *
     * @param accessToken
     * @param authCode
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    QYLoginUserInfo getLoginUserInfo(String accessToken, String authCode) throws IOException, URISyntaxException;

    /**
     * 获取企业号登陆用户的详细信息
     *
     * @param accessToken
     * @param userTicket
     * @return
     */
    QYUserDetail getLoginUserDetail(String accessToken, String userTicket) throws URISyntaxException, IOException;
}
