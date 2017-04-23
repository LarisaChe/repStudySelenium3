
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestLogin {
    private WebDriver driver;
    //private WebDriverWait wait;

    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, 100);
    }

    @Test
    public void trytest() {
        driver.get("http://localhost/litecart/public_html/en/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.name("email")).sendKeys("testortttt@mail.ru");
        driver.findElement(By.name("password")).sendKeys("12345678");
        driver.findElement(By.name("login")).click();
        //wait.until(ap By.id("You are now logged in as Тестор Тесторов."));
    }

    @AfterTest
    public void stop() {
        driver.quit();
        driver = null;
    }

}
