package io.github.wujun728.common.exception;


import io.github.wujun728.common.exception.code.BaseResponseCode;
import io.github.wujun728.common.exception.code.ResponseCodeInterface;

/**
 * BusinessException
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 6610083281801529147L;

    /**
     * 异常编号
     */
    private final int messageCode;

    /**
     * 对messageCode 异常信息进行补充说明
     */
    private final String detailMessage;

    public BusinessException(int messageCode, String message) {
        super(message);
        this.messageCode = messageCode;
        this.detailMessage = message;
    }

    public BusinessException(String message) {
        super(message);
        this.messageCode = BaseResponseCode.OPERATION_ERRO.getCode();
        this.detailMessage = message;
    }

    /**
     * 构造函数
     *
     * @param code 异常码
     */
    public BusinessException(ResponseCodeInterface code) {
        this(code.getCode(), code.getMsg());
    }

    public int getMessageCode() {
        return messageCode;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
