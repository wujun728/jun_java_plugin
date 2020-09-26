package com.kancy.emailplus.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MailPropertiesDataSourceTest
 *
 * @author kancy
 * @date 2020/2/20 5:01
 */
public class EmailSenderPropertiesDataSourceTest {

    @Test
    public void testLocalMailPropertiesDataSource(){
        LocalEmailSenderPropertiesDataSource dataSource = new LocalEmailSenderPropertiesDataSource();
        List<EmailSenderProperties> emailSenderProperties = dataSource.load();
        System.out.println(emailSenderProperties);
        Assert.assertEquals(3, emailSenderProperties.size());
    }
    @Test
    public void testResourceReg(){
        Pattern CLASS_RESOURCE_REG = Pattern.compile("^classpath:\\s*(\\S+)\\s*$");
        Matcher matcher = CLASS_RESOURCE_REG.matcher("classpath:ss//99//s  ");
        System.out.println(matcher.find());
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
    }

}
