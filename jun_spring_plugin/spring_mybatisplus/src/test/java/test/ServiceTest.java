package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.springmvc.model.system.User;
import com.baomidou.springmvc.service.system.IUserService;

/**
 * <p>
 * </p>
 *
 * @author yuxiaobin
 * @date 2018/5/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring.xml"})
public class ServiceTest {

    @Autowired
    IUserService iUserService;

    @Test
    public void selectTest(){
        List<User> list = iUserService.selectList(new EntityWrapper<User>());
        System.out.println("****************************************");
        for(User u:list){
            System.out.println(u.getType());
            Assert.assertNotNull("TypeEnum should not null",u.getType());
        }
        System.out.println("****************************************");
    }
}
