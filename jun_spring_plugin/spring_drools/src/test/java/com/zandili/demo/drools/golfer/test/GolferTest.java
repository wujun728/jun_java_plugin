package com.zandili.demo.drools.golfer.test;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

import com.zandili.demo.drools.pojo.Golfer;
import com.zandili.demo.drools.test.BaseTest;

/**
 * 演示高尔夫推理题
 * 
 * @author Wujun
 * 
 */
public class GolferTest extends BaseTest {
	private StatefulKnowledgeSession ksession;

	/**
	 * 演示高尔夫推理题<br>
	 * 1. 四个高尔夫选手从左向右排成一排, 站在树下. 每个选手穿着不通颜色的裤子.<br>
	 * 其中一个穿红色. <br>
	 * 在Fred右边的一个选手穿蓝色.
	 * 
	 * 2.Joe在队列中排第二.
	 * 
	 * 3.Bob穿着彩色格呢的裤子.
	 * 
	 * 4.Tom不在第一或者第四, 他没有穿那种让人恶心的桔黄色裤子.
	 * 
	 * 那么, 问题是, 这四个人在队列中的顺序是什么? 他们各自穿什么颜色的裤子?
	 */
	@Test
	public void testGolfer() {
		String[] names = new String[] { "Fred", "Joe", "Bob", "Tom" };
		String[] colors = new String[] { "red", "blue", "plaid", "orange" };
		int[] positions = new int[] { 1, 2, 3, 4 };

		for (int n = 0; n < names.length; n++) {
			for (int c = 0; c < colors.length; c++) {
				for (int p = 0; p < positions.length; p++) {
					ksession.insert(new Golfer(names[n], colors[c],
							positions[p]));
				}
			}
		}
		ksession.fireAllRules();
		ksession.dispose();
	}
}
