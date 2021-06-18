package com.chentongwei.email.factory;

import com.chentongwei.email.enums.MailContentTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 邮件发送bean工厂类
 *
 * @author Wujun
 * @Project common-boot-email
 */
public class MailFactory {

    private MailFactory() {}

    private static final class InnerMailFactory {
        private static final MailFactory MAIL_FACTORY = new MailFactory();
    }

    private static Map<String, String> maps = new HashMap<>();

    static {
        maps.put(MailContentTypeEnum.TEXT.value(), getStrategyClassName("simple"));
        maps.put(MailContentTypeEnum.HTML.value(), getStrategyClassName("html"));
        maps.put(MailContentTypeEnum.TEMPLATE.value(), getStrategyClassName("template"));
    }

    public static final MailFactory getInstance() {
        return InnerMailFactory.MAIL_FACTORY;
    }

    public String get(String type) {
        return maps.get(type);
    }

    /**
     * 获取策略类名
     *
     * @param classNamePrefix：类名前缀
     * @return
     */
    private static String getStrategyClassName(String classNamePrefix) {
        return classNamePrefix + "MailStrategy";
    }

}
