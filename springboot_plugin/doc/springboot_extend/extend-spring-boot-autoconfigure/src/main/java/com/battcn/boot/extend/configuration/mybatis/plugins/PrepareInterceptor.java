package com.battcn.boot.extend.configuration.mybatis.plugins;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Levin
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@AllArgsConstructor
public class PrepareInterceptor implements Interceptor {

    private final PreparePluginProperties preparePluginProperties;
    private final PreparePluginContent preparePluginContent;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //注解中method的值
        String methodName = invocation.getMethod().getName();
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (StringUtils.equalsIgnoreCase(SqlCommandType.UPDATE.name(), methodName)) {
            Object parameter = invocation.getArgs()[1];
            processFieldValue(parameter, sqlCommandType);
        }
        return invocation.proceed();
    }


    private void processFieldValue(Object parameter, SqlCommandType sqlCommandType) throws IllegalAccessException, NoSuchFieldException {
        boolean superClass = preparePluginProperties.getSuperClass();
        Map<String, Object> process = preparePluginContent.process();
        //对有要求的字段填值
        Field[] parameterFields = superClass ? parameter.getClass().getSuperclass().getDeclaredFields() : parameter.getClass().getDeclaredFields();
        for (Field field : parameterFields) {
            String fieldName = field.getName();
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                PreparePluginProperties.ClassField classField = preparePluginProperties.getInsert();
                if (classField != null) {
                    setFieldValue(parameter, superClass, process, fieldName, classField.getFields());
                } else {
                    log.debug("[Insert ClassField 为空，不做赋值操作]");
                }
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                PreparePluginProperties.ClassField classField = preparePluginProperties.getUpdate();
                if (classField != null) {
                    setFieldValue(parameter, superClass, process, fieldName, classField.getFields());
                } else {
                    log.debug("[Update ClassField 为空，不做赋值操作]");
                }
            }
        }
    }

    private void setFieldValue(Object parameter, boolean superClass, Map<String, Object> process, String fieldName, List<String> fields) throws NoSuchFieldException, IllegalAccessException {
        if (fields == null || fields.size() == 0) {
            log.warn("[需要处理的字段为空，请添加相关字段]");
            return;
        }
        if (fields.contains(fieldName)) {
            if (superClass) {
                Field createdBy = parameter.getClass().getSuperclass().getDeclaredField(fieldName);
                createdBy.setAccessible(true);
                createdBy.set(parameter, process.get(fieldName));
            } else {
                Field createdBy = parameter.getClass().getDeclaredField(fieldName);
                createdBy.setAccessible(true);
                createdBy.set(parameter, process.get(fieldName));
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}