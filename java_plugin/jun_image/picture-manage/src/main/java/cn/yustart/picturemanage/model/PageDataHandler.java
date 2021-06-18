package cn.yustart.picturemanage.model;


import cn.yustart.picturemanage.annotation.PageUrl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 10:44
 * 固定格式：
 * pages/constant
 */
@Component
public class PageDataHandler {


    @PageUrl("pages/console")
    public void pageTable(ModelAndView modelAndView) {
        modelAndView.addObject("name", "Chenzedeng");
    }

}
