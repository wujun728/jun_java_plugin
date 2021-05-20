package com.demo.weixin.api;


import com.demo.weixin.entity.QYDept;
import com.demo.weixin.entity.QYMessage;
import com.demo.weixin.entity.QYUserDetail;
import com.demo.weixin.entity.message.BasicMessage;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.entity.QYMenu;
import com.demo.weixin.enums.ProjectType;

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
public interface IQyService {

    /**
     * 获取企业微信授权地址
     *
     * @param projectType
     * @param clientType
     * @param from
     * @return
     * @throws WeixinException
     */
    String getAuthUrl(ProjectType projectType, ClientType clientType, String from) throws WeixinException, UnsupportedEncodingException;


    /**
     * 获取企业 accessToken
     *
     * @return
     */
    String getQYAccessToken(String corpId, String corpSecret) throws IOException, URISyntaxException;

    /**
     * 获取企业 jsApiTicket
     *
     * @param corpId
     * @param corpSecret
     * @return
     */
    String getJsApiTicket(String corpId, String corpSecret) throws IOException, URISyntaxException;

    /**
     * 获取授权登录成功的企业用户userId
     *
     * @param projectType
     * @param clientType
     * @param code
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws WeixinException
     */
    String getLoginUserId(ProjectType projectType, ClientType clientType, String code) throws IOException, URISyntaxException, WeixinException;

    /**
     * 获取授权登录成功的企业用户详细信息
     *
     * @param userId
     * @return
     */
    QYUserDetail getLoginUserDetail(String userId) throws WeixinException;


    /**
     * 创建菜单
     *
     * @param agentId
     * @param menuList
     * @return
     */
    void createMenu(Integer agentId, List<QYMenu> menuList) throws WeixinException, IOException, URISyntaxException;


    /**
     * 创建成员
     *
     * @param userDetail
     */
    void createUser(QYUserDetail userDetail) throws WeixinException, IOException, URISyntaxException;

    /**
     * 删除成员
     *
     * @param userId
     */
    void deleteUser(String userId) throws WeixinException, IOException, URISyntaxException;

    /**
     * 修改成员
     *
     * @param userDetail
     */
    void updateUser(QYUserDetail userDetail) throws WeixinException, IOException, URISyntaxException;


    /**
     * 获取部门成员列表
     *
     * @param deptId
     * @return
     */
    List<QYUserDetail> getDeptUserList(Integer deptId, Boolean fetchChild) throws WeixinException, IOException, URISyntaxException;

    /**
     * 获取部门成员详情列表
     *
     * @param deptId
     * @return
     */
    List<QYUserDetail> getDeptUserDetailList(Integer deptId, Boolean fetchChild) throws IOException, URISyntaxException, WeixinException;


    /**
     * 获取部门列表
     *
     * @param deptId
     * @return
     */
    List<QYDept.QYDeptInfo> getQYDeptList(Integer deptId) throws WeixinException, IOException, URISyntaxException;


    /**
     * 发送消息
     *
     * @param message
     * @param <T>
     * @throws WeixinException
     * @throws IOException
     * @throws URISyntaxException
     */
    <T extends BasicMessage> QYMessage sendMessage(String accessToken, T message) throws WeixinException, IOException, URISyntaxException;
}