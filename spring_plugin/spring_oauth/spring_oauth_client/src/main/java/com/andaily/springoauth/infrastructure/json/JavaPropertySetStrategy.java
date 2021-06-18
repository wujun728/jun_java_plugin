package com.andaily.springoauth.infrastructure.json;

import net.sf.json.JSONException;
import net.sf.json.util.PropertySetStrategy;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Convert DB namespace to java field namespace.
 * It is a singleton object.
 * <p/>
 * E.g.
 * access_token   ->   accessToken
 * token_type   ->   tokenType
 * username         ->   username
 * parent_key_id  ->   parentKeyId
 * Name  ->   name
 * <p/>
 * <p/>
 * Otherwise, ignore it
 *
 * @author Shengzhao Li
 */
public final class JavaPropertySetStrategy extends PropertySetStrategy {

    public static final JavaPropertySetStrategy INSTANCE = new JavaPropertySetStrategy();

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaPropertySetStrategy.class);

    private static final char SEPARATOR = '_';

    private JavaPropertySetStrategy() {
    }

    @Override
    public void setProperty(Object bean, String key, Object value) throws JSONException {
        final String field = keyToField(key);
        final Field beanField = findBeanField(bean, field);
        if (beanField != null) {
            beanField.setAccessible(true);
            try {
                beanField.set(bean, value);
            } catch (IllegalAccessException e) {
                LOGGER.debug("Unhandled field: " + field + ",key: " + key + "; ignore it ", e);
            }
        }
    }

    private Field findBeanField(Object bean, String field) {
        Class<?> aClass = bean.getClass();
        Field beanField;
        do {
            beanField = recursiveFindFiled(aClass, field);
            aClass = aClass.getSuperclass();
        } while (aClass != Object.class && beanField == null);

        return beanField;
    }

    private Field recursiveFindFiled(Class<?> clazz, String field) {
        try {
            return clazz.getDeclaredField(field);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }


    private String keyToField(String key) {
        if (key.length() == 0) {
            return key;
        }

        StringBuilder sb = new StringBuilder();
        char[] chars = key.toCharArray();

        //If first char is Uppercase, update it to lowercase
        if (key.indexOf(SEPARATOR) == -1 && ('A' <= chars[0] && chars[0] <= 'Z')) {
            chars[0] = (char) (chars[0] + 'a' - 'A');
            return String.valueOf(chars);
        }


        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == SEPARATOR) {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}