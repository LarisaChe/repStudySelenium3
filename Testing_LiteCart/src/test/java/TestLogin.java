
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class TestLogin {
    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeTest
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 100);
    }

    @Test
    public void trytest() {
        driver.get("http://localhost/litecart/public_html/en/");

        Set<String> handles = driver.getWindowHandles();
        //Set<String> handle = driver.getWindowHandles();

        //driver.getWindowHandle()
        driver.switchTo().window(handles.toArray(new String[0])[0]);
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        //driver.findElement(By.name("email")).sendKeys("Ctrl+F4");
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
