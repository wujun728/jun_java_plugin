package cn.classg.test;

import cn.classg.common.exception.CustomException;
import org.junit.Test;

public class SpringMyBatisTest {

    /*@Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService bookServiceImpl = (BookService) applicationContext.getBean("BookServiceImpl");
        System.out.println(bookServiceImpl.queryAllBooks());
    }*/

    @Test
    public void test01() {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            throw new CustomException(500, "错误错误");
        }
    }

}
