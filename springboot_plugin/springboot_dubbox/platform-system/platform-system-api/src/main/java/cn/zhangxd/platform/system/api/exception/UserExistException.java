package cn.zhangxd.platform.system.api.exception;


import cn.zhangxd.platform.system.api.exception.base.BusinessException;

/**
 * 用户已存在
 *
 * @author Wujun
 */
public class UserExistException extends BusinessException {

    public UserExistException(String message) {
        super(message);
    }

}
