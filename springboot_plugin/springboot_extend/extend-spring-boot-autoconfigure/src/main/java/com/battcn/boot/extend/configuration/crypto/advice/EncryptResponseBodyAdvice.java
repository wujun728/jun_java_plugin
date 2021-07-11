package com.battcn.boot.extend.configuration.crypto.advice;

import com.battcn.boot.extend.configuration.crypto.CryptoProperties;
import com.battcn.boot.extend.configuration.crypto.annotation.Encrypt;
import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import java.nio.charset.Charset;

/**
 * 对 ResponseBody 返回的信息做加密处理
 *
 * @author Levin
 * @since 2018/12/28 0028
 */
@Slf4j
@RestControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {

    @Resource
    private CryptoProperties properties;

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 检查加密注解是否存在 method 上
        return returnType.getMethod().isAnnotationPresent(Encrypt.class);
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current extend
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String content = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            content = objectMapper.writeValueAsString(body);
            if (log.isDebugEnabled()) {
                log.info("[加密前的内容] - [{}]", content);
            }

        } catch (JsonProcessingException e) {
            log.error("格式转换错误", e);
        }
        final Charset charset = Charset.forName(properties.getEncoding());
        final Encrypt encrypt = returnType.getMethodAnnotation(Encrypt.class);
        final CryptoProperties.Encrypt propertiesEncrypt = properties.getEncrypt();
        final String key = StringUtils.isNotBlank(encrypt.key()) ? encrypt.key() : propertiesEncrypt.getKey();
        final String result = CryptoUtils.encryptToString(encrypt.type(), key, content, charset);
        if (log.isDebugEnabled()) {
            log.info("[加密后的内容] - [{}]", result);
        }
        return result;
    }
}
