package com.baijob.commonTools;

import org.junit.Assert;
import org.junit.Test;

public class SettingTest {
	private Setting setting = new Setting("config/example.setting", "utf8", true);
	
	/**
	 * 普通键值对测试
	 */
	@Test
	public void settingTest() {

		boolean bool = setting.getBool("bool.key");
		char char1 = setting.getChar("char.key");
		String string = setting.getString("string1");
		String string2 = setting.getString("string2");
		
		Assert.assertTrue(bool);
		Assert.assertEquals(char1, 'A');
		Assert.assertEquals(string, "String of value");
		Assert.assertEquals(string2, "String of value other");
		
	}
	
	/**
	 * 带有分组的键值测试
	 */
	@Test
	public void settingGroupTest() {
		String group = "group1";
		boolean g_bool = setting.getBool("bool.key", group);
		char g_char1 = setting.getChar("char.key", group);
		String g_string = setting.getString("string1", group);
		String g_string2 = setting.getString("string2", group);
		
		Assert.assertTrue(g_bool);
		Assert.assertEquals(g_char1, 'B');
		Assert.assertEquals(g_string, "String of value with group");
		Assert.assertEquals(g_string2, "String of value with group append string");
	}
}
