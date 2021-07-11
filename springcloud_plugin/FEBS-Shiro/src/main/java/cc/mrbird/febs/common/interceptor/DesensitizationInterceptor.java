package cc.mrbird.febs.common.interceptor;

import cc.mrbird.febs.common.annotation.Desensitization;
import cc.mrbird.febs.common.entity.DesensitizationType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * SQL拦截器，用于数据脱敏
 *
 * @author MrBird
 */
@Data
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),})
public class DesensitizationInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        if (result instanceof ArrayList<?>) {
            List<?> list = (ArrayList<?>) result;
            return desensitization(list);
        } else {
            return desensitization(result);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private List<?> desensitization(List<?> list) {
        Class<?> cls = null;
        Field[] objFields = null;
        if (list != null && list.size() > 0) {
            for (Object o : list) {
                if (cls == null) {
                    cls = o.getClass();
                    objFields = cls.getDeclaredFields();
                }
                desensitizationField(o, objFields);
            }
        }
        return list;
    }

    private Object desensitization(Object obj) {
        Class<?> cls;
        Field[] objFields;
        if (obj != null) {
            cls = obj.getClass();
            objFields = cls.getDeclaredFields();
            desensitizationField(obj, objFields);
        }
        return obj;
    }

    private void desensitizationField(Object obj, Field[] objFields) {
        for (Field field : objFields) {
            Desensitization desensitization;
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            if (String.class != field.getType() || (desensitization = field.getAnnotation(Desensitization.class)) == null) {
                continue;
            }
            field.setAccessible(true);
            String value;
            try {
                value = field.get(obj) != null ? field.get(obj).toString() : null;
            } catch (Exception e) {
                value = null;
            }
            if (value == null) {
                continue;
            }
            List<String> regular;
            DesensitizationType type = desensitization.type();
            regular = Arrays.asList(type.getRegular());
            if (regular.size() > 1) {
                String match = regular.get(0);
                String result = regular.get(1);
                if (StringUtils.isNotBlank(match) && StringUtils.isNotBlank(result)) {
                    value = value.replaceAll(match, result);
                    try {
                        field.set(obj, value);
                    } catch (Exception ignore) {
                    }
                }
            }
        }
    }
}
