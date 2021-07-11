package cn.kiiwii.framework.mybatis;

import cn.kiiwii.framework.mybatis.model.Account;
import cn.kiiwii.framework.mybatis.service.ITestService;
import cn.kiiwii.framework.mybatis.service.ITestXmlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhong on 2016/12/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestServiceTest {

    @Resource(name = "testService")
    private ITestService testService;
    @Resource(name = "testXmlService")
    private ITestXmlService testXmlService;

    @Test
    public void testInsert(){

        Account account = new Account();
        account.setMoney(1000);
        account.setName("kael");
        try {
            int id = testService.insertAccount(account);
            System.out.println("==============================="+id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test
    public void testXmlInsert(){

        Account account = new Account();
        account.setMoney(1000);
        account.setName("小小1");
        try {
            int id = testXmlService.insertAccount(account);
            System.out.println("==============================="+id);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
	public void testTransfer() {
		boolean b;
		try {
			b = testService.transfer(200, 1, 2);
			if(b){
				System.out.println("转账成功");
			}else{
				System.out.println("转账失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("转账失败");
		}

	}

    @Test
    public void testFind(){

        try {
            Account account = testService.findAccountById(3);
            System.out.println(account);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testList(){

        try {
            List<Account> accounts = testService.findAccountsById(3);
            System.out.println(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testXmlList(){

        try {
//			List<Account> accounts = testXmlService.findAccountsById(3);
            Account account = testXmlService.findAccountById(3);
            Account account2 = testXmlService.findAccountById(3);
            System.out.println(account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
