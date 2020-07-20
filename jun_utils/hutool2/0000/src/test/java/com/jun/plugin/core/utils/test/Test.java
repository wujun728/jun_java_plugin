package com.jun.plugin.core.utils.test;

import java.util.concurrent.TimeUnit;

import com.jun.plugin.core.utils.lang.Console;
import com.jun.plugin.core.utils.setting.Setting;
import com.jun.plugin.core.utils.util.ThreadUtil;

/**
 * 仅用于临时测试
 * 
 * @author Looly
 *
 */
public class Test {
	public static void main(String[] args) throws InterruptedException {
		Setting setting = new Setting("config/db.setting");
		setting.autoLoad(true);
		Console.log("####" + setting);
		
		ThreadUtil.sleep(5, TimeUnit.HOURS);
	}
}
