package com.osmp.web.user.controller;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月28日 下午2:23:06
 */

public class 定制 {

	public void parse(float 本金, float 定投, float 年化, int 年) {
		for (int i = 0; i < 年 * 12; i++) {
			本金 = 本金 + 定投 + (本金 + 定投) * 年化 / 365;
		}
		System.out.println(本金);
	}

	public void parse2(float 本金, float 年化, int 年) {
		for (int i = 0; i < 年 * 12; i++) {
			本金 += 本金 * 年化 / 365;
		}
		System.out.println(本金);
	}

	public static void main(String[] args) {
		float 年化 = 0.07f;
		float 本金 = 0f;
		int 年 = 15;
		float 定投 = 3600f;

		new 定制().parse(本金, 定投, 年化, 年);
		new 定制().parse2(659376.6f, 年化, 20);
	}

}
