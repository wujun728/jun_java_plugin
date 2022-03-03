package test.seleniumhq;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springrain.frame.util.Finder;
import org.springrain.selenium.entity.Htmlcase;
import org.springrain.selenium.entity.Htmlfield;
import org.springrain.selenium.entity.Htmlfunction;
import org.springrain.selenium.entity.Validaterule;
import org.springrain.selenium.service.IHtmlcaseService;
import org.springrain.selenium.service.IHtmlfieldService;
import org.springrain.selenium.service.IHtmlfunctionService;
import org.springrain.selenium.service.IValidateruleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LoginTest {

   // String funcitonId = "testlogin";
   // String ctx = "http://127.0.0.1:8080/springrain";

    @Resource
    private IHtmlfunctionService htmlfunctionService;

    @Resource
    private IHtmlfieldService htmlfieldService;

    @Resource
    private IValidateruleService validateruleService;

    @Resource
    private IHtmlcaseService htmlcaseService;

    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    private static ChromeDriverService service;

    @BeforeClass
    public static void createAndStartService() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(
                        "C:\\Users\\caomei\\Documents\\maven\\repository\\org\\seleniumhq\\driver\\chromedriver.exe"))
                .usingAnyFreePort().build();
        service.start();
    }

    @Before
    public void setUp() throws Exception {
        driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testFunctions() throws Exception {
        Finder finder = Finder.getSelectFinder(Htmlfunction.class).append(" order by sortno asc ");
        List<Htmlfunction> list = htmlfunctionService.queryForList(finder, Htmlfunction.class);

        for (Htmlfunction htmlfunction : list) {
            testOneFunction(htmlfunction);
        }

    }

    private void testOneFunction(Htmlfunction htmlfunction) throws Exception {

        driver.get(htmlfunction.getUrl());
        Thread.sleep(1000);

        // 已经成功的测试用例,每个字段只保留最后一个成功的测试用例,测试下一个字段时,先设置上一个字段的正确值
        List<Htmlcase> successList = new ArrayList<>();

        // 查询所有的测试用例
        Finder finder = Finder.getSelectFinder(Htmlcase.class).append(" order by fuctionId asc,sortno asc ");
        List<Htmlcase> listHtmlCase = htmlcaseService.queryForList(finder, Htmlcase.class);

        for (Htmlcase htmlcase : listHtmlCase) {

            if (CollectionUtils.isNotEmpty(successList)) {
                for (Htmlcase hc : successList) {
                    setHtmlFieldValue(hc);
                }
            }

            // 规则Id
            String ruleId = htmlcase.getRuleId();
            Validaterule validaterule = validateruleService.findValidateruleById(ruleId);

            // 字段Name
            Htmlfield htmlfield = htmlfieldService.findHtmlfieldById(validaterule.getFieldId());

            // 字段赋值
            setHtmlFieldValue(htmlcase);

            // 点击功能按钮
            driver.findElement(By.xpath(htmlfunction.getXpath())).click();

            Thread.sleep(1000);

            Integer resultType = validaterule.getResultType();
            String resultValue = validaterule.getValidateElementValue();

            // 验证真实的值
            String realValue = "";

            // xpath 无法使用再根目录,具体原因还要查找
            if (0 - validaterule.getValidateFindType() == 0) {
                realValue = driver.getTitle();
            }else {
                realValue = driver.findElement(By.xpath(validaterule.getValidateXpath())).getText();
            }

            if (realValue.equals(resultValue)) {
                System.out.println(htmlfield.getName() + "的规则:" + validaterule.getValidateElementValue() + "验证通过");
            } else {
                System.out.println(htmlfield.getName() + "的规则:" + validaterule.getValidateElementValue() + "验证失败");
            }

            Thread.sleep(1000);

            // 如果是结果成功
            if (5 - resultType == 0) {
                successList.add(htmlcase);
            }

        }

        Thread.sleep(1000);

    }

    private void setHtmlFieldValue(Htmlcase htmlcase) throws Exception {
        // 规则Id
        String ruleId = htmlcase.getRuleId();
        Validaterule validaterule = validateruleService.findValidateruleById(ruleId);

        // 字段Name
        Htmlfield htmlfield = htmlfieldService.findHtmlfieldById(validaterule.getFieldId());
        // 需要赋值字段
        WebElement webElement = driver.findElement(By.xpath(htmlfield.getXpath()));
        webElement.clear();

        // 测试用例需要的值
        String htmlFieldValue = htmlcase.getHtmlFieldValue();
        // 设置字段值
        webElement.sendKeys(htmlFieldValue);

        Thread.sleep(1000);

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
