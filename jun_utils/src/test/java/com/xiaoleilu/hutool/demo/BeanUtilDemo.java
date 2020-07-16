package com.xiaoleilu.hutool.demo;

import java.util.HashMap;

import com.jun.plugin.utils2.lang.Console;
import com.jun.plugin.utils2.util.BeanUtil;

/**
 * BeanUtil类Demo
 * @author Looly
 *
 */
public class BeanUtilDemo {
	public static void main(String[] args) {
		Console.log(BeanUtil.isBean(HashMap.class));
	}
}
