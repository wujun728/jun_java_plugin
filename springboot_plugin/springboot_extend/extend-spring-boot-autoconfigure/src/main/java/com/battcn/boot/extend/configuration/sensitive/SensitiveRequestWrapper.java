package com.battcn.boot.extend.configuration.sensitive;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Levin
 * @since 2018/12/25 0025
 */
public class SensitiveRequestWrapper extends HttpServletRequestWrapper {

    private SensitiveProperties sensitiveProperties;
    private static final SensitiveProcessor PROCESSOR = SensitiveProcessor.getInstance();

    public SensitiveRequestWrapper(HttpServletRequest request, SensitiveProperties sensitiveProperties) {
        super(request);
        this.sensitiveProperties = sensitiveProperties;
    }

    @Override
    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);
        if (values == null || values.length <= 0) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = PROCESSOR.format(values[i], sensitiveProperties.getReplacement());
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return PROCESSOR.format(value, sensitiveProperties.getReplacement());
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return PROCESSOR.format(value, sensitiveProperties.getReplacement());
    }
}

