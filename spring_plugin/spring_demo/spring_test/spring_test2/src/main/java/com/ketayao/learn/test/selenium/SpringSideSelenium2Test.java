/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.selenium.SpringSideSelenium2Test.java
 * Class:			SpringSideSelenium2Test
 * Date:			2012-12-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.selenium;

import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springside.modules.test.selenium.Selenium2;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-6 下午3:01:22 
 */

public class SpringSideSelenium2Test {
	/**
	 * 	0.setStopAtShutdown()

		在JVM退出时关闭Selenium。因为每次创建Selenium实体都需要相当的消耗，因此希望在整个测试过程中只启动一次Selenium。
		但Junit没有所有测试完毕后执行某函数的概念，因此注册了一个JVM的shutdown hook，在JVM退出时调用selenium的quit()方法。
		
		1. waitForPageLoad()
		
		selenium1.0的waitForPageLoad(timeout)函数没有了，而那些承诺会block住直到页面完全打开的get(url),click(by)函数，在firefox下一点效果没有。
		
		好在有另一个解决方式，implicitlyWait会findByElement()失败时，隐式等待直到Element出现。 这样，就连1.0那句烦人的waitForPage()也省了。
		
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
		2. open(url)
		
		2.0的driver.get(url)居然必须输入完整路径，不许使用相对路径。只好抄了下兼容函数，如果路径是相对路径时，自动补上baseUrl.
		
		3. type(by,text)
		
		2.0的type不会管input框里原来有没有值，只好自己补一个clean()上去.
		
		4. check/uncheck/isChecked系列
		
		2.0没有checkbox的check这个概念，只有isSelected， 又帮它向后兼容了一把。
		
		5. Select系列
		
		总算，在处理Select框时，support包里有个org.openqa.selenium.support.ui.Select，里面有一堆丰富的函数可用,如
		
		s.getSelect(by).selectByValue(value);
		s.getSelect(by).getFirstSelectedOption();
		6. getValue(by)
		
		唉， WebElement这个抽象的对象里，getValue需要调用element.getAttribute("value")。
		
		7. waitfor系列
		
		waitfor是Ajax 测试中很重要的功能，2.0的support包里另一个给力的对象，在Selenium2里封装了waitForTextPresent(by,text,timeout)， waitForValuePresent(by,value,timeout), waitForVisible(by,timeout)
		
		但ExpectedConditions里还有更多更多的条件可以使用，如：
		
		s.waitForCondition(ExpectedConditions.xxxxxx(yyy,zzz), timeout);
		8.其他1.0特有函数
		
		isTextPresent(text),简单粗暴的看看页面里有没有出现某文本。
		
		getTable(by, rowIndex, colIndex), 取得单元格中的text。
	 * 描述
	 * @throws MalformedURLException
	 */
	@Test
	public void testSpringSideSelenium2() throws MalformedURLException {
		WebDriver driver = new HtmlUnitDriver();
		Selenium2 selenium2 = new Selenium2(driver); 
		
		selenium2.setStopAtShutdown();
		
		WebElement element =driver.findElement(By.xpath("//input[@id='passwd-id']")); 
	}
}
