package org.itkk.udf.exception.alert;

/**
 * 描述 : ExceptionAlertConstant
 *
 * @author Administrator
 */
public class ExceptionAlertConstant {

    /**
     * (EXCHANGE)消息记录
     */
    public enum EXCHANGE_EXCEPTION_ALERT {
        /**
         * 提醒
         */
        ALERT
    }

    /**
     * (EXCHANGE)异常提醒(死信)
     */
    public enum EXCHANGE_EXCEPTION_ALERT_DLX {
        /**
         * 提醒
         */
        ALERT_DLX
    }

    /**
     * 描述 : 私有化构造函数
     */
    private ExceptionAlertConstant() {
    }

}
