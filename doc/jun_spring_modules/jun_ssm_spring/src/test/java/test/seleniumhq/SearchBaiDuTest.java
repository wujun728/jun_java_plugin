package test.seleniumhq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
@RunWith(JUnit4.class)
public class SearchBaiDuTest {
    
       private static ChromeDriverService service;
       private WebDriver driver;
    
      @BeforeClass
       public static void createAndStartService() throws IOException {
         service = new ChromeDriverService.Builder()
             .usingDriverExecutable(new File("C:\\Users\\caomei\\Documents\\maven\\repository\\org\\seleniumhq\\driver\\chromedriver.exe"))
            .usingAnyFreePort()
             .build();
         service.start();
       }
    
       @AfterClass
       public static void createAndStopService() {
         service.stop();
       }
    
       @Before
       public void createDriver() {
           
           driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome());
           //设置隐性等待时间    
           driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);    
       }
    
       @After
       public void quitDriver() {
         driver.quit();
       }
    
       @Test
       public void testBaiduSearch() throws InterruptedException {
         driver.get("https://www.baidu.com");
         WebElement searchBox = driver.findElement(By.name("wd"));
             searchBox.sendKeys("webdriver");
             
             WebElement submitBtn = driver.findElement(By.id("su"));
             submitBtn.click();
             
             Thread.sleep(2000);
             assertEquals("webdriver_百度搜索", driver.getTitle());
       }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 //   @Test
    public void test1() throws Exception {
        
        
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\caomei\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");    
            
        //初始化一个chrome浏览器实例，实例名称叫driver    
        //WebDriver driver = new ChromeDriver();    
        
        DesiredCapabilities dc = DesiredCapabilities.chrome();  
       WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dc);  
        

        
        //最大化窗口    
        driver.manage().window().maximize();    
        //设置隐性等待时间    
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);    
        // get()打开一个站点    
        driver.get("https://www.baidu.com"); 
        
        //getTitle()获取当前页面title的值    
        System.out.println("当前打开页面的标题是： "+ driver.getTitle());    
            
        //关闭并退出浏览器    
        driver.quit();    
        
        
    }
}
