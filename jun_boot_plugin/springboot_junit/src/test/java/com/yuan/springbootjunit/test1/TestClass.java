/**
 * @Auther: yuanyuan
 * @Date: 2018/8/8 14:23
 * @Description:
 */
package com.yuan.springbootjunit.test1;



import com.yuan.springbootjunit.SpringbootJunitApplication;
import com.yuan.springbootjunit.test.TestApp;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//指定我们SpringBoot工程的Application启动类
//新版的Spring Boot取消了@SpringApplicationConfiguration这个注解，用@SpringBootTest就可以
@SpringBootTest(classes = SpringbootJunitApplication.class)
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
//测了，不用一下配置也可以，不知道为毛
@WebAppConfiguration
public class TestClass {

    private TestApp testApp;



    @Before
    public void  before(){
        testApp = new TestApp();
    }

    @After
    public void after(){
        testApp = null;
    }

    @Test
    public void test1(){
        //String responseString = "";
        //Junit断言
        //assertTrue(responseString.contains("color") || responseString.contains("colour"));
        //Hamcrest
        //assertThat(responseString, anyOf(containsString("color"), containsString("colour")));

        assertThat(118,is(testApp.add(120,18)));
        assertThat(200,greaterThan(55));
        assertThat(20,allOf(greaterThan(10), lessThan(120)));

        /**数值匹配**/
        //测试变量是否大于指定值
        assertThat(100, greaterThan(50));
        //测试变量是否小于指定值
        assertThat(30, lessThan(100));
        //测试变量是否大于等于指定值
        assertThat(50, greaterThanOrEqualTo(50));
        //测试变量是否小于等于指定值
        assertThat(30, lessThanOrEqualTo(100));
        //测试所有条件必须成立
        assertThat(60, allOf(greaterThan(50),lessThan(100)));
        //测试只要有一个条件成立
        assertThat(30, anyOf(greaterThanOrEqualTo(50), lessThanOrEqualTo(100)));
        //测试无论什么条件成立(还没明白这个到底是什么意思)
        assertThat(30, anything());
        //测试变量值等于指定值
        assertThat(100, is(100));
        //测试变量不等于指定值
        assertThat(30, not(50));

//        assertThat(user1,is(user2));

        /**测试字符串是否匹配**/

        String aa = "abcdefg";

        assertThat(aa, containsString("bcd"));
        //测试变量是否已指定字符串开头
        assertThat(aa, startsWith("abc"));
        //测试变量是否以指定字符串结尾
        assertThat(aa, endsWith("efg"));
        //测试变量是否等于指定字符串
        assertThat(aa, equalTo("abcdefg"));
        //测试变量再忽略大小写的情况下是否等于指定字符串
        assertThat(aa, equalToIgnoringCase("ABCDefg"));
        //测试变量再忽略头尾任意空格的情况下是否等于指定字符串
        assertThat(aa, equalToIgnoringWhiteSpace("  abcdefg  "));


        /**集合匹配*/

        HashMap<String,String> mapObject = new HashMap();
        mapObject.put("key","value");
        /**hasEntry匹配符断言被测的Map对象mapObject含有一个键值为"key"对应元素值为"value"的Entry项*/
        assertThat(mapObject, hasEntry("key", "value" ) );
        /**hasItem匹配符表明被测的迭代对象iterableObject含有元素element项则测试通过*/
        //assertThat(iterableObject, hasItem (element));
        /** hasKey匹配符断言被测的Map对象mapObject含有键值“key”*/
        assertThat(mapObject, hasKey ("key"));
        /** hasValue匹配符断言被测的Map对象mapObject含有元素值value*/
        assertThat(mapObject, hasValue("value"));

    }


    @Test(expected = IllegalArgumentException.class)
        public void test2(){
        testApp.canVote(0);

    }


    @Rule
    public ExpectedException  expectedException =  ExpectedException.none();
    @Test
    public void test3(){
        expectedException.expect(IllegalArgumentException.class);
        //expectedException.expect(NullPointerException.class);
        testApp.canVote(1) ;
        //throw new NullPointerException();
    }

    @Test
    public void test4(){
        try {
            testApp.canVote(1);
            fail("expected IllegalArgumentException to be thrown");
        }catch (IllegalArgumentException ex){
            assertThat(ex.getMessage(),containsString("age should be +"));
        }
    }




}
