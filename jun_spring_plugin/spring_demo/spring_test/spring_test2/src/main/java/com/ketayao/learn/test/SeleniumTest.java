/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.Selenium.java
 * Class:			Selenium
 * Date:			2012-12-12
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/

package com.ketayao.learn.test;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-12-12 上午11:30:01
 */

public class SeleniumTest {
	public void test() {
		SeleniumServer server = new SeleniumServer();
		String seleniumHost = "localhost";//因为我们是在本机上启动的Selenium server，所以host是localhost
		String seleniumPort = "4444";//selenium 默认端口是4444
		String browser = "*firefox";//指定浏览器,如果firefox没有安装在默认路径下，必须指定firefox的安装路径
		String browserUrl = "http://localhost:9999/gris-src-tool-faces"; //访问的webapp的地址
		selenium = new DefaultSelenium("localhost", "4444", seleniumBrowser, browserUrl);
		
		
		String url = "http://localhost:9999/gris-src-tool-faces"; //访问的webapp的地址
			selenium.open(url);
			assertTrue(selenium.isElementPresent("//input[@id='indexForm:UserName']"));
			assertTrue(selenium.isElementPresent("//input[@id='indexForm:Password']"));
			selenium.type("//input[@name='indexForm:UserName']", "admin");
			selenium.type("//input[@name='indexForm:Password']", "admin");
			selenium.click("//input[@type='image']");
			selenium.waitForPageToLoad("6000");
			assertEquals(selenium.getText(“//div[@class=‘logo_title’]”), “GRIS系统转台查询工具");
					
					
		// Create a new instance of the html unit driver
		// Notice that the remainder of the code relies on the interface,         
	    // not the implementation.        
					WebDriver driver = new HtmlUnitDriver();        
					// And now use this to visit Google        
					driver.get("http://www.google.com");        
					// Find the text input element by its name        
					WebElement element = driver.findElement(By.name("q"));        
					// Enter something to search for        
					element.sendKeys("Cheese!");        
					// Now submit the form. WebDriver will find the form for us from the element        
					element.submit();       
					// Check the title of the page        
					System.out.println("Page title is: " + driver.getTitle()); 
	}
}
