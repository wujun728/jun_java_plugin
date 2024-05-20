package com.jun.plugin.compile.util;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.jun.plugin.common.util.HttpRequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class VerifyCodeUtil {
    private TimedCache<String, Map<String,Object>> timedCache = CacheUtil.newTimedCache(60*1000);
    private static class Holder{
        private static final VerifyCodeUtil instance = new VerifyCodeUtil();
    }
    private VerifyCodeUtil(){
        timedCache.schedulePrune(60*1000);
    }
    public static VerifyCodeUtil getInstance(){
        return Holder.instance;
    }

    public int ips(){
        return timedCache.capacity();
    }
    public synchronized boolean verifyRunCount(HttpServletRequest request, Map<String, Object> result, String verifyCode){
        String clientIp = ServletUtil.getClientIP(request);
        if (StrUtil.isBlank(clientIp)){
            result.put("status", 1);
            result.put("statusText", "客户端IP错误");
            return false;
        }
        Map<String,Object> clientMap = timedCache.get(clientIp);
        if (clientMap == null) {
            clientMap = new HashMap<>();
        }
        HttpSession session = HttpRequestUtil.getRequest().getSession();
        long startTime=System.currentTimeMillis();
        long lastRunTime = Convert.toLong(clientMap.get("lastRunTime"),0L);
        int runCount = Convert.toInt(clientMap.get("runCount"),0);
        if (lastRunTime == 0){
            runCount = 1;
            clientMap.put("lastRunTime",startTime);
        }
        else{
            if (startTime - lastRunTime < 60 * 1000){
                runCount = runCount+1;
            }
            else{
                runCount = 1;
                clientMap.put("lastRunTime",startTime);
            }
        }
        clientMap.put("runCount",runCount);
        if (runCount > 5){
            clientMap.put("isVerify",true);
        }
        boolean isVerify = Convert.toBool(clientMap.get("isVerify"),false);
        boolean verifyResult = true;
        if (isVerify){
            if (StrUtil.isBlank(verifyCode)){
                result.put("status", 1);
                result.put("statusText", "请输入验证码");
                verifyResult = false;
            }
            else
            {
                CircleCaptcha captcha = (CircleCaptcha)session.getAttribute("captcha");
                if (captcha ==null || !captcha.verify(verifyCode)){
                    result.put("status", 1);
                    result.put("statusText", "验证码不正确");
                    verifyResult = false;
                }
                else{
                    clientMap.put("isVerify",false);
                    clientMap.put("runCount",0);
                    session.removeAttribute("captcha");
                    verifyResult = true;
                }
            }
        }
        timedCache.put(clientIp,clientMap);
        return verifyResult;
    }
}
