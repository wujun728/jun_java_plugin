package com.kancy.emailplus.core;

import com.kancy.emailplus.core.annotation.Order;
import com.kancy.emailplus.core.exception.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LocalEmailSenderPropertiesDataSource
 *
 * @author Wujun
 * @date 2020/2/20 4:15
 */
@Order(Integer.MIN_VALUE)
public class LocalEmailSenderPropertiesDataSource implements EmailSenderPropertiesDataSource {

    private static final Logger log = LoggerFactory.getLogger(LocalEmailSenderPropertiesDataSource.class);

    private static final String PROPERTY_SPLIT_CHAR = ".";

    private static final String EMAIL_PROPERTY_PREFIX = "emailplus.sender";
    private static final Pattern CLASS_RESOURCE_REGEX = Pattern.compile("^classpath:\\s*(\\S+)\\s*$");

    private static final Pattern MATCH_EMAIL_PROPERTY_PATTERN = Pattern.compile(String.format("^%s\\.[0-9A-Za-z]+\\.", EMAIL_PROPERTY_PREFIX));

    private String[] resourcePaths = new String[]{"classpath:email.properties","classpath:META-INF/email.properties"};

    private final Properties properties = new Properties();

    private final Map<String, EmailSenderProperties> mailPropertiesMap = new TreeMap<>();

    public LocalEmailSenderPropertiesDataSource() {
    }

    public LocalEmailSenderPropertiesDataSource(String ... resourcePaths) {
        this.resourcePaths = resourcePaths;
    }

    /**
     * 加载属性
     *
     * @return
     */
    @Override
    public List<EmailSenderProperties> load() {
        for (String resourcePath : resourcePaths) {
            InputStream resourceAsStream = getInputStreamByResourcePath(resourcePath);
            try {
                if (Objects.nonNull(resourceAsStream)) {
                    Set<String> namespaceSet = new HashSet<>();
                    Properties tempProperties = new Properties();
                    tempProperties.load(resourceAsStream);
                    this.properties.putAll(tempProperties);
                    tempProperties.stringPropertyNames().stream()
                            .filter(key -> MATCH_EMAIL_PROPERTY_PATTERN.matcher(key).find())
                            .forEach(key -> {
                                String namespaceAndName = key.substring(EMAIL_PROPERTY_PREFIX.length() + 1);
                                String namespace = namespaceAndName.substring(0, namespaceAndName.indexOf(PROPERTY_SPLIT_CHAR));
                                mailPropertiesMap.putIfAbsent(namespace, new EmailSenderProperties());
                                namespaceSet.add(namespace);
                            });
                    namespaceSet.stream().forEach(this::setMailProperties);
                }
            } catch (EmailException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new EmailException("加载邮件本地配置失败", ex);
            }
        }
        List list = new ArrayList(mailPropertiesMap.values());
        clearCache();
        return list;
    }

    private InputStream getInputStreamByResourcePath(String resourcePath) {
        InputStream resourceAsStream = null;
        try {
            Matcher matcher = CLASS_RESOURCE_REGEX.matcher(resourcePath);
            if (matcher.find()){
                String classResourcePath = matcher.group(1);
                resourceAsStream = LocalEmailSenderPropertiesDataSource.class.getClassLoader().getResourceAsStream(classResourcePath);
            }else {
                resourceAsStream = new FileInputStream(resourcePath);
            }
        } catch (Exception e) {
            log.warn("load email properties file [{}] fail: {}", resourcePath, e.getMessage());
        }
        return resourceAsStream;
    }

    private void setMailProperties(String namespace) {
        Field[] declaredFields = EmailSenderProperties.class.getDeclaredFields();
        EmailSenderProperties emailSenderProperties = mailPropertiesMap.get(namespace);
        emailSenderProperties.setName(namespace);

        for (Field declaredField : declaredFields) {
            String propertyKey = String.format("%s.%s.%s", EMAIL_PROPERTY_PREFIX, namespace, declaredField.getName());
            if (properties.containsKey(propertyKey)) {
                setMailProperties(emailSenderProperties, declaredField, propertyKey);
            }
        }
    }

    private void setMailProperties(EmailSenderProperties emailSenderProperties, Field declaredField, String propertyKey) {
        String property = properties.getProperty(propertyKey);
        if (Objects.nonNull(property) && !property.isEmpty()) {
            try {
                declaredField.setAccessible(true);
                declaredField.set(emailSenderProperties, property);
            } catch (Exception e) {
                try {
                    declaredField.set(emailSenderProperties, Integer.parseInt(property));
                } catch (Exception ex) {
                    throw new EmailException("解析邮件本地配置失败", ex);
                }
            }
        }
    }

    private void clearCache() {
        mailPropertiesMap.clear();
        properties.clear();
    }
}
