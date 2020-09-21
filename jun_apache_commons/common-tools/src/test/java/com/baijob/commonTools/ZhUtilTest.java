package com.baijob.commonTools;

import org.junit.Assert;
import org.junit.Test;

public class ZhUtilTest {
	@Test
	public void zhUtilTest(){
		//全角To半角
		String str1 = ZhUtil.toDBC("处理的符号：，！    不处理的符号『』【】");
		Assert.assertEquals("处理的符号:,!    不处理的符号『』【】", str1);
		
		//半角To全角
		String str2 = ZhUtil.toSBC("处理的符号,.");
		Assert.assertEquals("处理的符号，．", str2);
		
		//简体转繁体
		ZhUtil.initS2T();
		String t_str1 = ZhUtil.toTraditional("简体到繁体中文");
		Assert.assertEquals("簡體到繁體中文", t_str1);
		
		//繁体转简体
		ZhUtil.initS2T();
		String s_str2 = ZhUtil.toSimplified("簡體中文到繁體中文轉換這件事沒有捷徑，只能硬來。");
		Assert.assertEquals("简体中文到繁体中文转换这件事没有捷径，只能硬来。", s_str2);
		
		//调用我是为了释放资源，要是不调那一堆替换的map可就一直自内存里呆着。
		ZhUtil.clean();
	}
}
