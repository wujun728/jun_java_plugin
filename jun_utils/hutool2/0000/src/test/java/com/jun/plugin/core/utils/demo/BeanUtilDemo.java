package com.jun.plugin.core.utils.demo;

import java.util.HashMap;

import com.jun.plugin.core.utils.lang.Console;
import com.jun.plugin.core.utils.util.BeanUtil;

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
