package cn.antcore.security.exception;

/**
 * 用户未登录
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class NoLoginException extends RuntimeException {
    public NoLoginException(String message) {
        super(message);
    }
}
