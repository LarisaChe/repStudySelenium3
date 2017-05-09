import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 Задание 3
 */
public class TestLoginAdmin {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriver firefoxDriver;
    private WebDriver driverIE;

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 100);
    }

    @Test
    public void trytest() {
        driver.get("http://localhost/litecart/admin/");
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();
        //wait.until(titleIs("webdriver - Поиск в Google"));


    }

    @AfterTest
    public void stop() {
       driver.quit();
       driver = null;
    }

    //  Задание 4. FireFox и IE

    @BeforeTest
    public void startFF() {
        firefoxDriver = new FirefoxDriver();
        //driver = new ChromeDriver();
        wait = new WebDriverWait(firefoxDriver, 100);
    }
    @Test
    public void trytestFF() {
        firefoxDriver.get("http://localhost/litecart/admin/");
        firefoxDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        firefoxDriver.findElement(By.name("username")).sendKeys("admin");
        firefoxDriver.findElement(By.name("password")).sendKeys("admin");
        firefoxDriver.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();
    }

    @AfterTest
    public void stopFF() {
        firefoxDriver.quit();
        firefoxDriver = null;
    }
// IE   driverIE
   //DesiredCapabilities caps = new DesiredCapabilities();
   // caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
   /* driverIE = new InternetExplorerDriver(); //(caps);

    @BeforeTest
    public void startIE() {
        driverIE = new FirefoxDriver();
        //driver = new ChromeDriver();
        wait = new WebDriverWait(driverIE, 100);
    }
    @Test
    public void trytestIE() {
        driverIE.get("http://localhost/litecart/admin/");
        driverIE.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        driverIE.findElement(By.name("username")).sendKeys("admin");
        driverIE.findElement(By.name("password")).sendKeys("admin");
        driverIE.findElement(By.xpath("//*[@id=\"box-login\"]/form/div[2]/button")).click();
    }

    @AfterTest
    public void stopIE() {
        driverIE.quit();
        driverIE = null;
    }*/
}
