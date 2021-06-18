/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.selenium.BaseSeleniumTest.java
 * Class:			BaseSeleniumTest
 * Date:			2012-12-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-6 下午2:31:07 
 */

public class BaseSeleniumTest {
	
	@Test
	public void testBase() throws MalformedURLException {
		//不使用Selenium Server：
		WebDriver driver = new FirefoxDriver();

		//使用Selenium Server：
		DesiredCapabilities capabilities = new DesiredCapabilities("firefox", "", Platform.ANY);
		WebDriver driver2 = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub/"), capabilities);
		
		/**
		 * 	1. waitForPageLoad()
			selenium1.0的waitForPageLoad(timeout)函数没有了，
			而那些承诺会block住直到页面完全打开的get(url),click(by)函数，
			在firefox下一点效果没有。
			好在有另一个解决方式，implicitlyWait会findByElement()失败时，
			隐式等待直到Element出现。 这样，就连1.0那句烦人的waitForPage()也省了。
		 */
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.SECONDS);
	}
	
	@Test
	public void testBase2() throws MalformedURLException {
		//不使用Selenium Server：
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://www.baidu.com");
		
		WebElement titleElement = driver.findElement(By.tagName("title"));
		Assert.assertEquals("百度一下，你就知道", titleElement.getText());
		
		WebElement inputElement = driver.findElement(By.id("kw"));
		inputElement.sendKeys("o");
		inputElement.submit();
		
		WebElement titleElement2 = driver.findElement(By.tagName("title"));
		Assert.assertEquals("百度搜索_o", titleElement2.getText());
	}
}
