/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.anji.captcha.service.impl;


import com.anji.captcha.config.Container;
import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.AESUtil;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raodeming on 2019/12/25.
 */
@Component(value = "defaultCaptchaServiceImpl")
@Primary
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultCaptchaServiceImpl implements CaptchaService {

    private static Logger logger = LoggerFactory.getLogger(DefaultCaptchaServiceImpl.class);

    @Value("${captcha.captchaOriginalPath.jigsaw:}")
    private String captchaOriginalPathJigsaw;

    @Value("${captcha.captchaOriginalPath.pic-click:}")
    private String captchaOriginalPathClick;

    @Value("${captcha.aes.key:XwKsGlMcdPMEhR1B}")
    private String aesKey;

    protected static String REDIS_SECOND_CAPTCHA_KEY = "RUNNING:CAPTCHA:second-%s";

    protected CaptchaCacheService captchaCacheService;

    private Map<String,CaptchaService> instances = new HashMap();
    @PostConstruct
    public void init(){
        initCache();

        Object t = this;
        Container.getBeanOfType(CaptchaService.class).entrySet().stream().forEach(item->{
            if(!t.equals(item.getValue())) {
                instances.put(item.getKey(), item.getValue());
            }
        });
        System.out.println("supported-captchaTypes-service:"+instances.keySet().toString());
        //初始化底图
        ImageUtils.cacheImage(captchaOriginalPathJigsaw, captchaOriginalPathClick);
        logger.info("--->>>初始化验证码底图<<<---");
    }

    public void initCache(){
        Map<String, CaptchaCacheService> map = Container.getBeanOfType(CaptchaCacheService.class);
        if(map == null || map.isEmpty()){
            captchaCacheService = Container.getBean("captchaCacheServiceMemImpl", CaptchaCacheService.class);
            return;
        }
        if(map.size()==1){
            captchaCacheService = Container.getBean("captchaCacheServiceMemImpl", CaptchaCacheService.class);
            return;
        }
        if(map.size()>=2){
            map.entrySet().stream().forEach(item ->{
                if(captchaCacheService != null){
                    return;
                }
                if(!"captchaCacheServiceMemImpl".equals(item.getKey())){
                    captchaCacheService = item.getValue();
                    return;
                }
            });
        }
    }

    private CaptchaService getService(String captchaType){
        return instances.get(captchaType.concat("CaptchaService"));
    }

    @Override
    public ResponseModel get(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        if(!CaptchaTypeEnum.validateCodeValue(captchaVO.getCaptchaType())){
            return RepCodeEnum.PARAM_TYPE_ERROR.parseError("captchaType: "+CaptchaTypeEnum.getString());
        }
        if (captchaVO.getCaptchaType().equals("blockPuzzle")) {
            captchaVO.setCaptchaOriginalPath(captchaOriginalPathJigsaw);
        } else {
            captchaVO.setCaptchaOriginalPath(captchaOriginalPathClick);
        }
        return getService(captchaVO.getCaptchaType()).get(captchaVO);
    }

    @Override
    public ResponseModel check(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        if(!CaptchaTypeEnum.validateCodeValue(captchaVO.getCaptchaType())){
            return RepCodeEnum.PARAM_TYPE_ERROR.parseError("captchaType: "+CaptchaTypeEnum.getString());
        }
        if (StringUtils.isEmpty(captchaVO.getToken())) {
            return RepCodeEnum.NULL_ERROR.parseError("token");
        }
        return getService(captchaVO.getCaptchaType()).check(captchaVO);
    }

    @Override
    public ResponseModel verification(CaptchaVO captchaVO) {
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        if (StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVerification");
        }
        try {
            //aes解密
            String s = AESUtil.aesDecrypt(captchaVO.getCaptchaVerification(), aesKey);
            String token = s.split("---")[0];
            String pointJson = s.split("---")[1];
            //取坐标信息
            String codeKey = String.format(REDIS_SECOND_CAPTCHA_KEY, token);
            if (!captchaCacheService.exists(codeKey)) {
                return ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_INVALID);
            }
            String redisData = captchaCacheService.get(codeKey);
            //二次校验取值后，即刻失效
            captchaCacheService.delete(codeKey);
            if (!pointJson.equals(redisData)) {
                return ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_COORDINATE_ERROR);
            }
        } catch (Exception e) {
            logger.error("验证码坐标解析失败", e);
            return ResponseModel.errorMsg(e.getMessage());
        }
        return ResponseModel.success();
    }

}
