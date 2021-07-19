package com.jun.plugin.picturemanage.util;

import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.picturemanage.annotation.PageUrl;
import com.jun.plugin.picturemanage.model.PageDataHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 10:48
 */
public class ModelDataUtil {


    /**
     * 反射动态调用Page Handler数据填充方法
     *
     * @param modelAndView
     * @param url
     */
    public static void setData(ModelAndView modelAndView, String url) {
        if (modelAndView == null || url == null) {
            return;
        }
        PageDataHandler handler = SpringContextUtils.getBean(PageDataHandler.class);
        url = url.replaceAll(".html", "");
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            PageUrl annotation = method.getDeclaredAnnotation(PageUrl.class);
            if (annotation != null && annotation.value().equals(url)) {
                try {
                    method.invoke(handler, modelAndView);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

}
