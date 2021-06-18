package com.zandili.demo.drools.drink.test;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

import com.zandili.demo.drools.pojo.Message;
import com.zandili.demo.drools.pojo.User;
import com.zandili.demo.drools.test.BaseTest;

/**
 * 1、小明手上有50元钱；
 * 
 * 2、1元钱可以买一瓶饮料；
 * 
 * 3、2个空瓶可以兑换一瓶饮料；
 * 
 * 4、问题是：最终小明可以喝多少瓶饮料；
 * 
 * @author Wujun
 * 
 */
public class DrinkTest extends BaseTest {
//	@Autowired
	private StatefulKnowledgeSession ksession;

	/**
	 * 演示一下小明是怎么喝汽水的
	 */
	@Test
	public void testDrink() {
		// 初始化用户实例
		User user = new User();
		user.setMoney(50);// 设置钱数
		ksession.insert(user);// fact

		// 初始化message实例，演示简单的一个规则
		Message msg = new Message();
		msg.setType("Test");
		ksession.insert(msg);// fact

		// 触发所有规则执行
		ksession.fireAllRules();
		ksession.dispose();// 一定要销毁
		System.out.println("小明还剩钱数：" + user.getMoney());
		System.out.println("message对象的属性在规则中改变：" + msg.getMsg());

		System.out.println("计算完毕");
	}
}
