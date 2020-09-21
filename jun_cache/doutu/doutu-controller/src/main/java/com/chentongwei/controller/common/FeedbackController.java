package com.chentongwei.controller.common;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.entity.common.io.FeedbackIO;
import com.chentongwei.entity.common.vo.UserVO;
import com.chentongwei.service.common.IFeedbackService;
import com.chentongwei.util.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 反馈接口
 *
 * @author TongWei.Chen 2017-06-21 14:55:15
 */
@RestController
@RequestMapping("/common/feedback")
@CategoryLog(menu = "公用部分", subMenu = "反馈")
public class FeedbackController {

    @Autowired
    private IFeedbackService feedbackService;

    /**
     * 新增反馈
     * @param feedbackIO
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @DescLog("新增反馈")
    public Result save(@Valid FeedbackIO feedbackIO, HttpServletRequest request) {
        //获取ip
        feedbackIO.setIp(IPUtil.getIP(request));
        //获取创建人
        HttpSession session = request.getSession();
        String token = (String)session.getAttribute("token");
        String authCode = (String)session.getAttribute("authCode");

        if(! Objects.equals(token, feedbackIO.getToken())) {
            throw new BussinessException(ResponseEnum.AUTHCODE_ERROR);
        }

        if (! Objects.equals(authCode, feedbackIO.getAuthCode())) {
            throw new BussinessException(ResponseEnum.AUTHCODE_ERROR);
        }

        UserVO user = (UserVO) session.getAttribute("user");
        feedbackIO.setCreatorId(user == null ? -1L : user.getId());
        feedbackIO.setCreator(user == null ? "游客" : user.getLoginName());

        session.removeAttribute("token");
        session.removeAttribute("authCode");

        return ResultCreator.getSuccess(feedbackService.save(feedbackIO));
    }
}
