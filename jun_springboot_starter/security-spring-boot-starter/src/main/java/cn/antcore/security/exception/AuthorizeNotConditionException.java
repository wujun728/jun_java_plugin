package cn.antcore.security.exception;

/**
 * 权限未匹配异常
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class AuthorizeNotConditionException extends RuntimeException {
    public AuthorizeNotConditionException(String message) {
        super(message);
    }
}
