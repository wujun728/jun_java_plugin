package io.github.wujun728.common.exception.code;

/**
 * ResponseCodeInterface
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public interface IRespCodeMsg {
    /**
     * 获取code
     *
     * @return code
     */
    int getCode();

    /**
     * 获取信息
     *
     * @return msg
     */
    String getMsg();
}
