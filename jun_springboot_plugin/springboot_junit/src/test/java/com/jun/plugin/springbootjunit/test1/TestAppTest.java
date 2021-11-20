/**
 * @Auther: yuanyuan
 * @Date: 2018/9/3 10:38
 * @Description:
 */
package com.jun.plugin.springbootjunit.test1;

import org.junit.Before;
import org.junit.Test;

import com.jun.plugin.springbootjunit.test.TestApp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

public class TestAppTest {

    private  TestApp testApp;

    @Before
    public void  testapp(){

        testApp = new TestApp();
    }

    @Test
    public void testAdd(){

        int add = testApp.add(10, 20);
        assertThat(30,is(add));
    }


    @Test
    public void  testCan(){

        try {
            testApp.canVote(0);
            fail("expected IllegalArgumentException to be thrown");
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(),containsString("age should be +"));
        }


    }
}
