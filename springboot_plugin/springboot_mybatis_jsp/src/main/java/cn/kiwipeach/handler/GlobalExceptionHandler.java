package cn.kiwipeach.handler;

import cn.kiwipeach.enums.ResponseCodeEnum;
import cn.kiwipeach.exeception.BusinessException;
import cn.kiwipeach.response.DataResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Create Date: 2017/11/05
 * Description: 全局异常处理类
 *
 * @author Wujun
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error";
    @ExceptionHandler(value = Exception.class)
    public DataResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ResponseCodeEnum codeEnum = ResponseCodeEnum.FAIL;
        if (e instanceof BusinessException){// 若存在编码信息，则从枚举类中获取枚举异常信息
            BusinessException ex = (BusinessException) e;
            codeEnum = ResponseCodeEnum.stateOf(ex.getCode());
        }
        return new DataResponse(false,codeEnum);
    }
}
