package com.baibu.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenbin on 2017/6/29.
 */
public class Log4web {
    public String browser;//浏览器描述信息
    public String os;//操作系统
    //public String flash;//格式："1,15",逗号分隔，第一个表示是否安装flash，1：是，0：否。15:表示flash版本.
    //public String referrer;//referrer.document.referrer信息.
    public String url;//url.当前页面的url.
    //public String resolution;//屏幕分辨率信息.返回格式："1920*1080",(window.screen对象获取）
    //public String name;//.异常名称。
    public String message;//异常message。
    //public String stack;//异常调用堆栈字符串。


    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}