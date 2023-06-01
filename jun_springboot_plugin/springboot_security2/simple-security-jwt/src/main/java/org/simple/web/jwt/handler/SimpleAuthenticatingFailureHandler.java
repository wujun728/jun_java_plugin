package org.simple.web.jwt.handler;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleAuthenticatingFailureHandler implements AuthenticationFailureHandler, InitializingBean, MessageSourceAware {

    private static String AUTH_FAILURE_MESSAGES = "k.security.authenticate.failure";

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private static String buildExceptionMessageKey(AuthenticationException e) {
        return AUTH_FAILURE_MESSAGES + "." + AuthenticationException.class.getSimpleName();
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String defaultFailureMessage = messages.getMessage(AUTH_FAILURE_MESSAGES, "Authentication failed");
        String exceptionMessage = messages.getMessage(buildExceptionMessageKey(e), defaultFailureMessage);

        response.sendError(HttpServletResponse.SC_FORBIDDEN, exceptionMessage);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(messages, "The message accessor can't be null!");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

