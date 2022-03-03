package test.seleniumhq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class A {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  
  private static ChromeDriverService service;


 @BeforeClass
  public static void createAndStartService() throws IOException {
    service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("C:\\Users\\caomei\\Documents\\maven\\repository\\org\\seleniumhq\\driver\\chromedriver.exe"))
       .usingAnyFreePort()
        .build();
    service.start();
  }
  
  
  
  @Before
  public void setUp() throws Exception {
    driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome());
    baseUrl = "https://www.baidu.com";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testA() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.id("kw")).click();
        driver.findElement(By.id("kw")).clear();
        driver.findElement(By.id("kw")).sendKeys("e");
        driver.findElement(By.id("su")).click();
        driver.findElement(By.id("kw")).click();
        Thread.sleep(1000);
        assertEquals("e_百度搜索", driver.getTitle());
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
