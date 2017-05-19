import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Администратор on 19.05.2017.
 */
public class TestDriverChrome {
    private WebDriverWait wait;
    private WebDriver driver;

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testChrome() {
        TestCheckProduct testChromed = new TestCheckProduct();
        testChromed.testCheckProduct(driver, wait);
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }

}
