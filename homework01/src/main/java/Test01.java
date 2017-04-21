
//import org.junit.Test;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

//import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Test01 {

    private WebDriver driver;
    private WebDriverWait wait;

    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public void test01() {
	//public static void main (String[] args) {
	//	System.out.println("Hello, world");
	//	driver.navigate().to("http://www.google.com");
        driver.get("http://www.google.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.name("q")).sendKeys("webdriver");
		driver.findElement(By.name("btnG")).click();
		wait.until(titleIs("webdriver - Поиск в Google"));
    }
    public void stop() {
        driver.quit();
        driver = null;
    }
}