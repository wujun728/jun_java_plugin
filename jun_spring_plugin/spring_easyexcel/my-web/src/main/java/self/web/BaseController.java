package self.web;

import self.body.WebResponse;
import self.enumeration.ResponseEnum;

/**
 * 基础控制器
 */
public class BaseController {

    public static WebResponse success(String msg){
        return new WebResponse(ResponseEnum.SUCCESS,msg);
    }

    public static WebResponse fail(String msg){
        return new WebResponse(ResponseEnum.FAIL,msg);
    }
}
