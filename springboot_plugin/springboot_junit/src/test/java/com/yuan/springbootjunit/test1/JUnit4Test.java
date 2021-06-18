/**
 * @Auther: yuanyuan
 * @Date: 2018/8/30 09:26
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import org.junit.*;

public class JUnit4Test {
    @Before
    public void before2() {
        System.out.println("@Before22222");
    }

    @Before
    public void before3() {
        System.out.println("@Before33");
    }

    @Before
    public void before() {
        System.out.println("@Before");
    }



    @Test
    public void test() {
        System.out.println("@Test");
        Assert.assertEquals(5 + 5, 10);
    }

    @Ignore
    @Test
    public void testIgnore() {
        System.out.println("@Ignore");
    }

    @Test(timeout = 5000)
    public void testTimeout() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("@Test(timeout = 50)");
        Assert.assertEquals(5 + 5, 10);
    }

    @Test(expected = ArithmeticException.class)
    public void testExpected() {
        System.out.println("@Test(expected = Exception.class)");
        throw new ArithmeticException();
    }

    @After
    public void after() {
        System.out.println("@After");
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("@BeforeClass");
    };

    @AfterClass
    public static void afterClass() {
        System.out.println("@AfterClass");
    };
}
