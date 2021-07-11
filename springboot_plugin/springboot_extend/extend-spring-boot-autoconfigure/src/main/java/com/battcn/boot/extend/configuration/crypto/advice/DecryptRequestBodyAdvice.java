package com.battcn.boot.extend.configuration.crypto.advice;

import com.battcn.boot.extend.configuration.crypto.CryptoProperties;
import com.battcn.boot.extend.configuration.crypto.annotation.Decrypt;
import com.battcn.boot.extend.configuration.crypto.encrypt.CryptoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 对 RequestBody 中加密信息做解密处理
 *
 * @author Levin
 * @since 2018/12/27 0027
 */
@Slf4j
@RestControllerAdvice
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {

    @Resource
    private CryptoProperties properties;

    private static final String MARK = "\"";

    /**
     * Invoked first to determine if this interceptor applies.
     *
     * @param methodParameter the method parameter
     * @param targetType      the target type, not necessarily the same as the method
     *                        parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType   the selected converter type
     * @return whether this interceptor should be invoked or not
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 检查解密注解是否存在 method 上
        return methodParameter.getMethod().isAnnotationPresent(Decrypt.class);
    }

    /**
     * Invoked second before the extend body is read and converted.
     *
     * @param inputMessage  the extend
     * @param parameter     the target method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return the input extend or a new instance, never {@code null}
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        final InputStream inputStream = inputMessage.getBody();
        try {
            final Charset charset = Charset.forName(properties.getEncoding());
            final Decrypt decrypt = parameter.getMethod().getAnnotation(Decrypt.class);
            final CryptoProperties.Decrypt propertiesDecrypt = properties.getDecrypt();
            final String key = StringUtils.isNotBlank(decrypt.key()) ? decrypt.key() : propertiesDecrypt.getKey();
            final String content = IOUtils.toString(inputStream, charset);
            if (log.isDebugEnabled()) {
                log.info("[解密前的内容] - [{}]", content);
            }
            String decryptContent = CryptoUtils.decryptToString(decrypt.type(), key, content, charset);
            // 擦除 String 双引号
            if (decryptContent.indexOf(MARK) == 0 && decryptContent.lastIndexOf(MARK) == decryptContent.length() - 1) {
                decryptContent = StringUtils.substringBetween(decryptContent, MARK, MARK);
            }
            if (log.isDebugEnabled()) {
                log.info("[解密后的内容] - [{}]", decryptContent);
            }
            return new MappingJacksonInputMessage(IOUtils.toInputStream(decryptContent, charset), inputMessage.getHeaders());
        } catch (IOException e) {
            log.error("格式转换错误", e);
            throw new IOException("格式转换错误)");
        }
    }

    /**
     * Invoked third (and last) after the extend body is converted to an Object.
     *
     * @param body          set to the converter Object before the first advice is called
     * @param inputMessage  the extend
     * @param parameter     the target method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the converter used to deserialize the body
     * @return the same body or a new instance
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Body 读取完毕后执行的操作部分
        return body;
    }

    /**
     * Invoked second (and last) if the body is empty.
     *
     * @param body          usually set to {@code null} before the first advice is called
     * @param inputMessage  the extend
     * @param parameter     the method parameter
     * @param targetType    the target type, not necessarily the same as the method
     *                      parameter type, e.g. for {@code HttpEntity<String>}.
     * @param converterType the selected converter type
     * @return the value to use or {@code null} which may then raise an
     * {@code HttpMessageNotReadableException} if the argument is required.
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
