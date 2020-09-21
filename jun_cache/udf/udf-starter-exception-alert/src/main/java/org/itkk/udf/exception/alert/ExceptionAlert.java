package org.itkk.udf.exception.alert;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.amqp.rabbitmq.Rabbitmq;
import org.itkk.udf.amqp.rabbitmq.RabbitmqMessage;
import org.itkk.udf.core.RestResponse;
import org.itkk.udf.core.exception.alert.IExceptionAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ExceptionAlert
 */
@Component
@Slf4j
public class ExceptionAlert implements IExceptionAlert {

    /**
     * exceptionAlertProperties
     */
    @Autowired
    private ExceptionAlertProperties exceptionAlertProperties;

    /**
     * 描述 : springApplicationName
     */
    @Value("${spring.application.name}")
    private String springApplicationName;

    /**
     * port
     */
    @Value("${server.port}")
    private Integer port;

    /**
     * profiles
     */
    @Value("${spring.profiles.active}")
    private String profiles;

    /**
     * 描述 : rabbitmq
     */
    @Autowired
    private Rabbitmq rabbitmq;

    @Override
    public void alert(RestResponse<String> errorResponse) {
        //开关打开,才进行异常信息发送
        if (exceptionAlertProperties.getEnable()) {
            //发送消息标志位
            Boolean sendFlg = true;
            //判断
            sendFlg = this.checkExclude(sendFlg, errorResponse);
            //发送消息
            if (sendFlg) {
                //构造消息头
                Map<String, String> header = new HashMap<>();
                header.put("springApplicationName", springApplicationName);
                header.put("port", String.valueOf(port));
                header.put("profiles", profiles);
                //构造消息
                RabbitmqMessage<RestResponse<String>> alert = new RabbitmqMessage<>(header, errorResponse);
                //发送消息
                rabbitmq.convertAndSend(ExceptionAlertConstant.EXCHANGE_EXCEPTION_ALERT.class.getSimpleName(), ExceptionAlertConstant.EXCHANGE_EXCEPTION_ALERT.ALERT.name(), alert);
            }
        }
    }

    /**
     * checkExclude
     *
     * @param sendFlg       sendFlg
     * @param errorResponse errorResponse
     * @return boolean
     */
    private boolean checkExclude(Boolean sendFlg, RestResponse<String> errorResponse) { //NOSONAR
        if (errorResponse != null) {
            //逻辑判断
            if (errorResponse.getError() == null) { //error的空判断
                log.warn("errorResponse error is null");
                sendFlg = false;
            }
            if (StringUtils.isNotBlank(errorResponse.getError().getType()) && StringUtils.isNotBlank(exceptionAlertProperties.getExcludeType())) { //类型的排除判断
                String[] excludeTypes = exceptionAlertProperties.getExcludeType().split(",");
                for (String excludeType : excludeTypes) {
                    if (errorResponse.getError().getType().contains(excludeType)) {
                        log.warn("exclude type : " + excludeType);
                        sendFlg = false;
                    }
                }
            }
            if (StringUtils.isNotBlank(errorResponse.getError().getMessage()) && StringUtils.isNotBlank(exceptionAlertProperties.getExcludeMessage())) { //信息的排除判断
                String[] excludeMessages = exceptionAlertProperties.getExcludeMessage().split(",");
                for (String excludeMessage : excludeMessages) {
                    if (errorResponse.getError().getMessage().contains(excludeMessage)) {
                        log.warn("exclude message : " + excludeMessage);
                        sendFlg = false;
                    }
                }
            }
            if (StringUtils.isNotBlank(errorResponse.getError().getStackTrace()) && StringUtils.isNotBlank(exceptionAlertProperties.getExcludeStackTrace())) { //堆栈的排除判断
                String[] excludeStackTraces = exceptionAlertProperties.getExcludeStackTrace().split(",");
                for (String excludeStackTrace : excludeStackTraces) {
                    if (errorResponse.getError().getStackTrace().contains(excludeStackTrace)) {
                        log.warn("exclude stackTrace : " + excludeStackTrace);
                        sendFlg = false;
                    }
                }
            }
            //子错误
            sendFlg = checkExclude(sendFlg, errorResponse.getError().getChild());
        }
        //返回
        return sendFlg;
    }

}
