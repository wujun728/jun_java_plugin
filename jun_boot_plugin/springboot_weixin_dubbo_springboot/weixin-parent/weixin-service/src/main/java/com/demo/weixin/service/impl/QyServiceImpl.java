package com.demo.weixin.service.impl;

import com.demo.weixin.api.IQyService;
import com.demo.weixin.api.ICacheService;
import com.demo.weixin.clients.IQyWeixinClient;
import com.demo.weixin.constants.WeixinPropConstants;
import com.demo.weixin.constants.WeixinUrlConstants;
import com.demo.weixin.entity.*;
import com.demo.weixin.entity.message.BasicMessage;
import com.demo.weixin.enums.ClientType;
import com.demo.weixin.enums.ProjectType;
import com.demo.weixin.exception.WeixinException;
import com.demo.weixin.request.handlers.CreateMenuRequestHandler;
import com.demo.weixin.request.handlers.CreateUserRequestHandler;
import com.demo.weixin.request.handlers.UpdateUserRequestHandler;
import com.demo.weixin.utils.VerifyParamsUtils;
import com.demo.weixin.utils.WeixinUrlBuilder;
import com.qq.connect.utils.RandomStatusGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @description 企业号服务实现
 * @date 2017/7/25
 * @since 1.0
 */
public class QyServiceImpl implements IQyService {

    @Resource
    private ICacheService cacheService;

    @Resource
    private IQyWeixinClient qyWeixinClient;

    // 微信应用相关list
    @Resource
    private List<WeChatApplicationInfo> weChatApplicationInfoList;

    @Resource
    private RedisTemplate<String, QYUserDetail> redisTemplate;

    // 企业用户信息redis的key
    private static final String QY_USER_PREFIX = "qy_user_%s";

    @Value("${qy_user_expire_minutes}")
    private int qyUserExpireMinutes;

    @Override
    public String getAuthUrl(ProjectType projectType, ClientType clientType, String from) throws WeixinException, UnsupportedEncodingException {
        VerifyParamsUtils.notNull(projectType, "调用的项目类型不能为空");
        VerifyParamsUtils.notNull(clientType, "调用的客户端类型不能为空");
        for (WeChatApplicationInfo info : weChatApplicationInfoList) {
            // 项目类型和客户端类型都匹配不上
            if (projectType != info.getProjectType() || clientType != info.getClientType()) {
                continue;
            }
            String uniqueState = RandomStatusGenerator.getUniqueState();
            String authUrl = WeixinUrlBuilder.buildQYWebchatAuthUrl(WeixinUrlConstants.QY_PC_AUTH_LOCATION, WeixinPropConstants.CORP_ID,
                    info.getQyAgentId(), info.getQyAuthCallBack(), uniqueState);

            // 保存授权成功后的回调地址
            cacheService.saveAuthCallBackUrl(from, uniqueState);
            return authUrl;
        }
        return null;
    }

    @Override
    public String getQYAccessToken(String corpId, String corpSecret) throws IOException, URISyntaxException {
        String accessToken = cacheService.getAccessToken(corpId, corpSecret);
        if (StringUtils.isBlank(accessToken)) {
            QYAccessToken qyAccessToken = qyWeixinClient.getQYAccessToken(corpId, corpSecret);
            accessToken = cacheService.saveAccessToken(qyAccessToken.getAccessToken(), corpId, corpSecret);
        }
        return accessToken;
    }

    @Override
    public String getJsApiTicket(String corpId, String corpSecret) throws IOException, URISyntaxException {
        String ticket = cacheService.getJsApiTicket(corpId);
        if (StringUtils.isBlank(ticket)) {
            String accessToken = this.getQYAccessToken(corpId, corpSecret);
            WeixinJsApiTicket weixinJsApiTicket = qyWeixinClient.getWeixinQyJsApiTicket(accessToken);
            ticket = cacheService.saveJsApiTicket(corpId, weixinJsApiTicket.getTicket());
        }
        return ticket;
    }

    @Override
    public String getLoginUserId(ProjectType projectType, ClientType clientType, String code) throws WeixinException, IOException, URISyntaxException {
        VerifyParamsUtils.notNull(projectType, "调用的项目类型不能为空");
        VerifyParamsUtils.notNull(clientType, "调用的客户端类型不能为空");

        for (WeChatApplicationInfo info : weChatApplicationInfoList) {
            // 项目类型和客户端类型都匹配不上
            if (projectType != info.getProjectType() || clientType != info.getClientType()) {
                continue;
            }
            // 获取当前应用的的企业微信accessToken
            String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID,info.getQyAppSecret());
            VerifyParamsUtils.hasText(accessToken, "accessToken获取失败");

            QYLoginUserInfo qyLoginUserInfo = qyWeixinClient.getLoginUserInfo(accessToken, code);
            VerifyParamsUtils.isTrue(qyLoginUserInfo.getErrorCode() == 0, qyLoginUserInfo.getErrorMessage());

            // 判断是否是企业成员
            if (StringUtils.isBlank(qyLoginUserInfo.getUserId())) {
                throw new WeixinException(WeixinException.NOT_QY_USER_CODE, WeixinException.NOT_QY_USER_MESSAGE);
            }
            // 获取登录用户的详细信息
            QYUserDetail userDetail = qyWeixinClient.getQYUserDetail(accessToken, qyLoginUserInfo.getUserId());
            this.saveQYLoginUserDetail(userDetail);

            return userDetail.getUserId();
        }
        return null;
    }

    /**
     * 保存企业登陆用户信息
     *
     * @param qyUserDetail
     */
    private void saveQYLoginUserDetail(QYUserDetail qyUserDetail) {
        if (qyUserDetail == null || StringUtils.isEmpty(qyUserDetail.getUserId())) {
            return;
        }
        String qyUserKey = String.format(QY_USER_PREFIX, qyUserDetail.getUserId());
        redisTemplate.delete(qyUserKey);
        redisTemplate.opsForValue().set(qyUserKey, qyUserDetail);
        // 设置失效时间
        redisTemplate.expire(qyUserKey, qyUserExpireMinutes, TimeUnit.MINUTES);
    }


    @Override
    public QYUserDetail getLoginUserDetail(String userId) throws WeixinException {
        VerifyParamsUtils.hasText(userId, "userId不能为空");
        String qyUserKey = String.format(QY_USER_PREFIX, userId);
        return redisTemplate.opsForValue().get(qyUserKey);
    }

    @Override
    public void createMenu(Integer agentId, List<QYMenu> menuList) throws WeixinException, IOException, URISyntaxException {
        if (null == agentId || CollectionUtils.isEmpty(menuList)) {
            throw new WeixinException(-1, "创建菜单参数缺失");
        }

        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);

        CreateMenuRequestHandler createMenuDTO = new CreateMenuRequestHandler(agentId, new ArrayList<>());
        BeanUtils.copyProperties(menuList, createMenuDTO.getButton());

        BaseWeixinResponse response = qyWeixinClient.createMenu(accessToken, createMenuDTO);
        VerifyParamsUtils.isTrue(response.getErrorCode() == 0, response.getErrorMessage());
    }

    @Override
    public void createUser(QYUserDetail userDetail) throws WeixinException, IOException, URISyntaxException {
        VerifyParamsUtils.notNull(userDetail, "要创建的用户信息不能为空");
        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);

        CreateUserRequestHandler createUserDTO = new CreateUserRequestHandler();
        BeanUtils.copyProperties(userDetail, createUserDTO);
        BaseWeixinResponse response = qyWeixinClient.createUser(accessToken, createUserDTO);
        VerifyParamsUtils.isTrue(response.getErrorCode() == 0, response.getErrorMessage());
    }

    @Override
    public void deleteUser(String userId) throws WeixinException, IOException, URISyntaxException {
        VerifyParamsUtils.hasText(userId, "userId不能为空");
        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);
        BaseWeixinResponse response = qyWeixinClient.deleteUser(accessToken, userId);
        VerifyParamsUtils.isTrue(response.getErrorCode() == 0, response.getErrorMessage());
    }

    @Override
    public void updateUser(QYUserDetail userDetail) throws WeixinException, IOException, URISyntaxException {

        VerifyParamsUtils.notNull(userDetail, "要修改的用户信息不能为空");
        VerifyParamsUtils.hasText(userDetail.getUserId(), "用户ID不能为空");

        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);

        UpdateUserRequestHandler updateUserDTO = new UpdateUserRequestHandler();
        BeanUtils.copyProperties(userDetail, updateUserDTO);

        BaseWeixinResponse response = qyWeixinClient.updateUser(accessToken, updateUserDTO);
        VerifyParamsUtils.isTrue(response.getErrorCode() == 0, response.getErrorMessage());
    }


    @Override
    public List<QYUserDetail> getDeptUserList(Integer deptId, Boolean fetchChild) throws WeixinException, IOException, URISyntaxException {
        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);
        QYUserList qyUserList = qyWeixinClient.getDeptQYUserList(accessToken, deptId, fetchChild);
        VerifyParamsUtils.isTrue(qyUserList.getErrorCode() == 0, qyUserList.getErrorMessage());
        return qyUserList.getUserList();
    }

    @Override
    public List<QYUserDetail> getDeptUserDetailList(Integer deptId, Boolean fetchChild) throws IOException, URISyntaxException, WeixinException {
        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);
        VerifyParamsUtils.hasText(accessToken, "调用凭据accessToken不能为空");
        QYUserList qyUserList = qyWeixinClient.getDeptUserDetailList(accessToken, deptId, fetchChild);
        VerifyParamsUtils.isTrue(qyUserList.getErrorCode() == 0, qyUserList.getErrorMessage());
        return qyUserList.getUserList();
    }

    @Override
    public List<QYDept.QYDeptInfo> getQYDeptList(Integer deptId) throws WeixinException, IOException, URISyntaxException {
        String accessToken = this.getQYAccessToken(WeixinPropConstants.CORP_ID, WeixinPropConstants.CORP_SECRET);
        QYDept qyDept = qyWeixinClient.getDeptList(accessToken, deptId);
        VerifyParamsUtils.isTrue(qyDept.getErrorCode() == 0, qyDept.getErrorMessage());
        return qyDept.getDepartment();
    }

    @Override
    public <T extends BasicMessage> QYMessage sendMessage(String accessToken, T message) throws WeixinException, IOException, URISyntaxException {
        VerifyParamsUtils.hasText(accessToken, "调用凭据不能为空");
        VerifyParamsUtils.notNull(message, "消息不能为空");
        QYMessage response = qyWeixinClient.sendMessage(accessToken, message);
        return response;
    }
}
