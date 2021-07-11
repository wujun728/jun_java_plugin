package com.github.greengerong.app;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ***************************************
 * *
 * Auth: green gerong                     *
 * Date: 2014                             *
 * blog: http://greengerong.github.io/    *
 * github: https://github.com/greengerong *
 * *
 * ****************************************
 */
public class ExceptionMethodInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMethodInterceptor.class);

    private ExceptionMethodInterceptor() {
    }

    public static ExceptionMethodInterceptor exception() {
        return new ExceptionMethodInterceptor();
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        final String methodName = getMethodName(methodInvocation);
        try {
            LOGGER.debug(String.format("method(%s) call with: %s.", methodName, getArgs(methodInvocation)));
            final Object result = methodInvocation.proceed();
            LOGGER.debug(String.format("method(%s) return with: %s.", methodName, result));
            return result;
        } catch (Exception e) {
            LOGGER.error(String.format("method(%s) error with: %s.", methodName, e.getCause()), e);
            throw e;
        }

    }

    private Object getArgs(MethodInvocation methodInvocation) {
        final List<String> args = Lists.newArrayList();
        if (methodInvocation.getArguments() != null) {
            for (int i = 0; i < methodInvocation.getArguments().length; i++) {
                final Object arg = methodInvocation.getArguments()[i];
                args.add(arg == null ? "null" : arg.toString());
            }
        }
        return Joiner.on(",").join(args);
    }

    private String getMethodName(MethodInvocation methodInvocation) {
        return String.format("%s-(%s)", methodInvocation.getMethod().getDeclaringClass().getName(), methodInvocation.getMethod());
    }
}
