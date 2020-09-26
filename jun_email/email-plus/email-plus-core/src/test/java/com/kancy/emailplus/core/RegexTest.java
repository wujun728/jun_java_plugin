package com.kancy.emailplus.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexTest
 *
 * @author kancy
 * @date 2020/2/20 14:42
 */
public class RegexTest {

    @Test
    public void test(){
        Pattern reg = Pattern.compile("^ENC\\((.*)\\)$");
        Matcher matcher = reg.matcher("ENC( password )");
        System.out.println(matcher.find());
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(1).trim());
        Assert.assertEquals("password", matcher.group(1).trim());
    }
}
