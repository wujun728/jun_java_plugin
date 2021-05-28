package cn.kiwipeach.exeception;

/**
 * Create Date: 2017/11/06
 * Description: 自定义基础异常
 *
 * @author Wujun
 */
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String defaultMessage;

    public BaseException(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public BaseException(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
