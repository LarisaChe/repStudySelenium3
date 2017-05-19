import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by Администратор on 19.05.2017.
 */
public class TestDriverFF {
    private WebDriverWait wait;
    private WebDriver driver;

    @BeforeTest
    public void start() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testFF() {
        TestCheckProduct testFFd = new TestCheckProduct();
        testFFd.testCheckProduct(driver, wait);
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }
}
