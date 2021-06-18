package cn.yustart.picturemanage.util;

import cn.yustart.picturemanage.annotation.PageUrl;
import cn.yustart.picturemanage.model.PageDataHandler;
import org.springframework.web.servlet.ModelAndView;

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
