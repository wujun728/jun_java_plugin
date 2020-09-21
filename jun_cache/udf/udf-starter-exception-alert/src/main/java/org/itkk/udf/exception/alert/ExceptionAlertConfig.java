package org.itkk.udf.exception.alert;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述 : ExceptionAlertConfig
 *
 * @author wangkang
 */
@Configuration
public class ExceptionAlertConfig {

    /**
     * exchangeExceptionAlert
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange exchangeExceptionAlert() {
        return new DirectExchange(ExceptionAlertConstant.EXCHANGE_EXCEPTION_ALERT.class.getSimpleName(), true, false);
    }

    /**
     * exchangeExceptionAlertDlx
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange exchangeExceptionAlertDlx() {
        return new DirectExchange(ExceptionAlertConstant.EXCHANGE_EXCEPTION_ALERT_DLX.class.getSimpleName(), true, false);
    }

}
