package com.xiaoleilu.hutool.demo;

import java.util.HashMap;

import com.xiaoleilu.hutool.lang.Console;
import com.xiaoleilu.hutool.util.BeanUtil;

/**
 * BeanUtil类Demo
 * @author Wujun
 *
 */
public class BeanUtilDemo {
	public static void main(String[] args) {
		Console.log(BeanUtil.isBean(HashMap.class));
	}
}
