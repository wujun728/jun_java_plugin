package cn.kiiwii.framework;

import cn.kiiwii.framework.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhong on 2016/9/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceTest {

    @Autowired
    private IUserService userService;
    @Test
    public void testSetValue(){
        this.userService.set();
        Assert.assertTrue(true);
    }
    @Test
    public void testSetList(){
        this.userService.setList();
        Assert.assertTrue(true);
    }
    @Test
    public void testSetHash(){
        this.userService.setHash();
        Assert.assertTrue(true);
    }
    @Test
    public void testSetObject(){
        this.userService.setObject();
        Assert.assertTrue(true);
    }
}
