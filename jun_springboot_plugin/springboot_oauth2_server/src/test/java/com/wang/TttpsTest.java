package com.wang;

import com.wang.utils.HttpClientUtil;
import junit.framework.TestCase;

/**
 * Created by wangxiangyun on 2017/3/13.
 */
public class TttpsTest extends TestCase{

    public void test01(){
        String str=HttpClientUtil.doGet("https://www.hao123.com");
        System.out.println(str);
    }
}
