/**
 * @Auther: yuanyuan
 * @Date: 2018/9/5 10:04
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonControllerTest.class, TestAppTest.class,TestClass.class})
public class SuiteTest {

}
