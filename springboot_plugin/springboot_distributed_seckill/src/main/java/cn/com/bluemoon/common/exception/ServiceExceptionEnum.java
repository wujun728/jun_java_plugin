package cn.com.bluemoon.common.exception;

/**
 * 抽象接口
 *
 * @author Wujun
 * @date 2017-12-28-下午10:27
 */
public interface ServiceExceptionEnum {
    
    /**
     * 请求是否成功
     */
    Boolean getIsSuccess();
    
    /**
     * 获取返回的code
     */
    Integer getResponseCode();
    
    /**
     * 获取返回的message
     */
    String getResponseMsg();
}
